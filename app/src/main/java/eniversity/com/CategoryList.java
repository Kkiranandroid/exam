package eniversity.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.NoConnectionError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eniversity.app.R;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CircleTransform;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import Commmon.Methods.StretchedListView;
import data.base.CategoryTable;
import get.set.CategoryGetSet;
import login.page.LoginMainPage;
import login.page.ProfilePictureClass;

/**
 * Created by soumyay on 7/25/2016.
 */
public class CategoryList extends AppCompatActivity {

    ListView categoryListView;
    ArrayList<CategoryGetSet> categoryGetSetArrayList;
    TextView categoryNameTextView;
    TextView categoryIdTextView;
    CheckBox categoryNameCheckBox;
    CategoryAdapter categoryAdapter;
    Button buttonSelected;
    Toolbar toolbar;
    ImageView imageViewSearchIcon;
    ImageView imageViewBackIcon;
    TextView textViewHeading;
    CoordinatorLayout coordinatorLayout;
    LinearLayout MainLinearLayout;
    ProgressBar progressBarCategory;
    private static long back_pressed_time;
    private static long PERIOD = 2000;
    TextView txt_dia;
    Button buttonOK;
    TextView selectCatTextView;
    //navigation
    private View includelayoutnavigationDrawer;
    private ImageView imageViewProfilePic;
    private TextView textViewUserName;


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_category);
        try{

            includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);

            imageViewProfilePic = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.imageViewProfilePic);
            textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);

           /* Glide.get(MyApplication).clearMemory();
            Glide.get(MyApplication.getInstance()).clearDiskCache();*/
        categoryListView = (ListView) findViewById(R.id.categoryListView);
        buttonSelected = (Button) findViewById(R.id.buttonSelected);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        MainLinearLayout = (LinearLayout) findViewById(R.id.MainLinearLayout);
        selectCatTextView = (TextView) findViewById(R.id.selectCatTextView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        //imageViewSearchIcon= (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        progressBarCategory = (ProgressBar) findViewById(R.id.progressBarCategory);
        //imageViewSearchIcon.setVisibility(View.GONE);
        textViewHeading.setText("Categories");
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(CategoryList.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_alert_dialog);
                txt_dia = (TextView) dialog.findViewById(R.id.txt_dia);
                txt_dia.setText("Are you sure you want to exit?");
                buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                dialog.show();
            }
        });
        getCategorires();

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryNameTextView = (TextView) view.getTag(R.id.categoryTextView);
                categoryNameCheckBox = (CheckBox) view.getTag(R.id.categoryCheckBox);
                categoryIdTextView = (TextView) view.getTag(R.id.categoryIdTextView);

                if (categoryNameCheckBox.isChecked() == true) {
                    categoryNameCheckBox.setChecked(false);


                } else {
                    categoryNameCheckBox.setChecked(true);

                }
                if (categoryNameCheckBox.isChecked()) {
                    for (int i = 0; i < categoryGetSetArrayList.size(); i++) {
                        CategoryGetSet categoryGetSet = new CategoryGetSet();
                        categoryGetSet.setCategory_name(categoryNameTextView.getText().toString());
                        categoryGetSet.setCategory_id(categoryIdTextView.getText().toString());
                        categoryGetSet.setIsSelected(true);
                        categoryGetSetArrayList.set(position, categoryGetSet);

                    }
                    categoryAdapter.notifyDataSetChanged();

                } else {
                    for (int i = 0; i < categoryGetSetArrayList.size(); i++) {
                        CategoryGetSet categoryGetSet = new CategoryGetSet();
                        categoryGetSet.setCategory_name(categoryNameTextView.getText().toString());
                        categoryGetSet.setCategory_id(categoryIdTextView.getText().toString());
                        categoryGetSet.setIsSelected(false);
                        categoryGetSetArrayList.set(position, categoryGetSet);

                    }
                    categoryAdapter.notifyDataSetChanged();

                }


            }
        });

        buttonSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addCategories(categoryGetSetArrayList);
                String categoryIds = "";
                for (int i = 0; i < categoryGetSetArrayList.size(); i++) {
                    if (categoryGetSetArrayList.get(i).isSelected() == true) {

                        categoryIds = categoryIds + categoryGetSetArrayList.get(i).getCategory_id() + ",";

                    } else {
                        //categoryIds =categoryGetSetArrayList.get(i).getCategory_id();

                    }

                }
                if (categoryIds.equals("")) {
                    Toast.makeText(CategoryList.this, "please select atleast one category", Toast.LENGTH_SHORT).show();
                } else {
                    String categoryId2 = categoryIds.substring(0, categoryIds.length() - 1);
                    //SharedPreferences sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, MODE_PRIVATE);
                    //SharedPreferences.Editor editor=sharedPreferences.edit();
                    // editor.putString(CommonMethods.CATEGORY_ID,categoryId2);
                    // editor.commit();
                    CommonMethods.category_Id = categoryId2;
                    CategoryTable categoryTable = new CategoryTable(CategoryList.this);
                    categoryTable.open();
                    categoryTable.addlogindetails(categoryId2);
                    categoryTable.close();

                    addCategories(categoryId2);

                    Intent intent = new Intent(CategoryList.this, LoginMainPage.class);
                    startActivity(intent);
                }

                //Toast.makeText(CategoryList.this,categoryId2,Toast.LENGTH_SHORT).show();
                // Intent intent= new Intent(CategoryList.this, LoginMainPage.class);
                // startActivity(intent);


            }
        });

            ProfilePictureClass serverData = new ProfilePictureClass(CategoryList.this);
            String dealerName;
            final String path;
            if (serverData.getCurrentUserName().length() > 0) {

                dealerName = serverData.getCurrentUserName();
            } else {
                dealerName = CommonMethods.user_name;
            }


            if((dealerName.toString().length())>12){
                textViewUserName.setText("Welcome\n" + dealerName);
            }else {
                textViewUserName.setText("Welcome " + dealerName);
            }



            path = serverData.getCurrentProfilePic();

            SharedPreferences sharedPreferences1=getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences1.edit();
            editor1.putString("profile_imageurl",path);
            editor1.putString("username",dealerName);
            editor1.commit();
            CommonMethods.profile_image_url=sharedPreferences1.getString("profile_imageurl","");
            CommonMethods.userName=sharedPreferences1.getString("username","");


            if (path != null) {
                if (path.length() > 0) {
                    Glide.with(CategoryList.this).load(path)
                            // .skipMemoryCache(true)
                            .transform(new CircleTransform(CategoryList.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);


                } else {
                    Picasso.with(CategoryList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            }
            else{
                Picasso.with(CategoryList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);


            }

    }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getCategorires(){


        try {
            progressBarCategory.setVisibility(View.VISIBLE);
            MainLinearLayout.setVisibility(View.GONE);
            JSONObject categoryObject= new JSONObject();
            categoryObject.put("action","getcategory");
            categoryObject.put("userid",CommonMethods.userid);
            RequestQueue requestQueue = Volley.newRequestQueue(CategoryList.this);
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, categoryObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.v("response is:",response.toString());
                    try{
                        categoryGetSetArrayList= new ArrayList<>();
                        String categoryname=response.getString("category");
                        JSONArray array=new JSONArray(categoryname);


                        for(int i=0;i<array.length();i++){
                            CategoryGetSet categoryGetSet= new CategoryGetSet();
                            if(array.getJSONObject(i).getString("status").equals("deleted")||
                                    array.getJSONObject(i).getString("status").equals("blocked")){
                                categoryGetSet.setStatus(array.getJSONObject(i).getString("status"));
                            }
                            else {
                                categoryGetSet.setCategory_id(array.getJSONObject(i).getString("categoryid"));
                                categoryGetSet.setCategory_name(array.getJSONObject(i).getString("categoryname"));
                                categoryGetSet.setIsSelected(false);
                                categoryGetSet.setStatus(array.getJSONObject(i).getString("status"));
                            }
                            categoryGetSetArrayList.add(categoryGetSet);



                        }
                        if(categoryGetSetArrayList.size()==1){
                            if(categoryGetSetArrayList.get(0).getStatus().equals("deleted")
                                    ||categoryGetSetArrayList.get(0).getStatus().equals("blocked")){
                                final Dialog dialog= new Dialog(CategoryList.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message= (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been "+categoryGetSetArrayList.get(0).getStatus());
                                Button buttonOK=(Button)dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(CategoryList.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();
                            }
                            else{
                                categoryAdapter = new CategoryAdapter(CategoryList.this,categoryGetSetArrayList);
                                categoryListView.setAdapter(categoryAdapter);
                                setListViewHeightBasedOnItems(categoryListView);

                                progressBarCategory.setVisibility(View.GONE);
                                MainLinearLayout.setVisibility(View.VISIBLE);
                                buttonSelected.setVisibility(View.VISIBLE);
                                selectCatTextView.setVisibility(View.VISIBLE);
                            }

                        }
                        else {


                            categoryAdapter = new CategoryAdapter(CategoryList.this, categoryGetSetArrayList);
                            categoryListView.setAdapter(categoryAdapter);
                            setListViewHeightBasedOnItems(categoryListView);

                            progressBarCategory.setVisibility(View.GONE);
                            MainLinearLayout.setVisibility(View.VISIBLE);
                            buttonSelected.setVisibility(View.VISIBLE);
                            selectCatTextView.setVisibility(View.VISIBLE);
                        }


                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        progressBarCategory.setVisibility(View.GONE);
                        MainLinearLayout.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, CategoryList.this.getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCategorires();
                                    }
                                });
                        snackbar.show();
                        Log.v("error", error.toString());
                        error.printStackTrace();
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCategorires();
                                    }
                                });
                        snackbar.show();
                        progressBarCategory.setVisibility(View.GONE);
                        MainLinearLayout.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                    }
                }

            });

            requestQueue.add(customRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }
    public void addCategories(String categoryId){

        try{
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "addrecommendedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("categoryid", categoryId/*"Advanced"*/);


            RequestQueue requestQueue = Volley.newRequestQueue(CategoryList.this);
            CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        if(response.getJSONArray("Course").getJSONObject(0).getString("status").equals("deleted")||response.getJSONArray("Course").getJSONObject(0).getString("status").equals("blocked")){
                            final Dialog dialog = new Dialog(CategoryList.this);
                            dialog.setTitle(null);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setContentView(R.layout.custom_alert_dialog);
                            TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                            message.setText("Your account has been " + response.getJSONArray("Course").getJSONObject(0).getString("status"));
                            Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                            buttonOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    new LogOut(CategoryList.this).execute();
                                    finish();

                                }
                            });
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("error", "error");
                    error.printStackTrace();

                }
            });
            requestQueue.add(users);
        }
        catch (Exception e){
e.printStackTrace();
        }



    }

    public class CategoryAdapter extends ArrayAdapter<CategoryGetSet>
    {
        Activity context;
        ArrayList<CategoryGetSet>categoryGetSetArrayList;
        public CategoryAdapter(Activity context, ArrayList<CategoryGetSet> categoryGetSetArrayList) {
            super(context, R.layout.category_content,categoryGetSetArrayList);
            this.context=context;
            this.categoryGetSetArrayList=categoryGetSetArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=new Holder();


            if(convertView==null){
                LayoutInflater inflater=context.getLayoutInflater();
                convertView=inflater.inflate(R.layout.category_content,null);
                holder.categoryTextView= (TextView) convertView.findViewById(R.id.categoryTextView);
                holder.categoryCheckBox= (CheckBox) convertView.findViewById(R.id.categoryCheckBox);
                holder.categoryIdTextView= (TextView) convertView.findViewById(R.id.categoryIdTextView);
                convertView.setTag(R.id.categoryCheckBox,holder.categoryCheckBox);
                convertView.setTag(R.id.categoryTextView,holder.categoryTextView);
                convertView.setTag(R.id.categoryIdTextView,holder.categoryIdTextView);
                convertView.setTag(holder);

            }
            else{
                holder= (Holder) convertView.getTag();
            }
            holder.categoryCheckBox.setTag(position);
            holder.categoryTextView.setText(categoryGetSetArrayList.get(position).getCategory_name());
            holder.categoryIdTextView.setText(categoryGetSetArrayList.get(position).getCategory_id());
            holder.categoryCheckBox.setChecked(categoryGetSetArrayList.get(position).isSelected());

            return convertView;
        }
    }
    public  class Holder{
        TextView categoryTextView;
        CheckBox categoryCheckBox;
        TextView categoryIdTextView;
    }

    @Override
    public void onBackPressed() {
        try {
            Dialog dialog = new Dialog(CategoryList.this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert_dialog);
            txt_dia = (TextView) dialog.findViewById(R.id.txt_dia);
            txt_dia.setText("Are you sure you want to exit?");
            buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            dialog.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Intent intent= new Intent(CategoryList.this,MainActivity.class);
       // startActivity(intent);

    }
}
