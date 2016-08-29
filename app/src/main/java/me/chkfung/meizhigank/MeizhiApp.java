package me.chkfung.meizhigank;

import android.app.Application;
import android.content.Context;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import me.chkfung.meizhigank.Dagger.Component.AppComponent;
import me.chkfung.meizhigank.Dagger.Component.DaggerAppComponent;
import me.chkfung.meizhigank.Dagger.Module.AppModule;
import me.chkfung.meizhigank.Dagger.Module.NetModule;

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
        LeakCanary.install(this);
        Logger.init("Tibber Say");
        AndroidDevMetrics.initWith(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://gank.io/"))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
