package me.chkfung.meizhigank.Dagger.Presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import me.chkfung.meizhigank.Contract.MeizhiContract;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Fung on 01/08/2016.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    MeizhiContract.View mView;
    Subscription mSubscription;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Scheduler scheduler;

    @Inject
    MeizhiPresenter(MeizhiContract.View view) {
        mView = view;
    }

    @Override
    public void SaveImage(final String mUrl) {
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = DownloadImage(mUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(scheduler)
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {
                        Logger.i("Save Image Success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "Download Failure");
                        mView.DownloadFailure();
                    }

                    @Override
                    public void onNext(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        mView.getContext().sendBroadcast(scannerIntent);
                    }
                });
    }

    @Override
    public Observable<Uri> DownloadImage(final String mUrl) {
        return Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                //Create Directory
                File appDir = new File(Environment.getExternalStorageDirectory(), "MeizhiGank");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                //Basic Initialize
                Request mDownloadRequest = new Request.Builder()
                        .url(mUrl)
                        .build();
                try {
                    Response resp = okHttpClient.newCall(mDownloadRequest).execute();
                    if (resp.isSuccessful()) {
                        File downloadedFile = new File(appDir, mUrl.substring(mUrl.length() - 20));
                        BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
                        sink.writeAll(resp.body().source());
                        sink.close();
                        subscriber.onNext(Uri.fromFile(downloadedFile));
                    }
                    if (resp.code() == 504)
                        throw new IOException("File is not cached");
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void attachView(MeizhiContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
