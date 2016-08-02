package me.chkfung.meizhigank;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Fung on 21/07/2016.
 */

public class MeizhiApp extends Application {

    private NetworkApi networkApi;
    private Scheduler defaultSubscribeScheduler;
    private OkHttpClient okHttpClient;
    public static MeizhiApp get(Context context) {
        return (MeizhiApp) context.getApplicationContext();
    }

    public NetworkApi getNetworkApi() {
        if (networkApi == null)
            networkApi = NetworkApi.Factory.create();
        return networkApi;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null)
            okHttpClient = NetworkApi.OkHttpFactory.create();
        return okHttpClient;
    }

    public Scheduler getDefaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null)
            defaultSubscribeScheduler = Schedulers.io();
        return defaultSubscribeScheduler;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("Tibber Say");
    }
}
