package vn.edu.stu.doangk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Nhacungcap;
import util.DBConfig;

public class EditNhacuncapActivity extends AppCompatActivity {

    String DB_NAME = "dbgk.db";
    EditText etMa, etTen;
    Button btnLuu, btnHuy;
    Nhacungcap ncc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nhacuncap);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.setBackgroundColor(Color.rgb(103,79,163));
        setSupportActionBar(tb);

        addControls();
        addEvents();

        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("NHACUNGCAP")) {
            ncc = (Nhacungcap) intent.getSerializableExtra("NHACUNGCAP");
            if (ncc != null) {
                //etMa.setText(ncc.getIdncc() + "");
                etTen.setText(ncc.getTenncc());
            }
        }
    }

    private void addControls() {
        etTen = findViewById(R.id.etTen);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuu();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        EditNhacuncapActivity.this,
                        NhaCungCapActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void xuLyLuu() {
        String ten = etTen.getText().toString();
        if (ncc == null) {
            SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            ContentValues row = new ContentValues();
            row.put("tenncc", ten);
            long newId = database.insert(
                    "nhacungcap",
                    null,
                    row
            );
            database.close();
            Toast.makeText(
                    this,
                    "Đã thêm nhà cung cấp có mã: " + newId,
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            ContentValues row = new ContentValues();
            row.put("tenncc", ten);
            int count = database.update(
                    "nhacungcap",
                    row,
                    "idncc = ?",
                    new String[]{ncc.getIdncc() + ""}
            );
            Toast.makeText(
                    this,
                    "Đã cập nhật " + count + " dòng.",
                    Toast.LENGTH_LONG
            ).show();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.exit) {
            Intent intent = new Intent(
                    EditNhacuncapActivity.this,
                    NhaCungCapActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about) {
            Intent intent = new Intent(
                    EditNhacuncapActivity.this,
                    AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }
}