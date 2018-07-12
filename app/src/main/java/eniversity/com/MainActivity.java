package eniversity.com;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.eniversity.app.R;

import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


import com.google.android.gms.common.ConnectionResult;


import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.*;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.Plus;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.logging.Logger;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import data.base.LoginTable;
import get.set.LoginGetSet;
import login.page.LoginMainPage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private Button buttonExplore;
    private Button buttonSignIn;
    private LinearLayout imageButtonFacebookIcon;
    private LinearLayout imageButtonLoginToApp;
    private LinearLayout imageButtonGooglePlus;
    private LinearLayout imageButtonTwitter;
    private SignInButton btn_sign_in;
    private CallbackManager callbackManager;
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    private static long back_pressed_time;
    private static long PERIOD = 2000;

    private static String APP_ID = "538785446304905";
    //start of twitter
    public static final int WEBVIEW_REQUEST_CODE = 100;
    static String TWITTER_CONSUMER_KEY = "hG7EoswOeAfk1IxktryVp42NE";
    static String TWITTER_CONSUMER_SECRET = "1DIpji2U0Jrrg3UuYERbz4ZUgQCnzGAaW8OhJnaxknQNsRzPHQ";

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "719871773798481920-kM6ax8VxqNV6FK0MDbzBv7PP1BkTrKC";
    static final String PREF_KEY_OAUTH_SECRET = "TnZLZFZCAvE7vFzATolgoMLFLXjQ1ZCJKSAAnYiWo3hR7";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-twitter";
    public static final String OAUTH_CALLBACK_HOST = "callback";
    public static final String TWITTER_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
            + "://" + OAUTH_CALLBACK_HOST;


    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    //end of twitter

    //start of twitter
    private static Twitter twitter;
    private static RequestToken requestToken;

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

    // Internet Connection detector
    private ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    //end of twitter


    /**
     * Gmail
     */
    private static final int RC_SIGN_IN = 0;
    private static GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;


    String email = "";
    private ProfileTracker mProfileTracker;

    private String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String xiaomi = "Xiaomi"; final String CALC_PACKAGE_NAME = "com.miui.securitycenter";
        final String CALC_PACKAGE_ACITIVITY = "com.miui.permcenter.autostart.AutoStartManagementActivity";
        if (deviceManufacturer.equalsIgnoreCase(xiaomi))
        { DisplayUtils.showDialog(activity, "Ask for permission", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                try { Intent intent = new Intent();
                    intent.setComponent(new ComponentName(CALC_PACKAGE_NAME, CALC_PACKAGE_ACITIVITY));
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                   e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {

            } }); }*/

        imageButtonTwitter = (LinearLayout) findViewById(R.id.imageButtonTwitterIcon);
        //start of twitter
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        cd = new ConnectionDetector(MainActivity.this);

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Check if twitter keys are set
        if (TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
            // stop executing code by return
            return;
        }

        // All UI elements


        // Shared Preferences
        mSharedPreferences = MainActivity.this.getSharedPreferences("MyPref", 0);

        /** Twitter login button click event will call loginToTwitter() function */
        imageButtonTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginToTwitter();
            }
        });


        /** This if conditions is tested once is redirected from twitter page. Parse the uri to get
         * oAuth Verifier */
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);


                    // Shared Preferences
                    SharedPreferences.Editor e = mSharedPreferences.edit();

                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes

                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

                    // Hide login button


                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    Toast.makeText(MainActivity.this, "" + userID, Toast.LENGTH_SHORT).show();
                    String name = accessToken.getScreenName();
                    User user = twitter.showUser(userID);
                    String username = user.getName();

                    // Displaying in xml ui
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
        //end of twitter
        facebook_id = f_name = m_name = l_name = gender = profile_image = full_name = email_id = "";
        getFbKeyHash("onlineexam.infomaze.com.onlineexamination");
        FacebookSdk.sdkInitialize(MainActivity.this);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(MainActivity.this);
        AppEventsLogger.activateApp(this);
        buttonExplore = (Button) findViewById(R.id.buttonExplore);
        buttonExplore.setOnClickListener(openBrowswer);


        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationClass.class);
                i.putExtra("activityIntent", "MainActivity");
                startActivity(i);


            }
        });
        findViewById(R.id.imageButtonGooglePlus).setOnClickListener(this);
        try {
           /* mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                    .addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();*/
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API,Plus.PlusOptions.builder().build())
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(new Scope(Scopes.PLUS_LOGIN))
                    .addScope(new Scope(Scopes.PLUS_ME))
                    .build();
            mGoogleApiClient.connect();

        } catch (Exception e) {
            Log.getStackTraceString(e);
        }


        imageButtonLoginToApp = (LinearLayout) findViewById(R.id.imageButtonLoginToApp);
        imageButtonLoginToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, LoginClass.class);
                startActivity(i);

            }
        });

        imageButtonFacebookIcon = (LinearLayout) findViewById(R.id.imageButtonFacebookIcon);


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            String facebook_id = full_name = profile_image = "";

            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();

                if (profile != null) {
                    Log.i("the response is ", profile.toString());
                    facebook_id = profile.getId();
                    Log.i("the response is ", facebook_id.toString());
                    f_name = profile.getFirstName();
                    Log.i("the response is ", f_name.toString());
                    m_name = profile.getMiddleName();
                    Log.i("the response is ", m_name.toString());
                    l_name = profile.getLastName();
                    Log.i("the response is ", l_name.toString());
                    full_name = profile.getName();
                    Log.i("the response is ", full_name.toString());
                    profile_image = profile.getProfilePictureUri(400, 400).toString();
                    CommonMethods.socialnet(facebook_id, full_name, profile_image, email, MainActivity.this);

                } else {
                    mProfileTracker = new ProfileTracker() {
                        Profile profile3 = null;

                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile 1", profile2.getFirstName());
                            Log.i("the response is ", profile2.toString());
                            facebook_id = profile2.getId();
                            Log.i("the response is ", facebook_id.toString());
                            f_name = profile2.getFirstName();
                            Log.i("the response is ", f_name.toString());
                            m_name = profile2.getMiddleName();
                            Log.i("the response is ", m_name.toString());
                            l_name = profile2.getLastName();
                            Log.i("the response is ", l_name.toString());
                            full_name = profile2.getName();
                            Log.i("the response is ", full_name.toString());

                            profile_image = profile2.getProfilePictureUri(400, 400).toString();

                            mProfileTracker.stopTracking();
                            CommonMethods.socialnet(facebook_id, full_name, profile_image, email, MainActivity.this);

                        }

                    };
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //facebook button click
        imageButtonFacebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));

            }
        });
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


    }

    public void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }


    private View.OnClickListener openBrowswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(MainActivity.this, ExploreClass.class);
            startActivity(i);
            finish();
        }
    };


    //start of twitter
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                //final Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
                //this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));


                final Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL,requestToken.getAuthenticationURL());
                startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(MainActivity.this, "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        if (requestCode == WEBVIEW_REQUEST_CODE) {

            try {
                String verifier = data.getExtras().getString(
                        URL_TWITTER_OAUTH_VERIFIER);

                AccessToken accessToken = twitter.getOAuthAccessToken(
                        requestToken, verifier);

                long userID = accessToken.getUserId();
                final User user = twitter.showUser(userID);
                String username = user.getName();


                URL userProfile = user.getProfileImageUrlHttps();
                long id = user.getId();
                String sceenName = user.getScreenName();


                String twitterDeatls = "Twitter user details "
                        + "User name : " + username + " User profile: "
                        + userProfile + " id: " + id + " Sceen name :"
                        + sceenName;

                sendTwitterData("" + userID, username, "", "", MainActivity.this);


            } catch (Exception e) {
                //Log.getStackTraceString(e);
              // Log.e("Twitter Login Failed", e.getMessage());
                //Log.e("Twitter Login Failed", e.getMessage());
                e.printStackTrace();
            }
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }
    //end of twitter

    public void sendTwitterData(String twitter_id, String full_name, String profile_image, String email, final Context context) {

        try {

            JSONObject sendRequestObjects = new JSONObject();
            sendRequestObjects.put("action", "loginforsocialmediaapp");
            sendRequestObjects.put("gcmid", "");
            sendRequestObjects.put("device", "Android");
            sendRequestObjects.put("deviceid", "1");
            sendRequestObjects.put("socialappid", twitter_id);
            sendRequestObjects.put("name", full_name);
            sendRequestObjects.put("email", "");


            Log.v("request", sendRequestObjects.toString());
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendRequestObjects, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());


                    try {
                        String browseCatalogueitems = response.getString("useradd");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);

                        for (int i = 0; i < browseArray.length(); i++) {
                            String facebookUserId = browseArray.getJSONObject(i).getString("userid");
                            String facebookName = browseArray.getJSONObject(i).getString("name");
                            String facebookEmail = browseArray.getJSONObject(i).getString("email");
                            String facebookProfileImage = browseArray.getJSONObject(i).getString("profileimage");
                            String isalreadyexist=browseArray.getJSONObject(i).getString("isalreadyexist");

                            SharedPreferences sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("type","user");
                            editor.putString("secure_key",browseArray.getJSONObject(i).getString("securedkey"));
                            editor.commit();
                            CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                            Log.v("secured key login", CommonMethods.securedkeyuser);

                            LoginGetSet loginData = new LoginGetSet();
                            loginData.setUserId(facebookUserId);
                            loginData.setUserName(facebookName);
                            loginData.setUserpassword("");
                            loginData.setUserEmail(facebookEmail);

                            LoginTable loginTable = new LoginTable(context);
                            loginTable.open();
                            loginTable.addlogindetails(loginData);
                            loginTable.close();
                            CommonMethods.userid = facebookUserId;
                            CommonMethods.user_name=facebookName;
                            if(isalreadyexist.equals("1")){
                                Intent intent = new Intent(MainActivity.this, LoginMainPage.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this, CategoryList.class);

                                startActivity(intent);

                            }


                           /* Intent intent = new Intent(MainActivity.this, LoginMainPage.class);
                            startActivity(intent);*/


                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Log.v("error", error.toString());
                    Toast.makeText(MainActivity.this, "Sorry something went wrong..Please try again later", Toast.LENGTH_SHORT).show();

                    error.printStackTrace();
                }
            });
            requestQueue.add(customres);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
       // mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {

                try {
                    if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);

                        String full_name = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName();
                        Log.d("error", full_name.toString());

                        String id = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getId();
                        Log.d("error", id.toString());

                        String profile_image = "";

                        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                        Log.d("email", email.toString());
