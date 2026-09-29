package com.example.securitylab;

import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveSensitiveDataPublicly();
    }

    private void saveSensitiveDataPublicly() {
        String sensitiveData = "UserSecretCredentials123!";

        // Get path to the public Downloads directory
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(publicDir, "shared_passwords.txt");

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(sensitiveData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}