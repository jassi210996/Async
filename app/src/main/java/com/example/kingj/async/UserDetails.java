package com.example.kingj.async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UserDetails extends AppCompatActivity implements UserListner  {

    TextView uid,uname,userName,eMail;
    Intent intent;
    Long id;
    User userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        uid=findViewById(R.id.id);
        uname=findViewById(R.id.name);

        userName=findViewById(R.id.userName);
        eMail=findViewById(R.id.eMail);

        intent = getIntent();

        id=intent.getLongExtra(MainActivity.id_k,1);

        UserAsync userAsync = new UserAsync(this);

        userAsync.execute("https://jsonplaceholder.typicode.com/users/"+id);
    }

    @Override
    public void userDownload(User details) {

        userDetails=details;

        long id=userDetails.getUid();
        String name = userDetails.getUname();
        String nameuser = userDetails.getUserName();
        String email = userDetails.getEmail();

        uid.setText(String.valueOf(id));
        uname.setText(name);
        userName.setText(nameuser);
        eMail.setText(email);

    }
}
