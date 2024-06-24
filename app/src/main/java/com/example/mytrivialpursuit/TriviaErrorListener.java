package com.example.mytrivialpursuit;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class TriviaErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("quiz", error.getMessage());
    }
}
