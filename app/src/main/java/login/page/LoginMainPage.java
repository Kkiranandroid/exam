package login.page;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import Commmon.Methods.CircleTransform;
import Commmon.Methods.CommonMethods;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import Commmon.Methods.StretchedListView;
import adapter.SubscribedAdapter;

import eniversity.com.CorseInfo;
import eniversity.com.SubScription;
import get.set.SubscribedGetSet;
import eniversity.com.About;
import eniversity.com.Contact_us;
import eniversity.com.CourseDetails;
import eniversity.com.CourseList;
import eniversity.com.EditRecommendedCourses;
import eniversity.com.ExploreClass;
import eniversity.com.FrequentlyAskQuesionsClass;

import com.eniversity.app.R;
import eniversity.com.RegistrationClass;
import eniversity.com.RoundedTransformation;
import eniversity.com.ToppersClass;


/**
 * Created by shveta on 3/26/2016.
 */
public class LoginMainPage extends AppCompatActivity {


    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private View includelayoutnavigationDrawer;
    private ImageView imageViewSearchIcon;

    //Drawer_Layout....
    private TextView textViewUserName;
    private ImageView imageViewProfilePic;
    private LinearLayout linearLayoutExploreCourse;
    private LinearLayout linearLayoutSubScription;
    private LinearLayout linearLayoutEditRecCourse;
    private LinearLayout linearLayoutBrowse;
    private LinearLayout linearLayoutRegister;
    private ImageView linearLayoutEditProfile;
    private LinearLayout linearLayoutFAQ;
    private LinearLayout linearLayoutAboutUs;
    private ImageView linearLayoutLogout;
    private LinearLayout linearLayoutCouesrDetails2;
    private LinearLayout linearLayoutCouesrDetails;
    private LinearLayout linearLayoutCouesrDetailsNew;
    private LinearLayout linearLayoutCouesrDetailsNew3;
    private LinearLayout linearlayoutRecommendedCourses;

    private RelativeLayout relativeLayoutsuscbribed;


    private TextView textViewVisibilityNoCourseFound;
    private EditText editTextSearch;
    private StretchedListView stretchedListviewSubscribedScourse;

    private ArrayList<SubscribedGetSet> subscribeditems;

    private ProgressBar progressbarLoadLandingDetails;

    private CardView card_viewNew;
    private  CardView card_viewNew3;
    private CardView card_view;
    private CardView card_view2;
    private ImageView imageViewExploreCourse;
    private ImageView imageViewExploreCourse2;

    private TextView textViewCourseName;
    private TextView textViewCourseName2;
    private TextView textViewCoursePrice;
    private TextView textViewCoursePrice2;
    private ImageView imageViewBackIcon;
    private ImageView iamgeViewNavigationdrawer;
    private TextView textViewHeading;
    private TextView textViewHeading2;
    private TextView textViewSeeall;
    private TextView recoomendedSeeAllTextView;
    private TextView textViewCourseUsers;
    private TextView textViewCourseUsers2;
    private String searchText = "";
    private String edittextValue = "";
    private RatingBar ratingBarProduct;
    private RatingBar ratingBarProduct2;
    ImageView imageViewExploreCourseNew;
    ImageView imageViewExploreCourseNew3;
    TextView textViewCourseNameNew;
    TextView textViewCourseNameNew3;
    TextView textViewCoursePriceNew;
    TextView textViewCoursePriceNew3;
    TextView textViewCourseUsersNew;
    TextView textViewCourseUsersNew3;
    TextView textViewrecommended;
    private TextView textViewVisibilityNoRecommendedCourseFound;
    RatingBar ratingBarProductNew;
    RatingBar ratingBarProductNew3;
    private View linearLayoutToppers;
    private ImageView linearLayoutYOurCourse;
    private View layoutRecommended;
    private View layoutRecommended2;
    private Thread serverAlertThread = null;

    private View linearLayoutContactUs;
    private CoordinatorLayout coordinatorLayout;

    private static long back_pressed_time;
    private static long PERIOD = 2000;
    //recommended courses related
    String courseid3="";
    String courseid="";
    String courseid1="";
    String courseid2="";

