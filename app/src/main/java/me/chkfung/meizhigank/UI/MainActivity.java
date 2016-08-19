package me.chkfung.meizhigank.UI;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Contract.Presenter.MainPresenter;
import me.chkfung.meizhigank.Model.Meizhi;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.MeizhiRvAdapter;
import me.chkfung.meizhigank.Util.ConnectionUtil;

public class MainActivity extends BaseActivity implements MainContract.View { //, android.support.v4.app.LoaderManager.LoaderCallbacks<MainContract.Presenter> {

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
    private MainContract.Presenter mainPresenter = new MainPresenter();
    private int paging = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
//        if (savedInstanceState != null) {
//            getWindow().setBackgroundDrawable(null);
//            CommonUtil.StartAnim(findViewById(android.R.id.content));
//        }
//        getSupportLoaderManager().initLoader(101, null, this);
        mainPresenter.attachView(this);
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
        });
        summonMeizhi(true);
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
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutMeActivity.class));
                break;
        }
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
//        CommonUtil.ScreenshotAlpha(findViewById(android.R.id.content));
//        mFrame.addView();
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        else
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Bitmap bitmap = CommonUtil.getScreenShow(findViewById(android.R.id.content));
//        CommonUtil.ScreenshotAlpha(findViewById(android.R.id.content));
//        setContentView(R.layout.activity_main);
//        CommonUtil.ScreenshotAlpha(findViewById(android.R.id.content));
        //fixme - does not work for api below 23 (Marshmallow)
        //ref https://developer.android.com/reference/android/app/UiModeManager.html#setNightMode%28int%29
        if (Build.VERSION.SDK_INT >= 23) {
            UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
//                    uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    AppCompatDelegate.set
                    break;
                case Configuration.UI_MODE_NIGHT_NO:

//                    uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
//        Intent intentChangeTheme = new Intent(this,MainActivity.class);
////        intentChangeTheme.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intentChangeTheme);
//        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//        Logger.i("Day Night: "+ getDelegate().applyDayNight());
//        Snackbar.make(v, "Show Other Pages", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

}
