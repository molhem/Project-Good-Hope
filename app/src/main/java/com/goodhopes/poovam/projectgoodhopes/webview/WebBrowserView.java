package com.goodhopes.poovam.projectgoodhopes.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.goodhopes.poovam.projectgoodhopes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 6/12/16.
 * An activity to act as a web browser to read the contents
 */

public class WebBrowserView extends AppCompatActivity{
    @BindView(R.id.web_browser) WebView webview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_browser_view);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webview.setWebViewClient(new WebViewClient());
        String URL = getIntent().getStringExtra("contentURL");
        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);

        webview.loadUrl(URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default: onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
