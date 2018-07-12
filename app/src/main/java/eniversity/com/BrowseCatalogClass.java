package eniversity.com;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.eniversity.app.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import adapter.GridAdapterClass;
import get.set.CategoryGetSetClass;

/**
 * Created by shveta on 3/22/2016.
 */
public class BrowseCatalogClass extends AppCompatActivity {


    private GridView gridViweBrouwseCatalog;
    private View linearLayoutDrawer;

    private ArrayList<CategoryGetSetClass> categoryItemsList;
    private ArrayList<CategoryGetSetClass> browseSearchMainArray;
    private ArrayList<CategoryGetSetClass> categoryMainItemsList;

    private Toolbar toolbar;

    private TextView textViewHeading;
    private TextView textViewHeading2;

    private ImageView iamgeViewNavigationdrawer;
    private ImageView imageViewBackIcon;

    private EditText editTextSearch;
    private TextView textViewGetSortFilter;
    private TextView textViewBrowseNocourseFound;

    private LinearLayout linearLayoutSortBy;


    private ActionBarDrawerToggle drawerToggle;

    private ImageView imageViewSearchIcon;
    private String categoryId;

    private String sortStatus = "Popularity";
    private String searchText = "";
    private String edittextValue = "";
    private String isFrom = "";
    private ProgressBar progressBarBrowseCourseDetails;
    private ProgressBar progressBarBrowseMoreCourseDetails;
    private DrawerLayout drawer_layout;

    private GridAdapterClass adapter;
    private int pageSize = 1;
    private int searchPageSize = 1;
    private boolean loading = false;
    private ArrayList<CategoryGetSetClass> browseMainArray;
    private ArrayList<CategoryGetSetClass> bowseSortFilter;
    private CoordinatorLayout coordinatorLayout;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView textViewGetSort;
    private TextView textViewHeading1;
    private LinearLayout linearlayoutmainbrowse;

