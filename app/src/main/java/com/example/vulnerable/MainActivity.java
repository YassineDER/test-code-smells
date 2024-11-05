package com.example.vulnerable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.editTextUsername);
        TextView textViewPassword = findViewById(R.id.textViewPassword);
        Button buttonGeneratePassword = findViewById(R.id.buttonGeneratePassword);
        Button buttonShare = findViewById(R.id.buttonSave);

        String generatedPassword = Utils.generatePassword();
        buttonGeneratePassword.setOnClickListener(v ->
                textViewPassword.setText(String.format("Generated Password: %s", generatedPassword)));

        buttonShare.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            shareCredentials(username, generatedPassword);
        });

        String url = "jdbc:mysql://localhost/test";
        String u = "admin"; // hard-coded credential

        try {
            getConn(url, u, "password"); // hard-coded credential
        } catch (SQLException e) {
            // Throw toast message
            Toast.makeText(this, "Failed to connect to the database", Toast.LENGTH_SHORT).show();
        }
    }

    // Implicit intent to share information that could be intercepted by other apps.
    private void shareCredentials(String username, String password) {
        String message = "Username: " + username + "\nPassword: " + password;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Save"));
    }

    // Hard-coded credentials in the source code.
    private void getConn(String url, String u, String p) throws SQLException {
        DriverManager.getConnection(url, u, p);
    }



}
