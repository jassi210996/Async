package com.example.kingj.async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PostDetails extends AppCompatActivity implements PostDetailListner {

    public static final String Position_k = "position";
    Intent intent;
    long id;
    TextView postTitle;
    TextView postBody;
    TextView postUser;
    TextView postComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        intent = getIntent();
        id=intent.getIntExtra(Position_k,1);

        postTitle=findViewById(R.id.posttitle);
        postBody=findViewById(R.id.postbody);
        postUser=findViewById(R.id.postuser);
        postComments=findViewById(R.id.postcomments);



        PostDetailsAsync postDetailsAsync = new PostDetailsAsync(this);
        postDetailsAsync.execute("https://jsonplaceholder.typicode.com/posts/"+id+"");

    }


    @Override
    public void onDetailsDownload(ArrayList<String> titles) {
        String title = titles.get(1);
        String body=titles.get(2);
        String user = titles.get(3);

        postTitle.setText(title);
        postBody.setText(body);
        postBody.setText(user);
    }
}