if(full_name.length()==0) {

    signOutFromGplus();
    CommonMethods.socialnet(id, email.split("\\@")[0], profile_image, email, this);

}
                        else{
    signOutFromGplus();
    CommonMethods.socialnet(id, full_name, profile_image, email, this);
                        }
                        // dialog.dismiss();
                    } else {

                        //  Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mGoogleApiClient.connect();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
            }
        } else {
            try {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                    Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);

                    String full_name = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName();
                    Log.d("error", full_name.toString());

                    String id = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getId();
                    Log.d("id", id.toString());

                    String profile_image = "";

                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    Log.d("email", email.toString());
                    if(full_name.length()==0) {

                        signOutFromGplus();
                        CommonMethods.socialnet(id, email.split("\\@")[0], profile_image, email, this);
                    }
                    else{
                        signOutFromGplus();
                        CommonMethods.socialnet(id, full_name, profile_image, email, this);
                    }
                    // dialog.dismiss();
                } else {

                    //  Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mGoogleApiClient.connect();
            }
        }


    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API,Plus.PlusOptions.builder().build())
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(new Scope(Scopes.PLUS_LOGIN))
                    .addScope(new Scope(Scopes.PLUS_ME))
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButtonGooglePlus && !mGoogleApiClient.isConnected()) {
            if (mConnectionResult == null) {

            } else {
                try {
                    mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
                } catch (IntentSender.SendIntentException e) {
                    // Try connecting again.
                    mConnectionResult = null;
                    mGoogleApiClient.connect();
                }
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }


    @Override
    public void onResult(Result result) {

    }

    @Override
    public void onBackPressed() {

        if (back_pressed_time + PERIOD > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finishAffinity();
        }
        else
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        back_pressed_time = System.currentTimeMillis();
    }
}



