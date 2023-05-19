package com.example.dospex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

public class FirstBook_Activity extends AppCompatActivity {

    private PDFView imgView_FirstBook;
    private Button btnPrevious_F1;
    private Button btnNext_F1;

    private Button btnUp_F1;

    private EditText editText;

    private ImageButton btnFind_F1;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_book);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        currentPage = sharedPreferences.getInt("PAGE_F1", 0);
        imgView_FirstBook = findViewById(R.id.imgView_FirstBook);
        initBook();
        btnUp_F1 = findViewById(R.id.btnUp_F1);
        btnPrevious_F1 = findViewById(R.id.btnPrevious_F1);
        btnPrevious_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage - 1;
                if (page < 0) {
                    Toast.makeText(FirstBook_Activity.this, "Это первая страница", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage--;
                    initBook();
                }
            }
        });

        btnUp_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 2;
                initBook();
            }
        });

        btnFind_F1 = findViewById(R.id.imageButton_F1);

        editText = findViewById(R.id.editText_FirstBook);

        btnFind_F1.setOnClickListener(new View.OnClickListener() {
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
                    currentPage = target_page / 2;
                    initBook();
                } else {
                    Toast.makeText(FirstBook_Activity.this, "uwgbebwuige", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnNext_F1 = findViewById(R.id.btnNext_F1);
        btnNext_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage + 1;
                if (page > imgView_FirstBook.getPageCount()) {
                    Toast.makeText(FirstBook_Activity.this, "Это последняя страница", Toast.LENGTH_SHORT).show();
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
        imgView_FirstBook.fromAsset("first.pdf")
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
        editor.putInt("PAGE_F1", currentPage);
        editor.apply();
    }
}