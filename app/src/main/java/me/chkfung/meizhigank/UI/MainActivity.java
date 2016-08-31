package me.chkfung.meizhigank.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import me.chkfung.meizhigank.Service.AlarmReceiver;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;
import me.chkfung.meizhigank.Util.ConnectionUtil;

import static me.chkfung.meizhigank.Util.CommonUtil.FancyAnimation;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String SAVED_INSTANCE_MEIZHI = "SAVED_INSTANCE_MEIZHI";

    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    StaggeredGridLayoutManager layoutManager;
    ArrayList<DataInfo> MeizhiData = new ArrayList<>();
    MeizhiRvAdapter meizhiRvAdapter = new MeizhiRvAdapter();
    @Inject
    MainPresenter mainPresenter;

    AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainPresenterComponent.builder()
                .appComponent(MeizhiApp.get(this).getAppComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .build().inject(this);
        alarmReceiver.setAlarm(this);

        refreshlayout.setColorSchemeResources(R.color.colorAccent, R.color.md_red_500);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                summonMeizhi(true);
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        else
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        FancyAnimation(toolbarTitle);
    }

    @OnClick(R.id.toolbar_title)
    void scrollToTop() {
        layoutManager.smoothScrollToPosition(rvMeizhi, null, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    //TODO Use DiffUtil to update adapter
    @Override
    public void refreshRv(List<DataInfo> arrayList) {
        //To Prevent ViewHolder that had been loaded blink/ load
        meizhiRvAdapter.notifyItemRangeChanged(MeizhiData.size(), Constants.MEIZHI_AMOUNT);
//        DiffUtil.DiffResult diffResult =DiffUtil.calculateDiff(new MeizhiDiffUtil(MeizhiData,arrayList));
//        diffResult.dispatchUpdatesTo(meizhiRvAdapter);

        refreshlayout.setRefreshing(false);
    }

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
        }
        refreshlayout.setRefreshing(true);
        mainPresenter.loadMeizhi(MeizhiData.size() / 10 + 1, MeizhiData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        appbar.setExpanded(true, false);
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

    /**
     * Save Recycler View Instance when
     * 1. Changing Night Mode
     * 2. Android Neko Multi Window Resizing
     *
     * @param outState Bundle to put instances
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(SAVED_INSTANCE_MEIZHI, MeizhiData);
        super.onSaveInstanceState(outState);
    }

    /**
     * Restore Recycler View Instance When Recreate or Configuration Changed
     *
     * @param savedInstanceState Saved Bundle
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MeizhiData = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_MEIZHI);
        meizhiRvAdapter.setMeizhiList(MeizhiData);

    }

    @Override
    public void onBackPressed() {
        //Custom Exit Application Animation
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
