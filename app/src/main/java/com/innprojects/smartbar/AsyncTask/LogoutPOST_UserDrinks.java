package com.innprojects.smartbar.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Activity.FinalActivity;
import com.innprojects.smartbar.Activity.ProfileActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by simransarin on 30/09/17.
 */

public class LogoutPOST_UserDrinks extends AsyncTask<String, Void, String> {

    private DrinksActivity mActivity;

    public LogoutPOST_UserDrinks(DrinksActivity drinksActivity) {
        mActivity = drinksActivity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]); // here is your URL path
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 204) { //HTTP_OK : 201 http_201_created 204:for no content response
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } else {
                Log.e("doInBackground: ", new String("false : " + responseCode)); // : 400 http_400_bad_request
                return new String("false : ");
            }
        } catch (Exception e) {
            Log.e("doInBackground: ", new String("Exception: " + e.getMessage()));
            return new String("Exception: ");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("Exception: ") || s.equals("false : "))
                Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
        else
            super.onPostExecute(s);
    }
}
