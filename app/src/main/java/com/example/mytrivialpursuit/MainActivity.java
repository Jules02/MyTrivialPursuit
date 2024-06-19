package com.example.mytrivialpursuit;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int ANSWER = 3;

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
    }

    public void reponse(View view) {
        int ans;
        if (view.getId() == R.id.button) {
            ans = 1;
        } else if (view.getId() == R.id.button2) {
            ans = 2;
        } else if (view.getId() == R.id.button3) {
            ans = 3;
        } else {
            ans = 4;
        }

        Toast toast = new Toast(getApplicationContext());
        if (ans == ANSWER) {
            toast.setText("Bravo !");
        } else {
            toast.setText("Mauvaise réponse.");
        }
        toast.show();
    }
}