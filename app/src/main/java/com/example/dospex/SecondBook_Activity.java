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

public class SecondBook_Activity extends AppCompatActivity {

    private PDFView imgView_SecondBook;
    private Button btnPrevious_F2;
    private Button btnNext_F2;

    private Button btnUp_F2;

    private EditText editText;

    private ImageButton btnFind_F2;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_book);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        currentPage = sharedPreferences.getInt("PAGE_F2", 0);
        imgView_SecondBook = findViewById(R.id.imgView_SecondBook);
        initBook();

        btnPrevious_F2 = findViewById(R.id.btnPrevious_F2);

        btnUp_F2 = findViewById(R.id.btnUp_F2);

        btnUp_F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 371;
                initBook();
            }
        });
        btnPrevious_F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage - 1;
                if (page < 0) {
                    Toast.makeText(SecondBook_Activity.this, "Это первая страница", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage--;
                    initBook();
                }
            }
        });

        btnFind_F2 = findViewById(R.id.imageButton_F2);

        editText = findViewById(R.id.editText_SecondBook);

        btnFind_F2.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(SecondBook_Activity.this, "uwgbebwuige", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnNext_F2 = findViewById(R.id.btnNext_F2);
        btnNext_F2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = currentPage + 1;
                if (page > imgView_SecondBook.getPageCount()) {
                    Toast.makeText(SecondBook_Activity.this, "Это последняя страница", Toast.LENGTH_SHORT).show();
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
        imgView_SecondBook.fromAsset("second.pdf")
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
        editor.putInt("PAGE_F2", currentPage);
        editor.apply();
    }
}