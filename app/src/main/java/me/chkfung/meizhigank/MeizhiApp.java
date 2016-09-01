package me.chkfung.meizhigank;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import me.chkfung.meizhigank.Dagger.Component.AppComponent;
import me.chkfung.meizhigank.Dagger.Component.DaggerAppComponent;
import me.chkfung.meizhigank.Dagger.Module.AppModule;
import me.chkfung.meizhigank.Dagger.Module.NetModule;

//import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

/**
 * Created by Fung on 21/07/2016.
 */

public class MeizhiApp extends Application {

    private AppComponent appComponent;

    public static MeizhiApp get(Context context) {
        return (MeizhiApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Logger.init("Tibber Say");
            LeakCanary.install(this);
//        AndroidDevMetrics.initWith(this);
        } else {
            Logger.init().setLogLevel(LogLevel.NONE);
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
