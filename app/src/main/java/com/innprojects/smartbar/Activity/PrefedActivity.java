package com.innprojects.smartbar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.innprojects.smartbar.AsyncTask.FlavoursGET;
import com.innprojects.smartbar.AsyncTask.LoginPOST_User;
import com.innprojects.smartbar.AsyncTask.PreFedGET;
import com.innprojects.smartbar.AsyncTask.UserCartPUT;
import com.innprojects.smartbar.AsyncTask.UserDrinkGET;
import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.Models.Prefed;
import com.innprojects.smartbar.R;
import com.innprojects.smartbar.RCVadapters.PrefedAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class PrefedActivity extends AppCompatActivity {

    GridView preFedGridView;
    ArrayList<Prefed> arrayList;
    PrefedAdapter adapter;
    SharedPreferences mPreferences;
    String userID, key, userIDMachine, keyMachine;
    ArrayList<Flavour> flavoursgiven;


    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefed);
        arrayList = new ArrayList<Prefed>();
        preFedGridView = (GridView) findViewById(R.id.gridview2);
        adapter = new PrefedAdapter(this, arrayList);
        preFedGridView.setAdapter(adapter);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userIDMachine = mPreferences.getString("keymachine", "");
        keyMachine = mPreferences.getString("userIDmachine", "");
        userID = mPreferences.getString("key", "");
        key = mPreferences.getString("userID", "");

        flavoursgiven = new ArrayList<Flavour>();

        preFedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position).getColour().equals("red") || arrayList.get(position).getColour().equals("blue")) {
                    Intent i = new Intent();
                    i.setClass(PrefedActivity.this, FinalActivity.class);
                    Prefed clickedPrefed = (Prefed) parent.getAdapter().getItem(position);
                    i.putExtra("prefed", clickedPrefed);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)flavoursgiven);
                    i.putExtra("BUNDLE",args);
                    i.putExtra("name",clickedPrefed.getName());
                    startActivity(i);
                } else if (arrayList.get(position).getColour().equals("null")) {
                    Intent i = new Intent();
                    i.setClass(PrefedActivity.this, DrinksActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)flavoursgiven);
                    i.putExtra("BUNDLE",args);
                    startActivity(i);
                    finish();
                }
            }
        });
        if (isConnected(PrefedActivity.this)) {
            new FlavoursGET(this).execute("https://detoxbar.herokuapp.com/api/machines/" + keyMachine + "/bottles?access_token=" + userIDMachine);
            Log.e("gymuserdetailsurl", "https://detoxbar.herokuapp.com/api/machines/" + keyMachine + "/bottles?access_token=" + userIDMachine);
        } else
            Toast.makeText(PrefedActivity.this, "No internet", Toast.LENGTH_SHORT).show();
    }

    public void processResults(Flavour[] flavours) {
        for (Flavour f : flavours) {
            flavoursgiven.add(f);
        }
        if (isConnected(PrefedActivity.this)) {
            new PreFedGET(PrefedActivity.this).execute("https://detoxbar.herokuapp.com/api/machines/" + keyMachine + "/prefedRecipes?access_token=" + userIDMachine);
            Log.e("prefed", "https://detoxbar.herokuapp.com/api/machines/" + keyMachine + "/prefedRecipes?access_token=" + userIDMachine);
        }
        else
            Toast.makeText(PrefedActivity.this, "No internet", Toast.LENGTH_SHORT).show();
    }

    public void processResults(Prefed[] prefeds) {
        arrayList.clear();
        if (prefeds.length != 0) {
            for (Prefed p : prefeds) {
                arrayList.add(p);
            }
            adapter.notifyDataSetChanged();
            if (isConnected(PrefedActivity.this))
                new UserDrinkGET(PrefedActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/" + key + "/userFavRecipes?access_token=" + userID);
            else
                Toast.makeText(PrefedActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            i.setClass(PrefedActivity.this, DrinksActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)flavoursgiven);
            i.putExtra("BUNDLE",args);
            startActivity(i);
            finish();
        }
    }

    public void processResultsUser(Prefed[] prefeds) {
        if (prefeds.length != 0) {
            for (Prefed p : prefeds) {
                arrayList.add(p);
            }
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(PrefedActivity.this, "No user saved drinks", Toast.LENGTH_SHORT).show();
        }
        arrayList.add(new Prefed("+ Make your own drink", "null", ""));
        adapter.notifyDataSetChanged();
    }
}

