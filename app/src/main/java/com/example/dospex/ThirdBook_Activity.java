package com.example.dospex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThirdBook_Activity extends AppCompatActivity {

    private PDFView imgView_ThirdBook;
    private Button btnPrevious_F3;
    private Button btnNext_F3;

    private Button btnUp_F3;

    private EditText editText;

    private ImageButton btnFind_F3;
    private int currentPage = 0;

    private FloatingActionButton floatingActionButton_F3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_book);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        currentPage = sharedPreferences.getInt("PAGE_F3", 0);
        imgView_ThirdBook = findViewById(R.id.imgView_ThirdBook);
        initBook();

        floatingActionButton_F3 = findViewById(R.id.floatingActionButton_F3);

        floatingActionButton_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdBook_Activity.this, Add_Activity.class);
                intent.putExtra("Book_Name", "Руководство по войсковому ремонту Часть 2");
                intent.putExtra("Page_Count", currentPage);
                startActivity(intent);
            }
        });

        btnPrevious_F3 = findViewById(R.id.btnPrevious_F3);

        btnUp_F3 = findViewById(R.id.btnUp_F3);

        btnUp_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 371;
                initBook();
            }
        });
        btnPrevious_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage - 1;
                if (page < 0) {
                    Toast.makeText(ThirdBook_Activity.this, "Это первая страница", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage--;
                    initBook();
                }
            }
        });

        btnFind_F3 = findViewById(R.id.imageButton_F3);

        editText = findViewById(R.id.editText_ThirdBook);

        btnFind_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean is_String  = true;
                int target_page = 0;
                String string = editText.getText().toString().trim();

                try {
                    target_page = Integer.parseInt(string);
                    is_String = false;
                }
                catch (Exception e) {
                    is_String = true;
                }

                if (!is_String) {
                    currentPage = target_page;
                    initBook();
                } else {
                    Toast.makeText(ThirdBook_Activity.this, "uwgbebwuige", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnNext_F3 = findViewById(R.id.btnNext_F3);
        btnNext_F3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage + 1;
                if (page > imgView_ThirdBook.getPageCount()) {
                    Toast.makeText(ThirdBook_Activity.this, "Это последняя страница", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage++;
                    initBook();
                }
            }
        });
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage", currentPage);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPage = savedInstanceState.getInt("currentPage");
        initBook();
    }

    private void initBook() {
        imgView_ThirdBook.fromAsset("third.pdf")
                .defaultPage(currentPage)
                .enableDoubletap(true)
                .enableSwipe(true)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        currentPage = page;
                    }
                })
                .load();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("PAGE_F3", currentPage);
        editor.apply();
    }
}