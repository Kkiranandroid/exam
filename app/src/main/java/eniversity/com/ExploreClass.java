package eniversity.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.eniversity.app.R;


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
import adapter.DemoCatogoryAdapter;
import data.base.LoginTable;
import get.set.CategoryGetSet;
import get.set.CategoryGetSetClass;
import get.set.HomeCategoryGetSet;
import login.page.LoginMainPage;
import login.page.ProfilePictureClass;

/**
 * Created by shveta on 3/22/2016.
 */
public class ExploreClass extends AppCompatActivity {


    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawerToggle;


    private Toolbar toolbar;

    private EditText editTextSearch;

    private ImageView iamgeViewNavigationdrawer;
    private View linearLayoutExploreCourse;
    private View includelayoutnavigationDrawer;

    private ImageView linearLayoutEditProfile;
    private LinearLayout linearLayoutEditRecCourse;
    private View linearLayoutBrowse;
    private View linearLayoutToppers;
    private View linearLayoutRegister;
    private View linearLayoutContactUs;
    private View linearLayoutFAQ;
    private ImageView linearLayoutYOurCourse;
    private LinearLayout linearLayoutSubScription;
    private ImageView linearLayoutLogout;
    private View linearLayoutAboutUs;
    private ImageView imageViewProfilePic;

    private ImageView imageViewSearchIcon;
    private ImageView imageViewSearchCancel;
    private ProgressBar progressbarLoadExploreCourse;
    private ProgressBar progressbarLoadMoreCourse;

    private ImageView imageViewBackIcon;
    private TextView textViewHeading;
    private TextView textViewExploreNoCourseFound;
    private ArrayList<CategoryGetSetClass> categoryItemsList;

    private String searchText = "";
    private String edittextValue = "";
    private String isFrom = "";
    ArrayList<CategoryGetSet> categoryGetSetArrayList;


    private DemoCatogoryAdapter adapter;
    private ListView listViewLoadAllViewPager;

    private int pageSize = 1;
    private int searchPageSize = 1;

    private boolean loading = false;
    private TextView textViewUserName;
    private ArrayList<HomeCategoryGetSet> exploreMainArray;
    private ArrayList<HomeCategoryGetSet> searchMainArray;
    private ArrayList<HomeCategoryGetSet> homeListManArray;
    private ArrayList<HomeCategoryGetSet> exploreArrayList;


    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_layout);

        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
            textViewExploreNoCourseFound = (TextView) findViewById(R.id.textViewExploreNoCourseFound);

            imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);

            imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
            imageViewSearchCancel= (ImageView) toolbar.findViewById(R.id.imageViewSearchCancel);

            editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
            exploreMainArray = new ArrayList<>();
            homeListManArray = new ArrayList<>();

            exploreArrayList = new ArrayList<>();
            categoryItemsList = new ArrayList<>();
            searchMainArray = new ArrayList<>();


        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

