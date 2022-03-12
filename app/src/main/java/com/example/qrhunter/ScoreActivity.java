package com.example.qrhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    String qrcode;
    String imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedData appData = (SharedData) getApplication();
        qrcode = appData.getQrcode();
        imagepath = appData.getImagepath();
        ImageView imageView = findViewById(R.id.imgQrcode);
        Log.d("TAG", "onCreate: " + imagepath + "    " + qrcode);

        // method 1
        File file = new File(imagepath);
        imageView.setImageURI(Uri.fromFile(file));
        // method 2
//        try {
//            Bitmap bmp = MediaStore.Images.Media.getBitmap(ScoreActivity.this.getContentResolver(), Uri.fromFile(file));
//            imageView.setImageBitmap(bmp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        HashScore hashScore = new HashScore();
        /* 这里是课程里的例子，但hash值的结果不一样，如果用例子中的hash计算score结果是一样的
        qrcode = "BFG5DGW54";
        Log.d("TAG", "HashCode: " + hashScore.hash256(qrcode));
        String hashcode = "696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6";
        int score = hashScore.score(hashScore.counter(hashcode));
        */
        Log.d("TAG", "onCreate: Hash = " + hashScore.hash256(qrcode));
        int score = hashScore.score(hashScore.counter(hashScore.hash256(qrcode)));
        TextView textView = findViewById(R.id.txtScore);
        textView.setText(String.valueOf(score));

    }



}