    Dialog shortByDialog;
    ProgressBar ProgressBarSortByPopup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_grid_layout);
        try {

            try {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
                textViewHeading2 = (TextView) toolbar.findViewById(R.id.textViewHeading2);
                //textViewHeading1= (TextView) toolbar.findViewById(R.id.textViewHeading1);
                // textViewHeading5= (TextView) toolbar.findViewById(R.id.textViewHeading5);

                try {
                    textViewHeading.setVisibility(View.GONE);
                    textViewHeading2.setVisibility(View.VISIBLE);
               /* if(getIntent().getExtras().getString("coursename").length()>8){
                    textViewHeading5.setVisibility(View.VISIBLE);
                }
                else {
                    textViewHeading1.setVisibility(View.VISIBLE);
                }*/
                    textViewHeading.setText(getIntent().getExtras().getString("coursename"));
                    textViewHeading2.setText(getIntent().getExtras().getString("coursename"));

                    //textViewHeading1.setText(getIntent().getExtras().getString("coursename"));
                    //textViewHeading5.setText(getIntent().getExtras().getString("coursename"));
                } catch (Exception e) {
                    //textViewHeading.setVisibility(View.VISIBLE);
                    textViewHeading2.setVisibility(View.VISIBLE);

                    // textViewHeading1.setVisibility(View.GONE);
                    textViewHeading.setText("Browse Courses");
                    textViewHeading2.setText("Browse Courses");
                    //textViewHeading5.setVisibility(View.GONE);
                }

                drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
                linearlayoutmainbrowse = (LinearLayout) findViewById(R.id.linearlayoutmainbrowse);

                editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
                editTextSearch.setOnEditorActionListener(textSearchListener);
                textViewBrowseNocourseFound = (TextView) findViewById(R.id.textViewBrowseNocourseFound);
                browseMainArray = new ArrayList<>();
                categoryMainItemsList = new ArrayList<>();
                browseSearchMainArray = new ArrayList<>();
                imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
                linearLayoutDrawer = findViewById(R.id.linearLayoutDrawer);
                linearLayoutDrawer.setVisibility(View.GONE);
                imageViewBackIcon.setVisibility(View.VISIBLE);
                imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View view) {
                                                             finish();
                                                         }
                                                     }
                );

            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

            categoryId = getIntent().getExtras().getString("categoryId");
            gridViweBrouwseCatalog = (GridView) findViewById(R.id.gridViweBrouwseCatalog);

            linearLayoutSortBy = (LinearLayout) findViewById(R.id.linearLayoutSortBy);

            textViewGetSortFilter = (TextView) findViewById(R.id.textViewGetSortFilter);
            //textViewGetSortFilter.setText(" Popularity");
            textViewGetSort = (ImageView) findViewById(R.id.textViewGetSort);

            /*try {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(BrowseCatalogClass.this, R.drawable.ic_check_circle_black_24dp),0,1, 0);
               // builder.append(" ");
                textViewGetSort.setText(builder);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            progressBarBrowseCourseDetails = (ProgressBar) findViewById(R.id.progressBarBrowseCourseDetails);
            progressBarBrowseMoreCourseDetails = (ProgressBar) findViewById(R.id.progressBarBrowseMoreCourseDetails);

        /*
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextSearch.getVisibility() == View.VISIBLE) {
                    textViewHeading.setVisibility(View.VISIBLE);
                    editTextSearch.setVisibility(View.GONE);
                    pageSize = 1;
                    editTextSearch.setText("");
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    hideKeyboard();
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    browseSearchMainArray.clear();
                    browseMainArray.clear();
                    categoryItemsList.clear();
                    searchText = "1=1";
                    pageSize = 1;
                    getAllCatalog(searchText, pageSize);
                } else {
                    categoryItemsList = new ArrayList<>();
                    finish();
                }
            }
        });
        */


            imageViewSearchIcon.setOnClickListener(onClickSearchIcon);

        /*
        imageViewSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   loading = false;
                browseMainArray = new ArrayList<>();
                //  browseSearchMainArray=new ArrayList<>();
                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                textViewBrowseNocourseFound.setVisibility(View.GONE);
                progressBarBrowseCourseDetails.setVisibility(View.GONE);
                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                if (editTextSearch.getVisibility() == View.VISIBLE ) {
                    edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                    searchPageSize = 1;

                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                   *//**//* if (edittextValue.length() > 0) {
                        getSearchAllCatalog(edittextValue, searchPageSize);
                    }*//**//*
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search));
                    textViewHeading.setVisibility(View.GONE);
                    if(getIntent().getExtras().getString("coursename").length()>8){
                        textViewHeading5.setVisibility(View.VISIBLE);
                    }
                    else {
                        textViewHeading1.setVisibility(View.VISIBLE);
                    }
                    editTextSearch.setVisibility(View.GONE);
                    imageViewBackIcon.setVisibility(View.VISIBLE);
                    getAllCatalog("1=1", 1);
                    hideKeyboard();
                } else if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() == 0) {
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                } else {
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_24dp));
                    textViewHeading.setVisibility(View.GONE);
                    textViewHeading1.setVisibility(View.INVISIBLE);
                    textViewHeading5.setVisibility(View.INVISIBLE);
                    editTextSearch.setVisibility(View.VISIBLE);
                    imageViewBackIcon.setVisibility(View.GONE);
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                    editTextSearch.setText("");
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    editTextSearch.requestFocus();
                    showKeyboard();
                    //  searchText ="1=1";
                    //// getAllCatalog(searchText, 1);

                }*//*
            }
        });*/


            editTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edittextValue = "c.coursename like '%" + s.toString() + "%'";
                    searchPageSize = 1;
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray.clear();
                    browseSearchMainArray = new ArrayList<>();
                    if (s.length() > 0) {

                        getSearchAllCatalog(edittextValue, 1);
                    } else if (edittextValue.length() == 0) {

                        textViewBrowseNocourseFound.setVisibility(View.GONE);
                    }
                    else if(editTextSearch.length()==0){
                        isFrom="ontext";

                        searchText="1=1";
                        pageSize=1;
                        getAllCatalog(searchText, pageSize);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            gridViweBrouwseCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(BrowseCatalogClass.this, CorseInfo.class);
                    i.putExtra("categoryid", categoryItemsList.get(position).getCategoryId());
                    i.putExtra("courseid", categoryItemsList.get(position).getCourseId());
                    startActivity(i);
                }
            });


            linearLayoutSortBy.setOnClickListener(sortByClickListener);


            categoryItemsList = new ArrayList<>();
            browseMainArray = new ArrayList<>();
            browseSearchMainArray = new ArrayList<>();
            bowseSortFilter = new ArrayList<>();
            searchText = "1=1";
            //      getAllCatalog(searchText, pageSize);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            if(getIntent().getExtras().getString("searchtext").trim().equals("")){
                getAllCatalog(searchText, 1);
            }else{
                textViewHeading2.setVisibility(View.INVISIBLE);
                editTextSearch.setVisibility(View.VISIBLE);
                imageViewBackIcon.setVisibility(View.GONE);
                editTextSearch.setText(getIntent().getExtras().getString("searchtext"));
                editTextSearch.requestFocus();
                CommonMethods.showKeyboard(BrowseCatalogClass.this, (BrowseCatalogClass.this).getCurrentFocus());
                textViewBrowseNocourseFound.setVisibility(View.GONE);
                categoryItemsList = new ArrayList<>();
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
                getAllCatalog( "c.coursename like '%" + getIntent().getExtras().getString("searchtext") + "%'", 1);
            }

            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();
                    searchText = "1=1";
                    pageSize = 1;
                    sortStatus = "Popularity";
                    loading = false;
                    textViewGetSortFilter.setText(" Popularity");
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                    //textViewHeading.setVisibility(View.VISIBLE);
                    textViewHeading2.setVisibility(View.VISIBLE);
                /*if(getIntent().getExtras().getString("coursename").length()>8){
                    textViewHeading5.setVisibility(View.VISIBLE);
                }
                else {
                    textViewHeading1.setVisibility(View.VISIBLE);
                }*/
                    editTextSearch.setVisibility(View.GONE);
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    getAllCatalog("1=1", 1);

                    edittextValue = "";
                    swipeRefreshLayout.setRefreshing(false);
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                    // textViewHeading.setVisibility(View.VISIBLE);
                    textViewHeading2.setVisibility(View.VISIBLE);
               /* if(getIntent().getExtras().getString("coursename").length()>8){
                    textViewHeading5.setVisibility(View.VISIBLE);
                }
                else {
                    textViewHeading1.setVisibility(View.VISIBLE);
                }*/
                    editTextSearch.setVisibility(View.GONE);
                    imageViewBackIcon.setVisibility(View.VISIBLE);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private View.OnClickListener sortByClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loading = false;
            imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
