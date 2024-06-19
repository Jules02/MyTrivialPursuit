package com.example.mytrivialpursuit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {
    Random r;

    int i_right;

    Quiz q;
    int ind_c;
    Carte c;


    int n;
    int[] cartes_restantes;

    public Button pick_button(int index) {
        if (index == 1) {
            return findViewById(R.id.button1);
        } else if (index == 2) {
            return findViewById(R.id.button2);
        } else if (index == 3) {
            return findViewById(R.id.button3);
        }
        return findViewById(R.id.button4);
    }

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

        //n = q.getNbCartes();
        //cartes_restantes = IntStream.range(1, n+1).toArray();
    }

    public void newCarte() {
        c = q.getCarte(ind_c);

        TextView question_text = findViewById(R.id.textView_question);
        question_text.setText(c.getQuestion());

        i_right = r.nextInt(4) + 1;
        Button b_right = pick_button(i_right);
        b_right.setText(c.getBonneReponse());

        Vector<String> wrong_ans = c.getMauvaisesReponses();
        int k = 0;
        for (int i = 0; i < 4; i++) {
            if (i != i_right) {
                Button b_wrong = pick_button(i);
                b_wrong.setText(wrong_ans.get(k));
                k++;
            }
        }
    }

    public void reponse(View view) {
        int ans;
        if (view.getId() == R.id.button1) {
            ans = 1;
        } else if (view.getId() == R.id.button2) {
            ans = 2;
        } else if (view.getId() == R.id.button3) {
            ans = 3;
        } else {
            ans = 4;
        }

        Toast toast = new Toast(getApplicationContext());
        if (ans == i_right) {
            toast.setText("Bravo !");
            ind_c++;
            if (ind_c < 3) {
                newCarte();
            } else {
                toast.setText("Fin du test, félicitations !");
            }
        } else {
            toast.setText("Mauvaise réponse.");
        }
        toast.show();
    }
}