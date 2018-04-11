package com.innprojects.smartbar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.innprojects.smartbar.AsyncTask.LoginPOST_Machine;
import com.innprojects.smartbar.R;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivityMachine extends AppCompatActivity {

    SharedPreferences mPreferences;
    EditText userNameEditTextMachine, passwordEditTextMachine;
    String userNameMachine, passwordMachine;
    CheckBox checkMachine, rem;
    String keyMachine, userIDMachine;
    Boolean flagrem = false;

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
        setContentView(R.layout.activity_loginmachine);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (mPreferences.contains("usernamemachine")) {
            // start Main activity

            Intent i = new Intent();
            i.setClass(LoginActivityMachine.this, BluetoothActivity.class);
            startActivity(i);
            finish();
        } else {
            // ask him to enter his credentials
            final Button loginButtonmachine = (Button) findViewById(R.id.login_buttonmachine);
            userNameEditTextMachine = (EditText) findViewById(R.id.editTextmachine);
            passwordEditTextMachine = (EditText) findViewById(R.id.editText2machine);

//            forgotpass = (TextView) findViewById(R.id.forgotpass);
            checkMachine = (CheckBox) findViewById(R.id.checkboxmachine);
            checkMachine.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                passwordEditTextMachine.setTransformationMethod(null);
                            } else {
                                passwordEditTextMachine.setTransformationMethod(new PasswordTransformationMethod());
                            }
                        }
                    }
            );
            rem = (CheckBox) findViewById(R.id.checkboxmachine2);
            rem.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                flagrem=true;
                            } else {
                                flagrem=false;
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.remove("usernamemachinerem");
                                editor.remove("passwordrem");
                                editor.apply();
                            }
                        }
                    }
            );

            if (mPreferences.contains("usernamemachinerem") && mPreferences.contains("passwordrem"))
            {
                userNameEditTextMachine.setText(mPreferences.getString("usernamemachinerem", ""));
                passwordEditTextMachine.setText(mPreferences.getString("passwordrem", ""));
                rem.setChecked(true);
            }

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
            loginButtonmachine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userNameMachine = userNameEditTextMachine.getText().toString();
                    passwordMachine = passwordEditTextMachine.getText().toString();
                    if (isConnected(LoginActivityMachine.this))
                        new LoginPOST_Machine(LoginActivityMachine.this, userNameMachine, passwordMachine).execute("https://detoxbar.herokuapp.com/api/machines/login");
                    else
                        Toast.makeText(LoginActivityMachine.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            });
            loginButtonmachine.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        loginButtonmachine.setBackground(getResources().getDrawable(R.drawable.buttonlogin_background));
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        loginButtonmachine.setBackgroundColor(Color.parseColor("#f5f5f5"));
                    }
                    return false;
                }

            });
        }
    }

    public void processResultsLogin(String s) throws JSONException {
        if (s.equals("false : ")) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonResponse = new JSONObject(s);
            keyMachine = jsonResponse.getString("id");
            userIDMachine = jsonResponse.getString("userId");

            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("usernamemachine", userNameMachine);
            editor.putString("keymachine", keyMachine);
            editor.putString("userIDmachine", userIDMachine);
            editor.apply();

            if (flagrem)
            {
                editor.putString("usernamemachinerem", userNameMachine);
                editor.putString("passwordrem", passwordMachine);
                editor.apply();
            }

            Intent i = new Intent();
            i.setClass(LoginActivityMachine.this, BluetoothActivity.class);
            startActivity(i);
            finish();
        }
    }
}
