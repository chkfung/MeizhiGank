package me.chkfung.meizhigank.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import me.chkfung.meizhigank.Model.Day;

/**
 * Created by Fung on 16/08/2016.
 */

public class CommonUtil {

    public static Bitmap bitmap;

    public static List<Day.ResultsBean.DataBean> getDataOf(Day data, String selectedItem) {
        switch (selectedItem) {
            case "Android":
                return data.getResults().getAndroid();
            case "iOS":
                return data.getResults().getIOS();
            case "App":
                return data.getResults().getApp();
            case "休息视频":
                return data.getResults().getRestVideo();
            case "拓展资源":
                return data.getResults().getExtra();
            case "瞎推荐":
                return data.getResults().getRecommend();
            case "前端":
                return data.getResults().getFrontEnd();
            default:
                return null;
        }
    }

    public static void ShareImage(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent SharingIntent = new Intent();
        SharingIntent.setAction(Intent.ACTION_SEND);

        try {
            context.startActivity(Intent.createChooser(SharingIntent, "Share Image"));
        } catch (Exception e) {

        }
    }

    public static void ScreenshotAlpha(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        final WindowManager mWindowManager = (WindowManager) view.getContext().getApplicationContext().getSystemService(view.getContext().getApplicationContext().WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        final FrameLayout OverlayWrapper = new FrameLayout(view.getContext());

        ImageView Overlay = new ImageView(view.getContext());
        Overlay.setImageBitmap(bitmap);

        OverlayWrapper.addView(Overlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWindowManager.addView(OverlayWrapper, wmParams);
        Animation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(5000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mWindowManager.removeViewImmediate(OverlayWrapper);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Overlay.animate().translationY(100).setInterpolator(new AccelerateInterpolator()).start();
//        OverlayWrapper.startAnimation(animation);
//
//        FrameLayout frameLayout = (FrameLayout) view;
//        frameLayout.addView(Overlay);
//
//        Overlay.startAnimation(animation);

    }

    public static Bitmap getScreenShow(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
//        ImageView Overlay = new ImageView(view.getContext());
//        Overlay.setImageBitmap(bitmap);
//
//        Animation animation = new AlphaAnimation(1f,0.5f);
//        animation.setDuration(5000);
//
//        FrameLayout frameLayout = (FrameLayout) view;
//        frameLayout.addView(Overlay);
//
//        Overlay.startAnimation(animation);

    }

    public static void StartAnim(View view) {

        ImageView Overlay = new ImageView(view.getContext());
        Overlay.setImageBitmap(bitmap);

        final FrameLayout frameLayout = (FrameLayout) view;
        frameLayout.addView(Overlay);

        Animation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(5000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                frameLayout.removeViewAt(frameLayout.getChildCount() - 1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Overlay.startAnimation(animation);
    }
}
