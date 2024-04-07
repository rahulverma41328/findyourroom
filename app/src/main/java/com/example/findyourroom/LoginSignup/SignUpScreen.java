package com.example.findyourroom.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findyourroom.Database.UserHelperClass;
import com.example.findyourroom.R;
import com.example.findyourroom.User.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUpScreen extends AppCompatActivity {

    TextInputLayout email, username, password;
    Button signUpBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        signUpBtn = findViewById(R.id.signup_btn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // all methods
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePassword()) {
                    return;
                }
                String _email = email.getEditText().getText().toString();
                String _password = password.getEditText().getText().toString();
                String _username = username.getEditText().getText().toString();
                auth.createUserWithEmailAndPassword(_email, _password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserHelperClass user = new UserHelperClass(_username, _password, _email);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Toast.makeText(SignUpScreen.this, "User created Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), CompleteProfile.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void goToLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" + "(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 5) {
            password.setError("password should have minimum 5 characters");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("one special character and number required");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}