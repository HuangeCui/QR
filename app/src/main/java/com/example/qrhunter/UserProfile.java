package com.example.qrhunter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//see others' profile
public class UserProfile extends AppCompatActivity {

    SharedData appData;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        appData = (SharedData) getApplication();
        String searchName = appData.getSearchname();
        TextView textView = (TextView) findViewById(R.id.txtUsername);
        textView.setText(searchName);
        Log.d(TAG, "!!!!!searchName!!!!!!!!!!"+searchName);

        Button btn = findViewById(R.id.btnUserBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button code = findViewById(R.id.btnUserCode);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,SearchUserCode.class);
                intent.putExtra("userName",searchName);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        TextView txtEmail = findViewById(R.id.txtEmail2);
        CollectionReference userRef = db.collection("Users");
        DocumentReference docUserRef = userRef.document(searchName);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                String userEmail = document.getString("userEmail");
                Log.d(TAG, "eeeeeeeeeeeemail"+userEmail);
                txtEmail.setText(userEmail);
            }
        });

        /*not work yet
        Button comment = findViewById(R.id.btnUserComment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, CodeCommentActivity.class);
                startActivity(intent);
            }
        });

         */
    }
}




        /*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUserCode:
                break;
            case R.id.btnUserComment:
                usercomment();
                break;
            case R.id.btnUserBack:
                back();
                break;
            default:
                break;
        }
    }

    private void usercomment() {
        Intent intent = new Intent(this, CodeCommentActivity.class);
        startActivity(intent);
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

         */