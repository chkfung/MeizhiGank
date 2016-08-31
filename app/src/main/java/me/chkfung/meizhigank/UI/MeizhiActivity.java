package me.chkfung.meizhigank.UI;

import android.Manifest;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import uk.co.senab.photoview.PhotoViewAttacher;

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
    ProgressBar progressbar;

    PhotoViewAttacher photoViewAttacher;
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

//                        photoViewAttacher = new PhotoViewAttacher(image);
//                        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//                            @Override
//                            public void onViewTap(View view, float v, float v1) {
//                                onBackPressed();
//                            }
//                        });
//                        photoViewAttacher.update();
//                        Bitmap mBitmap =(Bitmap) resource;
//
//                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
//                                mBitmap, "Image Description", null);
//
//                        Uri uri = Uri.parse(path);
//                        Intent shareIntent = new Intent();
//                        shareIntent.setAction(Intent.ACTION_SEND);
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                        shareIntent.setType("image/*");
//                        miShareAction.setShareIntent(shareIntent);
                        return false;
                    }
                })
                .into(image);
        image.setOnTouchListener(new imageOnTouchListener());
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
        if (PermissionUtils.requestPermission(this, SAVE_MEIZHI, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.i("Granted");
            mPresenter.SaveImage(url);
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_share:
                CommonUtil.ShareImage(this, url);
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
            //Important Permission such as read external storage required a restart of Application to take effect
            Snackbar.make(findViewById(android.R.id.content)
                    , "Permission Granted! Image Saving will be taking effect on next Restart"
                    , Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", null)
                    .show();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
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
        progressbar.setProgress(100);
        progressbar.setVisibility(View.GONE);
        Snackbar.make(findViewById(android.R.id.content), "Image Saved", Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void updateProgressBar(int progress) {
        progressbar.setProgress(progress);
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
