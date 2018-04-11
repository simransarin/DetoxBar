package com.innprojects.smartbar.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.innprojects.smartbar.AsyncTask.LogoutPOST_User;
import com.innprojects.smartbar.Models.Flavour;
import com.innprojects.smartbar.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    ArrayList<Flavour> flavoursgiven;

    Button drinksa_button;
    Button logout;
    String access_token_user;

    TextView name, mem_no, add, email, number, cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_profile);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        access_token_user = mPreferences.getString("key", null);

        name = (TextView) findViewById(R.id.name_tv);
        name.setText(mPreferences.getString("name", null));
        mem_no = (TextView) findViewById(R.id.memno_tv);
        mem_no.setText(mPreferences.getString("mem_no", null));
        add = (TextView) findViewById(R.id.address_tv);
        add.setText(mPreferences.getString("add", null));
        email = (TextView) findViewById(R.id.email_tv);
        email.setText(mPreferences.getString("email", null));
        number = (TextView) findViewById(R.id.number_tv);
        number.setText(mPreferences.getString("number", null));
        cart = (TextView) findViewById(R.id.cartvalue_tv);
        cart.setText(mPreferences.getString("cart", null));

        flavoursgiven = new ArrayList<Flavour>();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        flavoursgiven = (ArrayList<Flavour>) args.getSerializable("ARRAYLIST");

        drinksa_button = (Button) findViewById(R.id.drinkactivity_button);
        drinksa_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, DrinksActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)flavoursgiven);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
                finish();
            }
        });
        drinksa_button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drinksa_button.setBackgroundColor(Color.parseColor("#F55E4E"));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drinksa_button.setBackgroundColor(Color.parseColor("#ffc107"));
                }
                return false;
            }

        });

        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        logout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    logout.setBackgroundColor(Color.parseColor("#F55E4E"));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    logout.setBackgroundColor(Color.parseColor("#ffc107"));
                }
                return false;
            }

        });
    }

    public void openDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.remove("key").apply();
                        editor.remove("userID").apply();
                        editor.remove("username").apply();
                        new LogoutPOST_User(ProfileActivity.this).execute("https://detoxbar.herokuapp.com/api/gym_users/logout?access_token=" + access_token_user);
                        Intent i = new Intent();
                        i.setClass(ProfileActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }
}
