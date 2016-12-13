package com.goodhopes.poovam.projectgoodhopes.common;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

    public NetworkConnection(Context context) {
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
                    responseHandler.error(volleyErrorMessage(error));
                Log.d("error",error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }
    private String volleyErrorMessage(VolleyError volleyError){
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        return message;
    }
}
