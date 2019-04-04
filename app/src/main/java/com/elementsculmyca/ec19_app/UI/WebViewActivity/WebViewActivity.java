package com.elementsculmyca.ec19_app.UI.WebViewActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.elementsculmyca.ec19_app.R;

public class WebViewActivity extends AppCompatActivity {
    WebView wb;
    ProgressBar pb;
    String name;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_web_view );
        wb = findViewById( R.id.webview );
        mProgress = new ProgressDialog( this );
        mProgress.setMessage( "Please Wait" );
        mProgress.setCanceledOnTouchOutside( false );
        wb.setWebViewClient( new WebViewClient() );
        wb.getSettings().setJavaScriptEnabled( true );
        name = getIntent().getStringExtra( "name" );
        if (name.equals( "hackon" ))
            wb.loadUrl( "http://hackon.elementsculmyca.com/" );
        else if (name.equals( "xunbao" ))
            wb.loadUrl( "http://xunbao.elementsculmyca.com" );
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            mProgress.show();
            super.onPageStarted( view, url, favicon );
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl( url );
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub
            mProgress.dismiss();
            super.onPageFinished( view, url );

        }

    }
}
