package com.example.qrhunter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankAmount extends AppCompatActivity {
    private ListView userList;
    private ArrayAdapter<User> userAdapter;
    private ArrayList<User> userDataList;
    private int chosenLine = 0;
    FirebaseFirestore db;
    TextView content;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_amount);

        content = findViewById(R.id.user_position01);
        userList = findViewById(R.id.amount_rank_list);
        userDataList = new ArrayList<>();

        SharedData appData = (SharedData) getApplication();
        userId = appData.getUsername();


        Button btn =  findViewById(R.id.back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(RankAmount.this,RankActivity.class);
               // startActivity(intent);
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                userDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String city = doc.getId();
                    long  total = (long) doc.getData().get("total");
                    int amount=(int)total;
                    userDataList.add(new User(city, amount));
                    Log.e("total",""+amount);
                }

                 userDataList = sortTotal(userDataList);
                 userAdapter = new CustomList(RankAmount.this, userDataList);
                 userList.setAdapter(userAdapter);
                 String str =findTotal(userDataList, userId);
                 content.setText(str);



            }
        });









    }

    public ArrayList<User> sortTotal(ArrayList<User> userDataList){
        ArrayList<User> list = new ArrayList<>();
        Collections.sort(userDataList, new Comparator<User>() {
            @Override
            public int compare(User user, User user2) {
                int i=-1*(user.getTotal()-user2.getTotal());
                return i;
            }
        });
        list = userDataList;
        return list;
    }

    public String findTotal(ArrayList<User> userDataList, String userId){
        String str ="";
        for(int i = 0; i< userDataList.size();i++){
            if( userDataList.get(i).getUserName().equals(userId)){
                int index = i+1;
                str = "User " +userId + " Rank " +index;
                break; }
        }
        return str;

    }






}