package com.thea.demo.hybrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String URL = "http://www.baidu.com";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWebView = (WebView) findViewById(R.id.wv_test);
        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setUserAgentString();
        //启用方法互调
        webSettings.setJavaScriptEnabled(true);
        //启用H5本地存储
        webSettings.setDomStorageEnabled(true);
        //启用H5本地数据库
        webSettings.setDatabaseEnabled(true);
        //启用H5地理定位
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(getApplicationContext().getDir(
                "database", MODE_PRIVATE).getPath());
        //开启应用程序缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getApplicationContext().getDir("cache", MODE_PRIVATE).getPath());
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.addJavascriptInterface(new JavaScriptInterface(), "app");
        //在APP里显示网页，不跳转到默认浏览器
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl(URL);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i(TAG, consoleMessage.message() + " -- From Line " + consoleMessage.lineNumber() +
                " of " + consoleMessage.sourceId());
            return true;
        }
    }
}
