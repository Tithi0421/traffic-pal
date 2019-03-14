package com.afinal.android.trafficpal1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import github.chenupt.springindicator.SpringIndicator;

public class ViewPagerActivity extends AppCompatActivity {

    private SmartFragmentStatePagerAdapter adapterViewPager;
    public static NonSwipeableViewPager vpPager;
    public static MyPagerAdapter myPagerAdapter;
    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter  {
        private static int NUM_ITEMS = 3;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(1, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return SecondFragment.newInstance(2, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ThirdFragment.newInstance(3, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {return "" + (position + 1);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        vpPager = (NonSwipeableViewPager) findViewById(R.id.vpPager);
        vpPager.setBackgroundColor(Color.WHITE);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        SpringIndicator springIndicator= (SpringIndicator)findViewById(R.id.indicator);
        springIndicator.setViewPager(vpPager);
        springIndicator.setOnClickListener(null);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);

    }

    public void setCurrentItem (boolean smoothScroll) {
        vpPager.setCurrentItem(vpPager.getCurrentItem()+1, smoothScroll);
    }
    public void setCurrentItem1 (boolean smoothScroll) {
        vpPager.setCurrentItem(vpPager.getCurrentItem()-1, smoothScroll);
    }



}
