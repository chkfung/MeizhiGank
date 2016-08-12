package me.chkfung.meizhigank.UI;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Contract.Presenter.MainPresenter;
import me.chkfung.meizhigank.Model.Meizhi;
import me.chkfung.meizhigank.PresenterFactory;
import me.chkfung.meizhigank.PresenterLoader;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;
import me.chkfung.meizhigank.Util.ConnectionUtil;

public class MainActivity extends BaseActivity implements MainContract.View, android.support.v4.app.LoaderManager.LoaderCallbacks<MainContract.Presenter> {

    final private List<Meizhi.ResultsBean> MeizhiData = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    private MeizhiRvAdapter meizhiRvAdapter;
    private MainContract.Presenter mainPresenter;// = new MainPresenter();
    private int paging = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        getSupportLoaderManager().initLoader(101, null, this);
//        mainPresenter.attachView(this);
        refreshlayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                summonMeizhi(true);
            }
        });

        meizhiRvAdapter = new MeizhiRvAdapter(MeizhiData);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.animate().translationY(0f)
                            .setInterpolator(new OvershootInterpolator()).start();
                else
                    fab.animate().translationY(fab.getHeight() + getResources().getDimension(R.dimen.fab_margin))
                            .setInterpolator(new DecelerateInterpolator(4)).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mainPresenter.attachView(this);
        summonMeizhi(true);
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

        if (ConnectionUtil.isNetworkAvailable(this)) {
            Snackbar.make(fab, "Error Message: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(fab, "No Internet Connection and cache unavailable", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void summonMeizhi(boolean clearItem) {
        if (clearItem) {
            MeizhiData.clear();
            paging = 1;
        }
        //Refresh Layout wont show when onCreate
//        refreshlayout.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
        refreshlayout.setRefreshing(true);
//                    }
//                }
//        );
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        if (item.getItemId() == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //fixme - does not work for api below 23 (Marshmallow)
        //ref https://developer.android.com/reference/android/app/UiModeManager.html#setNightMode%28int%29
//        UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
//        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
//            case Configuration.UI_MODE_NIGHT_YES:
//                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                break;
//            case Configuration.UI_MODE_NIGHT_NO:
//
//                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                break;
//        }
////        recreate();
//        Logger.i("Day Night: "+ getDelegate().applyDayNight());
        Snackbar.make(v, "Show Other Pages", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public android.support.v4.content.Loader<MainContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory<MainContract.Presenter>() {
            @Override
            public MainContract.Presenter create() {
                return new MainPresenter();
            }
        });
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<MainContract.Presenter> loader, MainContract.Presenter data) {

        mainPresenter = data;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<MainContract.Presenter> loader) {
        mainPresenter = null;

    }

}
