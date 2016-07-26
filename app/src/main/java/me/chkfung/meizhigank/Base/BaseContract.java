package me.chkfung.meizhigank.Base;

import android.content.Context;

import rx.Subscription;

/**
 * Created by Fung on 26/07/2016.
 */

public interface BaseContract {
    public interface Presenter<T> {
        void attachView(T view);

        void detachView();

        Subscription getmSubscription();

        T getView();
    }

    public interface View {
        Context getContext();
    }
}
