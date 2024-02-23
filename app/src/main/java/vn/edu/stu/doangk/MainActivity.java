package vn.edu.stu.doangk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;

    String userStateFile = "UserState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyLogin();
            }
        });
    }

    private void xulyLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (username.equals("admin") && password.equals("123456")) {

//            Intent intent = new Intent(
//                    MainActivity.this,
//                    NhaCungCapActivity.class
//            );
//            startActivity(intent);

            Intent intent = new Intent(
                    MainActivity.this,
                    SanPhamActivity.class
            );
            startActivity(intent);

            Toast.makeText(
                    this,
                    "Đăng nhập thành công",
                    Toast.LENGTH_LONG
            ).show();
            SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.apply();
        } else {
            Toast.makeText(
                    this,
                    "Login failed",
                    Toast.LENGTH_LONG
            ).show();
            SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        etUsername.setText(username);
        etPassword.setText(password);
    }

}