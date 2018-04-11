package com.innprojects.smartbar.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Activity.PrefedActivity;
import com.innprojects.smartbar.Models.Flavour;

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
 * Created by simransarin on 09/10/17.
 */

public class FlavoursGET extends AsyncTask<String, Void, Flavour[]> {

    private PrefedActivity prefedActivity;
    private ProgressDialog progressDialog;

    public FlavoursGET(PrefedActivity pActivity) {
        prefedActivity = pActivity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(prefedActivity);
        progressDialog.setMessage("Getting Flavours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
    private Flavour[] parseJson(String json) {
        try {
            JSONArray flavours = new JSONArray(json);
            Flavour[] output = new Flavour[flavours.length()];
            for (int i = 0; i < flavours.length(); i++) {
                JSONObject flavoursJSONObject = flavours.getJSONObject(i);
                String ingredient_name = flavoursJSONObject.getString("ingredient_name");
                String quantity = flavoursJSONObject.getString("quantity");
                String id = flavoursJSONObject.getString("id");
                String machineId = flavoursJSONObject.getString("machineId");
                String cost = flavoursJSONObject.getString("cost_per_ml");
                String bottle = flavoursJSONObject.getString("bottle_name");
                output[i] = new Flavour(ingredient_name, quantity, id, machineId, cost, bottle, "0");
            }
            return output;
        } catch (JSONException e) {
            Toast.makeText(prefedActivity, "Network error", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected Flavour[] doInBackground(String... params) {
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
    protected void onPostExecute(Flavour[] flavours) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (flavours != null) {
            prefedActivity.processResults(flavours);
        } else {
            Toast.makeText(prefedActivity, "Network error", Toast.LENGTH_SHORT).show();
        }
    }
}