    String categoryName = "";
    String categoryname1 = "";
    String categoryname2 = "";
    String categoryname3 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("inside","oncreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);

        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        } catch (Exception e) {

        }
        editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        textViewHeading.setVisibility(View.VISIBLE);
        //textViewHeading2=(TextView) toolbar.findViewById(R.id.textViewHeading2);
        //textViewHeading2.setVisibility(View.VISIBLE);
        imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        /*card_view = (CardView) findViewById(R.id.card_view);
        card_view2= (CardView) findViewById(R.id.card_view2);*/
        imageViewSearchIcon.setVisibility(View.VISIBLE);
        iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout_Users);
        textViewVisibilityNoCourseFound = (TextView) findViewById(R.id.textViewVisibilityNoCourseFound);
        textViewrecommended = (TextView) findViewById(R.id.textViewrecommended);
        progressbarLoadLandingDetails = (ProgressBar) findViewById(R.id.progressbarLoadLandingDetails);
        ratingBarProduct = (RatingBar) findViewById(R.id.ratingBarProduct);
        ratingBarProduct2= (RatingBar) findViewById(R.id.ratingBarProduct2);
        initDrawer();
        imageViewExploreCourseNew = (ImageView) findViewById(R.id.imageViewExploreCourseNew);
        imageViewExploreCourseNew3= (ImageView) findViewById(R.id.imageViewExploreCourseNew3);
        textViewCourseNameNew = (TextView) findViewById(R.id.textViewCourseNameNew);
        textViewCourseNameNew3= (TextView) findViewById(R.id.textViewCourseNameNew3);
        textViewCoursePriceNew = (TextView) findViewById(R.id.textViewCoursePriceNew);
        textViewCoursePriceNew3= (TextView) findViewById(R.id.textViewCoursePriceNew3);
        textViewCourseUsersNew = (TextView) findViewById(R.id.textViewCourseUsersNew);
        textViewCourseUsersNew3= (TextView) findViewById(R.id.textViewCourseUsersNew3);
        ratingBarProductNew = (RatingBar) findViewById(R.id.ratingBarProductNew);
        ratingBarProductNew3= (RatingBar) findViewById(R.id.ratingBarProductNew3);
        includelayoutnavigationDrawer = findViewById(R.id.includelayoutnavigationDrawer);
        layoutRecommended = (LinearLayout) findViewById(R.id.layoutRecommended);
        layoutRecommended2=findViewById(R.id.layoutRecommended2);
        linearLayoutCouesrDetails2= (LinearLayout) findViewById(R.id.linearLayoutCouesrDetails2);
        linearLayoutCouesrDetails= (LinearLayout) findViewById(R.id.linearLayoutCouesrDetails);
        linearLayoutCouesrDetailsNew=(LinearLayout) findViewById(R.id.linearLayoutCouesrDetailsNew);
        linearLayoutCouesrDetailsNew3=(LinearLayout) findViewById(R.id.linearLayoutCouesrDetailsNew3);
        linearlayoutRecommendedCourses=(LinearLayout) findViewById(R.id.linearlayoutRecommendedCourses);
        textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);

        relativeLayoutsuscbribed = (RelativeLayout)findViewById(R.id.relativeLayoutsuscbribed);

        imageViewExploreCourse = (ImageView) findViewById(R.id.imageViewExploreCourse);
        imageViewExploreCourse2= (ImageView) findViewById(R.id.imageViewExploreCourse2);

        imageViewProfilePic = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.imageViewProfilePic);
        textViewUserName = (TextView) includelayoutnavigationDrawer.findViewById(R.id.textViewUserName);

        textViewCourseName = (TextView) findViewById(R.id.textViewCourseName);
        textViewCourseName2= (TextView) findViewById(R.id.textViewCourseName2);
        textViewCourseUsers = (TextView) findViewById(R.id.textViewCourseUsers);
        textViewCourseUsers2= (TextView) findViewById(R.id.textViewCourseUsers2);
        textViewVisibilityNoRecommendedCourseFound= (TextView) findViewById(R.id.textViewVisibilityNoRecommendedCourseFound);

        stretchedListviewSubscribedScourse = (StretchedListView) findViewById(R.id.stretchedListviewSubscribedScourse);
        stretchedListviewSubscribedScourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LoginMainPage.this, CourseDetails.class);
                intent.putExtra("courseid", subscribeditems.get(position).getSubscribedCourseId());
                intent.putExtra("coursename", subscribeditems.get(position).getCoursename());
                startActivity(intent);
            }
        });
       // setDrawer();
        linearLayoutRegister = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutRegister);
        linearLayoutRegister.setVisibility(View.GONE);

        linearLayoutEditProfile = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditProfile);
        linearLayoutEditProfile.setVisibility(View.VISIBLE);
        textViewSeeall = (TextView) findViewById(R.id.textViewSeeAll);
        textViewSeeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainPage.this, SeeAllDetails.class);
                if(editTextSearch.getText().toString().equals("")){
                    intent.putExtra("wherecondition", "1=1");
                }else{
                    intent.putExtra("wherecondition", edittextValue);
                    intent.putExtra("searchtext", editTextSearch.getText().toString());
                }
                startActivity(intent);
                finish();
            }
        });
        linearLayoutEditRecCourse= (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutEditRecCourse);

        linearLayoutEditRecCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(LoginMainPage.this,EditRecommendedCourses.class);
                startActivity(intent);



            }
        });
        recoomendedSeeAllTextView = (TextView) findViewById(R.id.recoomendedSeeAll);
        recoomendedSeeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainPage.this, RecommendedSeeAll.class);
                if(editTextSearch.getText().toString().equals("")){
                    intent.putExtra("wherecondition", "1=1");
                }else{
                intent.putExtra("wherecondition", edittextValue);
                    intent.putExtra("searchtext", editTextSearch.getText().toString());
                }
                startActivity(intent);
                finish();
            }
        });
        linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, RegistrationClass.class);
                i.putExtra("activityIntent", "LoginMainPage");
                startActivity(i);
                finish();
            }
        });

        linearLayoutYOurCourse = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutYOurCourse);
        linearLayoutYOurCourse.setVisibility(View.VISIBLE);
        linearLayoutYOurCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawer(Gravity.LEFT);
            }
        });

        linearLayoutToppers = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutToppers);
        linearLayoutToppers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, ToppersClass.class);
                startActivity(i);

            }
        });
        linearLayoutFAQ = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutFAQ);
        linearLayoutFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, FrequentlyAskQuesionsClass.class);
                startActivity(i);

            }
        });

        linearLayoutAboutUs = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutAboutUs);
        linearLayoutContactUs = includelayoutnavigationDrawer.findViewById(R.id.linearLayoutContactUs);
        linearLayoutAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, About.class);
                startActivity(i);

            }
        });
        linearLayoutContactUs = includelayoutnavigationDrawer.findViewById(R.id.linearLayoutContactUs);
        linearLayoutContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, Contact_us.class);
                startActivity(i);

            }
        });
        linearLayoutYOurCourse = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutYOurCourse);
        linearLayoutExploreCourse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutExploreCourse);
        linearLayoutExploreCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, ExploreClass.class);
                startActivity(i);


            }
        });
        linearLayoutSubScription= (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutSubScription);
        linearLayoutSubScription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMainPage.this, SubScription.class);
                startActivity(i);
            }
        });

        linearLayoutBrowse = (LinearLayout) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutBrowse);
        linearLayoutBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMainPage.this, CourseList.class);
                startActivity(i);

            }
        });

       /* card_viewNew = (CardView) findViewById(R.id*//**//*.card_viewNew);
        card_viewNew3= (CardView) findViewById(R.id.card_viewNew3)*/;
        textViewCoursePrice = (TextView) findViewById(R.id.textViewCoursePrice);
        textViewCoursePrice2= (TextView) findViewById(R.id.textViewCoursePrice2);

        linearLayoutLogout = (ImageView) includelayoutnavigationDrawer.findViewById(R.id.linearLayoutLogout);
        linearLayoutLogout.setVisibility(View.VISIBLE);
        linearLayoutLogout.setOnClickListener(logOut);

        imageViewSearchIcon.setOnClickListener(onClickSearchIcon);
        searchText = "1=1";
        getSubScriberDetails(searchText);
        subscribeditems = new ArrayList<>();

        editTextSearch.addTextChangedListener(textChengListener);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
   /*     ProfilePictureClass serverData = new ProfilePictureClass(LoginMainPage.this);
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



        path = serverData.getCurrentProfilePic();*/



        /*} else {
            path = CommonMethods.image_path;
        }*/
        String dealerName="";
        if (CommonMethods.userName.length() > 0) {

            dealerName = CommonMethods.userName;
        } else {
            dealerName = CommonMethods.user_name;
        }


        if((dealerName.toString().length())>12){
            textViewUserName.setText("Welcome\n" + dealerName);
        }else {
            textViewUserName.setText("Welcome " + dealerName);
        }


        if (CommonMethods.profile_image_url != null) {
            if (CommonMethods.profile_image_url.length() > 0) {
                if(CommonMethods.profile_image_url.contains("http")) {

                    Glide.with(LoginMainPage.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                            //.skipMemoryCache(true)
                            .transform(new CircleTransform(LoginMainPage.this))
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                }
                else{
                    Glide.with(LoginMainPage.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                           // .skipMemoryCache(true)
                            .transform(new CircleTransform(LoginMainPage.this))
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.eniversity)
                            .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                }


            } else {
                Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
        }
        else{
            Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                    .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
        }



    }

    public class ServerThreadForAlert implements Runnable {
        @Override
        public void run() {
            startAlertTimer();
            //  startAlertTimer_filters();
        }
    }

    public void startAlertTimer() {
        try {

            /*Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 13); // For 1 PM or 2 PM
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);*/

           /* Intent Service1 = new Intent(LoginMainPage.this, DownloadImages.class);
            startService(Service1);*/
            /*PendingIntent piService1 = PendingIntent.getService(LoginMainPage.this, 0, Service1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager1 = (AlarmManager) LoginMainPage.this.getSystemService(Context.ALARM_SERVICE);
            alarmManager1.cancel(piService1);

            alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 60 * 24*//*15*60*1000*//*, piService1);*/
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private View.OnClickListener logOut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginMainPage.this);
            alertDialogBuilder.setMessage(getApplicationContext().getResources().getString(R.string.logout));
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    try {
//								if(CommonMethods.test_internet(MainClass.this) == true)
                        {
                            LogOut logOut = new LogOut(LoginMainPage.this);
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
    private View.OnClickListener onClickSearchIcon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            progressbarLoadLandingDetails.setVisibility(View.GONE);

            if (editTextSearch.getVisibility() == View.VISIBLE) {
                editTextSearch.requestFocus();
                CommonMethods.hideKeyboard(LoginMainPage.this, (LoginMainPage.this).getCurrentFocus());
                edittextValue = "c.coursename like '%" + editTextSearch.getText().toString() + "%'";
                subscribeditems = new ArrayList<>();
                getSubScriberDetails(edittextValue);
                drawer_layout.closeDrawers();
            } else {
                drawer_layout.closeDrawers();
                editTextSearch.requestFocus();
                textViewHeading.setVisibility(View.INVISIBLE);
                //textViewHeading2.setVisibility(View.INVISIBLE);
                editTextSearch.setVisibility(View.VISIBLE);
               // iamgeViewNavigationdrawer.setVisibility(View.VISIBLE);
              //  iamgeViewNavigationdrawer.setOnClickListener(finishSearchlistener);
                imageViewBackIcon.setVisibility(View.GONE);
                editTextSearch.requestFocus();
                CommonMethods.showKeyboard(LoginMainPage.this, (LoginMainPage.this).getCurrentFocus());
                drawerToggle.setDrawerIndicatorEnabled(false);

                imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_white));
                imageViewSearchIcon.setOnClickListener(finishSearchlistener);
            }
        }
    };
    private TextWatcher textChengListener= new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            edittextValue = "c.coursename like '%" + s.toString() + "%'";
            getSubScriberDetails(edittextValue);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static InputStream getHttpConnection(String urlString) throws IOException {

        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("downloadImage" + ex.toString());
        }
        return stream;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed_time + PERIOD > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finishAffinity();
        }
        else
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        back_pressed_time = System.currentTimeMillis();
    }



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
                   // setDrawer();
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
    private void setDrawer(){
        try{
            String dealerName="";
            if (CommonMethods.userName.length() > 0) {

                dealerName = CommonMethods.userName;
            } else {
                dealerName = CommonMethods.user_name;
            }


            if((dealerName.toString().length())>12){
                textViewUserName.setText("Welcome\n" + dealerName);
            }else {
                textViewUserName.setText("Welcome " + dealerName);
            }


            if (CommonMethods.profile_image_url != null) {
                if (CommonMethods.profile_image_url.length() > 0) {
                    if(CommonMethods.profile_image_url.contains("http")) {

                        Glide.with(LoginMainPage.this).load(CommonMethods.profile_image_url)/*.asBitmap()*/
                                //.skipMemoryCache(true)
                                .transform(new CircleTransform(LoginMainPage.this))
                                //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.eniversity)
                                .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                    }
                    else{
                        Glide.with(LoginMainPage.this).load(new File(CommonMethods.profile_image_url))/*.asBitmap()*/
                               // .skipMemoryCache(true)
                                .transform(new CircleTransform(LoginMainPage.this))
                                //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.eniversity)
                                .error(R.drawable.eniversity).into(/*new BitmapImageViewTarget(*/imageViewProfilePic);
                    }


                } else {
                    Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                            .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
                }
            }
            else{
                Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /*private void setDrawer() {
        ProfilePictureClass serverData = new ProfilePictureClass(LoginMainPage.this);
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
        *//*} else {
            path = CommonMethods.image_path;
        }*//*

        if (path != null) {
            if (path.length() > 0) {
               *//* Picasso.with(this).load(path).placeholder(R.drawable.eniversity).transform(new RoundedTransformation(800, 10))
                        .resize(250, 250).centerCrop().into(imageViewProfilePic);*//*
                *//*Glide.with(LoginMainPage.this)
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
                        });*//*
*//*
                Glide.with(LoginMainPage.this).load(path).asBitmap()
                        .skipMemoryCache(true)
                        .transform(new CircleTransform(LoginMainPage.this))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.eniversity)
                        .error(R.drawable.eniversity).into(new BitmapImageViewTarget(imageViewProfilePic){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        Bitmap toyImageScaled = Bitmap.createScaledBitmap(resource, 200, 200
                                * resource.getHeight() / resource.getWidth(), false);
                        imageViewProfilePic.setImageBitmap(toyImageScaled);
                    }
                });*//*
                Glide.with(LoginMainPage.this).load(path)*//*.asBitmap()*//*
                       // .skipMemoryCache(true)
                        .transform(new CircleTransform(LoginMainPage.this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.eniversity)
                        .error(R.drawable.eniversity).into(imageViewProfilePic);*//*new BitmapImageViewTarget(imageViewProfilePic){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        Bitmap toyImageScaled = Bitmap.createScaledBitmap(resource, 200, 200
                                * resource.getHeight() / resource.getWidth(), false);
                        imageViewProfilePic.setImageBitmap(toyImageScaled);
                    }
                });*//*


            } else {
                Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                        .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
            }
       }
        else{
            Picasso.with(LoginMainPage.this).load(R.drawable.eniversity).error(R.drawable.eniversity).placeholder(R.drawable.eniversity)
                    .transform(new RoundedTransformation(800, 2)).into(imageViewProfilePic);
        }

    }*/


    public void getSubScriberDetails(final String searchText) {
        try {
            textViewVisibilityNoCourseFound.setVisibility(View.GONE);
            progressbarLoadLandingDetails.setVisibility(View.VISIBLE);
            relativeLayoutsuscbribed.setVisibility(View.GONE);

            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "getsubcribedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("wherecondition", searchText);

            Log.v("the request ", sendObject.toString());

            subscribeditems = new ArrayList<>();


            RequestQueue requestQueue = Volley.newRequestQueue(LoginMainPage.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        subscribeditems = new ArrayList<>();
                        String browseCatalogueitems = response.getString("SubcribedCoursesList");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            SubscribedGetSet subscribedGetSet = new SubscribedGetSet();
                            subscribedGetSet.setCourseimage(browseArray.getJSONObject(i).getString("courseimage"));

                            subscribedGetSet.setCoursename(browseArray.getJSONObject(i).getString("coursename"));
                            subscribedGetSet.setOutof(browseArray.getJSONObject(i).getString("totalscore"));
                            subscribedGetSet.setRanking(browseArray.getJSONObject(i).getString("ranking"));
                            subscribedGetSet.setSubscribedon(browseArray.getJSONObject(i).getString("createdon"));
                            subscribedGetSet.setSubscribedCourseId(browseArray.getJSONObject(i).getString("courseid"));
                            if (i < 3) {
                                subscribeditems.add(subscribedGetSet);
                            }
                        }
                        if(browseArray.length()<=3){
                            textViewSeeall.setVisibility(View.GONE);
                        }

                        if(browseArray.length()==1){
                            textViewSeeall.setVisibility(View.GONE);
                        }
                        if(browseArray.length()==0){
                            textViewSeeall.setVisibility(View.GONE);
                        }
                        if(browseArray.length()>3){
                            textViewSeeall.setVisibility(View.VISIBLE);
                        }
                        if (subscribeditems.size() < 1) {
                            textViewVisibilityNoCourseFound.setVisibility(View.VISIBLE);
                            //textViewSeeall.setVisibility(View.GONE);
                        } else {
                            textViewVisibilityNoCourseFound.setVisibility(View.GONE);
                            //textViewSeeall.setVisibility(View.VISIBLE);
                        }
                        stretchedListviewSubscribedScourse.setAdapter(new SubscribedAdapter(LoginMainPage.this, subscribeditems,"LoginMainPage"));
                        progressbarLoadLandingDetails.setVisibility(View.GONE);
                        relativeLayoutsuscbribed.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof NoConnectionError) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getSubScriberDetails(searchText);
                                    }
                                });
                        snackbar.show();

                        progressbarLoadLandingDetails.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getSubScriberDetails(searchText);
                                    }
                                });
                        snackbar.show();

                        progressbarLoadLandingDetails.setVisibility(View.GONE);
                        error.printStackTrace();
                    }

                }
            });
            sendObject = new JSONObject();
            sendObject.put("action", "getrecommendedcourses");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("wherecondition", /*"1=1"*/searchText);
            sendObject.put("pagesize", "10");
            sendObject.put("pagenumber", "1");
