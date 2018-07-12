package eniversity.com;


import android.annotation.SuppressLint;

import com.eniversity.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import Commmon.Methods.SlidinTabLayoutNew;
import Commmon.Methods.SlidingTabLayout;
import fragments.AnalysisFrag;
import fragments.ResultFrag;
import get.set.QuestionGetSet;
import login.page.LoginMainPage;


/**
 * Created by soumyay on 7/20/2016.
 */
public class Result_Analysis extends FragmentActivity {

 String rank="";
     String outof="";
     String dateofexam="";
     String title="";
     String level="";
     String count="";
   // String date="";
    String correctAnswers="";
     String wrongtAnswers="";
    String noOfQuestions="";
     String questionAndAns="";
     ViewPager questionsViewPager;
    LinearLayout linearLayout;
    LinearLayout timerLayout;
    String timeRemaining="";
     String duration="";
     String maxXount="";
     String[] ColorValue = new String[100];
     TextView rankTextView;
     TextView outOfTextView;




     Double correctPercent=0.00;
     Double wrongPercent=0.00;
     Double unAttemptedPercent=0.00;
     int umattemptedNum=0;
     int num1=0;
     ImageView previousButton;
     ImageView nextButton;


    SlidinTabLayoutNew resulttab;
    SlidingTabLayout analysistab;
    private ViewPager pagerResult;
     String examId="";
     String courseid="";


    String showcorrectanswers="";
     ArrayList<QuestionGetSet> classObject;
    TextView textviewQuestionnum;
    //analysis frag properties

