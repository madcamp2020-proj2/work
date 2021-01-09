package com.example.madcamp_week2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.madcamp_week2.Activity.ViewpagerAdapter;
import com.example.madcamp_week2.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tabLayout);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.contact);
        images.add(R.drawable.photos);
        images.add(R.drawable.health);

        for(int i=0;i<3;i++) tab.getTabAt(i).setIcon(images.get(i));
    }
}