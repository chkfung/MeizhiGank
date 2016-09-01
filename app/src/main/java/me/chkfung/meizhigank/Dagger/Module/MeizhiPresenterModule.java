package me.chkfung.meizhigank.Dagger.Module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;

/**
 * Created by Fung on 29/08/2016.
 */
@Module
public class MeizhiPresenterModule {
    private final MeizhiContract.View mView;

    public MeizhiPresenterModule(MeizhiContract.View view) {
        mView = view;
    }

    @PresenterScope
    @Provides
    MeizhiContract.View provideView() {
        return mView;
    }

    @PresenterScope
    @Provides
    Context provideContext() {
        return mView.getContext();
    }
}
