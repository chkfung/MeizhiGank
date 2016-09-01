package me.chkfung.meizhigank.Dagger.Component;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.MeizhiPresenterModule;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.ui.MeizhiActivity;

/**
 * Created by Fung on 29/08/2016.
 */
@PresenterScope
@Component(
        modules = MeizhiPresenterModule.class,
        dependencies = AppComponent.class
)
public interface MeizhiPresenterComponent {
    void inject(MeizhiActivity meizhiActivity);
}
