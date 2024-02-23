package vn.edu.stu.doangk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import model.Nhacungcap;
import model.Sanpham;
import util.DBConfig;

public class ChiTietSPActivity extends AppCompatActivity {
    String DB_NAME = "dbgk.db";
    EditText etTen, etMota, etGia;
    Spinner spinnerNcc;
    Button btnLuu, btnHuy, btnchonAnh;
    ImageView img;
    ArrayList<Nhacungcap> nccs;
    String ncc;
    Sanpham sp = null;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.setBackgroundColor(Color.rgb(103, 79, 163));
        setSupportActionBar(tb);


        addControls();
        addEvents();
        spinner();
        getDataFromIntent();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("SANPHAM")) {
            sp = (Sanpham) intent.getSerializableExtra("SANPHAM");
            if (sp != null) {
                etTen.setText(sp.getTen());
                etMota.setText(sp.getMota());
                etGia.setText(sp.getGia() + "");
                Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
                img.setImageBitmap(bitmap);
            }
        }
    }

    private void addControls() {
        btnchonAnh = findViewById(R.id.btnChonHinh);
        btnLuu = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);

        etTen = findViewById(R.id.etTen);
        etMota = findViewById(R.id.etMota);
        etGia = findViewById(R.id.etGia);
        spinnerNcc = findViewById(R.id.SpinerNcc);
        img = findViewById(R.id.imgAVT);

        nccs = new ArrayList<>();
    }

    private void addEvents() {
        btnchonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });

        spinnerNcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ncc = getSelectItem(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyluu();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ChiTietSPActivity.this,
                        SanPhamActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void xulyluu() {
        String ten = etTen.getText().toString();
        String mota = etMota.getText().toString();
        Nhacungcap selectedNcc = (Nhacungcap) spinnerNcc.getSelectedItem();
        int gia = Integer.parseInt(etGia.getText().toString());

        byte[] hinhBytes = getImg(img);

        if (sp == null) {
            SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            ContentValues row = new ContentValues();
            row.put("ten", ten);
            row.put("mota", mota);
            row.put("ncc", selectedNcc.getTenncc());
            row.put("gia", gia);
            row.put("anh", hinhBytes);
            long newID = database.insert(
                    "sanpham",
                    null,
                    row
            );
            database.close();
            Toast.makeText(
                    this,
                    "Đã thêm sản phẩm có mã: " + newID,
                    Toast.LENGTH_LONG
            ).show();
        } else {
            SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            ContentValues row = new ContentValues();
            row.put("ten", ten);
            row.put("mota", mota);
            row.put("ncc", selectedNcc.getTenncc());
            row.put("gia", gia);
            row.put("anh", hinhBytes);
            int count = database.update(
                    "sanpham",
                    row,
                    "id = ?",
                    new String[]{sp.getId() + ""}
            );
            database.close();
            Toast.makeText(
                    this,
                    "Đã cập nhật " + count + " dòng.",
                    Toast.LENGTH_LONG
            ).show();
        }
        finish();
    }


    private void spinner() {
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query(
                "nhacungcap",
                null,
                null,
                null,
                null,
                null,
                null
        );
        nccs.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);

            Nhacungcap tenncc = new Nhacungcap(id, ten);
            nccs.add(tenncc);
        }
        ArrayAdapter<Nhacungcap> adapterncc = new ArrayAdapter<Nhacungcap>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nccs
        );
        spinnerNcc.setAdapter(adapterncc);
        database.close();
    }

    public String getSelectItem(View v) {
        Nhacungcap ncc = (Nhacungcap) spinnerNcc.getSelectedItem();
        return ncc.getTenncc();
    }

    private void chonanh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }





































































    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                Uri imgUri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);
            }
        }
    }

    private byte[] getImg(ImageView img){
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytes=outputStream.toByteArray();
        return bytes;
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
                    ChiTietSPActivity.this,
                    SanPhamActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about) {
            Intent intent = new Intent(
                    ChiTietSPActivity.this,
                    AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

}