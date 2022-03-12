package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnTmp = (Button) findViewById(R.id.btnSignup);
        btnTmp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText edtTmp;
        if (view.getId() == R.id.btnSignup) {
            edtTmp = (EditText) findViewById(R.id.txtUserSignup);
            String account = edtTmp.getText().toString();
            if (account.equals("")) {
                showMessage("Account can't be empty!");
                edtTmp.requestFocus();
                return;
            }
            edtTmp = (EditText) findViewById(R.id.txtPasswordSignup);
            String password = edtTmp.getText().toString();
            if (password.equals("")) {
                showMessage("Password can't be empty!");
                edtTmp.requestFocus();
                return;
            }
            saveAccount(account, password);
        }
    }

    private void saveAccount(String account, String password) {
        User Account = new User(account, password);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");

        DocumentReference docRef = usersRef.document(account);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        showMessage("Account already exists");
                        EditText edtTmp = (EditText) findViewById(R.id.txtUserSignup);
                        edtTmp.requestFocus();
                    } else {
//                        Log.d("TAG", "No such document");
                        docRef.set(Account);

                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }

    private void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Error");
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}