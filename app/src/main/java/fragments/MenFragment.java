package fragments;

import get.set.CategoryGetSetClass;
import eniversity.com.CorseInfo;
import com.eniversity.app.R;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class MenFragment extends Fragment {

    private ImageView imageViewExploreCourse;
    private TextView textViewCourseName;

    private TextView textViewCoursePrice;

    private LinearLayout linearLayoutCouesrDetails;
    private TextView textViewCourseUsers;

    private RatingBar ratingBarProduct;
    private ArrayList<CategoryGetSetClass> items = null;
    private int arrayPostion = 0;


    public MenFragment(ArrayList<CategoryGetSetClass> items, int postion) {

        this.items = items;
        this.arrayPostion = postion;
    }

    public void imageFragmentList(ArrayList<CategoryGetSetClass> iteems) {
        this.items = items;
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_grid_layout, container, false);
        imageViewExploreCourse = (ImageView) v.findViewById(R.id.imageViewExploreCourse);
        textViewCourseName = (TextView) v.findViewById(R.id.textViewCourseName);
        textViewCoursePrice = (TextView) v.findViewById(R.id.textViewCoursePrice);

        linearLayoutCouesrDetails = (LinearLayout) v.findViewById(R.id.linearLayoutCouesrDetails);

        linearLayoutCouesrDetails.setOnClickListener(DetailsListener);
        String imageUrl = "";
        imageUrl = items.get(arrayPostion).getCategoryImage();

if(imageUrl.length()>0) {
    Picasso.with(getActivity())
            .load(imageUrl).placeholder(R.drawable.ic_no_image_availeble)
            .error(R.drawable.ic_no_image_availeble)
            .into(imageViewExploreCourse);

}
        String categoryTitle = "";
        categoryTitle = items.get(arrayPostion).getCategoryTitle();
        textViewCourseName.setText(categoryTitle);


        textViewCoursePrice = (TextView) v.findViewById(R.id.textViewCoursePrice);
        String categoryPrice = "";
        categoryPrice =  items.get(arrayPostion).getCategorytotalfees();
        textViewCoursePrice.setText("Rs. " + categoryPrice);

        textViewCourseUsers = (TextView) v.findViewById(R.id.textViewCourseUsers);

        String  courseUsers = "";
        courseUsers  = items.get(arrayPostion).getCategoryUsers();
        textViewCourseUsers.setText(courseUsers + " Users");


        ratingBarProduct = (RatingBar) v.findViewById(R.id.ratingBarProduct);

        String ratings = "";

        ratings =items.get(arrayPostion).getCategoryRatings();
        ratingBarProduct.setRating(Float.parseFloat(ratings));

        ratingBarProduct.setEnabled(false);

        return v;
    }

    private OnClickListener DetailsListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(getActivity(), CorseInfo.class);
            i.putExtra("courseid", items.get(arrayPostion).getCategoryId());
            startActivity(i);
        }
    };




}
