package com.pickth.dddd.smartcoordination;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TabHost tabHost;
    RecyclerView rvCalendar;
    ClothesAdapter mAdapter;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private final int GALLERY_CODE=1112;

    ImageView imageView; //갤러리에서 선택한 이미지를 보여주기 위한 것
    Button albumbtn; //xml에서 btn_album과 연결하기 위한 것

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


        //룩북 탭
        imageView = (ImageView)findViewById(R.id.iv_view);

        albumbtn = (Button) findViewById(R.id.btn_album);
        albumbtn.setOnClickListener(new View.OnClickListener() { //갤러리 버튼 클릭 시 갤러리로 접근
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(); //이미지 불러오기
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); //갤러리에서 여러 이미지를 선택
                startActivityForResult(intent, 1); //시작할 액티비티를 통해 어떠한 값을 받을 것을 기대하고 액티비티를 시작
            }
        });

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
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) { //startActivityForResult를 실행했을 때, 받기를 기대했던 어떠한 값 동작을 정의하는 메소드
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}