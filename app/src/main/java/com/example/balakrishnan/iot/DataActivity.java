package com.example.balakrishnan.iot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        String index=extras.getString("Index");
        String id = extras.getString("ID");
        String data = index+" data"+" ID:"+id;
        WebView wv = (WebView)findViewById(R.id.webview);
        String url="https://thingspeak.com/channels/"+id+"/charts/"+index+"?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15";
        System.out.println("tagger "+url);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setMinimumFontSize(10);
        wv.setInitialScale(150);
        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv.loadUrl(url);



        //TextView tv = (TextView)findViewById(R.id.tv);
        //tv.setText(data);
    }
}
