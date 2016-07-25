package me.chkfung.meizhigank;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by Fung on 21/07/2016.
 */

public class MeizhiApp extends Application {

    private NetworkApi networkApi;

    public static MeizhiApp get(Context context) {
        return (MeizhiApp) context.getApplicationContext();
    }

    public NetworkApi getNetworkApi() {
        if (networkApi == null)
            networkApi = NetworkApi.Factory.create();
        return networkApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("Tibber Say");

    }
}
