package com.example.qrhunter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTmp;
    String userId  = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTmp = findViewById(R.id.btnPlay);
        btnTmp.setOnClickListener(this);
        btnTmp = findViewById(R.id.btnMap);
        btnTmp.setOnClickListener(this);
        btnTmp = findViewById(R.id.btnProfile);
        btnTmp.setOnClickListener(this);

        SharedData appData = (SharedData) getApplication();
        User user = appData.getUser();
        if (user == null) {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }
       //1234 Log.e("user",user.getUserPasscode() );


        Button rank= findViewById(R.id.btnRank);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rankActivity = new Intent(MainActivity.this, RankActivity.class);
                startActivity(rankActivity);
            }
        });

        Button manage = findViewById(R.id.btnManage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId.equals("1234")){
                    Intent manageActivity = new Intent(MainActivity.this, ManageActivity.class);
                    startActivity(manageActivity);
                }
                else{

                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                scanCode();
                break;
            case R.id.btnMap:
                break;
            default:
                break;

        }
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ScanActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null) {
            if (result.getContents() != null) {
                SharedData appData = (SharedData) getApplication();
                appData.setQrcode(result.getContents());
                appData.setImagepath(result.getBarcodeImagePath());

                Intent intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(result.toString());
//                builder.setTitle("Scanning Result");
//                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        scanCode();
//                    }
//                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
            } else {
                Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}