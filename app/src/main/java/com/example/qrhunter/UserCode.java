package com.example.qrhunter;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
//see user's profile
public class UserCode extends AppCompatActivity {

    FirebaseFirestore db;
    TextView textView;
    AlertDialog dialog;
    EditText editText;
    SharedData appData;
    String username;
    private int chosenLine=0;
    Button buttonhighest;
    Button buttonlowest;
    String highestscore="";
    String lowestscore="";
    ArrayAdapter<CodeScore> codeAdapter;
    ArrayList<CodeScore> codeScoreList;
    TextView txtTotalScore;
    TextView txtNumber ;
    ListView codeList ;
    EditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercode);

        // get the name of the user and show on the top

        SharedData appData = (SharedData) getApplication();
        TextView txtUsername = findViewById(R.id.txtUsername);
        username = appData.getUsername();
        txtUsername.setText(username);


    /*
        appData = (SharedData) getApplication();
        String searchName = appData.getSearchname();
        TextView textView = (TextView) findViewById(R.id.txtUsername);
        textView.setText(searchName);
    */
        db = FirebaseFirestore.getInstance();

        txtTotalScore = findViewById(R.id.txtTotalScore);
        txtNumber = findViewById(R.id.txtNumber);
        codeList = findViewById(R.id.code_list);
        txtEmail = findViewById(R.id.usercontact);


        CollectionReference userRef = db.collection("Users");
        DocumentReference docUserRef = userRef.document(username);
        //DocumentReference docUserRef = userRef.document(searchName);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Long totalScore = document.getLong("sum");
                Long totalNumber = document.getLong("total");
                String userEmail = document.getString("userEmail");
                //ArrayList<CodeScore> codeScoreList = (ArrayList<CodeScore>) document.get("codes");
                ArrayList<HashMap> tmp_codeScoreList = (ArrayList<HashMap>) document.get("codes");
                codeScoreList = new ArrayList<>();

                for(int i=0;i<tmp_codeScoreList.size();i++){
                    CodeScore tmp = new CodeScore((String)tmp_codeScoreList.get(i).get("code"),((Long)tmp_codeScoreList.get(i).get("score")).intValue());
                    codeScoreList.add(tmp);
                }


                //ArrayAdapter<CodeScore> codeAdapter;111
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


                codeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String userstr=codeScoreList.get(i).getCode();

                        CollectionReference codes  = db.collection("QRCodes");
                        codes.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable
                                    FirebaseFirestoreException error) {
                                boolean exit = false;
                                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                    String ID = doc.getId();
                                    if(ID.equals(userstr)){
                                        exit = true; }
                                }
                                if(exit==false){
                                    showDelete(i);
                                }
                                else{Intent intent=new Intent(UserCode.this,SelectedQrActivity.class);
                                    intent.putExtra("qrid",userstr);
                                    intent.putExtra("index", i);
                                    Long score = (Long)tmp_codeScoreList.get(i).get("score");
                                    intent.putExtra("score", score);
                                    startActivity(intent);}

                            }
                        });
                    }
                });



                Collections.sort(codeScoreList);
                codeAdapter.notifyDataSetChanged();
                if (codeScoreList.size()>=1) {
                    docUserRef.update("highest",codeScoreList.get(codeScoreList.size()-1).score);
                    lowestscore="THe  lowest score QR name is "+codeScoreList.get(0).code+"\n"+"It's score is "+codeScoreList.get(0).score+".";
                    highestscore="THe highest score QR name is "+codeScoreList.get(codeScoreList.size()-1).code+"\n"+"It's score is "+codeScoreList.get(codeScoreList.size()-1).score+".";
                }else{
                    lowestscore = "There no codes";
                    highestscore = "There no codes";
                }

            }
        });

        buttonhighest = findViewById(R.id.high_code);
        buttonlowest = findViewById(R.id.low_code);

        buttonhighest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highestDialog(highestscore);
            }
        });
        buttonlowest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowestDialog(lowestscore);
            }
        });

        Button back = findViewById(R.id.back_to_profile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.setUsername(appData.getPlayerName());
                Intent intent = new Intent(UserCode.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void highestDialog(String message){
        AlertDialog dlg =new AlertDialog.Builder(UserCode.this)
                .setTitle("Highest Score QR Code")
                .setMessage(message)
                .setPositiveButton("Now I know that", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dlg.show();


    }
    public void lowestDialog(String message){
        AlertDialog dlg =new AlertDialog.Builder(UserCode.this)
                .setTitle("Lowest Score QR Code")
                .setMessage(message)
                .setPositiveButton("Now I know that", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dlg.show();


    }

    public void showDelete(int chosen){
        AlertDialog dlg =new AlertDialog.Builder(UserCode.this)
                .setTitle("QR Code is not exist1")
                .setMessage("This code has already been removed from database by owner,but you can keep the score")
                .setPositiveButton("Now I know that", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .setNegativeButton("Delete code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CollectionReference codeRef = db.collection("Users");
                        //hashScore.hash256(codeScoreList.get(chosenLine).getCode())
                        DocumentReference docUserRef = codeRef.document(username);
                        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                Long totalNumber = document.getLong("total")-1;
                                Long totalScore = document.getLong("sum")-codeScoreList.get(chosenLine).getScore();
                                codeScoreList.remove(codeScoreList.get(chosen));
                                docUserRef.update("codes",codeScoreList);
                                docUserRef.update("total",totalNumber);
                                docUserRef.update("sum",totalScore);
                                docUserRef.update("highest",codeScoreList.get(codeScoreList.size()-1).score);
                                txtTotalScore.setText(totalScore.toString());
                                txtNumber.setText(totalNumber.toString());
                                if(codeScoreList.size()>=1) {
                                    lowestscore = "THe  lowest score QR name is " + codeScoreList.get(0).code + "\n" + "It's score is " + codeScoreList.get(0).score + ".";
                                    highestscore = "THe highest score QR name is " + codeScoreList.get(codeScoreList.size() - 1).code + "\n" + "It's score is " + codeScoreList.get(codeScoreList.size() - 1).score + ".";
                                }else{
                                    lowestscore = "There no codes";
                                    highestscore = "There no codes";
                                }
                                codeAdapter.notifyDataSetChanged();
                                }});
                        dialogInterface.dismiss();



                    }
                })
                .create();
        dlg.show();

    }

}
