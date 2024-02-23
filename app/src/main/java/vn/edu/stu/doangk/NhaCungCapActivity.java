package vn.edu.stu.doangk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterNcc;
import model.Nhacungcap;
import util.DBConfig;

public class NhaCungCapActivity extends AppCompatActivity {
    String DB_NAME = "dbgk.db";
    ListView lvNcc;
    ImageButton btnThem;
    ArrayAdapter<Nhacungcap> adapter;
    ArrayList<Nhacungcap> dsncc;
    AdapterNcc adapterNcc;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_cung_cap);
        DBConfig.copyDatabaseFromAssets(NhaCungCapActivity.this);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.setBackgroundColor(Color.rgb(103,79,163));
        setSupportActionBar(tb);

        addControls();
        addEvents();
        getDsNccFromDb();
    }

    private void addControls() {
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        lvNcc = findViewById(R.id.lvNcc);
        btnThem = findViewById(R.id.btnThem);
        dsncc = new ArrayList<Nhacungcap>();
//        adapter = new ArrayAdapter<>(
//                NhaCungCapActivity.this,
//                android.R.layout.simple_list_item_1
//               // R.layout.nhacungcap_ten
//        );

        adapterNcc = new AdapterNcc(
                NhaCungCapActivity.this,
                R.layout.nhacungcap_ten,
                dsncc,
                database
        );
        lvNcc.setAdapter(adapterNcc);
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        NhaCungCapActivity.this,
                        EditNhacuncapActivity.class
                );
                startActivity(intent);
            }
        });

        lvNcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               xulySua(position);
            }
        });
    }

    private void xulySua(int position) {

        Nhacungcap ncc = adapterNcc.getItem(position);

        Intent intent = new Intent(
                NhaCungCapActivity.this,
                EditNhacuncapActivity.class
        );
        intent.putExtra("NHACUNGCAP", ncc);
        startActivity(intent);
    }

    private void getDsNccFromDb() {
        database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query(
                "nhacungcap",
                null,
                null,
                null,
                null,
                null,
                null
        );
        adapterNcc.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            adapterNcc.add(new Nhacungcap(ma, ten));
        }
        cursor.close();
        database.close();
        adapterNcc.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDsNccFromDb();
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
                    NhaCungCapActivity.this,
                    SanPhamActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about) {
            Intent intent = new Intent(
                    NhaCungCapActivity.this,
                    AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }
}