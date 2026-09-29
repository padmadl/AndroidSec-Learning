package com.example.securitylab;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveSensitiveDataViaMediaStore(this);
    }

    private void saveSensitiveDataViaMediaStore(Context context) {
        String sensitiveData = "UserSecretCredentials123!";

        ContentValues values = new ContentValues();
        // Set filename and MIME type
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "backup_credentials.txt");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");

        // Set destination relative path in public storage (Downloads folder)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/AppBackups");
        }

        // Get the ContentResolver Uri for standard public Downloads/Files collection
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            collection = MediaStore.Files.getContentUri("external");
        }

        // Insert metadata record into MediaStore database to get the target Uri
        Uri itemUri = context.getContentResolver().insert(collection, values);

        if (itemUri != null) {
            try (OutputStream os = context.getContentResolver().openOutputStream(itemUri)) {
                if (os != null) {
                    os.write(sensitiveData.getBytes());
                    Toast.makeText(context, "Saved to MediaStore: " + itemUri.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}