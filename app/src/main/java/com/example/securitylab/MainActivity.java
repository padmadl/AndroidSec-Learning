package com.example.securitylab;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BiometricAuth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBiometricAvailability();
    }

    private void checkBiometricAvailability() {
        BiometricManager biometricManager = BiometricManager.from(this);

        // Define authenticators using bitmask flags (e.g., Class 3 Biometrics OR Device PIN/Pattern/Password)
        int authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG ;

        // Check if the device can authenticate using the specified authenticators
        int canAuthenticate = biometricManager.canAuthenticate(authenticators);

        switch (canAuthenticate) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics or device credentials.");
                Toast.makeText(this, "Biometrics / Credentials Available", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(TAG, "No biometric features available on this device.");
                Toast.makeText(this, "No Biometric Hardware Available", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(TAG, "Biometric features are currently unavailable.");
                Toast.makeText(this, "Biometric Hardware Unavailable", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.w(TAG, "The user has not enrolled any biometrics or credentials.");
                Toast.makeText(this, "No Biometrics or Credentials Enrolled", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                Log.e(TAG, "A security vulnerability was found and an update is required.");
                break;

            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                Log.e(TAG, "Requested authenticator combination is unsupported.");
                break;

            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                Log.e(TAG, "Unable to determine biometric status.");
                break;
        }
    }
}