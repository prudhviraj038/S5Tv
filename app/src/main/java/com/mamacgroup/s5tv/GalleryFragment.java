package com.mamacgroup.s5tv;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */
public class GalleryFragment extends Fragment {
    ArrayList<GalleryCategory> galleryCategories;
    ArrayList<GalleryImage> galleryImages;
    private static final String ARG_POSITION = "position";
    private static final String ARG_NAME = "name";
    GalleryCatageoryAdapter galleryCatageoryAdapter;
    GalleryImageAdapter galleryImageAdapter;
    private int position;
    private String name;
    GridView gridView,gridView2;
    ViewFlipper viewFlipper ;
    NetworkImageView imageView;
    ProgressBar progressBar;
    TextView gallery_title,gallery_title2;
    private SliderLayout mDemoSlider;
    public static GalleryFragment newInstance(int position,String name) {
        GalleryFragment f = new GalleryFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_NAME, name);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_layout, container, false);
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
        galleryCategories=new ArrayList<>();
        galleryImages=new ArrayList<>();
        name=(String)getArguments().getSerializable(ARG_NAME);
        Log.e("name", name);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);


        imageView = (NetworkImageView) view.findViewById(R.id.img_gallery_details);
        gallery_title = (TextView) view.findViewById(R.id.gallery_title);
        gallery_title2 = (TextView) view.findViewById(R.id.gallery_title2);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView2 = (GridView) view.findViewById(R.id.gridView2);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(1);

                get_gallery_photos(galleryCategories.get(position).id);
                gallery_title.setText(galleryCategories.get(position).title);
                gallery_title2.setText(galleryCategories.get(position).title);

            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(2);
                  ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(galleryImages.get(position).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(galleryImages.get(position).image, imageLoader);
                mDemoSlider.setCurrentPosition(position);

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
        String url = "http://clients.outlinedesigns.in/s5tv/api/category-json.php?type=gallery";
        Log.e("url", url);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                galleryCategories.clear();
                    try {
                        for(int i=0;i<jsonObject.getJSONArray("categories").length();i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("categories").getJSONObject(i);
                            galleryCategories.add(new GalleryCategory(jsonObject1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                galleryCatageoryAdapter = new GalleryCatageoryAdapter(getActivity(),galleryCategories);
                gridView.setAdapter(galleryCatageoryAdapter);
                galleryCatageoryAdapter.notifyDataSetChanged();
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

    private void get_gallery_photos(String id){

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */

        progressBar.setVisibility(View.VISIBLE);
        String url = "http://clients.outlinedesigns.in/s5tv/api/gallery-json.php?category="+id;
        Log.e("url", url);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                galleryImages.clear();
                try {
                    for(int i=0;i<jsonObject.getJSONArray("gallery").length();i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONArray("gallery").getJSONObject(i);
                        galleryImages.add(new GalleryImage(jsonObject1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                galleryImageAdapter = new GalleryImageAdapter(getActivity(), galleryImages);
                gridView2.setAdapter(galleryImageAdapter);
                galleryImageAdapter.notifyDataSetChanged();
                for (int i = 0; i < galleryImages.size(); i++) {
                    DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
                   // TextSliderView textSliderView = new TextSliderView(getActivity());
                    // initialize a SliderLayout
                    defaultSliderView
                            .description(String.valueOf(Html.fromHtml(galleryImages.get(i).image)))
                            .image(galleryImages.get(i).image).setScaleType(BaseSliderView.ScaleType.CenterCrop);
                    mDemoSlider.addSlider(defaultSliderView);


                }

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
