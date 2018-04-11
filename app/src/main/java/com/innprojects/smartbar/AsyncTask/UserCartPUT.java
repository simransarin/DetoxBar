package com.innprojects.smartbar.AsyncTask;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Activity.FinalActivity;
import com.innprojects.smartbar.Models.User;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by simransarin on 10/10/17.
 */

public class UserCartPUT extends AsyncTask<String, Void, String> {

    private DrinksActivity mActivity;
    private FinalActivity finalActivity;
    private SharedPreferences mPreferences;
    Boolean flag = false;

    public UserCartPUT(DrinksActivity dactivity) {
        mActivity = dactivity;
        flag = true;
    }
    public UserCartPUT(FinalActivity factivity) {
        finalActivity = factivity;
    }

    protected void onPreExecute() {
        if(flag)
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity.getApplicationContext());
        else
            mPreferences = PreferenceManager.getDefaultSharedPreferences(finalActivity.getApplicationContext());
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]); // here is your URL path
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("registration_number", mPreferences.getString("mem_no", null));
            postDataParams.put("name", mPreferences.getString("name", null));
            postDataParams.put("address", mPreferences.getString("add", null));
            postDataParams.put("phone_number", mPreferences.getString("number", null));
            postDataParams.put("cart_value", mPreferences.getString("cart", null));
            postDataParams.put("username", mPreferences.getString("username", null));
            postDataParams.put("password", mPreferences.getString("password", null));
            postDataParams.put("email", mPreferences.getString("email", null));
            postDataParams.put("id", mPreferences.getString("userID", null));
            postDataParams.put("machineId", mPreferences.getString("userIDmachine", null));
            Log.i("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { //HTTP_OK : 201 http_201_created
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
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
        if (s.equals("Exception: ") || s.equals("false : ") || s.equals(null))
            Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
        else {
            if (flag)
            mActivity.processResultsCartUpdate();
            else
                finalActivity.processResultsCartUpdate();
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
