package com.example.vulnerable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    // BAD: broadcast sensitive information to all listeners
    public void sendBroadcast1(Context context, String token, String refreshToken) {
        Intent intent = new Intent();
        intent.setAction("com.example.custom_action");
        intent.putExtra("token", token);
        intent.putExtra("refreshToken", refreshToken);
        context.sendBroadcast(intent);
    }


}
