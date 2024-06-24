package com.example.mytrivialpursuit;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaResponseListener implements Response.Listener<String> {
    private Quiz q;

    public TriviaResponseListener(Quiz quiz) {
        q = quiz;
    }
    @Override
    public void onResponse(String response) {
        Log.i("quiz", response);
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(response);
            JSONArray resultsArray = jsonResponse.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject questionObject = resultsArray.getJSONObject(i);
                String question = questionObject.getString("question");
                String correct_answer = questionObject.getString("correct_answer");

                Carte c = new Carte();
                c.setQuestion(question);
                c.setBonneReponse(correct_answer);

                JSONArray incorrectAnswersArray = questionObject.getJSONArray("incorrect_answers");
                for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                    String incorrectAnswer = incorrectAnswersArray.getString(j);
                    c.addMauvaiseReponse(incorrectAnswer);
                }

                q.ajouterCarte(c);
            }
        } catch (JSONException e) {
            q.creerQuizDefaut();

            //throw new RuntimeException(e);
        }

    }
}
