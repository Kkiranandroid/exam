package login.page;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;

import adapter.RecommendedAdapter;
import adapter.SubscribedAdapter;
import eniversity.com.CorseInfo;
import eniversity.com.ExploreClass;
import get.set.HomeCategoryGetSet;
import get.set.RecommendedGetSet;
import get.set.SubscribedGetSet;

import com.eniversity.app.R;
import com.facebook.GraphResponse;

import static com.eniversity.app.R.id.progressbarLoadExploreCourse;

public class RecommendedSeeAll extends AppCompatActivity {
    GridView SeeAllDetailsGridView;
    ProgressBar detailsProgressBar;
    ProgressBar progressbarMoreLoadLandingDetails;
    private ImageView imageViewBackIcon;
    private TextView textViewRecommandedNoCourseFound;
    TextView textViewHeading;
    TextView textViewHeading2;
    private ImageView imageViewSearchIcon;
    private TextView editTextSearch;
    private int myLastVisiblePos;

    private ArrayList<RecommendedGetSet> recommendeditems;
    private ArrayList<RecommendedGetSet> recommendedMainitems;
    private ArrayList<RecommendedGetSet> Mainitems;

    private ArrayList<RecommendedGetSet> searchMainArray;
    private int pageSize = 1;
    private int searchPageSize = 1;
    private String searchText = "";
    private String isFrom="";
    private String edittextValue = "";
    private boolean loading = false;
    private RecommendedAdapter adapter;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended_list);

        /*****************************Toolbar************************************/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RecommendedSeeAll.this,LoginMainPage.class);
                startActivity(intent);
                finish();
            }
        });

        imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        textViewHeading.setVisibility(View.GONE);

        textViewHeading2 = (TextView) toolbar.findViewById(R.id.textViewHeading1);
        textViewHeading2.setVisibility(View.VISIBLE);
        textViewHeading2.setText("Recommended Courses");
        /*************************************************************************/
        textViewRecommandedNoCourseFound= (TextView) findViewById(R.id.textViewRecommandedNoCourseFound);

        SeeAllDetailsGridView = (GridView) findViewById(R.id.gridViewDetails);
        myLastVisiblePos = SeeAllDetailsGridView.getFirstVisiblePosition();
        detailsProgressBar = (ProgressBar) findViewById(R.id.progressbarLoadLandingDetails);
        progressbarMoreLoadLandingDetails = (ProgressBar) findViewById(R.id.progressbarMoreLoadLandingDetails);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Mainitems = new ArrayList<>();
        searchMainArray = new ArrayList<>();
        searchText = "1=1";
        recommendeditems = new ArrayList<>();
        recommendedMainitems = new ArrayList<>();
        Mainitems = new ArrayList<>();
        searchText=getIntent().getExtras().getString("wherecondition");
        if(searchText.equals("1=1")){
            getRecommendedDetails(searchText, pageSize);
        }else{
            imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
            textViewHeading2.setVisibility(View.INVISIBLE);
            editTextSearch.setVisibility(View.VISIBLE);
            imageViewBackIcon.setVisibility(View.GONE);
            Mainitems = new ArrayList<>();
            editTextSearch.setText(getIntent().getExtras().getString("searchtext"));
            editTextSearch.requestFocus();
            getRecommendedSearchDetails(searchText, 1,"0");
        }





        /*if(CommonMethods.userid.length() > 0)
        {
            toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextSearch.getVisibility() == View.VISIBLE)
                    {
                        textViewHeading.setVisibility(View.VISIBLE);
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
                        textViewHeading.setVisibility(View.VISIBLE);
                        editTextSearch.setVisibility(View.GONE);
                    }
                    else {
                        finish();
                    }
                }
            });

        }*/

        /*public void onClick(View v) {
            if (editTextSearch.getVisibility() == View.VISIBLE *//*&& editTextSearch.length()>0*//*) {
                edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                imageViewBackIcon.setVisibility(View.VISIBLE);
                subscribeditems  = new ArrayList<>();
                subscribeditems.clear();
                editTextSearch.setVisibility(View.GONE);
                // textViewHeading.setVisibility(View.VISIBLE);
                textViewHeading1.setVisibility(View.VISIBLE);
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                CommonMethods.hideKeyboard(SeeAllDetails.this, (SeeAllDetails.this).getCurrentFocus());
                //getSubScriberSearchDetails(edittextValue);
            } else {
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                //textViewHeading.setVisibility(View.INVISIBLE);
                textViewHeading1.setVisibility(View.INVISIBLE);
                editTextSearch.setVisibility(View.VISIBLE);
                imageViewBackIcon.setVisibility(View.GONE);
                editTextSearch.setText("");
                editTextSearch.requestFocus();
                CommonMethods.showKeyboard(SeeAllDetails.this,(SeeAllDetails.this).getCurrentFocus());
                subscribeditems  = new ArrayList<>();
                subscribeditems.clear();
                // getSubScriberDetails();
            }

        }*/

        /*imageViewSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                textViewRecommandedNoCourseFound.setVisibility(View.GONE);
                if (editTextSearch.getVisibility() == View.VISIBLE) {
                    imageViewBackIcon.setVisibility(View.VISIBLE);
                    textViewHeading.setVisibility(View.VISIBLE);
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                    CommonMethods.hideKeyboard(RecommendedSeeAll.this, (RecommendedSeeAll.this).getCurrentFocus());
                    //edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                    recommendedMainitems=new ArrayList();
                    recommendedMainitems.clear();
                    getRecommendedSearchDetails("1=1", searchPageSize);

                } else {
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                    textViewHeading.setVisibility(View.INVISIBLE);
                    editTextSearch.setVisibility(View.VISIBLE);
                    imageViewBackIcon.setVisibility(View.GONE);
                    getRecommendedDetails(searchText, pageSize);
                }
            }
        });*/

        imageViewSearchIcon.setOnClickListener(onClickListener);


        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               /* if (s.toString().trim().length() != 0) {
                    Mainitems = new ArrayList<>();
                    searchMainArray = new ArrayList<>();
                    //Mainitems.clear();
                    searchText = "c.coursename like '%" + s.toString() + "%'";
                    getRecommendedSearchDetails(searchText, searchPageSize);
                    //new SearchCourseAsyncTask().execute(searchText, String.valueOf(searchPageSize));
                } else {
                    //Mainitems.clear();
                    getRecommendedDetails("1=1", searchPageSize);
                    //new SearchCourseAsyncTask().execute("1=1", String.valueOf(searchPageSize));
                }*/

                try {
                    searchPageSize = 1;
                    edittextValue = "c.coursename like '%" + s.toString() + "%'";
                    searchMainArray = new ArrayList<>();
                    searchMainArray.clear();
                    Mainitems = new ArrayList<>();
                    if (editTextSearch.length() > 0) {
                        getRecommendedSearchDetails(edittextValue, searchPageSize,"0");
                    }
                    else{
                        isFrom="ontextchanged";

                        searchText="1=1";
                        pageSize=1;
                        getRecommendedDetails(searchText, pageSize);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       /* SeeAllDetailsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(RecommendedSeeAll.this, CorseInfo.class);
                intent.putExtra("courseid", Mainitems.get(i).getCourseid());
                intent.putExtra("coursename" ,Mainitems.get(i).getCourseName());
                startActivity(intent);

            }
        });
        */


    }

    /*  AdapterView.OnItemClickListener SeeAllDetailsGridViewlistner= new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent= new Intent(RecommendedSeeAll.this, CorseInfo.class);
              intent.putExtra("courseid", Mainitems.get(i).getCourseid());
              intent.putExtra("coursename" ,Mainitems.get(i).getCourseName());
              startActivity(intent);
          }
      };*/
  /*public class SearchCourseAsyncTask extends AsyncTask<String, Void, String> {


      @Override
      protected String doInBackground(String... voids) {
          getRecommendedSearchDetails(voids[0],Integer.parseInt(voids[1]));
          return null;
      }
  }*/
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                imageViewBackIcon.setVisibility(View.VISIBLE);
               /* recommendedMainitems = new ArrayList();
                recommendedMainitems.clear();*/
                searchText = "1=1";
                Mainitems.clear();
                editTextSearch.setText("");
                searchPageSize = 1;
                searchMainArray.clear();
                searchMainArray = new ArrayList<>();

                Mainitems = new ArrayList<RecommendedGetSet>();
                Mainitems.clear();
                searchMainArray = new ArrayList<RecommendedGetSet>();
                //if (edittextValue.length() > 0) {

                // }

                // textViewHeading.setVisibility(View.VISIBLE);
                textViewHeading2.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.GONE);
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                // CommonMethods.hideKeyboard(RecommendedSeeAll.this, (RecommendedSeeAll.this).getCurrentFocus());
                //getSubScriberSearchDetails(edittextValue);
            } else {
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                textViewHeading2.setVisibility(View.INVISIBLE);
                editTextSearch.setVisibility(View.VISIBLE);
                imageViewBackIcon.setVisibility(View.GONE);
                searchMainArray = new ArrayList<>();
                Mainitems = new ArrayList<>();
                searchText="1=1";
                editTextSearch.setText("");
                editTextSearch.requestFocus();
                CommonMethods.showKeyboard(RecommendedSeeAll.this, (RecommendedSeeAll.this).getCurrentFocus());
                imageViewSearchIcon.setOnClickListener(finishSearchlistener);

                // getSubScriberDetails();
            }

        }
    };

    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                CommonMethods.hideKeyboard(RecommendedSeeAll.this, (RecommendedSeeAll.this).getCurrentFocus());
                textViewHeading2.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.INVISIBLE);
                imageViewBackIcon.setVisibility(View.VISIBLE);
                editTextSearch.setText("");

                pageSize = 1;
                searchMainArray = new ArrayList<>();
                Mainitems = new ArrayList<>();
                searchText = "1=1";
                if(isFrom.equals("ontextchanged")){

                }
                else {

                    //CommonMethods.showKeyboard(ExploreClass.this,(ExploreClass.this).getCurrentFocus());
                    getRecommendedDetails(searchText, pageSize);
                }

                // iamgeViewNavigationdrawer.setVisibility(View.GONE);
                //drawerToggle.setDrawerIndicatorEnabled(true);
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                imageViewSearchIcon.setOnClickListener(onClickListener);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void getRecommendedSearchDetails(String edittextValue, int searchPageSize, final String fromScroll) {
        try {
            recommendeditems = new ArrayList<>();
            textViewRecommandedNoCourseFound.setVisibility(View.GONE);
            //detailsProgressBar.setVisibility(View.VISIBLE);
            // progressbarLoadLandingDetails.setVisibility(View.VISIBLE);
            //{"action":"getsubcribedcourses","userid":"11"/*,"courseid":"4"*/}
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getrecommendedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("wherecondition", edittextValue);
            sendObject.put("pagesize", "5");
            sendObject.put("pagenumber", String.valueOf(searchPageSize));
            Log.i("the request is ", sendObject.toString());

            if (!loading) {
                progressbarMoreLoadLandingDetails.setVisibility(View.GONE);

            } else {

            }
            if (searchPageSize == 1) {
                progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
                detailsProgressBar.setVisibility(View.VISIBLE);
            } else if (searchPageSize > 1) {
                progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                detailsProgressBar.setVisibility(View.GONE);
            }
            if(fromScroll.equals("0")){
                searchMainArray = new ArrayList<>();
                searchMainArray.clear();
                Log.e("searchMainArray","searchMainArray is cleared");
            }

            RequestQueue requestQueue = Volley.newRequestQueue(RecommendedSeeAll.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the request is ", response.toString());
                    try {

                        if(fromScroll.equals("0")){
                            searchMainArray = new ArrayList<>();
                            searchMainArray.clear();
                            Log.e("searchMainArray","searchMainArray is cleared");
                        }

                        recommendeditems = new ArrayList<>();
                        recommendeditems.clear();
                        String browseCatalogueitems = response.getString("RecommendedCourses");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            RecommendedGetSet recommendedGetSet = new RecommendedGetSet();
                            recommendedGetSet.setCourseImage(browseArray.getJSONObject(i).getString("courseimage"));
                            recommendedGetSet.setCourseid(browseArray.getJSONObject(i).getString("courseid"));

                            recommendedGetSet.setCourseName(browseArray.getJSONObject(i).getString("coursename"));
                            recommendedGetSet.setCoursePrice(browseArray.getJSONObject(i).getString("totalfees"));
                            recommendedGetSet.setCourseUsers(browseArray.getJSONObject(i).getString("noofsubcribers"));
                            recommendedGetSet.setRating(browseArray.getJSONObject(i).getString("rating"));

                            recommendeditems.add(recommendedGetSet);
                        }
                        detailsProgressBar.setVisibility(View.GONE);

                        //Mainitems.clear();
                        searchMainArray.addAll(recommendeditems);
                        if(searchMainArray.size()==0){
                            textViewRecommandedNoCourseFound.setVisibility(View.VISIBLE);
                        }

                        progressbarMoreLoadLandingDetails.setVisibility(View.GONE);

//                      gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(LoginMainPage.this, categoryItemsList, "exploreclass"));

                        if (!loading) {
                            Log.e("searchMainArray size", "" + searchMainArray.size());
                            SeeAllDetailsGridView.setAdapter(null);
                            adapter = new RecommendedAdapter(RecommendedSeeAll.this, searchMainArray);
                            SeeAllDetailsGridView.setAdapter(adapter);
                            // SeeAllDetailsGridView.setOnItemClickListener(SeeAllDetailsGridViewlistner);

                            if (searchMainArray.size() > 1) {
                                // adapter=new RecommendedAdapter(RecommendedSeeAll.this, Mainitems);
                                //  SeeAllDetailsGridView.setAdapter(adapter);
                                loading = false;
                                SeeAllDetailsGridView.setOnScrollListener(scrollListener);
                            } else {
                                // adapter=new RecommendedAdapter(RecommendedSeeAll.this, Mainitems);
                                // SeeAllDetailsGridView.setAdapter(adapter);
                                loading = false;
                                SeeAllDetailsGridView.setOnScrollListener(null);
                            }
                            progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
                            detailsProgressBar.setVisibility(View.GONE);
                        } else {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("the boolean value is ", String.valueOf(loading));
                                    try {
                                        loading = false;
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }
                                }
                            });

                        }

                        if (recommendeditems.size() <= 1) {
                            SeeAllDetailsGridView.setOnScrollListener(null);
                            detailsProgressBar.setVisibility(View.GONE);
                            progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
                            loading = false;
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    detailsProgressBar.setVisibility(View.GONE);
                    error.printStackTrace();

                }
            });
            requestQueue.add(customres);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getRecommendedDetails(final String searchText, final int pageSize) {
        try {
            searchMainArray = new ArrayList<>();
            searchMainArray.clear();
            recommendeditems = new ArrayList<>();
            recommendedMainitems = new ArrayList<>();
            recommendeditems = new ArrayList<>();
            textViewRecommandedNoCourseFound.setVisibility(View.GONE);
            detailsProgressBar.setVisibility(View.VISIBLE);
            // progressbarLoadLandingDetails.setVisibility(View.VISIBLE);
            //{"action":"getsubcribedcourses","userid":"11"/*,"courseid":"4"*/}
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getrecommendedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("wherecondition", searchText);
            sendObject.put("pagesize", "5");
            sendObject.put("pagenumber", String.valueOf(pageSize));
            Log.i("the request is ", sendObject.toString());

            if (!loading) {

                progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
            } else {
                if (pageSize == 1) {
                    //progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                    detailsProgressBar.setVisibility(View.VISIBLE);
                } else if (pageSize > 1) {
                    // progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
                    detailsProgressBar.setVisibility(View.GONE);
                }
            }

            RequestQueue requestQueue = Volley.newRequestQueue(RecommendedSeeAll.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the request is ", response.toString());
                    try {
                        recommendeditems = new ArrayList<>();
                        recommendeditems.clear();
                        String browseCatalogueitems = response.getString("RecommendedCourses");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            RecommendedGetSet recommendedGetSet = new RecommendedGetSet();
                            recommendedGetSet.setCourseImage(browseArray.getJSONObject(i).getString("courseimage"));
                            recommendedGetSet.setCourseid(browseArray.getJSONObject(i).getString("courseid"));

                            recommendedGetSet.setCourseName(browseArray.getJSONObject(i).getString("coursename"));
                            recommendedGetSet.setCoursePrice(browseArray.getJSONObject(i).getString("totalfees"));
                            recommendedGetSet.setCourseUsers(browseArray.getJSONObject(i).getString("noofsubcribers"));
                            recommendedGetSet.setRating(browseArray.getJSONObject(i).getString("rating"));

                            recommendeditems.add(recommendedGetSet);
                        }
                        detailsProgressBar.setVisibility(View.GONE);

                        Mainitems.addAll(recommendeditems);

                        progressbarMoreLoadLandingDetails.setVisibility(View.GONE);

                        if(Mainitems.size()==0){
                            textViewRecommandedNoCourseFound.setVisibility(View.VISIBLE);
                        }

//                      gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(LoginMainPage.this, categoryItemsList, "exploreclass"));

                        if (!loading) {
                            Log.e("Mainitems size", "" + Mainitems.size());
                            adapter = new RecommendedAdapter(RecommendedSeeAll.this, Mainitems);
                            SeeAllDetailsGridView.setAdapter(adapter);
                            //  SeeAllDetailsGridView.setOnItemClickListener(SeeAllDetailsGridViewlistner);

                            if (recommendeditems.size() > 1) {
                                // adapter=new RecommendedAdapter(RecommendedSeeAll.this, Mainitems);
                                //  SeeAllDetailsGridView.setAdapter(adapter);
                                loading = false;
                                SeeAllDetailsGridView.setOnScrollListener(scrollListener);
                            } else {
                                // adapter=new RecommendedAdapter(RecommendedSeeAll.this, Mainitems);
                                // SeeAllDetailsGridView.setAdapter(adapter);
                                loading = false;
                                SeeAllDetailsGridView.setOnScrollListener(null);
                            }
                            progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
                            detailsProgressBar.setVisibility(View.GONE);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Log.v("the boolean value is ", String.valueOf(loading));

                                    try {
                                        loading = false;
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }
                                }
                            });


                            //  progressbarMoreLoadLandingDetails.setVisibility(View.GONE);

                        }


                        if (recommendeditems.size() <= 1) {
                            SeeAllDetailsGridView.setOnScrollListener(null);
                            detailsProgressBar.setVisibility(View.GONE);
                            progressbarMoreLoadLandingDetails.setVisibility(View.GONE);
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


                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getRecommendedDetails(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        detailsProgressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                    else {

                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getRecommendedDetails(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        detailsProgressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }




                }
            });
            requestQueue.add(customres);
        } catch (Exception e) {

        }
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
           /* Log.e("SeeAllDetailsGridView.getLastVisiblePosition()",""+SeeAllDetailsGridView.getLastVisiblePosition());
            Log.e("SeeAllDetailsGridView.getAdapter().getCount() - 1",""+(SeeAllDetailsGridView.getAdapter().getCount() - 1));
            Log.e("SeeAllDetailsGridView.getChildAt(SeeAllDetailsGridView.getChildCount() - 1).getBottom()",""+SeeAllDetailsGridView.getChildAt(SeeAllDetailsGridView.getChildCount() - 1).getBottom());
            Log.e("SeeAllDetailsGridView.getHeight()",""+SeeAllDetailsGridView.getHeight());*/
