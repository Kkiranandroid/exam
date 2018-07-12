package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import get.set.HomeCategoryGetSet;
import eniversity.com.BrowseCatalogClass;
import eniversity.com.CorseInfo;

import com.eniversity.app.R;

/**
 * Created by kirankumar on 4/7/2016.
 */
public class DemoCatogoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<HomeCategoryGetSet> homecatogorysize;
    private LayoutInflater inflater;
    private FragmentManager fragmanager;
    private String searchdata;
    private EditText editTextSearch;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView imageViewSearchIcon;
    private TextView textViewHeading;


    public DemoCatogoryAdapter(Context context, ArrayList<HomeCategoryGetSet> homecatogorysize, FragmentManager fragmanager,
                               String searchdata,EditText editTextSearch,ActionBarDrawerToggle drawerToggle,ImageView imageViewSearchIcon,TextView textViewHeading) {
        this.context = context;
        this.homecatogorysize = homecatogorysize;
        this.searchdata = searchdata;
        this.editTextSearch = editTextSearch;
        this.drawerToggle = drawerToggle;
        this.imageViewSearchIcon = imageViewSearchIcon;
        this.textViewHeading = textViewHeading;
        this.fragmanager = fragmanager;
    }

    @Override
    public int getCount() {
        return homecatogorysize.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView textViewSeeAllCategories;
        ImageView imageViewExploreCourseDemoExplore;
        ImageView imageViewExploreCourseNewDemoExplore;
        TextView textViewCourseNameDemoExplore;
        TextView textViewCourseNameNewDemoExplore;
        TextView textViewCoursePriceDemoExplore;
        TextView textViewCoursePriceNewDemoExplore;
        TextView textViewCourseUsersDemoExplore;
        TextView textViewCourseUsersNewDemoExplore;
        RatingBar ratingBarProductDemoExplore;
        RatingBar ratingBarProductDemoExplorenew;
        RatingBar ratingBarProductNewDemoExplore;
        RatingBar ratingBarProductNewDemoExplorenew;
        TextView textViewrecomendendCourse;
        LinearLayout card_viewNew;
        //LinearLayout linearLayoutCouesrDetailsNewDemo;
        LinearLayout card_viewDemo;
        LinearLayout linearLayoutCouesrDetailsDemo;
        TextView textViewNoMoreRecordFound;
        RelativeLayout userslayout;
        RelativeLayout userslayoutNew;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View rowView = null;
        try {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (rowView == null) {
                holder = new ViewHolder();

                rowView = inflater.inflate(R.layout.viewcatogorylayout, null);



                holder.textViewSeeAllCategories = (TextView) rowView.findViewById(R.id.textViewSeeAllCategories);

                holder.imageViewExploreCourseDemoExplore = (ImageView) rowView.findViewById(R.id.imageViewExploreCourseDemoExplore);
                holder.imageViewExploreCourseNewDemoExplore = (ImageView) rowView.findViewById(R.id.imageViewExploreCourseNewDemoExplore);

                // holder.card_viewNew= (CardView) rowView.findViewById(R.id.card_viewNew);
                holder.card_viewNew = (LinearLayout) rowView.findViewById(R.id.linearLayoutCouesrDetailsNewDemo);
                // holder.card_viewDemo= (CardView) rowView.findViewById(R.id.card_viewDemo);
                holder.card_viewDemo = (LinearLayout) rowView.findViewById(R.id.linearLayoutCouesrDetailsDemo);

                holder.textViewCourseNameDemoExplore = (TextView) rowView.findViewById(R.id.textViewCourseNameDemoExplore);
                holder.textViewCourseNameNewDemoExplore = (TextView) rowView.findViewById(R.id.textViewCourseNameNewDemoExplore);

                holder.textViewCoursePriceDemoExplore = (TextView) rowView.findViewById(R.id.textViewCoursePriceDemoExplore);
                holder.textViewCoursePriceNewDemoExplore = (TextView) rowView.findViewById(R.id.textViewCoursePriceNewDemoExplore);

                holder.textViewCourseUsersDemoExplore = (TextView) rowView.findViewById(R.id.textViewCourseUsersDemoExplore);
                holder.userslayout = (RelativeLayout) rowView.findViewById(R.id.userslayout);
                holder.userslayoutNew = (RelativeLayout) rowView.findViewById(R.id.userslayoutNew);
                holder.textViewCourseUsersNewDemoExplore = (TextView) rowView.findViewById(R.id.textViewCourseUsersNewDemoExplore);
                holder.ratingBarProductDemoExplore = (RatingBar) rowView.findViewById(R.id.ratingBarProductDemoExplore);
                holder.ratingBarProductDemoExplorenew = (RatingBar) rowView.findViewById(R.id.ratingBarProductDemoExplorenew);
                holder.ratingBarProductNewDemoExplore = (RatingBar) rowView.findViewById(R.id.ratingBarProductNewDemoExplore);
                holder.ratingBarProductNewDemoExplorenew = (RatingBar) rowView.findViewById(R.id.ratingBarProductNewDemoExplorenew);

                holder.textViewrecomendendCourse = (TextView) rowView.findViewById(R.id.textViewrecomendendCourse);
                holder.textViewNoMoreRecordFound = (TextView) rowView.findViewById(R.id.textViewNoMoreRecordFound);

                holder.ratingBarProductDemoExplore.setFocusable(false);
                holder.ratingBarProductDemoExplorenew.setFocusable(false);
                holder.ratingBarProductDemoExplorenew.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                holder.ratingBarProductDemoExplore.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                holder.ratingBarProductNewDemoExplore.setFocusable(false);
                holder.ratingBarProductNewDemoExplorenew.setFocusable(false);
                holder.ratingBarProductNewDemoExplorenew.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                holder.ratingBarProductNewDemoExplore.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }



           /* SpannableString content = new SpannableString("See All");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.textViewSeeAllCategories.setText(content);*/
            holder.textViewrecomendendCourse.setText(homecatogorysize.get(position).getCategroyName());
            if (CommonMethods.userid.length() == 0) {
                holder.userslayout.setVisibility(View.GONE);
                holder.userslayoutNew.setVisibility(View.GONE);
                holder.ratingBarProductDemoExplore.setVisibility(View.GONE);
                holder.ratingBarProductNewDemoExplore.setVisibility(View.GONE);
                holder.textViewCourseUsersDemoExplore.setVisibility(View.INVISIBLE);
                holder.textViewCourseUsersNewDemoExplore.setVisibility(View.INVISIBLE);
            } else {
                holder.ratingBarProductDemoExplorenew.setVisibility(View.GONE);
                holder.ratingBarProductNewDemoExplorenew.setVisibility(View.GONE);
            }

            if (homecatogorysize.get(position).getMenList().size() > 1) {
                holder.textViewCourseNameDemoExplore.setText(homecatogorysize.get(position).getMenList().get(0).getCategoryTitle());
                holder.textViewCoursePriceDemoExplore.setText(homecatogorysize.get(position).getMenList().get(0).getCategorytotalfees());
                holder.textViewCourseUsersDemoExplore.setText( homecatogorysize.get(position).getMenList().get(0).getCategoryUsers());
                holder.ratingBarProductDemoExplore.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(0).getCategoryRatings()));
                Log.v("rating1", "" + Float.parseFloat(homecatogorysize.get(position).getMenList().get(0).getCategoryRatings()));
                holder.ratingBarProductDemoExplorenew.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(0).getCategoryRatings()));

                if (homecatogorysize.get(position).getMenList().get(0).getCategoryImage().length() > 0) {



                        Picasso.with(context).load(homecatogorysize.get(position).getMenList().get(0).getCategoryImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                .error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseDemoExplore);

                } else {
                    Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble).error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseDemoExplore);

                }
                holder.textViewCourseNameNewDemoExplore.setText(homecatogorysize.get(position).getMenList().get(1).getCategoryTitle());
                holder.textViewCoursePriceNewDemoExplore.setText(homecatogorysize.get(position).getMenList().get(1).getCategorytotalfees());
                holder.textViewCourseUsersNewDemoExplore.setText(homecatogorysize.get(position).getMenList().get(1).getCategoryUsers());
                holder.ratingBarProductNewDemoExplore.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(1).getCategoryRatings()));
                holder.ratingBarProductNewDemoExplorenew.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(1).getCategoryRatings()));
                Log.v("rating2", "" + Float.parseFloat(homecatogorysize.get(position).getMenList().get(1).getCategoryRatings()));
                if (homecatogorysize.get(position).getMenList().get(1).getCategoryImage().length() > 0) {

                    Picasso.with(context).load(homecatogorysize.get(position).getMenList().get(1).getCategoryImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                            .error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseNewDemoExplore);
                } else {
                    Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble).error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseNewDemoExplore);

                }
            } else if (homecatogorysize.get(position).getMenList().size() == 1) {
                holder.textViewCourseNameDemoExplore.setText(homecatogorysize.get(position).getMenList().get(0).getCategoryTitle());
                holder.textViewCoursePriceDemoExplore.setText(homecatogorysize.get(position).getMenList().get(0).getCategorytotalfees());
                holder.textViewCourseUsersDemoExplore.setText(homecatogorysize.get(position).getMenList().get(0).getCategoryUsers());
                holder.ratingBarProductDemoExplore.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(0).getCategoryRatings()));
                holder.ratingBarProductDemoExplorenew.setRating(Float.parseFloat(homecatogorysize.get(position).getMenList().get(0).getCategoryRatings()));
                if (homecatogorysize.get(position).getMenList().get(0).getCategoryImage().length() > 0) {


                        Picasso.with(context).load(homecatogorysize.get(position).getMenList().get(0).getCategoryImage()/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                .error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseDemoExplore);



                } else {

                    Picasso.with(context).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble).error(R.drawable.ic_no_image_availeble).into(holder.imageViewExploreCourseNewDemoExplore);

                }
                holder.card_viewNew.setVisibility(View.INVISIBLE);
                // holder.textViewSeeAllCategories.setVisibility(View.GONE);

            } else if (homecatogorysize.get(position).getMenList().size() == 0) {
                holder.card_viewNew.setVisibility(View.GONE);
                holder.card_viewDemo.setVisibility(View.GONE);
                holder.textViewNoMoreRecordFound.setVisibility(View.VISIBLE);
                // holder.textViewSeeAllCategories.setVisibility(View.GONE);
            }

            if (Integer.parseInt(homecatogorysize.get(position).getCoursecount()) > 2) {
                holder.textViewSeeAllCategories.setVisibility(View.VISIBLE);
            } else {
                holder.textViewSeeAllCategories.setVisibility(View.GONE);
            }

            holder.textViewSeeAllCategories = (TextView) rowView.findViewById(R.id.textViewSeeAllCategories);


            holder.textViewSeeAllCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, BrowseCatalogClass.class);
                    editTextSearch.setText("");
                    editTextSearch.setVisibility(View.GONE);
                    drawerToggle.setDrawerIndicatorEnabled(true);
                    textViewHeading.setVisibility(View.VISIBLE);
                    imageViewSearchIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_search_white));
                    i.putExtra("coursename", homecatogorysize.get(position).getCategroyName());
                    i.putExtra("searchtext", searchdata);
                    i.putExtra("categoryId", homecatogorysize.get(position).getCategoryId());
                    context.startActivity(i);
                }
            });
            holder.card_viewDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CorseInfo.class);
                    intent.putExtra("courseid", homecatogorysize.get(position).getMenList().get(0).getCourseId());
                    intent.putExtra("coursename", homecatogorysize.get(position).getMenList().get(0).getCategoryTitle());

                    context.startActivity(intent);
                }
            });

            holder.card_viewNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CorseInfo.class);
                    intent.putExtra("courseid", homecatogorysize.get(position).getMenList().get(1).getCourseId());
                    intent.putExtra("coursename", homecatogorysize.get(position).getMenList().get(1).getCategoryTitle());
                    context.startActivity(intent);
                }
            });

         /*   if(homecatogorysize.get(position).getMenList().size()<=2){
                holder.textViewSeeAllCategories.setVisibility(View.GONE);
            }*/

        } catch (Exception t) {
            t.printStackTrace();
            Log.getStackTraceString(t);
        }
        return rowView;
    }
}
