package com.example.kingj.async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static final String Position_k = "position";
    public static  final  int Request_Code = 1;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> posts = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,posts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }

    public void fetchData(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        AsyncPost asyncPost = new AsyncPost(new PostDownloadListner() {
        @Override
        public void postDownload(ArrayList<String> titles) {
            posts.clear();
            posts.addAll(titles);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    });
        asyncPost.execute("https://jsonplaceholder.typicode.com/posts");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Bundle bundle = new Bundle();
        bundle.putInt(Position_k,i);
        Intent intent= new Intent(this,PostDetails.class);
        startActivity(intent);

    }
}
