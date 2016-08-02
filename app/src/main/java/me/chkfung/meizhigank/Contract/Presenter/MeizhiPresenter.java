package me.chkfung.meizhigank.Contract.Presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.MeizhiApp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import rx.Subscription;

/**
 * Created by Fung on 01/08/2016.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    MeizhiContract.View mView;
    Subscription mSubscription;

    @Override
    public void SaveImage(String mUrl) {
        final File appDir = new File(Environment.getExternalStorageDirectory(), "MeizhiGank");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        Request mDownloadRequest = new Request.Builder().
                url(mUrl).
                build();
        MeizhiApp meizhiApp = MeizhiApp.get(mView.getContext());
        meizhiApp.getOkHttpClient().newCall(mDownloadRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e(e, "Image Save Fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File downloadedFile = new File(appDir, "2.png");
                BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
                sink.writeAll(response.body().source());
                sink.close();
                Logger.i("Save Image Success");

                Uri uri = Uri.fromFile(downloadedFile);
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                mView.getContext().sendBroadcast(scannerIntent);
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

    @Override
    public Subscription getmSubscription() {
        return mSubscription;
    }

    @Override
    public MeizhiContract.View getView() {
        return mView;
    }
}
