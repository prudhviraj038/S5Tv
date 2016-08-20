package com.mamacgroup.s5tv;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    LinearLayout live;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       try {
           Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
           window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
           window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
           window.setStatusBarColor(getResources().getColor(R.color.aa_app_red));
       }catch (Exception e){}
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        live=(LinearLayout)findViewById(R.id.livw_tv_ll);
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                intent.putExtra("video","PLQWG4VdLKw");
                startActivity(intent);
            }
        });
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
