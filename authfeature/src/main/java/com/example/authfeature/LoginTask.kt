package com.example.authfeature;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class LoginTask extends AsyncTask<String, Void, String> {


    private String username, password;
    private Context context;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String result;
        String inputLine;

        //Set username and password
        this.username = params[0];
        this.password = params[1];

        try {
            //Setup URL connection
            URL url = new URL("https://assessment-sn12.halan.io/auth");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("password", password);

            writer.write(body.toString());
            writer.close();
            connection.connect();

            int status = connection.getResponseCode();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();


            switch (status) {
                case 200:
                    return result;
                default:
                    return null;
            }
        } catch (java.net.MalformedURLException e) {
            Log.w(TAG, "Exception while constructing URL" + e.getMessage());
        } catch (IOException | JSONException e) {
            Log.w(TAG, "Exception occured while logging in: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
//        if (result != null) {
//            Intent myIntent = new Intent(context, ProductsListActivity.class);
//            myIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            myIntent.putExtra("RESPONSE", result);
//            context.startActivity(myIntent);
//        }

    }

}
