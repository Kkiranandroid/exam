package eniversity.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eniversity.app.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import Commmon.Methods.CircleTransform;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import adapter.GridAdapterClass;
import data.base.LoginTable;
import get.set.CategoryGetSetClass;
import login.page.LoginMainPage;


/**
 * Created by shveta on 3/24/2016.
 */
public class CourseList extends AppCompatActivity {

    private GridView gridViweBrouwseCatalog;

    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawerToggle;
    private View linearLayoutExploreCourse;
    private View includelayoutnavigationDrawer;
    private View linearLayoutAboutUs;
    private View linearLayoutContactUs;
    private ImageView linearLayoutEditProfile;
    private View linearLayoutBrowse;
    private View linearLayoutToppers;
    private View linearLayoutRegister;
    private  View linearLayoutFAQ;
    private ImageView linearLayoutYOurCourse;
    private LinearLayout linearLayoutEditRecCourse;
    private LinearLayout linearLayoutSubScription;
    private  ImageView linearLayoutLogout;
    private  View linearLayoutDrawer;
    private TextView textViewUserName;
    private ImageView iamgeViewNavigationdrawer;
    private ImageView imageViewProfilePic;





    private ArrayList<CategoryGetSetClass> categoryItemsList;
    private ArrayList<CategoryGetSetClass> categoryMainItemsList;
    private ArrayList<CategoryGetSetClass> categorySearchMainItemsList;
    private ArrayList<CategoryGetSetClass> categorySearch;
    private  ArrayList<CategoryGetSetClass> categoryGetItemList;

    private Toolbar toolbar;
    private TextView textViewHeading;

    private ImageView imageViewBackIcon;
    private ImageView imageViewSearchIcon;
    private EditText editTextSearch;
    private TextView textViewGetSortFilter;
    private TextView textViewGetSort;
    private TextView textViewBrowseNocourseFound;

    private String searchText = "";
    private String edittextValue = "";

    private String isFrom = "";
    private boolean loading = false;
    private int pageSize = 1;
    private GridAdapterClass adapter;
    private int searchPageSize=1;

    private ProgressBar progressBarBrowseMoreCourseDetails;
    private ProgressBar progressBarBrowseCourseDetails;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_grid_layout2);
