package com.example.qrhunter;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateCOde extends AppCompatActivity {
    ImageView SignInCode;
    ImageView ProfileCode;
    Button back;
    String username;
    SharedData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        appData = (SharedData) getApplication();
        username = appData.getSearchname();


        SignInCode = findViewById(R.id.SigninCode);
        String SignInQrVl = username+"SignIn";
        //String ProfileQrVl = username + "Profile";
        Bitmap SignInQR = createBitmap(SignInQrVl);
        //Bitmap ProfileQR = createBitmap(ProfileQrVl);
        SignInCode.setImageBitmap(SignInQR);
        //ProfileCode.setImageBitmap(ProfileQR);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActivityCompat.requestPermissions(GenerateCOde.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(GenerateCOde.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        SignInCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveToGallery();
            }
        });


    }

    //still need to implement for Profile QR Code

    private Bitmap createBitmap(String signInQrVl) {
        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(signInQrVl,
                    BarcodeFormat.QR_CODE,
                    220,
                    220);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for(int i = 0; i < height; i++) {
            int offset = i * width;
            for (int k=0; k<width; k++) {
                pixels[offset + k] = result.get(k, i) ? BLACK : WHITE;
            }
        }
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return myBitmap;
    }

    private void SaveToGallery(){
        BitmapDrawable bitmapdrawable = (BitmapDrawable) SignInCode.getDrawable();
        Bitmap bitmap = bitmapdrawable.getBitmap();

        FileOutputStream fileoutputstream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/MyPics");
        dir.mkdirs();

        String filename = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir, filename);
        try {
            fileoutputstream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileoutputstream);
        try {
            fileoutputstream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileoutputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}