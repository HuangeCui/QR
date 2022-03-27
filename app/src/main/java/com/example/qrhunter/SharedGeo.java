package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SharedGeo extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_geo);

        Button back = findViewById(R.id.btnBackToScan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedGeo.this, SharedPicture.class);
                startActivity(intent);
            }
        });

        Button notBtn =findViewById(R.id.btntxt);
        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedGeo.this, UserCode.class);
                startActivity(intent);

            }
        });

        //update code geo
        Button share = findViewById(R.id.btnTake);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGeo();
                Intent intent = new Intent(SharedGeo.this, UserCode.class);
                startActivity(intent);

            }
        });


    }


    public void saveGeo() {
        // hash
//        db = FirebaseFirestore.getInstance();
//        SharedData appData = new SharedData();
//        HashScore hashScore = new HashScore();
//        CollectionReference codeRef = db.collection("QRCodes");
//        DocumentReference docCodeRef = codeRef.document(hashScore.hash256(appData.getQrcodekey()));
//
//        docCodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Map<String, Object> data = new HashMap<>();
//                    data.put("shared", true);
//                    docCodeRef.set(data, SetOptions.merge());
//                }
//            }
//        });
//    }
    }

}
