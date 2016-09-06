package com.mamacgroup.s5tv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Script;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by HP on 7/26/2016.
 */
public class TestFragment extends Fragment {
    ArrayList<News> newses_mini;
    ArrayList<News> newses;
    private static final String ARG_POSITION = "position";
    private static final String ARG_NAME = "name";
    private static final String ARG_POS = "pos";
    private static final String ARG_MSG = "msg";

    NewsListAdapter newsListAdapter;
    private int position;
    public String name,cat;
    ListView listView;
    ViewFlipper viewFlipper;
    TextView title,description,date,author;
    NetworkImageView imageView;
    ProgressBar progressBar;
    NetworkImageView header_news_image1,header_news_image2;
    TextView header_news_txt1,header_news_txt2;
    ScrollView scrollView;
    public static TestFragment newInstance(int position, String name, String pos, String msg) {
        TestFragment f = new TestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_NAME, name);
        f.setArguments(b);
        return f;
    }

    public void reload_data(int  pos,String msg){
        Log.e(String.valueOf(pos),String.valueOf(position));
        if(pos==position){
            get_news("&category="+msg);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_layout, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        View header_view = getActivity().getLayoutInflater().inflate(R.layout.listview_header,null);
        newses=new ArrayList<>();
        newses_mini = new ArrayList<>();
        name=(String)getArguments().getSerializable(ARG_NAME);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        TextView label = (TextView) view.findViewById(R.id.label);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        title = (TextView) view.findViewById(R.id.title_details);
        description = (TextView) view.findViewById(R.id.descr_details);
        imageView = (NetworkImageView) view.findViewById(R.id.img_details);
        label.setText("position:"+String.valueOf(position)+ "--->"+ "name:"+name);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.addHeaderView(header_view);
        header_news_image1 = (NetworkImageView) view.findViewById(R.id.header_news_image1);
        header_news_image2 = (NetworkImageView) view.findViewById(R.id.header_newsimage2);
        header_news_txt1 = (TextView) view.findViewById(R.id.header_news_title1);
        header_news_txt2 = (TextView) view.findViewById(R.id.header_news_title2);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        header_news_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
                title.setText(Html.fromHtml(newses_mini.get(0).getTitle()));
                description.setText(Html.fromHtml(newses_mini.get(0).getData()));

                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses_mini.get(0).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses_mini.get(0).image, imageLoader);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                });



            }
        });
        header_news_txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header_news_image1.performClick();
            }
        });

        header_news_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
                title.setText(Html.fromHtml(newses_mini.get(1).getTitle()));
                description.setText(Html.fromHtml(newses_mini.get(1).getData()));

                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses_mini.get(1).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses_mini.get(1).image, imageLoader);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                });


            }
        });

        header_news_txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header_news_image2.performClick();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    position --;
                    title.setText(Html.fromHtml(newses.get(position).getTitle()));
                    description.setText(Html.fromHtml(newses.get(position).getData()));
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(imageView,
                            R.drawable.logo, android.R.drawable
                                    .ic_dialog_alert));
                    imageView.setImageUrl(newses.get(position).image, imageLoader);

                    viewFlipper.setDisplayedChild(1);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_UP);
                        }
                    });


                }catch (Exception ex){

                }

            }
        });
        get_news("");
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
                }
                return false;
            }
        });


     }

    private void get_news(String append){
       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

       */
        progressBar.setVisibility(View.VISIBLE);
        String url;
        url = "http://clients.outlinedesigns.in/s5tv/api/news-json.php?type="+name.toLowerCase();
        url=url+append;
        Log.e("url", url);


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response", jsonObject.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                newses.clear();
                newses_mini.clear();
                    try {
                        for(int i=0;i<jsonObject.getJSONArray("categories").length();i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("categories").getJSONObject(i);
                            if(newses_mini.size()<2)
                                newses_mini.add(new News(jsonObject1,getActivity()));
                            else
                            newses.add(new News(jsonObject1,getActivity()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                newsListAdapter=new NewsListAdapter(getActivity(),newses);
                listView.setAdapter(newsListAdapter);
                if(newses_mini.size()>0){
                    header_news_txt1.setText(Html.fromHtml(newses_mini.get(0).getTitle()));
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses_mini.get(0).image, ImageLoader.getImageListener(header_news_image1,
                            R.drawable.logo, android.R.drawable
                                    .ic_dialog_alert));
                    header_news_image1.setImageUrl(newses_mini.get(0).image, imageLoader);

                }
                if(newses_mini.size()>1){
                    header_news_txt2.setText(Html.fromHtml(newses_mini.get(1).getTitle()));
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses_mini.get(1).image, ImageLoader.getImageListener(header_news_image2,
                            R.drawable.nwessss, android.R.drawable
                                    .ic_dialog_alert));
                    header_news_image2.setImageUrl(newses_mini.get(1).image, imageLoader);

                }
                newsListAdapter.notifyDataSetChanged();
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
