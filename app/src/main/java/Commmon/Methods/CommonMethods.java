package Commmon.Methods;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


import data.base.LoginTable;
import eniversity.com.CategoryList;
import eniversity.com.MainActivity;
import get.set.CategoryGetSet;
import get.set.LoginGetSet;
import login.page.LoginMainPage;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by shveta on 3/28/2016.
 */
public class CommonMethods {

    //local url
//public static String url = "http://192.168.1.26/OnlineExamination/MobileAPI.php/";

//live url

    //public static String url ="http://www.microbharat.com/onlineexamination/API/MobileAPI.php/";
//public static String url = "http://www.projectsouk.com/onlineexamination/API/MobileAPI.php/";
    //live URL Version 1

   public static String url ="http://www.microbharat.com/onlineexamination_v1/API/MobileAPI.php/";
public static final String MyPREFERENCES = "MyPrefs";
    public static final String CATEGORY_ID = "category_id";
    public static String SHARED_PREFERENCE="Online_Exam";
    public static  String category_Id="";
    public static String userid ="";
    public static  String userid1="";
    public static String user_name ="";
    public static String image_path="";
    public static String securedkeyuser="";
    public static String securedkeyguest="";
    public static ArrayList<CategoryGetSet> categoryGetSetArrayList;
    public static Bitmap bitmapimagemain=null;
    public static final String MyPREFERENCES_CAMERA = "MyPrefs_Cam";
    public static  String profile_image_url="";
    public static String userName="";
    public static String fromclass="";


    public static String generateRandomForCache() {
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        String result = "";
        for (int i = 0; i < 5; i++) {
            result += numbers.get(i).toString();
        }
        System.out.println(result);
        return result;
    }

     public static String compressImage(String path,int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            String s = "tmp.jpg";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }
    public static void hideKeyboard(Context context,View view ) {

        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String postDate(String jsonString, String methodName, String authenticationKey, String companyKey) {
        String json = "error";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url + "?cache=" + generateRandomForCache());


                httppost.addHeader("type", "user");

            httppost.addHeader("Content-Type", "application/json;");
            httppost.addHeader("securedkey" ,CommonMethods.securedkeyuser);
            StringEntity ent = new StringEntity(jsonString.toString());
            ent.setContentEncoding("UTF-8");
            httppost.setEntity(ent);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            json = EntityUtils.toString(entity);
            String jos = json;
            Log.i("reg", jos);
        } catch (Exception e) {
            e.printStackTrace();
            Log.getStackTraceString(e);
            return json;
        }
        return json;
    }

