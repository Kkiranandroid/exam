package eniversity.com;

import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.NoConnectionError;
import com.eniversity.app.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import adapter.ToppersListAdapter;
import get.set.ToppersListGetSet;



/**
 * Created by soumyay on 4/4/2016.
 */
public class ToppersList extends AppCompatActivity {
    TextView HeadingTextView;
    ListView toppersliListView;

    private Toolbar toolbar;

    TextView TextViewNoRecordFound;

    private ProgressBar progressbarTopperlist;
    private ArrayList<ToppersListGetSet> topperSlist;
    private ArrayList<ToppersListGetSet> toppersMainlist;
    private boolean loading = false;
    private ToppersListAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    private ProgressBar progressbarMoreTopperList;
    private int pageSize = 1;
    private ImageView imageViewBackIcon;
    private TextView HeadingTextViewtoolbar;

    String CourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toppers_listview);
        try{
        toppersliListView = (ListView) findViewById(R.id.listViewtoppers);

        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
            HeadingTextViewtoolbar = (TextView) toolbar.findViewById(R.id.textViewHeading);

            imageViewBackIcon.setVisibility(View.VISIBLE);
            imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            HeadingTextViewtoolbar.setText("Topper's List");
            //textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
            //textViewHeading.setVisibility(View.GONE);
            //textViewHeading2= (TextView) toolbar.findViewById(R.id.textViewHeading2);
            //textViewHeading2.setVisibility(View.VISIBLE);

            TextViewNoRecordFound = (TextView) findViewById(R.id.TextViewNoRecordFound);
            //imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
            //imageViewSearchIcon= (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
            //imageViewSearchIcon.setVisibility(View.GONE);
            progressbarTopperlist = (ProgressBar) findViewById(R.id.progressbarTopperlist);
            progressbarMoreTopperList = (ProgressBar) findViewById(R.id.progressbarMoreTopperList);
            //editTextSearch=(EditText)toolbar.findViewById(R.id.editTextSearch);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        } catch (Exception e) {
            Log.getStackTraceString(e);
        }





        /*if(CommonMethods.userid.length() > 0)
        {
            toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextSearch.getVisibility() == View.VISIBLE)
                    {
                       // textViewHeading.setVisibility(View.VISIBLE);
                        textViewHeading2.setVisibility(View.VISIBLE);
                        editTextSearch.setVisibility(View.GONE);
                    }
                    else {
                        finish();
                    }
                }
            });
        }
        else {
            toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextSearch.getVisibility() == View.VISIBLE)
                    {
                        //textViewHeading.setVisibility(View.VISIBLE);
                        textViewHeading2.setVisibility(View.VISIBLE);
                        editTextSearch.setVisibility(View.GONE);
                    }
                    else {
                        finish();
                    }
                }
            });

        }*/


        pageSize = 1;
        topperSlist = new ArrayList<>();
        toppersMainlist = new ArrayList<>();
        gettopperclasslist(pageSize);


        HeadingTextView = (TextView) findViewById(R.id.TextViewHeading);
        String courseName = getIntent().getExtras().getString("name");
        CourseId = getIntent().getExtras().getString("Courseid");
        //if(courseName.length()>20){
           /* textViewHeading.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
            textViewHeading.setText(courseName);*/
          /*  textViewHeading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
            textViewHeading2.setText(courseName);*/
       /* }else {
            //textViewHeading.setText(courseName + " TOPPERS");
            textViewHeading2.setText(courseName + " TOPPERS");

        }*/
        String name = "Topper's List for the course " + "<b>" + courseName + "</b>";
        HeadingTextView.setText(Html.fromHtml(name));
        toppersliListView = (ListView) findViewById(R.id.listViewtoppers);

    }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    private void gettopperclasslist(final int pageSize) {
        {
            topperSlist=new ArrayList<>();

            try {
                JSONObject sendJExploreObject = new JSONObject();
                progressbarTopperlist.setVisibility(View.VISIBLE);
                sendJExploreObject.put("action", "getcoursetopperdetails");

                sendJExploreObject.put("wherecondition", "c.courseid=" + getIntent().getExtras().getString("Courseid"));
                sendJExploreObject.put("pagenumber", String.valueOf(pageSize));
                sendJExploreObject.put("pagesize", "5");
                Log.i("the request is ", sendJExploreObject.toString());

                if (!loading) {

                    progressbarMoreTopperList.setVisibility(View.GONE);
                } else {
                    if (pageSize == 1) {
                        progressbarTopperlist.setVisibility(View.VISIBLE);
                    } else if (pageSize > 1) {
                        progressbarTopperlist.setVisibility(View.GONE);
                    }
                }
                Log.i("Tag1:---->", sendJExploreObject.toString());
                topperSlist=new ArrayList<>();
                RequestQueue requestQueue = Volley.newRequestQueue(ToppersList.this);
                CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                        sendJExploreObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Log.i("the response is ", response.toString());
                        try {topperSlist=new ArrayList<>();
                            String browseCatalogueitems = response.getString("RankingReport");
                            JSONArray browseArray = new JSONArray(browseCatalogueitems);


                            for (int i = 0; i < browseArray.length(); i++) {
                                ToppersListGetSet listGetSet = new ToppersListGetSet();
                                listGetSet.setName((browseArray.getJSONObject(i).getString("name")));
                                listGetSet.setPercentage((browseArray.getJSONObject(i).getString("totalscore")));
                                listGetSet.setRank((browseArray.getJSONObject(i).getString("ranking")));
                                topperSlist.add(listGetSet);

                            }

                            toppersMainlist.addAll(topperSlist);
                            if(toppersMainlist.size()==0){
                                TextViewNoRecordFound.setVisibility(View.VISIBLE);
                            }

                            progressbarTopperlist.setVisibility(View.GONE);
                            progressbarMoreTopperList.setVisibility(View.GONE);

                            if (!loading) {
                                adapter = new ToppersListAdapter(ToppersList.this, toppersMainlist);
                                toppersliListView.setAdapter(adapter);

                                if (topperSlist.size() > 1 ) {
                                    toppersliListView.setOnScrollListener(scrollListener);
                                } else {
                                    toppersliListView.setOnScrollListener(null);
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            adapter.notifyDataSetInvalidated();
                                            adapter.notifyDataSetChanged();
                                            loading = false;
                                        } catch (Exception w) {
                                            w.printStackTrace();
                                        }
                                    }
                                });

                            }
                            if (topperSlist.size() <= 1) {
                                toppersliListView.setOnScrollListener(null);
                                progressbarMoreTopperList.setVisibility(View.GONE);
                                loading = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            gettopperclasslist(pageSize);
                                        }
                                    });
                            snackbar.show();
                            progressbarTopperlist.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                        else {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout,ToppersList.this.getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            gettopperclasslist(pageSize);
                                        }
                                    });
                            snackbar.show();
                            progressbarTopperlist.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
                });
                requestQueue.add(customres);
            } catch (Exception e) {
e.printStackTrace();
            }
        }
    }
    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            try
            {
              /*  if ((toppersliListView.getLastVisiblePosition() == toppersliListView.getAdapter().getCount() - 1
                        && toppersliListView.getChildAt(toppersliListView.getChildCount() - 1).getBottom() <= toppersliListView.getHeight()))
                {   progressbarMoreTopperList.setVisibility(View.VISIBLE);
                    pageSize++;
                    Log.e("pagesize toppers",""+pageSize);
                    loading = true;
                    gettopperclasslist(pageSize);
                }*/

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !(loading)){
                    progressbarMoreTopperList.setVisibility(View.VISIBLE);

                    pageSize++;
                    Log.e("pagesize",""+pageSize);
                    loading = true;
                    gettopperclasslist(pageSize);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    };
}
