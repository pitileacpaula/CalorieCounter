package com.example.calorieccounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calorieccounter.register.Produs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdaugaProdusNou extends AppCompatActivity {
    EditText produsNume,calorii;
    Button button;
    DatabaseReference reference;
    long maxid = 0;
    Produs produs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_produs_nou);

        produsNume=findViewById(R.id.adaugaProdus);
        calorii=findViewById(R.id.adaugaCalorii);
        button=findViewById(R.id.butonAdaugaProdusDB);

        produs = new Produs();

        reference=FirebaseDatabase.getInstance().getReference().child("Produs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (snapshot).getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int calorii2 = Integer.parseInt(calorii.getText().toString().trim());
                produs.setCalorii(calorii2);
                produs.setNume(produsNume.getText().toString().trim());
                reference.child(String.valueOf(maxid + 1)).setValue(produs);
                Toast.makeText(AdaugaProdusNou.this, "Ati inserat produsul cu succes!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}