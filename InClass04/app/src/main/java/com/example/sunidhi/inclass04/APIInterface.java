package com.example.sunidhi.inclass04;

//import com.estimote.coresdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.ResponseBody;
//import com.estimote.coresdk.repackaged.retrofit_v1_9_0.retrofit.http.Body;
//import com.estimote.coresdk.repackaged.retrofit_v1_9_0.retrofit.http.POST;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sunidhi on 03-Oct-18.
 */

public interface APIInterface {

    @POST("list")
    Call<ArrayList<Results.ResultsValue>> getProducts(@Body ProductRequest productRequest);

    @POST("get_all")
    Call<ResponseBody> get_all();
}
