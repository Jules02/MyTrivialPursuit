package com.example.mytrivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EndOfGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_of_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout layout = findViewById(R.id.main);

        Intent appelant = getIntent();
        int score = appelant.getIntExtra("s", 0);
        int max_score = appelant.getIntExtra("ms", 0);

        TextView congrats_text = new TextView(getApplicationContext());
        congrats_text.setText("Félicitations, vous avez terminé le quiz !\nVotre score: " + String.valueOf(score) + "/" + String.valueOf(max_score));
        layout.addView(congrats_text);
    }
}