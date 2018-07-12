package eniversity.com;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.eniversity.app.R;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soumyay on 7/18/2016.
 */
public class SubDetails extends AppCompatActivity {
    private GraphicalView mChartView;
    String title="";
    String level="";
    String count="";
    int num1=0;
    String date="";
    String correctAnswers="";
    String wrongtAnswers="";
    String noOfQuestions="";
    String courseId="";
    String examId="";
    String duration="";
    String maxtimeusercantakeexam="";

    Double correctPercent=0.00;
    Double wrongPercent=0.00;
    Double unAttemptedPercent=0.00;
    int umattemptedNum=0;
    CategorySeries distributionSeries;
    DefaultRenderer defaultRenderer;
    TextView titleTextView;
    TextView examLevelTextView;
    TextView numOfAttemptsTextView;
    TextView lastattemptedOnTextView;
    TextView CorrectPerTextView;
    TextView correctNumTextView;
    TextView wrongNumTextView;
    TextView UnattemptedNumTextView;
    TextView rank;
    TextView outOfTextView;
Toolbar toolbar;
    ImageView imageViewBackIcon;
    ImageView imageViewSearchIcon;
    Button takeExamAgainButton;

    String yourRank="";
    ImageView topperLogo;
    View view1new;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_layout);
        title=getIntent().getExtras().getString("title");
        level=getIntent().getExtras().getString("level");
        count=getIntent().getExtras().getString("count");
       // Toast.makeText(SubDetails.this,count,Toast.LENGTH_SHORT).show();
        Log.v( "count in subcourse",count);
        date=getIntent().getExtras().getString("date");
        yourRank=getIntent().getExtras().getString("yourRank");
        String youRanking=yourRank.substring(yourRank.indexOf("o"),yourRank.length());
        String ranking=yourRank.substring(0,yourRank.indexOf("o")-1);
        //Toast.makeText(SubDetails.this, rank, Toast.LENGTH_SHORT).show();
        topperLogo= (ImageView) findViewById(R.id.topperLogo);
        courseId=getIntent().getExtras().getString("courseid");
        examId=getIntent().getExtras().getString("examId");
        duration=getIntent().getExtras().getString("duration");
        maxtimeusercantakeexam=getIntent().getExtras().getString("maxtimeusercantakeexam");
        Log.v("maxtimeusercantakeexam in subdetails",maxtimeusercantakeexam);
        //Toast.makeText(SubDetails.this,count+"max"+maxtimeusercantakeexam,Toast.LENGTH_SHORT).show();
        correctAnswers=getIntent().getExtras().getString("correctAnswers");
        wrongtAnswers=getIntent().getExtras().getString("wrongtAnswers");
        noOfQuestions=getIntent().getExtras().getString("noOfQuestions");
        correctPercent=(Double.parseDouble(correctAnswers)/(Double.parseDouble(noOfQuestions)))*100.00;
        wrongPercent=(Double.parseDouble(wrongtAnswers)/(Double.parseDouble(noOfQuestions)))*100.00;
        umattemptedNum=Integer.parseInt(noOfQuestions)-(Integer.parseInt(correctAnswers) + Integer.parseInt(wrongtAnswers));
        unAttemptedPercent=(Double.parseDouble(String.valueOf(umattemptedNum))/(Double.parseDouble(noOfQuestions)))*100.00;
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        imageViewBackIcon= (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        imageViewBackIcon.setVisibility(View.VISIBLE);
       // imageViewSearchIcon= (ImageView) findViewById(R.id.imageViewSearchIcon);
        //imageViewSearchIcon.setVisibility(View.GONE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleTextView= (TextView) findViewById(R.id.titleTextView);
        examLevelTextView= (TextView) findViewById(R.id.examLevelTextView);
        numOfAttemptsTextView= (TextView) findViewById(R.id.numOfAttemptsTextView);
        lastattemptedOnTextView= (TextView) findViewById(R.id.lastattemptedOnTextView);
        CorrectPerTextView= (TextView) findViewById(R.id.CorrectPerTextView);
        correctNumTextView= (TextView) findViewById(R.id.correctNumTextView);
        wrongNumTextView= (TextView) findViewById(R.id.wrongNumTextView);
        UnattemptedNumTextView= (TextView) findViewById(R.id.UnattemptedNumTextView);
        takeExamAgainButton= (Button) findViewById(R.id.takeExamAgainButton);
        view1new=(View) findViewById(R.id.view1new);
        rank= (TextView) findViewById(R.id.rank);
        if(ranking.equals("0")){
            rank.setText(ranking+" / "+youRanking.replace("out of",""));
            topperLogo.setVisibility(View.GONE);
        }
        else {
            rank.setText(ranking+" / "+youRanking.replace("out of",""));
        }
        outOfTextView= (TextView) findViewById(R.id.outOfTextView);
        outOfTextView.setText(youRanking);
        if(Integer.parseInt(count)>=Integer.parseInt(maxtimeusercantakeexam)){
            takeExamAgainButton.setVisibility(View.GONE);
            if(view1new!=null) {
                view1new.setVisibility(View.GONE);
            }
        }
        else {
            num1=Integer.parseInt(count)+1;
            takeExamAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dia = new Dialog(SubDetails.this);
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.exam_instruction);
                    TextView durationText= (TextView) dia.findViewById(R.id.durationText);
                    String[] parts=duration.split(":");
                    String hours=parts[0];
                    String mins=parts[1];
                    int hours1=Integer.parseInt(hours);
                    int mins1=(hours1*60)+Integer.parseInt(mins);
                    if(mins1<100)
                    {
                        if(mins1==1){

                            durationText.setText("1.Total duration of the exam is "+String.valueOf(mins1)+" min");
                        }
                        durationText.setText("1.Total duration of the exam is "+String.valueOf(mins1)+" mins");
                    }

                    else{
                        if(Integer.parseInt(hours)>1){
                            if(Integer.parseInt(mins)>1){

                                durationText.setText("1.Total duration of the exam is "+hours+" hrs "+mins+" mins");
                            }
                            else{

                                durationText.setText("1.Total duration of the exam is "+hours+" hrs "+mins+" min");
                            }
                        }
                        else{
                            if(Integer.parseInt(mins)>1){

                                durationText.setText("1.Total duration of the exam is "+hours+" hr "+mins+" mins");
                            }
                            else{

                                durationText.setText("1.Total duration of the exam is "+hours+" hr "+mins+" min");
                            }

                        }

                    }
                    Button StartButton= (Button) dia.findViewById(R.id.StartButton);
                    ImageView imageViewCancel= (ImageView) dia.findViewById(R.id.imageViewCancel);
                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }
                    });

                    StartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SubDetails.this, Questions.class);
                            intent.putExtra("examtitle", title);
                            intent.putExtra("count", ""+num1);
                            intent.putExtra("examlevel", level);
                            intent.putExtra("courseid", courseId);
                            intent.putExtra("date", date);
                            intent.putExtra("examinationid", examId);
                            intent.putExtra("duration", duration);
                            intent.putExtra("maxtimeusercantakeexam",maxtimeusercantakeexam);
                            startActivity(intent);
                            finish();
                            dia.dismiss();
                        }
                    });
                    dia.show();
                    /*Intent intent = new Intent(SubDetails.this, Questions.class);
                    intent.putExtra("examtitle", title);
                    intent.putExtra("count", ""+num1);
                    intent.putExtra("examlevel", level);
                    intent.putExtra("courseid", courseId);
                    intent.putExtra("date", date);
                    intent.putExtra("examinationid", examId);
                    intent.putExtra("duration", duration);
                    intent.putExtra("maxtimeusercantakeexam",maxtimeusercantakeexam);
                    startActivity(intent);
                    finish();*/
                }
            });
        }

        titleTextView.setText(title);
        examLevelTextView.setText(level);
        numOfAttemptsTextView.setText(count);
        String output = date.substring(0, 10);
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
        CorrectPerTextView.setText(String.valueOf((int)Math.round(correctPercent))+"%");
       correctNumTextView.setText(correctAnswers);
        wrongNumTextView.setText(wrongtAnswers);
        UnattemptedNumTextView.setText(String.valueOf(umattemptedNum));




        String[] code = new String[] {
                "Correct", "Wrong", "UnAttempted"
        };
       // Toast.makeText(SubDetails.this,""+(int)Math.round(correctPercent),Toast.LENGTH_SHORT).show();
        //Toast.makeText(SubDetails.this,""+(int)Math.round(wrongPercent),Toast.LENGTH_SHORT).show();
        //Toast.makeText(SubDetails.this,""+(int)Math.round(unAttemptedPercent),Toast.LENGTH_SHORT).show();
        // Pie Chart Section Value
        int[] distribution = { (int)Math.round(correctPercent), (int)Math.round(wrongPercent), (int)Math.round(unAttemptedPercent)} ;

        // Color of each Pie Chart Sections
        int[] colors = { R.color.appcolor, R.color.wrongColor, R.color.unattemptedColor};

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
                seriesRenderer.setColor(getApplicationContext().getResources().getColor(colors[i]));
                seriesRenderer.setDisplayBoundingPoints(false);
                // Adding a renderer for a slice
                defaultRenderer.addSeriesRenderer(seriesRenderer);
                // defaultRenderer.setChartTitle("");
                defaultRenderer.setShowLabels(false);
                defaultRenderer.setPanEnabled(false);
                defaultRenderer.setZoomButtonsVisible(false);
                defaultRenderer.setZoomEnabled(false);
               // defaultRenderer.setDisplayValues(true);
                //defaultRenderer.setLabelsTextSize(15.00f);

                defaultRenderer.setShowLegend(false);
                defaultRenderer.setScale(1.3f);

                //defaultRenderer.setLegendTextSize(20.00f);
                //defaultRenderer.setLegendTextSize(15.00f);

                //defaultRenderer.setMargins(new int[] {0, 0, 0, 0 });

                //defaultRenderer.setShowLegend(false);

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.linear1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500,500);
            params.height=500;
           params.width=800;
            params.gravity=Gravity.CENTER;
            //layout.setLayoutParams(params);
            mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
           // mChartView.setLayoutParams(params);
            layout.addView(mChartView,params);
        } else {
            mChartView.repaint();
        }
    }
}
