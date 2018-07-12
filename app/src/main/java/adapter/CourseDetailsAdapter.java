package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import eniversity.com.CorseInfo;
import get.set.ChildGetSet;
import eniversity.com.Questions;

import com.eniversity.app.R;

/**
 * Created by soumyay on 7/18/2016.
 */
public class CourseDetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChildGetSet> childGetSetArrayList;
    String corseId = "";
    String maxtimeusercantakeexam = "";
    public static LayoutInflater inflater = null;

    public CourseDetailsAdapter(Context context, ArrayList<ChildGetSet> childGetSetArrayList, String corseId, String maxtimeusercantakeexam) {
        this.context = context;
        this.corseId = corseId;
        this.maxtimeusercantakeexam = maxtimeusercantakeexam;
        this.childGetSetArrayList = childGetSetArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return childGetSetArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = null;
        Holder holder = new Holder();
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.fragment_items, parent, false);
            holder.textViewExamTitle2 = (TextView) rowView.findViewById(R.id.textViewExamTitle2);
            holder.textViewExamTitle = (TextView) rowView.findViewById(R.id.textViewExamTitle);
            holder.textViewExamTakenOn = (TextView) rowView.findViewById(R.id.textViewExamTakenOn);
            holder.textViewNoOfAttempts = (TextView) rowView.findViewById(R.id.textViewNoOfAttempts);
            holder.textViewRank = (TextView) rowView.findViewById(R.id.textViewRank);
            holder.TextViewScore = (TextView) rowView.findViewById(R.id.TextViewScore);
            holder.textViewExamTakenText = (TextView) rowView.findViewById(R.id.textViewExamTakenText);
            holder.notSubScribedTextView = (TextView) rowView.findViewById(R.id.notSubScribedTextView);
            holder.takeExamButton = (Button) rowView.findViewById(R.id.takeExamButton);
            holder.textViewNoOfAttemptsText = (TextView) rowView.findViewById(R.id.textViewNoOfAttemptsText);
            holder.MainLayout = (LinearLayout) rowView.findViewById(R.id.MainLayout);
            holder.rankTextTextView = (TextView) rowView.findViewById(R.id.rankTextTextView);
            holder.scoreTextTextView = (TextView) rowView.findViewById(R.id.scoreTextTextView);
            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();
        }


        if (childGetSetArrayList.get(position).getExamtakendate().equals("")) {
            holder.MainLayout.setVisibility(View.VISIBLE);
            holder.textViewExamTitle.setText(childGetSetArrayList.get(position).getExamTitle());
            holder.textViewExamTitle2.setVisibility(View.GONE);
            holder.TextViewScore.setVisibility(View.VISIBLE);
            holder.textViewExamTakenText.setVisibility(View.VISIBLE);
            holder.textViewExamTakenOn.setVisibility(View.VISIBLE);
            holder.textViewNoOfAttemptsText.setVisibility(View.VISIBLE);
            //holder.takeExamButton.setVisibility(View.VISIBLE);
            holder.textViewRank.setVisibility(View.VISIBLE);
            holder.textViewNoOfAttempts.setVisibility(View.VISIBLE);
            holder.scoreTextTextView.setVisibility(View.VISIBLE);
            holder.rankTextTextView.setVisibility(View.VISIBLE);
            //float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_S, 14F, context.getResources().getDisplayMetrics());
            holder.textViewExamTakenOn.setText("-");
            // holder.textViewExamTakenOn.setTextSize(myTextSize);
            //holder.textViewExamTakenOn.setText();
            holder.textViewNoOfAttempts.setText("-");
            holder.textViewRank.setText("-");
            holder.TextViewScore.setText("-");

            /*holder.takeExamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dia = new Dialog(context);
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.exam_instruction);
                    TextView durationText = (TextView) dia.findViewById(R.id.durationText);
                    String[] parts = childGetSetArrayList.get(position).getDuration().split(":");
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
                            Intent intent = new Intent(context, Questions.class);
                            intent.putExtra("examtitle", childGetSetArrayList.get(position).getExamTitle());
                            intent.putExtra("count", childGetSetArrayList.get(position).getCount());
                            intent.putExtra("maxtimeusercantakeexam", childGetSetArrayList.get(position).getMaxCount());
                            Log.v("count", childGetSetArrayList.get(position).getCount());
                            intent.putExtra("examlevel", childGetSetArrayList.get(position).getExaminationlevel());
                            intent.putExtra("courseid", corseId);
                            intent.putExtra("date", childGetSetArrayList.get(position).getExamtakendate());
                            intent.putExtra("examinationid", childGetSetArrayList.get(position).getExaminationid());
                            intent.putExtra("duration", childGetSetArrayList.get(position).getDuration());

                            context.startActivity(intent);
                            dia.dismiss();
                        }
                    });
                    dia.show();
                }
            });*/

        } else {
            holder.textViewExamTitle.setText(childGetSetArrayList.get(position).getExamTitle());

            String date = childGetSetArrayList.get(position).getExamtakendate();
            if (date.equals("")) {
                holder.textViewExamTakenOn.setText(date);
            } else {
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
                holder.textViewExamTakenOn.setText(formattedDate);
            }

            holder.textViewNoOfAttempts.setText(childGetSetArrayList.get(position).getCount());
            holder.textViewRank.setText(childGetSetArrayList.get(position).getRanking().replace("out of", "/").replace(" ", ""));
            holder.TextViewScore.setText(childGetSetArrayList.get(position).getTotalscore() + "%");
        }


        return rowView;
    }

    public class Holder {
        TextView textViewExamTakenText;
        TextView textViewNoOfAttemptsText;
        TextView scoreTextTextView;
        TextView rankTextTextView;
        TextView textViewExamTitle2;

        TextView textViewExamTitle;
        TextView textViewExamTakenOn;
        TextView textViewNoOfAttempts;
        TextView textViewRank;
        TextView TextViewScore;
        TextView notSubScribedTextView;
        Button takeExamButton;
        LinearLayout MainLayout;

    }
}
