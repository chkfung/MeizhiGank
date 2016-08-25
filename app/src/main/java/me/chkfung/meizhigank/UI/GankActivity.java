package me.chkfung.meizhigank.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Dagger.Presenter.GankPresenter;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.GankExpandableRvAdapter;
import me.chkfung.meizhigank.UI.Adapter.GankRvAdapter;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankActivity extends BaseActivity implements GankContract.View {

    GankContract.Presenter mPresenter = new GankPresenter();
    String mDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.expandable_listview)
    RecyclerView expandableListview;
    @BindView(R.id.viewswitcher)
    ViewSwitcher viewswitcher;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    Day mDay;
    Menu menu;
    @BindView(R.id.viewswitcher_loading)
    ViewSwitcher viewswitcherLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        mDate = getIntent().getExtras().getString("Date");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(mDate);
        animateToolbar();

        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public void setupRecycleView(Day day) {
        mDay = day;
        viewswitcherLoading.setVisibility(View.INVISIBLE);
        expandableListview.setAdapter(new GankExpandableRvAdapter(day));
        expandableListview.setLayoutManager(new LinearLayoutManager(this));
        rvGank.setAdapter(new GankRvAdapter(day));
        rvGank.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void showError(Throwable e) {
        progressbar.setVisibility(View.INVISIBLE);
        viewswitcherLoading.showNext();
        Snackbar.make(findViewById(android.R.id.content), "Request Failed: " + e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefresh();
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        this.menu = menu;
        setMenuIcon(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_viewswitcher:
                SharedPreferences sharedpreferences = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (viewswitcher.getCurrentView() == expandableListview) {
                    viewswitcher.showNext();
                    editor.putBoolean("GRID_LAYOUT", true);
                    menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_expand);

                } else {
                    viewswitcher.showPrevious();
                    editor.putBoolean("GRID_LAYOUT", false);
                    menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_grid);

                }
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setMenuIcon(Menu menu) {
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        if (sharedpreferences.getBoolean("GRID_LAYOUT", false)) {
            viewswitcher.showNext();
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_expand);
        } else {
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_grid);
        }
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

    @OnClick(R.id.refresh)
    public void onRefresh() {
        if (viewswitcherLoading.getCurrentView() != progressbar)
            viewswitcherLoading.showPrevious();
        mPresenter.getGank(mDate);
    }
}
