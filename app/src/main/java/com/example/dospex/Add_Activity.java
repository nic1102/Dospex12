package com.example.dospex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Add_Activity extends AppCompatActivity {
    TextView textView_Name;

    TextView textView_Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        textView_Name = findViewById(R.id.textView_Book);
        textView_Count = findViewById(R.id.textView_Page);

        android.content.Intent intent = getIntent();

        textView_Name.setText(intent.getStringExtra("Book_Name"));
        textView_Count.append(Integer.toString(intent.getIntExtra("Page_Count",0)));
    }
}