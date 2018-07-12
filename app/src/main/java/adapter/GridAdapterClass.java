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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import get.set.CategoryGesetClassNew;
import get.set.CategoryGetSetClass;
import eniversity.com.BrowseCatalogClass;
import eniversity.com.CorseInfo;
import com.eniversity.app.R;

/**
 * Created by shveta on 3/22/2016.
 */
public class GridAdapterClass extends BaseAdapter {

    ArrayList<CategoryGetSetClass> categoryClass;
    ArrayList<CategoryGesetClassNew> categoryClassnew;
    Context context;
    String id;


    private static LayoutInflater inflater = null;
    String fromClass;


    public GridAdapterClass(Context context, ArrayList<CategoryGetSetClass> categoryClass, String fromClass) {
        this.context = context;
        this.categoryClass = categoryClass;
        this.fromClass = fromClass;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return categoryClass.size();
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

        ViewHolder1 holder1 =  new ViewHolder1();
        View rowView = null;
try {
    if (fromClass.equals("exploreclass")) {


        if (rowView == null) {
            rowView = inflater.inflate(R.layout.custom_grid_layout, null);
            holder.imageViewExploreCourse = (ImageView) rowView.findViewById(R.id.imageViewExploreCourse);
            holder.textViewCourseName = (TextView) rowView.findViewById(R.id.textViewCourseName);
            holder.textViewCoursePrice = (TextView) rowView.findViewById(R.id.textViewCoursePrice);
            holder.textViewCourseUsers = (TextView) rowView.findViewById(R.id.textViewCourseUsers);
            holder.textviewuserName= (TextView) rowView.findViewById(R.id.textviewuserName);
            holder.ratingBarProduct = (RatingBar) rowView.findViewById(R.id.ratingBarProduct);
            holder.userslayout = (RelativeLayout) rowView.findViewById(R.id.userslayout);
            holder.ratingBarProductDemoExplorenew = (RatingBar) rowView.findViewById(R.id.ratingBarProductDemoExplorenew);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        if (CommonMethods.userid.length() == 0) {
            holder.ratingBarProduct.setVisibility(View.GONE);
           // holder.userslayout.setVisibility(View.GONE);
            holder.textViewCourseUsers.setVisibility(View.GONE);
            holder.textviewuserName.setVisibility(View.GONE);
        } else {
            holder.ratingBarProduct.setVisibility(View.VISIBLE);
            //holder.userslayout.setVisibility(View.VISIBLE);
            holder.textViewCourseUsers.setVisibility(View.VISIBLE);
            holder.textviewuserName.setVisibility(View.VISIBLE);
            holder.ratingBarProductDemoExplorenew.setVisibility(View.GONE);
        }
        if (categoryClass.get(position).getCategoryImage().length() > 0) {
                Picasso.with(context).load(categoryClass.get(position).getCategoryImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourse);

        } else {
            Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                    .error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourse);
        }
        holder.textViewCourseName.setText(categoryClass.get(position).getCategoryName());
        holder.textViewCoursePrice.setText(categoryClass.get(position).getCategorytotalfees());
        holder.textViewCourseUsers.setText(categoryClass.get(position).getCategoryUsers() );
        holder.ratingBarProduct.setRating(Float.parseFloat(categoryClass.get(position).getCategoryRatings()));
        holder.ratingBarProductDemoExplorenew.setRating(Float.parseFloat(categoryClass.get(position).getCategoryRatings()));


        holder.ratingBarProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        holder.ratingBarProductDemoExplorenew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        rowView.setId(position);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CorseInfo.class);
                i.putExtra("courseid", categoryClass.get(position).getCategoryId());
                i.putExtra("coursename", categoryClass.get(position).getCategoryName());
                context.startActivity(i);
            }
        });

    } else {
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.custom_browse_category_lsi, null);


            holder1.imageViewExploreCourse = (ImageView) rowView.findViewById(R.id.imageViewExploreCourse);
            holder1.textViewCourseName = (TextView) rowView.findViewById(R.id.textViewCourseName);
            holder1.textViewCoursePriceProduct = (TextView) rowView.findViewById(R.id.textViewCoursePriceProduct);
            holder1.textViewCourseUsers = (TextView) rowView.findViewById(R.id.textViewCourseUsers);
            holder1.usersrelativelayout = (RelativeLayout) rowView.findViewById(R.id.usersrelativelayout);

        } else {
            holder1 = (ViewHolder1) rowView.getTag();
        }
        if (CommonMethods.userid.length() == 0) {
            holder1.usersrelativelayout.setVisibility(View.GONE);
        } else {
            holder1.usersrelativelayout.setVisibility(View.VISIBLE);
        }


        if (categoryClass.get(position).getCategoryImage().length() > 0) {



                Picasso.with(context).load(categoryClass.get(position).getCategoryImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                        .error(R.drawable.ic_no_image_availeble).into(holder1.imageViewExploreCourse);

        } else {
            Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                    .error(R.drawable.ic_no_image_availeble).into(holder1.imageViewExploreCourse);
        }

        holder1.textViewCourseName.setText(categoryClass.get(position).getCategoryName());
        holder1.textViewCourseUsers.setText(categoryClass.get(position).getCategoryUsers());
        holder1.textViewCoursePriceProduct.setText(categoryClass.get(position).getCategoryNoOfCourse());

        rowView.setId(position);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrowseCatalogClass.class);
                intent.putExtra("categoryId", categoryClass.get(position).getCategoryId());
                intent.putExtra("searchtext", "");
                intent.putExtra("coursename", categoryClass.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });

    }
}
catch (Exception e){
    e.printStackTrace();
}
        return rowView;
    }


    public class ViewHolder
    {
        ImageView imageViewExploreCourse;
        TextView textViewCourseName;
        TextView textViewCoursePrice;
        RatingBar ratingBarProduct;
        TextView textViewCourseUsers;
        RatingBar ratingBarProductDemoExplorenew;
        RelativeLayout userslayout;
        TextView textviewuserName;
    }


    public class ViewHolder1
    {
        ImageView imageViewExploreCourse;
        TextView textViewCourseName;
        TextView textViewCoursePriceProduct;
        TextView textViewCourseUsers;
        RelativeLayout usersrelativelayout;
    }
}
