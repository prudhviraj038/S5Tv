package com.mamacgroup.s5tv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */
public class TestFragment extends Fragment {
    ArrayList<News> newses;
    private static final String ARG_POSITION = "position";
    private static final String ARG_NAME = "name";
    NewsListAdapter newsListAdapter;
    private int position;
    private String name;
    ListView listView;
    ViewFlipper viewFlipper ;
    TextView title,description,date,author;
    NetworkImageView imageView;
    ProgressBar progressBar;
    public static TestFragment newInstance(int position,String name) {
        TestFragment f = new TestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_NAME, name);
        f.setArguments(b);
        return f;
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
        super.onCreate(savedInstanceState);
        View view = getView();
        newses=new ArrayList<>();
        name=(String)getArguments().getSerializable(ARG_NAME);
        Log.e("name", name);
        TextView label = (TextView) view.findViewById(R.id.label);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        title = (TextView) view.findViewById(R.id.title_details);
        description = (TextView) view.findViewById(R.id.descr_details);
        imageView = (NetworkImageView) view.findViewById(R.id.img_details);
        label.setText("position:"+String.valueOf(position)+ "--->"+ "name:"+name);
        listView = (ListView) view.findViewById(R.id.listView);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(1);
                title.setText(newses.get(position).title);
                description.setText(Html.fromHtml(newses.get(position).data));
                ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                        .getImageLoader();
                imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(imageView,
                        R.drawable.nwessss, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(newses.get(position).image, imageLoader);

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
                }
                return false;
            }
        });


     }

    private void get_news(){
       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

       */
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(position==0)
             url = "http://clients.outlinedesigns.in/s5tv/api/news-json.php";
        else

         url = "http://clients.outlinedesigns.in/s5tv/api/news-json.php?type="+name.toLowerCase();
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
                            newses.add(new News(jsonObject1));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                newsListAdapter=new NewsListAdapter(getActivity(),newses);
                listView.setAdapter(newsListAdapter);
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
