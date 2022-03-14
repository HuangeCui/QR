package com.example.qrhunter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.Comparator;

public class UserCode extends AppCompatActivity {

    FirebaseFirestore db;
    TextView textView;
    AlertDialog dialog;
    EditText editText;

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
        EditText txtEmail = findViewById(R.id.usercontact);


        CollectionReference userRef = db.collection("Users");
        DocumentReference docUserRef = userRef.document(username);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Long totalScore = document.getLong("sum");
                Long totalNumber = document.getLong("total");
                String userEmail = document.getString("userEmail");
                ArrayList<CodeScore> codeScoreList = (ArrayList<CodeScore>) document.get("codes");
                ArrayAdapter<CodeScore> codeAdapter;
                codeAdapter = new ArrayAdapter<CodeScore>(UserCode.this, android.R.layout.simple_list_item_1,codeScoreList);
                txtTotalScore.setText(totalScore.toString());
                txtNumber.setText(totalNumber.toString());
                txtEmail.setText(userEmail);
                codeList.setAdapter(codeAdapter);

                Button changeEmail = findViewById(R.id.change_email);
                changeEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = txtEmail.getText().toString();
                        docUserRef.update("userEmail", email);
                    }
                });

                /*
                Button high = findViewById(R.id.high_code);
                high.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Collections.sort(codeScoreList, new Comparator<CodeScore>() {
                            @Override
                            public int compare(CodeScore codeScore, CodeScore t1) {
                                int i = -1 * (codeScore.getScore()-t1.getScore());
                                return i;
                            }
                        });
                    }
                });

                */

            }
        });
        textView = (TextView) findViewById(R.id.usercontact);
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);
        dialog.setTitle("Please enter you contact email");
        dialog.setView(editText);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(editText.getText());

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(textView.getText());
                dialog.show();
            }
        });

        Button back = findViewById(R.id.back_to_profile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
    }
}
