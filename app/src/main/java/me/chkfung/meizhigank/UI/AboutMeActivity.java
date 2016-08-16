package me.chkfung.meizhigank.UI;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Me");
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
