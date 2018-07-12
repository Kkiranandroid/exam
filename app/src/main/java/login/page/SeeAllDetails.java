package login.page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import adapter.SubscribedAdapter;
import get.set.SubscribedGetSet;
import eniversity.com.CourseDetails;
import com.eniversity.app.R;


public class SeeAllDetails extends AppCompatActivity {
    ListView SeeAllDetailsListView;
    ProgressBar detailsProgressBar;
    private ImageView imageViewBackIcon;
    private   ArrayList<SubscribedGetSet> subscribeditems;
    private ImageView imageViewSearchIcon;
    private EditText editTextSearch;
    CoordinatorLayout coordinatorLayout;
    private String searchText = "";
    private String edittextValue = "";
    private TextView textViewHeading;
    private TextView textViewHeading1;
    private TextView textViewNoRecordFoundseeall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seealldetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SeeAllDetailsListView = (ListView) findViewById(R.id.ListViewSubscriber);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        SeeAllDetailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeeAllDetails.this, CourseDetails.class);
                intent.putExtra("courseid", subscribeditems.get(position).getSubscribedCourseId());
                intent.putExtra("coursename", subscribeditems.get(position).getCoursename());
                startActivity(intent);
            }
        });



        detailsProgressBar=(ProgressBar)findViewById(R.id.progressbarLoadLandingDetails);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeeAllDetails.this,LoginMainPage.class);
                startActivity(intent);
            }
        });


        imageViewSearchIcon= (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        //imageViewSearchIcon.setVisibility(View.GONE);

        editTextSearch= (EditText) toolbar.findViewById(R.id.editTextSearch);
        textViewHeading= (TextView) toolbar.findViewById(R.id.textViewHeading);
        textViewHeading.setVisibility(View.GONE);


       textViewHeading1= (TextView) toolbar.findViewById(R.id.textViewHeading1);
        textViewHeading1.setVisibility(View.VISIBLE);
        textViewHeading1.setText("Your Subscribed Courses");
        textViewNoRecordFoundseeall= (TextView)findViewById(R.id.textViewNoRecordFoundseeall);






           /* toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(editTextSearch.getVisibility() == View.VISIBLE)
                    {
                        textViewHeading.setVisibility(View.VISIBLE);
                        editTextSearch.setVisibility(View.GONE);
                        CommonMethods.hideKeyboard(SeeAllDetails.this, (SeeAllDetails.this).getCurrentFocus());
                        subscribeditems  = new ArrayList<>();
                        subscribeditems.clear();
                        SeeAllDetailsListView.setAdapter(null);
                        getSubScriberDetails();
                    }
                    else {

                        finish();
                    }
                }
            });*/

        imageViewSearchIcon.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (editTextSearch.getVisibility() == View.VISIBLE /*&& editTextSearch.length()>0*/) {
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
                                                           getSubScriberDetails("1=1");
                                                       } else {
                                                           imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                                                           //textViewHeading.setVisibility(View.INVISIBLE);
                                                           textViewHeading1.setVisibility(View.INVISIBLE);
                                                           editTextSearch.setVisibility(View.VISIBLE);
                                                           imageViewBackIcon.setVisibility(View.GONE);
                                                          editTextSearch.setText("");
                                                           searchText="1=1";
                                                           editTextSearch.requestFocus();
                                                           CommonMethods.showKeyboard(SeeAllDetails.this,(SeeAllDetails.this).getCurrentFocus());
                                                           subscribeditems  = new ArrayList<>();
                                                           subscribeditems.clear();

                                                       }

                                                   }
                                               }
        );
        subscribeditems  = new ArrayList<>();
        searchText=getIntent().getExtras().getString("wherecondition");
        if(searchText.equals("1=1")){}else{

            imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
            textViewHeading1.setVisibility(View.INVISIBLE);
            editTextSearch.setVisibility(View.VISIBLE);
            imageViewBackIcon.setVisibility(View.GONE);
            subscribeditems  = new ArrayList<>();
            editTextSearch.setText(getIntent().getExtras().getString("searchtext"));
            editTextSearch.requestFocus();
        }
        getSubScriberDetails(searchText);



        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextSearch.toString().trim().length()>0) {
                subscribeditems  = new ArrayList<>();
                subscribeditems.clear();
                edittextValue = "c.coursename like '%" + s.toString() + "%'";

                    getSubScriberSearchDetails(edittextValue);
                }else{
                    subscribeditems  = new ArrayList<>();
                    searchText="1=1";
                    getSubScriberDetails(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void getSubScriberDetails(final String searchText) {
        try {
            textViewNoRecordFoundseeall.setVisibility(View.GONE);
            subscribeditems  = new ArrayList<>();
          // textViewNoRecordFound.setVisibility(View.GONE);
            detailsProgressBar.setVisibility(View.VISIBLE);

            //{"action":"getsubcribedcourses","userid":"11"/*,"courseid":"4"*/}
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getsubcribedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("wherecondition", searchText);

//            {"action":"getsubcribedcourses","userid":"11", "wherecondition" : "1=1"}



            RequestQueue requestQueue = Volley.newRequestQueue(SeeAllDetails.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        String browseCatalogueitems = response.getString("SubcribedCoursesList");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            SubscribedGetSet subscribedGetSet = new SubscribedGetSet();
                            subscribedGetSet.setCourseimage(browseArray.getJSONObject(i).getString("courseimage"));

                            subscribedGetSet.setCoursename(browseArray.getJSONObject(i).getString("coursename"));
                            subscribedGetSet.setOutof(browseArray.getJSONObject(i).getString("totalscore"));
                            subscribedGetSet.setRanking(browseArray.getJSONObject(i).getString("ranking"));
                            subscribedGetSet.setSubscribedon(browseArray.getJSONObject(i).getString("createdon"));
                            subscribedGetSet.setSubscribedCourseId(browseArray.getJSONObject(i).getString("courseid"));

                            subscribeditems.add(subscribedGetSet);
//                            categoryItemsList.add(categoryItems);
                        }
//                        gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(LoginMainPage.this, categoryItemsList, "exploreclass"));
Log.d("subscribeditems",""+subscribeditems.size());
                        SeeAllDetailsListView.setAdapter(new SubscribedAdapter(SeeAllDetails.this, subscribeditems,"seeall"));
//                        progressbarLoadLandingDetails.setVisibility(View.GONE);
                        detailsProgressBar.setVisibility(View.GONE);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getSubScriberDetails(searchText);
                                }
                            });
                    snackbar.show();

                    detailsProgressBar.setVisibility(View.GONE);
                    error.printStackTrace();

                }
            });
            requestQueue.add(customres);
        }
        catch (Exception e){

        }
    }



    public void getSubScriberSearchDetails(String edittextValue) {
        try {  subscribeditems = new ArrayList<>();
           textViewNoRecordFoundseeall.setVisibility(View.GONE);
            detailsProgressBar.setVisibility(View.VISIBLE);
            // progressbarLoadLandingDetails.setVisibility(View.VISIBLE);
            //{"action":"getsubcribedcourses","userid":"11"/*,"courseid":"4"*/}
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getsubcribedcourses");
            sendObject.put("userid",CommonMethods.userid);
            sendObject.put("wherecondition", edittextValue);

//            {"action":"getsubcribedcourses","userid":"11", "wherecondition" : "1=1"}
            subscribeditems = new ArrayList<>();


            RequestQueue requestQueue = Volley.newRequestQueue(SeeAllDetails.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {subscribeditems = new ArrayList<>();
                        String browseCatalogueitems = response.getString("SubcribedCoursesList");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            SubscribedGetSet subscribedGetSet = new SubscribedGetSet();
                            subscribedGetSet.setCourseimage(browseArray.getJSONObject(i).getString("courseimage"));

                            subscribedGetSet.setCoursename(browseArray.getJSONObject(i).getString("coursename"));
                            subscribedGetSet.setOutof(browseArray.getJSONObject(i).getString("totalscore"));
                            subscribedGetSet.setRanking(browseArray.getJSONObject(i).getString("ranking"));
                            subscribedGetSet.setSubscribedon(browseArray.getJSONObject(i).getString("createdon"));
                            subscribedGetSet.setSubscribedCourseId(browseArray.getJSONObject(i).getString("courseid"));

                            subscribeditems.add(subscribedGetSet);
//                            categoryItemsList.add(categoryItems);
                        }if(browseArray.length()==0){
                            textViewNoRecordFoundseeall.setVisibility(View.VISIBLE);
                        }
//                        gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(LoginMainPage.this, categoryItemsList, "exploreclass"));

                        SeeAllDetailsListView.setAdapter(new SubscribedAdapter(SeeAllDetails.this, subscribeditems,"seeall"));
//                        progressbarLoadLandingDetails.setVisibility(View.GONE);
                        detailsProgressBar.setVisibility(View.GONE);
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
        }
        catch (Exception e){

        }
    }
}
