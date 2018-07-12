package eniversity.com;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.android.volley.NoConnectionError;
import com.eniversity.app.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import Commmon.Methods.SlidingTabLayout;
import fragments.DetailsFrag;
import fragments.ExaminationFrag;
import get.set.CountGetSet;
import get.set.Examdetails_GetSet;
import login.page.LoginMainPage;

/**
 * Created by shveta on 3/24/2016.
 */
public class CorseInfo extends AppCompatActivity {


    private ImageView CourseImageView;
    ArrayList<Examdetails_GetSet> exam_details;
    private ListView examDetailsListView;
    ProgressBar loadDetailsProgressBar;
    private Button subscribeButton;
    private TextView descriptionTextView;
    ImageView imageViewBackIcon;
    Toolbar toolbar;
    LinearLayout ratingLayout;
    TextView ratingDescriptionTextView;
    Button ratingSubmitButton;
    RatingBar userRatingBar;
    CheckBox easyExamCheckBox;
    CheckBox intermediateExamCheckBox;
    CheckBox advancedExamCheckBox;
    String courseid = "";

    TextView HeadingTextView;
    TextView NoexamsFoundTextView;
    LinearLayout noexamsLayout;
    int isSubscribed;
    String approvedExamLevel = "";
    ImageView imageViewSearchIcon;
    LinearLayout examTitleLayout;


    ArrayList<CountGetSet> CountArray;

    float rating;
    String description;
    String isRated = "";
    Dialog dialog;
    String ratingVal = "";


    String CourseName = "";
    String Subscribed = "";
    CharSequence examlevel = "";
    String[] ratingval2;
    ImageView imageViewCancel;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout linearLayout1;
    Button StartButton;
    TextView durationText;
    SlidingTabLayout tabsCourse;
    ViewPager pagerCourse;


