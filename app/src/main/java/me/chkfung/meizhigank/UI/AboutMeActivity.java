package me.chkfung.meizhigank.UI;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.AboutRvAdapter;

/**
 * Created by Fung on 15/08/2016.
 */

public class AboutMeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rv_about)
    RecyclerView rvAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PackageManager pck = getPackageManager();
        String ver;
        try {
            PackageInfo pckInfo = pck.getPackageInfo(getPackageName(), 0);
            ver = pckInfo.versionName + "." + pckInfo.versionCode;
        } catch (Exception e) {
            ver = "Unable to retrieve Info";
        }
        version.setText(ver);
        animateToolbar();

        String[][] item = {
                {"Butterknife", "Jake Wharton"},
                {"Dagger 2", "Google"},
                {"Glide", "Bumptech"},
                {"Gson", "Google"},
                {"Leak Canary", "Square Up"},
                {"Logger", "Orhanobut"},
                {"Retrofit", "Square Up"},
                {"RxAndroid", "ReactiveX"},
                {"Support Library", "Android"},
        };
        rvAbout.setLayoutManager(new LinearLayoutManager(this));
        rvAbout.setAdapter(new AboutRvAdapter(item));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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
