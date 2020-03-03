package com.example.servicedo.Pages.SignUpPage.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicedo.Config.DialogConfig;
import com.example.servicedo.Pages.HomePage.Controller.HomeActivity;
import com.example.servicedo.Pages.SignInPage.Controller.SignInActivity;
import com.example.servicedo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtRePassword;
    private Button btnSignUp;
    private TextView tvSignIn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUserName = findViewById(R.id.edt_user_name);
        edtPassword = findViewById(R.id.edt_password);
        edtRePassword = findViewById(R.id.edt_re_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        tvSignIn = findViewById(R.id.tv_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    public void handleSignUp(){
        String userName = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        auth = FirebaseAuth.getInstance();
        final DialogConfig dialogConfig = new DialogConfig(this);

        if (TextUtils.isEmpty(userName)) {
            dialogConfig.showAlertDialog(getString(R.string.dialog_empty_user_name));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            dialogConfig.showAlertDialog(getString(R.string.dialog_empty_password));
            return;
        }

        if (password.length() < 6) {
            dialogConfig.showAlertDialog(getString(R.string.dialog_password_least_6));
            return;
        }

        if(!edtPassword.getText().toString().equals(edtRePassword.getText().toString())){
            dialogConfig.showAlertDialog(getString(R.string.dialog_password_not_match_re_password));
            return;
        }

        //create user
        auth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            dialogConfig.showAlertDialog(getString(R.string.dialog_something_when_wrong));
                        } else {
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });
    }
}