/*    private void scaleImage(ImageView view) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(250);
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + Integer.toString(width));
        Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }*/

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }


    public static void showKeyboard(Context context,View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    public static void socialnet(String facebook_id, String full_name, String profile_image, String email, final Context context){
        try {
            JSONObject sendRequestObjects = new JSONObject();
        sendRequestObjects.put("action", "loginforsocialmediaapp");
        sendRequestObjects.put("gcmid", "");
        sendRequestObjects.put("device", "Android");
        sendRequestObjects.put("deviceid", "1");
        sendRequestObjects.put("socialappid", facebook_id);
        sendRequestObjects.put("name", full_name);
        sendRequestObjects.put("email", email);


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
                    String    facebookUserId = browseArray.getJSONObject(i).getString("userid");
                        String   facebookName = browseArray.getJSONObject(i).getString("name");
                        String  facebookEmail = browseArray.getJSONObject(i).getString("email");
                        String  facebookProfileImage = browseArray.getJSONObject(i).getString("profileimage");
                        String isalreadyexist=browseArray.getJSONObject(i).getString("isalreadyexist");


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

                        SharedPreferences sharedPreferences=context.getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("type","user");
                        editor.putString("secure_key",browseArray.getJSONObject(i).getString("securedkey"));
                        editor.commit();
                        CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                        Log.v("secured key login", CommonMethods.securedkeyuser);

                        if(isalreadyexist.equals("1")){
                            Intent intent = new Intent(context, LoginMainPage.class);
                            context.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(context, CategoryList.class);
                            context.startActivity(intent);
                        }
                       /* Intent intent = new Intent(context, LoginMainPage.class);
                        context.startActivity(intent);*/


                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.v("error", error.toString());

                error.printStackTrace();
            }
        });
        requestQueue.add(customres);


    } catch (Exception e) {
        e.printStackTrace();
    }

}



    public static String sendFilesToServer(String name, String email, String password, String mobilenum, String location, String dob,
                                           String imagefile, String gcmid, String device, String deviceid,Context context) {
        String listId = "";
        HttpPost postRequest;
        if (imagefile != null) {
            try {
               // context.getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA, Context.MODE_PRIVATE).edit().clear().commit();

                HttpClient httpClient = new DefaultHttpClient();


                if (CommonMethods.userid.length() > 0) {
                    String url1 = CommonMethods.url;
                    String url = (url1.substring(0, (url1).length() - 1)) + "?action=updateprofile";
                    Log.i("Update URL :", url);
                    postRequest = new HttpPost(url);
                } else {
                    String url1 = CommonMethods.url;
                    String url = (url1.substring(0, (url1).length() - 1)) + "?action=registration";
                    Log.i("Register URL :", url);
                    postRequest = new HttpPost(url);
                }

                postRequest.addHeader("securedkey", CommonMethods.securedkeyuser);

                File file = null;

                FileBody fileBody1 = null;
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
Log.e("imagefile",imagefile);

                    if (imagefile.length() > 5) {
                        file = new File(imagefile);
                        fileBody1 = new FileBody(file);
                        if (imagefile.contains("http"))
                        {

                            Log.i("imgaPth :",imagefile);
                        }
                        else {
                            reqEntity.addPart("userfile", fileBody1);
                            if (CommonMethods.userid.length() > 0) {
                                reqEntity.addPart("isimageavailable", new StringBody("1"));
                            }
                            Log.i("ImgPath:", fileBody1.toString());
                        }
                    }
                /*else if(imagefile.length()==0){
                        reqEntity.addPart("isimageavailable", new StringBody("0"));
                    }*/


                if (CommonMethods.userid.length() > 0) {
                    reqEntity.addPart("userid", new StringBody(CommonMethods.userid));

                    reqEntity.addPart("type", new StringBody("user"));

                    if(imagefile.length()==0){
                        reqEntity.addPart("isimageavailable", new StringBody("0"));
                    }
                    Log.i("Test", CommonMethods.userid.toString());
                }


                /*if (CommonMethods.userid.length() > 0) {
                    reqEntity.addPart("userid", new StringBody(CommonMethods.userid));
                    Log.i("Test", CommonMethods.userid.toString());
                }*/

                reqEntity.addPart("name", new StringBody(name));
                reqEntity.addPart("email", new StringBody(email));
                reqEntity.addPart("password", new StringBody(password));
                reqEntity.addPart("mobilenumber", new StringBody(mobilenum));
                reqEntity.addPart("location", new StringBody(location));
                reqEntity.addPart("dob", new StringBody(dob));
                reqEntity.addPart("device", new StringBody(device));
                reqEntity.addPart("gcmid", new StringBody(gcmid));
                reqEntity.addPart("deviceid", new StringBody(deviceid));


                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();
                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }

                Log.i("Test", s.toString());





                System.out.println("Response s");
                listId = s.toString();



            } catch (Exception e) {
                e.printStackTrace();
                Log.getStackTraceString(e);
            }
        }
            return listId;

    }
    public static boolean test_internet(Context cxt) {
        boolean int_test;
        ConnectivityManager manager = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if (!is3g && !isWifi) {
            int_test = false;
        } else {
            int_test = true;
        }
        return int_test;
    }





}
