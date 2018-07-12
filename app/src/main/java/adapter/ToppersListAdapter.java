package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.eniversity.app.R;
import java.util.ArrayList;

import get.set.ToppersListGetSet;
import eniversity.com.RoundImageView;

/**
 * Created by soumyay on 4/4/2016.
 */
public class ToppersListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ToppersListGetSet> TopperDetails;



    public ToppersListAdapter(Context context, ArrayList<ToppersListGetSet> TopperDetails) {
        this.context = context;
        this.TopperDetails = TopperDetails;
    }
    @Override
    public int getCount() {
        return TopperDetails.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder hold= new Holder();
        		View rowView;
        			rowView=convertView;
        if (rowView==null)
        {
            LayoutInflater	inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.toppers_list,parent,false);


            hold.NameTextView=(TextView) rowView.findViewById(R.id.NameTextView);
            hold.percentageTextView=(TextView) rowView.findViewById(R.id.PercentageTextView);
            hold.topperImageView=(ImageView) rowView.findViewById(R.id.TopperImageView);
            hold.rankTextView= (TextView)rowView.findViewById(R.id.rankTextView);
            hold.imageView1 = (ImageView) rowView.findViewById(R.id.imageView1);
            rowView.setTag(hold);
        }
        else{
            hold = (Holder) rowView.getTag();
        }

        if(TopperDetails.get(position).getNameImageView()!=null) {

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(TopperDetails.get(position).getNameImageView()));
            RoundImageView round= new RoundImageView(icon);
            hold.imageView1.setImageDrawable(round);
        }else{
            String name = TopperDetails.get(position).getName();
            String substr = name.substring(0, 1);

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                        .withBorder(2)
                        //.textColor(Color.RED)
                    .textColor(context.getResources().getColor(R.color.white))
                        .toUpperCase()
                        .bold()
                    .endConfig()
                    .buildRound(substr,context.getResources().getColor(R.color.appcolor));

            hold.imageView1.setImageDrawable(drawable);

        }

        hold.NameTextView.setText(TopperDetails.get(position).getName());
        hold.percentageTextView.setText(TopperDetails.get(position).getPercentage()+"%");
        hold.rankTextView.setText(TopperDetails.get(position).getRank());
        return rowView;
    }

    public class Holder
    {
        ImageView topperImageView;
        TextView NameTextView;
        TextView percentageTextView;
        TextView rankTextView;
        ImageView imageView1;
    }
}
