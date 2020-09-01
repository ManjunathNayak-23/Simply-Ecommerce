package com.example.grocery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText fullName, Email, Password;
    FloatingActionButton Register;
    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_register);
        fullName = findViewById(R.id.registerName);
        Email = findViewById(R.id.registerEmail);
        Password = findViewById(R.id.registerPassword);
        Register = findViewById(R.id.registerBtn);
       progressBar  = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new RotatingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameOfUser = fullName.getText().toString();
                final String userEmail = Email.getText().toString();
                String userPassword = Password.getText().toString();
                if (nameOfUser.isEmpty()) {
                    fullName.setError("This Field Cannot Be Empty");
                }
                if (userEmail.isEmpty()) {
                    Email.setError("This Field Cannot Be Empty");
                }
                if (userPassword.isEmpty()) {
                    Password.setError("This Field Cannot Be Empty");
                }
                if (userPassword.length() < 6) {
                    Password.setError("Password Length Should Be More Than 6 Characters Long");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String userid = firebaseUser.getUid();

                                firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", nameOfUser);
                                hashMap.put("userEmail",userEmail);

                                firebaseDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                         //   progressBar.setVisibility(View.GONE);
                                        }

                                    }
                                });

                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Error.! Creating Account", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }

    public void LoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}