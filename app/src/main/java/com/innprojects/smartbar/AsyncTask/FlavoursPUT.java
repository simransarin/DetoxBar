package com.innprojects.smartbar.AsyncTask;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Activity.FinalActivity;
import com.innprojects.smartbar.Models.Flavour;
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by simransarin on 11/10/17.
 */

public class FlavoursPUT extends AsyncTask<String, Void, String> {

    ArrayList<Flavour> flavours;
    Boolean flag = false;
    private DrinksActivity mActivity;
    private FinalActivity finalActivity;
    private SharedPreferences mPreferences;
    private ProgressDialog progressDialog;
    private String access_tokenmachine;

    public FlavoursPUT(DrinksActivity activity, ArrayList<Flavour> flavours1) {
        mActivity = activity;
        flavours = flavours1;
        flag = true;
    }

    public FlavoursPUT(FinalActivity finalActivity, ArrayList<Flavour> flavours) {
        this.flavours = flavours;
        this.finalActivity = finalActivity;
    }

    protected void onPreExecute() {
        if (flag)
            progressDialog = new ProgressDialog(mActivity);
        else
            progressDialog = new ProgressDialog(finalActivity);

        progressDialog.setMessage("Updating Flavours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (flag)
            mPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity.getApplicationContext());
        else
            mPreferences = PreferenceManager.getDefaultSharedPreferences(finalActivity.getApplicationContext());
        access_tokenmachine = mPreferences.getString("keymachine", null);
        super.onPreExecute();
    }

    protected String doInBackground(String... strings) {
        try {
            for (Flavour f : flavours) {
                URL url = new URL("https://detoxbar.herokuapp.com/api/bottles/" + f.getId() + "?access_token=" + access_tokenmachine); // here is your URL path
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("ingredient_name", f.getIngredient_name());
                postDataParams.put("quantity", Integer.toString(Integer.parseInt(f.getQuantity())));
                postDataParams.put("cost_per_ml", f.getCost());
                postDataParams.put("bottle_name", f.getBottle());
                postDataParams.put("machineId", f.getMachineId());
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
                String msgcode = conn.getResponseMessage();
            }
        } catch (Exception e) {
            Log.e("doInBackground: ", new String("Exception: " + e.getMessage()));
            return new String("Exception: ");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (s != null)
            if (flag)
                Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(finalActivity, "Network error", Toast.LENGTH_SHORT).show();
        else
        if (flag)
            mActivity.processResultsFlavour();
        else
            finalActivity.processResultsFlavour();
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
