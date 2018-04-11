package com.innprojects.smartbar.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.innprojects.smartbar.Activity.DrinksActivity;
import com.innprojects.smartbar.Models.Flavour;
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
 * Created by simransarin on 05/11/17.
 */

public class UserDrinkPOST extends AsyncTask<String, Void, String> {

    DrinksActivity mActivity;
    ArrayList<Flavour> pf;
    String name;
    private ProgressDialog progressDialog;

    public UserDrinkPOST(DrinksActivity drinksActivity, ArrayList<Flavour> prefed, String name) {
        mActivity = drinksActivity;
        pf = prefed;
        this.name = name;
    }

    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]); // here is your URL path

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name", name);
            postDataParams.put("ingredient_name_1", pf.get(0).getIngredient_name());
            postDataParams.put("bottle_name_1", pf.get(0).getBottle());
            postDataParams.put("quantity_1", pf.get(0).getQuantity_selected());
            postDataParams.put("cost_per_ml_1", pf.get(0).getCost());
            postDataParams.put("ingredient_name_2", pf.get(1).getIngredient_name());
            postDataParams.put("bottle_name_2", pf.get(1).getBottle());
            postDataParams.put("quantity_2", pf.get(1).getQuantity_selected());
            postDataParams.put("cost_per_ml_2", pf.get(1).getCost());
            postDataParams.put("ingredient_name_3", pf.get(2).getIngredient_name());
            postDataParams.put("bottle_name_3", pf.get(2).getBottle());
            postDataParams.put("quantity_3", pf.get(2).getQuantity_selected());
            postDataParams.put("cost_per_ml_3", pf.get(2).getCost());
            postDataParams.put("ingredient_name_4", pf.get(3).getIngredient_name());
            postDataParams.put("bottle_name_4", pf.get(3).getBottle());
            postDataParams.put("quantity_4", pf.get(3).getQuantity_selected());
            postDataParams.put("cost_per_ml_4", pf.get(3).getCost());
            postDataParams.put("ingredient_name_5", pf.get(4).getIngredient_name());
            postDataParams.put("bottle_name_5", pf.get(4).getBottle());
            postDataParams.put("quantity_5", pf.get(4).getQuantity_selected());
            postDataParams.put("cost_per_ml_5", pf.get(4).getCost());
            postDataParams.put("ingredient_name_6", pf.get(5).getIngredient_name());
            postDataParams.put("bottle_name_6", pf.get(5).getBottle());
            postDataParams.put("quantity_6", pf.get(5).getQuantity_selected());
            postDataParams.put("cost_per_ml_6", pf.get(5).getCost());
            postDataParams.put("ingredient_name_7", pf.get(6).getIngredient_name());
            postDataParams.put("bottle_name_7", pf.get(6).getBottle());
            postDataParams.put("quantity_7", pf.get(6).getQuantity_selected());
            postDataParams.put("cost_per_ml_7", pf.get(6).getCost());
            postDataParams.put("ingredient_name_8", pf.get(7).getIngredient_name());
            postDataParams.put("bottle_name_8", pf.get(7).getBottle());
            postDataParams.put("quantity_8", pf.get(7).getQuantity_selected());
            postDataParams.put("cost_per_ml_8", pf.get(7).getCost());

            Log.i("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
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
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                Log.e("response string: ", sb.toString());
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
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (s.equals("Exception: "))
            Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
        else
            mActivity.processResultsPOST();
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
