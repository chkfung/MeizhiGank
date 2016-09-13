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

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.CommonUtil;
import me.chkfung.meizhigank.ui.Adapter.AboutRvAdapter;

import static me.chkfung.meizhigank.Util.CommonUtil.FancyAnimation;

/**
 * About Me Activity
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
            ver = getString(R.string.version_error);
        }
        version.setText(ver);

        String[][] item = {
                {"Butterknife", "Jake Wharton"},
                {"Dagger 2", "Google, Square"},
                {"Glide", "Bumptech"},
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
            case R.id.action_share:
                CommonUtil.Share(this,getString(R.string.share_app_title),getString(R.string.share_app_url),getString(R.string.share_app_intent_chooser));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
