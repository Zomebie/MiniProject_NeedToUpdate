package com.example.zomebie.miniproject01;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    PagerAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
                                          {
                                              @Override
                                              public void onPageScrolled(int i, float v, int i1) {

                                              }

                                              @Override
                                              public void onPageSelected(int i) {

                                                  viewPager.getAdapter().notifyDataSetChanged();

                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int i) {

                                              }
                                          }
        );


    }


}