/*
 * Meizhi & Gank.io
 * Copyright (C) 2016 ChkFung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
 * Application Class to initialize Dagger dependencies
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
