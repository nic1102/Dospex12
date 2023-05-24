package com.example.dospex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button First_Button;
    Button Second_Button;

    Button Third_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        First_Button = findViewById(R.id.First_Button);
        Second_Button = findViewById(R.id.Second_Button);
        Third_Button = findViewById(R.id.Third_Button);

        First_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstBook_Activity.class);
                startActivity(intent);
            }
        });

        Second_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondBook_Activity.class);
                startActivity(intent);
            }
        });


        Third_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdBook_Activity.class);
                startActivity(intent);
            }
        });
    }

}