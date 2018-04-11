package com.innprojects.smartbar.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.innprojects.smartbar.AsyncTask.FlavoursPUT;
import com.innprojects.smartbar.AsyncTask.LogoutPOST_User;
import com.innprojects.smartbar.AsyncTask.UserCartPUT;
import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.Models.Prefed;
import com.innprojects.smartbar.R;
import com.innprojects.smartbar.RCVadapters.RCVAdapterFinal;

import java.util.ArrayList;

public class FinalActivity extends BluetoothActivity {

    RecyclerView recyclerViewfinal;
    ArrayList<Flavour> flavoursfinal, flavoursgiven;
    RCVAdapterFinal adapterfinal;
    Prefed prefed;
    TextView totalml, totalcost, prefedname;
    Button make;
    int tml, tc;
    String cart, name;

    SharedPreferences mPreferences;
    String access_token_machine, machineID;
    String access_token_user, userID;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        access_token_machine = mPreferences.getString("keymachine", null);
        machineID = mPreferences.getString("userIDmachine", null);
        access_token_user = mPreferences.getString("key", null);
        userID = mPreferences.getString("userID", null);
        cart = mPreferences.getString("cart", null);

        recyclerViewfinal = (RecyclerView) findViewById(R.id.recyclerviewfinal);
        totalcost = (TextView) findViewById(R.id.totalcost_tv);
        totalml = (TextView) findViewById(R.id.totalml_tv);
        prefedname = (TextView) findViewById(R.id.prefedname);
        make = (Button) findViewById(R.id.make);

        Intent intent = getIntent();
        prefed = (Prefed) intent.getSerializableExtra("prefed");
        Bundle args = intent.getBundleExtra("BUNDLE");
        flavoursgiven = (ArrayList<Flavour>) args.getSerializable("ARRAYLIST");
        name = (String) intent.getStringExtra("name");
        prefedname.setText(name);

