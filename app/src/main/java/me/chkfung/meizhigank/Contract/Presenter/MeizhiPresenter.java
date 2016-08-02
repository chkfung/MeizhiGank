package me.chkfung.meizhigank.Contract.Presenter;

import android.os.Environment;

import java.io.File;

import me.chkfung.meizhigank.Contract.MeizhiContract;
import rx.Subscription;

/**
 * Created by Fung on 01/08/2016.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    MeizhiContract.View mView;
    Subscription mSubscription;

    @Override
    public void SaveImage() {
        File appDir = new File(Environment.getExternalStorageDirectory(), "MeizhiGank");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
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
