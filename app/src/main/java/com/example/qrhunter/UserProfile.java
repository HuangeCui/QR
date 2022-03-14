package com.example.qrhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//see others' profile
public class UserProfile extends AppCompatActivity {

    SharedData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        appData = (SharedData) getApplication();
        String searchName = appData.getSearchname();
        TextView textView = (TextView) findViewById(R.id.txtUsername);
        textView.setText(searchName);

        Button btn = findViewById(R.id.btnUserBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*not work yet
        Button comment = findViewById(R.id.btnUserComment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, CodeCommentActivity.class);
                startActivity(intent);
            }
        });

         */
    }
}




        /*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUserCode:
                break;
            case R.id.btnUserComment:
                usercomment();
                break;
            case R.id.btnUserBack:
                back();
                break;
            default:
                break;
        }
    }

    private void usercomment() {
        Intent intent = new Intent(this, CodeCommentActivity.class);
        startActivity(intent);
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

         */