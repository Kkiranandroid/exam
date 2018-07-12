package fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eniversity.app.R;

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

import eniversity.com.Questions;
import eniversity.com.SubDetails;

/**
 * Created by Administrator on 11/30/2016.
 */
@SuppressLint("ValidFragment")
public class ResultFrag extends Fragment {
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
    View view1new;

        ImageView imageViewBackIcon;
        ImageView imageViewSearchIcon;
        CategorySeries distributionSeries;
        DefaultRenderer defaultRenderer;
        private GraphicalView mChartView;


        private ImageView imageBack;
        private TextView textViewHeading;
        private LinearLayout analysistoolBar;
    private TextView rankTextView;
    private TextView outOfTextView;
    Context context;
    ArrayList<String>resultvalues;
     Double correctPercent=0.00;
     Double wrongPercent=0.00;
     Double unAttemptedPercent=0.00;
     int umattemptedNum=0;
     int num1=0;


    public ResultFrag(Context context, ArrayList<String>resultvalues){
        this.context=context;
        this.resultvalues=resultvalues;

    }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.exam_layout, null);
            //analysistoolBar= (LinearLayout) getActivity().findViewById(R.id.analysistoolBar);
            //analysistoolBar.setVisibility(View.GONE);
try{
            titleTextView = (TextView) rootView.findViewById(R.id.titleTextView);
            examLevelTextView = (TextView) rootView.findViewById(R.id.examLevelTextView);
            numOfAttemptsTextView = (TextView) rootView.findViewById(R.id.numOfAttemptsTextView);
            lastattemptedOnTextView = (TextView) rootView.findViewById(R.id.lastattemptedOnTextView);
            CorrectPerTextView = (TextView) rootView.findViewById(R.id.CorrectPerTextView);
            correctNumTextView = (TextView) rootView.findViewById(R.id.correctNumTextView);
            wrongNumTextView = (TextView) rootView.findViewById(R.id.wrongNumTextView);
            UnattemptedNumTextView = (TextView) rootView.findViewById(R.id.UnattemptedNumTextView);
            takeExamAgainButton = (Button) rootView.findViewById(R.id.takeExamAgainButton);
            view1new = (View) rootView.findViewById(R.id.view1new);
            rankTextView = (TextView) rootView.findViewById(R.id.rankTextView);
            topperLogo = (ImageView) rootView.findViewById(R.id.topperLogo);
            topperLogo2 = (ImageView) rootView.findViewById(R.id.topperLogo2);
            outOfTextView = (TextView) rootView.findViewById(R.id.outOfTextView);
            outOfTextView.setVisibility(View.GONE);

            correctPercent = (Double.parseDouble(resultvalues.get(3)) / (Double.parseDouble(resultvalues.get(12)))) * 100.00;
            wrongPercent = (Double.parseDouble(resultvalues.get(4)) / (Double.parseDouble(resultvalues.get(12)))) * 100.00;
            umattemptedNum = Integer.parseInt(resultvalues.get(12)) - (Integer.parseInt(resultvalues.get(3)) + Integer.parseInt(resultvalues.get(4)));
            unAttemptedPercent = (Double.parseDouble(String.valueOf(umattemptedNum)) / (Double.parseDouble(resultvalues.get(12)))) * 100.00;
            if (Integer.parseInt(resultvalues.get(0)) > 10) {
                topperLogo2.setVisibility(View.VISIBLE);
                topperLogo.setVisibility(View.GONE);
            }
            if (Integer.parseInt(resultvalues.get(0)) == 0) {
                rankTextView.setText(resultvalues.get(0)+" / "+ resultvalues.get(1));
                topperLogo2.setVisibility(View.GONE);
                topperLogo.setVisibility(View.GONE);
                //outOfTextView.setText("out of "+resultvalues.get(1));
            } else {
                rankTextView.setText(resultvalues.get(0) + " / " + resultvalues.get(1));
                // outOfTextView.setText("out of "+resultvalues.get(1));
            }
            examLevelTextView.setText(resultvalues.get(10));
            titleTextView.setText(resultvalues.get(2));
            correctNumTextView.setText(resultvalues.get(3));
            wrongNumTextView.setText(resultvalues.get(4));
            UnattemptedNumTextView.setText(String.valueOf(umattemptedNum));
            numOfAttemptsTextView.setText(resultvalues.get(5));
            CorrectPerTextView.setText(String.valueOf((int) Math.round(correctPercent)) + "%");

            if (resultvalues.get(6).equals("")) {
                lastattemptedOnTextView.setText("NA");
            } else {
                String output = resultvalues.get(6).substring(0, 10);
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

            if (Integer.parseInt(resultvalues.get(5)) >= Integer.parseInt(resultvalues.get(7))) {
                takeExamAgainButton.setVisibility(View.GONE);
                view1new.setVisibility(View.GONE);
            } else {
                num1 = Integer.parseInt(resultvalues.get(5)) + 1;
                takeExamAgainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dia = new Dialog(context);
                        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dia.setContentView(R.layout.exam_instruction);
                        TextView durationText = (TextView) dia.findViewById(R.id.durationText);
                        String[] parts = resultvalues.get(11).split(":");
                        String hours = parts[0];
                        String mins = parts[1];
                        int hours1 = Integer.parseInt(hours);
                        int mins1 = (hours1 * 60) + Integer.parseInt(mins);
                        if (mins1 < 100) {
                            if (mins1 == 1) {

                                durationText.setText("1.Total duration of the exam is " + String.valueOf(mins1) + " min");
                            }
                            durationText.setText("1.Total duration of the exam is " + String.valueOf(mins1) + " mins");
                        } else {
                            if (Integer.parseInt(hours) > 1) {
                                if (Integer.parseInt(mins) > 1) {

                                    durationText.setText("1.Total duration of the exam is " + hours + " hrs " + mins + " mins");
                                } else {

                                    durationText.setText("1.Total duration of the exam is " + hours + " hrs " + mins + " min");
                                }
                            } else {
                                if (Integer.parseInt(mins) > 1) {

                                    durationText.setText("1.Total duration of the exam is " + hours + " hr " + mins + " mins");
                                } else {

                                    durationText.setText("1.Total duration of the exam is " + hours + " hr " + mins + " min");
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
                                Intent intent = new Intent(getActivity().getApplicationContext(), Questions.class);
                                intent.putExtra("examtitle", resultvalues.get(2));
                                intent.putExtra("count", String.valueOf(num1));
                                intent.putExtra("maxtimeusercantakeexam", resultvalues.get(7));
                                //Toast.makeText(context,childGetSetArrayList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                                Log.v("count", resultvalues.get(5));
                                intent.putExtra("examlevel", resultvalues.get(10));
                                intent.putExtra("courseid", resultvalues.get(8));
                                //intent.putExtra("date", date);
                                intent.putExtra("examinationid", resultvalues.get(9));
                                intent.putExtra("duration", resultvalues.get(11));
                                startActivity(intent);
                                getActivity().finish();
                                dia.dismiss();
                            }
                        });
                        dia.show();
                       /* Intent intent = new Intent(getActivity().getApplicationContext(), Questions.class);
                        intent.putExtra("examtitle", resultvalues.get(2));
                        intent.putExtra("count", String.valueOf(num1));
                        intent.putExtra("maxtimeusercantakeexam", resultvalues.get(7));
                        //Toast.makeText(context,childGetSetArrayList.get(position).getCount(),Toast.LENGTH_SHORT).show();
                        Log.v("count", resultvalues.get(5));
                        intent.putExtra("examlevel", resultvalues.get(10));
                        intent.putExtra("courseid", resultvalues.get(8));
                        //intent.putExtra("date", date);
                        intent.putExtra("examinationid", resultvalues.get(9));
                        intent.putExtra("duration", resultvalues.get(11));
                        startActivity(intent);
                        getActivity().finish();*/
                    }
                });
            }


            String[] code = new String[]{
                    "Correct", "Wrong", "UnAttempted"
            };
            // Toast.makeText(SubDetails.this,""+(int)Math.round(correctPercent),Toast.LENGTH_SHORT).show();
            //Toast.makeText(SubDetails.this,""+(int)Math.round(wrongPercent),Toast.LENGTH_SHORT).show();
            //Toast.makeText(SubDetails.this,""+(int)Math.round(unAttemptedPercent),Toast.LENGTH_SHORT).show();
            // Pie Chart Section Value
            int[] distribution = {(int) Math.round(correctPercent), (int) Math.round(wrongPercent), (int) Math.round(unAttemptedPercent)};

            // Color of each Pie Chart Sections
            //int[] colors = { R.color.Green, R.color.wrongColor, R.color.unattemptedColor};
            int[] colors = {R.color.appcolor, R.color.wrongColor, R.color.unattemptedColor};

            // Instantiating CategorySeries to plot Pie Chart
            distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
            for (int i = 0; i < distribution.length; i++) {
                // Adding a slice with its values and name to the Pie Chart

                if (distribution[i] == 0) {

                } else {
                    distributionSeries.add(code[i], distribution[i]);

                }
            }

            // Instantiating a renderer for the Pie Chart
            defaultRenderer = new DefaultRenderer();
            for (int i = 0; i < distribution.length; i++) {
                if (distribution[i] == 0) {

                } else {
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
                LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear1);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500, 500);
                //params.leftMargin=5;

                params.height = 500;
                params.width = 800;

                params.gravity = Gravity.CENTER;
                //layout.setLayoutParams(params);
                mChartView = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
                layout.addView(mChartView, params);

            } else {
                mChartView.repaint();
            }
        }
catch (Exception e){
    e.printStackTrace();
}

            return rootView;
        }




}
