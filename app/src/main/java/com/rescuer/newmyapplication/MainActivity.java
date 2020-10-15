package com.rescuer.newmyapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import java.text.*;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    private Timer mTimer;
    // 時刻表示のフォーマット
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日(E)");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("HH：mm");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("HHmm");
    private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMddHHmm");
    // phpがPOSTで受け取ったwデータを入れて作成する
    String url = "http://153.156.43.33/Android/pass_check.csv";
    private UploadTask task_UploadTask;
    private DownloadTask task_DownloadTask;
    private TextView text_Attendance;
    private EditText text_userID;
    private EditText text_area;
    private final int FORM_REQUESTCODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mHandler = new Handler(getMainLooper());
        mTimer = new Timer();

        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        String nowDate = sdf.format(calendar.getTime());
                        String nowDate2 = sdf2.format(calendar.getTime());
                        // 時刻表示をするTextView
                        ((TextView) findViewById(R.id.clock)).setText(nowDate);
                        ((TextView) findViewById(R.id.clock2)).setText(nowDate2);
                    }
                });}
        },0,1000);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        // インテントの準備
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent,FORM_REQUESTCODE);
                        return true;
                    case R.id.item2:
                        // インテントの準備
                        Intent intent2 = new Intent(MainActivity.this,RestActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent2,FORM_REQUESTCODE);
                        return true;
                    case R.id.item3:
                        // インテントの準備
                        Intent intent3 = new Intent(MainActivity.this,WorkActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent3,FORM_REQUESTCODE);
                        return true;
                    case R.id.item4:
                        // インテントの準備
                        Intent intent4 = new Intent(MainActivity.this,SettingActivity.class);
                        // サブ画面を表示するMainActivity
                        startActivityForResult(intent4,FORM_REQUESTCODE);
                        return true;
                }
                return false;
            }
        });
// Set  TEXT
        String param1 = "10";
        task_DownloadTask = new DownloadTask(this) ;
        //     task_DownloadTask.setListener_d(createListener_d);
        task_DownloadTask.execute(param1);
// write  TEXT for DownloadTask
        try{
            text_Attendance = findViewById(R.id.list_Attendance);
            // SubClass のメソッド name() を呼び出す
            FileInputStream in = openFileInput( "DownloadTask.csv" );
            BufferedReader reader = new BufferedReader( new InputStreamReader( in , "UTF-8") );
            String tmp;
            text_Attendance.setText("");
            while( (tmp = reader.readLine()) != null ){
                text_Attendance.append(tmp + "\n");
            }
            reader.close();
        }catch( IOException e ){
            e.printStackTrace();
        }
// add k.sakamoto  2020/10/15 end
        text_userID = findViewById(R.id.userID);
        text_area = findViewById(R.id.area);

        //現在日時の取得
        final Date d = new Date();

        Button post = findViewById(R.id.post);
        // ボタンをタップして非同期処理を開始
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param0 = "10" + "," + text_userID.getText().toString() + "," + sdf3.format(d) +
                "," + sdf4.format(d) + "," + text_area.getText().toString() + "," + sdf5.format(d);

                if(param0.length() != 0){
                    task_UploadTask = new UploadTask();
                    task_UploadTask.setListener(createListener());
                    task_UploadTask.execute(param0);
                }
            }
        });
        // ブラウザを起動する
        Button browser = findViewById(R.id.browser);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // phpで作成されたhtmlファイルへアクセス
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                // text clear
                text_Attendance.setText("");

            }
        });
        // 設定を起動する
        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントの準備
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                // サブ画面を表示する
                startActivityForResult(intent,FORM_REQUESTCODE);

            }
        });
        text_Attendance = findViewById(R.id.text_view);
    }

    @Override
    protected void onDestroy() {
        task_UploadTask.setListener(null);
        super.onDestroy();
        // 定期実行をcancelする
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private UploadTask.Listener createListener() {
        return new UploadTask.Listener() {
            @Override
            public void onSuccess(String result) {
                text_Attendance.setText(result);
            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // オプションメニューを作成する
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // コンテキストメニューを作成する
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle("コンテキストメニュー");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String str= "";

        // オプションメニュー
        switch (item.getItemId()){
            case R.id.item1 : str="アイテム1";break;
            case R.id.item2 : str="アイテム2";break;
            case R.id.item3 : str="アイテム3";break;
            case android.R.id.home : str="戻る";break;
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("情報")
                .setMessage(str +"が選択されました。")
                .setPositiveButton("OK", null)
                .show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String str= "";

        // コンテキストメニュー
        switch (item.getItemId()){
            case R.id.itemC1 : str="アイテム1";break;
            case R.id.itemC2  : str="アイテム2";break;
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("情報")
                .setMessage(str +"が選択されました。")
                .setPositiveButton("OK", null)
                .show();

        return super.onContextItemSelected(item);
    }
}

