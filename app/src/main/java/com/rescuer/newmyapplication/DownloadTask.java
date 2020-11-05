package com.rescuer.newmyapplication;

//package your.package.name;

import android.content.Context;

import android.os.AsyncTask;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private Listener listener;
    String OUT_FILE_NAME = "DownloadTask.csv";
    @NonNull private final Context context;
    public DownloadTask(@NonNull Context context) { this.context = context; }

    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //  @Override
    protected String doInBackground(String... params) {
        String urlSt = "http://" + params[1] + "/syain/file_dir/pass_list.csv";

        // 使用するサーバーのURLに合わせる
        String result = null;
        try {
            // URL設定
            URL url = new URL(urlSt);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
// HTTPのメソッドをGETに
            urlCon.setRequestMethod("GET");
// 接続
            urlCon.connect();
// HTTPレスポンスコード
            final int status = urlCon.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {

                // 通信に成功した
                // ファイルのダウンロード処理を実行
                // 読み込み用ストリーム
                final InputStream input = urlCon.getInputStream();
                final DataInputStream dataInput = new DataInputStream(input);
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
                String wk_line;
                StringBuilder wk_txt = new StringBuilder();
                while ((wk_line = reader.readLine()) != null) {
                    wk_txt.append(wk_line);
                    wk_txt.append("\n");
                }
                try (final OutputStream os = context.openFileOutput(OUT_FILE_NAME, Context.MODE_PRIVATE);
                     final OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                     final PrintWriter writer = new PrintWriter(osw)) {
                    writer.write(wk_txt.toString());
                }
                // 各ストリームを閉じる
                reader.close();
                result = "HTTP_OK";
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    // 非同期処理が終了後、結果をメインスレッドに返す

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String result);
    }
}