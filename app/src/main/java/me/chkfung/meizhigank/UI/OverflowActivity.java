package me.chkfung.meizhigank.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Util.CommonUtil;

/**
 * Created by Fung on 18/08/2016.
 */

public class OverflowActivity extends BaseActivity {

    public static final String EXTRA_BITMAP = "bitmap";

    static Intent newIntent(Context context, Bitmap bitmap) {
        Intent i = new Intent(context, OverflowActivity.class);
        i.putExtra(EXTRA_BITMAP, bitmap);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra(EXTRA_BITMAP);
        FrameLayout frameLayout = (FrameLayout) findViewById(android.R.id.content);
        ImageView Overlay = new ImageView(this);
//        Overlay.setImageBitmap(bitmap);
        Overlay.setImageBitmap(CommonUtil.bitmap);
        frameLayout.addView(Overlay);
//        BitmapFactory.
    }
}
