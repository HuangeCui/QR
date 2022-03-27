package com.example.qrhunter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class SharedPicture extends AppCompatActivity {
    String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_picture);

        Button notBtn =findViewById(R.id.btntxt);
        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedPicture.this, SharedGeo.class);
                startActivity(intent);

            }
        });

        Button share =findViewById(R.id.btnShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               notBigPhoto();
                //Intent intent = new Intent(SharedPicture.this, SharedGeo.class);
                //startActivity(intent);

            }
        });

        Button back = findViewById(R.id.btnBackToScan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedPicture.this, ScoreActivity.class);
                startActivity(intent);
            }
        });


        //take a photo or use the code photo
        Button image = findViewById(R.id.btnphoto);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.imgQrcode);
                SharedData appData = (SharedData) getApplication();
                imagePath = appData.getImagepath();
                File file = new File(imagePath);
                imageView.setImageURI(Uri.fromFile(file));

            }
        });

        Button take = findViewById(R.id.btnTake);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();

            }
        });








    }

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
                //Log.e("quality", ""+bytes.length);
                // tvCompress.setText(info);
                // imgvCompress.setImageBitmap(newBitmap);
            }

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

        savePhoto();
        Intent intent = new Intent(SharedPicture.this, SharedGeo.class);
        startActivity(intent);
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
           // Log.e("fail","not exist!");
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
           // Log.e("fail","not exit!");
        }
        return size;
    }


    public void takePhoto(){
        //take photo save at sharedData which use to check the picture size, put on the view


        //after taking, put on the view
        ImageView imageView = findViewById(R.id.imgQrcode);
        SharedData appData = (SharedData) getApplication();
        imagePath = appData.getImagepath();
        File file = new File(imagePath);
        imageView.setImageURI(Uri.fromFile(file));
    }

    //save the picture on the firebase
    public void savePhoto(){}



}