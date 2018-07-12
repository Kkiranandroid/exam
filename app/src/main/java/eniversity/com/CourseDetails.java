package eniversity.com;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.NoConnectionError;
import com.eniversity.app.R;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.SlidingTabLayout;
import adapter.CourseDetailsAdapter;
import get.set.ChildGetSet;
import get.set.ParentGetSet;

/**
 * Created by soumyay on 5/27/2016.
 */
@SuppressLint("ValidFragment")
public class CourseDetails extends AppCompatActivity {

    static TextView suscriptionDateTextView;
    static TextView examStatusTextView;
    static TextView rankTextView;
    static TextView textViewDescription;
    //FragmentAdapter courseAdapter;
    RootFragmentAdapter courseAdapter;
    static String Subcourseid = "";
    String courseName = "";
    static ImageView CourseImageView;
    static String examlevel = "";
    static String title = "";
    static String examinationId = "";
    static ExpandableListView listViewDetails1;
    static ExpandableListView listViewDetails2;
    static ExpandableListView listViewDetails3;
    static TextView notsubTextView1;
    static TextView notsubTextView2;
    static TextView notsubTextView3;
    static String outOf = "";
    static ProgressBar ProgressBarLoadingCourseDetailsFrag;
    ImageView imageViewBackIcon;
    TextView headingTextView;
    ImageView imageViewSearchIcon;
    static LinearLayout linearLayoutMain;
    static CoordinatorLayout coordinatorLayout;
    TextView NoexamsTextView;
    LinearLayout linearLayoutpager;
    static ProgressBar progressbarLoadMore1;
    static ProgressBar progressbarLoadMore2;
    static ProgressBar progressbarLoadMore3;
    static CoordinatorLayout coordinatorLayout1;
    static CoordinatorLayout coordinatorLayout2;
    static CoordinatorLayout coordinatorLayout3;
    TextView textViewExamTitle;
    TextView textViewExamTakenOn;
    TextView TextViewScore;
    TextView textViewNoOfAttempts;
    TextView textViewRank;
    TextView notSubScribedTextView;
    Button takeExamButton;
    LinearLayout MainLayout;
    static ListView tab1ListView1;
    static ListView tab1ListView2;
    static ListView tab1ListView3;

    static Button takeExamButton1;
    static Button takeExamButton2;
    static Button takeExamButton3;

    static Button buttonAnalysis1;
    static Button buttonAnalysis2;
    static Button buttonAnalysis3;

    static String maxtimeusercantakeexam1 = "";
    static String maxtimeusercantakeexam2 = "";
    static String maxtimeusercantakeexam3 = "";
    static String countEasy = "";
    static String countIntermediate= "";
    static String countAdvance = "";
    static String examtype = "";

