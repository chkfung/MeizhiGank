package me.chkfung.meizhigank.Dagger.Component;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.AppModule;
import me.chkfung.meizhigank.Dagger.Module.NetModule;
import me.chkfung.meizhigank.UI.MainActivity;

/**
 * Created by Fung on 12/08/2016.
 */
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void injectActivity(MainActivity mainActivity);
}