        flavoursfinal = new ArrayList<Flavour>();
        flavoursfinal.add(new Flavour(flavoursgiven.get(0).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(0).getQuantity())-Integer.parseInt(prefed.getQuantity_1())), flavoursgiven.get(0).getId(), flavoursgiven.get(0).getMachineId(), prefed.getCost_per_ml_1(), prefed.getBottle_name_1(), prefed.getQuantity_1()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(1).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(1).getQuantity())-Integer.parseInt(prefed.getQuantity_2())), flavoursgiven.get(1).getId(), flavoursgiven.get(1).getMachineId(), prefed.getCost_per_ml_2(), prefed.getBottle_name_2(), prefed.getQuantity_2()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(2).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(2).getQuantity())-Integer.parseInt(prefed.getQuantity_3())), flavoursgiven.get(2).getId(), flavoursgiven.get(2).getMachineId(), prefed.getCost_per_ml_3(), prefed.getBottle_name_3(), prefed.getQuantity_3()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(3).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(3).getQuantity())-Integer.parseInt(prefed.getQuantity_4())), flavoursgiven.get(3).getId(), flavoursgiven.get(3).getMachineId(), prefed.getCost_per_ml_4(), prefed.getBottle_name_4(), prefed.getQuantity_4()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(4).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(4).getQuantity())-Integer.parseInt(prefed.getQuantity_5())), flavoursgiven.get(4).getId(), flavoursgiven.get(4).getMachineId(), prefed.getCost_per_ml_5(), prefed.getBottle_name_5(), prefed.getQuantity_5()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(5).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(5).getQuantity())-Integer.parseInt(prefed.getQuantity_6())), flavoursgiven.get(5).getId(), flavoursgiven.get(5).getMachineId(), prefed.getCost_per_ml_6(), prefed.getBottle_name_6(), prefed.getQuantity_6()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(6).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(6).getQuantity())-Integer.parseInt(prefed.getQuantity_7())), flavoursgiven.get(6).getId(), flavoursgiven.get(6).getMachineId(), prefed.getCost_per_ml_7(), prefed.getBottle_name_7(), prefed.getQuantity_7()));
        flavoursfinal.add(new Flavour(flavoursgiven.get(7).getIngredient_name(), Integer.toString(Integer.parseInt(flavoursgiven.get(7).getQuantity())-Integer.parseInt(prefed.getQuantity_8())), flavoursgiven.get(7).getId(), flavoursgiven.get(7).getMachineId(), prefed.getCost_per_ml_8(), prefed.getBottle_name_8(), prefed.getQuantity_8()));

        adapterfinal = new RCVAdapterFinal(this, flavoursfinal);
        recyclerViewfinal.setAdapter(adapterfinal);

        LinearLayoutManager lmfinal = new LinearLayoutManager(this);
        recyclerViewfinal.setLayoutManager(lmfinal);
        lmfinal.setOrientation(LinearLayoutManager.VERTICAL);

        tml = Integer.parseInt(prefed.getQuantity_1()) + Integer.parseInt(prefed.getQuantity_2()) + Integer.parseInt(prefed.getQuantity_2()) + Integer.parseInt(prefed.getQuantity_4())
                + Integer.parseInt(prefed.getQuantity_5()) + Integer.parseInt(prefed.getQuantity_6()) + Integer.parseInt(prefed.getQuantity_7()) + Integer.parseInt(prefed.getQuantity_8());

        tc = Integer.parseInt(prefed.getCost_per_ml_1()) * Integer.parseInt(prefed.getQuantity_1()) + Integer.parseInt(prefed.getCost_per_ml_2()) * Integer.parseInt(prefed.getQuantity_2()) + Integer.parseInt(prefed.getCost_per_ml_3()) * Integer.parseInt(prefed.getQuantity_3())
                + Integer.parseInt(prefed.getCost_per_ml_4()) * Integer.parseInt(prefed.getQuantity_4()) + Integer.parseInt(prefed.getCost_per_ml_5()) * Integer.parseInt(prefed.getQuantity_5()) + Integer.parseInt(prefed.getCost_per_ml_6()) * Integer.parseInt(prefed.getQuantity_6())
                + Integer.parseInt(prefed.getCost_per_ml_7()) * Integer.parseInt(prefed.getQuantity_7()) + Integer.parseInt(prefed.getCost_per_ml_8()) * Integer.parseInt(prefed.getQuantity_8());

        totalml.setText(Integer.toString(tml));
        totalcost.setText(Integer.toString(tc));

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart = Integer.toString(Integer.parseInt(cart) - tc);
                if (Integer.parseInt(cart) >= 0) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("cart",cart);
                    editor.apply();
                    editor.commit();
                    StringBuffer messageBuffer = new StringBuffer("*A000B000C000D000E000F000G000H000");
                    for (Flavour f : flavoursfinal) {
                        int index = messageBuffer.indexOf(f.getBottle());
                        if (Integer.parseInt(f.getQuantity_selected()) <= 99 && Integer.parseInt(f.getQuantity_selected()) > 9) {
                            messageBuffer.replace(index + 2, index + 4, f.getQuantity_selected());
                        } else if (Integer.parseInt(f.getQuantity_selected()) <= 999 && Integer.parseInt(f.getQuantity_selected()) >= 100) {
                            messageBuffer.replace(index + 1, index + 4, f.getQuantity_selected());
                        } else {
                            messageBuffer.replace(index + 3, index + 4, f.getQuantity_selected());
                        }
                    }
                    String msg = messageBuffer.toString();
                    Log.e("String built", msg);
                    try {
                        write(msg);
                        if (isConnected(FinalActivity.this)) {
                            new UserCartPUT(FinalActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + access_token_user);
                            Log.e("UserCartPUT", "https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + access_token_user);
                        } else
                            Toast.makeText(FinalActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(FinalActivity.this, "Connect to bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FinalActivity.this, "Not enough balance", Toast.LENGTH_SHORT).show();
                }
            }
        });
        make.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    make.setBackgroundColor(Color.parseColor("#F55E4E"));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    make.setBackgroundColor(Color.parseColor("#ffc107"));
                }
                return false;
            }

        });
    }

    public void processResultsCartUpdate() {
        if (isConnected(FinalActivity.this)) {
            new FlavoursPUT(this, flavoursfinal).execute();
        } else
            Toast.makeText(FinalActivity.this, "No internet", Toast.LENGTH_SHORT).show();
    }

    public void processResultsFlavour() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing it...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.remove("key").apply();
                editor.remove("userID").apply();
                editor.remove("username").apply();
                new LogoutPOST_User(FinalActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/logout?access_token=" + access_token_user);
                Intent i = new Intent();
                i.setClass(FinalActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);

    }
}
