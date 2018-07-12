package eniversity.com;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.eniversity.app.R;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import Commmon.Methods.AlertDialogClass;
import Commmon.Methods.AlertForLastQuestion;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import Commmon.Methods.MyViewPager;
import Commmon.Methods.SwipeDirection;
import get.set.QuestionGetSet;


/**
 * Created by soumyay on 4/12/2016.
 */
public class Questions extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    TextView timeTextView;
    TextView NoQuestionsTextView;
    LinearLayout linear1;
    ImageView previousButton;
    ImageView nextButton;
    ImageView imageViewBackIcon;
    ImageView logoutImageView;
    LinearLayout linearMainLayout;
    RelativeLayout MainLayout;
    MyViewPager questionsViewPager;
    MyViewPager customQuestionViewPager;
    QuestionAdapter questionAdapter;
    CountDownTimer count;
    LinearLayout linearLayoutExam;
    LinearLayout linear2;
    LinearLayout linear3;
    RelativeLayout examsLayout;
    TextView textViewHeading;
    static TextView noOfQuestionsTextView;
    long millisecs;
    Bundle b;
    static int marks;
    static String questionAndAnswers = "";
    String AnsweredQuestionIDs = "";
    String resultQuestionAndAnswers = "";
    static int NumberOfCorrectanswers = 0;
    int wronganswers = 0;
    static String resultstatus = "";
    static Double totalScore = 0.0;
    static String answer1 = "";
    static String answer2 = "";
    static String answer3 = "";
    static String answer4 = "";
    static String answer5 = "";
    static RadioGroup radioGroup;
    static String carrot = "^";
    static RadioButton radioButton;
    static int newposn = 0;
    static int posn = 0;
    String subCourseId = "";
    String examinationId = "";
    String examlevel = "";
    String outOf = "";
    String date = "";
    int totalattempted = 0;
    ImageView imageViewAllTheBest;
    String duration = "";
    static String[] ColorValue = new String[100];
    static ArrayList<QuestionGetSet> questions1;
    static TabHost mTabHost;
    static char subStringValue = ' ';
    String isVisible = "";
    int Duration;
    String showcorrectanswers = "";
    String examTitle = "";
    private Timer t;
    boolean selected;
    int pagelimit = 5;
    String maxtimeusercantakeexam = "";
    ProgressBar progressbarLoadQuesions;
    LinearLayout mainLayoutQuestions;
    String rank = "";
    String outof = "";
    String dateofexam = "";
    CoordinatorLayout coordinatorLayout;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    ProgressDialog progressDialog;
    Handler handler = null;
    private boolean isdone=false;

    private static void AddTab(Questions activity, TabHost tabHost,
                               TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ViewParent parent = customQuestionViewPager;
        // or get a reference to the ViewPager and cast it to ViewParent

        parent.requestDisallowInterceptTouchEvent(true);

        // let this view deal with the event or
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionpaper_layout);
        try {
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_timer);
            setSupportActionBar(toolbar);
            examTitle = getIntent().getExtras().getString("examtitle");
            outOf = getIntent().getExtras().getString("count");
            //date=getIntent().getExtras().getString("date");
            Log.v("date in questions", date);
            maxtimeusercantakeexam = getIntent().getExtras().getString("maxtimeusercantakeexam");
            // Toast.makeText(Questions.this,outOf,Toast.LENGTH_SHORT).show();
            Log.v("count", outOf);
            examlevel = getIntent().getExtras().getString("examlevel");
            subCourseId = getIntent().getExtras().getString("courseid");
            examinationId = getIntent().getExtras().getString("examinationid");
            duration = getIntent().getExtras().getString("duration");
            String[] parts = duration.split(":");
            String hours = parts[0];
            String Hours = "" + TimeUnit.HOURS.toMillis(Integer.parseInt(hours));
            String mins = parts[1];
            String Mins = "" + TimeUnit.MINUTES.toMillis(Integer.parseInt(mins));

            Duration = Integer.parseInt(Hours) + Integer.parseInt(Mins);

            //imageViewAllTheBest = (ImageView) findViewById(R.id.imageViewAllTheBest);
            linearLayoutExam = (LinearLayout) findViewById(R.id.linearLayoutExam);
            progressbarLoadQuesions = (ProgressBar) findViewById(R.id.progressbarLoadQuesions);
            mainLayoutQuestions = (LinearLayout) findViewById(R.id.mainLayoutQuestions);
            linear2 = (LinearLayout) findViewById(R.id.linear2);
            linear3 = (LinearLayout) findViewById(R.id.linear3);
            examsLayout = (RelativeLayout) findViewById(R.id.examsLayout);
            imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon_timer);
            timeTextView = (TextView) toolbar.findViewById(R.id.textViewTime);
            logoutImageView = (ImageView) toolbar.findViewById(R.id.logoutImageView);
            //linearMainLayout = (LinearLayout) toolbar.findViewById(R.id.linearMainLayout);
            //MainLayout = (RelativeLayout) toolbar.findViewById(R.id.MainLayout);
            textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
            textViewHeading.setText(examTitle);


            logoutImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertClass cdd = new AlertClass(Questions.this);
                    cdd.show();

                    // count.cancel();

                }
            });
            questionsViewPager = (MyViewPager) findViewById(R.id.questionsViewPager);
            //questionsViewPager.setOffscreenPageLimit(pagelimit);
            questionsViewPager.setOffscreenPageLimit(2);


            customQuestionViewPager = (MyViewPager) findViewById(R.id.questionsViewPager2);
            customQuestionViewPager.setPageTransformer(true, new DefaultTransformer());
            customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);

            linear1 = (LinearLayout) findViewById(R.id.linear1);


            NoQuestionsTextView = (TextView) findViewById(R.id.NoQuestionsTextView);
            previousButton = (ImageView) findViewById(R.id.previousButton);
            nextButton = (ImageView) findViewById(R.id.nextButton);


            questionsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                boolean isDialogShow = false;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == 0) {
                        previousButton.setVisibility(View.GONE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }

                    if (position == (questions1.size() - 1)) {
                        handler = new Handler();
                        nextButton.setVisibility(View.GONE);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!isDialogShow && mTabHost.getCurrentTab() == questions1.size() - 1) {
                                    isDialogShow = true;
                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    Drawable myIcon = getResources().getDrawable(R.drawable.ic_power_settings_new_black_24dp);
                                    myIcon.setBounds(0, 0, 40, 40);
                                    builder.append("You are done with all your questions. Please click on ").append(" ");
                                    builder.setSpan(new ImageSpan(myIcon), builder.length() - 1, builder.length(), 0);
                                    builder.append("  to complete the examination.");

                                    AlertForLastQuestion customDialog = new AlertForLastQuestion(Questions.this, builder);
                                    if (!customDialog.isShowing()) {
                                        if(isdone==false){
                                        customDialog.show();}
                                    }
                                }
                            }
                        };
                        handler.postDelayed(runnable, 1000 * 10); //10 sec
                    } else {
                        nextButton.setVisibility(View.VISIBLE);
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                }

                @Override
                public void onPageSelected(final int position) {
                    if (position != (questions1.size() - 1)) {
                        isDialogShow = false;
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            customQuestionViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int lastPosition = 0;
                boolean isDialogShow = false;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);

                    previousButton.setVisibility(View.GONE);

                    if (position == (questions1.size() - 1)) {
                        handler = new Handler();
                        nextButton.setVisibility(View.GONE);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!isDialogShow && mTabHost.getCurrentTab() == questions1.size() - 1) {
                                    isDialogShow = true;
                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    Drawable myIcon = getResources().getDrawable(R.drawable.ic_power_settings_new_black_24dp);
                                    myIcon.setBounds(0, 0, 40, 40);
                                    builder.append("You are done with all your questions. Please click on ").append(" ");
                                    builder.setSpan(new ImageSpan(myIcon), builder.length() - 1, builder.length(), 0);
                                    builder.append("  to complete the examination.");

                                    AlertForLastQuestion customDialog = new AlertForLastQuestion(Questions.this, builder);
                                    if (!customDialog.isShowing()) {
                                        if (!customDialog.isShowing()) {
                                            if(isdone==false){
                                                customDialog.show();}
                                        }
                                    }
                                }
                            }
                        };
                        handler.postDelayed(runnable, 1000 * 10); //10 sec
                    } else {
                        nextButton.setVisibility(View.VISIBLE);
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                }

                @Override
                public void onPageSelected(final int position) {
                    if (lastPosition < position) {
                        customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);
                        customQuestionViewPager.setCurrentItem(position, true);
                        lastPosition = position;
                    } else {
                        customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);
                        customQuestionViewPager.setCurrentItem(lastPosition, true);
                    }
                    if (position != (questions1.size() - 1)) {
                        isDialogShow = false;
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionsViewPager.getVisibility() == View.VISIBLE) {
                        questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() - 1, true);
                    } else if (customQuestionViewPager.getVisibility() == View.VISIBLE) {
                        customQuestionViewPager.setCurrentItem(customQuestionViewPager.getCurrentItem() - 1, true);
                    }
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionsViewPager.getVisibility() == View.VISIBLE) {
                        questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() + 1, true);
                    } else if (customQuestionViewPager.getVisibility() == View.VISIBLE) {
                        customQuestionViewPager.setCurrentItem(customQuestionViewPager.getCurrentItem() + 1, true);
                    }
                }
            });


            imageViewBackIcon.setVisibility(View.VISIBLE);
            questionAndAnswers = "";
            getQuestions();


             b= savedInstanceState;


            imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questions1.size() == 0) {
                        finish();
                    } else {
                        AlertClass cdd = new AlertClass(Questions.this);
                        cdd.show();
                        // count.cancel();
                    }

                }
            });

            for (int i = 0; i < ColorValue.length; i++) {
                ColorValue[i] = "YELLOW";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        try {
            if (questions1.size() == 0) {
                finish();
            } else {
                AlertClass cdd = new AlertClass(Questions.this);
                cdd.show();
                //count.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQuestions() {

        try {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            progressbarLoadQuesions.setVisibility(View.VISIBLE);
            linearLayoutExam.setVisibility(View.GONE);

            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "generateexam");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("examlevel", examlevel);
            sendObject.put("examinationid", examinationId);
            sendObject.put("courseid", subCourseId);
            Log.i("request is", sendObject.toString());
            questions1 = new ArrayList<>();
            RequestQueue requestQueue = Volley.newRequestQueue(Questions.this);
            CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {

                    Log.i("the response is ", response.toString());

                    try {


                        String Exams = response.getString("Exam");
                        JSONArray examArray = new JSONArray(Exams);

                        if (examArray.length() == 0) {

                            NoQuestionsTextView.setVisibility(View.VISIBLE);
                            timeTextView.setVisibility(View.GONE);
                            logoutImageView.setVisibility(View.GONE);
                            NoQuestionsTextView.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            linearLayoutExam.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            examsLayout.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            linear2.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            linear3.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            linear3.setVisibility(View.GONE);
                            previousButton.setVisibility(View.GONE);
                            nextButton.setVisibility(View.GONE);
                            questionsViewPager.setVisibility(View.GONE);
                            questionsViewPager.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            customQuestionViewPager.setBackgroundColor(getResources().getColor(R.color.lightGray));

                            customQuestionViewPager.setVisibility(View.GONE);

                        }
                        for (int i = 0; i < examArray.length(); i++) {
                            QuestionGetSet questionGetSet = new QuestionGetSet();
                            if (examArray.getJSONObject(i).getString("status").equals("deleted") || examArray.getJSONObject(i).getString("status").equals("blocked")) {
                                questionGetSet.setStatus(examArray.getJSONObject(i).getString("status"));
                                   /* Dialog dialog= new Dialog(Questions.this);
                                    dialog.setTitle(null);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setContentView(R.layout.custom_alert_dialog);
                                    TextView message= (TextView) dialog.findViewById(R.id.txt_dia);
                                    message.setText("Your account has been "+examArray.getJSONObject(i).getString("status"));
                                    Button buttonOK=(Button)dialog.findViewById(R.id.buttonOK);
                                    buttonOK.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new LogOut(Questions.this).execute();
                                            finish();

                                        }
                                    });*/
                            } else {

                                questionGetSet.setQuestion(examArray.getJSONObject(i).getString("questiontitle"));
                                questionGetSet.setAnswer1(examArray.getJSONObject(i).getString("option1"));
                                questionGetSet.setAnswer2(examArray.getJSONObject(i).getString("option2"));
                                questionGetSet.setAnswer3(examArray.getJSONObject(i).getString("option3"));
                                questionGetSet.setAnswer4(examArray.getJSONObject(i).getString("option4"));
                                questionGetSet.setAnswer5(examArray.getJSONObject(i).getString("option5"));
                                questionGetSet.setCorrectAnswer(examArray.getJSONObject(i).getString("correctanswerchoice"));
                                questionGetSet.setNoofchoices(examArray.getJSONObject(i).getString("noofchoices"));
                                questionGetSet.setNoofquestions(examArray.getJSONObject(i).getString("noofquestions"));
                                questionGetSet.setQuestionid(examArray.getJSONObject(i).getString("questionid"));
                                questionGetSet.setOptionToGoBack(examArray.getJSONObject(i).getString("optiontogoback"));
                                questionGetSet.setStatus(examArray.getJSONObject(i).getString("status"));
                                showcorrectanswers = examArray.getJSONObject(i).getString("showcorrectanswers");

                                if (examArray.getJSONObject(i).getString("optiontogoback").equals("0")) {
                                    isVisible = "no";
                                }
                            }

                            questions1.add(questionGetSet);

                        }
                        if (questions1.size() == 1) {
                            if (questions1.get(0).getStatus().equals("blocked") || questions1.get(0).getStatus().equals("deleted")) {
                                final Dialog dialog = new Dialog(Questions.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been " + questions1.get(0).getStatus());
                                Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(Questions.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();
                            } else {
                                questionAdapter = new QuestionAdapter(Questions.this, getSupportFragmentManager(), questions1);

                                if (isVisible.equals("no")) {
                                    previousButton.setVisibility(View.GONE);
                                    customQuestionViewPager.setVisibility(View.VISIBLE);
                                    customQuestionViewPager.setAdapter(questionAdapter);
                                    customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);
                                } else {
                                    questionsViewPager.setVisibility(View.VISIBLE);
                                    questionsViewPager.setAdapter(questionAdapter);
                                }


                                //count = new MyTimer(Duration, 500);
                                // count.start();
                                initialiseTabHost(b);
                                //setTabColor(mTabHost);
                                questionsViewPager.addOnPageChangeListener(Questions.this);
                                customQuestionViewPager.addOnPageChangeListener(Questions.this);

                                // imageViewAllTheBest.setVisibility(View.GONE);
                                linearLayoutExam.setVisibility(View.VISIBLE);
                                progressbarLoadQuesions.setVisibility(View.GONE);
                                if (examArray.length() == 0) {

                                } else {
                                    count = new MyTimer(Duration, 500);
                                    count.start();
                                }

                            }

                        } else {


                            questionAdapter = new QuestionAdapter(Questions.this, getSupportFragmentManager(), questions1);

                            if (isVisible.equals("no")) {
                                previousButton.setVisibility(View.GONE);
                                customQuestionViewPager.setVisibility(View.VISIBLE);

                                customQuestionViewPager.setAdapter(questionAdapter);
                                customQuestionViewPager.setAllowedSwipeDirection(SwipeDirection.right);
                            } else {
                                questionsViewPager.setVisibility(View.VISIBLE);
                                questionsViewPager.setAdapter(questionAdapter);
                            }


                            //count = new MyTimer(Duration, 500);
                            // count.start();
                            initialiseTabHost(b);
                            //setTabColor(mTabHost);
                            questionsViewPager.addOnPageChangeListener(Questions.this);
                            customQuestionViewPager.addOnPageChangeListener(Questions.this);

                            // imageViewAllTheBest.setVisibility(View.GONE);
                            linearLayoutExam.setVisibility(View.VISIBLE);
                            progressbarLoadQuesions.setVisibility(View.GONE);
                            count = new MyTimer(Duration, 500);
                            count.start();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // imageViewAllTheBest.setVisibility(View.GONE);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    /*NoQuestionsTextView.setVisibility(View.VISIBLE);
                   // MainLayout.setVisibility(View.GONE);
                   // imageViewAllTheBest.setVisibility(View.GONE);

                    questionsViewPager.setVisibility(View.GONE);
                    customQuestionViewPager.setVisibility(View.GONE);
                    linearLayoutExam.setVisibility(View.VISIBLE);
                    progressbarLoadQuesions.setVisibility(View.GONE);*/
                    //mTabHost.setVisibility(View.GONE);
                    if (error instanceof NoConnectionError) {
                        previousButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources()
                                        .getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getQuestions();
                                    }
                                });
                        snackbar.show();
                        linearLayoutExam.setVisibility(View.VISIBLE);
                        progressbarLoadQuesions.setVisibility(View.GONE);

                        error.printStackTrace();

                    } else {
                        previousButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, getApplicationContext().getResources()
                                .getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getQuestions();
                            }
                        });
                        snackbar.show();
                        linearLayoutExam.setVisibility(View.VISIBLE);
                        progressbarLoadQuesions.setVisibility(View.GONE);

                        error.printStackTrace();
                    }
                }
            });

            requestQueue.add(users);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initialiseTabHost(Bundle args) {
        try {
            mTabHost = (TabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup();

            TabInfo tabInfo = null;
            Intent intent = new Intent(this, SwipeAnswers.class);


            for (int i = 0; i < questions1.size(); i++) {
                Questions.AddTab(
                        this,
                        this.mTabHost,
                        this.mTabHost.newTabSpec(String.valueOf(i + 1)).setIndicator("" + (i + 1)).setContent(intent),
                        (tabInfo = new TabInfo(String.valueOf(i + 1), Questions.class, args, i)));

                mTabHost.getTabWidget().setStripEnabled(false);
                mTabHost.getTabWidget().getChildTabViewAt(i).setEnabled(false);
                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);

                /****OnClick Listener of the Quession no tab layout****/
                tabText.setWidth(73);
                tabText.setFocusable(true);
                tabText.setGravity(Gravity.CENTER);
                tabText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customQuestionViewPager.setCurrentItem(Integer.parseInt(((TextView) view).getText().toString().trim()) - 1);
                        questionsViewPager.setCurrentItem(Integer.parseInt(((TextView) view).getText().toString().trim()) - 1);
                    }
                });
                tabText.setTextColor(Color.parseColor("#000000"));
                tabText.setTextSize(20.0f);

                mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.current_selected_tab);
                this.mapTabInfo.put(tabInfo.tag, tabInfo);
            }
            int width = mTabHost.getTabWidget().getLayoutParams().width;
            int tabcount = mTabHost.getTabWidget().getTabCount();
            Log.v("width", "" + width);
            Log.v("tabcount", "" + tabcount);
            mTabHost.setOnTabChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTabColor(TabHost tabhost, String qnandans, ArrayList<QuestionGetSet> array1) {
        try {
            int pos = tabhost.getCurrentTab();
            if (qnandans.length() > 0) {
                if (String.valueOf(qnandans.charAt(0)).equals("~")) {
                    StringBuilder myName = new StringBuilder(qnandans);
                    myName.setCharAt(0, ' ');
                    qnandans = myName.toString().trim();
                    Log.i("questioncolor-->", qnandans);
                }
            }
            String arr4[];

            String[] arr2 = qnandans.split("~");
            for (int j = 0; j < arr2.length; j++) {
                arr4 = arr2[j].split("\\^");
                for (int k = 0; k < arr4.length; k++) {
                    for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {

                        if (i == pos) {
                            if (ColorValue[i].contains("GREEN")) {
                                if (mTabHost.getCurrentTab() == i) {
                                    mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectedtabcolor_new);
                                } else {
                                    mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectedtabcolor);
                                }
                            } else {
                                tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.current_selected_tab);
                                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                tabText.setTextSize(20.0f);
                            }
                        } else {
                            if (arr4[0].equals(array1.get(i).getQuestionid())) {

                                mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectedtabcolor);

                                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#FFFFFF"));
                                tabText.setTextSize(20.0f);
                                ColorValue[i] = "GREEN";
                            }
                            if (!arr4[0].equals(array1.get(i).getQuestionid())) {
                                if (ColorValue[i].contains("GREEN")) {

                                } else {
                                    tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.notansquestions_tab);
                                    TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                    tabText.setTextSize(20.0f);
                                    ColorValue[i] = "PINK";
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTabChanged(String tabId) {
        try {

            setTabColor(mTabHost, questionAndAnswers, questions1);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            View tabView = mTabHost.getTabWidget().getChildAt(position);
            HorizontalScrollView horizontalScrollView1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);


            if (tabView != null) {
                int x, y;
                final int width = horizontalScrollView1.getWidth();
                x = tabView.getLeft() - (width - tabView.getWidth()) / 2;/*tabView.getLeft();*/
                y = tabView.getRight();
                horizontalScrollView1.scrollTo(x, y);
            } else {
                horizontalScrollView1.scrollBy(positionOffsetPixels, 0);
            }

            if (questionsViewPager.getVisibility() == View.VISIBLE) {
                int pos = this.questionsViewPager.getCurrentItem();
                this.mTabHost.setCurrentTab(pos);
            } else if (customQuestionViewPager.getVisibility() == View.VISIBLE) {
                int pos = this.customQuestionViewPager.getCurrentItem();
                this.mTabHost.setCurrentTab(pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    public void submitExam() {
        try {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            progressDialog = ProgressDialog.show(Questions.this, null, "Please wait...", true);
            progressDialog.show();
            if (questionAndAnswers.length() > 0) {
                if (String.valueOf(questionAndAnswers.charAt(0)).equals("~")) {
                    StringBuilder myName = new StringBuilder(questionAndAnswers);
                    myName.setCharAt(0, ' ');
                    questionAndAnswers = myName.toString().trim();
                    Log.i("questionAndAnswers-->", questionAndAnswers);
                }
            }

            int NoOfCurrectAns = 0;
            int NoOfWrongAns = 0;
            int NoOfAtteptedQue = 0;
            int NoOfTotalQue = questions1.size();
            double totalPersentage = 0.0;
            try {

                JSONObject sendObject = new JSONObject();
                sendObject.put("action", "submitexam");
                sendObject.put("userid", CommonMethods.userid);
                sendObject.put("examlevel", examlevel);
                sendObject.put("examinationid", examinationId);
                sendObject.put("courseid", subCourseId);
                sendObject.put("questionAndAnswer", questionAndAnswers);
                Log.i("questionAndAnswers", questionAndAnswers);
                /*-----------------------------------------------------------------------------*/
                String[] mainQuestionStr = questionAndAnswers.split("~");
                for (int i = 0; i < mainQuestionStr.length; i++) {
                    String[] quesionStr = mainQuestionStr[i].split("\\^");
                    int questionId = Integer.parseInt(quesionStr[0]);
                    int selectedQuePos = Integer.parseInt(quesionStr[1]);
                    int marksOfDQue = Integer.parseInt(quesionStr[2]);

                    if (marksOfDQue == 1) {
                        NoOfCurrectAns++;
                    }
                    if (selectedQuePos != -1) {
                        NoOfAtteptedQue++;
                    }
                }
                NoOfWrongAns = (NoOfAtteptedQue - NoOfCurrectAns);

                /*DecimalFormat df=new DecimalFormat("0.00");
                String formate = df.format();*/
                totalPersentage = ((1.0 * NoOfCurrectAns) * 100.0) / (1.0 * NoOfTotalQue);
                Log.i("totalPersentage", "-->" + totalPersentage);
                /*------------------------------------------------------------------------------*/
                if (totalPersentage < 35.0) {
                    resultstatus = "Poor";

                } else if (totalPersentage >= 35.0 && totalPersentage <= 50.0) {
                    resultstatus = "Average";
                } else if (totalPersentage > 50.0 && totalPersentage <= 80.0) {
                    resultstatus = "Good";
                } else {
                    resultstatus = "Excellent";
                }

                totalattempted = NumberOfCorrectanswers + wronganswers;
                sendObject.put("noofquestions", String.valueOf(questions1.size()));
                sendObject.put("NumberOfCorrectanswers", String.valueOf(NoOfCurrectAns));
                sendObject.put("wronganswers", String.valueOf(NoOfWrongAns));
                sendObject.put("totalScore", String.valueOf(Math.ceil(totalPersentage)));
                sendObject.put("resultstatus", resultstatus);

                Log.i("request is", sendObject.toString());
                final int finalNoOfCurrectAns = NoOfCurrectAns;
                final int finalNoOfWrongAns = NoOfWrongAns;
                final double finalTotalPersentage = totalPersentage;
                RequestQueue requestQueue = Volley.newRequestQueue(Questions.this);
                CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("response of submit is", response.toString());
                        try {

                            String rankaobject = response.getString("Rank");
                            JSONArray rankArray = new JSONArray(rankaobject);
                            if (rankArray.getJSONObject(0).getString("status").equals("deleted") ||
                                    rankArray.getJSONObject(0).getString("status").equals("blocked")) {
                                final Dialog dialog = new Dialog(Questions.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been " + rankArray.getJSONObject(0).getString("status"));
                                Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(Questions.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();

                            } else {


                                rank = rankArray.getJSONObject(0).getString("rank");
                                outof = rankArray.getJSONObject(0).getString("outof");
                                dateofexam = rankArray.getJSONObject(0).getString("dateofexam");

                                Log.v("questionpage", questionAndAnswers);

                                Intent intent = new Intent(Questions.this, Result_Analysis.class);
                                Bundle bundleObject = new Bundle();
                                bundleObject.putSerializable("key", questions1);
                                intent.putExtras(bundleObject);
                                intent.putExtra("count", outOf);
                                intent.putExtra("rank", rank);
                                intent.putExtra("outof", outof);
                                intent.putExtra("dateofexam", dateofexam);


                                //intent.putExtra("question1",questions1);
                                intent.putExtra("duration", duration);
                                intent.putExtra("time_taken", String.valueOf(millisecs));
                                intent.putExtra("correctAns", String.valueOf(finalNoOfCurrectAns));
                                intent.putExtra("wrongAns", String.valueOf(finalNoOfWrongAns));
                                intent.putExtra("examlevel", examlevel);
                                intent.putExtra("examId", examinationId);
                                intent.putExtra("courseid", subCourseId);
                                intent.putExtra("showcorrectanswers", showcorrectanswers);
                                intent.putExtra("questionAndAns", questionAndAnswers);
                                // intent.putExtra("date",date);
                                intent.putExtra("examtitle", examTitle);
                                intent.putExtra("maxtimeusercantakeexam", maxtimeusercantakeexam);
                                //intent.putExtra("totalAttempted", String.valueOf(NoOfAtteptedQue));
                                //intent.putExtra("overallPerformance", resultstatus);
                                intent.putExtra("noOfQuestions", String.valueOf(questions1.size()));
                                intent.putExtra("score", String.valueOf((int) Math.ceil(finalTotalPersentage)));
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();


                    }
                });
                users
                        .setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(users);
            } catch (JSONException e) {
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        Log.v("position of tab", String.valueOf(position));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class TabInfo {
        int pos;
        private String tag;
        private Class<?> clss;
        private Bundle args;

        TabInfo(String tag, Class<?> clazz, Bundle args, int pos) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
            this.pos = pos;
        }
    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }


        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(70);
            v.setMinimumHeight(70);
            return v;
        }
    }

    @SuppressLint("ValidFragment")
    public static class SwipeAnswers extends Fragment {

        TextView numeratorTextView;
        TextView denominatorTextView;
        TextView textviewQuestionnum;


        int posnn = 0;

        public SwipeAnswers(int posnn) {
            this.posnn = posnn;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View swipeView = inflater.inflate(R.layout.swipe_questions_fragment, container, false);
            noOfQuestionsTextView = (TextView) swipeView.findViewById(R.id.noOfQuestionsTextView);
            TextView textViewQuestion = (TextView) swipeView.findViewById(R.id.textviewQuestion);
            numeratorTextView = (TextView) swipeView.findViewById(R.id.questionNoNumTextView);
            textviewQuestionnum = (TextView) swipeView.findViewById(R.id.textviewQuestionnum);
            denominatorTextView = (TextView) swipeView.findViewById(R.id.questionNoDenomTextView);

            try {
                final String questions = questions1.get(posnn).getQuestion();

                textViewQuestion.setText(questions);

                radioGroup = (RadioGroup) swipeView.findViewById(R.id.questionsGroup);
                radioGroup.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //customQuestionViewPager.requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                final RadioButton answerButton1 = (RadioButton) swipeView.findViewById(R.id.selectA);
                final RadioButton answerButton2 = (RadioButton) swipeView.findViewById(R.id.selectB);
                final RadioButton answerButton3 = (RadioButton) swipeView.findViewById(R.id.selectC);
                final RadioButton answerButton4 = (RadioButton) swipeView.findViewById(R.id.selectD);
                final RadioButton answerButton5 = (RadioButton) swipeView.findViewById(R.id.selectE);

                if (questions1.get(posnn).getNoofchoices().equals("1")) {
                    answerButton2.setVisibility(View.GONE);
                    answerButton3.setVisibility(View.GONE);
                    answerButton4.setVisibility(View.GONE);
                    answerButton5.setVisibility(View.GONE);

                } else if (questions1.get(posnn).getNoofchoices().equals("2")) {
                    answerButton3.setVisibility(View.GONE);
                    answerButton4.setVisibility(View.GONE);
                    answerButton5.setVisibility(View.GONE);


                } else if (questions1.get(posnn).getNoofchoices().equals("3")) {
                    answerButton4.setVisibility(View.GONE);
                    answerButton5.setVisibility(View.GONE);
                } else if (questions1.get(posnn).getNoofchoices().equals("4")) {
                    answerButton5.setVisibility(View.GONE);

                }


                answer1 = questions1.get(posnn).getAnswer1();
                answer2 = questions1.get(posnn).getAnswer2();
                answer3 = questions1.get(posnn).getAnswer3();
                answer4 = questions1.get(posnn).getAnswer4();
                answer5 = questions1.get(posnn).getAnswer5();
                String[] answers = {answer1, answer2, answer3, answer4, answer5};
                for (int i = 0; i < radioGroup.getChildCount(); i++) {

                    if (answers[i].equals("")) {
                        ((RadioButton) radioGroup.getChildAt(i)).setVisibility(View.GONE);
                    } else {
                        ((RadioButton) radioGroup.getChildAt(i)).setText(answers[i]);
                    }


                }
                int posNew = posnn + 1;
                numeratorTextView.setText("" + posNew);
                noOfQuestionsTextView.setText(posNew + " of " + questions1.size());
                textviewQuestionnum.setText("" + posNew + ". ");
                denominatorTextView.setText("/" + questions1.size());


                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        try {


                            int radioButtonId = group.getCheckedRadioButtonId();


                            radioButton = (RadioButton) group.findViewById(radioButtonId);

                            posn = group.indexOfChild(radioButton);
                            newposn = posn + 1;

                            String beforeReplacedString = "";


                            String questionId = questions1.get(posnn).getQuestionid();


                            Log.v("Before: ", questionAndAnswers);
                            String tempQuestionAndAsw = "";

                            if (newposn == Integer.parseInt(questions1.get(posnn).getCorrectAnswer())) {
                                marks = 1;
                                tempQuestionAndAsw = "~" + questionId + carrot + newposn + carrot + marks;


                            } else {
                                marks = 0;
                                tempQuestionAndAsw = "~" + questionId + carrot + newposn + carrot + marks;


                            }
                            if (questionAndAnswers.length() > 0) {
                                subStringValue = questionAndAnswers.charAt(0);

                            }

                            if (String.valueOf(subStringValue).equals("~")) {
                                StringBuilder myName = new StringBuilder(questionAndAnswers);
                                myName.setCharAt(0, ' ');
                                questionAndAnswers = myName.toString().trim();
                                Log.i("questionAndAnswers-->", questionAndAnswers);
                            }

                            if (questionAndAnswers.length() > 0) {
                                Log.i("TEMP_STR", tempQuestionAndAsw);
                                String domy = tempQuestionAndAsw.substring(1, tempQuestionAndAsw.length());
                                Log.i("domy", domy);
                                String[] questionId1 = domy.split("\\^");
                                Log.i("ID", questionId1[0]);
                                String strQuestion = questionId1[0];

                                if (questionAndAnswers.contains(strQuestion)) {
                                    String replaceString1 = tempQuestionAndAsw.substring(1, tempQuestionAndAsw.length());
                                    String[] splitValue1 = questionAndAnswers.split("\\~");
                                    for (int k = 0; k < splitValue1.length; k++) {
                                        if (splitValue1[k].contains(strQuestion)) {
                                            Log.i("REPLACE", replaceString1);
                                            questionAndAnswers = questionAndAnswers.replace(splitValue1[k], replaceString1);
                                            Log.i("questionAndAns----->", questionAndAnswers);
                                            return;
                                        }

                                    }

                                } else {
                                    questionAndAnswers = questionAndAnswers + tempQuestionAndAsw;
                                }
                            } else {
                                questionAndAnswers = tempQuestionAndAsw;
                            }


                            Log.v("After: ", questionAndAnswers);


                            totalScore = ((1.0 * NumberOfCorrectanswers) / (1.0 * (questions1.size()))) * 100.0;
                            if (totalScore < 35.0) {
                                resultstatus = "Poor";

                            } else if (totalScore >= 35.0 && totalScore <= 50.0) {
                                resultstatus = "Average";
                            } else if (totalScore > 50.0 && totalScore <= 80.0) {
                                resultstatus = "Good";
                            } else {
                                resultstatus = "Excellent";
                            }
                            if (radioButton.isChecked()) {

                                mTabHost.getTabWidget().getChildAt(posnn).setBackgroundResource(R.drawable.selectedtabcolor_new);
                                TextView tabText = (TextView) mTabHost.getTabWidget()
                                        .getChildAt(posnn).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#FFFFFF"));
                                tabText.setTextSize(20.0f);

                                ColorValue[posnn].contains("GREEN");
                            }

                            if (group.getCheckedRadioButtonId() == 0) {
                                Toast.makeText(getActivity().getApplicationContext(), "NOT SELECTED", Toast.LENGTH_LONG).show();
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

            return swipeView;

        }


    }

    public class QuestionAdapter extends FragmentStatePagerAdapter {
        ArrayList<QuestionGetSet> questions;
        Context context;

        public QuestionAdapter(Context context, FragmentManager fm, ArrayList<QuestionGetSet> questions) {
            super(fm);
            this.questions = questions;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            // Toast.makeText(Questions.this,""+position,Toast.LENGTH_SHORT).show();

            return new SwipeAnswers(position);
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int newPosition = position + 1;
            return "" + newPosition;
        }


    }

    public class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            try {
                millisecs = millisUntilFinished;
                String hour = "";
                String minute = "";
                String seconds = "";
                if (String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))).length() >= 2) {
                    minute = "%d";
                } else {
                    minute = "0%d";

                }
                if (String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)).length() >= 2) {
                    hour = "%d";
                } else {
                    hour = "0%d";
                }
                if (String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))).length() >= 2) {
                    seconds = "%d";
                } else {
                    seconds = "0%d";
                }
                timeTextView.setText("" + String.format(hour + ":" + minute + ":" + seconds,

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onFinish() {
           /* for (int i = 0; i < questions1.size(); i++) {
                if (questionAndAnswers.contains(questions1.get(i).getQuestionid())) {

                } else {
                    questionAndAnswers = questionAndAnswers + "~" + questions1.get(i).getQuestionid() + carrot + "-1" + carrot + 0;
                }
            }*/
            try {
                Log.v("onclick of yes", questionAndAnswers);
                String questionIds = "";
                for (int i = 0; i < questions1.size(); i++) {

                    questionIds = questionIds + questions1.get(i).getQuestionid() + ",";

                }
                String questionIds2 = questionIds.substring(0, questionIds.length() - 1);


                String answeredIds = "";
                String ansQnIds = "";
                if (questionAndAnswers.length() > 0) {
                    if (String.valueOf(questionAndAnswers.charAt(0)).equals("~")) {
                        StringBuilder myName = new StringBuilder(questionAndAnswers);
                        myName.setCharAt(0, ' ');
                        questionAndAnswers = myName.toString().trim();

                    }
                    if (questionAndAnswers.contains("~")) {
                        String[] arr1 = questionAndAnswers.split("~");
                        for (int j = 0; j < arr1.length; j++) {
                            String arra[] = arr1[j].split("\\^");
                            answeredIds = answeredIds + arra[0] + ",";

                        }
                    } else {
                        String[] single_qn = questionAndAnswers.split("\\^");
                        answeredIds = answeredIds + single_qn[0] + ",";
                    }
                    ansQnIds = answeredIds.substring(0, answeredIds.length() - 1);
                } else {
                    ansQnIds = "";
                }
                Log.v("questionIds2", questionIds2);
                Log.v(" ansQnIds", ansQnIds);
                if (questionIds2.length() == ansQnIds.length()) {
                    Log.v("yes it is equal", "yes it is equal");
                } else {
                    String[] splitIds = questionIds.split(",");
                    if (ansQnIds.length() > 0) {

                        for (int k = 0; k < splitIds.length; k++) {
                            if (answeredIds.length() == 1) {
                                if (answeredIds.equals(splitIds[k])) {
                                    splitIds[k] = "";
                                }

                            } else {
                                String[] splitAnsIds = answeredIds.split(",");
                                for (int j = 0; j < splitAnsIds.length; j++) {
                                    if (splitAnsIds[j].equals(splitIds[k])) {
                                        splitIds[k] = "";
                                    }

                                }
                            }

                        }
                    }
                    ArrayList<String> finalIds = new ArrayList<>();
                    for (int m = 0; m < splitIds.length; m++) {

                        if (!(splitIds[m].equals(""))) {
                            finalIds.add(splitIds[m]);
                        }

                    }
                    for (int l = 0; l < finalIds.size(); l++) {
                        questionAndAnswers = questionAndAnswers + "~" + finalIds.get(l) + carrot + "-1" + carrot + "0";
                    }


                }
                Log.v("questionAndAnswers", questionAndAnswers);

                submitExam();
                final Dialog dialog = new Dialog(Questions.this);
                dialog.setContentView(R.layout.popup_timeout);
                try {
                    dialog.show();
                } catch (Exception e) {

                }
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("Time UP!!");
                Button btnDismiss = (Button) dialog.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        submitExam();
                        dialog.dismiss();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class AlertClass extends Dialog implements View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView yes, no, txt_dia;

        public AlertClass(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setCanceledOnTouchOutside(false);
                setContentView(R.layout.dialog);
                txt_dia = (TextView) findViewById(R.id.txt_dia);
                yes = (TextView) findViewById(R.id.btn_yes);
                no = (TextView) findViewById(R.id.btn_no);
                yes.setOnClickListener(this);
                no.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.btn_yes:
                        count.cancel();

                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        isdone=true;
                        Log.v("onclick of yes", questionAndAnswers);
                        String questionIds = "";
                        for (int i = 0; i < questions1.size(); i++) {

                            questionIds = questionIds + questions1.get(i).getQuestionid() + ",";

                        }
                        String questionIds2 = questionIds.substring(0, questionIds.length() - 1);


                        String answeredIds = "";
                        String ansQnIds = "";
                        if (questionAndAnswers.length() > 0) {
                            if (String.valueOf(questionAndAnswers.charAt(0)).equals("~")) {
                                StringBuilder myName = new StringBuilder(questionAndAnswers);
                                myName.setCharAt(0, ' ');
                                questionAndAnswers = myName.toString().trim();

                            }
                            if (questionAndAnswers.contains("~")) {
                                String[] arr1 = questionAndAnswers.split("~");
                                for (int j = 0; j < arr1.length; j++) {
                                    String arra[] = arr1[j].split("\\^");
                                    answeredIds = answeredIds + arra[0] + ",";

                                }
                            } else {
                                String[] single_qn = questionAndAnswers.split("\\^");
                                answeredIds = answeredIds + single_qn[0] + ",";
                            }
                            ansQnIds = answeredIds.substring(0, answeredIds.length() - 1);
                        } else {
                            ansQnIds = "";
                        }
                        Log.v("yes questionIds2", questionIds2);
                        Log.v("yes ansQnIds", ansQnIds);
                        if (questionIds2.length() == ansQnIds.length()) {
                            Log.v("yes it is equal", "yes it is equal");
                        } else {
                            String[] splitIds = questionIds.split(",");
                            if (ansQnIds.length() > 0) {

                                for (int k = 0; k < splitIds.length; k++) {
                                    if (answeredIds.length() == 1) {
                                        if (answeredIds.equals(splitIds[k])) {
                                            splitIds[k] = "";
                                        }

                                    } else {
                                        String[] splitAnsIds = answeredIds.split(",");
                                        for (int j = 0; j < splitAnsIds.length; j++) {
                                            if (splitAnsIds[j].equals(splitIds[k])) {
                                                splitIds[k] = "";
                                            }

                                        }
                                    }

                                }
                            }
                            ArrayList<String> finalIds = new ArrayList<>();
                            for (int m = 0; m < splitIds.length; m++) {

                                if (!(splitIds[m].equals(""))) {
                                    finalIds.add(splitIds[m]);
                                }

                            }
                            for (int l = 0; l < finalIds.size(); l++) {
                                questionAndAnswers = questionAndAnswers + "~" + finalIds.get(l) + carrot + "-1" + carrot + "0";
                            }


                        }
                        Log.v("questionAndAnswers", questionAndAnswers);

                        submitExam();

                        break;
                    case R.id.btn_no:


                        break;
                    default:
                        break;
                }
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class OnTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }


}
