package me.chkfung.meizhigank.Util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Fung on 03/08/2016.
 */

public class ConnectionUtil {
    /**
     * Check Network availability
     *
     * @param mContext Current Context
     * @return true = Available
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
