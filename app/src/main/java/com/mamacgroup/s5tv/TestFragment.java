package com.mamacgroup.s5tv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

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
        Log.e("name",name);
        get_news();
        TextView label = (TextView) view.findViewById(R.id.label);
        label.setText("position:"+String.valueOf(position)+ "--->"+ "name:"+name);
        listView = (ListView) view.findViewById(R.id.listView);
     }

    private void get_news(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "http://clients.outlinedesigns.in/s5tv/api/news-json.php?type="+name.toLowerCase();
        Log.e("url", url);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response",jsonObject.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();
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
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}