try {
    try {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);

        try {
            textViewHeading.setText("Browse Courses");
        } catch (Exception e) {

        }

        linearLayoutDrawer = findViewById(R.id.linearLayoutDrawer);
        linearLayoutDrawer.setVisibility(View.VISIBLE);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener(textSearchListener);

        textViewGetSortFilter = (TextView) findViewById(R.id.textViewGetSortFilter);
        textViewGetSort = (TextView) findViewById(R.id.textViewGetSort);
        textViewGetSort.setVisibility(View.GONE);
        textViewGetSortFilter.setVisibility(View.GONE);
        progressBarBrowseMoreCourseDetails = (ProgressBar) findViewById(R.id.progressBarBrowseMoreCourseDetails);
        progressBarBrowseCourseDetails = (ProgressBar) findViewById(R.id.progressBarBrowseCourseDetails);
        textViewBrowseNocourseFound = (TextView) findViewById(R.id.textViewBrowseNocourseFound);
        gridViweBrouwseCatalog = (GridView) findViewById(R.id.gridViweBrouwseCatalog);
        categorySearch = new ArrayList<>();
        categoryItemsList = new ArrayList<>();
        categoryMainItemsList = new ArrayList<>();
        categoryGetItemList = new ArrayList<>();

    } catch (Exception e) {
        Log.getStackTraceString(e);
    }
    drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
    includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);

    //--set round image and dealer name in drawer.--
    imageViewProfilePic = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.imageViewProfilePic);
    textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);

    iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
    initDrawer();
    linearLayoutRegister = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutRegister);
    linearLayoutRegister.setVisibility(View.GONE);
    linearLayoutRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, MainActivity.class);
            // i.putExtra("activityIntent", "CourseList");
            startActivity(i);
            finish();
        }
    });
    linearLayoutEditProfile = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditProfile);
    linearLayoutEditProfile.setVisibility(View.VISIBLE);
    linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, RegistrationClass.class);
            i.putExtra("activityIntent", "CourseList");
            startActivity(i);
            finish();
        }
    });
    linearLayoutEditRecCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditRecCourse);

    linearLayoutEditRecCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(CourseList.this, EditRecommendedCourses.class);
            startActivity(intent);


        }
    });
    linearLayoutSubScription = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutSubScription);
    linearLayoutSubScription.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(CourseList.this, SubScription.class);
            startActivity(i);
        }
    });

    linearLayoutYOurCourse = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutYOurCourse);
    linearLayoutYOurCourse.setVisibility(View.VISIBLE);
    linearLayoutYOurCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, LoginMainPage.class);
            startActivity(i);
            finish();
        }
    });

    linearLayoutToppers = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutToppers);
    linearLayoutToppers.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, ToppersClass.class);
            startActivity(i);
            finish();
        }
    });
    linearLayoutFAQ = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutFAQ);
    linearLayoutFAQ.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, FrequentlyAskQuesionsClass.class);
            startActivity(i);

        }
    });

    LoginTable loginGetUserName = new LoginTable(CourseList.this);
    loginGetUserName.open();

    if (CommonMethods.userid.length() > 0) {

    } else {
        linearLayoutYOurCourse.setVisibility(View.GONE);
    }
    loginGetUserName.close();


    linearLayoutExploreCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutExploreCourse);
    linearLayoutExploreCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, ExploreClass.class);
            startActivity(i);
            finish();
        }
    });

    linearLayoutBrowse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutBrowse);
    linearLayoutBrowse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawer_layout.closeDrawer(Gravity.LEFT);

        }
    });
    linearLayoutAboutUs = includelayoutnavigationDrawer.findViewById(R.id.linearLayoutAboutUs);
    linearLayoutAboutUs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, About.class);
            startActivity(i);

        }
    });
    linearLayoutContactUs = includelayoutnavigationDrawer.findViewById(R.id.linearLayoutContactUs);
    linearLayoutContactUs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CourseList.this, Contact_us.class);
            startActivity(i);

        }
    });
    linearLayoutLogout = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutLogout);
    linearLayoutLogout.setVisibility(View.VISIBLE);
    linearLayoutLogout.setOnClickListener(logOut);

    if (CommonMethods.userid.length() > 0) {
        linearLayoutRegister.setVisibility(View.GONE);
       // setDrawer();

    } else {
        linearLayoutSubScription.setVisibility(View.GONE);
        linearLayoutEditRecCourse.setVisibility(View.GONE);
        linearLayoutEditProfile.setVisibility(View.GONE);
        linearLayoutRegister.setVisibility(View.VISIBLE);
        linearLayoutLogout.setVisibility(View.GONE);
        textViewUserName.setText("Welcome Guest");
    }
    imageViewSearchIcon.setOnClickListener(onClickSearchIcon);

    searchText = "1=1";
    categoryMainItemsList = new ArrayList<>();
    categorySearchMainItemsList = new ArrayList<>();
    getCourseList(searchText, pageSize);

    editTextSearch.addTextChangedListener(textChengListener);

    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
            progressBarBrowseCourseDetails.setVisibility(View.GONE);
            categorySearchMainItemsList=new ArrayList<CategoryGetSetClass>();
           // categoryItemsList = new ArrayList<>();
            categoryMainItemsList = new ArrayList<>();
            pageSize = 1;
            searchText = "1=1";

            editTextSearch.setVisibility(View.GONE);
            textViewHeading.setVisibility(View.VISIBLE);
            drawerToggle.setDrawerIndicatorEnabled(true);
            iamgeViewNavigationdrawer.setVisibility(View.GONE);
            editTextSearch.setText("");
            edittextValue = "";

            categoryMainItemsList = new ArrayList<>();
            getCourseList(searchText, pageSize);
            swipeRefreshLayout.setRefreshing(false);
        }
    });


}
catch (Exception e){
    e.printStackTrace();
}
        try {

            if(CommonMethods.userid.length()>0) {
                String dealerName = "";
                if (CommonMethods.userName.length() > 0) {

                    dealerName = CommonMethods.userName;
                } /*else {
                dealerName = CommonMethods.user_name;
            }*/


                if ((dealerName.toString().length()) > 12) {
                    textViewUserName.setText("Welcome\n" + dealerName);
                } else {
                    textViewUserName.setText("Welcome " + dealerName);
                }
            }
            else{
                textViewUserName.setText("Welcome Guest");
            }
            if(CommonMethods.userid.length()>0){
                if (CommonMethods.profile_image_url != null) {
                    if (CommonMethods.profile_image_url.length() > 0) {
                        if (CommonMethods.profile_image_url.contains("http")) {

                            Glide.with(CourseList.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                   // .skipMemoryCache(true)
                                    .transform(new CircleTransform(CourseList.this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        } else {
                            Glide.with(CourseList.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                                    //.skipMemoryCache(true)
                                    .transform(new CircleTransform(CourseList.this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        }


                    } else {
                        Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                    }
                } else {
                    Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }

            }
            else {

                Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
          /*  ProfilePictureClass serverData = new ProfilePictureClass(CourseList.this);
            String dealerName;
            String path;
            if (serverData.getCurrentUserName().length() > 0) {

                dealerName = serverData.getCurrentUserName();
            } else {
                dealerName = CommonMethods.user_name;
            }

            if ((dealerName.toString().length()) > 12) {
                textViewUserName.setText("Welcome\n" + dealerName);
            } else {
                textViewUserName.setText("Welcome " + dealerName);
            }



            path = serverData.getCurrentProfilePic();


            if (path != null) {
                if (path.length() > 0) {

                    Glide.with(CourseList.this).load(path)
                            .transform(new CircleTransform(CourseList.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);

                } else {
                    Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            } else {
                Picasso.with(CourseList.this).load(R.drawable.eniversity).placeholder(R.drawable.eniversity).error(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private TextWatcher textChengListener= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {

                categorySearchMainItemsList = new ArrayList<>();
                categoryMainItemsList= new ArrayList<>();
                if (editTextSearch.length() > 0) {
                    searchPageSize = 1;
                    edittextValue = "cr.coursename like '%" + s.toString() + "%' or c.categoryname like '%" + s.toString() + "%'";
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    getSearchCourseList(edittextValue, searchPageSize);
                }
                else{
                    isFrom="ontext";
                    searchText="1=1";
                    pageSize=1;
                    getCourseList(searchText, pageSize);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View.OnClickListener onClickSearchIcon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                textViewBrowseNocourseFound.setVisibility(View.GONE);
                if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                    edittextValue = "cr.coursename like '%" + editTextSearch.getText().toString() + "%' or c.categoryname like '%" + editTextSearch.getText().toString() + "%'";
                    categoryMainItemsList = new ArrayList<>();
                    if (edittextValue.length() > 0) {
                        categorySearchMainItemsList = new ArrayList<>();
                        searchPageSize = 1;
                        CommonMethods.hideKeyboard(CourseList.this, (CourseList.this).getCurrentFocus());
                        getSearchCourseList(edittextValue, searchPageSize);
                    }
                    drawer_layout.closeDrawers();
                } else {
                    drawer_layout.closeDrawers();
                    textViewHeading.setVisibility(View.INVISIBLE);
                    editTextSearch.setVisibility(View.VISIBLE);
                    //iamgeViewNavigationdrawer.setVisibility(View.VISIBLE);
                    // iamgeViewNavigationdrawer.setOnClickListener(finishSearchlistener);
                    imageViewBackIcon.setVisibility(View.GONE);
                    drawerToggle.setDrawerIndicatorEnabled(false);

                    editTextSearch.setText("");
                    editTextSearch.requestFocus();
                    CommonMethods.showKeyboard(CourseList.this, (CourseList.this).getCurrentFocus());
                    textViewBrowseNocourseFound.setVisibility(View.GONE);
                    categoryMainItemsList = new ArrayList<>();
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                    imageViewSearchIcon.setOnClickListener(finishSearchlistener);

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener logOut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CourseList.this);
                alertDialogBuilder.setMessage(getApplicationContext().getResources().getString(R.string.logout));
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            {
                                LogOut logOut = new LogOut(CourseList.this);
                                logOut.execute("");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    };
    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                textViewHeading.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.INVISIBLE);
                editTextSearch.setText("");
                pageSize = 1;
                searchText = "1=1";
                categorySearchMainItemsList= new ArrayList<>();
                categoryMainItemsList = new ArrayList<>();
                if(isFrom.equalsIgnoreCase("ontext")) {

                }
                else  {
                    getCourseList(searchText, pageSize);
                }
                CommonMethods.hideKeyboard(CourseList.this, (CourseList.this).getCurrentFocus());
                iamgeViewNavigationdrawer.setVisibility(View.GONE);
                drawerToggle.setDrawerIndicatorEnabled(true);
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
                imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.opendrawer, R.string.closedrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                try {
                    if (CommonMethods.userid.length() > 0) {
                       // setDrawer();
                    } else {
                        textViewUserName.setText("Welcome Guest");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        drawer_layout.setDrawerListener(drawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try {
            drawerToggle.syncState();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            drawerToggle.onConfigurationChanged(newConfig);
        } catch (Exception t) {

        }
    }

    private void setDrawer() {
        try {
            if(CommonMethods.userid.length()>0) {
                String dealerName = "";
                if (CommonMethods.userName.length() > 0) {

                    dealerName = CommonMethods.userName;
                } /*else {
                dealerName = CommonMethods.user_name;
            }*/


                if ((dealerName.toString().length()) > 12) {
                    textViewUserName.setText("Welcome\n" + dealerName);
                } else {
                    textViewUserName.setText("Welcome " + dealerName);
                }
            }
            else{
                textViewUserName.setText("Welcome Guest");
            }
            if(CommonMethods.userid.length()>0){
                if (CommonMethods.profile_image_url != null) {
                    if (CommonMethods.profile_image_url.length() > 0) {
                        if (CommonMethods.profile_image_url.contains("http")) {

                            Glide.with(CourseList.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                   // .skipMemoryCache(true)
                                    .transform(new CircleTransform(CourseList.this))
                                   //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                   // .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        } else {
                            Glide.with(CourseList.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                                   // .skipMemoryCache(true)
                                    .transform(new CircleTransform(CourseList.this))
                                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                   // .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        }


                    } else {
                        Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                    }
                } else {
                    Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }

            }
            else {

                Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
           /* ProfilePictureClass serverData = new ProfilePictureClass(CourseList.this);
            String dealerName;
            String path;
            if (serverData.getCurrentUserName().length() > 0) {

                dealerName = serverData.getCurrentUserName();
            } else {
                dealerName = CommonMethods.user_name;
            }

            if ((dealerName.toString().length()) > 12) {
                textViewUserName.setText("Welcome\n" + dealerName);
            } else {
                textViewUserName.setText("Welcome " + dealerName);
            }



            path = serverData.getCurrentProfilePic();

            if (path != null) {
                if (path.length() > 0) {

                    Glide.with(CourseList.this).load(path)
                            //.skipMemoryCache(true)
                            .transform(new CircleTransform(CourseList.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            // .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);

                } else {
                    Picasso.with(CourseList.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            } else {
                Picasso.with(CourseList.this).load(R.drawable.eniversity).placeholder(R.drawable.eniversity).error(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




    private void getCourseList(final String searchText, final int pageSize)

    {
        categoryItemsList=new ArrayList<>();

        textViewBrowseNocourseFound.setVisibility(View.GONE);
        try{
           // progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
            JSONObject sendExploreObject = new JSONObject();

            sendExploreObject.put("action", "getbrowsecourse");
            sendExploreObject.put("wherecondition", searchText);
            sendExploreObject.put("pagenumber", String.valueOf(pageSize));
            sendExploreObject.put("pagesize", "5");
            sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
            String securekeyuser=sharedPreferences.getString("secure_key","");
            if(CommonMethods.userid.length()==0) {
                sendExploreObject.put("type", "guestuser");
                sendExploreObject.put("securedkey","");
            }
            else{

                sendExploreObject.put("type", "user");
                sendExploreObject.put("securedkey",securekeyuser);
            }

           // Log.i("Request:", sendExploreObject.toString());

            if (!loading) {

                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
            } else {
                if (pageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                }else if(pageSize>1){
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(CourseList.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                //    Log.i("the response is ", response.toString());



                    try {
                        categoryItemsList=new ArrayList<>();

                        String browseCatalogueitems = response.getString("BrowseCourse");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);



                        for(int i=0;i<browseArray.length();i++)
                        {
                            CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                            categoryItems.setCategoryName(browseArray.getJSONObject(i).getString("categoryname"));
                            categoryItems.setCategoryUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));
                            categoryItems.setCategoryId(browseArray.getJSONObject(i).getString("categoryid"));
                            categoryItems.setCategoryNoOfCourse(browseArray.getJSONObject(i).getString("noofcourses"));
                            categoryItems.setCategoryImage(browseArray.getJSONObject(i).getString("courseimage"));

                            categoryItemsList.add(categoryItems);

                        }

                        categoryMainItemsList.addAll(categoryItemsList);
                      //  categoryGetItemList.addAll(categoryMainItemsList);
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                        if(categoryMainItemsList.size()<1&& pageSize==1){
                            textViewBrowseNocourseFound.setVisibility(View.VISIBLE);
                        }else{
                            textViewBrowseNocourseFound.setVisibility(View.GONE);
                        }

                        if (!loading) {
                            adapter = new GridAdapterClass(CourseList.this, categoryMainItemsList, "courseList");
                            gridViweBrouwseCatalog.setAdapter(adapter);
                            progressBarBrowseCourseDetails.setVisibility(View.GONE);

                            if (categoryItemsList.size()>1) {
                                gridViweBrouwseCatalog.setOnScrollListener(scrollListener);
                            }else{
                                gridViweBrouwseCatalog.setOnScrollListener(null);
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }
                                }
                            });
                           // progressBarBrowseMoreCourseDetails.setVisibility(View.VISIBLE);
                        }
                        if (categoryItemsList.size()<=1) {
                            gridViweBrouwseCatalog.setOnScrollListener(null);
                            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                            loading = false;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCourseList(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        error.printStackTrace();

                    }
                    else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCourseList(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                }
            });

            requestQueue.add(customres);

        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
    }

        private void getSearchCourseList(String searchText, int searchPageSize)
    {
        categoryItemsList=new ArrayList<>();
        textViewBrowseNocourseFound.setVisibility(View.GONE);
        try{
            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
            JSONObject sendExploreObject = new JSONObject();

            sendExploreObject.put("action", "getbrowsecourse");
            sendExploreObject.put("wherecondition", searchText);
            sendExploreObject.put("pagenumber", String.valueOf(searchPageSize));
            sendExploreObject.put("pagesize", "3");

           // Log.i("Request:", sendExploreObject.toString());

            if (!loading) {

                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
            } else {
                if (pageSize == 1) {
                    progressBarBrowseCourseDetails.setVisibility(View.VISIBLE);
                }else if(pageSize>1){
                    progressBarBrowseCourseDetails.setVisibility(View.GONE);
                }
            }

            RequestQueue requestQueue = Volley.newRequestQueue(CourseList.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 //   Log.i("the response is ", response.toString());
                    try {categorySearchMainItemsList=new ArrayList<>();
                        categoryItemsList=new ArrayList<>();
                        String browseCatalogueitems = response.getString("BrowseCourse");



                        JSONArray browseArray = new JSONArray(browseCatalogueitems);

                        for(int i=0;i<browseArray.length();i++)
                        {
                            CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                            categoryItems.setCategoryName(browseArray.getJSONObject(i).getString("categoryname"));
                            categoryItems.setCategoryUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));
                            categoryItems.setCategoryId(browseArray.getJSONObject(i).getString("categoryid"));
                            categoryItems.setCategoryNoOfCourse(browseArray.getJSONObject(i).getString("noofcourses"));
                            categoryItems.setCategoryImage(browseArray.getJSONObject(i).getString("courseimage"));

                            categoryItemsList.add(categoryItems);
                        }
                        categorySearchMainItemsList.addAll(categoryItemsList);

                     //   Log.e("categorySearchMainItemsList.size()",""+categorySearchMainItemsList.size());
                        progressBarBrowseCourseDetails.setVisibility(View.GONE);
                        progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                        if(categorySearchMainItemsList.size()<1){
                            textViewBrowseNocourseFound.setVisibility(View.VISIBLE);
                        }else{
                            textViewBrowseNocourseFound.setVisibility(View.GONE);
                        }

                        if (!loading) {
                            adapter = new GridAdapterClass(CourseList.this, categorySearchMainItemsList, "courseList");
                            gridViweBrouwseCatalog.setAdapter(adapter);

                            progressBarBrowseCourseDetails.setVisibility(View.GONE);
                               /* if(categorySearchMainItemsList.size()>3) {


                                    gridViweBrouwseCatalog.setOnScrollListener(scrollListener);
                                }
                                else{*/
                            gridViweBrouwseCatalog.setOnScrollListener(null);
                            if (categoryItemsList.size() > 1) {


                               // }
                            }else{
/*
                                adapter = new GridAdapterClass(CourseList.this, categorySearchMainItemsList, "courseList");
                                gridViweBrouwseCatalog.setAdapter(adapter);

                                progressBarBrowseCourseDetails.setVisibility(View.GONE);*/
                                progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);

                                //gridViweBrouwseCatalog.setOnScrollListener(null);


                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {



                                    try {
                                        loading = false;
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }

                                }
                            });
                            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                        }



                        if (categoryItemsList.size() <= 1) {
                            gridViweBrouwseCatalog.setOnScrollListener(null);
                            progressBarBrowseMoreCourseDetails.setVisibility(View.GONE);
                            loading = false;
                        }


                    }
                    catch (Exception e)
                    {

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

            customres.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customres);

        }
        catch(Exception e)
        {
e.printStackTrace();
        }
    }

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
        if (editTextSearch.length() > 0) {

            searchPageSize++;
            getSearchCourseList(edittextValue, searchPageSize);
            loading = true;
        } else {
            Log.e("pagesize in browse",""+pageSize);

            pageSize++;
            loading = true;
            getCourseList(searchText, pageSize);
        }
    }
}
catch (Exception e){
    e.printStackTrace();
}
        }
    };
    private TextView.OnEditorActionListener textSearchListener = new TextView.OnEditorActionListener() {



        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				/*shopListProdutecsArray.clear();
				CommonMethods
						.hideSoftKeyboard(AllAndSpecialofferProductsPage.this);
				perPage = 10;
				pageNumber = 1;
				isFromFliter = 1;
				isScrrolling = 0;
				//searchedText = edittextSearch.getText().toString();
				searchedText = edittextSearchAllProducts.getText().toString();

				shopListProdutecsArray = new ArrayList<ShoppingitemsGetSet>();

				optionName = "searchProducts";
				loadProdutesAs = new loadallShopingProducts();
				loadProdutesAs.execute(optionName, String.valueOf(perPage),
						String.valueOf(pageNumber), searchedText);

				return true;*/

                CommonMethods.hideKeyboard(CourseList.this,(CourseList.this).getCurrentFocus());
                return true;
            }
            return false;
        }
    };

}
