package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import get.set.CountGetSet;
import get.set.Examdetails_GetSet;

import com.eniversity.app.R;

/**
 * Created by soumyay on 4/20/2016.
 */
public class CourseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Examdetails_GetSet> examDetails;
    private static LayoutInflater inflater = null;
    private int isSubscribed;
    private int easysub=0;
    private int advsub=0;
    private int intsub=0;
    ArrayList<CountGetSet> CountArray;
    String approvedExamLevel = "";


    public CourseAdapter(Context context, ArrayList<Examdetails_GetSet> examDetails, int isSubscribed, ArrayList<CountGetSet> CountArray, String approvedExamLevel) {
        this.context = context;
        this.examDetails = examDetails;
        this.approvedExamLevel = approvedExamLevel;
        this.isSubscribed = isSubscribed;
        this.CountArray = CountArray;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Holder holder = new Holder();
        View rowView = null;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.exam_details, parent, false);
            holder.examTitleTextView = (TextView) rowView.findViewById(R.id.examTitleTextView);
            holder.durationTextView = (TextView) rowView.findViewById(R.id.durationTextView);
            holder.takeExamButton = (TextView) rowView.findViewById(R.id.takeExamButton);
            rowView.setTag(R.id.takeExamButton, holder.takeExamButton);
            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();

        }
        String duration = examDetails.get(position).getExamDuration();
        String[] parts = duration.split(":");
        String hours = parts[0];
        String mins = parts[1];
        int hours1 = Integer.parseInt(hours);
        int mins1 = (hours1 * 60) + Integer.parseInt(mins);


        boolean isallowed = false;
        int noofattempts = 0;

        for (int i = 0; i < CountArray.size(); i++) {
            if (examDetails.get(position).getExamId().equals(CountArray.get(i).getExamId())) {
                noofattempts=Integer.parseInt(CountArray.get(i).getNoOfattCount());
                if (Integer.parseInt(CountArray.get(i).getNoOfattCount()) >= Integer.parseInt(examDetails.get(position).getMaxtimeusercantakeexam())) {
                    isallowed = true;
                }
            }
        }
        if(approvedExamLevel.contains("Easy")){
            easysub=1;
        }
        if(approvedExamLevel.contains("Intermediate")){
            intsub=1;
        }
        if(approvedExamLevel.contains("Advanced")){
            advsub=1;
        }

       /* if (isSubscribed == 1) {

            holder.takeExamButton.setVisibility(View.VISIBLE);
        } else {
            holder.takeExamButton.setVisibility(View.GONE);
        }*/


       if(easysub!=1&&advsub!=1&&intsub!=1){
           holder.takeExamButton.setVisibility(View.GONE);
       }

       else {
           if (easysub == 1 && examDetails.get(position).getExamtype().equals("Easy") && isallowed == true) {
               holder.takeExamButton.setVisibility(View.GONE);
           } else if (intsub == 1 && examDetails.get(position).getExamtype().equals("Intermediate") && isallowed == true) {
               holder.takeExamButton.setVisibility(View.GONE);
           } else if (advsub == 1 && examDetails.get(position).getExamtype().equals("Advanced") && isallowed == true) {
               holder.takeExamButton.setVisibility(View.GONE);

           }
           else{
               holder.takeExamButton.setVisibility(View.GONE);
               if(approvedExamLevel.contains(examDetails.get(position).getExamtype())){
                   if(noofattempts==0){
                       holder.takeExamButton.setText("TAKE EXAM NOW");
                   }
                   else if(noofattempts>0){
                       holder.takeExamButton.setText("TAKE EXAM AGAIN");
                   }
                   holder.takeExamButton.setVisibility(View.VISIBLE);
               }

           }
       }




/*
        for (int i = 0; i < CountArray.size(); i++) {
            if (isSubscribed == 1) {
                if ((approvedExamLevel.contains(examDetails.get(position).getExamtype()))) {
                    if (examDetails.get(position).getExamId().equals(CountArray.get(i).getExamId())) {

                        if (Integer.parseInt(CountArray.get(i).getNoOfattCount()) >= Integer.parseInt(examDetails.get(position).getMaxtimeusercantakeexam())) {
                            holder.takeExamButton.setVisibility(View.GONE);
                            break;

                        } else {
                            holder.takeExamButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.takeExamButton.setVisibility(View.GONE);
                    }


                } else {
                    holder.takeExamButton.setVisibility(View.GONE);
                }

            } else {
                holder.takeExamButton.setVisibility(View.GONE);
            }

        }*/


        holder.takeExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0);
            }
        });
        /*if(hours.length()<=1){
            if(mins.length()<=1){
                holder.durationTextView.setText("0"+hours+" : "+"0"+mins);
            }
            else{
                holder.durationTextView.setText("0"+hours+" : "+mins);
            }

        }
        else{
            if(mins.length()<=1){
                holder.durationTextView.setText(hours+" : "+"0"+mins);
            }
            else{
                holder.durationTextView.setText(hours+" : "+mins);
            }


        }*/


        if (mins1 < 100) {
            if (mins1 == 1) {
                holder.durationTextView.setText(String.valueOf(mins1) + " min");
            }
            holder.durationTextView.setText(String.valueOf(mins1) + " mins");
        } else {
            if (Integer.parseInt(hours) > 1) {
                if (Integer.parseInt(mins) > 1) {
                    holder.durationTextView.setText(hours + " hrs " + mins + " mins");
                } else {
                    holder.durationTextView.setText(hours + " hrs " + mins + " min");
                }
            } else {
                if (Integer.parseInt(mins) > 1) {
                    holder.durationTextView.setText(hours + " hr " + mins + " mins");
                } else {
                    holder.durationTextView.setText(hours + " hr " + mins + " min");
                }

            }

        }

        holder.examTitleTextView.setText(examDetails.get(position).getExamTitle());


        return rowView;

    }

    @Override
    public int getCount() {
        return examDetails.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public class Holder {
        TextView examTitleTextView;
        TextView durationTextView;
        TextView takeExamButton;
    }
}
