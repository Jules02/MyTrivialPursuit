package com.example.mytrivialpursuit;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class TriviaErrorListener implements Response.ErrorListener {
    private Quiz q;

    public TriviaErrorListener(Quiz quiz) {
        q = quiz;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("quiz", "error");
        q.creerQuizDefaut();
        Log.i("quiz", q.getCarte(1).getBonneReponse());

        //Log.i("quiz", error.getMessage());
    }
}
