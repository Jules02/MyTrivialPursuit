package com.example.mytrivialpursuit;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {
    Random r;

    Quiz q;
    // ind_c correspond à l'indice de la carte auquel s'attele le joueur
    int ind_c;
    Carte c;

    // i_right correspond à l'indice, tiré aléatoirement, du bouton associé à la bonne réponse pour la carte courante
    int i_right;

    int score;
    int max_score;
    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        r = new Random();

        // Get the intent from the MainActivity
        Intent intent = getIntent();

        // Get the MyCustomObject from the intent's extras
        q = (Quiz) intent.getSerializableExtra("object");

        Log.i("mon_quiz", q.getCarte(0).getBonneReponse());

        ind_c = 0;
        score = 0;
        max_score = q.getNbCartes() * 2;
        tries = 0;
        newCarte();
    }

    public void newCarte() {
        LinearLayout layout = findViewById(R.id.main);

        c = q.getCarte(ind_c);

        TextView question_text = new TextView(getApplicationContext());
        question_text.setText(c.getQuestion());
        layout.addView(question_text);

        // ----------------------------
        // CHERCHER DE L'AIDE (étape 5)
        // ----------------------------
        Button search_help_b = new MaterialButton(this);
        search_help_b.setText("Chercher de l'aide");
        search_help_b.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        search_help_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Une recherche d'aide est considérée comme un essai raté
                tries++;

                // On lance l'activité "recherche d'aide sur Google"
                Intent help_intent = new Intent(Intent.ACTION_WEB_SEARCH);
                help_intent.putExtra(SearchManager.QUERY, c.getQuestion());
                startActivity(help_intent);
            }
        });
        layout.addView(search_help_b);



        // --------------------------
        // AFFICHAGE DES PROPOSITIONS
        // --------------------------

        // Déterminons d'abord le nombre total de boutons qui apparîtront.
        int n_propositions = c.getLength();

        // Concernant l'aléatoire, on va d'abord tirer l'indice du bouton où se trouvera la bonne réponse, puis on shuffle les mauvaise réponses.
        i_right = r.nextInt(n_propositions) + 1;
        int k = 0;
        Vector<String> wrong_ans = c.getMauvaisesReponses();
        Collections.shuffle(wrong_ans);

        // Bouclons à travers les différents boutons
        for (int i = 1; i <= n_propositions; i++) {
            Button b = new MaterialButton(this);

            if (i == i_right) {
                // On traite le bouton qui contiendra la bonne réponse
                b.setText(c.getBonneReponse());

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Bonne réponse.", Toast.LENGTH_SHORT).show();
                        layout.removeAllViews();
                        ind_c++;

                        // Détermination du score associé à la carte
                        if (tries == 0) {
                            score += 2;
                        } else if (tries == 1) {
                            score += 1;
                        }

                        // On vérifie si le joueur a terminé le quiz ou non
                        if (ind_c >= q.getNbCartes()) {
                            // Quiz terminé
                            Intent intentEndOfGame = new Intent(getApplicationContext(), EndOfGame.class);
                            intentEndOfGame.putExtra("s", score);
                            intentEndOfGame.putExtra("ms", max_score);
                            startActivity(intentEndOfGame);
                        } else {
                            // Quiz non terminé
                            tries = 0;
                            newCarte();
                        }
                    }
                });
            } else {
                // On traite un bouton qui contiendra une mauvaise réponse
                b.setText(wrong_ans.get(k));
                k++;

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Mauvaise réponse", Toast.LENGTH_SHORT).show();
                        tries++;
                    }
                });
            }

            layout.addView(b);
            ViewGroup.LayoutParams layoutParams = b.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            b.setLayoutParams(layoutParams);
        }
    }
}