package me.chkfung.meizhigank.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Dagger.Component.DaggerMainPresenterComponent;
import me.chkfung.meizhigank.Dagger.Module.MainPresenterModule;
import me.chkfung.meizhigank.Dagger.Presenter.MainPresenter;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;
import me.chkfung.meizhigank.Util.ConnectionUtil;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String LAYOUT_MANAGER_SAVED_INSTANCE = "LAYOUT_MANAGER_SAVED_INSTANCE";
    private static final String PAGING_SAVED_INSTANCE = "PAGING_SAVED_INSTANCE";
    private static final String MEIZHI_DATA_SAVED_INSTANCE = "MEIZHI_DATA_SAVED_INSTANCE";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @Inject
    StaggeredGridLayoutManager layoutManager;
    @Inject
    ArrayList<DataInfo> MeizhiData;
    @Inject
    MeizhiRvAdapter meizhiRvAdapter;
    @Inject
    MainPresenter mainPresenter;
    private int paging = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setup Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DaggerMainPresenterComponent.builder()
                .appComponent(MeizhiApp.get(this).getAppComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .build().inject(this);

        refreshlayout.setColorSchemeResources(R.color.colorAccent, R.color.md_red_500);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                summonMeizhi(true);
            }
        });

        meizhiRvAdapter.setMeizhiList(MeizhiData);
        rvMeizhi.setAdapter(meizhiRvAdapter);
        rvMeizhi.setLayoutManager(layoutManager);

        rvMeizhi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lasPos = new int[layoutManager.getSpanCount()];
                layoutManager.findLastVisibleItemPositions(lasPos);
                if (MeizhiData.size() - Math.max(lasPos[0], lasPos[1]) < Constants.MEIZHI_PRELOAD &&
                        !refreshlayout.isRefreshing()) {
                    summonMeizhi(false);
                }
            }
        });

        if (savedInstanceState == null) {
            summonMeizhi(true);
        }
        animateToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void refreshRv(List<DataInfo> arrayList) {
        //To Prevent ViewHolder that had been loaded blink/ load
        meizhiRvAdapter.notifyItemRangeChanged(10 * paging++, Constants.MEIZHI_AMOUNT);
//        DiffUtil.DiffResult diffResult =DiffUtil.calculateDiff(new MeizhiDiffUtil(MeizhiData,arrayList));
//        diffResult.dispatchUpdatesTo(meizhiRvAdapter);

        refreshlayout.setRefreshing(false);
    }

    /**
     * Display Error Message
     *
     * @param e Exception Thrown
     */
    @Override
    public void networkError(Throwable e) {
        refreshlayout.setRefreshing(false);
        View rootView = findViewById(android.R.id.content);
        if (ConnectionUtil.isNetworkAvailable(this)) {
            Snackbar.make(rootView, "Error Message: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(rootView, "No Internet Connection and cache unavailable", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void summonMeizhi(boolean clearItem) {
        if (clearItem) {
            MeizhiData.clear();
            paging = 1;
        }
        refreshlayout.setRefreshing(true);
        mainPresenter.loadMeizhi(paging, MeizhiData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutMeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Toggle Night Mode
     *
     * @param v fab
     */
    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        //fixme Nougat not showing animation
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAYOUT_MANAGER_SAVED_INSTANCE, layoutManager.onSaveInstanceState());
        outState.putParcelableArrayList(MEIZHI_DATA_SAVED_INSTANCE, MeizhiData);
        outState.putInt(PAGING_SAVED_INSTANCE, paging);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Parcelable layoutManagerInstance = savedInstanceState.getParcelable(LAYOUT_MANAGER_SAVED_INSTANCE);
        layoutManager.onRestoreInstanceState(layoutManagerInstance);

        MeizhiData = savedInstanceState.getParcelableArrayList(MEIZHI_DATA_SAVED_INSTANCE);
        meizhiRvAdapter.setMeizhiList(MeizhiData);

        paging = savedInstanceState.getInt(PAGING_SAVED_INSTANCE);
    }

    @Override
    public void animateToolbar() {
        toolbarTitle.setAlpha(0f);
        toolbarTitle.setScaleX(0.6f);
        toolbarTitle.animate().scaleX(1f)
                .alpha(1f)
                .setStartDelay(300)
                .setDuration(900)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }

    @Override
    public void onBackPressed() {
        //Custom Exit Application Animation
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
