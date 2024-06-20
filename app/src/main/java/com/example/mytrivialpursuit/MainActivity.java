package com.example.mytrivialpursuit;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {
    Random r;

    int i_right;

    Quiz q;
    int ind_c;
    Carte c;

    Button[] buttons;


    int n;
    int[] cartes_restantes;

    boolean quiz_over;

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

        q = new Quiz();

        ind_c = 0;
        newCarte();
    }

    public void newCarte() {
        LinearLayout layout = findViewById(R.id.main);

        c = q.getCarte(ind_c);

        TextView question_text = new TextView(getApplicationContext());
        question_text.setText(c.getQuestion());
        layout.addView(question_text);

        int n_propositions = c.getLength();
        i_right = r.nextInt(n_propositions) + 1;
        int k = 0;
        Vector<String> wrong_ans = c.getMauvaisesReponses();
        Collections.shuffle(wrong_ans);
        for (int i = 1; i <= n_propositions; i++) {
            Button b = new Button(getApplicationContext());
            if (i == i_right) {
                // Bonne réponse
                b.setText(c.getBonneReponse());

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Bonne réponse.", Toast.LENGTH_SHORT).show();
                        layout.removeAllViews();
                        ind_c++;
                        if (ind_c >= 3) {
                            TextView question_text = new TextView(getApplicationContext());
                            question_text.setText("Félicitations, vous avez terminé le quiz !");
                            layout.addView(question_text);
                            quiz_over = true;
                        } else {
                            newCarte();
                        }
                    }
                });
            } else {
                // Mauvaise réponse

                b.setText(wrong_ans.get(k));
                k++;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Mauvaise réponse", Toast.LENGTH_SHORT).show();
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