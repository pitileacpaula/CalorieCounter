package com.example.calorieccounter;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IstoricCalorii extends AppCompatActivity {
        ListView listView;
        private List<String> list=new ArrayList<>();
        private Map<String,Object> map=new HashMap<>();
        Button butonVizualizare;
        EditText dataVizualizarii;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_calorii);

        butonVizualizare =findViewById(R.id.buttonVizualizare);
        dataVizualizarii=findViewById(R.id.dataVizualizarii);
        String data=dataVizualizarii.getText().toString();
        listView=findViewById(R.id.listviewMancare);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();

        LocalDate localDate=LocalDate.now();
        DateTimeFormatter dateTimeFormatter2=DateTimeFormatter.ofPattern("dd MM");
        String formattedDate2= localDate.format(dateTimeFormatter2);

    }

    public void loadIstoric(View view)
    {
        String data=dataVizualizarii.getText().toString();
        System.out.println("DATA : "+ data);
        firebaseFirestore.collection("users").document(userID).collection("Zile").document(data).collection("AlimenteConsumate").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for(DocumentSnapshot snapshot : value)
                {
                    list.add( snapshot.getString("produs")+"       : " +snapshot.getString("calorii")+"  calorii     cantitate :" + snapshot.getString("cantitate"));
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item,list);
                arrayAdapter.notifyDataSetChanged();
                listView.setAdapter(arrayAdapter);
            }
        });

    }
}