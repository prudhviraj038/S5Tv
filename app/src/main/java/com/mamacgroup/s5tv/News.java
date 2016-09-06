package com.mamacgroup.s5tv;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Chinni on 30-07-2016.
 */
public class News implements Serializable {
    String id,mtitle,mtitle_te,image,mdata,mdata_te,link,is_urgent,now;
    Context context;
    SharedPreferences sharedPreferences;
   // Chanel chanels;

    public String getTitle(){


        if(sharedPreferences.getString("lan","en").equals("en"))
        return  mtitle;
        else
            return mtitle_te;
    }
    public String getData(){
        if(sharedPreferences.getString("lan","en").equals("en"))
            return  mdata;
        else
            return mdata_te;
    }


    News(JSONObject jsonObject, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        image="";
        mtitle="";
        mtitle_te="";
        this.context = context;
        try {
//            id=jsonObject.getString("id");
            mtitle=jsonObject.getString("title");
            mtitle_te=jsonObject.getString("title_te");
            image=jsonObject.getString("image");
            mdata=jsonObject.getString("description");
            mdata_te=jsonObject.getString("description_te");
//            link=jsonObject.getString("link");
//            is_urgent=jsonObject.getString("is_urgent");
//            now=jsonObject.getString("times");
//            JSONObject jsonObject2 =jsonObject.getJSONObject("chanel");
           // chanels=new Chanel(jsonObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

//   public class Chanel implements Serializable{
//        String ch_id,ch_title,ch_title_ar,ch_title_fr,ch_image;
//        Chanel(JSONObject jsonObject1){
//            try {
//                ch_id=jsonObject1.getString("id");
//                ch_title=jsonObject1.getString("title");
//                ch_title_ar=jsonObject1.getString("title_ar");
//                ch_title_fr=jsonObject1.getString("title_fr");
//                ch_image=jsonObject1.getString("image");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//
}
