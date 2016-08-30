package me.chkfung.meizhigank.UI;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.AboutRvAdapter;

import static me.chkfung.meizhigank.Util.CommonUtil.FancyAnimation;

/**
 * Created by Fung on 15/08/2016.
 */

public class AboutMeActivity extends BaseActivity {
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rv_about)
    RecyclerView rvAbout;
    @BindView(R.id.AppName)
    TextView AppName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

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

        String[][] item = {
                {"Butterknife", "Jake Wharton"},
                {"Dagger 2", "Google, Square"},
                {"Glide", "Bumptech - Sam Judd"},
                {"Gson", "Google"},
                {"Leak Canary", "Square"},
                {"Logger", "Orhan Obut"},
                {"Retrofit", "Square"},
                {"RxAndroid", "RxAndroid authors"},
                {"Support Library", "Android Developers"},
        };
        rvAbout.setNestedScrollingEnabled(false);
        rvAbout.setLayoutManager(new LinearLayoutManager(this));
        rvAbout.setAdapter(new AboutRvAdapter(item));

        if (savedInstanceState == null) {
            FancyAnimation(toolbarTitle);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_scale);
            animation.setInterpolator(new FastOutSlowInInterpolator());
            AppName.startAnimation(animation);
        }
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

}
