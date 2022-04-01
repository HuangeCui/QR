package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
// can delete other player, but cannot delete self
public class DeleteUserActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private ListView userList;
    private ArrayList<String> userDataList;
    private ArrayAdapter<String> userAdapter;
    private int chosenLine=0;
   // private String user = "test";

    //不能删除自己


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        userList = findViewById(R.id.user_list);
        userDataList = new ArrayList<>();
        userAdapter=new ArrayAdapter<>(DeleteUserActivity.this,android.R.layout.simple_list_item_1,userDataList);
        userList.setAdapter(userAdapter);

        SharedData appData = (SharedData) getApplication();
        String user = appData.getUsername();

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");


       userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenLine=i;


            }
        });

       userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               appData.setUsername(userDataList.get(i));
               Log.e( "onItemClick: ",userDataList.get(i) );
               Intent intent=new Intent(DeleteUserActivity.this,UserCode.class);
               startActivity(intent);
               return true;
           }
       });


        Button btn =  findViewById(R.id.back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(DeleteUserActivity.this,ManageActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!user.equals(userDataList.get(chosenLine))){
                collectionReference
                        .document(userDataList.get(chosenLine))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("work", "Data has been deleted successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("not work", "Data could not be deleted!" + e.toString());
                            }
                        });

                userAdapter.notifyDataSetChanged();
                chosenLine=0;
            }
                else{
                    Log.e("cannot","this is own" );
                }
            }

        });


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                userDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                { String user = doc.getId();
                    userDataList.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }
        });

    }
}