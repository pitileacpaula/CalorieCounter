package com.example.calorieccounter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.calorieccounter.register.AfisareDateProfil;
import com.example.calorieccounter.register.Produs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MainActivity2 extends AppCompatActivity {
    // Database insert hooks
    EditText insertdbprodus, insertdbcalorii;
    Button btndbinsert;
    Produs produs;
    DatabaseReference reff;
    TextView produsInserat,caloriiIserat;
    long maxid = 0;

    //Listview reading from database
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String result;
    DatabaseReference mref;
    FirebaseDatabase database;

    //Adding to istoric
    EditText cantitte;
    ArrayList<String> arrayList2;
    ArrayAdapter<String> arrayAdapter2;
    DatabaseReference reference2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    String TAG="aa";
    Button adaugaIstoric,activ;
    TextView totalAzi;
    TextView textView;

    Button button3,buton2;
    FirebaseFirestore firebaseFirestore2;
    FirebaseFirestore firebaseFirestore3;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserare_produse);
        {
            button3=findViewById(R.id.button3456);
            buton2=findViewById(R.id.button2);
            totalAzi=findViewById(R.id.totalAzi);

            //Database insert

            btndbinsert = findViewById(R.id.butonDBInsert2);

            produs = new Produs();

            reff = FirebaseDatabase.getInstance().getReference().child("Produs");
            reff.addValueEventListener(new ValueEventListener() {
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

            //adding to istoric
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseFirestore=FirebaseFirestore.getInstance();
            firebaseFirestore2=FirebaseFirestore.getInstance();
            userID=firebaseAuth.getCurrentUser().getUid();

            adaugaIstoric=findViewById(R.id.butonAdaugaProduse);
            cantitte=findViewById(R.id.cantitateInserata);


            //Timestamp timestamp=new Timestamp();



            int i=0;
            //List view retrrieving from database
            produsInserat=findViewById(R.id.produsAdaugat);
            caloriiIserat=findViewById(R.id.caloriidaugat);
            listView=findViewById(R.id.listViewProduse);
            arrayList=new ArrayList<>();
            arrayAdapter=new ArrayAdapter<>(MainActivity2.this, android.R.layout.simple_list_item_1,arrayList);
            mref=FirebaseDatabase.getInstance().getReference("Produs");
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String result=listView.getItemAtPosition(position).toString();
                    String parts[]=result.split(" ");
                    String part1=parts[0];
                    String part2=parts[1];
                    produsInserat.setText(part1);
                    caloriiIserat.setText(part2);

                }
            });

            mref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value=snapshot.getValue(Produs.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();

                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            LocalDate localDate=LocalDate.now();
            DateTimeFormatter dateTimeFormatter2=DateTimeFormatter.ofPattern("dd MM");
            String formattedDate2= localDate.format(dateTimeFormatter2);

            firebaseFirestore3=FirebaseFirestore.getInstance();
            DocumentReference documentReference11=firebaseFirestore3.collection("users").document(userID).collection("Zile").document(formattedDate2);
            documentReference11.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    totalAzi.setText(value.getString("calorii adunate"));
                    System.out.println("TOTAL "+totalAzi.getText().toString());
                    if (totalAzi.getText().toString().equals(""))
                    {
                        System.out.println("ESTE NULL");
                        totalAzi.setText("0");
                    }
                    else {
                        System.out.println("Nu este ");
                    }
                }
            });
            buton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MainActivity2.this, AfisareDateProfil.class);
                    startActivity(intent);
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MainActivity2.this,IstoricCalorii.class);
                    startActivity(intent);
                }
            });
            btndbinsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MainActivity2.this,AdaugaProdusNou.class);
                    startActivity(intent);

                }
            });

        }

    }

    int tot;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPr(View view){
        String pr=produsInserat.getText().toString();
        String cal=caloriiIserat.getText().toString();

        int calo=Integer.parseInt(cal);

        String cantitate=cantitte.getText().toString();
        int cantitater=Integer.parseInt(cantitate);

        int calorii=cantitater*calo;
        Integer calories=new Integer(calorii);
        String caloriiFinal=calories.toString();

        LocalDate localDate=LocalDate.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM");
        String formattedDate= localDate.format(dateTimeFormatter);
        DateTimeFormatter dateTimeFormatter2=DateTimeFormatter.ofPattern("dd MM");
        String formattedDate2= localDate.format(dateTimeFormatter2);


        Toast.makeText(MainActivity2.this,"Ati inserat "+produsInserat.getText().toString(),Toast.LENGTH_SHORT).show();

        String string=totalAzi.getText().toString();
        int int1=Integer.parseInt(string);


        tot=calories+int1;


        String result=Integer.toString(tot);
        totalAzi.setText(result);



        DocumentReference documentReference1=firebaseFirestore2.collection("users").document(userID).collection("Zile").document(formattedDate2);

        Map<String, Object> addd = new HashMap<>();
        addd.put("data", formattedDate);
        addd.put("calorii adunate", totalAzi.getText().toString());

        documentReference1.set(addd).addOnSuccessListener((OnSuccessListener) (aVoid) ->
        {
            Log.d(TAG, "succes ; " + userID);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "on faiulure :" + e.toString());
            }
        });


        DocumentReference documentReference=firebaseFirestore.collection("users").document(userID).collection("Zile").document(formattedDate2).collection("AlimenteConsumate").document();

        Map<String,Object> istoricMancare=new HashMap<>();
        istoricMancare.put("produs",produsInserat.getText().toString());
        istoricMancare.put("calorii",caloriiFinal);
        istoricMancare.put("cantitate",cantitate);


        documentReference.set(istoricMancare).addOnSuccessListener((OnSuccessListener) (aVoid) ->
        {
            Log.d(TAG,"succes ; "+ userID);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"on faiulure :"+ e.toString());
            }
        });

    }
}
