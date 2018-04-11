package com.innprojects.smartbar.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.innprojects.smartbar.AsyncTask.FlavoursPUT;
import com.innprojects.smartbar.AsyncTask.LogoutPOST_User;
import com.innprojects.smartbar.AsyncTask.LogoutPOST_UserDrinks;
import com.innprojects.smartbar.AsyncTask.UserCartPUT;
import com.innprojects.smartbar.AsyncTask.UserDrinkPOST;
import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.R;
import com.innprojects.smartbar.RCVadapters.RCVAdapterFinal;
import com.innprojects.smartbar.RCVadapters.RCVAdapterGiven;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class DrinksActivity extends BluetoothActivity implements RCVAdapterGiven.PlusMinusClickedListener {


    RecyclerView recyclerViewfinal;
    RecyclerView recyclerViewgiven;

    Button profile;
    Button makeDrink;

    ArrayList<Flavour> flavoursfinal;
    ArrayList<Flavour> flavoursgiven;

    RCVAdapterFinal adapterfinal;
    RCVAdapterGiven adaptergiven;

    SharedPreferences mPreferences;
    String access_token_machine, machineID;
    String access_token_user, userID;

    String cart;

    TextView totalml, totalcost;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_drinks);

        flavoursgiven = new ArrayList<Flavour>();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        flavoursgiven = (ArrayList<Flavour>) args.getSerializable("ARRAYLIST");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        access_token_machine = mPreferences.getString("keymachine", null);
        machineID = mPreferences.getString("userIDmachine", null);
        access_token_user = mPreferences.getString("key", null);
        userID = mPreferences.getString("userID", null);
        cart = mPreferences.getString("cart", null);

        totalcost = (TextView) findViewById(R.id.totalcost_tv);
        totalml = (TextView) findViewById(R.id.totalml_tv);

        profile = (Button) findViewById(R.id.profile_button);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, ProfileActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) flavoursgiven);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
                finish();
            }
        });
        profile.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    profile.setBackgroundColor(Color.parseColor("#F55E4E"));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    profile.setBackgroundColor(Color.parseColor("#ffc107"));
                }
                return false;
            }

        });

        makeDrink = (Button) findViewById(R.id.proceed_button);
        makeDrink.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    makeDrink.setBackgroundColor(Color.parseColor("#F55E4E"));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    makeDrink.setBackgroundColor(Color.parseColor("#ffc107"));
                }
                return false;
            }

        });
        makeDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart = Integer.toString(Integer.parseInt(cart) - Integer.parseInt(totalcost.getText().toString()));
                if (Integer.parseInt(cart) >= 0) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("cart", cart);
                    editor.apply();
                    editor.commit();
                    StringBuffer messageBuffer = new StringBuffer("*A000B000C000D000E000F000G000H000");
                    if (Integer.parseInt(totalml.getText().toString()) <= 300) {
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
                            if (isConnected(DrinksActivity.this)) {
                                new UserCartPUT(DrinksActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + access_token_user);
                                Log.e("UserCartPUT", "https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + access_token_user);
                            } else
                                Toast.makeText(DrinksActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(DrinksActivity.this, "Connect to bluetooth", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DrinksActivity.this, "Drink exceeded 300ml", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DrinksActivity.this, "Not enough balance", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerViewfinal = (RecyclerView) findViewById(R.id.recyclerviewfinal);
        recyclerViewgiven = (RecyclerView) findViewById(R.id.recyclerviewgiven);

        adaptergiven = new RCVAdapterGiven(this, flavoursgiven, this);
        recyclerViewgiven.setAdapter(adaptergiven);

        flavoursfinal = new ArrayList<Flavour>();
        adapterfinal = new RCVAdapterFinal(this, flavoursfinal);
        recyclerViewfinal.setAdapter(adapterfinal);

        LinearLayoutManager lmgiven = new LinearLayoutManager(this);
        LinearLayoutManager lmfinal = new LinearLayoutManager(this);

        recyclerViewgiven.setLayoutManager(lmgiven);
        lmgiven.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewfinal.setLayoutManager(lmfinal);
        lmfinal.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public void plusClicked(Flavour flavour, String quant) {
        int count = 0;
        for (Flavour f : flavoursfinal) {
            if (f.getIngredient_name().equals(flavour.getIngredient_name())) {
                f.setQuantity_selected(quant);
            } else
                count++;
        }
        if (flavoursfinal.size() == count)
            flavoursfinal.add(new Flavour(flavour.getIngredient_name(), flavour.getQuantity(), flavour.getId(), flavour.getMachineId(), flavour.getCost(), flavour.getBottle(), quant));
        adapterfinal.notifyDataSetChanged();
        int rupee = Integer.parseInt(totalcost.getText().toString());
        int ml = Integer.parseInt(totalml.getText().toString());
        ml += 10;
        rupee += Integer.parseInt(flavour.getCost()) * 10;
        if (ml > 300) {
            Toast.makeText(DrinksActivity.this, "Crossed 300ml mark !", Toast.LENGTH_SHORT).show();
        }
        totalcost.setText(Integer.toString(rupee));
        totalml.setText(Integer.toString(ml));
    }

    @Override
    public void minusClicked(Flavour flavour, String quant) {
        if (quant.equals("0")) {
            Iterator<Flavour> iterator = flavoursfinal.iterator();
            while (iterator.hasNext()) {
                Flavour f = iterator.next();
                if (f.getIngredient_name().equals(flavour.getIngredient_name())) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            int count = 0;
            for (Flavour f : flavoursfinal) {
                if (f.getIngredient_name().equals(flavour.getIngredient_name())) {
                    f.setQuantity_selected(quant);
                } else
                    count++;
            }
            if (flavoursfinal.size() == count)
                flavoursfinal.add(new Flavour(flavour.getIngredient_name(), flavour.getQuantity(), flavour.getId(), flavour.getMachineId(), flavour.getCost(), flavour.getBottle(), quant));
        }
        adapterfinal.notifyDataSetChanged();
        int rupee = Integer.parseInt(totalcost.getText().toString());
        int ml = Integer.parseInt(totalml.getText().toString());
        ml -= 10;
        rupee -= Integer.parseInt(flavour.getCost()) * 10;
        if (ml > 300) {
            Toast.makeText(DrinksActivity.this, "Crossed 300ml mark !", Toast.LENGTH_SHORT).show();
        }
        totalcost.setText(Integer.toString(rupee));
        totalml.setText(Integer.toString(ml));
    }

    public void processResultsCartUpdate() {
        openDialog();
    }

    public void processResultsPOST() {
        if (isConnected(DrinksActivity.this)) {
            new FlavoursPUT(this, flavoursgiven).execute();
        } else
            Toast.makeText(DrinksActivity.this, "No internet", Toast.LENGTH_SHORT).show();
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
                new LogoutPOST_UserDrinks(DrinksActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/logout?access_token=" + access_token_user);
                Intent i = new Intent();
                i.setClass(DrinksActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);

    }

    public void openDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_layout_prefed, null);
        final EditText name = (EditText) promptView.findViewById(R.id.name);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nam = name.getText().toString();
                        if(nam.equals(""))
                        {
                        }else {
                            if (isConnected(DrinksActivity.this)) {
                                new UserDrinkPOST(DrinksActivity.this, flavoursgiven, nam).execute("https://detoxbar.herokuapp.com/api/gym_users/" + userID + "/userFavRecipes?access_token=" + access_token_user);
                            } else
                                Toast.makeText(DrinksActivity.this, "No internet", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (isConnected(DrinksActivity.this)) {
                                    new FlavoursPUT(DrinksActivity.this, flavoursgiven).execute();
                                } else
                                    Toast.makeText(DrinksActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.RED);
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.GREEN);
    }
}
