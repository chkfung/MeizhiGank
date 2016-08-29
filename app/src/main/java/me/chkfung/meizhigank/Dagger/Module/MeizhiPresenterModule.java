package me.chkfung.meizhigank.Dagger.Module;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;

/**
 * Created by Fung on 29/08/2016.
 */
@Module
public class MeizhiPresenterModule {
    private MeizhiContract.View view;

    public MeizhiPresenterModule(MeizhiContract.View view) {
        this.view = view;
    }

    @PresenterScope
    @Provides
    MeizhiContract.View provideView() {
        return view;
    }
}
