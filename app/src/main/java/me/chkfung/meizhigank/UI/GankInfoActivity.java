package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.GankInfoRvAdapter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gankinfo);
        ButterKnife.bind(this);
        ArrayList<Day.ResultsBean.DataBean> mData;
        mData = getIntent().getParcelableArrayListExtra("Data");
        category.setText(mData.get(0).getType());

        rvGankinfo.setAdapter(new GankInfoRvAdapter(mData));
        rvGankinfo.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.close_button})
    void Close() {
        this.onBackPressed();
    }
}
