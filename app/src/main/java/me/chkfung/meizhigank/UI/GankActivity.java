package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Contract.Presenter.GankPresenter;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankActivity extends BaseActivity implements GankContract.View {

    GankContract.Presenter mPresenter = new GankPresenter();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        String mDate = getIntent().getExtras().getString("Date");
        Logger.i(mDate);
        mPresenter.attachView(this);
        mPresenter.getGank(mDate);

        rvGank.setAdapter(new GankRvAdapter());
        rvGank.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}
