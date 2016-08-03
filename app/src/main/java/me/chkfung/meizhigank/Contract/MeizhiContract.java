package me.chkfung.meizhigank.Contract;

import android.net.Uri;

import me.chkfung.meizhigank.Base.BaseContract;
import rx.Observable;

/**
 * Created by Fung on 26/07/2016.
 */

public interface MeizhiContract {
    public interface Presenter extends BaseContract.Presenter<View> {
        void SaveImage(String url);

        Observable<Uri> DownloadImage(String mUrl);

    }

    public interface View extends BaseContract.View {
        void SaveMenuTapped();

        void DownloadFailure();
    }
}
