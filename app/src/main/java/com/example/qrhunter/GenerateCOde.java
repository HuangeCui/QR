package com.example.qrhunter;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateCOde extends AppCompatActivity {
    ImageView SignInCode;
    ImageView ProfileCode;
    Button back;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
/* In progress
        SignInCode = findViewById(R.id.SigninCode);
        String SignInQrVl = username+"SignIn";
        String ProfileQrVl = username + "Profile";
        Bitmap SignInQR = createBitmap(SignInQrVl);
        Bitmap ProfileQR = createBitmap(ProfileQrVl);
        SignInCode.setImageBitmap(SignInQR);
        ProfileCode.setImageBitmap(ProfileQR);
        */
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
/*In progress

    }

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
                */
    }
}