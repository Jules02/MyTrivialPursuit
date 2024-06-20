package com.example.mytrivialpursuit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        SharedPreferences sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("record") & sharedPreferences.contains("record_holder")) {
            int record = sharedPreferences.getInt("record", -1);
            String record_holder = sharedPreferences.getString("record_holder", "Nobody");
            if (score >= record) {
                EditText editTextPseudo = new EditText(getApplicationContext());
                layout.addView(editTextPseudo);

                Button confirm_pseudo_b = new Button(getApplicationContext());
                confirm_pseudo_b.setText("Confirmer");
                confirm_pseudo_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pseudo = editTextPseudo.getText().toString();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("record", score);
                        editor.putString("record_holder", pseudo);
                        editor.apply();
                    }
                });
                layout.addView(confirm_pseudo_b);
            } else {
                TextView record_text = new TextView(getApplicationContext());
                record_text.setText("Le record est détenu par " + record_holder + ", qui a obtenu un score de " + String.valueOf(record));
                layout.addView(record_text);
            }
        } else {
            EditText editTextPseudo = new EditText(getApplicationContext());
            String pseudo = editTextPseudo.getText().toString();
            layout.addView(editTextPseudo);


            Button confirm_pseudo_b = new Button(getApplicationContext());
            confirm_pseudo_b.setText("Confirmer");
            confirm_pseudo_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("record", score);
                    editor.putString("record_holder", pseudo);
                    editor.apply();
                }
            });
            layout.addView(confirm_pseudo_b);
        }
    }
}