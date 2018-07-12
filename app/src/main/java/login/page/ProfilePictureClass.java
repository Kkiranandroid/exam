package login.page;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;

/**
 * Created by ramagouda on 5/24/2016.
 */
public class ProfilePictureClass {
    public static String httpImgUrlPath="";
    public static String httpUserName="";
    public ProfilePictureClass(Context context){

        try {
            JSONObject getProfileDetails = new JSONObject();
            getProfileDetails.put("action", "getmyprofile");
            getProfileDetails.put("userid", CommonMethods.userid);

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    getProfileDetails, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Log.i("the response is ", response.toString());
                        String profile = response.getString("Profile");
                        JSONArray getArray = new JSONArray(profile);
                        for (int i = 0; i < getArray.length(); i++) {
                                String Name=getArray.getJSONObject(i).getString("name");
                                String ImgPath=getArray.getJSONObject(i).getString("profileimage");
CommonMethods.profile_image_url=ImgPath;
                            CommonMethods.userName=Name;

                                httpImgUrlPath=ImgPath;
                                httpUserName=Name;

                        }
                    } catch (Exception e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    error.printStackTrace();
                }
            });
            requestQueue.add(customres);

        } catch (Exception e) {

        }
        Log.i("profileimage path:",httpImgUrlPath);
        Log.i("profileimage path:",httpUserName);
    }
    public String getCurrentUserName(){
        return httpUserName;
    }
    public String getCurrentProfilePic(){
        return httpImgUrlPath;
    }
}
