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
    ArrayList<String> mainCategories_menu;

    private DrawerLayout mDrawerLayout;
    TextView live_txt;
    ExpandableListView el;
    ImageView live_img,menu_btn;
    LinearLayout home,livetv,search,news,ap,telangana,sports,videos,hyd,cinema,adults,gallery,live_tv_ll_header;
    String pos,msg;
    HomeFragment homeFragment;
    TestFragment testFragment;
    HashMap<Integer,TestFragment> fragments;
    ImageView header_logo;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pos="0";
        msg="";
        mainCategories = new ArrayList<>();
        header_logo = (ImageView) findViewById(R.id.header_logo);
        header_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
            }
        });
        fragments = new HashMap<>();
        el=(ExpandableListView)findViewById(R.id.expandableListView);
        el.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                fragments.get(groupPosition+1).reload_data(groupPosition+1,mainCategories.get(groupPosition).chanels.get(childPosition).ch_id);
                pager.setCurrentItem(groupPosition+1);
                mDrawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        el.setGroupIndicator(null);
        mainCategories = (ArrayList<MainCategory>)getIntent().getSerializableExtra("data");
        mainCategories_menu=new ArrayList<>();
        mainCategories_menu.add("Home");
        for(int i=0;i<mainCategories.size();i++){
            mainCategories_menu.add(mainCategories.get(i).title);
            fragments.put(i+1,TestFragment.newInstance(i+1,mainCategories.get(i).type,"0",""));
        }
        expandListAdapter=new ExpandListAdapter(MainActivity.this,mainCategories);
        el.setAdapter(expandListAdapter);

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
                    homeFragment.reload_data();


            }
        });
        search = (LinearLayout)findViewById(R.id.search_ll);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                pager.setCurrentItem(0);

                    homeFragment.reload_search();


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
        myPagerAdapter =new  MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myPagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
       // get_all_categories();

    }

    ViewPager pager;
    MyPagerAdapter myPagerAdapter;
    private class MyPagerAdapter extends  FragmentPagerAdapter{
        //ap,adults, cinema , telangana , sports ,  video , gallery  , hyd


        /*private final String[] TITLES = {
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
        };*/

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mainCategories_menu.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public Fragment getItem(int position) {

            if(mainCategories_menu.get(position).equals("Home")) {
                homeFragment = HomeFragment.newInstance(position, mainCategories_menu.get(position), pos, msg);
                return homeFragment;

            }
           else if(mainCategories_menu.get(position).equals("Videos")) {
                return VideoFragment.newInstance(position, mainCategories_menu.get(position), pos, msg);
            }
            else if(mainCategories_menu.get(position).equals("Gallery")) {
                return GalleryFragment.newInstance(position, mainCategories_menu.get(position), pos, msg);
            }
            else {
                return fragments.get(position);
            }
        }

        @Override
        public int getCount() {
            return mainCategories_menu.size();
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
