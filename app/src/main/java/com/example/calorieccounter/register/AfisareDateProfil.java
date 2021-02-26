package com.example.calorieccounter.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.calorieccounter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AfisareDateProfil extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView emaill,nume,varsta,inaltime,greutate,sex,BMRdisplay;
    RadioGroup radioGroup;
    RadioButton selectedGender;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afisare_date_profil);
        emaill= findViewById(R.id.emaildisplay);
        nume=findViewById(R.id.numeDisplay);
        varsta=findViewById(R.id.varstaDisplay);
        inaltime=findViewById(R.id.inaltimeDisplay);
        greutate=findViewById(R.id.greutateDisplay);
        sex=findViewById(R.id.sexDisplay);
        BMRdisplay=findViewById(R.id.BMRdisplay);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                emaill.setText(value.getString("email"));
                nume.setText(value.getString("nume"));
                varsta.setText(value.getString("varsta"));
                inaltime.setText(value.getString("inaltime"));
                greutate.setText(value.getString("greutate"));
                sex.setText(value.getString("sex"));

            }
        });

        btnLogout= findViewById(R.id.butonLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(AfisareDateProfil.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }

}