package com.example.kingj.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class AsyncPost extends AsyncTask<String,Void,ArrayList<Post>>{


    PostDownloadListner listner;


    AsyncPost(PostDownloadListner listner){
        this.listner=listner;
    }

    @Override
    protected ArrayList<Post> doInBackground(String... strings) {

        ArrayList<Post> titles = new ArrayList<>();
        String urlString = strings[0];

        try {
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection =(HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            Log.v("id we get"," = ");

            urlConnection.connect();

            InputStream inputStream =urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            String result="";

            while (scanner.hasNext())
            {
                result=result+scanner.next();
            }

            JSONArray root = new JSONArray(result);

            for(int i=0;i<root.length();i++)
            {
                Post post = new Post();
                JSONObject postObject = root.getJSONObject(i);

                String title = postObject.getString("title");
                post.setPost_title(title);

                long id = postObject.getLong("id");
                post.setPost_id(id);

                long user_id = postObject.getInt("userId");
                post.setUser_id(user_id);

                String body = postObject.getString("body");
                post.setBody(body);


                titles.add(post);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return titles;
    }

    @Override
    protected void onPostExecute(ArrayList<Post> strings) {
        super.onPostExecute(strings);
        listner.postDownload(strings);
    }
}
