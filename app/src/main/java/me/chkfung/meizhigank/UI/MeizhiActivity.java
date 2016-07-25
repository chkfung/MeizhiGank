package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 25/07/2016.
 */

public class MeizhiActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        Glide.with(this)
                .load(data.get("URL"))
                .into(image);
//        appbar.setAlpha(0.7f);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_share:
                break;
            case R.id.action_save:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
