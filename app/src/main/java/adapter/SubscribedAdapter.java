package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import get.set.SubscribedGetSet;
import com.eniversity.app.R;

/**
 * Created by shveta on 4/1/2016.
 */
public class SubscribedAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SubscribedGetSet> subscribedItems;

    private static LayoutInflater inflater = null;
    String isFrom="";


    public SubscribedAdapter(Context context, ArrayList<SubscribedGetSet> subscribedItems,String isFrom) {
        this.context = context;
        this.subscribedItems = subscribedItems;
        this.isFrom=isFrom;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return subscribedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView = null;

        if (rowView == null) {

            if(isFrom.equalsIgnoreCase("LoginMainPage")) {
                rowView = inflater.inflate(R.layout.subscribed_layout, null);

                holder.imageViewsubscribeOne = (ImageView) rowView.findViewById(R.id.imageViewsubscribeOne);
                holder.textViewSubscribedScourseDate = (TextView) rowView.findViewById(R.id.textViewSubscribedScourseDate);
                holder.textViewSubscribedScoursename = (TextView) rowView.findViewById(R.id.textViewSubscribedScoursename);
                holder.textViewSubscribedScoursePercentage = (TextView) rowView.findViewById(R.id.textViewSubscribedScoursePercentage);
                holder.textViewSubscribedScourseRank = (TextView) rowView.findViewById(R.id.textViewSubscribedScourseRank);
            }
            else{
                rowView = inflater.inflate(R.layout.subscribed_seeall, null);
                holder.imageViewsubscribeOne = (ImageView) rowView.findViewById(R.id.imageViewsubscribeOne);
                holder.textViewSubscribedScourseDate = (TextView) rowView.findViewById(R.id.textViewSubscribedScourseDate);
                holder.textViewSubscribedScoursename = (TextView) rowView.findViewById(R.id.textViewSubscribedScoursename);
                holder.textViewSubscribedScoursePercentage = (TextView) rowView.findViewById(R.id.textViewSubscribedScoursePercentage);
                holder.textViewSubscribedScourseRank = (TextView) rowView.findViewById(R.id.textViewSubscribedScourseRank);
            }
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

String date=subscribedItems.get(position).getSubscribedon();

        String output = date.substring(0, 10);
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date1= null;
        try {
            date1 = originalFormat.parse(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        holder.textViewSubscribedScourseDate.setText(formattedDate);



        if(subscribedItems.get(position).getRanking().equals("-")) {
            holder.textViewSubscribedScourseRank.setText("NA");
        }else{
            holder.textViewSubscribedScourseRank.setText(subscribedItems.get(position).getRanking());
        }
        if(subscribedItems.get(position).getOutof().equals("-")) {
            holder.textViewSubscribedScoursePercentage.setText("0 %");
        }
        else {
            holder.textViewSubscribedScoursePercentage.setText(subscribedItems.get(position).getOutof() + "%");
        }
        holder.textViewSubscribedScoursename.setText(subscribedItems.get(position).getCoursename());

if(subscribedItems.get(position).getCourseimage().length()>0) {
    Picasso.with(context).load(subscribedItems.get(position).getCourseimage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
            .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
}else{
    Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble) .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
}

        return rowView;
    }




    public class ViewHolder {
        ImageView imageViewsubscribeOne;
        TextView textViewSubscribedScoursename;
        TextView textViewSubscribedScourseDate;
        TextView textViewSubscribedScourseRank;
        TextView textViewSubscribedScoursePercentage;
    }
}
