package me.chkfung.meizhigank.Base;

import android.content.Context;

import rx.Subscription;

/**
 * Created by Fung on 26/07/2016.
 */

public interface BaseContract {
    public interface Presenter<T> {
        /**
         * Attach View to Presenter
         *
         * @param view
         */
        void attachView(T view);

        /**
         * Detach View to prevent Memory Leak
         * Call it in onDestroy of all Activity
         */
        void detachView();

        Subscription getmSubscription();

        T getView();
    }

    public interface View {

        Context getContext();

        /**
         * General Method to display SnackBar
         * @param desc
         */
        void SnackBarResult(String desc);

        /**
         * General Method to display Toast
         * @param desc
         */
        void ToastResult(String desc);
    }


}
