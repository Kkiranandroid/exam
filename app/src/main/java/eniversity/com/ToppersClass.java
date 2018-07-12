package eniversity.com;

import android.app.AlertDialog;
import android.app.Dialog;

import com.android.volley.NoConnectionError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.eniversity.app.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import android.view.Window;

import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import adapter.CoursePopupAdapter;

import adapter.TopperAdapter;

import get.set.CoursePopupGetSet;
import get.set.TopperGetSet;

import login.page.LoginMainPage;
import login.page.ProfilePictureClass;

/**
 * Created by kirankumar on 3/24/2016.
 */
public class ToppersClass extends AppCompatActivity {
    private ProgressBar progressbarTopperClass;
    private ListView listviewTopperList;
    private ImageView imageViewBackIcon;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawerToggle;
    private View linearLayoutExploreCourse;
    private View includelayoutnavigationDrawer;

    private ImageView linearLayoutEditProfile;
    private LinearLayout linearLayoutBrowse;
    private LinearLayout linearLayoutToppers;
    private LinearLayout linearLayoutRegister;
    private LinearLayout linearLayoutFAQ;
    private LinearLayout linearLayoutSubScription;
    private LinearLayout linearLayoutEditRecCourse;

    private ImageView linearLayoutYOurCourse;
    private LinearLayout linearLayoutAboutUs;
    private LinearLayout linearLayoutContactUs;
    private ImageView linearLayoutLogout;
    private TextView textViewUserName;
    private ImageView iamgeViewNavigationdrawer;
    private ImageView imageViewProfilePic;

    private ImageView imageViewSearchIcon;
    private EditText editTextSearch;
    private ArrayList<TopperGetSet> topperSlist;
    private ArrayList<TopperGetSet> topperMainSlist;


    private ArrayList<CoursePopupGetSet> courseArrayPopupGetSets;
    private ArrayList<CoursePopupGetSet> courseMainArrayPopupGetSets;

    private ArrayList<TopperGetSet> topperSearchList;
    private Toolbar toolbar;
    private TextView textViewHeading;
    private TextView textViewHeading3;

    private String searchText = "1=1";
    private String edittextValue = "";
    private String courseChange = "";

    private int pageSize = 1;
    private int searchPageSize = 1;
    private boolean loading = false;
    private ListView listViewCourseList;

    private TextView textViewManage;
    private String course = "All Category Toppers";
    private TextView textViewNoRecordFound;
    private ProgressBar progressbarMoreTopperClass;
    private TopperAdapter adapter;
    private TextView textViewChangeCourse;

