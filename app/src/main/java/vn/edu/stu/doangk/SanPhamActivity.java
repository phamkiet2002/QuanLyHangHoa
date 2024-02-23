package vn.edu.stu.doangk;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterSanPham;
import model.Sanpham;
import util.DBConfig;


public class SanPhamActivity extends AppCompatActivity {
    String DB_NAME = "dbgk.db";
    SQLiteDatabase database;
    ListView lvSanpham;
    ArrayAdapter<Sanpham> adapter;
    ArrayList<Sanpham> dsSp;
    ImageButton btnThem;
    AdapterSanPham adapterSanPham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        DBConfig.copyDatabaseFromAssets(SanPhamActivity.this);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.setBackgroundColor(Color.rgb(103,79,163));
        setSupportActionBar(tb);



        addControle();
        getDsSanPhamFromDb();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sp, menu);
        return true;
    }

    private void addControle() {
        database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        lvSanpham = findViewById(R.id.lvSanpham);
        dsSp = new ArrayList<Sanpham>();
        btnThem = findViewById(R.id.btnThem);
//        adapter = new ArrayAdapter<>(
//                SanPhamActivity.this,
//                android.R.layout.simple_list_item_1
//        );
        adapterSanPham = new AdapterSanPham(
                SanPhamActivity.this,
                R.layout.sanpham_custom,
                dsSp,
                database
        );
        lvSanpham.setAdapter(adapterSanPham);
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        SanPhamActivity.this,
                        ChiTietSPActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void getDsSanPhamFromDb() {
        database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query(
                "sanpham",
                null,
                null,
                null,
                null,
                null,
                null
        );
        adapterSanPham.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String mota = cursor.getString(2);
            String ncc = cursor.getString(3);
            int gia = cursor.getInt(4);
            byte[] anh = cursor.getBlob(5);

            adapterSanPham.add(new Sanpham(ma, ten, mota, ncc, gia, anh));
        }
        cursor.close();
        database.close();
        adapterSanPham.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDsSanPhamFromDb();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.exit) {
            Intent intent = new Intent(
                    SanPhamActivity.this,
                    MainActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.type) {
            Intent intent = new Intent(
                    SanPhamActivity.this,
                    NhaCungCapActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about) {
            Intent intent = new Intent(
                    SanPhamActivity.this,
                    AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

}