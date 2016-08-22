package me.chkfung.meizhigank.Base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 21/07/2016.
 */

public class BaseActivity extends AppCompatActivity implements BaseContract.View {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void SnackBarResult(String desc) {
        Snackbar.make(findViewById(android.R.id.content), desc, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void ToastResult(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
