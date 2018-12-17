package com.example.madi.workhard2;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegister extends Activity implements View.OnClickListener{
    private EditText mRegisterLogin;
    private EditText mRegisterPassword;
    private EditText mRegisterConfPassword;

    private Button mRegButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
        mRegButton = findViewById(R.id.registration_button);
        mRegisterLogin = findViewById(R.id.register_login);
        mRegisterPassword = findViewById(R.id.register_password);
        mRegisterConfPassword = findViewById(R.id.register_confirm_password);

        mAuth = FirebaseAuth.getInstance();

        mRegButton.setOnClickListener(this);
    }

    public boolean checkConfPassword(){
        return mRegisterPassword.getText().toString().
                equals(mRegisterConfPassword.getText().toString());
    }

    @Override
    public void onClick(View v) {
        String login = mRegisterLogin.getText().toString();
        String password = mRegisterPassword.getText().toString();

        if (checkConfPassword()) {
            mAuth.createUserWithEmailAndPassword(login, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        showText("success");
                    }else {
                        showText(task.getException().toString());
                    }
                }
            });
        }else {
            Toast.makeText(this, "passwords does  not correspond", Toast.LENGTH_SHORT).show();
        }
    }

    private void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
