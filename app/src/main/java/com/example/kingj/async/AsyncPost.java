package com.example.kingj.async;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.JarInputStream;

import javax.net.ssl.HttpsURLConnection;

public class AsyncPost extends AsyncTask<String,Void,ArrayList<String>>{


    PostDownloadListner listner;

    AsyncPost(PostDownloadListner listner){
        this.listner=listner;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        ArrayList<String> titles = new ArrayList<>();
        String urlString = strings[0];

        try {
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection =(HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

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
                JSONObject postObject = root.getJSONObject(i);
                String title = postObject.getString("title");
                titles.add(title);
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
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        listner.postDownload(strings);
    }
}
