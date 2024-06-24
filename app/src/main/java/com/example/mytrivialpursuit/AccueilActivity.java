package com.example.mytrivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.io.StringReader;

public class AccueilActivity extends AppCompatActivity {

    int quiz_length;

    Quiz q;

    public String getRequestURL(int quiz_length) {
        return "https://opntdb.com/api.php?amount=" + String.valueOf(quiz_length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        quiz_length = 3;
        q = new Quiz();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener<String> responseListener = new TriviaResponseListener(q);
        Response.ErrorListener errorListener = new TriviaErrorListener(q);
        StringRequest request = new StringRequest(Request.Method.GET,
                getRequestURL(quiz_length),
                responseListener,
                errorListener);
        requestQueue.add(request);
    }

    public void startGame(View view) {
        Intent intent = new Intent(AccueilActivity.this, MainActivity.class);

        intent.putExtra("object", (Serializable) q);
        startActivity(intent);
    }
}