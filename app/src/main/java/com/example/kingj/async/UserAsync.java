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

import javax.net.ssl.HttpsURLConnection;

public class UserAsync extends AsyncTask<String,Void,User> {

    UserListner listner;

    UserAsync(UserListner listner){
        this.listner=listner;
    }

    @Override
    protected User doInBackground(String... strings) {

//        ArrayList<User> details = new ArrayList<>();
        String urlString = strings[0];
        User user = new User();

        try{

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


            JSONObject jsonObject = new JSONObject(result);


            long id=jsonObject.getLong("id");
            user.setUid(id);
            String name=jsonObject.getString("name");
            user.setUname(name);
            String username=jsonObject.getString("username");
            user.setUserName(username);
            String email=jsonObject.getString("email");
            user.setEmail(email);

//            details.add(user);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    protected void onPostExecute(User users) {
        super.onPostExecute(users);
        listner.userDownload(users);
    }
}
