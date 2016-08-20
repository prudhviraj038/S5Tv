package com.mamacgroup.s5tv;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

    }


    private class MyPagerAdapter extends  FragmentPagerAdapter{
        //ap,adults, cinema , telangana , sports ,  video , gallery  , hyd
        private final String[] TITLES = {
                "Home",
                "News",
                "Telangana",
                "AP",
                "Videos",
                "Cinema",
                "Sports",
                "HYD",
                "Adults",
                "Gallery"
        };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(TITLES[position].equals("Gallery"))
                return GalleryFragment.newInstance(position,TITLES[position]);
            else
                return TestFragment.newInstance(position,TITLES[position]);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
