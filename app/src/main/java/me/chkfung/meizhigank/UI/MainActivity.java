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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Contract.Presenter.MainPresenter;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;
import me.chkfung.meizhigank.Util.ConnectionUtil;

public class MainActivity extends BaseActivity implements MainContract.View { //, android.support.v4.app.LoaderManager.LoaderCallbacks<MainContract.Presenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    private ArrayList<DataInfo> MeizhiData = new ArrayList<>();
    private MeizhiRvAdapter meizhiRvAdapter;
    private MainContract.Presenter mainPresenter = new MainPresenter();
    private int paging = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setup Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mainPresenter.attachView(this);


        refreshlayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                summonMeizhi(true);
            }
        });

        meizhiRvAdapter = new MeizhiRvAdapter(MeizhiData);
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
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void refreshRv() {
        //To Prevent ViewHolder that had been loaded blink/ load
        meizhiRvAdapter.notifyItemRangeChanged(10 * paging++, Constants.MEIZHI_AMOUNT);
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

        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("RvLayoutManager", layoutManager.onSaveInstanceState());
        outState.putParcelableArrayList("Item", MeizhiData);
        outState.putInt("Page", paging);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable x = savedInstanceState.getParcelable("RvLayoutManager");
        layoutManager.onRestoreInstanceState(x);

        MeizhiData = savedInstanceState.getParcelableArrayList("Item");
        meizhiRvAdapter = new MeizhiRvAdapter(MeizhiData);
        rvMeizhi.setAdapter(meizhiRvAdapter);
        paging = savedInstanceState.getInt("Page");

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
}
