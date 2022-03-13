package com.example.qrhunter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserCode extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercode);

        // get the name of the user and show on the top
        SharedData appData = (SharedData) getApplication();
        TextView txtUsername = findViewById(R.id.txtUsername);
        String username = appData.getUsername();
        txtUsername.setText(username);

        db = FirebaseFirestore.getInstance();
        TextView txtTotalScore = findViewById(R.id.txtTotalScore);
        TextView txtNumber = findViewById(R.id.txtNumber);
        ListView codeList = findViewById(R.id.code_list);


        CollectionReference userRef = db.collection("Users");
        DocumentReference docUserRef = userRef.document(username);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Long totalScore = document.getLong("sum");
                Long totalNumber = document.getLong("total");
                ArrayList<CodeScore> codeScoreList = (ArrayList<CodeScore>) document.get("codes");
                ArrayAdapter<CodeScore> codeAdapter;
                codeAdapter = new ArrayAdapter<CodeScore>(UserCode.this, android.R.layout.simple_list_item_1,codeScoreList);
                txtTotalScore.setText(totalScore.toString());
                txtNumber.setText(totalNumber.toString());
                codeList.setAdapter(codeAdapter);
            }
        });
    }
}
