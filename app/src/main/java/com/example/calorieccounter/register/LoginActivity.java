package com.example.calorieccounter.register;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorieccounter.MainActivity2;
import com.example.calorieccounter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailID,passwordID;
    Button btnL;
    TextView tvL;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailID=findViewById(R.id.email);
        passwordID=findViewById(R.id.password);
        btnL=findViewById(R.id.button_signup);
        tvL=findViewById(R.id.textView);

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser  mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this,"Sunteti conectat!" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity2.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Va rugam sa va conectati!" , Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailID.getText().toString();
                String passwd=passwordID.getText().toString();
                if(email.isEmpty())
                {
                    emailID.setError("Introdu adresa de email");
                    emailID.requestFocus();
                }
                else if (passwd.isEmpty())
                {
                    passwordID.setError("Introdu parola");
                    passwordID.requestFocus();
                }
                else if(email.isEmpty() && passwd.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Campurile sunt goale!",Toast.LENGTH_SHORT).show();
                }
                else if ( ! (email.isEmpty() && passwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this,"Eroare la conectare, va rugam sa incercati din nou!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intToHome= new Intent(LoginActivity.this, AfisareDateProfil.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"A aparut o problema!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin= new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intLogin);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}