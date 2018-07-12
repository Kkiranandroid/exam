package eniversity.com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.eniversity.app.R;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.nu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;

import adapter.SubScriptionAdapter;

import get.set.SubScriptionGetSet;


/**
 * Created by Administrator on 12/9/2016.
 */

public class SubScription extends AppCompatActivity {
    ListView subscriptionListView;
    TextView selectCatTextView;
    TextView textViewSubNorecFound;
    ProgressBar progressbarLoadMoreCourse, progressbarLoadMoreCoursepage;
    ProgressBar progressbarLoadExploreCourse;
    private DrawerLayout drawer_layout;
    private Toolbar toolbar;
    private EditText editTextSearch;
    private ImageView iamgeViewNavigationdrawer;
    private TextView textViewHeading;
    private ImageView imageViewBackIcon;
    private ImageView imageViewSearchIcon;
    private ImageView imageViewSearchCancel;
    private ActionBarDrawerToggle drawerToggle;
    private View includelayoutnavigationDrawer;
    private ImageView imageViewProfilePic;
    private TextView textViewUserName;
    private LinearLayout linearLayoutRegister;
    private ImageView linearLayoutEditProfile;
    private ArrayList<SubScriptionGetSet> subScriptionGetSetsList;
    SubScriptionAdapter subScriptionAdapter;
    CoordinatorLayout coordinatorLayout;
    private int pageSize = 1;
    private int searchPageSize = 1;
    private boolean loading = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout MainLinearLayout;

