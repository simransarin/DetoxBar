package com.innprojects.smartbar.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.smartbar.Activity.PrefedActivity;
import com.innprojects.smartbar.Models.Prefed;
import org.json.JSONArray;
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
 * Created by simransarin on 04/11/17.
 */

public class PreFedGET extends AsyncTask<String, Void, Prefed[]> {

    private PrefedActivity prefedActivity;
    private ProgressDialog progressDialog;

    public PreFedGET(PrefedActivity pActivity) {
        prefedActivity = pActivity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(prefedActivity);
        progressDialog.setMessage("Getting drinks...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
    private Prefed[] parseJson(String json) {
        try {
            JSONArray prefeds = new JSONArray(json);
            Prefed[] output = new Prefed[prefeds.length()];
            for (int i = 0; i < prefeds.length(); i++) {
                JSONObject prefedsJSONObject = prefeds.getJSONObject(i);
                String name = prefedsJSONObject.getString("name");
                String ingredient_name_1 = prefedsJSONObject.getString("ingredient_name_1");
                String bottle_name_1 = prefedsJSONObject.getString("bottle_name_1");
                String quantity_1 = prefedsJSONObject.getString("quantity_1");
                String cost_per_ml_1 = prefedsJSONObject.getString("cost_per_ml_1");
                String ingredient_name_2 = prefedsJSONObject.getString("ingredient_name_2");
                String bottle_name_2 = prefedsJSONObject.getString("bottle_name_2");
                String quantity_2 = prefedsJSONObject.getString("quantity_2");
                String cost_per_ml_2 = prefedsJSONObject.getString("cost_per_ml_2");
                String ingredient_name_3 = prefedsJSONObject.getString("ingredient_name_3");
                String bottle_name_3 = prefedsJSONObject.getString("bottle_name_3");
                String quantity_3 = prefedsJSONObject.getString("quantity_3");
                String cost_per_ml_3 = prefedsJSONObject.getString("cost_per_ml_3");
                String ingredient_name_4 = prefedsJSONObject.getString("ingredient_name_4");
                String bottle_name_4 = prefedsJSONObject.getString("bottle_name_4");
                String quantity_4 = prefedsJSONObject.getString("quantity_4");
                String cost_per_ml_4 = prefedsJSONObject.getString("cost_per_ml_4");
                String ingredient_name_5 = prefedsJSONObject.getString("ingredient_name_5");
                String bottle_name_5 = prefedsJSONObject.getString("bottle_name_5");
                String quantity_5 = prefedsJSONObject.getString("quantity_5");
                String cost_per_ml_5 = prefedsJSONObject.getString("cost_per_ml_5");
                String ingredient_name_6 = prefedsJSONObject.getString("ingredient_name_6");
                String bottle_name_6 = prefedsJSONObject.getString("bottle_name_6");
                String quantity_6 = prefedsJSONObject.getString("quantity_6");
                String cost_per_ml_6 = prefedsJSONObject.getString("cost_per_ml_6");
                String ingredient_name_7 = prefedsJSONObject.getString("ingredient_name_7");
                String bottle_name_7 = prefedsJSONObject.getString("bottle_name_7");
                String quantity_7 = prefedsJSONObject.getString("quantity_7");
                String cost_per_ml_7 = prefedsJSONObject.getString("cost_per_ml_7");
                String ingredient_name_8 = prefedsJSONObject.getString("ingredient_name_8");
                String bottle_name_8 = prefedsJSONObject.getString("bottle_name_8");
                String quantity_8 = prefedsJSONObject.getString("quantity_8");
                String cost_per_ml_8 = prefedsJSONObject.getString("cost_per_ml_8");

                output[i] = new Prefed(name,ingredient_name_1,bottle_name_1,quantity_1,cost_per_ml_1,ingredient_name_2,bottle_name_2,quantity_2,cost_per_ml_2,
                        ingredient_name_3,bottle_name_3,quantity_3,cost_per_ml_3,ingredient_name_4,bottle_name_4,quantity_4,cost_per_ml_4
                        ,ingredient_name_5,bottle_name_5,quantity_5,cost_per_ml_5,ingredient_name_6,bottle_name_6,quantity_6,cost_per_ml_6
                        ,ingredient_name_7,bottle_name_7,quantity_7,cost_per_ml_7,ingredient_name_8,bottle_name_8,quantity_8,cost_per_ml_8, "red", "");
            }
            return output;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected Prefed[] doInBackground(String... params) {
        if (params.length == 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream =
                    urlConnection.getInputStream();
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
    protected void onPostExecute(Prefed[] prefeds) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (prefeds != null) {
            prefedActivity.processResults(prefeds);
        } else {
            Toast.makeText(prefedActivity, "Network error", Toast.LENGTH_SHORT).show();
        }
    }
}

