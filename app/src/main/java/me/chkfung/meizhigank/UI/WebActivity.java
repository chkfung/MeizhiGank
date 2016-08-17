package me.chkfung.meizhigank.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 15/08/2016.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.webview)
    WebView webview;

    public static Intent newIntent(Context context, String Title, String Url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("EXTRA_TITLE", Title);
        intent.putExtra("EXTRA_URL", Url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        String Title = getIntent().getExtras().getString("EXTRA_TITLE");
        String Url = getIntent().getExtras().getString("EXTRA_URL");

        webview.loadUrl(Url);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Title);

//        getSupportActionBar().setSubtitle(Url);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           super.onProgressChanged(view, newProgress);
                                           progressbar.setProgress(newProgress);
                                           if (newProgress >= 100)
                                               progressbar.setVisibility(View.GONE);
                                           else
                                               progressbar.setVisibility(View.VISIBLE);
                                       }
                                   }
        );

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_refresh:
                webview.reload();
                break;
            case R.id.action_copy_link:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Url", webview.getUrl());
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(findViewById(android.R.id.content), "Url Copied", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_open_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webview.getUrl()));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
