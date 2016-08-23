package com.mamacgroup.s5tv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */
public class VideoFragment extends Fragment {
    ArrayList<VideoCategory> VideoCategories;
    ArrayList<VideoImage> VideoImages;
    private static final String ARG_POSITION = "position";
    private static final String ARG_NAME = "name";
    VideoCatageoryAdapter VideoCatageoryAdapter;
    VideoImageAdapter videoimageadapter;
    private int position;
    private String name;
    GridView gridView,gridView2;
    ViewFlipper viewFlipper ;
    NetworkImageView imageView;
    ProgressBar progressBar;
    TextView Video_title,Video_title2;
    private SliderLayout mDemoSlider;
    public static VideoFragment newInstance(int position,String name) {
        VideoFragment f = new VideoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_NAME, name);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_layout, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        VideoCategories=new ArrayList<>();
        VideoImages=new ArrayList<>();
        name=(String)getArguments().getSerializable(ARG_NAME);
        Log.e("name", name);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);


        imageView = (NetworkImageView) view.findViewById(R.id.img_Video_details);
        Video_title = (TextView) view.findViewById(R.id.Video_title);
        Video_title2 = (TextView) view.findViewById(R.id.Video_title2);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView2 = (GridView) view.findViewById(R.id.gridView2);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(1);

                get_Video_photos(VideoCategories.get(position).id);
                Video_title.setText(VideoCategories.get(position).title);
                Video_title2.setText(VideoCategories.get(position).title);

            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),YoutubePlayer.class);
                intent.putExtra("video", VideoImages.get(position).link);
                startActivity(intent);

                /*viewFlipper.setDisplayedChild(2);
                  ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(VideoImages.get(position).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(VideoImages.get(position).image, imageLoader);
                mDemoSlider.setCurrentPosition(position);
*/
            }
        });

        get_news();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }
                    else if (viewFlipper.getDisplayedChild() == 2) {
                        viewFlipper.setDisplayedChild(1);
                        return true;
                    }
                }
                return false;
            }
        });


     }
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.nwessss,R.drawable.nwessss,R.drawable.nwessss,R.drawable.nwessss};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private void get_news(){

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */

        progressBar.setVisibility(View.VISIBLE);
        String url = "http://clients.outlinedesigns.in/s5tv/api/category-json.php?type=videos";
        Log.e("url", url);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                VideoCategories.clear();
                    try {
                        for(int i=0;i<jsonObject.getJSONArray("categories").length();i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("categories").getJSONObject(i);
                            VideoCategories.add(new VideoCategory(jsonObject1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                VideoCatageoryAdapter = new VideoCatageoryAdapter(getActivity(),VideoCategories);
                gridView.setAdapter(VideoCatageoryAdapter);
                VideoCatageoryAdapter.notifyDataSetChanged();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.toString());
                progressBar.setVisibility(View.GONE);
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private void get_Video_photos(String id){

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */

        progressBar.setVisibility(View.VISIBLE);
        String url = "http://clients.outlinedesigns.in/s5tv/api/videos-json.php?parent_id="+id;
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                VideoImages.clear();
                try {
                    for(int i=0;i<jsonObject.length();i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        VideoImages.add(new VideoImage(jsonObject1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                videoimageadapter = new VideoImageAdapter(getActivity(), VideoImages);
                gridView2.setAdapter(videoimageadapter);
                videoimageadapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.toString());
                progressBar.setVisibility(View.GONE);
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }


}
