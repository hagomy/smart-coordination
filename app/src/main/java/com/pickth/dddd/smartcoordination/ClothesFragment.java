package com.pickth.dddd.smartcoordination;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ClothesFragment extends Fragment implements View.OnClickListener{
    RecyclerView rvCalendar;
    ClothesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private final int GALLERY_CODE=1112;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clothes,container,false);

        //옷장 탭

        rvCalendar = view.findViewById(R.id.rv_clothes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvCalendar.setLayoutManager(mLayoutManager);
        rvCalendar.scrollToPosition(0);
        mAdapter = new ClothesAdapter();
        rvCalendar.setAdapter(mAdapter);
        rvCalendar.setItemAnimator(new DefaultItemAnimator());

        //fab// https://medium.com/wasd/android-floating-action-button-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-6ca52aba7a1f
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        return view;
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
//                Intent intent1 = new Intent(getApplicationContext(), ClothAddActivity.class);
//                startActivity(intent1);
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