Log.v("recommended:", sendObject.toString());
            subscribeditems = new ArrayList<>();
           /* try {
                String packageName = this.getPackageName();
                PackageInfo info = this.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
            }
            HttpStack httpStack = new OwnHttpClientStack(AndroidHttpClient.newInstance(userAgent));*/

            requestQueue = Volley.newRequestQueue(LoginMainPage.this);
            CustomRequest anotherReq = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {
                        layoutRecommended.setVisibility(View.VISIBLE);
                        layoutRecommended2.setVisibility(View.VISIBLE);
                        String browseCatalogueitems = response.getString("RecommendedCourses");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);

                        String imageUrl = "";
                        String imageUrl1 = "";
                        String imageUrl2="";
                        String imageUrl3="";

                        String categoryPrice = "";
                        String categoryPrice1 = "";
                        String categoryPrice2 = "";
                        String categoryPrice3 = "";

                        String numberOfUsers = "";
                        String numberOfUsers1 = "";
                        String numberOfUsers2 = "";
                        String numberOfUsers3 = "";


                        for (int i = 0; i < browseArray.length(); i++) {
                            if (i == 0) {
                                courseid=browseArray.getJSONObject(i).getString("courseid");
                                imageUrl = browseArray.getJSONObject(i).getString("courseimage");
                                categoryName = browseArray.getJSONObject(i).getString("coursename");

                                categoryPrice = browseArray.getJSONObject(i).getString("totalfees");

                                ratingBarProduct.setRating(Float.parseFloat(browseArray.getJSONObject(i).getString("rating")));
                                numberOfUsers = browseArray.getJSONObject(i).getString("noofsubcribers");
                            } else if (i == 1) {
                                courseid1=browseArray.getJSONObject(i).getString("courseid");
                                imageUrl1 = browseArray.getJSONObject(i).getString("courseimage");
                                categoryname1 = browseArray.getJSONObject(i).getString("coursename");
                                categoryPrice1 = browseArray.getJSONObject(i).getString("totalfees");

                                ratingBarProductNew.setRating(Float.parseFloat(browseArray.getJSONObject(i).getString("rating")));
                                numberOfUsers1 = browseArray.getJSONObject(i).getString("noofsubcribers");
                            }
                            else if(i==2){
                                imageUrl2 = browseArray.getJSONObject(i).getString("courseimage");
                                categoryname2 = browseArray.getJSONObject(i).getString("coursename");
                                courseid2=browseArray.getJSONObject(i).getString("courseid");

                                categoryPrice2 = browseArray.getJSONObject(i).getString("totalfees");
                                ratingBarProduct2.setRating(Float.parseFloat(browseArray.getJSONObject(i).getString("rating")));
                                numberOfUsers2 = browseArray.getJSONObject(i).getString("noofsubcribers");

                            }
                            else if(i==3){
                                imageUrl3 = browseArray.getJSONObject(i).getString("courseimage");
                                categoryname3 = browseArray.getJSONObject(i).getString("coursename");
                                courseid3=browseArray.getJSONObject(i).getString("courseid");


                                categoryPrice3 = browseArray.getJSONObject(i).getString("totalfees");
                                ratingBarProductNew3.setRating(Float.parseFloat(browseArray.getJSONObject(i).getString("rating")));
                                numberOfUsers3 = browseArray.getJSONObject(i).getString("noofsubcribers");
                            }
                        }
                        if (browseArray.length() <=4) {
                            recoomendedSeeAllTextView.setVisibility(View.GONE);
                        }else{
                            recoomendedSeeAllTextView.setVisibility(View.VISIBLE);
                        }

                        if (browseArray.length() == 1) {
                            recoomendedSeeAllTextView.setVisibility(View.GONE);
                            linearLayoutCouesrDetails.setVisibility(View.VISIBLE);
                            //card_viewNew.setVisibility(View.INVISIBLE);
                           // card_view2.setVisibility(View.INVISIBLE);
                            //card_viewNew3.setVisibility(View.INVISIBLE);
                            linearLayoutCouesrDetailsNew.setVisibility(View.INVISIBLE);
                            linearLayoutCouesrDetailsNew3.setVisibility(View.INVISIBLE);
                            linearLayoutCouesrDetails2.setVisibility(View.INVISIBLE);

                        }
                        if(browseArray.length()==3){
                           // card_viewNew3.setVisibility(View.INVISIBLE);
                            linearLayoutCouesrDetailsNew.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetails.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetails2.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetailsNew3.setVisibility(View.INVISIBLE);
                        }
                        if(browseArray.length()==2){
                            linearLayoutCouesrDetailsNew.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetails.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetails2.setVisibility(View.INVISIBLE);
                            linearLayoutCouesrDetailsNew3.setVisibility(View.INVISIBLE);
                           /* card_view2.setVisibility(View.INVISIBLE);
                            card_viewNew3.setVisibility(View.INVISIBLE);*/
                        } if(browseArray.length()>3){
                            linearLayoutCouesrDetails.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetailsNew.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetailsNew3.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetails2.setVisibility(View.VISIBLE);
                        }

                        if (browseArray.length() == 0) {
                            recoomendedSeeAllTextView.setVisibility(View.GONE);
                            textViewrecommended.setVisibility(View.VISIBLE);
                            linearlayoutRecommendedCourses.setVisibility(View.VISIBLE);
                            textViewVisibilityNoRecommendedCourseFound.setVisibility(View.VISIBLE);
                            linearLayoutCouesrDetailsNew.setVisibility(View.GONE);
                            linearLayoutCouesrDetailsNew3.setVisibility(View.GONE);
                            linearLayoutCouesrDetails2.setVisibility(View.GONE);
                            linearLayoutCouesrDetails.setVisibility(View.GONE);

                           // card_viewNew.setVisibility(View.GONE);
                           // card_viewNew3.setVisibility(View.GONE);
                            //card_view.setVisibility(View.GONE);
                            //card_view2.setVisibility(View.GONE);
                        }else{
                            textViewrecommended.setVisibility(View.VISIBLE);
                            linearlayoutRecommendedCourses.setVisibility(View.VISIBLE);
                            textViewVisibilityNoRecommendedCourseFound.setVisibility(View.GONE);
                        }
                        if (imageUrl.length() > 0) {
                            Picasso.with(LoginMainPage.this).load(imageUrl/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourse);
                        } else {
                            Picasso.with(LoginMainPage.this).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourse);
                        }
                        if (imageUrl1.length() > 0) {
                            Picasso.with(LoginMainPage.this).load(imageUrl1/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourseNew);
                        } else {
                            Picasso.with(LoginMainPage.this).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourseNew);
                        }
                        if(imageUrl2.length()>0){
                            Picasso.with(LoginMainPage.this).load(imageUrl2/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourse2);
                        } else {
                            Picasso.with(LoginMainPage.this).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourse2);
                        }
                        if (imageUrl3.length() > 0) {
                            Picasso.with(LoginMainPage.this).load(imageUrl3/*R.drawable.image_2*/).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourseNew3);
                        } else {
                            Picasso.with(LoginMainPage.this).load(R.drawable.ic_no_image_availeble).placeholder(R.drawable.ic_no_image_availeble)
                                    .error(R.drawable.ic_no_image_availeble).into(imageViewExploreCourseNew3);
                        }


                        textViewCourseName.setText(categoryName);
                        textViewCourseName2.setText(categoryname2);
                        textViewCoursePrice.setText(getApplicationContext().getResources().getString(R.string.rupees) + categoryPrice);
                        textViewCoursePrice2.setText(getApplicationContext().getResources().getString(R.string.rupees) + categoryPrice2);




                        ratingBarProduct.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        ratingBarProduct2.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });

                        textViewCourseUsers.setText(Html.fromHtml("Users "+"<font color=#45B97C>" + numberOfUsers + "</font>"));
                        textViewCourseUsers2.setText(Html.fromHtml("Users "+"<font color=#45B97C>" + numberOfUsers2 + "</font>") );


                        textViewCourseNameNew.setText(categoryname1);
                        textViewCourseNameNew3.setText(categoryname3);
                        textViewCoursePriceNew.setText(getApplicationContext().getResources().getString(R.string.rupees) + categoryPrice1);
                        textViewCoursePriceNew3.setText(getApplicationContext().getResources().getString(R.string.rupees) + categoryPrice3);

                        ratingBarProductNew.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        ratingBarProductNew3.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        textViewCourseUsersNew.setText(Html.fromHtml("Users "+"<font color=#45B97C>" + numberOfUsers1 + "</font>") );
                        textViewCourseUsersNew3.setText(Html.fromHtml("Users "+"<font color=#45B97C>" + numberOfUsers3 + "</font>") );



                        progressbarLoadLandingDetails.setVisibility(View.GONE);

                        linearLayoutCouesrDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent(LoginMainPage.this, CorseInfo.class);
                                intent.putExtra("courseid", courseid);
                                intent.putExtra("coursename" ,categoryName);
                                CommonMethods.fromclass="landing";
                                startActivity(intent);
                                finish();
                            }
                        });
                        linearLayoutCouesrDetailsNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent(LoginMainPage.this, CorseInfo.class);
                                intent.putExtra("courseid", courseid1);
                                intent.putExtra("coursename" ,categoryname1);
                                CommonMethods.fromclass="landing";
                                startActivity(intent);
                                finish();
                            }
                        });
                        linearLayoutCouesrDetails2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent(LoginMainPage.this, CorseInfo.class);
                                intent.putExtra("courseid", courseid2);
                                intent.putExtra("coursename" ,categoryname2);
                                CommonMethods.fromclass="landing";
                                startActivity(intent);
                                finish();
                            }
                        });
                        linearLayoutCouesrDetailsNew3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent(LoginMainPage.this, CorseInfo.class);
                                intent.putExtra("courseid", courseid3);
                                intent.putExtra("coursename" ,categoryname3);
                                CommonMethods.fromclass="landing";
                                startActivity(intent);
                                finish();
                            }
                        });


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressbarLoadLandingDetails.setVisibility(View.GONE);
                    error.printStackTrace();

                }
            });
            customres.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            anotherReq.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(customres);
            requestQueue.add(anotherReq);
        } catch (Exception e) {

        }
    }
    private View.OnClickListener finishSearchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           textViewHeading.setVisibility(View.VISIBLE);
           // textViewHeading2.setVisibility(View.VISIBLE);
            editTextSearch.setVisibility(View.INVISIBLE);
            editTextSearch.setText("");

            subscribeditems = new ArrayList<>();
            searchText = "1=1";
            getSubScriberDetails(searchText);

            CommonMethods.hideKeyboard(LoginMainPage.this, (LoginMainPage.this).getCurrentFocus());
            iamgeViewNavigationdrawer.setVisibility(View.GONE);
            drawerToggle.setDrawerIndicatorEnabled(true);
            imageViewSearchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_search_white));
            imageViewSearchIcon.setOnClickListener(onClickSearchIcon);

        }
    };