try {
    textViewHeading.setText("Explore Courses");

    progressbarLoadMoreCourse = (ProgressBar) findViewById(R.id.progressbarLoadMoreCourse);
    progressbarLoadExploreCourse = (ProgressBar) findViewById(R.id.progressbarLoadExploreCourse);
    drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
    includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);
    imageViewProfilePic = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.imageViewProfilePic);
    textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);

    listViewLoadAllViewPager = (ListView) findViewById(R.id.listViewLoadAllViewPager);
    initDrawer();
    iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
    linearLayoutRegister = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutRegister);
    linearLayoutRegister.setVisibility(View.GONE);

    linearLayoutEditProfile = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditProfile);
    linearLayoutEditRecCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditRecCourse);

    linearLayoutEditRecCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ExploreClass.this, EditRecommendedCourses.class);
            startActivity(intent);


        }
    });
    linearLayoutEditProfile.setVisibility(View.VISIBLE);
    linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, RegistrationClass.class);
            i.putExtra("activityIntent", "ExploreClass");
            startActivity(i);
            finish();
        }
    });

    LoginTable loginGetUserName = new LoginTable(ExploreClass.this);
    loginGetUserName.open();
    linearLayoutYOurCourse = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutYOurCourse);

    loginGetUserName.close();


    linearLayoutYOurCourse.setVisibility(View.VISIBLE);
    linearLayoutYOurCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, LoginMainPage.class);
            startActivity(i);
            finish();
            //drawer_layout.closeDrawer(Gravity.LEFT);

        }
    });
    linearLayoutSubScription = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutSubScription);
    linearLayoutSubScription.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ExploreClass.this, SubScription.class);
            startActivity(i);
        }
    });

    linearLayoutToppers = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutToppers);
    linearLayoutToppers.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, ToppersClass.class);
            startActivity(i);
            finish();
        }
    });
    linearLayoutFAQ = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutFAQ);
    linearLayoutFAQ.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, FrequentlyAskQuesionsClass.class);
            startActivity(i);

        }
    });


    linearLayoutExploreCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutExploreCourse);
    linearLayoutExploreCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawer_layout.closeDrawer(Gravity.LEFT);
        }
    });

    linearLayoutBrowse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutBrowse);
    linearLayoutBrowse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, CourseList.class);
            startActivity(i);
            finish();
        }
    });
    linearLayoutAboutUs = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutAboutUs);
    linearLayoutAboutUs.setVisibility(View.VISIBLE);
    linearLayoutAboutUs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, About.class);
            startActivity(i);

        }
    });
    linearLayoutContactUs = includelayoutnavigationDrawer.findViewById(R.id.linearLayoutContactUs);
    linearLayoutContactUs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExploreClass.this, Contact_us.class);
            startActivity(i);

        }
    });
    linearLayoutLogout = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutLogout);
    linearLayoutLogout.setVisibility(View.VISIBLE);
    linearLayoutLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ExploreClass.this);
            alertDialogBuilder.setMessage(getApplicationContext().getResources().getString(R.string.logout));
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        {
                            LogOut logOut = new LogOut(ExploreClass.this);
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
    });

    if (CommonMethods.userid.length() > 0) {


        linearLayoutRegister.setVisibility(View.GONE);
        linearLayoutRegister.setVisibility(View.GONE);
       // setDrawer();

    } else {
        linearLayoutEditRecCourse.setVisibility(View.GONE);
        linearLayoutEditProfile.setVisibility(View.GONE);
        linearLayoutRegister.setVisibility(View.VISIBLE);
        linearLayoutLogout.setVisibility(View.GONE);
        linearLayoutYOurCourse.setVisibility(View.GONE);
        linearLayoutSubScription.setVisibility(View.GONE);

        textViewUserName.setText("Welcome Guest");
    }

    imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
    //imageViewSearchCancel.setOnClickListener(imageViewSearchCancellistner);


    searchText = "1=1";
    exploreMainArray = new ArrayList<>();
    getExploreCourseDetails(searchText, pageSize);
    editTextSearch.setOnEditorActionListener(textSearchListener);
    editTextSearch.addTextChangedListener(textChengListener);

    try {
        includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);
        linearLayoutExploreCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutExploreCourse);

        linearLayoutExploreCourse.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
                                                             drawer_layout.closeDrawer(Gravity.LEFT);
                                                         }
                                                     }
        );
        linearLayoutRegister = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutRegister);
        linearLayoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExploreClass.this, MainActivity.class);
                // i.putExtra("activityIntent", "ExploreClass");
                startActivity(i);
                finish();
            }
        });

        linearLayoutBrowse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutBrowse);
        linearLayoutBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExploreClass.this, CourseList.class);
                startActivity(i);
                finish();
            }
        });

        linearLayoutFAQ = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutFAQ);
        linearLayoutFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExploreClass.this, FrequentlyAskQuesionsClass.class);
                startActivity(i);

            }
        });

    } catch (Exception e) {

    }
    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            try {
                pageSize = 1;
                searchMainArray = new ArrayList<>();
                exploreMainArray = new ArrayList<>();
                searchText = "1=1";

                editTextSearch.setVisibility(View.GONE);
                textViewHeading.setVisibility(View.VISIBLE);
                drawerToggle.setDrawerIndicatorEnabled(true);
                iamgeViewNavigationdrawer.setVisibility(View.GONE);
                editTextSearch.setText("");
                edittextValue = "";

                categoryItemsList = new ArrayList<>();
                getExploreCourseDetails(searchText, pageSize);
                swipeRefreshLayout.setRefreshing(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    });

   /* ProfilePictureClass serverData = new ProfilePictureClass(ExploreClass.this);
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


    if (path != null) {
        if (path.length() > 0) {

            Glide.with(ExploreClass.this).load(path)

                    .transform(new CircleTransform(ExploreClass.this))

                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.eniversity)
                    .error(R.drawable.eniversity).into(imageViewProfilePic);


        } else {
            Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                    .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
        }
    }
    else{
        Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
    }*/
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

                    Glide.with(ExploreClass.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                            //.skipMemoryCache(true)
                            .transform(new CircleTransform(ExploreClass.this))
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                           // .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                } else {
                    Glide.with(ExploreClass.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                            //.skipMemoryCache(true)
                            .transform(new CircleTransform(ExploreClass.this))
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            //.diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                }


            } else {
                Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
        } else {
            Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                    .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
        }

    }
    else {

        Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
    }

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

                exploreMainArray = new ArrayList<>();
                searchMainArray = new ArrayList<>();
                if (editTextSearch.length() > 0) {
                    searchPageSize = 1;
                    edittextValue = "cr.coursename like '%" + s.toString() + "%'";
                    getSearchExploreCourseDetails(edittextValue, searchPageSize);
                }
                else{
                    isFrom="ontext";
                    searchText="1=1";
                    pageSize=1;
                    getExploreCourseDetails(searchText, pageSize);

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
            // Toast.makeText(ExploreClass.this,"inside serch",Toast.LENGTH_SHORT).show();

            try {
                textViewExploreNoCourseFound.setVisibility(View.GONE);
                if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                    edittextValue = "cr.coursename like '%" + editTextSearch.getText().toString() + "%'";
                    searchPageSize = 1;
                    CommonMethods.hideKeyboard(ExploreClass.this, (ExploreClass.this).getCurrentFocus());
                    exploreMainArray = new ArrayList<>();
                    searchMainArray = new ArrayList<>();
                    if (edittextValue.length() > 0) {
                        getSearchExploreCourseDetails(edittextValue, searchPageSize);
                    }
                    drawer_layout.closeDrawers();
                } else {
                    drawer_layout.closeDrawers();
                    textViewHeading.setVisibility(View.INVISIBLE);
                    editTextSearch.setVisibility(View.VISIBLE);
                    // iamgeViewNavigationdrawer.setVisibility(View.VISIBLE);
                    //imageViewSearchCancel.setVisibility(View.VISIBLE);
                    editTextSearch.setText("");
                    exploreMainArray = new ArrayList<>();
                    editTextSearch.requestFocus();
                    CommonMethods.showKeyboard(ExploreClass.this, (ExploreClass.this).getCurrentFocus());
                    //iamgeViewNavigationdrawer.setOnClickListener(finishSearchlistener);
                    imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                    imageViewSearchIcon.setOnClickListener(finishSearchlistener);
                    imageViewBackIcon.setVisibility(View.GONE);
                    drawerToggle.setDrawerIndicatorEnabled(false);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };
  /*  private  View.OnClickListener imageViewSearchCancellistner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
*/
    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                CommonMethods.hideKeyboard(ExploreClass.this, (ExploreClass.this).getCurrentFocus());
                textViewHeading.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.INVISIBLE);
                editTextSearch.setText("");
                pageSize = 1;
                searchMainArray = new ArrayList<>();
                exploreMainArray = new ArrayList<>();
                searchText = "1=1";
                //CommonMethods.showKeyboard(ExploreClass.this,(ExploreClass.this).getCurrentFocus());
                if(isFrom.equalsIgnoreCase("ontext")) {

                }
                else{
                    getExploreCourseDetails(searchText, pageSize);
                }
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
                      //  setDrawer();
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

                            Glide.with(ExploreClass.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                   // .skipMemoryCache(true)
                                    .transform(new CircleTransform(ExploreClass.this))
                                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        } else {
                            Glide.with(ExploreClass.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                                   //.skipMemoryCache(true)
                                    .transform(new CircleTransform(ExploreClass.this))
                                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        }


                    } else {
                        Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                    }
                } else {
                    Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }

            }
            else {

                Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
            /*ProfilePictureClass serverData = new ProfilePictureClass(ExploreClass.this);
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


        if (serverData.getCurrentProfilePic().length() > 0) {
            path = serverData.getCurrentProfilePic();
        } else {
            path = CommonMethods.image_path;
        }

            if (path != null) {
                if (path.length() > 0) {
                Picasso.with(this).load(path).placeholder(R.drawable.eniversity).transform(new RoundedTransformation(800, 10))
                        .resize(250, 250).centerCrop().into(imageViewProfilePic);
                    Glide.with(ExploreClass.this).load(path)
                            .transform(new CircleTransform(ExploreClass.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);

                Glide.with(ExploreClass.this)
                        .load(path)
                        .asBitmap()
                        .placeholder(R.drawable.eniversity)
                        .error(R.drawable.eniversity)
                        .into(new BitmapImageViewTarget(imageViewProfilePic) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                                        Bitmap.createScaledBitmap(resource, 300, 300, false));
                                drawable.setCircular(true);

                                imageViewProfilePic.setImageDrawable(drawable);
                            }
                        });

                } else {
                    Picasso.with(ExploreClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            } else {
                Picasso.with(ExploreClass.this).load(R.drawable.eniversity).placeholder(R.drawable.eniversity).error(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getExploreCourseDetails(final String searchText, final int pageSize) {
        try {


            textViewExploreNoCourseFound.setVisibility(View.GONE);
            //  exploreArrayList = new ArrayList<>();
            categoryItemsList = new ArrayList<>();

            JSONObject sendExploreObject = new JSONObject();
            sendExploreObject.put("action", "getexplorecourse");
            sendExploreObject.put("wherecondition", searchText);
            sendExploreObject.put("pagenumber", String.valueOf(pageSize));
            sendExploreObject.put("pagesize", "3");
           /* if(CommonMethods.userid.length()==0) {
                sendExploreObject.put("type", "guestuser");
                sendExploreObject.put("securedkey","");
            }
            else{
                sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES,Context.MODE_PRIVATE);
                String securekeyuser=sharedPreferences.getString("secure_key","");
                sendExploreObject.put("type", "user");
                sendExploreObject.put("securedkey",securekeyuser);
            }*/




            Log.i("Request:", sendExploreObject.toString());


            if (!loading) {

                progressbarLoadMoreCourse.setVisibility(View.GONE);
            } else {
                if (pageSize == 1) {
                    progressbarLoadExploreCourse.setVisibility(View.VISIBLE);
                } else if (pageSize > 1) {
                    progressbarLoadExploreCourse.setVisibility(View.GONE);
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(ExploreClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String securekey="";
                    Log.i("the response is ", response.toString());

                    try { categoryItemsList = new ArrayList<>();
                        exploreArrayList = new ArrayList<>();
                        String exploreString = response.getString("ExploreCourse");
                        JSONArray exploreCourseArray = new JSONArray(exploreString);
                        if(CommonMethods.userid.length()==0){
                            String securekeyString=response.getString("key");
                            JSONArray securekeyarr=new JSONArray(securekeyString);
                            securekey=securekeyarr.getJSONObject(0).getString("securedkey");
                            sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("type","guestuser");
                            editor.putString("secure_key",securekey);
                            editor.commit();
                            CommonMethods.securedkeyguest=sharedPreferences.getString("secure_key","");
                            Log.v("secured key explore", CommonMethods.securedkeyguest);
                        }

                        for (int i = 0; i < exploreCourseArray.length(); i++) {

                            HomeCategoryGetSet exploreItems = new HomeCategoryGetSet();
                            exploreItems.setCategroyName(exploreCourseArray.getJSONObject(i).getString("categoryname"));
                            exploreItems.setCategoryId(exploreCourseArray.getJSONObject(i).getString("categoryid"));
                            exploreItems.setCoursecount(exploreCourseArray.getJSONObject(i).getString("coursecount"));


                            String getDetails = (exploreCourseArray.getJSONObject(i).getString("categoryname"));

                            categoryItemsList = new ArrayList<>();

                            JSONArray getDetailsArray = new JSONArray(response.getString(getDetails));
                            for (int j = 0; j < getDetailsArray.length(); j++) {
                                CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                                categoryItems.setCourseId(getDetailsArray.getJSONObject(j).getString("courseid"));
                                categoryItems.setCategoryImage(getDetailsArray.getJSONObject(j).getString("courseimage"));
                                categoryItems.setCategoryUsers(getDetailsArray.getJSONObject(j).getString("noofsubscribers"));
                                categoryItems.setCategoryRatings(getDetailsArray.getJSONObject(j).getString("rating"));
                                categoryItems.setCategoryTitle(getDetailsArray.getJSONObject(j).getString("coursename"));
                                categoryItems.setCategoryadvancefees(getDetailsArray.getJSONObject(j).getString("advancefees"));
                                categoryItems.setCategorybasicfees(getDetailsArray.getJSONObject(j).getString("basicfees"));
                                categoryItems.setCategoryintermediatefees(getDetailsArray.getJSONObject(j).getString("intermediatefees"));
                                categoryItems.setCategorytotalfees(getDetailsArray.getJSONObject(j).getString("totalfees"));
                                categoryItems.setCategoryId(getDetailsArray.getJSONObject(j).getString("categoryid"));
                                categoryItemsList.add(categoryItems);

                            }


                            exploreItems.setMenList(categoryItemsList);
                            exploreArrayList.add(exploreItems);

                        }


                        exploreMainArray.addAll(exploreArrayList);
                        Log.e("exploreMainArray size",""+exploreMainArray.size());
                        progressbarLoadMoreCourse.setVisibility(View.GONE);
                        progressbarLoadExploreCourse.setVisibility(View.GONE);


                        if (exploreMainArray.size() < 1 && pageSize == 1) {
                            textViewExploreNoCourseFound.setVisibility(View.VISIBLE);
                        } else {
                            textViewExploreNoCourseFound.setVisibility(View.GONE);

                        }


                        if (!loading) {

                            adapter = new DemoCatogoryAdapter(ExploreClass.this, exploreMainArray, getSupportFragmentManager(),editTextSearch.getText().toString(),editTextSearch,drawerToggle,imageViewSearchIcon,textViewHeading);
                            listViewLoadAllViewPager.setAdapter(adapter);
                            if (exploreArrayList.size() > 1) {


                                listViewLoadAllViewPager.setOnScrollListener(scrollListener);
                            } else {
                                listViewLoadAllViewPager.setOnScrollListener(null);
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


                        }


                        if (exploreArrayList.size() <= 1) {
                            listViewLoadAllViewPager.setOnScrollListener(null);
                            progressbarLoadMoreCourse.setVisibility(View.GONE);
                            loading = false;
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {


                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getExploreCourseDetails(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressbarLoadExploreCourse.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                    else {

                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getExploreCourseDetails(searchText, pageSize);
                                    }
                                });
                        snackbar.show();
                        progressbarLoadExploreCourse.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                }

            });
            customres.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customres);
        } catch (Exception r) {
            r.printStackTrace();

        }
    }




    public void getSearchExploreCourseDetails(String searchText, int searchPageSize) {
        try {

            textViewExploreNoCourseFound.setVisibility(View.GONE);
            exploreArrayList = new ArrayList<>();
            categoryItemsList = new ArrayList<>();
            JSONObject sendExploreObject = new JSONObject();
            sendExploreObject.put("action", "getexplorecourse");

            sendExploreObject.put("wherecondition", searchText);


            sendExploreObject.put("pagenumber", String.valueOf(searchPageSize));
            sendExploreObject.put("pagesize", "3");

            Log.i("Request:", sendExploreObject.toString());


            if (!loading) {

                progressbarLoadMoreCourse.setVisibility(View.GONE);
            } else {
                progressbarLoadExploreCourse.setVisibility(View.GONE);
            }

            RequestQueue requestQueue = Volley.newRequestQueue(ExploreClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendExploreObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String securekey="";
                    Log.i("the response is ", response.toString());

                    searchMainArray = new ArrayList<>();
                    try {
                        exploreArrayList = new ArrayList<>();

                        String exploreString = response.getString("ExploreCourse");
                        JSONArray exploreCourseArray = new JSONArray(exploreString);

                        if(CommonMethods.userid.length()==0){
                            String securekeyString=response.getString("key");
                            JSONArray securekeyarr=new JSONArray(securekeyString);
                            securekey=securekeyarr.getJSONObject(0).getString("securedkey");
                            sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("type","guestuser");
                            editor.putString("secure_key",securekey);
                            editor.commit();
                            CommonMethods.securedkeyguest=securekeyarr.getJSONObject(0).getString("securedkey");
                        }
                        for (int i = 0; i < exploreCourseArray.length(); i++) {

                            HomeCategoryGetSet exploreItems = new HomeCategoryGetSet();
                            exploreItems.setCategroyName(exploreCourseArray.getJSONObject(i).getString("categoryname"));
                            exploreItems.setCategoryId(exploreCourseArray.getJSONObject(i).getString("categoryid"));
                            exploreItems.setCoursecount(exploreCourseArray.getJSONObject(i).getString("coursecount"));


                            String getDetails = (exploreCourseArray.getJSONObject(i).getString("categoryname"));

                            categoryItemsList = new ArrayList<>();

                            JSONArray getDetailsArray = new JSONArray(response.getString(getDetails));
                            for (int j = 0; j < getDetailsArray.length(); j++) {
                                CategoryGetSetClass categoryItems = new CategoryGetSetClass();
                                categoryItems.setCourseId(getDetailsArray.getJSONObject(j).getString("courseid"));
                                categoryItems.setCategoryImage(getDetailsArray.getJSONObject(j).getString("courseimage"));
                                categoryItems.setCategoryUsers(getDetailsArray.getJSONObject(j).getString("noofsubscribers"));
                                categoryItems.setCategoryRatings(getDetailsArray.getJSONObject(j).getString("rating"));
                                categoryItems.setCategoryTitle(getDetailsArray.getJSONObject(j).getString("coursename"));
                                categoryItems.setCategoryadvancefees(getDetailsArray.getJSONObject(j).getString("advancefees"));
                                categoryItems.setCategorybasicfees(getDetailsArray.getJSONObject(j).getString("basicfees"));
                                categoryItems.setCategoryintermediatefees(getDetailsArray.getJSONObject(j).getString("intermediatefees"));
                                categoryItems.setCategorytotalfees(getDetailsArray.getJSONObject(j).getString("totalfees"));
                                categoryItems.setCategoryId(getDetailsArray.getJSONObject(j).getString("categoryid"));


                                categoryItemsList.add(categoryItems);

                            }
                            exploreItems.setMenList(categoryItemsList);
                            exploreArrayList.add(exploreItems);


                        }


                        searchMainArray.addAll(exploreArrayList);
                        progressbarLoadExploreCourse.setVisibility(View.GONE);

                        if (!loading) {
                            adapter = new DemoCatogoryAdapter(ExploreClass.this, searchMainArray, getSupportFragmentManager(),editTextSearch.getText().toString(),editTextSearch,drawerToggle,imageViewSearchIcon,textViewHeading);
                            listViewLoadAllViewPager.setAdapter(adapter);
                            if (searchMainArray.size() > 1) {
                                progressbarLoadExploreCourse.setVisibility(View.GONE);
                                loading = false;
                                listViewLoadAllViewPager.setOnScrollListener(scrollListener);
                            } else {
                                progressbarLoadExploreCourse.setVisibility(View.GONE);
                                loading = false;
                                listViewLoadAllViewPager.setOnScrollListener(null);
                            }
                            if (searchMainArray.size() < 1) {
                                textViewExploreNoCourseFound.setVisibility(View.VISIBLE);
                            } else {
                                textViewExploreNoCourseFound.setVisibility(View.GONE);
                            }
                            progressbarLoadMoreCourse.setVisibility(View.GONE);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {



                                    try { loading = false;
                                        adapter.notifyDataSetInvalidated();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception w) {
                                        w.printStackTrace();
                                    }

                                }
                            });
                            progressbarLoadMoreCourse.setVisibility(View.VISIBLE);
                        }


                        if (exploreArrayList.size() <= 1) {
                            listViewLoadAllViewPager.setOnScrollListener(null);
                            progressbarLoadMoreCourse.setVisibility(View.GONE);
                            loading = false;
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarLoadExploreCourse.setVisibility(View.GONE);
                    error.printStackTrace();
                }
            });
            requestQueue.add(customres);
        } catch (Exception r) {
            r.printStackTrace();

        }
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            try {
                if ((listViewLoadAllViewPager.getLastVisiblePosition() == listViewLoadAllViewPager.getAdapter().getCount() - 1
                        && listViewLoadAllViewPager.getChildAt(listViewLoadAllViewPager.getChildCount() - 1).getBottom() <= listViewLoadAllViewPager.getHeight())) {
                    progressbarLoadMoreCourse.setVisibility(View.VISIBLE);
                    if (editTextSearch.length() > 0) {

                        searchPageSize++;
                        loading = true;
                        getSearchExploreCourseDetails(edittextValue, searchPageSize);

                    } else {
                        Log.e("pagesize explore",""+pageSize);

                        pageSize++;
                        loading = true;
                        getExploreCourseDetails(searchText, pageSize);
                    }
                }
            } catch (Exception e) {
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


                CommonMethods.hideKeyboard(ExploreClass.this,(ExploreClass.this).getCurrentFocus());
                return true;
            }
            return false;
        }
    };



}
