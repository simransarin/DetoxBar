package com.innprojects.smartbar.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Activity.LoginActivity;
import com.innprojects.smartbar.Models.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by simransarin on 09/10/17.
 */

public class UserDetailsGET extends AsyncTask<String, Void, User> {
    private LoginActivity loginActivity;

    public UserDetailsGET(LoginActivity activity) {
        loginActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private User parseJson(String json) {
        User outputUser;
        try {
            JSONObject finalObject = new JSONObject(json);
            String registration_number = finalObject.getString("registration_number");
            String name = finalObject.getString("name");
            String phone_number = finalObject.getString("phone_number");
            String email = finalObject.getString("email");
            String address = finalObject.getString("address");
            String cart_value = finalObject.getString("cart_value");
            outputUser = new User(registration_number, name, phone_number, email, address, cart_value);
            Log.i("Log_BUSDownloadTask", outputUser.getEmail());
            return outputUser;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected User doInBackground(String... params) {
        if (params.length == 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            Scanner s = new Scanner(inputStream);
            while (s.hasNext()) {
                buffer.append(s.nextLine());
            }
            Log.i("jsondata", buffer.toString());
        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return parseJson(buffer.toString());
    }

    @Override
    protected void onPostExecute(User user) {
        if (user != null) {
            loginActivity.processResultsProfile(user);
        } else {
            Toast.makeText(loginActivity, "Network error", Toast.LENGTH_SHORT).show();
        }
    }
}