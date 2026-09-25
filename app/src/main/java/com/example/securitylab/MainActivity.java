package com.example.securitylab;



import android.content.Context;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;



import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {



    private EditText inputKey, inputValue;

    private Button btnSave, btnRead;



// Key used to store privacy consent state

    private static final String PREF_PRIVACY_CONSENT = "user_privacy_consent";



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



// Bind UI elements to layout IDs

        inputKey = findViewById(R.id.input_key);

        inputValue = findViewById(R.id.input_value);

        btnSave = findViewById(R.id.btn_save);

        btnRead = findViewById(R.id.btn_read);



// Check if user consent has been handled before initializing third-party services

        checkAndHandlePrivacyConsent();



// Save data to SharedPreferences (Insecure Plaintext Storage)

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String key = inputKey.getText().toString();

                String value = inputValue.getText().toString();



                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(key, value);

                editor.apply();



                Toast.makeText(MainActivity.this, "Saved to SharedPreferences!", Toast.LENGTH_SHORT).show();

            }

        });



// Read data from SharedPreferences

        btnRead.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String key = inputKey.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                String value = sharedPref.getString(key, "Not Found");



                Toast.makeText(MainActivity.this, "Retrieved Value: " + value, Toast.LENGTH_LONG).show();

            }

        });

    }



    /**

     * Checks if the user has made a privacy choice.

     * Shows a consent dialog if no decision has been recorded yet.

     */

    private void checkAndHandlePrivacyConsent() {

        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);



        if (!sharedPref.contains(PREF_PRIVACY_CONSENT)) {

// Show consent dialog on first launch

            showPrivacyConsentDialog(sharedPref);

        } else {

            boolean consentGiven = sharedPref.getBoolean(PREF_PRIVACY_CONSENT, false);

            if (consentGiven) {

                initializeThirdPartySDKs();

            }

        }

    }



    /**

     * Displays an explicit consent dialog to the user before enabling third-party services.

     */

    private void showPrivacyConsentDialog(final SharedPreferences sharedPref) {

        new AlertDialog.Builder(this)

                .setTitle("Privacy & Data Usage")

                .setMessage("We use third-party services to collect diagnostic and usage data to improve app stability. Do you consent to this data collection?")

                .setPositiveButton("Accept", (dialog, which) -> {

// Save user decision

                    sharedPref.edit().putBoolean(PREF_PRIVACY_CONSENT, true).apply();

// Initialize services now that explicit consent is obtained

                    initializeThirdPartySDKs();

                })

                .setNegativeButton("Decline", (dialog, which) -> {

// Save user decision

                    sharedPref.edit().putBoolean(PREF_PRIVACY_CONSENT, false).apply();

                    Toast.makeText(MainActivity.this, "Analytics disabled.", Toast.LENGTH_SHORT).show();

                })

                .setCancelable(false)

                .show();

    }



    /**

     * Entry point for initializing third-party libraries (e.g., Firebase Analytics, App Center, Crashlytics).

     * This will only run if the user gives explicit consent.

     */

    private void initializeThirdPartySDKs() {

// TODO: Place third-party SDK initialization code here.

// Example:

// FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);

        Toast.makeText(this, "Third-party services initialized.", Toast.LENGTH_SHORT).show();

    }

}