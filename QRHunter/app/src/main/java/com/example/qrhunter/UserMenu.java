package com.example.qrhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        final Button profile = findViewById(R.id.my_button);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this,UserProfile.class);
                startActivity(intent);
            }
        });

        final Button play = findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this,UserPlay.class);
                startActivity(intent);
            }
        });

        final Button out = findViewById(R.id.out_button);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Button accountCode = findViewById(R.id.code_button);
        accountCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this,UserCode.class);
                startActivity(intent);
            }
        });

        final Button rank = findViewById(R.id.rank_button);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this,RankingTable.class);
                startActivity(intent);
            }
        });

        final Button map = findViewById(R.id.map_button);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this,Map.class);
                startActivity(intent);
            }
        });
    }
}