package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class CodeCommentActivity extends AppCompatActivity implements View.OnClickListener {
    String codeDisplay;
    FirebaseFirestore db;

    final String TAG = "CodeCommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codecomment);

        db = FirebaseFirestore.getInstance();
        SharedData appData = (SharedData) getApplication();
        codeDisplay = appData.getCodedisplay();

        Button button;
        button = findViewById(R.id.btnAddCodeComment);
        button.setOnClickListener(this);
        button = findViewById(R.id.btnBackToCodeImage);
        button.setOnClickListener(this);

        displayInformation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddCodeComment:
                doAddComment();
                break;
            case R.id.btnBackToCodeImage:
                goBack();
                break;
            default:
                break;
        }
    }

    private void displayInformation() {
        CollectionReference codesRef = db.collection("QRCodes");
        DocumentReference docCodeRef = codesRef.document(codeDisplay);
        docCodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String comment = document.getString("Comment");
                    if (comment != null) {
                        EditText editText = findViewById(R.id.txtCodeComment);
                        editText.setText(comment);
                    }
                    Log.d(TAG, "User documents write success. ");
                } else {
                    Log.d(TAG, "Error getting user documents: ", task.getException());
                }
            }
        });
    }

    private void doAddComment() {
        CollectionReference codesRef = db.collection("QRCodes");
        DocumentReference docCodeRef = codesRef.document(codeDisplay);
        docCodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    EditText editText = findViewById(R.id.txtCodeComment);
                    String comment = editText.getText().toString();
                    if (comment != null) {
                        HashMap<String, String> data = new HashMap<>();
                        data.put("Comment", comment);
                        docCodeRef.set(data, SetOptions.merge());
                    }

                    Log.d(TAG, "User documents write success. ");
                } else {
                    Log.d(TAG, "Error getting user documents: ", task.getException());
                }
            }
        });
    }

    private void goBack() {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }
}