package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 09/08/2016.
 */

public class GankInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gankinfo);
    }

    @OnClick({R.id.close_button})
    void Close() {
        this.onBackPressed();
    }
}
