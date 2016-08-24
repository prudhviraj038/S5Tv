package com.mamacgroup.s5tv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */
public class HomeFragment extends Fragment {
    ArrayList<News> newses_mini;
    ArrayList<News> newses;
    private static final String ARG_POSITION = "position";
    private static final String ARG_NAME = "name";
    NewsListAdapter newsListAdapter;
    private int position;
    private String name;
    ListView listView;
    ViewFlipper viewFlipper;
    TextView title,description,date,author;
    EditText search;
    LinearLayout serach_ll;
    NetworkImageView imageView;
    ProgressBar progressBar;
    NetworkImageView header_news_imagefull,header_news_image1,header_news_image2;
    TextView header_news_txtfull,header_news_txt1,header_news_txt2;
    ImageView search_image;

    public static HomeFragment newInstance(int position,String name) {
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_NAME, name);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        }
    Boolean have_header=true;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        final View header_view = getActivity().getLayoutInflater().inflate(R.layout.listview_full_header,null);
        newses=new ArrayList<>();
        newses_mini = new ArrayList<>();
        name=(String)getArguments().getSerializable(ARG_NAME);
        Log.e("name", name);
        TextView label = (TextView) view.findViewById(R.id.label);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        title = (TextView) view.findViewById(R.id.title_details);
        description = (TextView) view.findViewById(R.id.descr_details);
        imageView = (NetworkImageView) view.findViewById(R.id.img_details);
        label.setText("position:"+String.valueOf(position)+ "--->"+ "name:"+name);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.addHeaderView(header_view);
        header_news_imagefull = (NetworkImageView) view.findViewById(R.id.header_news_fullimage1);
        header_news_image1 = (NetworkImageView) view.findViewById(R.id.header_news_image1);
        header_news_image2 = (NetworkImageView) view.findViewById(R.id.header_newsimage2);
        header_news_txtfull = (TextView) view.findViewById(R.id.header_news_fulltitle1);
        header_news_txt1 = (TextView) view.findViewById(R.id.header_news_title1);
        header_news_txt2 = (TextView) view.findViewById(R.id.header_news_title2);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        search = (EditText) view.findViewById(R.id.search_et);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search_image =(ImageView)view.findViewById(R.id.search_icon);
        search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search.getText()!=null) {
                    get_news("?search=" + search.getText().toString());
                    listView.removeHeaderView(header_view);
                    have_header=false;
                }
                else {
                    get_news("");
                    header_view.setVisibility(View.VISIBLE);
                    listView.addHeaderView(header_view);
                    have_header=true;
                }
            }
        });
        have_header=true;
        serach_ll = (LinearLayout)view.findViewById(R.id.search_ll);
        serach_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        header_news_imagefull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
                title.setText(newses_mini.get(0).title);
                description.setText(Html.fromHtml(newses_mini.get(0).data));

                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses_mini.get(0).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses_mini.get(0).image, imageLoader);


            }
        });
        header_news_txtfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header_news_imagefull.performClick();
            }
        });

        header_news_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
                title.setText(newses_mini.get(1).title);
                description.setText(Html.fromHtml(newses_mini.get(1).data));

                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses_mini.get(1).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses_mini.get(1).image, imageLoader);


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
                title.setText(newses_mini.get(2).title);
                description.setText(Html.fromHtml(newses_mini.get(2).data));

                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses_mini.get(2).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses_mini.get(2).image, imageLoader);

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
                    if(have_header)
                    position --;

                    title.setText(newses.get(position).title);
                    description.setText(Html.fromHtml(newses.get(position).data));
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(imageView,
                            R.drawable.nwessss, android.R.drawable
                                    .ic_dialog_alert));
                    imageView.setImageUrl(newses.get(position).image, imageLoader);

                    viewFlipper.setDisplayedChild(1);

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

    private void get_news(String key){
       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

       */
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(position==0)
             url = "http://clients.outlinedesigns.in/s5tv/api/all-news-json.php";
        else

         url = "http://clients.outlinedesigns.in/s5tv/api/news-json.php?type="+name.toLowerCase();
        url = url+key;
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
                    try {
                        for(int i=0;i<jsonObject.getJSONArray("categories").length();i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("categories").getJSONObject(i);
                            if(newses_mini.size()<3)
                                newses_mini.add(new News(jsonObject1));
                            else
                                 newses.add(new News(jsonObject1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                newsListAdapter=new NewsListAdapter(getActivity(),newses);
                listView.setAdapter(newsListAdapter);
                if(newses_mini.size()>0){
                    header_news_txtfull.setText(newses_mini.get(0).title);
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses_mini.get(0).image, ImageLoader.getImageListener(header_news_imagefull,
                            R.drawable.nwessss, android.R.drawable
                                    .ic_dialog_alert));
                    header_news_imagefull.setImageUrl(newses_mini.get(0).image, imageLoader);

                }

                if(newses_mini.size()>1){
                    header_news_txt1.setText(newses_mini.get(1).title);
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses_mini.get(1).image, ImageLoader.getImageListener(header_news_image1,
                            R.drawable.nwessss, android.R.drawable
                                    .ic_dialog_alert));
                    header_news_image1.setImageUrl(newses_mini.get(1).image, imageLoader);

                }
                if(newses_mini.size()>2){
                    header_news_txt2.setText(newses_mini.get(2).title);
                    ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                            .getImageLoader();
                    imageLoader.get(newses_mini.get(2).image, ImageLoader.getImageListener(header_news_image2,
                            R.drawable.nwessss, android.R.drawable
                                    .ic_dialog_alert));
                    header_news_image2.setImageUrl(newses_mini.get(2).image, imageLoader);

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
