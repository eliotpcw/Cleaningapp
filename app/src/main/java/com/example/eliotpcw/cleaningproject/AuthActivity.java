package com.example.eliotpcw.cleaningproject;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eliotpcw.cleaningproject.Adapters.ViewPagerAdapter;
import com.example.eliotpcw.cleaningproject.Fragments.signin;
import com.example.eliotpcw.cleaningproject.Fragments.signup;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragments(new signin());
        viewPagerAdapter.AddFragments(new signup());


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
