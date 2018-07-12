package fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eniversity.app.R;

import java.util.ArrayList;

import adapter.CourseAdapter;
import eniversity.com.CorseInfo;
import eniversity.com.Questions;
import get.set.CountGetSet;
import get.set.Examdetails_GetSet;

/**
 * Created by soumyay on 4/17/2017.
 */

@SuppressLint("ValidFragment")
public class ExaminationFrag extends Fragment {
    ListView ListviewexamDetails;
    TextView noexamstEXTView;
    ArrayList<Examdetails_GetSet> exam_details;
    TextView durationText;
    Button StartButton;
    ImageView imageViewCancel;
    int isSubscribed;
    ArrayList<CountGetSet>CountArray;
    String approvedExamLevel="";
    String courseid="";

    public ExaminationFrag(ArrayList<Examdetails_GetSet>exam_details,int isSubscribed,ArrayList<CountGetSet>CountArray, String approvedExamLevel, String courseid){
        this.exam_details=exam_details;
        this.isSubscribed=isSubscribed;
        this.CountArray=CountArray;
        this.approvedExamLevel=approvedExamLevel;
        this.courseid=courseid;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.examination_frag_layout, container, false);
        ListviewexamDetails= (ListView) view.findViewById(R.id.ListviewexamDetails);
        noexamstEXTView= (TextView) view.findViewById(R.id.noexamstEXTView);
        if(exam_details.size()==0){
            noexamstEXTView.setVisibility(View.VISIBLE);
        }
        else {
            ListviewexamDetails.setAdapter(new CourseAdapter(getActivity(), exam_details,isSubscribed,CountArray,approvedExamLevel));

            ListviewexamDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView takeExamButton = (TextView) view.getTag(R.id.takeExamButton);

                    switch (view.getId()) {
                        case R.id.takeExamButton:
                            if (isSubscribed == 1) {
                                getTakeExamOpration(position);
                            }  break;
                        default:
                            if (isSubscribed == 1) {
                                getTakeExamOpration(position);
                            }  break;
                    }
                }
            });

        }





        return view;
    }
    private void getTakeExamOpration(final int position) {

        final Dialog dia = new Dialog(getActivity());
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(R.layout.exam_instruction);
        durationText = (TextView) dia.findViewById(R.id.durationText);
        String[] parts = exam_details.get(position).getExamDuration().split(":");
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


        StartButton = (Button) dia.findViewById(R.id.StartButton);
        imageViewCancel = (ImageView) dia.findViewById(R.id.imageViewCancel);
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();
            }
        });
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                String lastExamdate = "";
                if (CountArray.size() == 0) {
                    count = 0 + 1;
                    lastExamdate = "";
                } else {
                    for (int i = 0; i < CountArray.size(); i++) {

                        if (exam_details.get(position).getExamId().equals(CountArray.get(i).getExamId())) {
                            lastExamdate = CountArray.get(i).getExamdate();
                            count = Integer.parseInt(CountArray.get(i).getNoOfattCount()) + 1;
                        } else {
                            count = 1;
                            lastExamdate = "";
                        }
                    }
                }

                Intent intent = new Intent(getActivity(), Questions.class);
                intent.putExtra("count", "" + count);
                intent.putExtra("examlevel", exam_details.get(position).getExamtype());
                intent.putExtra("courseid", courseid);
                intent.putExtra("date", lastExamdate);
                intent.putExtra("maxtimeusercantakeexam", exam_details.get(position).getMaxtimeusercantakeexam());
                intent.putExtra("examtitle", exam_details.get(position).getExamTitle().toString());
                intent.putExtra("duration", exam_details.get(position).getExamDuration().toString());
                intent.putExtra("examinationid", String.valueOf(exam_details.get(position).getExamId()));
                startActivity(intent);
                getActivity().finish();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("All attempts completed");
        builder.setPositiveButton("OK", null);
        boolean isallowed = false;
        for (int i = 0; i < CountArray.size(); i++) {
            if (exam_details.get(position).getExamId().equals(CountArray.get(i).getExamId())) {
                if (Integer.parseInt(CountArray.get(i).getNoOfattCount()) >= Integer.parseInt(exam_details.get(position).getMaxtimeusercantakeexam())) {
                    isallowed = true;
                }
            }
        }

        if (isallowed) {
            AlertDialog alert = builder.create();
            alert.show();

        } else if (approvedExamLevel.contains(exam_details.get(position).getExamtype())) {
            if (!exam_details.get(position).getExamtype().equals(""))
                if (!dia.isShowing()) {
                    dia.show();
                }

        }
    }

}