    TextView statusTextView;
    String yourRank = "";
    SlidingTabLayout tabs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercoursedetails);
        outOf="";
     examlevel = "";
      title = "";
      examinationId = "";
        Subcourseid="";
        maxtimeusercantakeexam1 = "";
        maxtimeusercantakeexam2 = "";
         maxtimeusercantakeexam3 = "";
        countEasy = "";
         countIntermediate= "";
        countAdvance = "";
        examtype = "";
        Subcourseid = getIntent().getExtras().getString("courseid");
        courseName = getIntent().getExtras().getString("coursename");


        ProgressBarLoadingCourseDetailsFrag = (ProgressBar) findViewById(R.id.ProgressBarLoadingCourseDetailsFrag);
        CourseImageView = (ImageView) findViewById(R.id.CourseImageView);


        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pager.setOffscreenPageLimit(1);
        tabs.setBackgroundColor(Color.parseColor("#F2F2F2"));
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
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

        /*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appcolor));
        tabLayout.addTab(tabLayout.newTab().setText("Easy"));
        tabLayout.addTab(tabLayout.newTab().setText("Intermediate"));
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        */

        courseAdapter = new RootFragmentAdapter(CourseDetails.this, getSupportFragmentManager());
        pager.setAdapter(courseAdapter);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);




        /*final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs= (SlidingTabLayout) findViewById(R.id.tabs);
        pager.setOffscreenPageLimit(2);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                if (position == 0) {
                   // analysistoolBar.setVisibility(View.GONE);
                    //resultToolBar.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                  //  resultToolBar.setVisibility(View.GONE);
                    //analysistoolBar.setVisibility(View.VISIBLE);
                }
                return Color.parseColor("#4684EE");
            }
        });

        *//*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appcolor));
        tabLayout.addTab(tabLayout.newTab().setText("Easy"));
        tabLayout.addTab(tabLayout.newTab().setText("Intermediate"));
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        *//*

        courseAdapter = new FragmentAdapter(CourseDetails.this, getSupportFragmentManager());
        pager.setAdapter(courseAdapter);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);*/
        // pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       /* tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }
        });*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout111);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        headingTextView = (TextView) toolbar.findViewById(R.id.textViewHeading);
        // imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        //imageViewSearchIcon.setVisibility(View.GONE);
        headingTextView.setText(courseName);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getUserInfo();
    }


    public class RootFragmentAdapter extends FragmentStatePagerAdapter {
        Context context;
        String TITLES[] = {"DESCRIPTION", "EXAMINATIONS"};

        public RootFragmentAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DetailsFrag frag1 = new DetailsFrag();
                    return frag1;
                case 1:
                    ExamsFrag frag2 = new ExamsFrag();
                    return frag2;

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


    @SuppressLint("ValidFragment")
    public class ExamsFrag extends Fragment {
        public ExamsFrag() {
        }

        String duration = "";

        ArrayList<ParentGetSet> parentArrayList = null;
        ArrayList<ChildGetSet> childArraList = null;
        ArrayList<ArrayList<ChildGetSet>> childXXX = null;

        SlidingTabLayout tabs;
        FragmentAdapter fragmentAdapter = null;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if(isVisibleToUser){
                fragmentAdapter.notifyDataSetChanged();
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Toast.makeText(CourseDetails.this, "inside oncreateview()1", Toast.LENGTH_SHORT).show();

            Log.e("inside","oncreateview of ExamsFrag");
            View view = inflater.inflate(R.layout.exams_frag, container, false);

            final ViewPager pager = (ViewPager) view.findViewById(R.id.pager1);
            tabs = (SlidingTabLayout) view.findViewById(R.id.tabs1);
            pager.setOffscreenPageLimit(1);
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    if (position == 0) {
                        // analysistoolBar.setVisibility(View.GONE);
                        //resultToolBar.setVisibility(View.VISIBLE);
                    } else if (position == 1) {
                        //  resultToolBar.setVisibility(View.GONE);
                        //analysistoolBar.setVisibility(View.VISIBLE);
                    }
                    return Color.parseColor("#388fce");//#509BD3  388FCE
                }
            });

        /*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appcolor));
        tabLayout.addTab(tabLayout.newTab().setText("Easy"));
        tabLayout.addTab(tabLayout.newTab().setText("Intermediate"));
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        */

            fragmentAdapter = new FragmentAdapter(getActivity(),getChildFragmentManager());
            pager.setAdapter(fragmentAdapter);
            tabs.setDistributeEvenly(true);
            tabs.setViewPager(pager);
            return view;
        }
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        Context context;
        String TITLES[] = {"EASY", "INTERMEDIATE", "ADVANCED"};

        public FragmentAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Frag1 frag1 = new Frag1();
                    return frag1;
                case 1:
                    Frag2 frag2 = new Frag2();
                    return frag2;
                case 2:
                    Frag3 frag3 = new Frag3();
                    return frag3;
                default:
                    return null;
            }

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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

    @SuppressLint("ValidFragment")
    public static class DetailsFrag extends Fragment {
        public DetailsFrag() {
        }

        String duration = "";

        ArrayList<ParentGetSet> parentArrayList = null;
        ArrayList<ChildGetSet> childArraList = null;
        ArrayList<ArrayList<ChildGetSet>> childXXX = null;

        SlidingTabLayout tabs;
        String yourRank = "";
        //TextView NoexamsTextView;
        //TextView statusTextView;
        LinearLayout linearLayoutrDetailsFragment;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Toast.makeText(CourseDetails.this, "inside oncreateview()1", Toast.LENGTH_SHORT).show();
            View view = inflater.inflate(R.layout.details_frag, container, false);
            suscriptionDateTextView = (TextView) view.findViewById(R.id.suscriptionDateTextView);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            rankTextView = (TextView) view.findViewById(R.id.rankTextView);
            examStatusTextView = (TextView) view.findViewById(R.id.examStatusTextView);
            linearLayoutrDetailsFragment = (LinearLayout) view.findViewById(R.id.linearLayoutrDetailsFragment);

            //NoexamsTextView= (TextView) view.findViewById(R.id.NoexamsTextView);
            //statusTextView= (TextView) view.findViewById(R.id.statusTextView);
            //linearLayoutpager= (LinearLayout) view.findViewById(R.id.linearLayoutpager);

            getUserInfo();
            return view;

        }

        public void getUserInfo() {
            try {
                linearLayoutrDetailsFragment.setVisibility(View.GONE);
                linearLayoutMain.setVisibility(View.GONE);
                ProgressBarLoadingCourseDetailsFrag.setVisibility(View.VISIBLE);
                JSONObject sendObject = new JSONObject();
                sendObject.put("action", "getsubscribedcoursedetails");
                sendObject.put("userid", CommonMethods.userid);
                sendObject.put("wherecondition", "1=1");
                sendObject.put("courseid", Subcourseid);
                Log.i("request is", sendObject.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response is", response.toString());
                        try {
                            String courseItems = response.getString("SubscribedCourseDetails");
                            JSONArray array1 = new JSONArray(courseItems);
                            for (int i = 0; i < array1.length(); i++) {
                                if (array1.getJSONObject(i).getString("examstat").toString().contains("out of")) {
                                    examStatusTextView.setText(array1.getJSONObject(i).getString("examstat").toString().replace("out of", "/"));
                                } else {
                                    examStatusTextView.setText(array1.getJSONObject(i).getString("examstat").toString());
                                }
                                yourRank = array1.getJSONObject(i).getString("yourrank");
                                if (array1.getJSONObject(i).getString("yourrank").contains("out of")) {
                                    rankTextView.setText(array1.getJSONObject(i).getString("yourrank").replace("out of", "/"));
                                } else {
                                    rankTextView.setText(array1.getJSONObject(i).getString("yourrank"));
                                }
                                String subscribedon = array1.getJSONObject(i).getString("subscribedon");
                                examlevel = array1.getJSONObject(i).getString("examlevel");
                                outOf = array1.getJSONObject(i).getString("outof");
                                String output = subscribedon.substring(0, 10);
                                DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
                                Date date1 = originalFormat.parse(output);
                                String formattedDate = targetFormat.format(date1);
                                suscriptionDateTextView.setText(formattedDate);
                                if (array1.getJSONObject(i).getString("courseimage").length() > 0) {
                                    Picasso.with(getActivity()).load(array1.getJSONObject(i).getString("courseimage")).placeholder(R.drawable.ic_no_image_availeble)
                                            .error(R.drawable.ic_no_image_availeble).into(CourseImageView);
                                } else {
                                    Picasso.with(getActivity()).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                            .error(R.drawable.ic_no_image_availeble).into(CourseImageView);
                                }

                                String Description = array1.getJSONObject(i).getString("description").toString();
                                if (!Description.equals("")) {
                                    textViewDescription.setText(Description);
                                } else {
                                    textViewDescription.setVisibility(View.GONE);
                                }

                                if (Description.length() > 70) {
                                    makeTextViewResizable(textViewDescription, 2, "Read More", true);
                                }

                            }

                            String courseDetails = response.getString("CourseExamDetails");
                            JSONArray array2 = new JSONArray(courseDetails);
                            /*
                            if(array2.length()==0){
                                //NoexamsTextView.setVisibility(View.VISIBLE);
                                linearLayoutpager.setVisibility(View.GONE);
                                statusTextView.setVisibility(View.GONE);
                            } else {
                                //NoexamsTextView.setVisibility(View.GONE);
                                linearLayoutpager.setVisibility(View.VISIBLE);
                                statusTextView.setVisibility(View.VISIBLE);
                            }
                            */
                            linearLayoutrDetailsFragment.setVisibility(View.VISIBLE);
                            linearLayoutMain.setVisibility(View.VISIBLE);
                            ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getUserInfo();
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
                                            getUserInfo();
                                        }
                                    });
                            snackbar.show();
                            ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);
                            error.printStackTrace();
                        }

                    }
                });
                requestQueue.add(customRequest);
            } catch (Exception e) {

            }
        }
    }


    @SuppressLint("ValidFragment")
    public static class Frag1 extends Fragment {
        public Frag1() {
        }

        String duration = "";


        ArrayList<ParentGetSet> parentArrayList = null;
        ArrayList<ChildGetSet> childArraList = null;
        ArrayList<ArrayList<ChildGetSet>> childXXX = null;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if(isVisibleToUser/*&&isResumed()*/){
                getCorseInfo();
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Toast.makeText(CourseDetails.this, "inside oncreateview()1", Toast.LENGTH_SHORT).show();
            Log.e("inside","oncreateview of 1");
            View view = inflater.inflate(R.layout.tab1_list, container, false);
            tab1ListView1 = (ListView) view.findViewById(R.id.tab1ListView);
            takeExamButton1 = (Button) view.findViewById(R.id.takeExamButton);
            buttonAnalysis1 = (Button) view.findViewById(R.id.buttonAnalysis);
            //progressbarLoadMore1= (ProgressBar) view.findViewById(R.id.progressbarLoadMore);
            notsubTextView1 = (TextView) view.findViewById(R.id.notsubTextView1);
            coordinatorLayout1 = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout1);
            progressbarLoadMore1 = (ProgressBar) view.findViewById(R.id.progressbarLoadMore1);
            parentArrayList = new ArrayList<ParentGetSet>();
            childArraList = new ArrayList<ChildGetSet>();
            childXXX = new ArrayList<ArrayList<ChildGetSet>>();
            getCorseInfo();
            takeExamButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dia = new Dialog(getActivity());
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.exam_instruction);
                    TextView durationText = (TextView) dia.findViewById(R.id.durationText);
                    String[] parts = childArraList.get(0).getDuration().split(":");
                    String hours = parts[0];
                    String mins = parts[1];
                    int hours1 = Integer.parseInt(hours);
                    int mins1 = (hours1 * 60) + Integer.parseInt(mins);
                    if (mins1 < 100) {
                        if (mins1 == 1) {
                            durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " min");
                        }
                        durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " mins");
                    } else {
                        if (Integer.parseInt(hours) > 1) {
                            if (Integer.parseInt(mins) > 1) {

                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " mins");
                            } else {

                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " min");
                            }
                        } else {
                            if (Integer.parseInt(mins) > 1) {

                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " mins");
                            } else {

                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " min");
                            }

                        }

                    }
                    Button StartButton = (Button) dia.findViewById(R.id.StartButton);
                    ImageView imageViewCancel = (ImageView) dia.findViewById(R.id.imageViewCancel);
                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }
                    });

                    StartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Questions.class);
                            intent.putExtra("examtitle", childArraList.get(0).getExamTitle());

                            if(childArraList.get(0).getCorrectanswers().equals("")) {
                                intent.putExtra("count", childArraList.get(0).getCount());
                            }
                            else{
                                intent.putExtra("count", String.valueOf(Integer.parseInt(childArraList.get(0).getCount())+1));
                            }
                            intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                            Log.v("count", childArraList.get(0).getCount());
                            intent.putExtra("examlevel", childArraList.get(0).getExaminationlevel());
                            intent.putExtra("courseid", Subcourseid);
                            intent.putExtra("date", childArraList.get(0).getExamtakendate());
                            intent.putExtra("examinationid", childArraList.get(0).getExaminationid());
                            intent.putExtra("duration", childArraList.get(0).getDuration());

                            startActivity(intent);
                            dia.dismiss();
                        }
                    });
                    dia.show();
                }
            });

            tab1ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!childArraList.get(position).getTotalscore().equals("") &&
                            !childArraList.get(position).getRanking().equals("") &&
                            !childArraList.get(position).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(position).getExamTitle());
                        intent.putExtra("level", childArraList.get(position).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(position).getCount());
                        Log.v("count in course details", childArraList.get(position).getCount());
                        // Toast.makeText(CourseDetails.this,childArraList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(position).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(position).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(position).getMaxCount());
                        intent.putExtra("duration", childArraList.get(position).getDuration());
                        intent.putExtra("examId", childArraList.get(position).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(position).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(position).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(position).getNoofquestions());
                        startActivity(intent);
                    } /*else {
                        Toast.makeText(getActivity(), "Contents is empty", Toast.LENGTH_SHORT).show();
                    }*/
                }
            });
            buttonAnalysis1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!childArraList.get(0).getTotalscore().equals("") &&
                            !childArraList.get(0).getRanking().equals("") &&
                            !childArraList.get(0).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(0).getExamTitle());
                        intent.putExtra("level", childArraList.get(0).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(0).getCount());
                        Log.v("count in course details", childArraList.get(0).getCount());
                        // Toast.makeText(CourseDetails.this,childArraList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(0).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(0).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                        intent.putExtra("duration", childArraList.get(0).getDuration());
                        intent.putExtra("examId", childArraList.get(0).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(0).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(0).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(0).getNoofquestions());
                        startActivity(intent);
                    }
                }
            });
            return view;
        }

        public void getCorseInfo() {
            try {
                Log.e("inside","getCorseInfo of 1");
                progressbarLoadMore1.setVisibility(View.VISIBLE);
                JSONObject sendObject = new JSONObject();
                sendObject.put("action", "getsubscribedcoursedetails");
                sendObject.put("userid", CommonMethods.userid);
                sendObject.put("wherecondition", "1=1");
                sendObject.put("courseid", Subcourseid);
                Log.i("request is", sendObject.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response is", response.toString());
                        try {
                            String courseDetails = response.getString("CourseExamDetails");
                            JSONArray array2 = new JSONArray(courseDetails);
                            if (!(examlevel.contains("Easy"))) {
                                notsubTextView1.setVisibility(View.VISIBLE);
                                progressbarLoadMore1.setVisibility(View.GONE);
                            }
                            /*else{
                                notsubTextView1.setVisibility(View.GONE);
                            }*/
                            childArraList = new ArrayList<ChildGetSet>();
                            for (int i = 0; i < array2.length(); i++) {

                                title = array2.getJSONObject(i).getString("title");
                                examtype = array2.getJSONObject(i).getString("examtype");
                                examinationId = array2.getJSONObject(i).getString("examinationid");
                                duration = array2.getJSONObject(i).getString("duration");


                                if (examlevel.contains("Easy")) {

                                    ParentGetSet parentGetSet = null;
                                    ChildGetSet childGetSet = null;
                                    if (examtype.equals("Easy")) {
                                        maxtimeusercantakeexam1 = array2.getJSONObject(i).getString("maxtimeusercantakeexam");

                                        if (response.toString().contains(title + "Easy")) {

                                            String easy = response.getString(title + "Easy");
                                            JSONArray array4 = new JSONArray(easy);


                                       /* parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                        parentGetSet.setRank(array4.getJSONObject(0).getString("ranking"));
                                        parentGetSet.setScore(array4.getJSONObject(0).getString("totalscore"));
                                        parentGetSet.setExamTakenOn(array4.getJSONObject(0).getString("examtakendate"));
                                        parentGetSet.setNumOfAttempts(array4.getJSONObject(0).getString("count"));
                                        parentArrayList.add(parentGetSet);*/


                                            // textViewExamTitle.setText(array4.getJSONObject(0).getString("title"));
                                            //textViewRank.setText(array4.getJSONObject(0).getString("ranking"));
                                            /// TextViewScore.setText(array4.getJSONObject(0).getString("totalscore"));
                                            // textViewExamTakenOn.setText(array4.getJSONObject(0).getString("examtakendate"));
                                            // textViewNoOfAttempts.setText(array4.getJSONObject(0).getString("count"));


                                            ///for (int j = 0; j < array4.length(); j++) {

                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                            childGetSet.setCorrectanswers(array4.getJSONObject(0).getString("correctanswers"));
                                            childGetSet.setNoofquestions(array4.getJSONObject(0).getString("noofquestions"));
                                            childGetSet.setCount(array4.getJSONObject(0).getString("count"));
                                            childGetSet.setDuration(array4.getJSONObject(0).getString("duration"));
                                            childGetSet.setExaminationid(array4.getJSONObject(0).getString("examinationid"));
                                            childGetSet.setExamtakendate(array4.getJSONObject(0).getString("examtakendate"));
                                            childGetSet.setRanking(array4.getJSONObject(0).getString("ranking"));
                                            childGetSet.setTotalpercentage(array4.getJSONObject(0).getString("totalpercentage"));
                                            childGetSet.setTotalscore(array4.getJSONObject(0).getString("totalscore"));
                                            childGetSet.setWronganswers(array4.getJSONObject(0).getString("wronganswers"));
                                            childGetSet.setMaxCount(array4.getJSONObject(0).getString("maxtimeusercantakeexam"));
                                            childGetSet.setExaminationlevel("Easy");
                                            childArraList.add(childGetSet);
                                            countEasy = array4.getJSONObject(0).getString("count");

                                        } else {
                                        /*parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(title);
                                        parentGetSet.setRank("NA");
                                        parentGetSet.setNumOfAttempts("0 out of 3");
                                        parentGetSet.setScore("NA");
                                        parentGetSet.setExamTakenOn("none");
                                        parentArrayList.add(parentGetSet);*/
                                            // MainLayout.setVisibility(View.GONE);

                                            //takeExamButton.setVisibility(View.VISIBLE);

                                            //childArraList = new ArrayList<ChildGetSet>();
                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(title);
                                            childGetSet.setCorrectanswers("");
                                            childGetSet.setNoofquestions("");
                                            childGetSet.setCount("1");
                                            childGetSet.setDuration(duration);
                                            childGetSet.setExaminationid(examinationId);
                                            childGetSet.setExamtakendate("");
                                            childGetSet.setRanking("");
                                            childGetSet.setTotalpercentage("");
                                            childGetSet.setTotalscore("");
                                            childGetSet.setMaxCount(maxtimeusercantakeexam1);
                                            childGetSet.setWronganswers("");
                                            childGetSet.setExaminationlevel("Easy");
                                            childArraList.add(childGetSet);


                                        }
                                    }

                                    // childXXX.add(childArraList);


                                }

                            }



                            if (childArraList.size()>0&&childArraList.get(0).getExamtakendate().equals("")) {
                                takeExamButton1.setVisibility(View.VISIBLE);
                            }else{

                                if(Integer.parseInt(countEasy)<Integer.parseInt(maxtimeusercantakeexam1)){
                                takeExamButton1.setVisibility(View.VISIBLE);
                                takeExamButton1.setText("TAKE EXAM AGAIN");}
                            }

                            if(!countEasy.equals("")){
                            if(Integer.parseInt(countEasy)>=Integer.parseInt(maxtimeusercantakeexam1)){
                                buttonAnalysis1.setVisibility(View.VISIBLE);
                            }
                            }
                            CourseDetailsAdapter courseDetailsAdapter = new CourseDetailsAdapter(getActivity(), childArraList, Subcourseid, maxtimeusercantakeexam1);
                            tab1ListView1.setAdapter(courseDetailsAdapter);
                            //ExamExpandableListAdapter expandableAdapter = new ExamExpandableListAdapter(getContext(), parentArrayList, childXXX, Subcourseid, outOf);
                            //listViewDetails1.setAdapter(expandableAdapter);

                            progressbarLoadMore1.setVisibility(View.GONE);

                            //ProgressBarLoadingFrag1.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Log.v("error", error.toString());
                            progressbarLoadMore1.setVisibility(View.GONE);
                            //ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout1, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();

                            error.printStackTrace();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout1, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();
                            progressbarLoadMore1.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
                });

                requestQueue.add(customRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @SuppressLint("ValidFragment")

    public static class Frag2 extends Fragment {
        public Frag2() {

        }

        String duration = "";


        ArrayList<ParentGetSet> parentArrayList = null;
        ArrayList<ChildGetSet> childArraList = null;
        ArrayList<ArrayList<ChildGetSet>> childXXX = null;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if(isVisibleToUser&&isResumed()){
                getCorseInfo();
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Toast.makeText(CourseDetails.this, "inside oncreateview()2", Toast.LENGTH_SHORT).show();
            View view = inflater.inflate(R.layout.tab2_list, container, false);
            tab1ListView2 = (ListView) view.findViewById(R.id.tab1ListView2);
            takeExamButton2 = (Button) view.findViewById(R.id.takeExamButton);
            buttonAnalysis2= (Button) view.findViewById(R.id.buttonAnalysis);
            notsubTextView2 = (TextView) view.findViewById(R.id.notsubTextView2);
            progressbarLoadMore2 = (ProgressBar) view.findViewById(R.id.progressbarLoadMore2);
            coordinatorLayout2 = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout2);

            parentArrayList = new ArrayList<ParentGetSet>();
            childArraList = new ArrayList<ChildGetSet>();
            childXXX = new ArrayList<ArrayList<ChildGetSet>>();



            takeExamButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dia = new Dialog(getActivity());
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.exam_instruction);
                    TextView durationText = (TextView) dia.findViewById(R.id.durationText);
                    String[] parts = childArraList.get(0).getDuration().split(":");
                    String hours = parts[0];
                    String mins = parts[1];
                    int hours1 = Integer.parseInt(hours);
                    int mins1 = (hours1 * 60) + Integer.parseInt(mins);
                    if (mins1 < 100) {
                        if (mins1 == 1) {
                            durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " min");
                        }
                        durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " mins");
                    } else {
                        if (Integer.parseInt(hours) > 1) {
                            if (Integer.parseInt(mins) > 1) {
                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " mins");
                            } else {
                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " min");
                            }
                        } else {
                            if (Integer.parseInt(mins) > 1) {
                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " mins");
                            } else {
                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " min");
                            }

                        }

                    }
                    Button StartButton = (Button) dia.findViewById(R.id.StartButton);
                    ImageView imageViewCancel = (ImageView) dia.findViewById(R.id.imageViewCancel);
                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }
                    });

                    StartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Questions.class);
                            intent.putExtra("examtitle", childArraList.get(0).getExamTitle());
                            if(childArraList.get(0).getCorrectanswers().equals("")) {
                                intent.putExtra("count", childArraList.get(0).getCount());
                            }
                            else{
                                intent.putExtra("count", String.valueOf(Integer.parseInt(childArraList.get(0).getCount())+1));
                            }
                            intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                            Log.v("count", childArraList.get(0).getCount());
                            intent.putExtra("examlevel", childArraList.get(0).getExaminationlevel());
                            intent.putExtra("courseid", Subcourseid);
                            intent.putExtra("date", childArraList.get(0).getExamtakendate());
                            intent.putExtra("examinationid", childArraList.get(0).getExaminationid());
                            intent.putExtra("duration", childArraList.get(0).getDuration());

                            startActivity(intent);
                            dia.dismiss();
                        }
                    });
                    dia.show();
                }
            });

            tab1ListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (!childArraList.get(position).getTotalscore().equals("") &&
                            !childArraList.get(position).getRanking().equals("") &&
                            !childArraList.get(position).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(position).getExamTitle());
                        intent.putExtra("level", childArraList.get(position).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(position).getCount());
                        //Toast.makeText(CourseDetails.this, childArraList.get(position).getCount(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(position).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(position).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(position).getMaxCount());
                        intent.putExtra("duration", childArraList.get(position).getDuration());
                        intent.putExtra("examId", childArraList.get(position).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(position).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(position).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(position).getNoofquestions());

                        startActivity(intent);
                    }

                }
            });

            buttonAnalysis2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!childArraList.get(0).getTotalscore().equals("") &&
                            !childArraList.get(0).getRanking().equals("") &&
                            !childArraList.get(0).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(0).getExamTitle());
                        intent.putExtra("level", childArraList.get(0).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(0).getCount());
                        Log.v("count in course details", childArraList.get(0).getCount());
                        // Toast.makeText(CourseDetails.this,childArraList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(0).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(0).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                        intent.putExtra("duration", childArraList.get(0).getDuration());
                        intent.putExtra("examId", childArraList.get(0).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(0).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(0).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(0).getNoofquestions());
                        startActivity(intent);
                    }
                }
            });
            return view;

        }

        public void getCorseInfo() {
            try {
                progressbarLoadMore2.setVisibility(View.VISIBLE);
                tab1ListView2.setVisibility(View.GONE);
                JSONObject sendObject = new JSONObject();
                sendObject.put("action", "getsubscribedcoursedetails");
                sendObject.put("userid", CommonMethods.userid);
                sendObject.put("wherecondition", "1=1");
                sendObject.put("courseid", Subcourseid);
                Log.i("request is", sendObject.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response is", response.toString());
                        try {
                            String courseDetails = response.getString("CourseExamDetails");
                            JSONArray array2 = new JSONArray(courseDetails);
                            if (!(examlevel.contains("Intermediate"))) {
                                notsubTextView2.setVisibility(View.VISIBLE);
                                progressbarLoadMore2.setVisibility(View.GONE);
                                tab1ListView2.setVisibility(View.VISIBLE);
                                //listViewDetails1.setVisibility(View.GONE);
                            }
                            childArraList = new ArrayList<ChildGetSet>();
                            for (int i = 0; i < array2.length(); i++) {

                                title = array2.getJSONObject(i).getString("title");
                                examtype = array2.getJSONObject(i).getString("examtype");
                                examinationId = array2.getJSONObject(i).getString("examinationid");
                                duration = array2.getJSONObject(i).getString("duration");


                                if (examlevel.contains("Intermediate")) {

                                    ParentGetSet parentGetSet = null;
                                    ChildGetSet childGetSet = null;
                                    if (examtype.equals("Intermediate")) {
                                        maxtimeusercantakeexam2 = array2.getJSONObject(i).getString("maxtimeusercantakeexam");

                                        if (response.toString().contains(title + "Intermediate")) {

                                            String easy = response.getString(title + "Intermediate");
                                            JSONArray array4 = new JSONArray(easy);


                                       /* parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                        parentGetSet.setRank(array4.getJSONObject(0).getString("ranking"));
                                        parentGetSet.setScore(array4.getJSONObject(0).getString("totalscore"));
                                        parentGetSet.setExamTakenOn(array4.getJSONObject(0).getString("examtakendate"));
                                        parentGetSet.setNumOfAttempts(array4.getJSONObject(0).getString("count"));
                                        parentArrayList.add(parentGetSet);*/


                                            // textViewExamTitle.setText(array4.getJSONObject(0).getString("title"));
                                            //textViewRank.setText(array4.getJSONObject(0).getString("ranking"));
                                            /// TextViewScore.setText(array4.getJSONObject(0).getString("totalscore"));
                                            // textViewExamTakenOn.setText(array4.getJSONObject(0).getString("examtakendate"));
                                            // textViewNoOfAttempts.setText(array4.getJSONObject(0).getString("count"));


                                            ///for (int j = 0; j < array4.length(); j++) {

                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                            childGetSet.setCorrectanswers(array4.getJSONObject(0).getString("correctanswers"));
                                            childGetSet.setNoofquestions(array4.getJSONObject(0).getString("noofquestions"));
                                            childGetSet.setCount(array4.getJSONObject(0).getString("count"));
                                            childGetSet.setDuration(array4.getJSONObject(0).getString("duration"));
                                            childGetSet.setExaminationid(array4.getJSONObject(0).getString("examinationid"));
                                            childGetSet.setExamtakendate(array4.getJSONObject(0).getString("examtakendate"));
                                            childGetSet.setRanking(array4.getJSONObject(0).getString("ranking"));
                                            childGetSet.setTotalpercentage(array4.getJSONObject(0).getString("totalpercentage"));
                                            childGetSet.setTotalscore(array4.getJSONObject(0).getString("totalscore"));
                                            childGetSet.setWronganswers(array4.getJSONObject(0).getString("wronganswers"));
                                            childGetSet.setMaxCount(array4.getJSONObject(0).getString("maxtimeusercantakeexam"));
                                            childGetSet.setExaminationlevel("Intermediate");
                                            childArraList.add(childGetSet);

                                            countIntermediate = array4.getJSONObject(0).getString("count");

                                            //}

                                        } else {
                                        /*parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(title);
                                        parentGetSet.setRank("NA");
                                        parentGetSet.setNumOfAttempts("0 out of 3");
                                        parentGetSet.setScore("NA");
                                        parentGetSet.setExamTakenOn("none");
                                        parentArrayList.add(parentGetSet);*/
                                            // MainLayout.setVisibility(View.GONE);

                                            //takeExamButton.setVisibility(View.VISIBLE);

                                            //childArraList = new ArrayList<ChildGetSet>();
                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(title);
                                            childGetSet.setCorrectanswers("");
                                            childGetSet.setNoofquestions("");
                                            childGetSet.setCount("1");
                                            childGetSet.setDuration(duration);
                                            childGetSet.setExaminationid(examinationId);
                                            childGetSet.setExamtakendate("");
                                            childGetSet.setRanking("");
                                            childGetSet.setTotalpercentage("");
                                            childGetSet.setMaxCount(maxtimeusercantakeexam2);
                                            childGetSet.setTotalscore("");
                                            childGetSet.setWronganswers("");
                                            childGetSet.setExaminationlevel("Intermediate");
                                            childArraList.add(childGetSet);


                                        }
                                    }

                                    // childXXX.add(childArraList);


                                }

                            }

                            if (childArraList.size()>0&&childArraList.get(0).getExamtakendate().equals("")) {
                                takeExamButton2.setVisibility(View.VISIBLE);
                            }
                            else{
                                if(Integer.parseInt(countIntermediate)<Integer.parseInt(maxtimeusercantakeexam2)){
                                takeExamButton2.setVisibility(View.VISIBLE);
                                takeExamButton2.setText("TAKE EXAM AGAIN");}
                            }
if(!countIntermediate.equals("")){
                            if(Integer.parseInt(countIntermediate)>=Integer.parseInt(maxtimeusercantakeexam2)){
                                buttonAnalysis2.setVisibility(View.VISIBLE);
                            }}
                            CourseDetailsAdapter courseDetailsAdapter = new CourseDetailsAdapter(getActivity(), childArraList, Subcourseid, maxtimeusercantakeexam2);
                            tab1ListView2.setAdapter(courseDetailsAdapter);
                            //ExamExpandableListAdapter expandableAdapter = new ExamExpandableListAdapter(getContext(), parentArrayList, childXXX, Subcourseid, outOf);
                            //listViewDetails1.setAdapter(expandableAdapter);

                            progressbarLoadMore2.setVisibility(View.GONE);
                            tab1ListView2.setVisibility(View.VISIBLE);

                            //ProgressBarLoadingFrag1.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Log.v("error", error.toString());
                            progressbarLoadMore2.setVisibility(View.GONE);
                            tab1ListView2.setVisibility(View.VISIBLE);
                            //ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout2, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();

                            error.printStackTrace();
                        } else {
                            Log.v("error", error.toString());
                            progressbarLoadMore2.setVisibility(View.GONE);
                            //ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout2, getActivity().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();

                            error.printStackTrace();
                        }
                    }

                });

                requestQueue.add(customRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressLint("ValidFragment")
    public static class Frag3 extends Fragment {
        public Frag3() {

        }

        String duration = "";

        ArrayList<ParentGetSet> parentArrayList = null;
        ArrayList<ChildGetSet> childArraList = null;
        ArrayList<ArrayList<ChildGetSet>> childXXX = null;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if(isVisibleToUser&&isResumed()){
                getCorseInfo();
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //Toast.makeText(CourseDetails.this, "inside oncreateview()3", Toast.LENGTH_SHORT).show();
            View view = inflater.inflate(R.layout.tab3_list, container, false);
            tab1ListView3 = (ListView) view.findViewById(R.id.tab1ListView3);
            takeExamButton3 = (Button) view.findViewById(R.id.takeExamButton);
            buttonAnalysis3 = (Button) view.findViewById(R.id.buttonAnalysis);
            notsubTextView3 = (TextView) view.findViewById(R.id.notsubTextView3);
            progressbarLoadMore3 = (ProgressBar) view.findViewById(R.id.progressbarLoadMore3);
            coordinatorLayout3 = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout3);
            parentArrayList = new ArrayList<ParentGetSet>();
            childArraList = new ArrayList<ChildGetSet>();
            childXXX = new ArrayList<ArrayList<ChildGetSet>>();


            takeExamButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dia = new Dialog(getActivity());
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.exam_instruction);
                    TextView durationText = (TextView) dia.findViewById(R.id.durationText);
                    String[] parts = childArraList.get(0).getDuration().split(":");
                    String hours = parts[0];
                    String mins = parts[1];
                    int hours1 = Integer.parseInt(hours);
                    int mins1 = (hours1 * 60) + Integer.parseInt(mins);
                    if (mins1 < 100) {
                        if (mins1 == 1) {
                            durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " min");
                        }
                        durationText.setText("1. Total duration of the exam is " + String.valueOf(mins1) + " mins");
                    } else {
                        if (Integer.parseInt(hours) > 1) {
                            if (Integer.parseInt(mins) > 1) {

                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " mins");
                            } else {

                                durationText.setText("1. Total duration of the exam is " + hours + " hrs " + mins + " min");
                            }
                        } else {
                            if (Integer.parseInt(mins) > 1) {

                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " mins");
                            } else {

                                durationText.setText("1. Total duration of the exam is " + hours + " hr " + mins + " min");
                            }

                        }

                    }
                    Button StartButton = (Button) dia.findViewById(R.id.StartButton);
                    ImageView imageViewCancel = (ImageView) dia.findViewById(R.id.imageViewCancel);
                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }
                    });

                    StartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Questions.class);
                            intent.putExtra("examtitle", childArraList.get(0).getExamTitle());
                            if(childArraList.get(0).getCorrectanswers().equals("")) {
                                intent.putExtra("count", childArraList.get(0).getCount());
                            }
                            else{
                                intent.putExtra("count", String.valueOf(Integer.parseInt(childArraList.get(0).getCount())+1));
                            }
                            intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                            Log.v("count", childArraList.get(0).getCount());
                            intent.putExtra("examlevel", childArraList.get(0).getExaminationlevel());
                            intent.putExtra("courseid", Subcourseid);
                            intent.putExtra("date", childArraList.get(0).getExamtakendate());
                            intent.putExtra("examinationid", childArraList.get(0).getExaminationid());
                            intent.putExtra("duration", childArraList.get(0).getDuration());
                            startActivity(intent);
                            dia.dismiss();
                        }
                    });
                    dia.show();
                }
            });

            tab1ListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!childArraList.get(position).getTotalscore().equals("") &&
                            !childArraList.get(position).getRanking().equals("") &&
                            !childArraList.get(position).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(position).getExamTitle());
                        intent.putExtra("level", childArraList.get(position).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(position).getCount());
                        //Toast.makeText(CourseDetails.this, childArraList.get(position).getCount(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(position).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(position).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(position).getMaxCount());
                        //Log.v("maxtimeusercantakeexam in course",childArraList.get(position).getMaxCount());
                        intent.putExtra("duration", childArraList.get(position).getDuration());
                        intent.putExtra("examId", childArraList.get(position).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(position).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(position).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(position).getNoofquestions());

                        startActivity(intent);
                    }
                }
            });

            buttonAnalysis3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!childArraList.get(0).getTotalscore().equals("") &&
                            !childArraList.get(0).getRanking().equals("") &&
                            !childArraList.get(0).getExamtakendate().equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SubDetails.class);
                        intent.putExtra("title", childArraList.get(0).getExamTitle());
                        intent.putExtra("level", childArraList.get(0).getExaminationlevel());
                        intent.putExtra("count", childArraList.get(0).getCount());
                        Log.v("count in course details", childArraList.get(0).getCount());
                        // Toast.makeText(CourseDetails.this,childArraList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", childArraList.get(0).getExamtakendate());
                        intent.putExtra("courseid", Subcourseid);
                        intent.putExtra("yourRank", childArraList.get(0).getRanking());
                        intent.putExtra("maxtimeusercantakeexam", childArraList.get(0).getMaxCount());
                        intent.putExtra("duration", childArraList.get(0).getDuration());
                        intent.putExtra("examId", childArraList.get(0).getExaminationid());
                        intent.putExtra("correctAnswers", childArraList.get(0).getCorrectanswers());
                        intent.putExtra("wrongtAnswers", childArraList.get(0).getWronganswers());
                        intent.putExtra("noOfQuestions", childArraList.get(0).getNoofquestions());
                        startActivity(intent);
                    }
                }
            });
            return view;

        }

        public void getCorseInfo() {
            try {
                progressbarLoadMore3.setVisibility(View.VISIBLE);
                JSONObject sendObject = new JSONObject();
                sendObject.put("action", "getsubscribedcoursedetails");
                sendObject.put("userid", CommonMethods.userid);
                sendObject.put("wherecondition", "1=1");
                sendObject.put("courseid", Subcourseid);
                Log.i("request is", sendObject.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response is", response.toString());
                        try {
                            String courseDetails = response.getString("CourseExamDetails");
                            JSONArray array2 = new JSONArray(courseDetails);
                            if (!(examlevel.contains("Advanced"))) {
                                notsubTextView3.setVisibility(View.VISIBLE);
                                progressbarLoadMore3.setVisibility(View.GONE);
                                //listViewDetails1.setVisibility(View.GONE);
                            }
                            childArraList = new ArrayList<ChildGetSet>();
                            for (int i = 0; i < array2.length(); i++) {

                                title = array2.getJSONObject(i).getString("title");
                                examtype = array2.getJSONObject(i).getString("examtype");
                                examinationId = array2.getJSONObject(i).getString("examinationid");
                                duration = array2.getJSONObject(i).getString("duration");


                                if (examlevel.contains("Advanced")) {

                                    ParentGetSet parentGetSet = null;
                                    ChildGetSet childGetSet = null;

                                    if (examtype.equals("Advanced")) {
                                        maxtimeusercantakeexam3 = array2.getJSONObject(i).getString("maxtimeusercantakeexam");

                                        if (response.toString().contains(title + "Advanced")) {

                                            String easy = response.getString(title + "Advanced");
                                            JSONArray array4 = new JSONArray(easy);


                                       /* parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                        parentGetSet.setRank(array4.getJSONObject(0).getString("ranking"));
                                        parentGetSet.setScore(array4.getJSONObject(0).getString("totalscore"));
                                        parentGetSet.setExamTakenOn(array4.getJSONObject(0).getString("examtakendate"));
                                        parentGetSet.setNumOfAttempts(array4.getJSONObject(0).getString("count"));
                                        parentArrayList.add(parentGetSet);*/


                                            // textViewExamTitle.setText(array4.getJSONObject(0).getString("title"));
                                            //textViewRank.setText(array4.getJSONObject(0).getString("ranking"));
                                            /// TextViewScore.setText(array4.getJSONObject(0).getString("totalscore"));
                                            // textViewExamTakenOn.setText(array4.getJSONObject(0).getString("examtakendate"));
                                            // textViewNoOfAttempts.setText(array4.getJSONObject(0).getString("count"));


                                            ///for (int j = 0; j < array4.length(); j++) {

                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(array4.getJSONObject(0).getString("title"));
                                            childGetSet.setCorrectanswers(array4.getJSONObject(0).getString("correctanswers"));
                                            childGetSet.setNoofquestions(array4.getJSONObject(0).getString("noofquestions"));
                                            childGetSet.setCount(array4.getJSONObject(0).getString("count"));
                                            childGetSet.setDuration(array4.getJSONObject(0).getString("duration"));
                                            childGetSet.setExaminationid(array4.getJSONObject(0).getString("examinationid"));
                                            childGetSet.setExamtakendate(array4.getJSONObject(0).getString("examtakendate"));
                                            childGetSet.setRanking(array4.getJSONObject(0).getString("ranking"));
                                            childGetSet.setTotalpercentage(array4.getJSONObject(0).getString("totalpercentage"));
                                            childGetSet.setTotalscore(array4.getJSONObject(0).getString("totalscore"));
                                            childGetSet.setWronganswers(array4.getJSONObject(0).getString("wronganswers"));
                                            childGetSet.setExaminationlevel("Advanced");
                                            childGetSet.setMaxCount(array4.getJSONObject(0).getString("maxtimeusercantakeexam"));
                                            childArraList.add(childGetSet);
                                            countAdvance = array4.getJSONObject(0).getString("count");

                                            //}

                                        } else {
                                        /*parentGetSet = new ParentGetSet();
                                        parentGetSet.setExamTitle(title);
                                        parentGetSet.setRank("NA");
                                        parentGetSet.setNumOfAttempts("0 out of 3");
                                        parentGetSet.setScore("NA");
                                        parentGetSet.setExamTakenOn("none");
                                        parentArrayList.add(parentGetSet);*/
                                            // MainLayout.setVisibility(View.GONE);

                                            //takeExamButton.setVisibility(View.VISIBLE);

                                            //childArraList = new ArrayList<ChildGetSet>();
                                            childGetSet = new ChildGetSet();
                                            childGetSet.setExamTitle(title);
                                            childGetSet.setCorrectanswers("");
                                            childGetSet.setNoofquestions("");
                                            childGetSet.setCount("1");
                                            childGetSet.setDuration(duration);
                                            childGetSet.setExaminationid(examinationId);
                                            childGetSet.setExamtakendate("");
                                            childGetSet.setRanking("");
                                            childGetSet.setTotalpercentage("");
                                            childGetSet.setTotalscore("");
                                            childGetSet.setWronganswers("");
                                            childGetSet.setMaxCount(maxtimeusercantakeexam3);
                                            childGetSet.setExaminationlevel("Advanced");
                                            childArraList.add(childGetSet);


                                        }
                                    }

                                    // childXXX.add(childArraList);


                                }

                            }

                            if (childArraList.size()>0&&childArraList.get(0).getExamtakendate().equals("")) {
                                takeExamButton3.setVisibility(View.VISIBLE);
                            }else{
                                if(Integer.parseInt(countAdvance)<Integer.parseInt(maxtimeusercantakeexam3)){
                                takeExamButton3.setVisibility(View.VISIBLE);
                                takeExamButton3.setText("TAKE EXAM AGAIN");}
                            }
                            if(!countAdvance.equals("")){
                            if(Integer.parseInt(countAdvance)>=Integer.parseInt(maxtimeusercantakeexam3)){
                                buttonAnalysis3.setVisibility(View.VISIBLE);
                            }}

                            CourseDetailsAdapter courseDetailsAdapter = new CourseDetailsAdapter(getActivity(), childArraList, Subcourseid, maxtimeusercantakeexam3);
                            tab1ListView3.setAdapter(courseDetailsAdapter);
                            //ExamExpandableListAdapter expandableAdapter = new ExamExpandableListAdapter(getContext(), parentArrayList, childXXX, Subcourseid, outOf);
                            //listViewDetails1.setAdapter(expandableAdapter);

                            progressbarLoadMore3.setVisibility(View.GONE);

                            //ProgressBarLoadingFrag1.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Log.v("error", error.toString());
                            progressbarLoadMore3.setVisibility(View.GONE);
                            //ProgressBarLoadingCourseDetailsFrag.setVisibility(View.GONE);

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout3, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();

                            error.printStackTrace();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout3, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCorseInfo();
                                        }
                                    });
                            snackbar.show();
                            progressbarLoadMore3.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }

                });

                requestQueue.add(customRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ReadMoreClick() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {


                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);

                        tv.invalidate();
                        makeTextViewResizable(tv, -2, "Read Less", false);


                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);

                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "Read More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }



}
