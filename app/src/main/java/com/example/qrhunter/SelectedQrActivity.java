package com.example.qrhunter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectedQrActivity extends AppCompatActivity {
    String codeDisplay;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_qr);
        Intent intent=getIntent();
        String qrid=intent.getStringExtra("qrid");
        Log.d(TAG, "从UserCode传递过来的参数"+qrid);
        db = FirebaseFirestore.getInstance();
        SharedData appData = (SharedData) getApplication();
        codeDisplay = appData.getCodedisplay();

        TextView textView;

        textView = findViewById(R.id.txtElse);
        //textView.setOnClickListener(this);

        //button = findViewById(R.id.btnCodeComment);
        //button.setOnClickListener(this);

        //delete按钮的功能
        Button deletebutton;
        deletebutton = findViewById(R.id.btnCodeDelete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCode();
            }
        });


        //back按钮的功能
        Button backbutton;
        backbutton = findViewById(R.id.btnBackToCodeList);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //displayCodeInformation();
        Long score = intent.getLongExtra("score", 0);
        TextView scoretxt = findViewById(R.id.txtCodeScore);
        scoretxt.setText("Score: " + score.toString());

        //display geolocation
        Button codeLocation = findViewById(R.id.txtCodeLocation);
        codeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapdemo2 = new Intent(SelectedQrActivity.this, MapDemo2.class);
                startActivity(mapdemo2);
                mapdemo2.putExtra("strqrid",qrid);

            }
        });

    }


    //删除代码
    private void deleteCode() {
        Intent intent = getIntent();
        Integer i = intent.getIntExtra("index", 0);
        //String i = intent.getStringExtra("index");
        SharedData appData = (SharedData) getApplication();
        String username = appData.getUsername();
        CollectionReference userRef = db.collection("Users");
        DocumentReference docUserRef = userRef.document(username);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Long totalNumber = document.getLong("total");
                ArrayList<HashMap> tmp_codeScoreList = (ArrayList<HashMap>) document.get("codes");
                ArrayList<CodeScore> codeScoreList = new ArrayList<>();

                for(int i=0;i<tmp_codeScoreList.size();i++){
                    CodeScore tmp = new CodeScore((String)tmp_codeScoreList.get(i).get("code"),((Long)tmp_codeScoreList.get(i).get("score")).intValue());
                    codeScoreList.add(tmp);
                }
                //codeScoreList.remove(codeScoreList.get(i));

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedQrActivity.this);
                builder.setMessage("Are you sure to delete this code?");
                builder.setTitle("Information");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete
                        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + " deleting code xdxdxdxdxdxdxdxdxdxdxdxd");
                        codeScoreList.remove(codeScoreList.get(i));
                        docUserRef.update("codes",codeScoreList);
                        docUserRef.update("total",totalNumber-1);
                        Intent intent = new Intent(SelectedQrActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}