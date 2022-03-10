package com.example.qrhunter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
    private CustomList customList;
    final String TAG = "Sample";
    private int chosenLine = 0;
    private String userId="UserName";
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_amount);

        userList = findViewById(R.id.amount_rank_list);
        userDataList = new ArrayList<>();
        userAdapter = new CustomList(this, userDataList);
        userList.setAdapter(userAdapter);
        userDataList=initData();
        Log.e(TAG,String.valueOf(userDataList.size()) );

        //userAdapter.notifyDataSetChanged();


       if(userDataList.size()>=2){
           Collections.sort(userDataList, new Comparator<User>() {
               @Override
               public int compare(User user, User user2) {
                   if(user.getTotal()>user.getTotal()){
                       return 1;
                   } else{
                       return -1;
                   }
               }
           });
       }






        /**TextView content = findViewById(R.id.user_position01);
        for(int i = 0; i<userDataList.size();i++){
            if(userDataList.get(i).getUserID()==userId){
                content.setText("User Rank "+i);
                break;
            }

        }**/






    }


    public ArrayList<User> initData() {
        ArrayList<User> temp = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("UserList");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
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
                   // Log.e("name", doc.getId() );
                    String city = doc.getId();
                    String province = (String) doc.getData().get("ID");
                    temp.add(new User(city, province, codes));


                }
            }
        });
        Log.e("name", String.valueOf(temp.size()) );
        return temp;
    }
}