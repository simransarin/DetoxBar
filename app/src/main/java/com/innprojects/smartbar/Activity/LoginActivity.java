package com.innprojects.smartbar.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innprojects.smartbar.AsyncTask.LoginPOST_User;
import com.innprojects.smartbar.AsyncTask.LogoutPOST_Machine;
import com.innprojects.smartbar.AsyncTask.LogoutPOST_User;
import com.innprojects.smartbar.AsyncTask.UserDetailsGET;
import com.innprojects.smartbar.Models.User;
import com.innprojects.smartbar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static String access_token;
    SharedPreferences mPreferences;
    TextView forgotpass;
    EditText userNameEditText;
    EditText passwordEditText;
    String userName;
    String password;
    CheckBox check;
    String key, userID;
    String name, mem_no, email, add, number, cart;
    ImageView logout;

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
        setContentView(R.layout.activity_login);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (mPreferences.contains("username")) {
            // start Main activity
            Intent i = new Intent();
            i.setClass(LoginActivity.this, DrinksActivity.class);
            startActivity(i);
            finish();
        } else {
            // ask him to enter his credentials
            access_token = mPreferences.getString("keymachine", null);
            logout = (ImageView) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog();
                }
            });
            final Button loginButton = (Button) findViewById(R.id.login_button);
            userNameEditText = (EditText) findViewById(R.id.editText);
            passwordEditText = (EditText) findViewById(R.id.editText2);
//            forgotpass = (TextView) findViewById(R.id.forgotpass);
            check = (CheckBox) findViewById(R.id.checkbox);
            check.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                passwordEditText.setTransformationMethod(null);
                            } else {
                                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                            }
                        }
                    }
            );

//            forgotpass.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                    builder.setTitle("Forgot password");
//
////                  Set up the input
//                    final EditText input = new EditText(LoginActivity.this);
////                  Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                    input.setInputType(InputType.TYPE_CLASS_TEXT);
//                    input.setHint("Enter username");
//                    builder.setView(input);
//                    builder.setCancelable(false);
//
////                  Set up the buttons
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            m_Text = input.getText().toString();
//                            new ForgotPassGETtask(LoginActivity.this).execute("https://biometric-app.herokuapp.com/reset-pass/" + m_Text);
//                            Toast.makeText(LoginActivity.this,"If username valid, Password reset email sent",Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//                }
//            });
//
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = userNameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    if (isConnected(LoginActivity.this))
                        new LoginPOST_User(LoginActivity.this, userName, password).execute(" https://detoxbar.herokuapp.com/api/gym_users/login");
                    else
                        Toast.makeText(LoginActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            });
            loginButton.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        loginButton.setBackground(getResources().getDrawable(R.drawable.buttonlogin_background));
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        loginButton.setBackgroundColor(Color.parseColor("#f5f5f5"));
                    }
                    return false;
                }

            });
        }
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
                        String usermachine = "";
                        String pass = "";
                        if (mPreferences.contains("usernamemachinerem") && mPreferences.contains("passwordrem")) {
                            usermachine = mPreferences.getString("usernamemachinerem", "");
                            pass = mPreferences.getString("passwordrem", "");
                        }
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.clear();   // This will delete all your preferences, check how to delete just one
                        editor.commit();
                        editor.apply();
                        if (!usermachine.equals("") && !pass.equals("")) {
                            editor.putString("usernamemachinerem", usermachine);
                            editor.putString("passwordrem", pass);
                            editor.apply();
                        }
                        new LogoutPOST_Machine(LoginActivity.this).execute("https://detoxbar.herokuapp.com/api/machines/logout?access_token=" + access_token);
                        Intent i = new Intent();
                        i.setClass(LoginActivity.this, LoginActivityMachine.class);
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

    public void processResultsLogin(String s) throws JSONException {
        if (s.equals("false : ")) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonResponse = new JSONObject(s);
            key = jsonResponse.getString("id"); //access_token
            userID = jsonResponse.getString("userId");

            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("username", userName);
            editor.putString("password", password);
            editor.putString("key", key);
            editor.putString("userID", userID);
            editor.apply();

            if (isConnected(LoginActivity.this)) {
                new UserDetailsGET(this).execute("https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + key);
                Log.e("gymuserdetailsurl", "https://detoxbar.herokuapp.com/api/gym_users/" + userID + "?access_token=" + key);
            } else
                Toast.makeText(LoginActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void processResultsProfile(User user) {
        name = user.getName();
        mem_no = user.getRegistration_number();
        email = user.getEmail();
        add = user.getAddress();
        number = user.getPhone_number();
        cart = user.getCart_value();

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("name", name);
        editor.putString("mem_no", mem_no);
        editor.putString("email", email);
        editor.putString("add", add);
        editor.putString("number", number);
        editor.putString("cart", cart);
        editor.apply();

        Intent i = new Intent();
        i.setClass(LoginActivity.this, PrefedActivity.class);
        startActivity(i);
        finish();
    }
}
