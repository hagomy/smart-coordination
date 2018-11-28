package com.pickth.dddd.smartcoordination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스마트코디");

        setTabHost();
    }
    public void setTabHost() {

        tabHost = (TabHost)findViewById(R.id.host);
        tabHost.setup();

        TabHost.TabSpec clothes = tabHost.newTabSpec("A");
        clothes.setIndicator("옷장");
        clothes.setContent(R.id.clothes);
        tabHost.addTab(clothes);

        TabHost.TabSpec lookbook = tabHost.newTabSpec("B");
        lookbook.setIndicator("룩북");
        lookbook.setContent(R.id.lookbook);
        tabHost.addTab(lookbook);


        TabHost.TabSpec history = tabHost.newTabSpec("C");
        history.setIndicator("히스토리");
        history.setContent(R.id.history);
        tabHost.addTab(history);


        TabHost.TabSpec laundry = tabHost.newTabSpec("D");
        laundry.setIndicator("빨래통");
        laundry.setContent(R.id.laundry);
        tabHost.addTab(laundry);
    }
}
