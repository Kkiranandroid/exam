package eniversity.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.eniversity.app.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import Commmon.Methods.StretchedListView;
import get.set.CategoryGetSet;
import login.page.LoginMainPage;

/**
 * Created by soumyay on 7/25/2016.
 */
public class EditRecommendedCourses extends AppCompatActivity {
    ListView categoryListView;
    ArrayList<CategoryGetSet>categoryGetSetArrayList;
    ArrayList<CategoryGetSet> categoryGetSetArrayList2;
    CategoryAdapter categoryAdapter;
    String categoryid="";
    String[] categoryarr;
    Toolbar toolbar;
    ImageView imageViewSearchIcon;
    ImageView imageViewBackIcon;

    ArrayList<String> categoryArray= new ArrayList<>();
    String returnValue="";
    String categoryNmae="";
    TextView categoryNameTextView;
    TextView categoryIdTextView;
    CheckBox categoryNameCheckBox;
    Button buttonSelected;
    TextView textViewHeading;
    TextView selectCatTextView;
    ProgressBar progressBarCategory;
    LinearLayout MainLinearLayout;


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
        categoryListView= (ListView) findViewById(R.id.categoryListView);
        buttonSelected= (Button) findViewById(R.id.buttonSelected);
        selectCatTextView= (TextView) findViewById(R.id.selectCatTextView);
        selectCatTextView.setText("My Categories");
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        textViewHeading= (TextView) toolbar.findViewById(R.id.textViewHeading);
        //imageViewSearchIcon= (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        imageViewBackIcon= (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        //imageViewSearchIcon.setVisibility(View.GONE);
        textViewHeading.setText("My Categories");
        imageViewBackIcon.setVisibility(View.VISIBLE);
        progressBarCategory= (ProgressBar) findViewById(R.id.progressBarCategory);
        MainLinearLayout= (LinearLayout) findViewById(R.id.MainLinearLayout);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EditRecommendedCourses.this, LoginMainPage.class);
                startActivity(intent);
                finish();
            }
        });

        //String returnVal=getEditRecCourses();
        getEditRecCourses();
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
                if(categoryIds.equals("")){
                    Toast.makeText(EditRecommendedCourses.this, "please select atleast one category", Toast.LENGTH_SHORT).show();
                }
                else {
                    String categoryId2 = categoryIds.substring(0, categoryIds.length() - 1);
                    addCategories(categoryId2);

                    //Toast.makeText(EditRecommendedCourses.this, categoryId2, Toast.LENGTH_SHORT).show();
                }


              // finish();


            }
        });





    }
    public void addCategories(String categoryId){

        try{
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "addrecommendedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("categoryid", categoryId/*"Advanced"*/);

            Log.v("request is:",sendObject.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(EditRecommendedCourses.this);
            CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        if(response.getJSONArray("Course").getJSONObject(0).getString("status").equals("deleted")||response.getJSONArray("Course").getJSONObject(0).getString("status").equals("blocked")){
                            final Dialog dialog = new Dialog(EditRecommendedCourses.this);
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
                                    new LogOut(EditRecommendedCourses.this).execute();
                                    finish();

                                }
                            });
                            dialog.show();
                        }
                        else{
                            Intent intent= new Intent(EditRecommendedCourses.this, LoginMainPage.class);
                            startActivity(intent);
                            finish();
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


    public void getEditRecCourses() {


        try {

            JSONObject categoryObject = new JSONObject();
            categoryObject.put("action", "editrecommendedcourses");
            categoryObject.put("userid",CommonMethods.userid);

            Log.v("request is",categoryObject.toString());
            RequestQueue requestQueue = Volley.newRequestQueue(EditRecommendedCourses.this);
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, categoryObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.v("response is:",response.toString());
                    try {

                        String reecommendedCourses=response.getString("RecommendedCourses");
                        JSONArray array=new JSONArray(reecommendedCourses);
                        for(int i=0;i<array.length();i++){
                            //CategoryGetSet categoryGetSet= new CategoryGetSet();
                            categoryid=array.getJSONObject(i).getString("categoryid");
                            categoryNmae=array.getJSONObject(i).getString("categoryname");
                        }


                        String[] categoryarr=categoryid.split(",");
                        categoryArray= new ArrayList<>();
                        for(int j=0;j<categoryarr.length;j++) {
                            categoryArray.add(categoryarr[j]);
                            //Toast.makeText(EditRecommendedCourses.this, categoryarr[j], Toast.LENGTH_SHORT).show();
                        }
                        getCategorires();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Log.v("error", error.toString());
                    error.printStackTrace();
                }
            });
            requestQueue.add(customRequest);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //return "OK";

    }

    public void getCategorires( ){
        //categoryarr= new String[categoryid.split(",").length];
        try {

            progressBarCategory.setVisibility(View.VISIBLE);
            MainLinearLayout.setVisibility(View.GONE);
            JSONObject categoryObject= new JSONObject();
            categoryObject.put("action","getcategory");
            categoryObject.put("userid",CommonMethods.userid);
            RequestQueue requestQueue = Volley.newRequestQueue(EditRecommendedCourses.this);
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, CommonMethods.url, categoryObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.v("response is:",response.toString());
                    try {
                        String ispresent = "";
                        categoryGetSetArrayList = new ArrayList<>();
                        String categoryname = response.getString("category");
                        JSONArray array = new JSONArray(categoryname);

                            for (int i = 0; i < array.length(); i++) {
                                CategoryGetSet categoryGetSet = new CategoryGetSet();
                                if(array.getJSONObject(i).getString("status").equals("deleted")
                                        ||array.getJSONObject(i).getString("status").equals("blocked")){
                                    categoryGetSet.setStatus(array.getJSONObject(i).getString("status"));
                                }
                                else {

                                    categoryGetSet.setCategory_id(array.getJSONObject(i).getString("categoryid"));
                                    categoryGetSet.setCategory_name(array.getJSONObject(i).getString("categoryname"));
                                    categoryGetSet.setStatus(array.getJSONObject(i).getString("status"));
                                    for (int j = 0; j < categoryArray.size(); j++) {
                                        if (categoryArray.get(j).equals(array.getJSONObject(i).getString("categoryid"))) {
                                            ispresent = "true";
                                            break;

                                        } else {
                                            ispresent = "false";
                                        }
                                    }
                                    if (ispresent.equals("true")) {
                                        categoryGetSet.setIsSelected(true);
                                    } else {
                                        categoryGetSet.setIsSelected(false);
                                    }
                                }
                                categoryGetSetArrayList.add(categoryGetSet);
                        }

                        if(categoryGetSetArrayList.size()==1) {
                            if (categoryGetSetArrayList.get(0).getStatus().equals("deleted")
                                    || categoryGetSetArrayList.get(0).getStatus().equals("blocked")) {
                                final Dialog dialog = new Dialog(EditRecommendedCourses.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been " + categoryGetSetArrayList.get(0).getStatus());
                                Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(EditRecommendedCourses.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();
                            }
                            else{
                                categoryAdapter = new CategoryAdapter(EditRecommendedCourses.this,categoryGetSetArrayList);
                                categoryListView.setAdapter(categoryAdapter);
                                setListViewHeightBasedOnItems(categoryListView);
                                progressBarCategory.setVisibility(View.GONE);
                                MainLinearLayout.setVisibility(View.VISIBLE);
                                selectCatTextView.setVisibility(View.VISIBLE);
                                buttonSelected.setVisibility(View.VISIBLE);
                            }
                        }
                        else {


                            categoryAdapter = new CategoryAdapter(EditRecommendedCourses.this, categoryGetSetArrayList);
                            categoryListView.setAdapter(categoryAdapter);
                            setListViewHeightBasedOnItems(categoryListView);
                            progressBarCategory.setVisibility(View.GONE);
                            MainLinearLayout.setVisibility(View.VISIBLE);
                            selectCatTextView.setVisibility(View.VISIBLE);
                            buttonSelected.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Log.v("error", error.toString());
                    error.printStackTrace();
                }
            });

            requestQueue.add(customRequest);
        } catch (JSONException e) {
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
}
