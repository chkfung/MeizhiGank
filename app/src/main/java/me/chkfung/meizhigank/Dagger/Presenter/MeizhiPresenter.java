/*
 * Meizhi & Gank.io
 * Copyright (C) 2016 ChkFung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.chkfung.meizhigank.Dagger.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.R;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Meizhi Presenter that perform business logic
 * Created by Fung on 01/08/2016.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    @Inject
    Scheduler scheduler;
    @Inject
    Context context;
    private MeizhiContract.View mView;
    private Subscription mSubscription;
    private OkHttpClient.Builder okHttpClientBuilder;

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
                        mView.ImageSaved();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, context.getString(R.string.download_failure));
                        mView.DownloadFailure();
                    }

                    @Override
                    public void onNext(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                    }
                });
    }

    @Override
    public Observable<Uri> DownloadImage(final String mUrl) {
        return Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                //Create Directory
                File appDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.ImageDir));
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                //Basic Initialize
                Request mDownloadRequest = new Request.Builder()
                        .url(mUrl)
                        .build();
                final progressListener progressListener = new progressListener() {
                    @Override
                    public void update(long bytesloaded, long totalbytes, boolean done) {
                        int progress = (int) (bytesloaded / totalbytes);
                        mView.updateProgressBar(progress);
                    }
                };
                okHttpClientBuilder = new OkHttpClient.Builder();
                okHttpClientBuilder.addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                });
                try {
                    Response resp = okHttpClientBuilder.build().newCall(mDownloadRequest).execute();
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
        })
                ;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    interface progressListener {
        void update(long bytesloaded, long totalbytes, boolean done);
    }

    private static class ProgressResponseBody extends ResponseBody {
        final ResponseBody responseBody;
        final progressListener downloadProgress;
        BufferedSource bufferedSource;

        private ProgressResponseBody(ResponseBody responseBody, progressListener downloadProgress) {
            this.responseBody = responseBody;
            this.downloadProgress = downloadProgress;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    downloadProgress.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }
}
