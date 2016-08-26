package me.chkfung.meizhigank.Dagger.Component;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.AppModule;
import me.chkfung.meizhigank.Dagger.Module.NetModule;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.NetworkApi;
import rx.Scheduler;

/**
 * Created by Fung on 12/08/2016.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                NetModule.class
        }
)
public interface AppComponent {
    void inject(MeizhiApp meizhiApp);

    Application getApplication();

    NetworkApi getNetworkApi();

    Scheduler getScheduler();

    SharedPreferences getSharedPreferences();
}
