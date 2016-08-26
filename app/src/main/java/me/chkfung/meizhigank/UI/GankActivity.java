package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Dagger.Component.DaggerGankPresenterComponent;
import me.chkfung.meizhigank.Dagger.Module.GankPresenterModule;
import me.chkfung.meizhigank.Dagger.Presenter.GankPresenter;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankActivity extends BaseActivity {

    //    GankContract.Presenter mPresenter = new GankPresenter();
    String mDate;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Inject
    GankPresenter gankPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        mDate = getIntent().getExtras().getString("Date");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(mDate);
//        animateToolbar();

//        mPresenter.attachView(this);
//        onRefresh();

        GankFragment gankFragment = (GankFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if (gankFragment == null) {
            gankFragment = GankFragment.newInstance(mDate);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_content, gankFragment);
            fragmentTransaction.commit();
        }
        DaggerGankPresenterComponent.builder()
                .appComponent(MeizhiApp.get(this).getAppComponent())
                .gankPresenterModule(new GankPresenterModule(gankFragment))
                .build().inject(this);

    }
//
//    @Override
//    public void setupRecycleView(Day day) {
//        mDay = day;
//        viewswitcherLoading.setVisibility(View.INVISIBLE);
//        expandableListview.setAdapter(new GankExpandableRvAdapter(day));
//        expandableListview.setLayoutManager(new LinearLayoutManager(this));
//        rvGank.setAdapter(new GankRvAdapter(day));
//        rvGank.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//    }
//
//    @Override
//    public void showError(Throwable e) {
//        progressbar.setVisibility(View.INVISIBLE);
//        viewswitcherLoading.showNext();
//        Snackbar.make(findViewById(android.R.id.content), "Request Failed: " + e.getMessage(), Snackbar.LENGTH_LONG)
//                .setAction("Retry", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onRefresh();
//                    }
//                }).show();
//    }
//
//    @Override
//    public void animateToolbar() {
//        toolbarTitle.setAlpha(0f);
//        toolbarTitle.setScaleX(0.6f);
//        toolbarTitle.animate().scaleX(1f)
//                .alpha(1f)
//                .setStartDelay(300)
//                .setDuration(900)
//                .setInterpolator(new FastOutSlowInInterpolator())
//                .start();
//    }
//
//    @OnClick(R.id.refresh)
//    public void onRefresh() {
//        if (viewswitcherLoading.getCurrentView() != progressbar)
//            viewswitcherLoading.showPrevious();
//        mPresenter.getGank(mDate);
//    }
}
