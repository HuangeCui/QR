package com.example.qrhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserList  extends ArrayAdapter<UserScore> {
    ArrayList<UserScore> users;
    Context context;

    public UserList(ArrayList<UserScore> users, Context context) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserScore userScore = users.get(position);
        View view = convertView;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.user_content, parent,false);
        }

        TextView txtCode = view.findViewById(R.id.txtUserCustom);
        TextView txtScore = view.findViewById(R.id.txtScoreCustom1);
        String sTmp = userScore.getUser();
        if ( sTmp.length() > 26) {
            sTmp = sTmp.substring(0, 26) + "...";
        }
        txtCode.setText(sTmp);
        txtScore.setText(Integer.toString(userScore.getScore()));
        return view;
    }
}
