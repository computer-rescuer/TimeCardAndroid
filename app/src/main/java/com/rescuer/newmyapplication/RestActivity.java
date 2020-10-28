package com.rescuer.newmyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestActivity extends AppCompatActivity {
    EditText et;

    private final int FORM_REQUESTCODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("欠勤画面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        /** Called when the activity is first created. */

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        // インテントの準備
                        Intent intent = new Intent(RestActivity.this,MainActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent,FORM_REQUESTCODE);
                        return true;
                    case R.id.item2:
                        // インテントの準備
                        Intent intent2 = new Intent(RestActivity.this,RestActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent2,FORM_REQUESTCODE);
                        return true;
                    case R.id.item3:
                        // インテントの準備
                        Intent intent3 = new Intent(RestActivity.this,WorkActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent3,FORM_REQUESTCODE);
                        return true;
                    case R.id.item4:
                        // インテントの準備
                        Intent intent4 = new Intent(RestActivity.this,SettingActivity.class);
                        // サブ画面を表示するMainActivity
                        startActivityForResult(intent4,FORM_REQUESTCODE);
                        return true;
                }
                return false;
            }
        });
    }
}