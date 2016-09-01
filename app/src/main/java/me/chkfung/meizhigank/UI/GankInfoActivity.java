package me.chkfung.meizhigank.ui;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.ui.Adapter.GankInfoRvAdapter;

/**
 * Created by Fung on 09/08/2016.
 */

public class GankInfoActivity extends BaseActivity {
    @BindView(R.id.headerImage)
    ImageView headerImage;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.rv_gankinfo)
    RecyclerView rvGankinfo;
    @BindView(R.id.revealTest)
    LinearLayout revealTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gankinfo);
        ArrayList<DataInfo> mData;
        mData = getIntent().getParcelableArrayListExtra("Data");
        category.setText(mData.get(0).getType());

        rvGankinfo.setAdapter(new GankInfoRvAdapter(mData));
        rvGankinfo.setLayoutManager(new LinearLayoutManager(this));
        revealTest.post(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT > 21) {
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(revealTest, revealTest.getWidth() / 2, 0, 0, revealTest.getWidth());
                    circularReveal.setDuration(500);
                    circularReveal.setInterpolator(new AccelerateInterpolator(1.5f));
                    circularReveal.start();
                }
            }
        });
    }

    @OnClick({R.id.close_button})
    void Close() {
        this.onBackPressed();
    }
}
