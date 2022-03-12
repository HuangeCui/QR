package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class DeleteCodesActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private ListView codeList;
    private ArrayList<String> codeDataList;
    private ArrayAdapter<String> codeAdapter;
    private int chosenLine=0;
    private String ID = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_codes);


        codeList = findViewById(R.id.codes_list);
        codeDataList = new ArrayList<>();
        //userAdapter = new CustomList(this, userDataList);
        codeAdapter=new ArrayAdapter<>(DeleteCodesActivity.this,android.R.layout.simple_list_item_1,codeDataList);
        codeList.setAdapter(codeAdapter);

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("QRCodes");


        codeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenLine=i;
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.equals(codeDataList.get(chosenLine))){
                    collectionReference
                            .document(codeDataList.get(chosenLine))
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

                    codeAdapter.notifyDataSetChanged();
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

                codeDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                { String code = doc.getId();
                    codeDataList.add(code);
                }
                codeAdapter.notifyDataSetChanged();
            }
        });

    }
}