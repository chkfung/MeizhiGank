package me.chkfung.meizhigank.Dagger.Component;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.GankPresenterModule;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.UI.GankActivity;

/**
 * Created by Fung on 26/08/2016.
 */
@PresenterScope
@Component(
        modules = GankPresenterModule.class,
        dependencies = AppComponent.class
)
public interface GankPresenterComponent {
    void inject(GankActivity gankActivity);
}