    //new
    String courseexamlevel = "";
    String fees="";
    String num_of_users="";
    String Description="";
    Float ratings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.making_payment);


        linearLayout1 = (RelativeLayout) findViewById(R.id.linearLayout1);

        CourseImageView = (ImageView) findViewById(R.id.CourseImageView);
        loadDetailsProgressBar = (ProgressBar) findViewById(R.id.ProgressBarLoading);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        subscribeButton = (Button) findViewById(R.id.subscribeNowButton);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        HeadingTextView = (TextView) toolbar.findViewById(R.id.textViewHeading);


        CourseName = getIntent().getExtras().getString("coursename");
        HeadingTextView.setText("Eniversity");


        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CommonMethods.fromclass.equals("landing")){
                    CommonMethods.fromclass="";
                    Intent intent=new Intent(CorseInfo.this, LoginMainPage.class);
                    startActivity(intent);
                }
                finish();
            }
        });
        tabsCourse= (SlidingTabLayout) findViewById(R.id.tabsCourse);
        tabsCourse.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                if (position == 0) {
                    // analysistoolBar.setVisibility(View.GONE);
                    //resultToolBar.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    //  resultToolBar.setVisibility(View.GONE);
                    //analysistoolBar.setVisibility(View.VISIBLE);
                }
                return Color.parseColor("#45B97C");
            }


        });
        pagerCourse= (ViewPager) findViewById(R.id.pagerCourse);



    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        Context context;
        String TITLES[]={"DESCRIPTION","EXAMINATIONS"};
        ArrayList<Examdetails_GetSet>exam_details;

        public FragmentAdapter(Context context, FragmentManager fm,ArrayList<Examdetails_GetSet>exam_details) {
            super(fm);
            this.context = context;
            this.exam_details=exam_details;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DetailsFrag detailsFrag= new DetailsFrag(courseid,CourseName,fees ,num_of_users, Description ,ratings,isRated);

                    return detailsFrag;
                case 1:
                    ExaminationFrag examinationFrag= new ExaminationFrag(exam_details,isSubscribed,CountArray,approvedExamLevel,courseid);

                    return examinationFrag;

                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCourseDetails1();
    }

    private void getCourseDetails1() {
        try {
            linearLayout1.setVisibility(View.GONE);
            loadDetailsProgressBar.setVisibility(View.VISIBLE);
            courseid = getIntent().getExtras().getString("courseid");
            JSONObject getCourseDetails = new JSONObject();
            getCourseDetails.put("action", "getcoursedetails");
            getCourseDetails.put("courseid", courseid/*"4"*/);
            getCourseDetails.put("userid", CommonMethods.userid /*"11"*/);

            Log.v("RequestCoursedetails", getCourseDetails.toString());


            RequestQueue requestQueue = Volley.newRequestQueue(CorseInfo.this);
            CustomRequest courses = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    getCourseDetails, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("response", response.toString());
                    try {

                        String browseCatalogueitems = response.getString("CourseDetails");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        if (browseArray.getJSONObject(0).getString("status").equals("deleted") ||
                                browseArray.getJSONObject(0).getString("status").equals("blocked")) {
                            final Dialog dialog = new Dialog(CorseInfo.this);
                            dialog.setTitle(null);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setContentView(R.layout.custom_alert_dialog);
                            TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                            message.setText("Your account has been " + browseArray.getJSONObject(0).getString("status"));
                            Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                            buttonOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    new LogOut(CorseInfo.this).execute();
                                    finish();

                                }
                            });
                            dialog.show();

                        } else {
                            for (int i = 0; i < browseArray.length(); i++) {

                                fees=getResources().getString(R.string.rupees) + browseArray.getJSONObject(i).getString("totalfees");
                                num_of_users=browseArray.getJSONObject(i).getString("noofsubscribers");
                                Description=browseArray.getJSONObject(i).getString("description");
                                ratings=Float.parseFloat(browseArray.getJSONObject(i).getString("rating"));
                                isRated = browseArray.getJSONObject(i).getString("isratted");


                                approvedExamLevel = browseArray.getJSONObject(i).getString("approvedexamlevel");
                                courseexamlevel = browseArray.getJSONObject(i).getString("courseexamlevel");


                                isRated = browseArray.getJSONObject(i).getString("isratted");

                                if (browseArray.getJSONObject(i).getString("courseimage").length() > 0) {
                                    Picasso.with(getApplicationContext()).load(browseArray.getJSONObject(i).getString("courseimage")/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                            .error(R.drawable.ic_no_image_availeble).into(CourseImageView);
                                } else {
                                    Picasso.with(getApplicationContext()).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                            .error(R.drawable.ic_no_image_availeble).into(CourseImageView);
                                }
                                isSubscribed = Integer.parseInt(browseArray.getJSONObject(i).getString("issubscribed"));
                                if (CommonMethods.userid.length() > 0) {
                                    if (isSubscribed == 1 && getExamLevelDetails()) {

                                        subscribeButton.setVisibility(View.GONE);
                                    } else if (courseexamlevel.equals("")) {
                                        subscribeButton.setVisibility(View.GONE);
                                    } else {
                                        subscribeButton.setVisibility(View.VISIBLE);
                                        subscribeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intent = new Intent(CorseInfo.this, ProceedToPay.class);
                                                intent.putExtra("courseid", courseid);
                                                intent.putExtra("examlevels", approvedExamLevel);
                                                intent.putExtra("coursename", CourseName);
                                                intent.putExtra("courseexamlevel", courseexamlevel);

                                                startActivity(intent);

                                            }
                                        });
                                    }
                                } else {
                                    subscribeButton.setVisibility(View.GONE);
                                }
                            }
                            String browseCatalogueitems2 = response.getString("CourseExamDetails");
                            JSONArray browseArray2 = new JSONArray(browseCatalogueitems2);
                            exam_details = new ArrayList<>();
                            if (browseArray2.length() == 0) {
                                //NoexamsFoundTextView.setVisibility(View.VISIBLE);
                             //   noexamsLayout.setVisibility(View.VISIBLE);
                               // examTitleLayout.setVisibility(View.GONE);


                            } else {

                                CountArray = new ArrayList<>();
                                for (int i = 0; i < browseArray2.length(); i++) {
                                    Examdetails_GetSet examGetSet;
                                    examGetSet = new Examdetails_GetSet();
                                    examGetSet.setExamTitle(browseArray2.getJSONObject(i).getString("title"));
                                    examGetSet.setExamDuration(browseArray2.getJSONObject(i).getString("duration"));
                                    examGetSet.setExamId(browseArray2.getJSONObject(i).getString("examinationid"));
                                    examGetSet.setMaxtimeusercantakeexam(browseArray2.getJSONObject(i).getString("maxtimeusercantakeexam"));
                                    examGetSet.setExamtype(browseArray2.getJSONObject(i).getString("examtype"));

                                    exam_details.add(examGetSet);

                                    if (response.toString().contains(browseArray2.getJSONObject(i).getString("title"))) {
                                        String browseCatalogueitems1 = response.getString(browseArray2.getJSONObject(i).getString("title"));
                                        JSONArray browseArray3 = new JSONArray(browseCatalogueitems1);
                                        Log.i("title", browseArray2.toString());
                                        for (int j = 0; j < browseArray3.length(); j++) {
                                            CountGetSet countGetSet = new CountGetSet();
                                            countGetSet.setNoOfattCount(browseArray3.getJSONObject(j).getString("count"));
                                            countGetSet.setExamLevel(browseArray3.getJSONObject(j).getString("examlevel"));
                                            countGetSet.setExamId(browseArray3.getJSONObject(j).getString("examinationid"));
                                            countGetSet.setExamdate(browseArray3.getJSONObject(j).getString("lastexamtakendate"));
                                            CountArray.add(countGetSet);
                                        }
                                    }
                                }
                            }
                            pagerCourse.setAdapter(new FragmentAdapter(CorseInfo.this, getSupportFragmentManager(),exam_details));
                            tabsCourse.setDistributeEvenly(true);
                            tabsCourse.setViewPager(pagerCourse);
                            // examDetailsListView.setAdapter(new CourseAdapter(CorseInfo.this, exam_details));
                            linearLayout1.setVisibility(View.VISIBLE);
                            loadDetailsProgressBar.setVisibility(View.GONE);

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
                                .make(coordinatorLayout, getApplicationContext().getResources()
                                        .getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCourseDetails1();
                                    }
                                });
                        snackbar.show();
                        linearLayout1.setVisibility(View.VISIBLE);
                        loadDetailsProgressBar.setVisibility(View.GONE);

                        error.printStackTrace();

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCourseDetails1();
                                    }
                                });
                        snackbar.show();
                        linearLayout1.setVisibility(View.VISIBLE);
                        loadDetailsProgressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }

                }
            });

            requestQueue.add(courses);

        } catch (Exception r) {

        }
    }

    private boolean getExamLevelDetails() {
        String selectedExamLevels = approvedExamLevel;
        String courseexamlevels = courseexamlevel;
        /*if(selectedExamLevels.contains("Easy")){
            if(selectedExamLevels.contains("Intermediate")){
                if(selectedExamLevels.contains("Advanced")){
                    return true;
                }
            }
        }*/
        if (selectedExamLevels.length() == courseexamlevels.length()) {
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(CommonMethods.fromclass.equals("landing")){
            CommonMethods.fromclass="";
            Intent intent=new Intent(CorseInfo.this, LoginMainPage.class);
            startActivity(intent);
        }finish();
    }
}
