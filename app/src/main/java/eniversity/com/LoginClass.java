package eniversity.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.eniversity.app.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import Commmon.Methods.AlertDialogClass;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import data.base.LoginTable;
import get.set.LoginGetSet;
import login.page.LoginMainPage;
import login.page.ProfilePictureClass;

/**
 * Created by shveta on 3/31/2016.
 */
public class LoginClass extends AppCompatActivity {

    private EditText editTextUserEmailAddress;
    private EditText editTextUserEmailPassword;
    private Button buttonUserLogin;
    private CheckBox checkBoxVisiblePassword1;
    private Toolbar toolbar;
    private TextView textViewHeading;

    private ImageView imageViewSearchIcon;
    private TextView txt_dia;
    private Button buttonOK;

    private ImageButton imageButtonCloseLogin;
    final String emailPattern = "^[a-zA-Z][a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        checkBoxVisiblePassword1 = (CheckBox) findViewById(R.id.checkBoxVisiblePassword1);

        imageButtonCloseLogin= (ImageButton) findViewById(R.id.imageButtonCloseLogin1);


        imageButtonCloseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginClass.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextUserEmailAddress = (EditText) findViewById(R.id.editTextUserEmailAddress);
        editTextUserEmailPassword = (EditText) findViewById(R.id.editTextUserEmailPassword);
        editTextUserEmailAddress.setTypeface(Typeface.SANS_SERIF);
        editTextUserEmailPassword.setTypeface(Typeface.SANS_SERIF);
        buttonUserLogin = (Button) findViewById(R.id.buttonUserLogin);
        if (editTextUserEmailPassword.getText().length() > 0) {
            checkBoxVisiblePassword1.setVisibility(View.VISIBLE);
        } else {
            checkBoxVisiblePassword1.setVisibility(View.GONE);
        }

        buttonUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUserEmailAddress.length() == 0) {
                    editTextUserEmailAddress.requestFocus();
                    AlertDialogClass customDialog = new AlertDialogClass(LoginClass.this, "Please enter email");
                    customDialog.show();

                    return;
                }
                if (!editTextUserEmailAddress.getText().toString().matches(emailPattern)) {
                    editTextUserEmailAddress.requestFocus();
                    AlertDialogClass customDialog = new AlertDialogClass(LoginClass.this, "Please enter valid email");
                    customDialog.show();
                    return;
                }
                if ( editTextUserEmailPassword.getText().length() == 0) {
                    // Toast.makeText(getApplicationContext(), "please enter login credentials", Toast.LENGTH_LONG).show();
                    AlertDialogClass customDialog = new AlertDialogClass(LoginClass.this, "Please enter password");
                    customDialog.show();
                }
                else
                {
                    if(!CommonMethods.test_internet(LoginClass.this))
                    {
                        AlertDialogClass customDialog = new AlertDialogClass(LoginClass.this, "No internet connection");
                        customDialog.show();
                    }else {
                        loginUser();
                    }
                }
            }
        });

        editTextUserEmailPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextUserEmailPassword.getText().length() > 0) {
                    checkBoxVisiblePassword1.setVisibility(View.VISIBLE);
                } else {
                    checkBoxVisiblePassword1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkBoxVisiblePassword1.setOnCheckedChangeListener(cbShowPwdsignupListener);
    }
    private CompoundButton.OnCheckedChangeListener cbShowPwdsignupListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int start, end;
            if (!isChecked) {
                start = editTextUserEmailPassword.getSelectionStart();
                end = editTextUserEmailPassword.getSelectionEnd();
                editTextUserEmailPassword.setTransformationMethod(new PasswordTransformationMethod());
                editTextUserEmailPassword.setSelection(start, end);
            } else {
                start = editTextUserEmailPassword.getSelectionStart();
                end = editTextUserEmailPassword.getSelectionEnd();
                editTextUserEmailPassword.setTransformationMethod(null);
                editTextUserEmailPassword.setSelection(start, end);
            }
        }
    };

    public void loginUser()
    {

        final ProgressDialog dialog;
        try{

            dialog = ProgressDialog.show(LoginClass.this, null,
                    "Please wait...", true);
            JSONObject loginJson = new JSONObject();
            loginJson.put("action", "login");
            loginJson.put("email",editTextUserEmailAddress.getText().toString());
            loginJson.put("password", editTextUserEmailPassword.getText().toString());
            loginJson.put("gcmid"," ");
            RequestQueue reqQueue = Volley.newRequestQueue(LoginClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    loginJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.v("the response is ", response.toString());
                    try {
                        String securekey="";
                        String imageurl_pic="";
                        String user_name="";
                        String userArray = response.getString("user");
                        JSONArray newJsonArray = new JSONArray(userArray);
                        for(int i=0;i<newJsonArray.length();i++)
                        {
                            if(newJsonArray.getJSONObject(i).getString("error").length() > 0)
                            {  dialog.dismiss();
                                dialog.cancel();
                                AlertDialogClass customDialog = new AlertDialogClass(LoginClass.this,newJsonArray.getJSONObject(i).getString("error"));
                                customDialog.show();
                                //Toast.makeText(getApplicationContext() ,newJsonArray.getJSONObject(i).getString("error"), Toast.LENGTH_LONG ).show();
                            }
                            else {

                                LoginGetSet loginData = new LoginGetSet();
                                loginData.setUserId(newJsonArray.getJSONObject(i).getString("userid"));
                                loginData.setUserName(newJsonArray.getJSONObject(i).getString("username"));
                                loginData.setUserpassword(editTextUserEmailPassword.getText().toString());
                                loginData.setUserEmail(newJsonArray.getJSONObject(i).getString("email"));
                                loginData.setProfilePic(newJsonArray.getJSONObject(i).getString("profileimage"));
                                securekey=newJsonArray.getJSONObject(i).getString("securedkey");

                                SharedPreferences sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("type","user");
                                editor.putString("secure_key",securekey);
                                editor.commit();
                                CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                                Log.v("secured key login", CommonMethods.securedkeyuser);

                                imageurl_pic=newJsonArray.getJSONObject(i).getString("profileimage");
                                user_name=newJsonArray.getJSONObject(i).getString("username");

                                SharedPreferences sharedPreferences1=getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA,Context.MODE_PRIVATE);
                               SharedPreferences.Editor editor1=sharedPreferences1.edit();
                                editor1.putString("profile_imageurl",imageurl_pic);
                                editor1.putString("username",user_name);
                                editor1.commit();
                                CommonMethods.profile_image_url=sharedPreferences1.getString("profile_imageurl","");
                                CommonMethods.userName=sharedPreferences1.getString("username","");
                                LoginTable loginTable = new LoginTable(LoginClass.this);
                                loginTable.open();
                                loginTable.addlogindetails(loginData);
                                loginTable.close();
                                CommonMethods.userid=newJsonArray.getJSONObject(i).getString("userid");
                                CommonMethods.user_name=newJsonArray.getJSONObject(i).getString("username");
                                        Intent intent = new Intent(LoginClass.this, LoginMainPage.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                        dialog.cancel();
                                        finish();
                            }
                        }

                       /* ProfilePictureClass serverData = new ProfilePictureClass(LoginClass.this);
                        String dealerName;
                        final String path;
                        path = serverData.getCurrentProfilePic();
                        CommonMethods.image_path=path;*/
                    }
                    catch (Exception r)
                    {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Email already exists
                    //Registered successfully

                    dialog.dismiss();
                    dialog.cancel();
                    error.printStackTrace();
                }
            });
           // loginJson.s
            customres.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            reqQueue.add(customres);
        }
        catch (Exception r)
        {

        }
    }

//    {"user":[{"userid":"6","username":"Shvets","email":"s@gmail.com","error":""}]}
}
