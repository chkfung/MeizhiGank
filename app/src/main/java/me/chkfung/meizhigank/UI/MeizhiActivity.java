package me.chkfung.meizhigank.UI;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Contract.Presenter.MeizhiPresenter;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.PermissionUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Fung on 25/07/2016.
 */

public class MeizhiActivity extends BaseActivity implements MeizhiContract.View {
    private static final int SAVE_MEIZHI = 1;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    PhotoViewAttacher photoViewAttacher;
    private MeizhiContract.Presenter mPresenter = new MeizhiPresenter();
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        //TODO there should be better way
        //To Prevent Image Flicker 2 times when glide load success and previous image transition,
        //Save Image in private storage and pass it as Uri?
        //looks like double caching to me.
        //Ref : https://github.com/bumptech/glide/issues/627#issuecomment-146136474
        //Ref : http://stackoverflow.com/a/33283006/5367003
        supportPostponeEnterTransition();
        Bundle data = getIntent().getExtras();
        url = data.getString("URL");
        progressbar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
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
                        supportStartPostponedEnterTransition();

//                        photoViewAttacher = new PhotoViewAttacher(image);
//                        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//                            @Override
//                            public void onViewTap(View view, float v, float v1) {
//                                onBackPressed();
//                            }
//                        });
//                        photoViewAttacher.update();
                        return false;
                    }
                })
                .into(image);
        image.setOnTouchListener(new View.OnTouchListener() {
            int StartX;
            int StartY;

            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                //YAY
                switch (event.getActionMasked()) {
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        Logger.i("Pointer Down");
//                        break;
                    case MotionEvent.ACTION_DOWN:
                        Logger.i("Action Down");
                        StartX = (int) event.getRawX();
                        StartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int mMotionX = (int) event.getRawX() - StartX;
                        int mMotionY = (int) event.getRawY() - StartY;
                        layoutParams.leftMargin = mMotionX;
                        layoutParams.rightMargin = -mMotionX;
                        layoutParams.topMargin = mMotionY;
                        layoutParams.bottomMargin = -mMotionY;
                        v.requestLayout();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //TODO Reduce the amount of repeat code
                        final ValueAnimator valueAnimator1 = ValueAnimator.ofInt(layoutParams.topMargin, 0);
                        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, 0);
                        final ValueAnimator valueAnimator2 = ValueAnimator.ofInt(layoutParams.bottomMargin, 0);
                        final ValueAnimator valueAnimator3 = ValueAnimator.ofInt(layoutParams.leftMargin, 0);
                        final ValueAnimator valueAnimator4 = ValueAnimator.ofInt(layoutParams.rightMargin, 0);

                        ValueAnimator.AnimatorUpdateListener animatorUpdateListener1 = new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                layoutParams.topMargin = (int) valueAnimator1.getAnimatedValue();
                                v.requestLayout();
                            }
                        };
                        ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                layoutParams.bottomMargin = (int) valueAnimator2.getAnimatedValue();
                                v.requestLayout();
                            }
                        };
                        ValueAnimator.AnimatorUpdateListener animatorUpdateListener3 = new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                layoutParams.leftMargin = (int) valueAnimator3.getAnimatedValue();
                                v.requestLayout();
                            }
                        };
                        ValueAnimator.AnimatorUpdateListener animatorUpdateListener4 = new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                layoutParams.rightMargin = (int) valueAnimator4.getAnimatedValue();
                                v.requestLayout();
                            }
                        };
                        valueAnimator1.addUpdateListener(animatorUpdateListener1);
                        valueAnimator2.addUpdateListener(animatorUpdateListener2);
                        valueAnimator3.addUpdateListener(animatorUpdateListener3);
                        valueAnimator4.addUpdateListener(animatorUpdateListener4);
                        valueAnimator1.setDuration(1000);
                        valueAnimator2.setDuration(1000);
                        valueAnimator3.setDuration(1000);
                        valueAnimator4.setDuration(1000);
                        valueAnimator1.start();
                        valueAnimator2.start();
                        valueAnimator3.start();
                        valueAnimator4.start();

//                        Animation animation =
//                                new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
//                                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
//                        animation.setDuration(android.R.integer.config_shortAnimTime);
//                        animation.setDuration(1000);
//                        animation.setInterpolator(new LinearInterpolator());
//
////                        animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
//                        animationSet.addAnimation(animation);
//                        animationSet.setFillAfter(true);
//                        animationSet.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                layoutParams.topMargin = 0;
//                                layoutParams.bottomMargin = 0;
//                                layoutParams.leftMargin = 0;
//                                layoutParams.rightMargin = 0;
//
//                                v.requestLayout();
////                                v.clearAnimation();
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
//                        v.setVisibility(View.VISIBLE);
//                        v.startAnimation(animationSet);
                        break;
                }

                return true;
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void DownloadFailure() {
        Snackbar.make(findViewById(android.R.id.content), "Save Failed", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
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
        if (PermissionUtils.requestPermission(this, SAVE_MEIZHI, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.i("Granted");
            mPresenter.SaveImage(url);
        }
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
            mPresenter.SaveImage(url);
        } else {
            ToastResult("Permission Denied");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();

    }
}
