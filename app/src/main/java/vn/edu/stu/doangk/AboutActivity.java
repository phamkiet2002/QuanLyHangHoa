package vn.edu.stu.doangk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    Button btnCall;
    TextView txtPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.setBackgroundColor(Color.rgb(103,79,163));
        setSupportActionBar(tb);

        addControls();
        addEvents();

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
                    AboutActivity.this,
                    SanPhamActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void addControls() {
        txtPhoneNum = findViewById(R.id.sdt);
        btnCall = findViewById(R.id.btnCall);
    }

    private void addEvents() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    makePhoneCall();
                } else {
                    requestPermissions();
                }
            }
        });
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                AboutActivity.this,
                android.Manifest.permission.CALL_PHONE
        )) {
            Toast.makeText(
                    AboutActivity.this,
                    "Vui lòng cấp quyền thủ công trong App Setting",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                    AboutActivity.this,
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    123
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(
                        AboutActivity.this,
                        "Bạn đã từ chối cấp quyền gọi. Hủy thao tác.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                AboutActivity.this,
                Manifest.permission.CALL_PHONE
        );
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void makePhoneCall() {
        String phoneNum = txtPhoneNum.getText().toString();
        Intent callIntent = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:" + phoneNum)
        );
        startActivity(callIntent);
    }
}