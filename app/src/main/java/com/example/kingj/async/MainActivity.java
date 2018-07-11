package com.example.kingj.async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static final String id_k = "id";
    public static final String Body_k = "body";
    public static final String Title_k = "title";
    public static final String Userid_k = "userid";
    public static  final  int Request_Code = 1;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<String> postTitle = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,postTitle);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }

    public void fetchData(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        AsyncPost asyncPost = new AsyncPost(new PostDownloadListner() {
        @Override
        public void postDownload(ArrayList<Post> titles) {

            int size=titles.size();
            postTitle.clear();

            for(int i=0;i<size;i++){
                posts.add(titles.get(i));
                Post post=titles.get(i);

                String bod = post.getBody();
                Log.i("mother","fucker = " +bod);

                postTitle.add(post.getPost_title());
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    });
        asyncPost.execute("https://jsonplaceholder.typicode.com/posts");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Post post = posts.get(i);

        String title = post.getPost_title();
        long id = post.getPost_id();
        String body=post.getBody();
        long user_id = post.getPost_id();

        Bundle bundle = new Bundle();

        bundle.putString(Title_k,title);
        bundle.putLong(id_k,id);
        bundle.putLong(Userid_k,user_id);
        bundle.putString(Body_k,body);
        Intent intent= new Intent(this,PostDetails.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
