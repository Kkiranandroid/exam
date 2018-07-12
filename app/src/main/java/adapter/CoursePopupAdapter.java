package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import get.set.CoursePopupGetSet;
import get.set.TopperGetSet;

import com.eniversity.app.R;

/**
 * Created by kirankumar on 4/25/2016.
 */
public class CoursePopupAdapter extends BaseAdapter {
    ArrayList<CoursePopupGetSet> coursePopupAdapters;
    Context context;


    private static LayoutInflater inflater = null;

    public CoursePopupAdapter(Context context, ArrayList<CoursePopupGetSet> coursePopupAdapters) {
        this.context = context;
        this.coursePopupAdapters = coursePopupAdapters;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return coursePopupAdapters.size();
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
        View rowView = null;
        final ViewHolder holder;


        try {

            if (rowView == null) {
                holder = new ViewHolder();
                rowView = inflater.inflate(R.layout.courselist_layout, null);

                holder.textViewDisplayCourse = (TextView) rowView.findViewById(R.id.textViewDisplayCourse);
                holder.imageViewSelectedCourse = (ImageView) rowView.findViewById(R.id.imageViewSelectedCourse);

            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            if (coursePopupAdapters.get(position).getIsSelected()) {
                holder.imageViewSelectedCourse.setVisibility(View.VISIBLE);
                holder.textViewDisplayCourse.setTextColor(context.getResources().getColor(R.color.primaryColor));
            } else {
                holder.imageViewSelectedCourse.setVisibility(View.GONE);
            }

            holder.textViewDisplayCourse.setText(coursePopupAdapters.get(position).getCategoryname());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowView;
    }

    public class ViewHolder {
        TextView textViewDisplayCourse;
        ImageView imageViewSelectedCourse;

    }

}
