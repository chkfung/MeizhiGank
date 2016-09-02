/*
 * Meizhi & Gank.io
 * Copyright (C) 2016 ChkFung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.chkfung.meizhigank.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;

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
import me.chkfung.meizhigank.Util.ConnectionUtil;
import me.chkfung.meizhigank.service.AlarmReceiver;
import me.chkfung.meizhigank.ui.Adapter.MeizhiRvAdapter;

import static me.chkfung.meizhigank.Util.CommonUtil.FancyAnimation;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String SAVED_INSTANCE_MEIZHI = "SAVED_INSTANCE_MEIZHI";
    private static final String NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED";
    private final MeizhiRvAdapter meizhiRvAdapter = new MeizhiRvAdapter();
    private final AlarmReceiver alarmReceiver = new AlarmReceiver();
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @Inject
    MainPresenter mainPresenter;
    @Inject
    SharedPreferences sharedPreferences;
    private StaggeredGridLayoutManager layoutManager;
    private ArrayList<DataInfo> MeizhiData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
    public void refreshRv() {
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
            Snackbar.make(rootView, getString(R.string.request_fail,e.getMessage()), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(rootView, R.string.internet_error, Snackbar.LENGTH_LONG)
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
        setMenuIcon(menu.findItem(R.id.action_notif));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutMeActivity.class));
                break;
            case R.id.action_notif:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean(NOTIFICATION_ENABLED, true)) {
                    editor.putBoolean(NOTIFICATION_ENABLED, false);
                    alarmReceiver.cancelAlarm(this);
                    Toast.makeText(this, R.string.notif_disable, Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean(NOTIFICATION_ENABLED, true);
                    alarmReceiver.setAlarm(this);
                    Toast.makeText(this, R.string.notif_enable, Toast.LENGTH_SHORT).show();
                }
                editor.apply();
                setMenuIcon(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Toggle Night Mode
     *
     */
    @OnClick(R.id.fab)
    public void onClick() {
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
        overridePendingTransition(R.anim.app_quit_in, R.anim.app_quit_out);
    }

    private void setMenuIcon(MenuItem item) {
        if (sharedPreferences.getBoolean(NOTIFICATION_ENABLED, true)) {
            item.setIcon(R.drawable.ic_notifications);
        } else {
            item.setIcon(R.drawable.ic_notifications_off);
        }
    }

}
