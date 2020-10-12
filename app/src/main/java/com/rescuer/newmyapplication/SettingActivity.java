package com.rescuer.newmyapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {
    EditText et;

    private final int FORM_REQUESTCODE = 1000;
    private TextView textView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private String  PlusText1;
    private String  PlusText2;
    private String  PlusText3;
    private String  PlusText4;
    private String  PlusText5;
    private String  PlusText6;
    private String  PlusText7;
    private String  PlusText;

    private String fileName = "UserDate.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        /** Called when the activity is first created. */

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        // インテントの準備
                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent, FORM_REQUESTCODE);
                        return true;
                    case R.id.item2:
                        // インテントの準備
                        Intent intent2 = new Intent(SettingActivity.this, RestActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent2, FORM_REQUESTCODE);
                        return true;
                    case R.id.item3:
                        // インテントの準備
                        Intent intent3 = new Intent(SettingActivity.this, WorkActivity.class);
                        // サブ画面を表示する
                        startActivityForResult(intent3, FORM_REQUESTCODE);
                        return true;
                    case R.id.item4:
                        // インテントの準備
                        Intent intent4 = new Intent(SettingActivity.this, SettingActivity.class);
                        // サブ画面を表示するMainActivity
                        startActivityForResult(intent4, FORM_REQUESTCODE);
                        return true;
                }
                return false;
            }
        });

        textView = findViewById(R.id.text_view);
        editText1 = findViewById(R.id.userID_form);
        editText2 = findViewById(R.id.password_form);
        editText3 = findViewById(R.id.name_form);
        editText4 = findViewById(R.id.ID_form);
        editText5 = findViewById(R.id.area1_form);
        editText6 = findViewById(R.id.area2_form);
        editText7 = findViewById(R.id.area3_form);



        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PlusText1= editText1.getText().toString();
                PlusText2= editText2.getText().toString();
                PlusText3= editText3.getText().toString();
                PlusText4= editText4.getText().toString();
                PlusText5= editText5.getText().toString();
                PlusText6= editText6.getText().toString();
                PlusText7= editText7.getText().toString();
                PlusText = PlusText1 + "," + PlusText2 + "," + PlusText3 + "," + PlusText4
                + "," + PlusText5 + "," + PlusText6 + "," + PlusText7;
                // エディットテキストのテキストを取得
                String text = PlusText;

                saveFile(fileName, text);
                if(text.length() == 0){
                    textView.setText(R.string.no_text);
                }
                else{
                    textView.setText(R.string.saved);
                }
            }
        });

        Button buttonRead = findViewById(R.id.button_read);
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String str = readFile(fileName);
                if (str != null) {
                    textView.setText(str);
                } else {
                    textView.setText(R.string.read_error);
                }
            }
        });

    }

    // ファイルを保存
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveFile(String file, String str) {

        // try-with-resources
        try (FileOutputStream  fileOutputstream = openFileOutput(file,
                Context.MODE_PRIVATE);){

            fileOutputstream.write(str.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ファイルを読み出し
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String readFile(String file) {
        String text = null;

        // try-with-resources
        try (FileInputStream fileInputStream = openFileInput(file);
             BufferedReader reader= new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {

            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                text = lineBuffer ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    class InputClick implements OnClickListener{
        @Override
        public void onClick(View v) {

            String str = et.getText().toString();
            try{
                FileOutputStream out = openFileOutput( "test.txt", MODE_PRIVATE );
                out.write( str.getBytes()   );
            }catch( IOException e ){
                e.printStackTrace();
            }
        }
    }

    class OutputClick implements OnClickListener{
        @Override
        public void onClick(View v) {

            try{
                FileInputStream in = openFileInput( "test.txt" );
                BufferedReader reader = new BufferedReader( new InputStreamReader( in , "UTF-8") );
                String tmp;
                et.setText("");
                while( (tmp = reader.readLine()) != null ){
                    et.append(tmp + "\n");
                }
                reader.close();
            }catch( IOException e ){
                e.printStackTrace();
            }
        }
    }
    class DeleteClick implements OnClickListener{
        @Override
        public void onClick(View v) {
            deleteFile( "test.txt" );
        }
    }

    class InClick implements OnClickListener{
        @Override
        public void onClick(View v) {
            // インテントの準備
            Intent intent = new Intent(SettingActivity.this,MainActivity.class);

            // サブ画面に渡す値
            //   intent.putExtra("main_text",et.getText().toString());

            // サブ画面を表示する
            startActivityForResult(intent,FORM_REQUESTCODE);
        }
    }
}