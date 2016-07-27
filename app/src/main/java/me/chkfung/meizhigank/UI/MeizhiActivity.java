package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        progressbar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(data.get("URL"))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Object, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //TODO set Failure msg
                        progressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.image)
    public void onClick() {
        onBackPressed();
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
