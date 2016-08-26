package me.chkfung.meizhigank.Dagger.Module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.UI.Adapter.GankExpandableRvAdapter;
import me.chkfung.meizhigank.UI.Adapter.GankRvAdapter;

/**
 * Created by Fung on 26/08/2016.
 */
@Module
public class GankPresenterModule {
    private GankContract.View view;


    public GankPresenterModule(GankContract.View view) {
        this.view = view;
    }

    @PresenterScope
    @Provides
    GankContract.View provideGankContractView() {
        return view;
    }

    @PresenterScope
    @Provides
    GankExpandableRvAdapter provideGankExpandableRvAdapter() {
        return new GankExpandableRvAdapter();
    }

    @PresenterScope
    @Provides
    LinearLayoutManager provideLinearLayoutManager(Context mContext) {
        return new LinearLayoutManager(mContext);
    }

    @PresenterScope
    @Provides
    GankRvAdapter provideGankRvAdapter() {
        return new GankRvAdapter();
    }

    @PresenterScope
    @Provides
    StaggeredGridLayoutManager provideStaggeredGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
