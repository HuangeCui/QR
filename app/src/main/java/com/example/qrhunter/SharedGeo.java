package com.example.qrhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SharedGeo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_geo);

        Button back = findViewById(R.id.btnBackToScan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedGeo.this, SharedPicture.class);
                startActivity(intent);
            }
        });

        Button notBtn =findViewById(R.id.btntxt);
        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedGeo.this, UserCode.class);
                startActivity(intent);

            }
        });

        //update code geo
        Button share = findViewById(R.id.btnTake);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGeo();
                Intent intent = new Intent(SharedGeo.this, UserCode.class);
                startActivity(intent);

            }
        });


    }


    public void saveGeo(){}

}
