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

import android.Manifest;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Dagger.Component.DaggerMeizhiPresenterComponent;
import me.chkfung.meizhigank.Dagger.Module.MeizhiPresenterModule;
import me.chkfung.meizhigank.Dagger.Presenter.MeizhiPresenter;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.CommonUtil;
import me.chkfung.meizhigank.Util.PermissionUtils;
import me.chkfung.meizhigank.ui.widget.LoadingCircleView;

/**
 * Created by Fung on 25/07/2016.
 */

public class MeizhiActivity extends BaseActivity implements MeizhiContract.View {
    private static final int SAVE_MEIZHI = 1;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    LoadingCircleView progressbar;
    @BindView(R.id.frame_meizhi)
    FrameLayout frameMeizhi;
    @Inject
    MeizhiPresenter mPresenter;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);

        DaggerMeizhiPresenterComponent.builder()
                .appComponent(MeizhiApp.get(this).getAppComponent())
                .meizhiPresenterModule(new MeizhiPresenterModule(this))
                .build().inject(this);

        frameMeizhi.getBackground().setAlpha(255);

        //TODO there should be better way
        //To Prevent Image Flicker 2 times when glide load success and previous image transition,
        //Save Image in private storage and pass it as Uri?
        //looks like double caching to me.
        //Ref : https://github.com/bumptech/glide/issues/627#issuecomment-146136474
        //Ref : http://stackoverflow.com/a/33283006/5367003
        supportPostponeEnterTransition();
        Bundle data = getIntent().getExtras();
        url = data.getString("URL");
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .listener(new RequestListener<Object, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressbar.setVisibility(View.INVISIBLE);
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(image);
        image.setOnTouchListener(new imageOnTouchListener());
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_close_clear_cancel));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void DownloadFailure() {
        progressbar.setVisibility(View.GONE);
        Snackbar.make(findViewById(android.R.id.content), R.string.save_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveMenuTapped();
                    }
                }).show();
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
    public void SaveMenuTapped() {
        if (PermissionUtils.requestPermission(this, SAVE_MEIZHI, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.i("Granted");
            mPresenter.SaveImage(url);
            progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_share:
                CommonUtil.Share(this, getString(R.string.share_meizhi_title), url, getString(R.string.share_meizhi_intent_chooser));
                break;
            case R.id.action_save:
                SaveMenuTapped();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Immediate Action When Permission Dialog Tapped
        if (PermissionUtils.permissionGranted(requestCode, SAVE_MEIZHI, grantResults)) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                SaveMenuTapped();
            else
            //Important Permission such as read external storage required a restart of Application to take effect
            Snackbar.make(findViewById(android.R.id.content)
                    , R.string.Permission_granted
                    , Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, null)
                    .show();
        } else {
            Toast.makeText(this, R.string.Permission_denie, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void ImageSaved() {
//        progressbar.setProgress(100);
        Snackbar.make(findViewById(android.R.id.content), R.string.image_saved, Snackbar.LENGTH_INDEFINITE)
                .show();

        progressbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressbar.setVisibility(View.GONE);
            }
        }, 500);
    }

    @Override
    public void updateProgressBar(float progress) {
        progressbar.setProgress(progress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP)
            overridePendingTransition(R.anim.pre_lolipop_enter,R.anim.pre_lolipop_exit);
    }

    private class imageOnTouchListener implements View.OnTouchListener {
        int StartX;
        int StartY;
        int mMotionX;
        int mMotionY;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    if (toolbar.getAlpha() == 1)
                        toolbar.animate().translationY(-toolbar.getMeasuredHeight())
                                .alpha(0)
                                .setDuration(100)
                                .setInterpolator(new LinearInterpolator());
                    Logger.i("Action Down");
                    StartX = (int) event.getRawX();
                    StartY = (int) event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    mMotionX = (int) event.getRawX() - StartX;
                    mMotionY = (int) event.getRawY() - StartY;
//                        layoutParams.leftMargin = mMotionX;
//                        layoutParams.rightMargin = -mMotionX;
                    layoutParams.topMargin = mMotionY / 2;
                    layoutParams.bottomMargin = -mMotionY / 2;
                    v.requestLayout();

                    if (Math.abs(mMotionY) > 200) {
                        frameMeizhi.getBackground().setAlpha(128);

                    } else {
                        //Alpha min 128 max 255
                        //255 - 128 = 127
                        double ratioAlpha = (Math.abs(mMotionY) / 200.0) * 127;
                        frameMeizhi.getBackground().setAlpha(255 - (int) ratioAlpha);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Logger.i("Motion Y: " + mMotionY);
                    if (Math.abs(mMotionY) > 100) {
                        onBackPressed();
                    } else {
                        //TODO Reuse Value Animator?
                        animate(v, layoutParams, 1);
                        animate(v, layoutParams, 2);
                        animate(v, layoutParams, 3);
                        animate(v, layoutParams, 4);

                        frameMeizhi.getBackground().setAlpha(255);
                        toolbar.animate().translationY(0)
                                .alpha(1)
                                .setDuration(100)
                                .setInterpolator(new LinearInterpolator());
                    }
                    break;
            }
            return true;
        }

        void animate(final View view, final FrameLayout.LayoutParams layoutParams, final int identifier) {
            int from = 0;
            switch (identifier) {
                case 1:
                    from = layoutParams.topMargin;
                    break;
                case 2:
                    from = layoutParams.bottomMargin;
                    break;
                case 3:
                    from = layoutParams.leftMargin;
                    break;
                case 4:
                    from = layoutParams.rightMargin;
                    break;
            }
            ValueAnimator valueAnimator = ValueAnimator.ofInt(from, 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    switch (identifier) {
                        case 1:
                            layoutParams.topMargin = (int) animation.getAnimatedValue();
                            break;
                        case 2:
                            layoutParams.bottomMargin = (int) animation.getAnimatedValue();
                            break;
                        case 3:
                            layoutParams.leftMargin = (int) animation.getAnimatedValue();
                            break;
                        case 4:
                            layoutParams.rightMargin = (int) animation.getAnimatedValue();
                            break;
                    }
                    view.requestLayout();
                }
            });
            valueAnimator.setDuration(200);
            valueAnimator.start();
        }
    }
}
