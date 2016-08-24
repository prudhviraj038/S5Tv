package com.mamacgroup.s5tv;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    private List<String> home_data;
    private List<String> news_data;
    ExpandListAdapter expandListAdapter;
    ArrayList<MainCategory> mainCategories;
    private DrawerLayout mDrawerLayout;
    TextView live_txt;
    ExpandableListView el;
    ImageView live_img,menu_btn;
    LinearLayout home,livetv,search,news,ap,telangana,sports,videos,hyd,cinema,adults,gallery,live_tv_ll_header;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainCategories = new ArrayList<>();
        el=(ExpandableListView)findViewById(R.id.expandableListView);
        el.setGroupIndicator(null);
        live_tv_ll_header = (LinearLayout)findViewById(R.id.livw_tv_ll);
        live_tv_ll_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                intent.putExtra("video", "PLQWG4VdLKw");
                startActivity(intent);
            }
        });
        live_txt = (TextView) findViewById(R.id.live_txtview);
        live_img =(ImageView) findViewById(R.id.live_imageview);
        live_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                intent.putExtra("video", "PLQWG4VdLKw");
                startActivity(intent);
            }
        });
        live_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                live_txt.performClick();
            }
        });
        home = (LinearLayout)findViewById(R.id.home_ll);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(0);
            }
        });
        search = (LinearLayout)findViewById(R.id.search_ll);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        livetv = (LinearLayout)findViewById(R.id.livetv_ll);
        livetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(mDrawerLayout.isDrawerOpen(GravityCompat.START));
                mDrawerLayout.closeDrawer(GravityCompat.START);*/
                Intent intent = new Intent(MainActivity.this,YoutubePlayer.class);
                intent.putExtra("video", "PLQWG4VdLKw");
                startActivity(intent);

            }
        });
        news = (LinearLayout)findViewById(R.id.news_ll);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(1);
            }
        });
        ap = (LinearLayout)findViewById(R.id.ap_ll);
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(3);
            }
        });
        telangana = (LinearLayout)findViewById(R.id.telangana_ll);
        telangana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(2);
            }
        });
        sports = (LinearLayout)findViewById(R.id.sports_ll);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(6);
            }
        });
        adults = (LinearLayout)findViewById(R.id.adults_ll);
        adults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(8);
            }
        });
        videos = (LinearLayout)findViewById(R.id.videos_ll);
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(4);
            }
        });
        hyd = (LinearLayout)findViewById(R.id.hyd_ll);
        hyd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(7);
            }
        });
        gallery = (LinearLayout)findViewById(R.id.gallery_ll);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(9);
            }
        });
        cinema = (LinearLayout)findViewById(R.id.cinema_ll);
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(5);
            }
        });
       mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        menu_btn = (ImageView) findViewById(R.id.menu_icon);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
         pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        get_all_categories();

    }

    ViewPager pager;
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

            if(TITLES[position].equals("Home"))
                return HomeFragment.newInstance(position,TITLES[position]);
           else if(TITLES[position].equals("Videos"))
                return VideoFragment.newInstance(position,TITLES[position]);
            else if(TITLES[position].equals("Gallery"))
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


    private void get_all_categories(){

      //  progressBar.setVisibility(View.VISIBLE);
        String url = "http://clients.outlinedesigns.in/s5tv/api/all-category-json.php";
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
              //  progressBar.setVisibility(View.GONE);
             //   galleryCategories.clear();
                try {
                    for(int i=0;i<jsonObject.length();i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        mainCategories.add(new MainCategory(jsonObject1));
                    }
                    expandListAdapter=new ExpandListAdapter(MainActivity.this,mainCategories);
                    el.setAdapter(expandListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
             //   galleryCatageoryAdapter = new GalleryCatageoryAdapter(getActivity(),galleryCategories);
             //   gridView.setAdapter(galleryCatageoryAdapter);
             //   galleryCatageoryAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error", volleyError.toString());
                //  progressBar.setVisibility(View.GONE);
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}
