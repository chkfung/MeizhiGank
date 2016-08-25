package me.chkfung.meizhigank.Dagger.Component;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.MainPresenterModule;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.UI.MainActivity;

/**
 * Created by Fung on 25/08/2016.
 */
@PresenterScope
@Component(
        dependencies = {AppComponent.class},
        modules = {MainPresenterModule.class}
)
public interface MainPresenterComponent {
    void inject(MainActivity mainActivity);
}
