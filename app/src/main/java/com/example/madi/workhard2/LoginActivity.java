package com.example.madi.workhard2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mLogin;
    private EditText mPassword;
    private FirebaseAuth mAuth;

    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        mAuth = FirebaseAuth.getInstance();
        checkCurrentUser();
        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
        }
    }

    private void initUI() {
        mLoginButton = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.register_button);

        mLogin = findViewById(R.id.user_name);
        mPassword = findViewById(R.id.user_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                Intent intent = new Intent(this, ActivityRegister.class);
                startActivity(intent);
            case R.id.login_button:
                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(login, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showText("successfully signed in");
                            startTheMovieDB();
                        }else{
                            showText(task.getException().toString());
                        }
                    }
                });
        }
    }

    private void startTheMovieDB() {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    private void showText(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
