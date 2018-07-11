package com.example.kingj.async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostDetails extends AppCompatActivity implements PostDetailListner, View.OnClickListener {

    public static final String id_k = "id";
    Intent intent;
    long id,userid;
    String body,title;
    TextView postTitle;
    TextView postBody;
    TextView postUser;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> comments=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        listView=findViewById(R.id.listviewde);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,comments);
        listView.setAdapter(adapter);

        intent = getIntent();

        id=intent.getLongExtra(MainActivity.id_k,1);
        userid=intent.getLongExtra(MainActivity.Userid_k,1);


        body=intent.getStringExtra(MainActivity.Body_k);
        title=intent.getStringExtra(MainActivity.Title_k);


        postTitle=findViewById(R.id.posttitle);
        postBody=findViewById(R.id.postbody);
        postUser=findViewById(R.id.postuser);

        postUser.setOnClickListener(this);

        CommentsAsync commentsAsync = new CommentsAsync(this);

        commentsAsync.execute("https://jsonplaceholder.typicode.com/posts/"+id+"/comments");
    }

    @Override
    public void onDetailsDownload(ArrayList<String> titles) {

        postTitle.setText(title);
        postBody.setText(body);
        postUser.setText(String.valueOf(userid));



        int size = titles.size();

        for(int i=0;i<size;i++)
        {
          String comment=titles.get(i);
          comments.add(comment);
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();

        bundle.putLong(id_k,id);
        Intent intent= new Intent(this,UserDetails.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