    private ArrayList<SubScriptionGetSet> SubScriptionGetSetMainArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcr_list);
        subscriptionListView = (ListView) findViewById(R.id.subscriptionListView);
        selectCatTextView = (TextView) findViewById(R.id.selectCatTextView);
        textViewSubNorecFound = (TextView) findViewById(R.id.textViewSubNorecFound);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        MainLinearLayout = (LinearLayout) findViewById(R.id.MainLinearLayout);

        progressbarLoadMoreCourse = (ProgressBar) findViewById(R.id.progressbarLoadMoreCourse);
        progressbarLoadMoreCoursepage = (ProgressBar) findViewById(R.id.progressbarLoadMoreCoursepage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading.setText("Subscriptions");
        //selectCatTextView.setVisibility(View.VISIBLE);

        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SubScriptionGetSetMainArray = new ArrayList<>();
        getsubscriptions(pageSize);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageSize = 1;
                loading = false;
                progressbarLoadMoreCoursepage.setVisibility(View.GONE);

                SubScriptionGetSetMainArray = new ArrayList<>();


                textViewHeading.setVisibility(View.VISIBLE);


                // subScriptionGetSetsList = new ArrayList<>();
                getsubscriptions(pageSize);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void getsubscriptions(final int pagesize) {
        try {
            // MainLinearLayout.setVisibility(View.GONE);
            // progressbarLoadMoreCourse.setVisibility(View.VISIBLE);
            subScriptionGetSetsList = new ArrayList<>();
            if (!loading) {

                progressbarLoadMoreCoursepage.setVisibility(View.GONE);
            } else {
                if (pageSize == 1) {

                    progressbarLoadMoreCourse.setVisibility(View.VISIBLE);
                } else if (pageSize > 1) {
                    progressbarLoadMoreCourse.setVisibility(View.GONE);
                }
            }
            //progressbarLoadMoreCourse.setVisibility(View.VISIBLE);
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getyoursubscriptions");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("pagesize", "10");
            sendObject.put("pagenumber", String.valueOf(pagesize));

            Log.i("the request is ", sendObject.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(SubScription.this);
            CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());

                    try {

                        subScriptionGetSetsList = new ArrayList<>();
                        JSONArray YourSubscriptionArray = response.getJSONArray("YourSubscription");
                        for (int i = 0; i < YourSubscriptionArray.length(); i++) {
                            SubScriptionGetSet subScriptionGetSet = new SubScriptionGetSet();
                            if(YourSubscriptionArray.getJSONObject(i).getString("status").equals("deleted")||YourSubscriptionArray.getJSONObject(i).getString("status").equals("blocked"))
                            {
                                subScriptionGetSet.setStatus(YourSubscriptionArray.getJSONObject(i).getString("status"));
                            }
                            else {
                                subScriptionGetSet.setId(YourSubscriptionArray.getJSONObject(i).getString("id"));
                                subScriptionGetSet.setCorseName(YourSubscriptionArray.getJSONObject(i).getString("coursename"));
                                subScriptionGetSet.setCourseImage(YourSubscriptionArray.getJSONObject(i).getString("courseimage"));
                                subScriptionGetSet.setExamlevel(YourSubscriptionArray.getJSONObject(i).getString("examlevel"));
                                subScriptionGetSet.setSubDate(YourSubscriptionArray.getJSONObject(i).getString("createdon"));
                                subScriptionGetSet.setTxnAmt(YourSubscriptionArray.getJSONObject(i).getString("transactionAmount"));
                                subScriptionGetSet.setTxnId(YourSubscriptionArray.getJSONObject(i).getString("transactionTxnId"));
                                subScriptionGetSet.setStatus(YourSubscriptionArray.getJSONObject(i).getString("status"));
                            }
                            subScriptionGetSetsList.add(subScriptionGetSet);


                        }
                        SubScriptionGetSetMainArray.addAll(subScriptionGetSetsList);
                        progressbarLoadMoreCoursepage.setVisibility(View.GONE);
                        progressbarLoadMoreCourse.setVisibility(View.GONE);

                       /* subScriptionAdapter= new SubScriptionAdapter(SubScription.this,subScriptionGetSetsList);
                        subscriptionListView.setAdapter(subScriptionAdapter);*/
                        if(SubScriptionGetSetMainArray.size()==1){
                            if(SubScriptionGetSetMainArray.get(0).getStatus().equals("deleted")||
                                    SubScriptionGetSetMainArray.get(0).getStatus().equals("blocked")  ){
                                final Dialog dialog= new Dialog(SubScription.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message= (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been "+SubScriptionGetSetMainArray.get(0).getStatus());
                                Button buttonOK=(Button)dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(SubScription.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();

                            }
                            else{
                                if (!loading) {
                                    subscriptionListView.setAdapter(null);
                                    subScriptionAdapter = new SubScriptionAdapter(SubScription.this, SubScriptionGetSetMainArray);
                                    subscriptionListView.setAdapter(subScriptionAdapter);
                                    subScriptionAdapter.notifyDataSetChanged();
                                    subScriptionAdapter.notifyDataSetInvalidated();
                                    if (subScriptionGetSetsList.size() > 1) {
                                        subscriptionListView.setOnScrollListener(scrollListener);
                                    } else {
                                        subscriptionListView.setOnScrollListener(null);
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            try {
                                                subScriptionAdapter.notifyDataSetInvalidated();
                                                subScriptionAdapter.notifyDataSetChanged();
                                            } catch (Exception w) {
                                                w.printStackTrace();
                                            }

                                        }
                                    });
                                    progressbarLoadMoreCoursepage.setVisibility(View.VISIBLE);

                                }


                                if (subScriptionGetSetsList.size() <= 1) {
                                    subscriptionListView.setOnScrollListener(null);
                                    progressbarLoadMoreCoursepage.setVisibility(View.GONE);
                                    loading = false;
                                }
                                //progressbarLoadMoreCoursepage.setVisibility(View.GONE);
                                // MainLinearLayout.setVisibility(View.VISIBLE);
                                // progressbarLoadMoreCourse.setVisibility(View.GONE);

                                if (SubScriptionGetSetMainArray.size() == 0) {
                                    textViewSubNorecFound.setVisibility(View.VISIBLE);
                                }

                            }

                        }
                        else {
                            if (!loading) {
                                subscriptionListView.setAdapter(null);
                                subScriptionAdapter = new SubScriptionAdapter(SubScription.this, SubScriptionGetSetMainArray);
                                subscriptionListView.setAdapter(subScriptionAdapter);
                                subScriptionAdapter.notifyDataSetChanged();
                                subScriptionAdapter.notifyDataSetInvalidated();
                                if (subScriptionGetSetsList.size() > 1) {
                                    subscriptionListView.setOnScrollListener(scrollListener);
                                } else {
                                    subscriptionListView.setOnScrollListener(null);
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        try {
                                            subScriptionAdapter.notifyDataSetInvalidated();
                                            subScriptionAdapter.notifyDataSetChanged();
                                        } catch (Exception w) {
                                            w.printStackTrace();
                                        }

                                    }
                                });
                                progressbarLoadMoreCoursepage.setVisibility(View.VISIBLE);

                            }


                            if (subScriptionGetSetsList.size() <= 1) {
                                subscriptionListView.setOnScrollListener(null);
                                progressbarLoadMoreCoursepage.setVisibility(View.GONE);
                                loading = false;
                            }
                            //progressbarLoadMoreCoursepage.setVisibility(View.GONE);
                            // MainLinearLayout.setVisibility(View.VISIBLE);
                            // progressbarLoadMoreCourse.setVisibility(View.GONE);

                            if (SubScriptionGetSetMainArray.size() == 0) {
                                textViewSubNorecFound.setVisibility(View.VISIBLE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("error", "error");
                    error.printStackTrace();

                    if (error instanceof NoConnectionError) {
                        progressbarLoadMoreCourse.setVisibility(View.GONE);
                        // MainLinearLayout.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getsubscriptions(pagesize);
                                    }
                                });
                        snackbar.show();
                        Log.v("error", error.toString());
                        error.printStackTrace();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getsubscriptions(pagesize);
                                    }
                                });
                        snackbar.show();
                        progressbarLoadMoreCourse.setVisibility(View.GONE);
                        // MainLinearLayout.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                    }

                }
            });
            users.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(users);
        } catch (Exception e) {

        }


    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            try {
                if ((subscriptionListView.getLastVisiblePosition() == subscriptionListView.getAdapter().getCount() - 1
                        && subscriptionListView.getChildAt(subscriptionListView.getChildCount() - 1).getBottom() <= subscriptionListView.getHeight())) {
                    progressbarLoadMoreCoursepage.setVisibility(View.VISIBLE);


                    pageSize++;
                    loading = true;
                    getsubscriptions(pageSize);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    };


}
