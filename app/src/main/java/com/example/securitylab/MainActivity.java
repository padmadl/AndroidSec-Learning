package com.example.securitylab;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// Import SQLCipher classes, NOT standard android.database.sqlite classes
import net.sqlcipher.database.SQLiteDatabase;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize SQLCipher native libraries
        SQLiteDatabase.loadLibs(this);

        // 2. Define the database file path
        File databaseFile = getDatabasePath("securePrivate.db");
        databaseFile.getParentFile().mkdirs();

        // 3. Open or create the encrypted database with a password
        SQLiteDatabase secureDB = SQLiteDatabase.openOrCreateDatabase(
                databaseFile,
                "password123", // Encryption key / passphrase
                null
        );

        // 4. Perform database operations (Data is automatically encrypted on disk)
        secureDB.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR, Password VARCHAR);");
        secureDB.execSQL("INSERT INTO Accounts VALUES('admin','AdminPassEnc');");

        // 5. Close database connection
        secureDB.close();
    }
}