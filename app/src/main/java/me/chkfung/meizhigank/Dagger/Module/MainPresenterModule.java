package me.chkfung.meizhigank.Dagger.Module;

import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;

/**
 * Created by Fung on 25/08/2016.
 */
@Module
public class MainPresenterModule {
    private MainContract.View mView;

    public MainPresenterModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    MainContract.View providesMainContractView() {
        return mView;
    }

    @Provides
    StaggeredGridLayoutManager provideLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Provides
    ArrayList<DataInfo> provideRvData() {
        return new ArrayList<>();
    }

    @Provides
    MeizhiRvAdapter provideRvAdapter() {
        return new MeizhiRvAdapter();
    }
}
