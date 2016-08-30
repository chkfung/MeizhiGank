package me.chkfung.meizhigank.UI;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Dagger.Component.DaggerGankPresenterComponent;
import me.chkfung.meizhigank.Dagger.Module.GankPresenterModule;
import me.chkfung.meizhigank.Dagger.Presenter.GankPresenter;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.GankExpandableRvAdapter;
import me.chkfung.meizhigank.UI.Adapter.GankRvAdapter;

import static me.chkfung.meizhigank.Util.CommonUtil.FancyAnimation;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankActivity extends BaseActivity implements GankContract.View {

    private static final String SAVED_INSTANCE_GANK = "SAVED_INSTANCE_GANK";

    String mDate;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.expandable_listview)
    RecyclerView expandableListview;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;
    @BindView(R.id.viewswitcher)
    ViewSwitcher viewswitcher;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.refresh)
    ImageButton refresh;
    @BindView(R.id.viewswitcher_loading)
    ViewSwitcher viewswitcherLoading;

    @Inject
    CustomTabsIntent.Builder customTabsIntentBuilder;
    @Inject
    GankPresenter mPresenter;
    @Inject
    SharedPreferences sharedPreferences;

    Day mDay;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    GankExpandableRvAdapter gankExpandableRvAdapter = new GankExpandableRvAdapter();
    GankRvAdapter gankRvAdapter = new GankRvAdapter();
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        mDate = getIntent().getExtras().getString("Date");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(mDate);
        FancyAnimation(toolbarTitle);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        else
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

//        GankFragment gankFragment = (GankFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
//        if (gankFragment == null) {
//            gankFragment = GankFragment.newInstance(mDate);
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.add(R.id.fragment_content, gankFragment);
//            fragmentTransaction.commit();
//        }
        DaggerGankPresenterComponent.builder()
                .appComponent(MeizhiApp.get(this).getAppComponent())
                .gankPresenterModule(new GankPresenterModule(this))
                .build().inject(this);

        expandableListview.setLayoutManager(linearLayoutManager);
        rvGank.setLayoutManager(staggeredGridLayoutManager);
        if (savedInstanceState == null)
            onRefresh();
    }

    @Override
    public void setupRecycleView(Day day) {
        mDay = day;
        viewswitcherLoading.setVisibility(View.INVISIBLE);
        gankExpandableRvAdapter.setup(day);
        expandableListview.setAdapter(gankExpandableRvAdapter);
        gankRvAdapter.setup(day);
        rvGank.setAdapter(gankRvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        setMenuIcon(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_INSTANCE_GANK, mDay);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDay = savedInstanceState.getParcelable(SAVED_INSTANCE_GANK);
        setupRecycleView(mDay);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_viewswitcher:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (viewswitcher.getCurrentView() == expandableListview) {
                    viewswitcher.showNext();
                    editor.putBoolean("GRID_LAYOUT", true);
                    item.setIcon(R.drawable.ic_view_expand);
                } else {
                    viewswitcher.showPrevious();
                    editor.putBoolean("GRID_LAYOUT", false);
                    item.setIcon(R.drawable.ic_view_grid);
                }
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    @OnClick(R.id.refresh)
    public void onRefresh() {
        if (viewswitcherLoading.getCurrentView() != progressbar)
            viewswitcherLoading.showPrevious();
        mPresenter.getGank(mDate);
    }

    public void setMenuIcon(Menu menu) {
        if (sharedPreferences.getBoolean("GRID_LAYOUT", false)) {
            viewswitcher.showNext();
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_expand);
        } else {
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_grid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void startCustomTabIntent(String Desc, String url) {

        //Sharing Intent
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_SUBJECT, Desc);
        actionIntent.putExtra(Intent.EXTRA_TEXT, url);
        PendingIntent pi = PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_ONE_SHOT);

        //Share Icon
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_share);
        Bitmap icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        //Combine Intent and Icon
        customTabsIntentBuilder.setActionButton(icon, "Share Url", pi, true);

        //Build and Launch Url
        customTabsIntentBuilder.build().launchUrl(this, Uri.parse(url));
    }
}
