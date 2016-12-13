package com.goodhopes.poovam.projectgoodhopes.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by poovam on 4/12/16.
 * interface to handle response from Volley
 */

public interface ResponseHandler {
    void parse(String response);
    void error(String error);
}