imageViewBackIcon.setVisibility(View.VISIBLE);
            editTextSearch.setVisibility(View.GONE);
            // textViewHeading.setVisibility(View.VISIBLE);
            textViewHeading2.setVisibility(View.VISIBLE);

            shortByDialog = new Dialog(BrowseCatalogClass.this, android.R.style.Theme_Black_NoTitleBar);
            shortByDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            shortByDialog.setContentView(R.layout.sort_by_popup);
            shortByDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            shortByDialog.setCanceledOnTouchOutside(true);

            Toolbar sort_toolbar = (Toolbar) shortByDialog.findViewById(R.id.toolbar);
            ImageView imageViewBackIcon = (ImageView) sort_toolbar.findViewById(R.id.imageViewBackIcon);
            TextView textViewHeading = (TextView) sort_toolbar.findViewById(R.id.textViewHeading);

            //Dialog TextViews
            TextView textViewNewestFirst = (TextView) shortByDialog.findViewById(R.id.textViewNewestFirst);
            TextView textViewpriceHightolow = (TextView) shortByDialog.findViewById(R.id.textViewpriceHightolow);
            TextView textViewpriceLowToHigh = (TextView) shortByDialog.findViewById(R.id.textViewpriceLowToHigh);
            TextView textViewHighestRating = (TextView) shortByDialog.findViewById(R.id.textViewHighestRating);
            TextView textViewLowestRating = (TextView) shortByDialog.findViewById(R.id.textViewLowestRating);
            TextView textViewPopularity = (TextView) shortByDialog.findViewById(R.id.textViewPopularity);

            ProgressBarSortByPopup = (ProgressBar) shortByDialog.findViewById(R.id.ProgressBarSortByPopup);

            switch (sortStatus) {
                case "Newest":
                    textViewNewestFirst.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewNewestFirst.setTypeface(textViewNewestFirst.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewNewestFirst).setVisibility(View.VISIBLE);
                    break;
                case "Price High to Low":
                    textViewpriceHightolow.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewpriceHightolow.setTypeface(textViewpriceHightolow.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewpriceHightolow).setVisibility(View.VISIBLE);
                    break;
                case "Price Low to High":
                    textViewpriceLowToHigh.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewpriceLowToHigh.setTypeface(textViewpriceLowToHigh.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewpriceLowToHigh).setVisibility(View.VISIBLE);
                    break;
                case "Highest Rating":
                    textViewHighestRating.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewHighestRating.setTypeface(textViewHighestRating.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewHighestRating).setVisibility(View.VISIBLE);
                    break;
                case "Lowest Rating":
                    textViewLowestRating.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewLowestRating.setTypeface(textViewLowestRating.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewLowestRating).setVisibility(View.VISIBLE);
                    break;
                case "Popularity":
                    textViewPopularity.setTextColor(getResources().getColor(R.color.primaryColor));
                    textViewPopularity.setTypeface(textViewPopularity.getTypeface(), Typeface.BOLD);
                    shortByDialog.findViewById(R.id.imageViewsortby).setVisibility(View.VISIBLE);
                    break;
            }

            textViewHeading.setText("Sort by");
            imageViewBackIcon.setVisibility(View.VISIBLE);
            imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shortByDialog.dismiss();
                }
            });
            searchText = "1=1";
            pageSize = 1;
            searchPageSize = 1;
            adapter = null;

            shortByDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            RelativeLayout relativeLayoutNewestFirst = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutNewestFirst);

            relativeLayoutNewestFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortStatus = "Newest";
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    textViewGetSortFilter.setText(" Newest");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);


                }
            });


            RelativeLayout relativeLayoutpriceHightolow = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutpriceHightolow);
            relativeLayoutpriceHightolow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    sortStatus = "Price High to Low";
                    textViewGetSortFilter.setText(" Price High to Low");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);

                }
            });


            RelativeLayout relativeLayoutpriceLowToHigh = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutpriceLowToHigh);
            relativeLayoutpriceLowToHigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    sortStatus = "Price Low to High";
                    textViewGetSortFilter.setText(" Price Low to High");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);
                }
            });

            RelativeLayout relativeLayoutHighestRating = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutHighestRating);
            relativeLayoutHighestRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    sortStatus = "Highest Rating";
                    textViewGetSortFilter.setText(" Highest Rating");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);
                }
            });


            RelativeLayout relativeLayoutLowestRating = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutLowestRating);
            relativeLayoutLowestRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    sortStatus = "Lowest Rating";

                    textViewGetSortFilter.setText(" Lowest Rating");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    browseSearchMainArray = new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);

                }
            });


            RelativeLayout relativeLayoutPopularity = (RelativeLayout) shortByDialog.findViewById(R.id.relativeLayoutPopularity);

            relativeLayoutPopularity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryItemsList = new ArrayList<>();
                    categoryItemsList.clear();
                    browseMainArray = new ArrayList<>();
                    browseMainArray.clear();
                    bowseSortFilter = new ArrayList<CategoryGetSetClass>();
                    bowseSortFilter.clear();
                    browseMainArray = new ArrayList<CategoryGetSetClass>();
                    browseMainArray.clear();

                    sortStatus = "Popularity";

                    textViewGetSortFilter.setText(" Popularity");
                    categoryItemsList = new ArrayList<>();
                    browseMainArray = new ArrayList<>();
                    //  browseSearchMainArray=new ArrayList<>();
                    searchText = "1=1";
                    //shortByDialog.dismiss();
                    pageSize = 1;
                    ProgressBarSortByPopup.setVisibility(View.VISIBLE);
                    getAllCatalog(searchText, pageSize);
                }
            });
            shortByDialog.show();
                  /*Window window = shortByDialog.getWindow();
                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);*/
        }
    };

    private View.OnClickListener onClickSearchIcon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                textViewBrowseNocourseFound.setVisibility(View.GONE);
                if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                    edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                    if (edittextValue.length() > 0) {
                        browseSearchMainArray = new ArrayList<>();
                        searchPageSize = 1;
                        CommonMethods.hideKeyboard(BrowseCatalogClass.this, (BrowseCatalogClass.this).getCurrentFocus());
                        getSearchAllCatalog(edittextValue, searchPageSize);
                    }

                    editTextSearch.setVisibility(View.INVISIBLE);
                    editTextSearch.setText("");
                    pageSize = 1;
                    searchText = "1=1";
                    categoryItemsList = new ArrayList<>();
                    if(isFrom.equalsIgnoreCase("ontext")){

                    }
                    else {
                        getAllCatalog(searchText, pageSize);
                    }
                    imageViewBackIcon.setVisibility(View.VISIBLE);
                    CommonMethods.hideKeyboard(BrowseCatalogClass.this, (BrowseCatalogClass.this).getCurrentFocus());
                    // iamgeViewNavigationdrawer.setVisibility(View.GONE);
                    //drawerToggle.setDrawerIndicatorEnabled(true);
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                    imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
                    textViewHeading2.setVisibility(View.VISIBLE);

                } else {
                    //textViewHeading.setVisibility(View.INVISIBLE);
                    textViewHeading2.setVisibility(View.INVISIBLE);

                    // textViewHeading5.setVisibility(View.INVISIBLE);


                    //textViewHeading1.setVisibility(View.INVISIBLE);

                    editTextSearch.setVisibility(View.VISIBLE);
                    //iamgeViewNavigationdrawer.setVisibility(View.VISIBLE);
                    // iamgeViewNavigationdrawer.setOnClickListener(finishSearchlistener);
                    imageViewBackIcon.setVisibility(View.GONE);
                    // drawerToggle.setDrawerIndicatorEnabled(false);

                    editTextSearch.setText("");
                    editTextSearch.requestFocus();
                    CommonMethods.showKeyboard(BrowseCatalogClass.this, (BrowseCatalogClass.this).getCurrentFocus());
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    categoryItemsList = new ArrayList<>();
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                    imageViewSearchIcon.setOnClickListener(finishSearchlistener);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //textViewHeading.setVisibility(View.VISIBLE);
                textViewHeading2.setVisibility(View.VISIBLE);
            /*if(getIntent().getExtras().getString("coursename").length()>8){
                textViewHeading5.setVisibility(View.VISIBLE);
            }
            else {
                textViewHeading1.setVisibility(View.VISIBLE);
            }*/
                editTextSearch.setVisibility(View.INVISIBLE);
                editTextSearch.setText("");
                pageSize = 1;
                searchText = "1=1";
                categoryItemsList = new ArrayList<>();
                if(isFrom.equalsIgnoreCase("ontext")){

                }
                else {
                    getAllCatalog(searchText, pageSize);
                }
                imageViewBackIcon.setVisibility(View.VISIBLE);
                CommonMethods.hideKeyboard(BrowseCatalogClass.this, (BrowseCatalogClass.this).getCurrentFocus());
                // iamgeViewNavigationdrawer.setVisibility(View.GONE);
                //drawerToggle.setDrawerIndicatorEnabled(true);
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    private void showKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    public void getSearchAllCatalog(String searchText, final int searchPageSize) {
        //categoryItemsList = new ArrayList<>();
        // browseMainArray = new ArrayList<>();
        //browseSearchMainArray=new ArrayList<>();
        textViewBrowseNocourseFound.setVisibility(View.GONE);
        progressBarBrowseCourseDetails.setVisibility(View.GONE);
//        {"wherecondition":"1=1","action":"getcourselist","pagesize":"2","pagenumber":"1", "categoryid":"1","sortby":"Popularity"}
        try {
            JSONObject sendExploreObject = new JSONObject();

            sendExploreObject.put("action", "getcourselist");
            sendExploreObject.put("wherecondition", searchText);
            sendExploreObject.put("pagenumber", String.valueOf(searchPageSize));
            sendExploreObject.put("pagesize", "10");
            sendExploreObject.put("sortby", "Popularity");
            sendExploreObject.put("categoryid", categoryId);

            if (!loading) {
                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                if (searchPageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                } else if (searchPageSize > 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            } else {
                if (searchPageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                } else if (searchPageSize > 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(BrowseCatalogClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    browseSearchMainArray = new ArrayList<>();
                    try {
                        categoryItemsList = new ArrayList<>();

                        String browseCatalogueitems = response.getString("CourseList");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        if (browseArray.length() == 1) {
                            /*textViewGetSortFilter.setVisibility(View.INVISIBLE);
                            textViewGetSort.setVisibility(View.INVISIBLE);*/
                            linearLayoutSortBy.setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < browseArray.length(); i++) {
                            CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                            categoryItems.setCategoryId(browseArray.getJSONObject(i).getString("courseid"));
                            categoryItems.setCategoryName(browseArray.getJSONObject(i).getString("coursename"));
                            categoryItems.setCategoryImage(browseArray.getJSONObject(i).getString("courseimage"));
                            categoryItems.setCategorytotalfees(browseArray.getJSONObject(i).getString("totalfees"));
                            categoryItems.setCategorybasicfees(browseArray.getJSONObject(i).getString("basicfees"));
                            categoryItems.setCategoryintermediatefees(browseArray.getJSONObject(i).getString("intermediatefees"));
                            categoryItems.setCategoryadvancefees(browseArray.getJSONObject(i).getString("advancefees"));

                            categoryItems.setCategoryRatings(browseArray.getJSONObject(i).getString("rating"));
                            categoryItems.setCategoryUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));


                            categoryItemsList.add(categoryItems);

                        }
                        browseSearchMainArray.addAll(categoryItemsList);
                        //  gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(BrowseCatalogClass.this, categoryItemsList, "exploreclass"));
                        progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);

                        if (!loading) {

                            if (categoryItemsList.size() == 10) {

                                adapter = new GridAdapterClass(BrowseCatalogClass.this, browseSearchMainArray, "exploreclass");
                                gridViweBrouwseCatalog.setAdapter(adapter);
                                adapter.notifyDataSetInvalidated();
                                adapter.notifyDataSetChanged();
                                gridViweBrouwseCatalog.setOnScrollListener(scrollListener);
                            } else {
                                adapter = new GridAdapterClass(BrowseCatalogClass.this, browseSearchMainArray, "exploreclass");
                                gridViweBrouwseCatalog.setAdapter(adapter);
                                textViewBrowseNocourseFound.setVisibility(View.GONE);
                                linearLayoutSortBy.setVisibility(View.VISIBLE);
                                gridViweBrouwseCatalog.setOnScrollListener(null);
                                adapter.notifyDataSetInvalidated();
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Log.v("the boolean value is ", String.valueOf(loading));

                                    try {
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }

                                }
                            });

                        }

                        if (browseSearchMainArray.size() == 0) {
                            textViewBrowseNocourseFound.setVisibility(View.VISIBLE);
                            linearLayoutSortBy.setVisibility(View.GONE);
                        } else {
                            textViewBrowseNocourseFound.setVisibility(View.GONE);
                            linearLayoutSortBy.setVisibility(View.VISIBLE);
                        }

                        if (categoryItemsList.size() <= 1) {
                            gridViweBrouwseCatalog.setOnScrollListener(null);

                            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                            loading = false;
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                    error.printStackTrace();

                }
            });

            requestQueue.add(customres);
            Log.i("Tag:", sendExploreObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void getAllCatalog(final String searchText, final int pageSize) {
        categoryItemsList = new ArrayList<>();

        browseSearchMainArray = new ArrayList<>();

        textViewBrowseNocourseFound.setVisibility(View.GONE);
//        {"wherecondition":"1=1","action":"getcourselist","pagesize":"2","pagenumber":"1", "categoryid":"1","sortby":"Popularity"}
        try {
            linearlayoutmainbrowse.setVisibility(View.GONE);

            JSONObject sendExploreObject = new JSONObject();

            sendExploreObject.put("action", "getcourselist");
            sendExploreObject.put("wherecondition", searchText);
            sendExploreObject.put("pagenumber", String.valueOf(pageSize));
            sendExploreObject.put("pagesize", "10");
            sendExploreObject.put("sortby", sortStatus);
            sendExploreObject.put("categoryid", categoryId);


            if (!loading) {
                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                if (pageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                } else if (pageSize > 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            } else {
                if (pageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                } else if (pageSize > 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(BrowseCatalogClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        browseMainArray = new ArrayList<>();
                        categoryItemsList = new ArrayList<>();

                        String browseCatalogueitems = response.getString("CourseList");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);


                        for (int i = 0; i < browseArray.length(); i++) {
                            CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                            categoryItems.setCategoryId(browseArray.getJSONObject(i).getString("courseid"));
                            categoryItems.setCategoryName(browseArray.getJSONObject(i).getString("coursename"));
                            categoryItems.setCategoryImage(browseArray.getJSONObject(i).getString("courseimage"));
                            categoryItems.setCategorytotalfees(browseArray.getJSONObject(i).getString("totalfees"));
                            categoryItems.setCategorybasicfees(browseArray.getJSONObject(i).getString("basicfees"));
                            categoryItems.setCategoryintermediatefees(browseArray.getJSONObject(i).getString("intermediatefees"));
                            categoryItems.setCategoryadvancefees(browseArray.getJSONObject(i).getString("advancefees"));

                            categoryItems.setCategoryRatings(browseArray.getJSONObject(i).getString("rating"));
                            categoryItems.setCategoryUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));


                            categoryItemsList.add(categoryItems);

                        }

                        browseMainArray.addAll(categoryItemsList);
                        //  gridViweBrouwseCatalog.setAdapter(new GridAdapterClass(BrowseCatalogClass.this, categoryItemsList, "exploreclass"));
                        progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                       /* if(browseMainArray.size()<1)
                        {if(browseMainArray.size()<1){
                            textViewBrowseNocourseFound.setVisibility(View.VISIBLE);
                        }
                        else {
                            textViewBrowseNocourseFound.setVisibility(View.GONE);
                        }
                        }*/
                        if (browseArray.length() == 1) {
                            /*textViewGetSortFilter.setVisibility(View.GONE);
                            textViewGetSort.setVisibility(View.GONE);*/
                            linearLayoutSortBy.setVisibility(View.GONE);
                        } else {
                            /*textViewGetSort.setVisibility(View.VISIBLE);
                            textViewGetSortFilter.setVisibility(View.VISIBLE);*/
                            linearLayoutSortBy.setVisibility(View.VISIBLE);
                        }
                        if (!loading) {


                            if (categoryItemsList.size() > 1) {
                                browseSearchMainArray = new ArrayList<>();
                                gridViweBrouwseCatalog.setAdapter(null);
                                adapter = new GridAdapterClass(BrowseCatalogClass.this, browseMainArray, "exploreclass");
                                gridViweBrouwseCatalog.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetInvalidated();
                                //gridViweBrouwseCatalog.setOnScrollListener(scrollListener);
                            } else {
                                browseSearchMainArray = new ArrayList<>();

                                adapter = new GridAdapterClass(BrowseCatalogClass.this, browseMainArray, "exploreclass");
                                gridViweBrouwseCatalog.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetInvalidated();
                                // gridViweBrouwseCatalog.setOnScrollListener(null);
                            }


                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Log.v("the boolean value is ", String.valueOf(loading));

                                    try {
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }

                                }
                            });
                        }
                        if (browseMainArray.size() == 0) {
                            textViewBrowseNocourseFound.setVisibility(View.VISIBLE);
                            linearLayoutSortBy.setVisibility(View.GONE);
                        } else {
                            textViewBrowseNocourseFound.setVisibility(View.GONE);
                            linearLayoutSortBy.setVisibility(View.VISIBLE);
                        }

                        if (browseMainArray.size() <= 1) {

                            gridViweBrouwseCatalog.setOnScrollListener(null);

                            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                            loading = false;
                        }
                        linearlayoutmainbrowse.setVisibility(View.VISIBLE);

                        if (shortByDialog != null) {
                            if (shortByDialog.isShowing()) {
                                shortByDialog.dismiss();
                            }
                        }
                        if (ProgressBarSortByPopup != null) {
                            if (ProgressBarSortByPopup.isShown()) {
                                ProgressBarSortByPopup.setVisibility(View.GONE);
                            }
                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getAllCatalog(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        if (shortByDialog != null) {
                            if (shortByDialog.isShowing()) {
                                shortByDialog.dismiss();
                            }
                        }
                        if (ProgressBarSortByPopup != null) {
                            if (ProgressBarSortByPopup.isShown()) {
                                ProgressBarSortByPopup.setVisibility(View.GONE);
                            }
                        }
                        error.printStackTrace();

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getAllCatalog(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        if (shortByDialog != null) {
                            if (shortByDialog.isShowing()) {
                                shortByDialog.dismiss();
                            }
                        }
                        if (ProgressBarSortByPopup != null) {
                            if (ProgressBarSortByPopup.isShown()) {
                                ProgressBarSortByPopup.setVisibility(View.GONE);
                            }
                        }
                        error.printStackTrace();
                    }
                }

            });

            requestQueue.add(customres);
            Log.i("Tag:", sendExploreObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
            if (shortByDialog != null) {
                if (shortByDialog.isShowing()) {
                    shortByDialog.dismiss();
                }
            }
            if (ProgressBarSortByPopup != null) {
                if (ProgressBarSortByPopup.isShown()) {
                    ProgressBarSortByPopup.setVisibility(View.GONE);
                }
            }
        }


    }

    private TextView.OnEditorActionListener textSearchListener = new TextView.OnEditorActionListener() {


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                hideKeyboard();
                return true;
            }
            return false;
        }
    };


    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            try {

                if ((gridViweBrouwseCatalog.getLastVisiblePosition() == gridViweBrouwseCatalog.getAdapter().getCount() - 1
                        && gridViweBrouwseCatalog.getChildAt(gridViweBrouwseCatalog.getChildCount() - 1).getBottom() <= gridViweBrouwseCatalog.getHeight())) {
                    progressBarBrowseMoreCourseDetails.setVisibility(View.VISIBLE);
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    if (edittextValue.length() > 0 && editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                        loading = true;
                        textViewBrowseNocourseFound.setVisibility(View.GONE);
                        searchPageSize++;
                        getSearchAllCatalog(edittextValue, searchPageSize);
                    } else {
                        pageSize++;
                        loading = true;
                        getAllCatalog(searchText, pageSize);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        categoryItemsList = new ArrayList<>();
        browseMainArray = new ArrayList<>();
        browseSearchMainArray = new ArrayList<>();
        bowseSortFilter = new ArrayList<>();
        searchText = "1=1";



    }
}
