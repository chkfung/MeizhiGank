package me.chkfung.meizhigank.Contract;

import android.net.Uri;

import me.chkfung.meizhigank.Base.BaseContract;
import rx.Observable;

/**
 * Created by Fung on 26/07/2016.
 */

public interface MeizhiContract {
    interface Presenter extends BaseContract.Presenter<View> {

        /**
         * Save Image to Storage
         *
         * @param url URL
         */
        void SaveImage(String url);

        /**
         * Download Image using OkHttpClient
         *
         * @param mUrl URL
         * @return Uri for informing gallery to update
         */
        Observable<Uri> DownloadImage(String mUrl);

    }

    interface View extends BaseContract.View {
        void SaveMenuTapped();

        void DownloadFailure();
    }
}
