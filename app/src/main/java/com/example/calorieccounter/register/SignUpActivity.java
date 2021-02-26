package com.example.calorieccounter.register;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorieccounter.MainActivity2;
import com.example.calorieccounter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText emailID,passwordID,nume,inaltime,greutate,varsta;
    RadioButton butonF, butonM;
    Button btnS;
    TextView tvS;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    private static final String TAG="signuo";

    String userID;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailID=findViewById(R.id.email);
        passwordID=findViewById(R.id.password);
        nume=findViewById(R.id.nume);
        inaltime=findViewById(R.id.Height);
        greutate=findViewById(R.id.Weight);
        varsta=findViewById(R.id.age);
        butonF=findViewById(R.id.radioButtonFemale);
        butonM=findViewById(R.id.radioButtonMale);
        btnS=findViewById(R.id.button_signup);
        tvS=findViewById(R.id.textView);
        database=FirebaseDatabase.getInstance();
        RadioGroup  radiog=findViewById(R.id.radioGroup);
        //reference=database.getReference(USER);
        mFirebaseAuth=FirebaseAuth.getInstance();
    firestore=FirebaseFirestore.getInstance();


        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String passwd = passwordID.getText().toString();
                String name=nume.getText().toString();
                String inaltimme=inaltime.getText().toString();
                String greutatte=greutate.getText().toString();
                String age=varsta.getText().toString();
                RadioButton selectedGender= findViewById(radiog.getCheckedRadioButtonId());
                String gender=selectedGender.getText().toString();
                String rezultatBMR;




            

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
                    Toast.makeText(SignUpActivity.this,"Campurile sunt goale!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(SignUpActivity.this,"Inregistrarea nu a reusit, incercati din nou!",Toast.LENGTH_SHORT).show();
                            }else {
                                userID=mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=firestore.collection("users").document(userID);
                                Map<String,Object> user= new HashMap<>();
                                user.put("email",email);
                                user.put("nume",name);
                                user.put("varsta",age);
                                user.put("inaltime",inaltimme);
                                user.put("greutate",greutatte);
                                user.put("sex",gender);

                                documentReference.set(user).addOnSuccessListener(( OnSuccessListener) (aVoid) ->
                                {
                                    Log.d(TAG,"succes ; "+ userID);
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"on faiulure :"+ e.toString());
                                    }
                                });
                                startActivity(new Intent(SignUpActivity.this, MainActivity2.class));

                            }
                        }
                    });
                }

                tvS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
        });



    }

}
