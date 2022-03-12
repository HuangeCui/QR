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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button btnTmp;
        TextView txtTmp;
        btnTmp = (Button) findViewById(R.id.btnSignin);
        btnTmp.setOnClickListener(this);
        txtTmp = (TextView) findViewById(R.id.txtSignup);
        txtTmp.setOnClickListener(this);
        txtTmp = (TextView) findViewById(R.id.txtSigninQRCode);
        txtTmp.setOnClickListener(this);

        // read from local, auto sign in
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                signin();
                break;
            case R.id.txtSignup:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.txtSigninQRCode:
                break;
            default:
                break;
        }

    }

    private void signin() {
        final EditText edtTmp1;
        final EditText edtTmp2;

        edtTmp1 = (EditText) findViewById(R.id.txtAccount);
        String account = edtTmp1.getText().toString();
        if (account.equals("")) {
            showMessage("Account can't be empty!");
            edtTmp1.requestFocus();
            return;
        }
        edtTmp2 = (EditText) findViewById(R.id.txtPassword);
        String password = edtTmp2.getText().toString();
        if (password.equals("")) {
            showMessage("Password can't be empty!");
            edtTmp2.requestFocus();
            return;
        }

        SharedData appData = (SharedData) getApplication();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");
        usersRef.whereEqualTo("userName", account).whereEqualTo("userPasscode", password)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        showMessage("Account or password error");
                        edtTmp1.requestFocus();
                        return;
                    } else {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                        User user = new User(account,password);
                        appData.setUser(new User(account,password));
                       Log.e("TAG", user.getUserName());
//                        Log.d("TAG", "onComplete1: " + " => " + document.toObject(User.class));
//                        Log.d("TAG", "onComplete2: " + " => " + appData.getUser().getName() + "  " + appData.getUser().getPassword());
                        CheckBox chkTmp = (CheckBox) findViewById(R.id.chkRemember);
                        if (chkTmp.isChecked()) {
                            // save to local
                        }
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        startActivity(intent);
//                    }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
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