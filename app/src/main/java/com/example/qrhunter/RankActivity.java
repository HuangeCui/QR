package com.example.qrhunter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    Button amount,highest,sum,unique;
    Intent intent01,intent02,intent03,intent04;
    private ArrayList<User> userDataList;
   // FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        userDataList = new ArrayList<>();
        //initData();




        amount = findViewById(R.id.amount_rank_button);
        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent01 = new Intent(RankActivity.this, RankAmount.class);
                //final Bundle bundle = new Bundle();
                //bundle.putSerializable("userDataList",userDataList);
                //intent01.putExtras(bundle);
                startActivity(intent01);

            }
        });

        highest = findViewById(R.id.highest_rank_button);
        highest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent02 = new Intent(RankActivity.this, RankHighest.class);
                //final Bundle bundle = new Bundle();
                //bundle.putSerializable("userDataList",userDataList);
                //intent02.putExtras(bundle);
                startActivity(intent02);
            }
        });


        sum = findViewById(R.id.sum_rank_button);
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent03 = new Intent(RankActivity.this, RankSum.class);
                //final Bundle bundle = new Bundle();
               // bundle.putSerializable("userDataList",userDataList);
                //intent03.putExtras(bundle);
                startActivity(intent03);
            }
        });

        unique = findViewById(R.id.unique_rank_button);
        unique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent04 = new Intent(RankActivity.this, RankUnique.class);
                //final Bundle bundle = new Bundle();
               // bundle.putSerializable("userDataList",userDataList);
                //intent04.putExtras(bundle);
                startActivity(intent04);
            }
        });


    }


    /**public void initData() {
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("UserList");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                userDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    final ArrayList<QRCode> codes = new ArrayList<>();
                    final CollectionReference second = db.collection("UserList").document("" + doc.getId()).collection("QRCode");
                    second.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable
                                FirebaseFirestoreException error) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                String codeId = doc.getId();
                                //String score =(String)doc.getData().get("score");
                                //int codeScore = Integer.parseInt(score);
                                int codeScore =11;
                                codes.add(new QRCode(codeId,codeScore));
                            }
                        }
                    });
                    Log.e("name", doc.getId() );
                    String city = doc.getId();
                    String province = (String) doc.getData().get("ID");
                    userDataList.add(new User(city, province, codes));

                }
            }
        });}**/
}