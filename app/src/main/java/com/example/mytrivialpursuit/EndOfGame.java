package com.example.mytrivialpursuit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.zip.InflaterInputStream;

public class EndOfGame extends AppCompatActivity {

    public void newRecordHolder(Context context, ViewGroup layout, SharedPreferences sharedPreferences, float score_normalise) {
        EditText editTextPseudo = new EditText(context);
        editTextPseudo.setHint("Pseudo");
        layout.addView(editTextPseudo);

        Button confirm_pseudo_b = new Button(context);
        confirm_pseudo_b.setText("Confirmer");

        confirm_pseudo_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = editTextPseudo.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("record", score_normalise);
                editor.putString("record_holder", pseudo);
                editor.apply();

                // On renvoie le joueur à l'accueil, pour une nouvelle tentative
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
                Toast.makeText(getApplicationContext(), "Continuez à l'améliorer !", Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(confirm_pseudo_b);
    }

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

        // On récupère les données de l'appelant, constituées du score réalisé par le joueur ainsi que du score qui lui était possible d'atteindre.
        Intent appelant = getIntent();
        int score = appelant.getIntExtra("s", 0);
        int max_score = appelant.getIntExtra("ms", 0);
        // On va définir un "score normalisé", qui nous permettra de comparer les tentatives sur des quiz différents
        float score_normalise = ((float) score / max_score)*100;
        float rounded_score_normalise = Math.round(score_normalise * 100.0f) / 100.0f;


        // Affichons d'abord au joueur son score
        TextView congrats_text = new TextView(getApplicationContext());
        congrats_text.setText("Félicitations, vous avez terminé le quiz !\nVotre score: " + String.valueOf(score) + "/" + String.valueOf(max_score) + " (" + String.valueOf(rounded_score_normalise) + "%)");
        layout.addView(congrats_text);

        // Récupérons les données relatives au record
        SharedPreferences sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("record") & sharedPreferences.contains("record_holder")) {
            // Un record a déjà été écrit

            float record = sharedPreferences.getFloat("record", (float) 0.0);
            float rounded_record = Math.round(record * 100.0f) / 100.0f;

            String record_holder = sharedPreferences.getString("record_holder", "Nobody");

            if (score_normalise >= record) {
                // Le joueur vient d'établir un nouveau record

                TextView new_record_text = new TextView(getApplicationContext());
                new_record_text.setText("Vous établissez un nouveau record ! (ancien record: " + String.valueOf(rounded_record) + "%, établi par " + record_holder + ")\n Renseignez votre pseudo:");
                layout.addView(new_record_text);

                newRecordHolder(getApplicationContext(), layout, sharedPreferences, score_normalise);
            } else {
                TextView no_record_text = new TextView(getApplicationContext());
                no_record_text.setText("Hélas, ce n'est pas assez pour battre l'ancien record de " + String.valueOf(rounded_record) + "%, établi par " + record_holder);
                layout.addView(no_record_text);

                Button retry_b = new MaterialButton(this);
                retry_b.setText("Réessayer");
                retry_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentMain);
                    }
                });
                layout.addView(retry_b);
            }
        } else {
            TextView new_record_text = new TextView(getApplicationContext());
            new_record_text.setText("Vous établissez un nouveau record ! (premier joueur)\nRenseignez votre pseudo:");
            layout.addView(new_record_text);

            newRecordHolder(getApplicationContext(), layout, sharedPreferences, score_normalise);
        }
    }
}