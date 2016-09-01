package me.chkfung.meizhigank.Dagger.Module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;

/**
 * Created by Fung on 25/08/2016.
 */
@Module
public class MainPresenterModule {
    private final MainContract.View mView;

    public MainPresenterModule(MainContract.View mView) {
        this.mView = mView;
    }

    @PresenterScope
    @Provides
    MainContract.View providesMainContractView() {
        return mView;
    }

    @PresenterScope
    @Provides
    Context provideContext() {
        return mView.getContext();
    }
}
