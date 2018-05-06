package jhotel.jhotel_android_anggi;

//import android.support.v7.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            final EditText emailInput = (EditText) findViewById(R.id.inputEmail);
            final EditText passInput = (EditText) findViewById(R.id.inputPass);
            final Button loginButton = (Button) findViewById(R.id.buttonLogin);
            final TextView registerClickable = (TextView) findViewById(R.id.registerClickable);
            registerClickable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent regisInt = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(regisInt);
                }
            });
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = emailInput.getText().toString();
                    final String password = passInput.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String> () {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                if(jsonResponse!=null) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Success")
                                            .create()
                                            .show();
                                    Intent mainInt = new Intent(LoginActivity.this, MainActivity.class);
                                    mainInt.putExtra("id_customer", jsonResponse.getInt("id"));
                                    LoginActivity.this.startActivity(mainInt);
                                }
                            } catch (JSONException e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed.")
                                        .create()
                                        .show();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(email,password,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            });
        }
    }
