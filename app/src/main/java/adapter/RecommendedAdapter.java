package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import eniversity.com.CorseInfo;
import get.set.RecommendedGetSet;
import com.eniversity.app.R;

/**
 * Created by shveta on 4/1/2016.
 */
public class RecommendedAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RecommendedGetSet> recommenededItems;

    private static LayoutInflater inflater = null;


    public RecommendedAdapter(Context context, ArrayList<RecommendedGetSet> recommenededItems) {
        this.context = context;
        this.recommenededItems = recommenededItems;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return recommenededItems.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView = null;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.recommended_see_all, null);

            holder.ImageViewCourseImage = (ImageView) rowView.findViewById(R.id.imageViewExploreCourse);
            holder.textViewRecommendedRcoursename = (TextView) rowView.findViewById(R.id.textViewCourseName);
            holder.textViewRecommendedRcoursePrice = (TextView) rowView.findViewById(R.id.textViewCoursePrice);
            holder.textViewRecommendedRcourseUsers = (TextView) rowView.findViewById(R.id.textViewCourseUsers);
            holder.ratingRatingBar = (RatingBar) rowView.findViewById(R.id.ratingBarProduct);

            holder.ratingRatingBar .setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });


        } else {
            holder = (ViewHolder) rowView.getTag();
        }


        holder.textViewRecommendedRcoursename.setText(recommenededItems.get(position).getCourseName());
        holder.textViewRecommendedRcoursePrice.setText(context.getResources().getString(R.string.rupees)+recommenededItems.get(position).getCoursePrice());
        holder.textViewRecommendedRcourseUsers.setText(recommenededItems.get(position).getCourseUsers());
        holder.ratingRatingBar.setRating(Float.parseFloat(recommenededItems.get(position).getRating()));

        if(recommenededItems.get(position).getCourseImage().length()>0) {
            Picasso.with(context).load(recommenededItems.get(position).getCourseImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                    .error(R.drawable.ic_no_image_availeble).into(holder.ImageViewCourseImage);
        }
        else{
            Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                    .error(R.drawable.ic_no_image_availeble).into(holder.ImageViewCourseImage);
        }
        rowView.setId(position);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, CorseInfo.class);
                intent.putExtra("courseid", recommenededItems.get(position).getCourseid());
                intent.putExtra("coursename" ,recommenededItems.get(position).getCourseName());
                context.startActivity(intent);
            }
        });

        return rowView;
    }


    public class ViewHolder {
        ImageView ImageViewCourseImage;
        TextView textViewRecommendedRcoursename;
        TextView textViewRecommendedRcoursePrice;
        TextView textViewRecommendedRcourseUsers;
        RatingBar ratingRatingBar;
    }
}
