package com.example.securitylab;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.security.Provider;
import java.security.Security;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SecurityProviders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSecurityProviders();
    }

    private void listSecurityProviders() {
        StringBuilder builder = new StringBuilder();
        for (Provider provider : Security.getProviders()) {
            builder.append("provider: ")
                    .append(provider.getName())
                    .append(" ")
                    .append(provider.getVersion())
                    .append(" (")
                    .append(provider.getInfo())
                    .append(")\n\n");
        }
        String providers = builder.toString();

        // 1. Output to Logcat
        Log.d(TAG, providers);

        // 2. Display on UI
        TextView tvProviders = findViewById(R.id.tv_providers);
        if (tvProviders != null) {
            tvProviders.setText(providers);
        }
    }
}