/*    public static class OwnHttpClientStack extends HttpClientStack {
        private final static String HEADER_CONTENT_TYPE = "Content-Type";

        public OwnHttpClientStack(HttpClient client) {
            super(client);
        }

        @Override
        public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
                throws IOException, AuthFailureError {
            HttpUriRequest httpRequest = createHttpRequest(request, additionalHeaders);
            addHeaders(httpRequest, additionalHeaders);
            addHeaders(httpRequest, request.getHeaders());
            onPrepareRequest(httpRequest);
            HttpParams httpParams = httpRequest.getParams();
            int timeoutMs = request.getTimeoutMs();
            // TODO: Reevaluate this connection timeout based on more wide-scale
            // data collection and possibly different for wifi vs. 3G.
            HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
            HttpConnectionParams.setSoTimeout(httpParams, timeoutMs);
            return mClient.execute(httpRequest);
        }

        private static void addHeaders(HttpUriRequest httpRequest, Map<String, String> headers) {
            for (String key : headers.keySet()) {
                httpRequest.setHeader(key, headers.get(key));
            }
        }

        static HttpUriRequest createHttpRequest(Request<?> request, Map<String, String> additionalHeaders) throws AuthFailureError {
            switch (request.getMethod()) {
                case Request.Method.DEPRECATED_GET_OR_POST: {
                    byte[] postBody = request.getPostBody();
                    if (postBody != null) {
                        HttpPost postRequest = new HttpPost(request.getUrl());
                        postRequest.addHeader(HEADER_CONTENT_TYPE, request.getPostBodyContentType());
                        HttpEntity entity;
                        entity = new ByteArrayEntity(postBody);
                        postRequest.setEntity(entity);
                        return postRequest;
                    } else {
                        return new HttpGet(request.getUrl());
                    }
                }
                case Request.Method.GET:
                    return new HttpGet(request.getUrl());
                case Request.Method.DELETE:
                    OwnHttpDelete deleteRequest = new OwnHttpDelete(request.getUrl());
                    deleteRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                    setEntityIfNonEmptyBody(deleteRequest, request);
                    return deleteRequest;
                case Request.Method.POST: {
                    HttpPost postRequest = new HttpPost(request.getUrl());
                    postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                    setEntityIfNonEmptyBody(postRequest, request);
                    return postRequest;
                }
                case Request.Method.PUT: {
                    HttpPut putRequest = new HttpPut(request.getUrl());
                    putRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                    setEntityIfNonEmptyBody(putRequest, request);
                    return putRequest;
                }
                case Request.Method.HEAD:
                    return new HttpHead(request.getUrl());
                case Request.Method.OPTIONS:
                    return new HttpOptions(request.getUrl());
                case Request.Method.TRACE:
                    return new HttpTrace(request.getUrl());
                case Request.Method.PATCH: {
                    HttpPatch patchRequest = new HttpPatch(request.getUrl());
                    patchRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                    setEntityIfNonEmptyBody(patchRequest, request);
                    return patchRequest;
                }
                default:
                    throw new IllegalStateException("Unknown request method.");
            }
        }

        private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase httpRequest,
                                                    Request<?> request) throws AuthFailureError {
            byte[] body = request.getBody();
            if (body != null) {
                HttpEntity entity = new ByteArrayEntity(body);
                httpRequest.setEntity(entity);
            }
        }

        private static class OwnHttpDelete extends HttpPost {
            public static final String METHOD_NAME = "DELETE";

            public OwnHttpDelete() {
                super();
            }

            public OwnHttpDelete(URI uri) {
                super(uri);
            }

            public OwnHttpDelete(String uri) {
                super(uri);
            }

            public String getMethod() {
                return METHOD_NAME;
            }
        }
    }*/
}
