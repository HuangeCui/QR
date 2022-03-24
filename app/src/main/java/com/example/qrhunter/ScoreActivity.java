package com.example.qrhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {
    String qrCode;
    String imagePath;
    int score;
    String userName;
    FirebaseFirestore db;
    Boolean savedPicture = false;
    Boolean savedGeo = false;

    final String TAG = "ScoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Button btnTmp;
        btnTmp = (Button) findViewById(R.id.btnBackToScan);
        btnTmp.setOnClickListener(this);
        btnTmp = (Button) findViewById(R.id.btnAddQRCode);
        btnTmp.setOnClickListener(this);
        btnTmp = (Button) findViewById(R.id.btnwhoscanned);
        btnTmp.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        SharedData appData = (SharedData) getApplication();
        qrCode = appData.getQrcode();
        imagePath = appData.getImagepath();
        userName = appData.getUsername();
        ImageView imageView = findViewById(R.id.imgQrcode);
        Log.d(TAG, "onCreate: " + imagePath + "    " + qrCode);

        // method 1
        File file = new File(imagePath);
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
        Log.d(TAG, "HashCode: " + hashScore.hash256(qrcode));
        String hashcode = "696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6";
        int score = hashScore.score(hashScore.counter(hashcode));
        */
        Log.d(TAG, "onCreate: Hash = " + hashScore.hash256(qrCode));
        score = hashScore.score(hashScore.counter(hashScore.hash256(qrCode)));
        appData.setCodescore(score);
        TextView textView = findViewById(R.id.txtScore);
        textView.setText(String.valueOf(score));

        CheckBox checkBox = findViewById(R.id.chkRecord);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button button = findViewById(R.id.btnAddQRCode);
                button.setEnabled(isChecked);
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackToScan:
                back();
                break;
            case R.id.btnAddQRCode:
                saveGeo();
               // add();
                //Intent intent = new Intent(this, MainActivity.class);
               // startActivity(intent);
                break;
            case R.id.btnwhoscanned:
                /*
                Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, CodeScannedbyActivity.class);
                startActivity(intent2);

                 */
                whoscanned();
                //back();
                break;
            default:
                break;
        }
    }

    private void whoscanned() {
        Intent intent = new Intent(this, CodeScannedbyActivity  .class);
        startActivity(intent);
    }

    private void back() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    // one code can scan so many times and keep adding score
    private void add() {
        //saveGeo();
        // Read user records modify parameters and write back
        CollectionReference usersRef = db.collection("Users");
        DocumentReference docUserRef = usersRef.document(userName);
        docUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> data = new HashMap<>();
                    data.put("sum", document.getLong("sum") + score);
                    data.put("total", document.getLong("total") + 1);
                    ArrayList<CodeScore> codeScoreList = (ArrayList<CodeScore>) document.get("codes");
                    codeScoreList.add(new CodeScore(qrCode, score));
                    data.put("codes", codeScoreList);
                    docUserRef.set(data, SetOptions.merge());
                    Log.d(TAG, "User documents write success. ");
                } else {
                    Log.d(TAG, "Error getting user documents: ", task.getException());
                }
            }
        });
        // If Code exists, read it out, create it if it does not exist, and write it back by modifying the parameters
        CollectionReference codesRef = db.collection("QRCodes");
        DocumentReference docCodeRef = codesRef.document(qrCode);
        docCodeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().exists()) {
                        QRCode newCode = new QRCode(qrCode, score);
                        newCode.addScanner(userName);
                        docCodeRef.set(newCode, SetOptions.merge());
                    } else {
                        DocumentSnapshot document = task.getResult();
                        ArrayList<String> userList = (ArrayList<String>) document.get("scanners");
                        userList.add(userName);
                        Map<String, Object> data = new HashMap<>();
                        data.put("scanners", userList);
                        docCodeRef.set(data, SetOptions.merge());
                    }
                    Log.d(TAG, "Code documents write success. ");
                } else {
                    Log.d(TAG, "Error getting code documents: ", task.getException());
                }
            }
        });



    }

    public void saveGeo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
        builder.setMessage("Are you sure to Save Location?");
        builder.setTitle("Information");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savedGeo= true;
                savePicture();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savedGeo = false;
                savePicture();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();}

    public void savePicture(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
        builder.setMessage("Are you sure to Save Picture?");
        builder.setTitle("Information");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savedPicture = true;
                notBigPhoto();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savedPicture = false;
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();}


    public void notBigPhoto(){
        SharedData appData = (SharedData) getApplication();
        imagePath = appData.getImagepath();
        File file = new File(imagePath);
        double size = getFileOrFilesSize(file);
        //Log.e(TAG, ""+size);
        if(size<=64){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png"
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String str = "2";
            int quality = Integer.parseInt(str);
            if(bitmap != null) {
//            bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos); // 设置Bitmap.CompressFormat.PNG，quality将不起作用，PNG是无损压缩
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                byte[] bytes = baos.toByteArray();
                Bitmap newBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //String info = " quality: " + quality + " 压缩图片大小: " + (newBitmap.getByteCount()) + " 压缩后文件大小: " + (bytes.length) + " 宽度为: " + newBitmap.getWidth() + " 高度为: " + newBitmap.getHeight();
                Log.e("quality", ""+bytes.length);
                // tvCompress.setText(info);
                // imgvCompress.setImageBitmap(newBitmap);
            }
            add();
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);

             }


        //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png"
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //String str = "2";//edtvQuality.getText().toString();
        //int quality = 100;
        //try {
         //int quality = Integer.parseInt(str);
      //  } catch (Exception e) {
            //Toast.makeText(this, "请输入有效数字内容", Toast.LENGTH_SHORT).show();
        //    e.printStackTrace();
           // return ;
       //}
      //  if(bitmap != null) {
//       //     bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos); // 设置Bitmap.CompressFormat.PNG，quality将不起作用，PNG是无损压缩
        //    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        //    byte[] bytes = baos.toByteArray();
         //   Bitmap newBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            //String info = " quality: " + quality + " 压缩图片大小: " + (newBitmap.getByteCount()) + " 压缩后文件大小: " + (bytes.length) + " 宽度为: " + newBitmap.getWidth() + " 高度为: " + newBitmap.getHeight();
          //  Log.e("quality", ""+bytes.length);
           // tvCompress.setText(info);
           // imgvCompress.setImageBitmap(newBitmap);
      //  }

       // if(size>=64){
       //     changeSize();
       // }



    }


    public static double getFileOrFilesSize(File file) {

        //File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("fail","not exist!");
        }
        return FormetFileSize(blockSize);
    }

    private static double FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
        return fileSizeLong;

    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("fail","not exit!");
        }
        return size;
    }

}