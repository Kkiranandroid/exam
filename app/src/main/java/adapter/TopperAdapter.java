package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import get.set.TopperGetSet;
import com.eniversity.app.R;
import eniversity.com.ToppersList;

/**
 * Created by shveta on 3/24/2016.
 */
public class TopperAdapter extends BaseAdapter {

    ArrayList<TopperGetSet> categoryClass;
    Context context;


    private static LayoutInflater inflater = null;


    public TopperAdapter(Context context, ArrayList<TopperGetSet> categoryClass) {
        this.context = context;
        this.categoryClass = categoryClass;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  categoryClass.size();
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


        View rowView = null;
        final ViewHolder holder;

        {


            if (rowView == null) {
                holder =  new ViewHolder();
                rowView = inflater.inflate(R.layout.topper_layout, null);
             holder.imageViewTopper = (ImageView) rowView.findViewById(R.id.imageViewTopper);
                holder.textViewSubscriber1= (TextView) rowView.findViewById(R.id.textViewSubscriber1);
                holder.textViewCourseName = (TextView) rowView.findViewById(R.id.textViewCourseName);
                holder.textViewSubscriber = (TextView) rowView.findViewById(R.id.textViewSubscriber);
                holder.textViewname = (TextView) rowView.findViewById(R.id.textViewname);
               holder.textViewpercentage= (TextView) rowView.findViewById(R.id.textViewpercentage);
            } else {
               holder = (ViewHolder) rowView.getTag();
            }
            holder.textViewCourseName.setText(categoryClass.get(position).getTopperCourseName());
            //holder.textViewSubscriber.setText(categoryClass.get(position).getTopperCourseUsers());
            if(categoryClass.get(position).getTopperCourseUsers().equals("0")){
                holder.textViewSubscriber.setText("no users yet");
                holder.textViewSubscriber.setTextColor(context.getResources().getColor(R.color.DarkGray));
                holder.textViewSubscriber1.setVisibility(View.GONE);
                holder.textViewname.setVisibility(View.GONE);
                holder.textViewpercentage.setVisibility(View.GONE);
            }
            else{
                holder.textViewname.setText(categoryClass.get(position).getTopperName());
                holder.textViewSubscriber.setText(categoryClass.get(position).getTopperCourseUsers());
                if (categoryClass.get(position).getTopperPercentage().equals("")) {
                    holder.textViewpercentage.setVisibility(View.GONE);
                }
                else {
                    holder.textViewpercentage.setText(categoryClass.get(position).getTopperPercentage() + "%");
                }
            }
          if (categoryClass.get(position).getTopperCourseIcon().length()>0) {

                Log.i("the response is ", categoryClass.get(position).getTopperCourseIcon().toString());
                Picasso.with(context).load(categoryClass.get(position).getTopperCourseIcon()).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewTopper);
        }
          else
            {
                Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewTopper);
            }
            rowView.setId(position);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(categoryClass.get(position).getTopperCourseUsers().equals("0")){

                    }
                    else {
                        Intent intent = new Intent(context, ToppersList.class);

                        String name = categoryClass.get(position).getTopperCourseName();
                        String Courseid = categoryClass.get(position).getTopperCourseId();
                        intent.putExtra("name", name);
                        intent.putExtra("Courseid", Courseid);

                        context.startActivity(intent);
                    }
                }
            });
        }
        return rowView;
    }



    public class ViewHolder
    {

TextView textViewSubscriber1;
        TextView textViewCourseName;
        ImageView imageViewTopper;
        TextView textViewSubscriber;
        TextView textViewpercentage;
        TextView textViewname;




    }

}