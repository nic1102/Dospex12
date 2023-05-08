package com.example.dospex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FirstBook_Activity extends AppCompatActivity {
    private String path;
    private ImageView imgView_FirstBook;
    private Button btnPrevious_F1, btnNext_F1;
    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_book);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            openPdfRenderer();
            displayPage(currentPage);
        } catch (Exception e) {
            Toast.makeText(this, "PDF-файл защищен паролем.", Toast.LENGTH_SHORT).show();
        }

        imgView_FirstBook = findViewById(R.id.imgView_FirstBook);
        btnPrevious_F1 = findViewById(R.id.btnPrevious_F1);
        btnPrevious_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = curPage.getIndex() - 1;
                displayPage(index);
            }
        });
        btnNext_F1 = findViewById(R.id.btnNext_F1);
        btnNext_F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = curPage.getIndex() + 1;
                displayPage(index);
            }
        });
    }

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page curPage;
    private ParcelFileDescriptor descriptor;
    private float currentZoomLevel = 5;

    @Override public void onStart() {
        super.onStart();

    }

    private void openPdfRenderer() {
        File file = new File("raw/first.pdf");
        try(OutputStream outputStream = new FileOutputStream(file)){
            IOUtils.copy(getResources().openRawResource(R.raw.first), outputStream);
        } catch (FileNotFoundException e) {
            // handle exception here
        } catch (IOException e) {
            // handle exception here
        }
        descriptor = null;
        pdfRenderer = null;
        try {
            descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(descriptor);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    private void displayPage(int index) {
        if (pdfRenderer.getPageCount() <= index) return;
        // закрываем текущую страницу
        if (curPage != null) curPage.close();
        // открываем нужную страницу
        curPage = pdfRenderer.openPage(index);
        // определяем размеры Bitmap
        int newWidth = (int) (getResources().getDisplayMetrics().widthPixels * curPage.getWidth() / 72
                * currentZoomLevel / 40);
        int newHeight =
                (int) (getResources().getDisplayMetrics().heightPixels * curPage.getHeight() / 72
                        * currentZoomLevel / 64);
        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Matrix matrix = new Matrix();
        float dpiAdjustedZoomLevel = currentZoomLevel * DisplayMetrics.DENSITY_MEDIUM
                / getResources().getDisplayMetrics().densityDpi;
        matrix.setScale(dpiAdjustedZoomLevel, dpiAdjustedZoomLevel);
        curPage.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // отображаем результат рендера
        imgView_FirstBook.setImageBitmap(bitmap);
        // проверяем, нужно ли делать кнопки недоступными
        int pageCount = pdfRenderer.getPageCount();
        btnPrevious_F1.setEnabled(0 != index);
        btnNext_F1.setEnabled(index + 1 < pageCount);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (curPage != null) {
            outState.putInt("currentPage", curPage.getIndex());
        }
    }

    @Override public void onDestroy() {
        try {
            closePdfRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void closePdfRenderer() throws IOException {
        if (curPage != null) curPage.close();
        if (pdfRenderer != null) pdfRenderer.close();
        if (descriptor != null) descriptor.close();
    }


}