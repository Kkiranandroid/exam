package Commmon.Methods;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shveta on 3/28/2016.
 */
public class CustomRequest extends JsonObjectRequest {
    Context context;
    protected boolean cacheHit;

    public CustomRequest(int method, String url, JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url+"cache="+CommonMethods.generateRandomForCache(), jsonRequest, listener, errorListener);
        this.context=context;
    }


  /*  @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        long now = System.currentTimeMillis();
        Map<String, String> responseHeaders = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;
        headerValue = responseHeaders.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = responseHeaders.get("ETag");

        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = responseHeaders;
        return super.parseNetworkResponse(response);
    }*/

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {



        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");

        if(CommonMethods.userid.length()>0){
            headers.put("type" ,"user");
            headers.put("securedkey" ,CommonMethods.securedkeyuser);
        }
        else{
            if(CommonMethods.securedkeyguest.equals("")){
                headers.put("type" ,"guestuser");
                headers.put("securedkey" ,"");
            }
            headers.put("type" ,"guestuser");
            headers.put("securedkey" ,CommonMethods.securedkeyguest);
        }

        Log.e("secure key",CommonMethods.securedkeyuser);

        return headers;
    }



 /*   @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        cacheHit = false;
        if (tag.equals("cache-hit")){
            cacheHit = true;

        }
        Log.e("cache hit",""+cacheHit);
    }*/
}