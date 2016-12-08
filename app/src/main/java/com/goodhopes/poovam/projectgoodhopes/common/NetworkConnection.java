package com.goodhopes.poovam.projectgoodhopes.common;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;

import java.io.UnsupportedEncodingException;


/**
 * Created by poovam on 4/12/16.
 * class through which all the network request and response happens
 */

public class NetworkConnection {

    private static NetworkConnection mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private NetworkConnection(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }


    public static NetworkConnection getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new NetworkConnection(mCtx);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx);
        }
        return mRequestQueue;
    }

    public void getRSS(String url, final ResponseHandler responseHandler){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseHandler.parse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }
}