/*
            if((SeeAllDetailsGridView.getLastVisiblePosition() == SeeAllDetailsGridView.getAdapter().getCount() - 1
                    && SeeAllDetailsGridView.getChildAt(SeeAllDetailsGridView.getChildCount() - 1).getBottom() <= SeeAllDetailsGridView.getHeight())) {
                progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                detailsProgressBar.setVisibility(View.GONE);
                pageSize++;
                Log.e("pagesize",""+pageSize);
                loading = true;
                getRecommendedDetails(searchText, pageSize);



            }*/

        /*    if (SeeAllDetailsGridView.getLastVisiblePosition() + 1 == totalItemCount && !loading) {
                progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                detailsProgressBar.setVisibility(View.GONE);
                pageSize++;
                Log.e("pagesize",""+pageSize);
                loading = true;
                getRecommendedDetails(searchText, pageSize);

            } else {
                loading = false;


            }*/

            int lastInScreen = firstVisibleItem + visibleItemCount;
            if ((lastInScreen == totalItemCount) && !(loading)) {
                progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                detailsProgressBar.setVisibility(View.GONE);
                if (editTextSearch.length() > 0) {
                    searchPageSize++;
                    loading = true;
                    getRecommendedSearchDetails("c.coursename like '%" + editTextSearch.getText().toString() + "%'", searchPageSize,"1");

                } else {
                    pageSize++;
                    Log.e("pagesize", "" + pageSize);
                    loading = true;
                    getRecommendedDetails("1=1", pageSize);
                }
            }
        }
    };
    /*private AbsListView.OnScrollListener scrollListenerInSearch = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            int lastInScreen = firstVisibleItem + visibleItemCount;
            if ((lastInScreen == totalItemCount) && !(loading)) {
                progressbarMoreLoadLandingDetails.setVisibility(View.VISIBLE);
                detailsProgressBar.setVisibility(View.GONE);
                searchPageSize++;
                Log.e("pagesize", "" + pageSize);
                loading = true;
                getRecommendedSearchDetails(searchText, pageSize);
            }
        }
    };*/
}
