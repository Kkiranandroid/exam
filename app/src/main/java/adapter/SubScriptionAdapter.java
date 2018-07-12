package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eniversity.app.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import get.set.SubScriptionGetSet;

/**
 * Created by Administrator on 12/12/2016.
 */

public class SubScriptionAdapter extends BaseAdapter {
    Context context;
    ArrayList<SubScriptionGetSet> subScriptionGetSets;
    private  static LayoutInflater inflater=null;
    public SubScriptionAdapter(Context context, ArrayList<SubScriptionGetSet> subScriptionGetSets){
        this.context=context;
        this.subScriptionGetSets=subScriptionGetSets;
        inflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return subScriptionGetSets.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
      Holder holder= new Holder();
        View rowView=null;
        if(rowView==null){
            rowView=inflater.inflate(R.layout.subscription,parent,false);
            holder.imageViewsubscribeOne=(ImageView)rowView.findViewById(R.id.imageViewsubscribeOne);
            holder.textViewSubscribedScoursename=(TextView)rowView.findViewById(R.id.textViewSubscribedScoursename);
            holder.textViewLevels=(TextView)rowView.findViewById(R.id.textViewLevels);
            holder.textViewSubscribedScourseDate=(TextView)rowView.findViewById(R.id.textViewSubscribedScourseDate);
            holder.transcAmtTextView=(TextView)rowView.findViewById(R.id.transcAmtTextView);
            holder.textViewTxnId=(TextView)rowView.findViewById(R.id.textViewTxnId);
            rowView.setTag(holder);
        }
        else
        {
            holder=(Holder)rowView.getTag();

        }

        holder.textViewSubscribedScoursename.setText(subScriptionGetSets.get(position).getCorseName());
        holder.textViewLevels.setText(subScriptionGetSets.get(position).getExamlevel());
        holder.transcAmtTextView.setText(context.getResources().getString(R.string.rupees)+subScriptionGetSets.get(position).getTxnAmt());
        holder.textViewTxnId.setText("Txn"+subScriptionGetSets.get(position).getTxnId());
        String date=subScriptionGetSets.get(position).getSubDate();

        String output = date.substring(0, 10);
        DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date1= null;
        try {
            date1 = originalFormat.parse(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        holder.textViewSubscribedScourseDate.setText(formattedDate);
        if(subScriptionGetSets.get(position).getCorseName().length()>0) {

            if(subScriptionGetSets.get(position).getCourseImage().length()>0){
                Picasso.with(context).load(subScriptionGetSets.get(position).getCourseImage()).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
            }
            else{
                Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
            }

        }else{


            if(subScriptionGetSets.get(position).getCourseImage().length()>0){
                Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
            }
            else{
                Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewsubscribeOne);
            }

           // Picasso.with(context).load(R.drawable.no_image).placeholder(R.drawable.no_image) .error(R.drawable.no_image).into(holder.imageViewsubscribeOne);
        }

        return rowView;
    }
   public  class Holder{
       ImageView imageViewsubscribeOne;
       TextView textViewSubscribedScoursename;
       TextView textViewLevels;
       TextView textViewSubscribedScourseDate;
       TextView transcAmtTextView;
       TextView textViewTxnId;
   }
}
