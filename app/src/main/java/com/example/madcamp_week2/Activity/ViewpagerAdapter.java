package com.example.madcamp_week2.Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.madcamp_week2.Frag1.Fragment1;
import com.example.madcamp_week2.Frag2.Fragment2;
import com.example.madcamp_week2.Frag3.Fragment3;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new Fragment2());
        items.add(new Fragment3());

        itext.add("contact");
        itext.add("gallery");
        itext.add("health");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
