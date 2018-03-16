package com.example.balakrishnan.iot;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    boolean fl = false;
    boolean s1 = false, s2 = false, tf = false;
    TextView t1, t2, tv;
    HashMap<String, Integer> hm;
    String ID = "386123";

    public void jsonfn() {
        //System.out.println("tagger 123json fn1");
        HttpHandler sh = new HttpHandler();
        String url = "https://api.thingspeak.com/channels/" + ID + "/feeds.json?results=2";

        String jsonStr = sh.makeServiceCall(url);


        if (jsonStr != null) {
            System.out.println("tagger json fn");
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject channel = jsonObj.getJSONObject("channel");
                Iterator<?> keys = channel.keys();
                int i = 1;
                System.out.println("tagger 2 json fn");
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.startsWith("field")) {
                        System.out.println("tagger" + channel.get(key).toString() + "  " + i);
                        hm.put(channel.get(key).toString(), i++);
                    }
                }

            } catch (Exception e) {
                System.out.println("tagger " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        temp = (TextView)findViewById(R.id.temp);
        hm = new HashMap<>();

        new BackgroundJson().execute();
        //jsonfn();
/*
        hm.put("temperature", 1);
        hm.put("humidity", 2);
        hm.put("sensor1", 3);
        hm.put("sensor2", 4);

*/
        final Animation tv1Open, tv1Close, tv2Open, tv2Close, tv3Open, tv3Close, imgOpen, imgClose;
        tv1Open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        tv1Close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        tv2Open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        tv2Close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        tv3Open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        tv3Close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        imgOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        imgClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);


        ImageView powerView = (ImageView) findViewById(R.id.powerButton);
        powerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) findViewById(R.id.onOff);
                GradientDrawable bgShape = (GradientDrawable) btn.getBackground();
                if (!fl) {
                    btn.startAnimation(imgOpen);
                    bgShape.setColor(Color.RED);
                    fl = true;
                } else {
                    btn.startAnimation(imgOpen);
                    bgShape.setColor(Color.GREEN);
                    fl = false;
                }
            }
        });

        t1 = (TextView) findViewById(R.id.sensor1View);
        Button sensor1 = (Button) findViewById(R.id.sensor1Button);
        sensor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!s1) {
                    t1.startAnimation(tv1Open);
                    t1.setVisibility(View.VISIBLE);
                    t1.setClickable(true);
                    s1 = true;
                } else {
                    t1.startAnimation(tv1Close);
                    t1.setVisibility(View.INVISIBLE);
                    t1.setClickable(false);
                    s1 = false;
                }
            }
        });
        t2 = (TextView) findViewById(R.id.sensor2View);
        Button sensor2 = (Button) findViewById(R.id.sensor2Button);
        sensor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!s2) {
                    t2.startAnimation(tv2Open);
                    t2.setVisibility(View.VISIBLE);
                    t2.setClickable(true);
                    s2 = true;
                } else {
                    t2.startAnimation(tv2Close);
                    t2.setVisibility(View.INVISIBLE);
                    t2.setClickable(false);
                    s2 = false;
                }
            }
        });
        tv = (TextView) findViewById(R.id.tempView);
        Button t = (Button) findViewById(R.id.tempButton);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tf) {
                    tv.startAnimation(tv3Open);
                    tv.setVisibility(View.VISIBLE);
                    tv.setClickable(true);
                    tf = true;
                } else {
                    tv.startAnimation(tv3Close);
                    tv.setVisibility(View.INVISIBLE);
                    tv.setClickable(false);
                    tf = false;
                }
            }
        });
        final Intent i = new Intent(MainActivity.this, DataActivity.class);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Index", hm.get("Humidity").toString());
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Index", hm.get("sensor2").toString());
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Index", hm.get("Temperature").toString());
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });
    }

    private class BackgroundJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            jsonfn();
            updateTemp();
            /*
            */return null;
        }
    }
    TextView temp;
    String jsonStr;
    public void updateTemp()
    {
        HttpHandler sh = new HttpHandler();
        String url = "https://api.thingspeak.com/channels/"+ID+"/fields/1/last.txt";

       jsonStr = sh.makeServiceCall(url);
        System.out.println("tagger "+jsonStr);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                temp.setText(jsonStr.trim()+" \'C");
            }
        });


        Timer timer = new Timer();
        TimerTask timerTask;
        timerTask = new TimerTask() {
            @Override
            public void run() {
               updateTemp();
            }
        };
        timer.schedule(timerTask, 5000);


    }

}