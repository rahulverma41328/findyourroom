package com.example.findyourroom.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findyourroom.R;
import com.example.findyourroom.User.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    TextInputLayout email,password;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

    }
    private Boolean validateUsername(){

        String val = Objects.requireNonNull(email.getEditText()).getText().toString();
        if (val.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }
        else{
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private  Boolean validatePassword(){
        String val = Objects.requireNonNull(password.getEditText()).getText().toString();
        if (val.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void goToDashboard(View view) {
        if(!validateUsername() && !validatePassword()){
            return;
        }
        else
            isUser();
    }

    public void goToSignup(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUpScreen.class);
        startActivity(intent);
        finish();
    }
    private void isUser() {
        String _email = email.getEditText().getText().toString();
        String _password = password.getEditText().getText().toString();

            auth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null){
            Intent intent = new Intent(LoginScreen.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}