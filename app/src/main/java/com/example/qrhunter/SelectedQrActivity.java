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
    boolean check =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_qr);
        Intent intent=getIntent();
        String qrid=intent.getStringExtra("qrid");

        check =intent.getBooleanExtra("check",false);

        Log.d(TAG, "从UserCode传递过来的参数"+qrid);
        db = FirebaseFirestore.getInstance();
        SharedData appData = (SharedData) getApplication();
        codeDisplay = appData.getCodedisplay();
        TextView qr = findViewById(R.id.txtQrcode);
        qr.setText(qrid);
        TextView textView;
        // who scanned this qrCode
        textView = findViewById(R.id.txtElse);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedQrActivity.this,WhoAlsoScan.class);
                intent.putExtra("qrid",qrid);
                startActivity(intent);
            }
        });

        //button = findViewById(R.id.btnCodeComment);
        //button.setOnClickListener(this);

        //delete
        Button deletebutton;
        deletebutton = findViewById(R.id.btnCodeDelete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==false){
                    deleteCode();
                }
                else{
                    showdetail();
                }
            }
        });


        //back按钮的功能
        Button backbutton;
        backbutton = findViewById(R.id.btnBackToCodeList);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (appData.getComefromme()) {
                    intent = new Intent(SelectedQrActivity.this, UserCode.class);
                } else {
                    intent = new Intent(SelectedQrActivity.this, SearchUserCode.class);
                }
                startActivity(intent);
            }
        });

        //comment button
        Button commbutton;
        commbutton = findViewById(R.id.btnCodeComment);
        commbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent comment = new Intent(SelectedQrActivity.this, CodeCommentActivity.class);
                comment.putExtra("qrid", qrid);
                startActivity(comment);
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
                mapdemo2.putExtra("strqrid",qrid);
                startActivity(mapdemo2);

            }
        });

    }
    private void showdetail(){
        android.app.AlertDialog dlg =new android.app.AlertDialog.Builder(SelectedQrActivity.this)
                .setTitle("Can not click")
                .setMessage("This is for owner to know the detail of code, if want operate the code, go to the profile")
                .setPositiveButton("Now I know that", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dlg.show();
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
                        Intent intent = new Intent(SelectedQrActivity.this,UserCode.class);
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