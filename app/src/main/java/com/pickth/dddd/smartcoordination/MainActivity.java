package com.pickth.dddd.smartcoordination;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TabHost tabHost;
    RecyclerView rvCalendar;
    ClothesAdapter mAdapter;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private final int GALLERY_CODE=1112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스마트코디");

        setTabHost();

        //옷장 탭
        mAdapter = new ClothesAdapter();
        rvCalendar = findViewById(R.id.rv_clothes);
        rvCalendar.setAdapter(mAdapter);
        rvCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        rvCalendar.addItemDecoration(new GridSpacingItemDecoration(this, 7, 3, false));


        //fab// https://medium.com/wasd/android-floating-action-button-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-6ca52aba7a1f
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);
                break;
            case R.id.fab2:
                anim();
                Intent intent1 = new Intent(getApplicationContext(), ClothAddActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}
