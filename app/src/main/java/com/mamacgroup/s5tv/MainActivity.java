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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity {
    ImageView menu_btn;
    LinearLayout home,livetv,search,news,telangana,ap,adults,videos,sports,hyd,cinema,gallary;
    private DrawerLayout mDrawerLayout;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu_btn = (ImageView)findViewById(R.id.menu_icon);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        home = (LinearLayout)findViewById(R.id.home_ll);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        livetv = (LinearLayout)findViewById(R.id.livetv_ll);
        livetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search = (LinearLayout)findViewById(R.id.search_ll);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        news = (LinearLayout)findViewById(R.id.news_ll);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
       telangana = (LinearLayout)findViewById(R.id.telangana_ll);
        telangana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ap = (LinearLayout)findViewById(R.id.ap_ll);
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        videos = (LinearLayout)findViewById(R.id.videos_ll);
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sports = (LinearLayout)findViewById(R.id.sports_ll);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        adults = (LinearLayout)findViewById(R.id.adults_ll);
        adults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hyd = (LinearLayout)findViewById(R.id.hyd_ll);
        hyd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        gallary = (LinearLayout)findViewById(R.id.gallery_ll);
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cinema = (LinearLayout)findViewById(R.id.cinema_ll);
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });







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
