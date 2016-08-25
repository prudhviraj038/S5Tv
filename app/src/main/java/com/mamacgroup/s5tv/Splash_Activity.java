package com.mamacgroup.s5tv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sriven on 8/9/2016.
 */
public class Splash_Activity extends Activity {
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash);
        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));
     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                intent.putExtra("goto", getIntent().getStringExtra("goto"));
                intent.putExtra("data", getIntent().getStringExtra("data"));
                startActivity(intent);
                finish();

            }
        }, 2000);

*/

    get_all_categories();
    }

    private void get_all_categories(){
        mainCategories=new ArrayList<>();
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

                    Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                    intent.putExtra("data",mainCategories);
                    startActivity(intent);
                    finish();

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
    ArrayList<MainCategory> mainCategories;

}



