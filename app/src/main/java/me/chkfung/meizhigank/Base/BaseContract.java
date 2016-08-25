package me.chkfung.meizhigank.Base;

import android.content.Context;

/**
 * Created by Fung on 26/07/2016.
 */

public interface BaseContract {
    interface Presenter<T> {
        /**
         * Attach View to Presenter
         *
         * @param view View
         */
        void attachView(T view);

        /**
         * Detach View to prevent Memory Leak
         * Call it in onDestroy of all Activity
         */
        void detachView();

    }

    interface View {

        Context getContext();

        /**
         * General Method to display SnackBar
         * @param desc String to display
         */
        void SnackBarResult(String desc);

        /**
         * General Method to display Toast
         * @param desc String to display
         */
        void ToastResult(String desc);
    }


}
