package com.example.kingj.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PostDetailsAsync extends AsyncTask<String,Void,ArrayList<String>> {

    PostDetailListner detailListner;

    PostDetailsAsync(PostDetailListner listner)
    {
        detailListner=listner;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        ArrayList<String> titles = new ArrayList<>();
        String urlString = strings[0];

        try {
            URL url =new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            String result="";

            while (scanner.hasNext())
            {
                result=result+scanner.next();
            }

            JSONObject rootObject = new JSONObject();

            String title = rootObject.getString("title");

            Log.d("post details" ," = " +title  );

            titles.add(1,title);
            String body = rootObject.getString("body");
            titles.add(2,body);
            String user= rootObject.getString("user");
            titles.add(3,user);
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
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        detailListner.onDetailsDownload(strings);
    }
}
