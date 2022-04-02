package com.example.qrhunter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class WhoAlsoScan extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayAdapter<HashMap> qrAdapter;
    ListView AlsoScanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_also_scan);
        Intent intent=getIntent();
        String qrid=intent.getStringExtra("qrid");
        Log.d(TAG, "从UserCode传递过来的参数"+qrid);
        db = FirebaseFirestore.getInstance();
        CollectionReference qrRef = db.collection("QRCodes");
        DocumentReference docQrRef = qrRef.document(qrid);
        AlsoScanList = findViewById(R.id.qr_also_list);
        docQrRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                ArrayList<HashMap> alsoScanList = (ArrayList<HashMap>) document.get("scanners");
                Log.d(TAG, "loooooooooool"+alsoScanList);

                qrAdapter = new ArrayAdapter<HashMap>(WhoAlsoScan.this, android.R.layout.simple_list_item_1,alsoScanList);
                AlsoScanList.setAdapter(qrAdapter);

            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}