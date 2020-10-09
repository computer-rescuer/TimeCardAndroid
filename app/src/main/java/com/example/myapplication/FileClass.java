package com.example.myapplication;
import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;

 import android.content.Context;
public class FileClass {

        Context c;

        public FileClass(Context ctx) {
            this.c = ctx;
        }

        void fileWrite(String text) {
            try {
                FileOutputStream file = c.openFileOutput("sample.txt",
                        Context.MODE_PRIVATE);
                file.write(text.getBytes());
                file.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }

        String fileRead() {
            String text = null;
            try {
                FileInputStream file = c.openFileInput("sample.txt");
                byte buffer[] = new byte[100];
                file.read(buffer);
                text = new String(buffer, "UTF-8");
                file.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }




            return text;
        }
    }


