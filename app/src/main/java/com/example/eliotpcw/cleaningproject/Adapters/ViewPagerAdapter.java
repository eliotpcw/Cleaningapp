package com.example.eliotpcw.cleaningproject.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliotpcw on 05.04.2018.
 */

    public class ViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void AddFragments(Fragment fragment){
        fragments.add(fragment);
    }
}
