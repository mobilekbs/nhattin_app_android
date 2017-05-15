package vn.ntlogistics.app.shipper.Views.Activities;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.databinding.ActivityWebViewBinding;


public class WebViewActivity extends BaseActivity {
    private ActivityWebViewBinding binding;

    /*WebView webView;
    Toolbar toolbar;
    AppCompatTextView title_toolbar;*/
    String title, URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        //setContentView(R.layout.activity_web_view);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        init();
    }

    private void init() {
        /*webView = (WebView) findViewById(webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_toolbar = (AppCompatTextView) findViewById(R.id.title_toolbar);*/
        setupWebView();
        setupToolbar();
    }

    private void setupWebView() {
        Bundle b = getIntent().getExtras();
        try {
            title = b.get("title").toString();
            URL = b.get("URL").toString();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.pbWebView.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.pbWebView.hide();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                binding.pbWebView.hide();
            }
        });
        //binding.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            binding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            binding.webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        binding.webView.loadUrl(URL);
    }

    void setupToolbar(){
        setSupportActionBar(binding.toolbar);
        binding.titleToolbar.setText(title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