    private ProgressBar progressbarPopupCourselist;
    private CoursePopupAdapter adapter1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private String searchTextcourse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topper_main_layout);
        try {
            listviewTopperList = (ListView) findViewById(R.id.listviewTopperList);
            textViewChangeCourse = (TextView) findViewById(R.id.textViewChangeCourse);
            textViewManage = (TextView) findViewById(R.id.textViewManage);
            try {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
                // textViewHeading3= (TextView) toolbar.findViewById(R.id.textViewHeading3);

                try {
                    textViewHeading.setText("Toppers");
              /*  textViewHeading3.setVisibility(View.VISIBLE);
                textViewHeading.setVisibility(View.GONE);*/
                    // textViewHeading3.setText("TOPPERS");
                } catch (Exception e) {
                }
                imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
                imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
                progressbarTopperClass = (ProgressBar) findViewById(R.id.progressbarTopperClass);
                progressbarMoreTopperClass = (ProgressBar) findViewById(R.id.progressbarMoreTopperClass);
                editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
                editTextSearch.setOnEditorActionListener(textSearchListener);
                textViewNoRecordFound = (TextView) findViewById(R.id.textViewNoRecordFound);
                coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
            drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout_Users);
            includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);

            imageViewProfilePic = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.imageViewProfilePic);
            textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);
            iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
            initDrawer();

            linearLayoutRegister = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutRegister);
            linearLayoutRegister.setVisibility(View.GONE);
            linearLayoutRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, MainActivity.class);
                    //i.putExtra("activityIntent", "ToppersClass");
                    startActivity(i);
                    finish();
                }
            });

            linearLayoutEditProfile = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditProfile);
            linearLayoutEditProfile.setVisibility(View.VISIBLE);
            linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, RegistrationClass.class);
                    i.putExtra("activityIntent", "ToppersClass");
                    startActivity(i);
                    finish();
                }
            });
            linearLayoutEditRecCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditRecCourse);

            linearLayoutEditRecCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ToppersClass.this, EditRecommendedCourses.class);
                    startActivity(intent);


                }
            });

            linearLayoutYOurCourse = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutYOurCourse);
            linearLayoutYOurCourse.setVisibility(View.VISIBLE);
            linearLayoutYOurCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, LoginMainPage.class);
                    startActivity(i);
                    finish();
                }
            });

            linearLayoutToppers = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutToppers);
            linearLayoutToppers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer_layout.closeDrawer(Gravity.LEFT);
                }
            });

            linearLayoutFAQ = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutFAQ);
            linearLayoutFAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, FrequentlyAskQuesionsClass.class);
                    startActivity(i);

                }
            });
            linearLayoutSubScription = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutSubScription);
            linearLayoutSubScription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ToppersClass.this, SubScription.class);
                    startActivity(i);
                }
            });


            linearLayoutExploreCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutExploreCourse);
            linearLayoutExploreCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, ExploreClass.class);
                    startActivity(i);
                    finish();
                }
            });

            linearLayoutBrowse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutBrowse);
            linearLayoutBrowse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, CourseList.class);
                    startActivity(i);
                    finish();
                }
            });

            linearLayoutContactUs = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutContactUs);
            linearLayoutContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, Contact_us.class);
                    startActivity(i);
                }
            });

            linearLayoutAboutUs = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutAboutUs);
            linearLayoutAboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ToppersClass.this, About.class);
                    startActivity(i);
                }
            });

            linearLayoutLogout = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutLogout);
            linearLayoutLogout.setOnClickListener(logout);

            if (CommonMethods.userid.length() > 0) {
                linearLayoutRegister.setVisibility(View.GONE);
                linearLayoutLogout.setVisibility(View.VISIBLE);
              // setDrawer();
            } else {
                linearLayoutSubScription.setVisibility(View.GONE);
                linearLayoutEditRecCourse.setVisibility(View.GONE);
                linearLayoutEditProfile.setVisibility(View.GONE);
                linearLayoutLogout.setVisibility(View.GONE);
                linearLayoutYOurCourse.setVisibility(View.GONE);
                linearLayoutRegister.setVisibility(View.VISIBLE);
                textViewUserName.setText("Welcome Guest");
            }

            imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
            topperSlist = new ArrayList<>();
            topperMainSlist = new ArrayList<>();
            topperSearchList = new ArrayList<>();
            topperMainSlist = new ArrayList<>();
            getTopersList("1=1", pageSize);
            courseArrayPopupGetSets = new ArrayList<>();
            courseMainArrayPopupGetSets = new ArrayList<>();
            textViewChangeCourse.setOnClickListener(OpenTopperPopUp);
            editTextSearch.addTextChangedListener(textChengListener);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(refresh);
        } catch (Exception e) {
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

                            Glide.with(ToppersClass.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                    //.skipMemoryCache(true)
                                    .transform(new CircleTransform(ToppersClass.this))
                                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        } else {
                            Glide.with(ToppersClass.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                                   // .skipMemoryCache(true)
                                    .transform(new CircleTransform(ToppersClass.this))
                                   // .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        }


                    } else {
                        Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                    }
                } else {
                    Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }

            }
            else {

                Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
            /*ProfilePictureClass serverData = new ProfilePictureClass(ToppersClass.this);
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

                    Glide.with(ToppersClass.this).load(path)

                            .transform(new CircleTransform(ToppersClass.this))

                            .diskCacheStrategy(DiskCacheStrategy.ALL)

                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);
                } else {
                    Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            } else {
                Picasso.with(ToppersClass.this).load(R.drawable.eniversity).placeholder(R.drawable.eniversity).error(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    SwipeRefreshLayout.OnRefreshListener refresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            progressbarTopperClass.setVisibility(View.GONE);
            progressbarMoreTopperClass.setVisibility(View.GONE);
            topperSlist = new ArrayList<>();
            topperSlist.clear();
            topperMainSlist = new ArrayList<>();
            topperMainSlist.clear();
            topperSearchList = new ArrayList<>();
            topperSearchList.clear();
            drawerToggle.setDrawerIndicatorEnabled(true);
            iamgeViewNavigationdrawer.setVisibility(View.GONE);
            searchText = "1=1";
            pageSize = 1;
            course = "All Category Toppers";
            loading = false;
            progressbarTopperClass.setVisibility(View.VISIBLE);
            textViewHeading.setVisibility(View.VISIBLE);
         /*   textViewHeading.setVisibility(View.GONE);
            textViewHeading3.setVisibility(View.VISIBLE);*/
            editTextSearch.setVisibility(View.GONE);
            textViewNoRecordFound.setVisibility(View.GONE);
            getTopersList("1=1", 1);
            edittextValue = "";
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    private View.OnClickListener onClickSearchIcon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                if (edittextValue.length() > 0) {
                    searchPageSize = 1;
                    CommonMethods.hideKeyboard(ToppersClass.this, (ToppersClass.this).getCurrentFocus());
                    topperSearchList = new ArrayList<>();
                    textViewNoRecordFound.setVisibility(View.GONE);
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                drawer_layout.closeDrawers();
            } else {
                drawer_layout.closeDrawers();
                textViewHeading.setVisibility(View.INVISIBLE);
            /*    textViewHeading.setVisibility(View.GONE);
                textViewHeading3.setVisibility(View.INVISIBLE);*/
                editTextSearch.setVisibility(View.VISIBLE);
                //iamgeViewNavigationdrawer.setVisibility(View.VISIBLE);
                // iamgeViewNavigationdrawer.setOnClickListener(finishSearchlistener);
                imageViewBackIcon.setVisibility(View.GONE);
                textViewNoRecordFound.setVisibility(View.GONE);
                drawerToggle.setDrawerIndicatorEnabled(false);
                topperSlist = new ArrayList<>();
                editTextSearch.requestFocus();
                CommonMethods.showKeyboard(ToppersClass.this, (ToppersClass.this).getCurrentFocus());
                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                imageViewSearchIcon.setOnClickListener(finishSearchlistener);
            }
        }
    };
    private TextWatcher textChengListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchPageSize = 1;

            edittextValue = "c.coursename like '%" + s.toString() + "%'";
            if (edittextValue.length() > 0) {
                topperSlist = new ArrayList<>();
                topperSearchList = new ArrayList<>();
                topperMainSlist = new ArrayList<>();
                try {
                    getSearchTopersList(edittextValue, searchPageSize);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                textViewNoRecordFound.setVisibility(View.GONE);
                topperSlist = new ArrayList<>();
                topperMainSlist = new ArrayList<>();
                topperSearchList = new ArrayList<>();
                getTopersList("1=1", 1);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ToppersClass.this);
            alertDialogBuilder.setMessage(getApplicationContext().getResources().getString(R.string.logout));
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        {
                            LogOut logOut = new LogOut(ToppersClass.this);
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
    };

    private View.OnClickListener OpenTopperPopUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //final Dialog dia = new Dialog(ToppersClass.this);
            final Dialog dia = new Dialog(ToppersClass.this, android.R.style.Theme_Black_NoTitleBar);
            dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia.setContentView(R.layout.topper_pop_up);
            dia.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dia.setCanceledOnTouchOutside(true);
            listViewCourseList = (ListView) dia.findViewById(R.id.listViewCourseList);

            Toolbar sort_toolbar = (Toolbar) dia.findViewById(R.id.toolbar);
            ImageView imageViewBackIcon = (ImageView) sort_toolbar.findViewById(R.id.imageViewBackIcon);
            TextView textViewHeading = (TextView) sort_toolbar.findViewById(R.id.textViewHeading);
            sort_toolbar.setMinimumHeight(50);
            textViewHeading.setText("Select Category");
            imageViewBackIcon.setVisibility(View.VISIBLE);
            imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dia.dismiss();
                }
            });


            progressbarPopupCourselist = (ProgressBar) dia.findViewById(R.id.progressbarPopupCourselist);

            //dia.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dia.show();
            Window window = dia.getWindow();
            window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            getPopupCourseList();

            listViewCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    course = courseMainArrayPopupGetSets.get(position).getCategoryname();
                    courseChange = courseMainArrayPopupGetSets.get(position).getCategoryid();
                    System.out.println("CourseChanged :----->:" + courseChange);
                    searchTextcourse = "c.categoryid=" + courseChange;
                    dia.dismiss();
                    topperSlist = new ArrayList<>();
                    topperMainSlist = new ArrayList<>();
                    topperSearchList = new ArrayList<>();
                    topperMainSlist.clear();
                    topperSearchList.clear();
                    topperSlist.clear();
                   /* textViewHeading.setVisibility(View.GONE);
                    textViewHeading3.setVisibility(View.VISIBLE);*/
                    editTextSearch.setVisibility(View.INVISIBLE);
                    iamgeViewNavigationdrawer.setVisibility(View.GONE);
                    drawerToggle.setDrawerIndicatorEnabled(true);
                    pageSize = 1;
                    loading = false;
                    if (course.equals("All Category Toppers")) {
                        getTopersList("1=1", pageSize);
                    } else {
                        getTopersList(searchTextcourse, pageSize);
                    }
                }
            });

            textViewManage.setText(course);
            /*dia.show();
            Window window = dia.getWindow();
            window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);*/

        }
    };
    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textViewHeading.setVisibility(View.VISIBLE);
          /*  textViewHeading.setVisibility(View.GONE);
            textViewHeading3.setVisibility(View.VISIBLE);*/
            editTextSearch.setVisibility(View.INVISIBLE);
            editTextSearch.setText("");
            iamgeViewNavigationdrawer.setVisibility(View.GONE);
            textViewNoRecordFound.setVisibility(View.GONE);
            drawerToggle.setDrawerIndicatorEnabled(true);
            pageSize = 1;
            topperSlist = new ArrayList<>();
            topperSearchList = new ArrayList<>();
            topperMainSlist = new ArrayList<>();
            searchText = "1=1";
            CommonMethods.hideKeyboard(ToppersClass.this, (ToppersClass.this).getCurrentFocus());
            getTopersList(searchText, pageSize);
            imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
            imageViewSearchIcon.setOnClickListener(onClickSearchIcon);


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
                if (CommonMethods.userid.length() > 0) {
                    //setDrawer();
                } else {
                    textViewUserName.setText("Welcome Guest");
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

    private void getPopupCourseList() {
        try {

            progressbarPopupCourselist.setVisibility(View.VISIBLE);
            listViewCourseList.setVisibility(View.INVISIBLE);

            courseArrayPopupGetSets = new ArrayList<>();
            courseMainArrayPopupGetSets = new ArrayList<>();
            textViewManage.setText(course.toString() + " TOPPERS");
            JSONObject sendJExploreObject = new JSONObject();
            textViewNoRecordFound.setVisibility(View.GONE);
            if (!loading) {
                progressbarMoreTopperClass.setVisibility(View.GONE);
            } else {
                progressbarTopperClass.setVisibility(View.GONE);
            }

            sendJExploreObject.put("action", "getcategory");

            RequestQueue requestQueue = Volley.newRequestQueue(ToppersClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendJExploreObject, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        courseArrayPopupGetSets = new ArrayList<>();
                        ArrayList<CoursePopupGetSet> courseArrayPopupGetSets1 = new ArrayList<>();
                        String browseCatalogueitems = response.getString("category");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            CoursePopupGetSet coursePopupGetSet = new CoursePopupGetSet();
                            coursePopupGetSet.setCategoryid(browseArray.getJSONObject(i).getString("categoryid"));
                            coursePopupGetSet.setCategoryname(browseArray.getJSONObject(i).getString("categoryname"));

                            if (browseArray.getJSONObject(i).getString("categoryname").trim().equals(course.trim())) {
                                coursePopupGetSet.setIsSelected(true);
                            } else {
                                coursePopupGetSet.setIsSelected(false);
                            }
                            courseArrayPopupGetSets.add(coursePopupGetSet);
                        }

                        CoursePopupGetSet coursePopupGetSet1 = new CoursePopupGetSet();
                        coursePopupGetSet1.setCategoryid("0");
                        coursePopupGetSet1.setCategoryname("All Category Toppers");
                        if (course.trim().equals("All Category Toppers")) {
                            coursePopupGetSet1.setIsSelected(true);
                        } else {
                            coursePopupGetSet1.setIsSelected(false);
                        }
                        courseArrayPopupGetSets1.add(coursePopupGetSet1);
                        courseMainArrayPopupGetSets.addAll(courseArrayPopupGetSets1);

                        courseMainArrayPopupGetSets.addAll(courseArrayPopupGetSets);
                        progressbarPopupCourselist.setVisibility(View.GONE);
                        if (browseArray.length() > 0) {
                            adapter1 = new CoursePopupAdapter(ToppersClass.this, courseMainArrayPopupGetSets);
                            listViewCourseList.setAdapter(adapter1);

                            progressbarPopupCourselist.setVisibility(View.GONE);
                            listViewCourseList.setVisibility(View.VISIBLE);
                        } else {
                            adapter1 = new CoursePopupAdapter(getApplicationContext(), courseMainArrayPopupGetSets);
                            listViewCourseList.setAdapter(adapter1);
                            textViewNoRecordFound.setVisibility(View.VISIBLE);
                            progressbarPopupCourselist.setVisibility(View.GONE);

                            progressbarPopupCourselist.setVisibility(View.GONE);
                            listViewCourseList.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        progressbarPopupCourselist.setVisibility(View.GONE);
                        listViewCourseList.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarPopupCourselist.setVisibility(View.GONE);

                    progressbarPopupCourselist.setVisibility(View.GONE);
                    listViewCourseList.setVisibility(View.VISIBLE);

                    error.printStackTrace();
                }
            });
            requestQueue.add(customres);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTopersList(String searchText, final int pageSize) {
        try {
            topperSlist = new ArrayList<>();

            textViewNoRecordFound.setVisibility(View.GONE);
            textViewManage.setText(course.toString());
            JSONObject sendJExploreObject = new JSONObject();
            sendJExploreObject.put("action", "getcoursetoppers");
            if (course.equals("All Category Toppers")) {
                sendJExploreObject.put("wherecondition", searchText);
            } else {
                sendJExploreObject.put("wherecondition", searchTextcourse);
            }
            sendJExploreObject.put("pagenumber", String.valueOf(pageSize));
            sendJExploreObject.put("pagesize", "10");
            if (!loading) {
                progressbarMoreTopperClass.setVisibility(View.GONE);
                if (pageSize == 1) {
                    progressbarTopperClass.setVisibility(View.VISIBLE);
                    listviewTopperList.setVisibility(View.GONE);
                } else if (pageSize > 1) {
                    progressbarTopperClass.setVisibility(View.GONE);
                }
            } else {
                if (pageSize == 1) {
                    progressbarTopperClass.setVisibility(View.VISIBLE);
                    listviewTopperList.setVisibility(View.GONE);
                } else if (pageSize > 1) {
                    progressbarTopperClass.setVisibility(View.GONE);
                }
            }
            topperSlist = new ArrayList<>();
            Log.i("the request topper is:----> ", sendJExploreObject.toString());
            RequestQueue requestQueue = Volley.newRequestQueue(ToppersClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendJExploreObject, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response1 is:----> ", response.toString());
                    try {


                        String browseCatalogueitems = response.getString("CourseToppers");
                        topperSlist = new ArrayList<>();
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            TopperGetSet topperlistgetSet = new TopperGetSet();
                            topperlistgetSet.setTopperCourseIcon(browseArray.getJSONObject(i).getString("courseimage"));
                            topperlistgetSet.setTopperCourseId(browseArray.getJSONObject(i).getString("courseid"));
                            topperlistgetSet.setTopperCourseName(browseArray.getJSONObject(i).getString("coursename"));
                            topperlistgetSet.setTopperCourseUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));
                            topperlistgetSet.setTopperName(browseArray.getJSONObject(i).getString("name"));
                            topperlistgetSet.setTopperPercentage(browseArray.getJSONObject(i).getString("totalscore"));
                            topperSlist.add(topperlistgetSet);
                        }
                        topperMainSlist.addAll(topperSlist);
                        progressbarMoreTopperClass.setVisibility(View.GONE);
                        progressbarTopperClass.setVisibility(View.GONE);
                        listviewTopperList.setVisibility(View.VISIBLE);
                        if (!loading) {
                            topperSearchList = new ArrayList<>();
                            listviewTopperList.setAdapter(null);
                            adapter = new TopperAdapter(ToppersClass.this, topperMainSlist);
                            listviewTopperList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.notifyDataSetInvalidated();
                            if (topperSlist.size() > 1) {
                                listviewTopperList.setOnScrollListener(scrollListener);
                            } else {
                                listviewTopperList.setOnScrollListener(null);
                                if (courseChange.length() > 0 && topperMainSlist.size() < 1) {
                                    textViewNoRecordFound.setVisibility(View.VISIBLE);
                                }

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
                        if (topperSlist.size() <= 1) {
                            listviewTopperList.setOnScrollListener(null);
                            progressbarMoreTopperClass.setVisibility(View.GONE);
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
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getTopersList("1=1", 1);
                                    }
                                });
                        snackbar.show();
                        error.printStackTrace();
                        progressbarTopperClass.setVisibility(View.GONE);
                        listviewTopperList.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getTopersList("1=1", 1);
                                    }
                                });
                        snackbar.show();
                        error.printStackTrace();
                        progressbarTopperClass.setVisibility(View.GONE);
                        listviewTopperList.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                    }
                }
            });
            requestQueue.add(customres);
        } catch (Exception e) {
            e.printStackTrace();

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

                            Glide.with(ToppersClass.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                    //.skipMemoryCache(true)
                                    .transform(new CircleTransform(ToppersClass.this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                   // .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        } else {
                            Glide.with(ToppersClass.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                                    //.skipMemoryCache(true)
                                    .transform(new CircleTransform(ToppersClass.this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                   // .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                   //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.eniversity)
                                    .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                        }


                    } else {
                        Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                                .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                    }
                } else {
                    Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }

            }
            else {

                Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
            /*ProfilePictureClass serverData = new ProfilePictureClass(ToppersClass.this);
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

                    Glide.with(ToppersClass.this).load(path)
                            .transform(new CircleTransform(ToppersClass.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(imageViewProfilePic);
                } else {
                    Picasso.with(ToppersClass.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            } else {
                Picasso.with(ToppersClass.this).load(R.drawable.eniversity).placeholder(R.drawable.eniversity).error(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getSearchTopersList(String edittextValue, int searchPageSize) {
        try {
            topperSlist = new ArrayList<>();

            textViewManage.setText(course.toString());
            JSONObject sendJExploreObject = new JSONObject();
            textViewNoRecordFound.setVisibility(View.GONE);
            progressbarTopperClass.setVisibility(View.VISIBLE);
            if (!loading) {
                progressbarMoreTopperClass.setVisibility(View.GONE);
            } else {
                progressbarTopperClass.setVisibility(View.GONE);
            }
            sendJExploreObject.put("action", "getcoursetoppers");
            sendJExploreObject.put("wherecondition", edittextValue);
            sendJExploreObject.put("pagenumber", String.valueOf(searchPageSize));
            sendJExploreObject.put("pagesize", "50");

            RequestQueue requestQueue = Volley.newRequestQueue(ToppersClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendJExploreObject, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    topperSearchList = new ArrayList<>();
                    topperSlist = new ArrayList<>();

                    Log.i("the response is ", response.toString());
                    try {
                        String browseCatalogueitems = response.getString("CourseToppers");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);

                        for (int i = 0; i < browseArray.length(); i++) {
                            TopperGetSet topperlistgetSet = new TopperGetSet();
                            topperlistgetSet.setTopperCourseIcon(browseArray.getJSONObject(i).getString("courseimage"));
                            topperlistgetSet.setTopperCourseId(browseArray.getJSONObject(i).getString("courseid"));
                            topperlistgetSet.setTopperCourseName(browseArray.getJSONObject(i).getString("coursename"));
                            topperlistgetSet.setTopperCourseUsers(browseArray.getJSONObject(i).getString("noofsubscribers"));
                            topperlistgetSet.setTopperName(browseArray.getJSONObject(i).getString("name"));
                            topperlistgetSet.setTopperPercentage(browseArray.getJSONObject(i).getString("totalscore"));
                            topperSlist.add(topperlistgetSet);
                        }

                        topperSearchList.addAll(topperSlist);
                        progressbarTopperClass.setVisibility(View.GONE);
                        if (!loading) {
                            adapter = new TopperAdapter(ToppersClass.this, topperSearchList);
                            listviewTopperList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            if (browseArray.length() > 0) {
                                listviewTopperList.setOnScrollListener(null);
                            } else {

                                listviewTopperList.setOnScrollListener(null);
                                textViewNoRecordFound.setVisibility(View.VISIBLE);
                                progressbarTopperClass.setVisibility(View.GONE);
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
                            progressbarMoreTopperClass.setVisibility(View.VISIBLE);
                        }
                        if (topperSlist.size() <= 1) {
                            listviewTopperList.setOnScrollListener(null);
                            progressbarMoreTopperClass.setVisibility(View.GONE);
                            loading = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarTopperClass.setVisibility(View.GONE);
                    error.printStackTrace();
                }
            });

            requestQueue.add(customres);
        } catch (Exception e) {
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

                if ((listviewTopperList.getLastVisiblePosition() == listviewTopperList.getAdapter().getCount() - 1
                        && listviewTopperList.getChildAt(listviewTopperList.getChildCount() - 1).getBottom() <= listviewTopperList.getHeight())) {
                    progressbarMoreTopperClass.setVisibility(View.VISIBLE);
                    progressbarTopperClass.setVisibility(View.GONE);
                    textViewNoRecordFound.setVisibility(View.GONE);
                    if (edittextValue.length() > 0 && editTextSearch.getVisibility() == View.VISIBLE && editTextSearch.length() > 0) {
                        loading = true;
                        textViewNoRecordFound.setVisibility(View.GONE);
                        searchPageSize++;
                        getSearchTopersList(edittextValue, searchPageSize);
                    } else {
                        pageSize++;
                        loading = true;
                        getTopersList(searchText, pageSize);

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

                CommonMethods.hideKeyboard(ToppersClass.this, (ToppersClass.this).getCurrentFocus());
                return true;
            }
            return false;
        }
    };
}
