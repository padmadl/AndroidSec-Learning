package com.example.securitylab;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Store Sensitive data in plaintext SQLite Datbad=se
        SQLiteDatabase notSoSecure = openOrCreateDatabase("privateNotSoSecure", Context.MODE_PRIVATE, null);
        notSoSecure.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR, Password VARCHAR);");
        notSoSecure.execSQL("INSERT INTO Accounts VALUES('admin','AdminPass');");
        notSoSecure.execSQL("DELETE FROM Accounts WHERE Username='admin';");
        notSoSecure.close();

    }
}