    CheckBox textOption1;
    CheckBox textOption2;
    CheckBox textOption3;
    CheckBox textOption4;
    CheckBox textOption5;
    TextView textviewQuestion;
    RelativeLayout resultToolBar;
    LinearLayout analysistoolBar;
    boolean isVisible=false;
    boolean loadfirstfragment=true;
    TextView textViewHeadingAnalysis;
    TextView textViewTime;
    ImageView imageBack;
    ImageView imageViewBackIcon_timer;
    TextView textViewHeading;
    TabHost tabHost;
    TextView noOfQuestionsTextView;
    TextView textOption1TextView;
    TextView textOption2TextView;
    TextView textOption3TextView;
    TextView textOption4TextView;
    TextView textOption5TextView;









    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.result_analysis);
            Bundle bundleObject = getIntent().getExtras();
            classObject = (ArrayList<QuestionGetSet>) bundleObject.getSerializable("key");
            timeRemaining = getIntent().getExtras().getString("time_taken");
            duration = getIntent().getExtras().getString("duration");
            maxXount = getIntent().getExtras().getString("maxtimeusercantakeexam");
            Log.v("maxXount in Result", maxXount);
            examId = getIntent().getExtras().getString("examId");
            courseid = getIntent().getExtras().getString("courseid");
            String durationArr[] = duration.split(":");
            String hour = durationArr[0];
            int min = Integer.parseInt(durationArr[1]) + (Integer.parseInt(hour) * 60);
            long totalTime = TimeUnit.MINUTES.toMillis(min);
            long timeTaken = totalTime - Long.parseLong(timeRemaining);
            resultToolBar = (RelativeLayout) findViewById(R.id.resultToolBar);
            analysistoolBar = (LinearLayout) findViewById(R.id.analysistoolBar);
            textViewHeadingAnalysis = (TextView) findViewById(R.id.textViewHeadingAnalysis);
            textViewHeading = (TextView) findViewById(R.id.textViewHeading);
            textViewTime = (TextView) findViewById(R.id.textViewTime);
            imageBack = (ImageView) findViewById(R.id.imageBack);
            imageViewBackIcon_timer = (ImageView) findViewById(R.id.imageViewBackIcon_timer);


            //long minutes=TimeUnit.MILLISECONDS.toMinutes(timeTaken);

            Log.v("totalTime", "" + totalTime);
            Log.v("millis", timeRemaining);
            Log.v("timetaken", "" + timeTaken);
            title = getIntent().getExtras().getString("examtitle");
            level = getIntent().getExtras().getString("examlevel");
            //date=getIntent().getExtras().getString("date");
            rank = getIntent().getExtras().getString("rank");
            outof = getIntent().getExtras().getString("outof");
            dateofexam = getIntent().getExtras().getString("dateofexam");
            correctAnswers = getIntent().getExtras().getString("correctAns");
            wrongtAnswers = getIntent().getExtras().getString("wrongAns");
            noOfQuestions = getIntent().getExtras().getString("noOfQuestions");
            count = getIntent().getExtras().getString("count");
            showcorrectanswers = getIntent().getExtras().getString("showcorrectanswers");
            questionAndAns = getIntent().getExtras().getString("questionAndAns");

            textViewHeadingAnalysis.setText(title);
            textViewHeading.setText("Exam Results ");
            String hour1 = "";
            String minute = "";
            String seconds = "";
            if (String.valueOf(TimeUnit.MILLISECONDS.toMinutes(timeTaken) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeTaken))).length() >= 2) {
                minute = "%d";
            } else {
                minute = "0%d";

            }
            if (String.valueOf(TimeUnit.MILLISECONDS.toHours(timeTaken)).length() >= 2) {
                hour1 = "%d";
            } else {
                hour1 = "0%d";
            }
            if (String.valueOf(TimeUnit.MILLISECONDS.toSeconds(timeTaken) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))).length() >= 2) {
                seconds = "%d";
            } else {
                seconds = "0%d";
            }
            textViewTime.setText("" + String.format(hour1 + ":" + minute + ":" + seconds,

                    TimeUnit.MILLISECONDS.toHours(timeTaken),
                    TimeUnit.MILLISECONDS.toMinutes(timeTaken) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeTaken)),
                    TimeUnit.MILLISECONDS.toSeconds(timeTaken) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))));


            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Result_Analysis.this, LoginMainPage.class);
                    startActivity(intent);

                }
            });
            imageViewBackIcon_timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Result_Analysis.this, LoginMainPage.class);
                    startActivity(intent);
                }
            });

            Log.v("questionAndAnswers in result page", questionAndAns);

            for (int i = 0; i < classObject.size(); i++) {
                Log.v("questionid", classObject.get(i).getQuestionid());

            }
            // Toast.makeText(Result_Analysis.this,"date"+date+" count"+count,Toast.LENGTH_SHORT).show();
            //Log.v( "count in resultpage",count);
            //Log.v("date",date);

            correctPercent = (Double.parseDouble(correctAnswers) / (Double.parseDouble(noOfQuestions))) * 100.00;
            wrongPercent = (Double.parseDouble(wrongtAnswers) / (Double.parseDouble(noOfQuestions))) * 100.00;
            umattemptedNum = Integer.parseInt(noOfQuestions) - (Integer.parseInt(correctAnswers) + Integer.parseInt(wrongtAnswers));
            unAttemptedPercent = (Double.parseDouble(String.valueOf(umattemptedNum)) / (Double.parseDouble(noOfQuestions))) * 100.00;
            pagerResult = (ViewPager) findViewById(R.id.pagerResult);


            resulttab = (SlidinTabLayoutNew) findViewById(R.id.resulttab);

            resulttab.setDistributeEvenly(true);
            resulttab.setCustomTabColorizer(new SlidinTabLayoutNew.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    if (position == 0) {
                        analysistoolBar.setVisibility(View.GONE);
                        resultToolBar.setVisibility(View.VISIBLE);
                    } else if (position == 1) {
                        resultToolBar.setVisibility(View.GONE);
                        analysistoolBar.setVisibility(View.VISIBLE);

                    }
                    return Color.parseColor("#45B97C");
                }
            });
            resulttab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try {
                        //Fragment curreFragment=getSupportFragmentManaarg1ger().findFragmentById(R.id.resulttab);

                        //Toast.makeText(Result_Analysis.this, "postion is:" + position + "arg1" + positionOffset + "arg2" + positionOffsetPixels, Toast.LENGTH_SHORT).show();
                        if (position == 0) {
                            analysistoolBar.setVisibility(View.GONE);
                            resultToolBar.setVisibility(View.VISIBLE);
                        } else if (position == 1) {
                            resultToolBar.setVisibility(View.GONE);
                            analysistoolBar.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            pagerResult.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {


                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //showcorrectanswers="true";

            final ResultAdapter resultAdapter = new ResultAdapter(getSupportFragmentManager(), showcorrectanswers);
            pagerResult.setAdapter(resultAdapter);
            // pagerResult.setOffscreenPageLimit(2);


            resulttab.setViewPager(pagerResult);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public class  ResultAdapter extends FragmentPagerAdapter{

        String TITLES[]={"RESULTS","ANALYSIS"};
        String TITLES2[]={"RESULTS"};
        String showCorrectAns="";

        public ResultAdapter(FragmentManager fm,String showCorrectAns) {
            super(fm);
            this.showCorrectAns=showCorrectAns;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (showCorrectAns.equals("1")) {
                return TITLES[position];
            }
            else{
                return TITLES2[position];
            }

        }

        @Override
        public Fragment getItem(int position) {

            if(position==0){

                    ArrayList<String> resultvalues = new ArrayList<>();
                try {
                    resultvalues.add(rank);
                    resultvalues.add(outof);
                    resultvalues.add(title);
                    resultvalues.add(correctAnswers);
                    resultvalues.add(wrongtAnswers);
                    resultvalues.add(count);
                    resultvalues.add(dateofexam);
                    resultvalues.add(maxXount);
                    resultvalues.add(courseid);
                    resultvalues.add(examId);
                    resultvalues.add(level);
                    resultvalues.add(duration);
                    resultvalues.add(noOfQuestions);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                return  new ResultFrag(Result_Analysis.this,resultvalues);
            }
            else{
                return new AnalysisFrag(Result_Analysis.this,questionAndAns,classObject);
            }
        }

        @Override
        public int getCount() {
            if (showCorrectAns.equals("1")) {
                return TITLES.length;
            }
            else{
                return TITLES2.length;
            }

        }
    }
/*    @SuppressLint("ValidFragment")

    public static class ResultFrag extends Fragment{
        TextView titleTextView;

        TextView examLevelTextView;
        TextView numOfAttemptsTextView;
        TextView lastattemptedOnTextView;
        TextView CorrectPerTextView;
        TextView correctNumTextView;
        TextView wrongNumTextView;
        TextView UnattemptedNumTextView;
        Button takeExamAgainButton;
        ImageView topperLogo;
        ImageView topperLogo2;

        ImageView imageViewBackIcon;
        ImageView imageViewSearchIcon;
        CategorySeries distributionSeries;
        DefaultRenderer defaultRenderer;
        private GraphicalView mChartView;


        private ImageView imageBack;
        private TextView textViewHeading;
        private LinearLayout analysistoolBar;




        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView= inflater.inflate(R.layout.exam_layout,null);
            //analysistoolBar= (LinearLayout) getActivity().findViewById(R.id.analysistoolBar);
            //analysistoolBar.setVisibility(View.GONE);

            titleTextView= (TextView) rootView.findViewById(R.id.titleTextView);
            examLevelTextView= (TextView) rootView.findViewById(R.id.examLevelTextView);
            numOfAttemptsTextView= (TextView) rootView.findViewById(R.id.numOfAttemptsTextView);
            lastattemptedOnTextView= (TextView) rootView.findViewById(R.id.lastattemptedOnTextView);
            CorrectPerTextView= (TextView) rootView.findViewById(R.id.CorrectPerTextView);
            correctNumTextView= (TextView) rootView.findViewById(R.id.correctNumTextView);
            wrongNumTextView= (TextView) rootView.findViewById(R.id.wrongNumTextView);
            UnattemptedNumTextView= (TextView) rootView.findViewById(R.id.UnattemptedNumTextView);
            takeExamAgainButton= (Button) rootView.findViewById(R.id.takeExamAgainButton);
            rankTextView= (TextView) rootView.findViewById(R.id.rankTextView);
            topperLogo= (ImageView) rootView.findViewById(R.id.topperLogo);
            topperLogo2= (ImageView) rootView.findViewById(R.id.topperLogo2);
            outOfTextView= (TextView) rootView.findViewById(R.id.outOfTextView);
            if(Integer.parseInt(rank)>10){
                topperLogo2.setVisibility(View.VISIBLE);
                topperLogo.setVisibility(View.GONE);
            }
            if(Integer.parseInt(rank)==0){
                rankTextView.setText("NA");
                topperLogo2.setVisibility(View.GONE);
                topperLogo.setVisibility(View.GONE);
                outOfTextView.setText("out of "+outof);
            }
            else {
                rankTextView.setText(rank);
                outOfTextView.setText("out of "+outof);
            }

            titleTextView.setText(title);
            correctNumTextView.setText(correctAnswers);
            wrongNumTextView.setText(wrongtAnswers);
            UnattemptedNumTextView.setText(String.valueOf(umattemptedNum));
            numOfAttemptsTextView.setText(count);
            CorrectPerTextView.setText(String.valueOf((int)Math.round(correctPercent))+"%");

            if(dateofexam.equals("")){
                lastattemptedOnTextView.setText("NA");
            }
            else {
                String output = dateofexam.substring(0, 10);
                DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
                Date date1 = null;
                try {
                    date1 = originalFormat.parse(output);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String formattedDate = targetFormat.format(date1);

                lastattemptedOnTextView.setText(formattedDate);
            }

    if (Integer.parseInt(count) >= Integer.parseInt(maxXount)) {
        takeExamAgainButton.setVisibility(View.GONE);
    }
    else {
        num1 = Integer.parseInt(count) + 1;
        takeExamAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Questions.class);
                intent.putExtra("examtitle", title);
                intent.putExtra("count", String.valueOf(num1));
                intent.putExtra("maxtimeusercantakeexam", maxXount);
                //Toast.makeText(context,childGetSetArrayList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                Log.v("count", count);
                intent.putExtra("examlevel", level);
                intent.putExtra("courseid", courseid);
                //intent.putExtra("date", date);
                intent.putExtra("examinationid", examId);
                intent.putExtra("duration", duration);
                startActivity(intent);
               getActivity().finish();
            }
        });
    }






            String[] code = new String[] {
                    "Correct", "Wrong", "UnAttempted"
            };
            // Toast.makeText(SubDetails.this,""+(int)Math.round(correctPercent),Toast.LENGTH_SHORT).show();
            //Toast.makeText(SubDetails.this,""+(int)Math.round(wrongPercent),Toast.LENGTH_SHORT).show();
            //Toast.makeText(SubDetails.this,""+(int)Math.round(unAttemptedPercent),Toast.LENGTH_SHORT).show();
            // Pie Chart Section Value
            int[] distribution = { (int)Math.round(correctPercent), (int)Math.round(wrongPercent), (int)Math.round(unAttemptedPercent)} ;

            // Color of each Pie Chart Sections
            int[] colors = { R.color.Green, R.color.wrongColor, R.color.unattemptedColor};

            // Instantiating CategorySeries to plot Pie Chart
            distributionSeries= new CategorySeries(" Android version distribution as on October 1, 2012");
            for(int i=0 ;i < distribution.length;i++){
                // Adding a slice with its values and name to the Pie Chart

                if(distribution[i]==0){

                }
                else {
                    distributionSeries.add(code[i], distribution[i]);

                }
            }

            // Instantiating a renderer for the Pie Chart
            defaultRenderer = new DefaultRenderer();
            for(int i = 0 ;i<distribution.length;i++){
                if(distribution[i]==0){

                }
                else {
                    SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                    seriesRenderer.setColor(getActivity().getApplicationContext().getResources().getColor(colors[i]));
                    seriesRenderer.setDisplayBoundingPoints(false);
                    // Adding a renderer for a slice
                    defaultRenderer.addSeriesRenderer(seriesRenderer);
                    // defaultRenderer.setChartTitle("");
                    defaultRenderer.setShowLabels(false);
                    defaultRenderer.setPanEnabled(false);
                    defaultRenderer.setZoomButtonsVisible(false);
                    defaultRenderer.setZoomEnabled(false);
                    defaultRenderer.setScale(1.3f);
                    //.setDisplayValues(true);
                    //defaultRenderer.setLabelsTextSize(15.00f);
                    //defaultRenderer.setLegendTextSize(15.00f);

                    defaultRenderer.setShowLegend(false);
                    //defaultRenderer.setLegendHeight(50);

                }




            }
            if (mChartView == null) {
                LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.linear1);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500,500);
                //params.leftMargin=5;

                params.height=500;
                params.width=800;

                params.gravity= Gravity.CENTER;
                //layout.setLayoutParams(params);
                mChartView = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
                layout.addView(mChartView,params);

            } else {
                mChartView.repaint();
            }

            return rootView;
        }



    }*/
  //  @SuppressLint("ValidFragment")

 /*   public class AnalysisFrag extends Fragment implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{
        private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();

        TabHost mTabHost;
        String [] QandAns;

        private TextView textViewHeadingAnalysis;
        private ImageView imageViewBackIcon_timer;
        private TextView textViewTime;
        private ImageView logoutImageView;
        private RelativeLayout resultToolBar;
        View includeAnalysis;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View analysisView = inflater.inflate(R.layout.analysis_layout, null);
            mTabHost = (TabHost) analysisView.findViewById(android.R.id.tabhost);
            previousButton= (ImageView) analysisView.findViewById(R.id.previousButton);
            nextButton= (ImageView) analysisView.findViewById(R.id.nextButton);

            mTabHost.setup();
            QandAns = questionAndAns.split("~");
            Log.v("questionAndAns",questionAndAns);







//Toast.makeText(Result_Analysis.this,"inside analysis",Toast.LENGTH_SHORT).show();


            questionsViewPager = (ViewPager) analysisView.findViewById(R.id.pagerAnalysis);
            questionsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == 0) {
                        previousButton.setVisibility(View.GONE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }
                    if (position == (classObject.size() - 1)) {
                        nextButton.setVisibility(View.GONE);
                    } else {
                        nextButton.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //analysistab = (SlidingTabLayout) analysisView.findViewById(R.id.analysistab);


            //Toast.makeText(Result_Analysis.this,"COUNT IS"+count,Toast.LENGTH_SHORT).show();

            AnalysusResAdapter analysusResAdapter = new AnalysusResAdapter(getActivity().getApplicationContext(), getActivity().getSupportFragmentManager(), classObject);
            questionsViewPager.setAdapter(analysusResAdapter);
           // analysistab.setViewPager(questionsViewPager);
            //analysistab.setDistributeEvenly(true);




            //mTabHost = (TabHost)analysisView.findViewById(android.R.id.tabhost);

            initialiseTabHost(savedInstanceState);


            for (int j = 0; j < classObject.size(); j++) {
                for (int i = 0; i < QandAns.length; i++) {
                    String arra[] = QandAns[i].split("\\^");
                    if (arra[0].equals(classObject.get(j).getQuestionid())) {

                        if (arra[1].equals(classObject.get(j).getCorrectAnswer())) {
                            mTabHost.getTabWidget().getChildAt(j)
                                    .setBackgroundResource(R.color.Green);
                            TextView tabText = (TextView) mTabHost.getTabWidget()
                                    .getChildAt(j).findViewById(android.R.id.title);
                            tabText.setTextColor(Color.parseColor("#FFFFFF"));
                            tabText.setTextSize(20.0f);
                        } else if (!(arra[1].equals(classObject.get(j).getCorrectAnswer()))) {
                            if ((arra[1].equals("-1"))) {
                                mTabHost.getTabWidget().getChildAt(j)
                                        .setBackgroundResource(R.color.unattemptedColor);
                                TextView tabText = (TextView) mTabHost.getTabWidget()
                                        .getChildAt(j).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#000000"));
                                tabText.setTextSize(20.0f);
                            } else {
                                mTabHost.getTabWidget().getChildAt(j)
                                        .setBackgroundResource(R.color.wrongColor
                                        );
                                TextView tabText = (TextView) mTabHost.getTabWidget()
                                        .getChildAt(j).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#FFFFFF"));
                                tabText.setTextSize(20.0f);
                            }
                        }

                    }

                }
            }
            questionsViewPager.addOnPageChangeListener(this);
            for (int i = 0; i < ColorValue.length; i++) {
                ColorValue[i] = "YELLOW";
            }
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() - 1, true);
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() + 1, true);
                }
            });

            return analysisView;

        }
        private  void AddTab(Result_Analysis context, TabHost tabHost,
                                   TabHost.TabSpec tabSpec, TabInfo tabInfo) {
            tabSpec.setContent(context.new TabFactory(context));
            tabHost.addTab(tabSpec);
        }
        private void initialiseTabHost(Bundle args) {


            TabInfo tabInfo = null;



            for (int i = 0; i < QandAns.length; i++) {

                new AnalysisFrag().AddTab(Result_Analysis.this, mTabHost,
                        mTabHost.newTabSpec(String.valueOf(i+1)).setIndicator(String.valueOf(i+1)),
                        (tabInfo = new TabInfo(String.valueOf(i+1), AnswersFrag.class, args,i)));
                mTabHost.getTabWidget().setStripEnabled(false);
                //mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tabborder);
                mapTabInfo.put(tabInfo.tag, tabInfo);

            }
            mTabHost.setOnTabChangedListener(this);

        }




        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            View tabView = mTabHost.getTabWidget().getChildAt(position);
            HorizontalScrollView horizontalScrollView1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
            if (tabView != null) {
                final int width = horizontalScrollView1.getWidth();
                final int scrollPos = tabView.getLeft() - (width - tabView.getWidth()) / 2;
                horizontalScrollView1.scrollTo(scrollPos, 0);
            } else {
                horizontalScrollView1.scrollBy(positionOffsetPixels, 0);
            }

                int pos = questionsViewPager.getCurrentItem();
                this.mTabHost.setCurrentTab(pos);


        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onTabChanged(String tabId) {


        }



    }*/
    private  class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        int pos;

        TabInfo(String tag, Class<?> clazz, Bundle args,int pos) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
            this.pos=pos;
        }

    }
    public class TabFactory implements TabHost.TabContentFactory {

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
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

/*    @SuppressLint("ValidFragment")

    public class AnswersFrag extends Fragment{

        String queAndAns[];
        String splitby_caret[];
        int pos=0;
        int newPos=0;
        ArrayList<QuestionGetSet> questionGetSetArrayList;
        private SlidingTabLayout  view;

        public AnswersFrag(int pos){
            this.pos=pos;

        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View analysisResultView=inflater.inflate(R.layout.analysis_fragment_layout, null);
            textviewQuestion= (TextView) analysisResultView.findViewById(R.id.textviewQuestion);
            noOfQuestionsTextView= (TextView) analysisResultView.findViewById(R.id.noOfQuestionsTextView);
            textviewQuestionnum= (TextView) analysisResultView.findViewById(R.id.textviewQuestionnum);
            int newpos=pos+1;
            textviewQuestionnum.setText(String.valueOf(newpos)+".");
            noOfQuestionsTextView.setText(newpos+" of  "+classObject.size());



            textOption1= (CheckBox) analysisResultView.findViewById(R.id.textOption1);
            textOption2= (CheckBox) analysisResultView.findViewById(R.id.textOption2);
            textOption3= (CheckBox) analysisResultView.findViewById(R.id.textOption3);
            textOption4= (CheckBox) analysisResultView.findViewById(R.id.textOption4);
            textOption5= (CheckBox) analysisResultView.findViewById(R.id.textOption5);
            textOption1TextView= (TextView) analysisResultView.findViewById(R.id.textOption1TextView);
            textOption2TextView= (TextView) analysisResultView.findViewById(R.id.textOption2TextView);
            textOption3TextView= (TextView) analysisResultView.findViewById(R.id.textOption3TextView);
            textOption4TextView= (TextView) analysisResultView.findViewById(R.id.textOption4TextView);
            textOption5TextView= (TextView) analysisResultView.findViewById(R.id.textOption5TextView);


            textviewQuestion.setText(classObject.get(pos).getQuestion());
            textOption1TextView.setText(classObject.get(pos).getAnswer1());
            textOption2TextView.setText(classObject.get(pos).getAnswer2());
            textOption3TextView.setText(classObject.get(pos).getAnswer3());
            textOption4TextView.setText(classObject.get(pos).getAnswer4());
            textOption5TextView.setText(classObject.get(pos).getAnswer5());

            for(int j=0;j<classObject.size();j++){
                if(classObject.get(pos).getNoofchoices().equals("1")){
                    textOption2.setVisibility(View.GONE);
                    textOption2TextView.setVisibility(View.GONE);
                    textOption3TextView.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                    textOption3.setVisibility(View.GONE);
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                }
                else if(classObject.get(pos).getNoofchoices().equals("2")){
                    textOption3.setVisibility(View.GONE);
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                    textOption3TextView.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                }
                else if(classObject.get(pos).getNoofchoices().equals("3")){
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                }
                else if(classObject.get(pos).getNoofchoices().equals("4")){
                    textOption5.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                }
            }

            //toolbarMain.setVisibility(View.GONE);
            // timerLayout.setVisibility(View.VISIBLE);
            //Drawable img = getContext().getResources().getDrawable(R.drawable.ic_cheked72_green);
            //img.setBounds(0, 0, 110, 110);
           // Drawable img2 = getContext().getResources().getDrawable(R.drawable.ic_imag_wrong4);
            //img2.setBounds(0, 0, 110, 110);

            queAndAns=questionAndAns.split("~");
            newPos=pos-1;

            // for(int m=0;m<classObject.size();m++) {
            for (int i = 0; i <queAndAns.length; i++) {
                splitby_caret = queAndAns[i].split("\\^");
                if (splitby_caret[0].equals(classObject.get(pos).getQuestionid())) {

                    if (splitby_caret[1].equals(classObject.get(pos).getCorrectAnswer())) {
                        // view.setBackgroundColor(getActivity().getResources().getColor(R.color.Green));
                        //view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Green));

                        if (splitby_caret[1].equals("1")) {
                            textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                           // textOption1.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                        } else if (splitby_caret[1].equals("2")) {
                            textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        } else if (splitby_caret[1].equals("3")) {
                            textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        } else if (splitby_caret[1].equals("4")) {
                            textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                        } else {
                            textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        }


                    } else if (!(splitby_caret[1].equals(classObject.get(pos).getCorrectAnswer()))) {
                        if ((splitby_caret[1].equals("-1"))) {
                            //view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.notattemptedColor));
                            if(classObject.get(pos).getCorrectAnswer().equals("1")){
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("2")){
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("3")){
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("4")){
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                            }

                        }
                        else{
                            // view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Green));
                            if (splitby_caret[1].equals("1")) {
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));
                            } else if (splitby_caret[1].equals("2")) {
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            } else if (splitby_caret[1].equals("3")) {
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            } else if (splitby_caret[1].equals("4")) {
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));
                            } else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            }
                            if(classObject.get(pos).getCorrectAnswer().equals("1")){
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("2")){
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("3")){
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else if(classObject.get(pos).getCorrectAnswer().equals("4")){
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            }
                            else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                            }
                        }
                    }
                }
                //}
            }
            return analysisResultView;
        }
    }*/
/*    public class AnalysusResAdapter extends FragmentStatePagerAdapter{

        ArrayList<QuestionGetSet>questions;
        Context context;



        public AnalysusResAdapter(Context context,FragmentManager fm,ArrayList<QuestionGetSet>questions) {
            super(fm);
            this.context=context;
            this.questions=questions;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position+1);
        }

        @Override
        public Fragment getItem(int position) {
            return new AnswersFrag(position);
        }

        @Override
        public int getCount() {
            return questions.size();
        }
    }*/
    public interface OnRefreshListner1{
        public void onRefresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Result_Analysis.this, LoginMainPage.class);
        startActivity(intent);
    }
}
