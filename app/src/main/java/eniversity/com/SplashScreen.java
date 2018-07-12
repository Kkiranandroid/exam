package eniversity.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.eniversity.app.R;
import Commmon.Methods.CommonMethods;
import data.base.CategoryTable;
import data.base.LoginTable;
import data.base.RegistrationTable;
import login.page.LoginMainPage;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by shveta on 3/21/2016.
 */
public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                try {

                    //SharedPreferences sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES,MODE_PRIVATE);
                    //String categoryId=sharedPreferences.getString(CommonMethods.CATEGORY_ID, "");




                    RegistrationTable registrationTable = new RegistrationTable(SplashScreen.this);
                    registrationTable.open();
                    CommonMethods.userid = registrationTable.getAuthKey1("Select * from RegisterTable");
                    registrationTable.close();
                    CategoryTable categoryTable= new CategoryTable(SplashScreen.this);
                    categoryTable.open();
                    CommonMethods.category_Id=categoryTable.getCategoryId("Select * from CategoryTable");
                    categoryTable.close();
                    LoginTable getLoginDetails = new LoginTable(SplashScreen.this);
                    getLoginDetails.open();

                    sharedPreferences =getSharedPreferences(CommonMethods.MyPREFERENCES, Context.MODE_PRIVATE);
                    sharedPreferences1=getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA,Context.MODE_PRIVATE);

                   /* if (CommonMethods.userid.equals("")) {
                        CommonMethods.userid = getLoginDetails.getAuthKey("Select * from LoginTable");

                    }
                    else{
                        if (CommonMethods.category_Id.length() > 0) {
                            Intent i = new Intent(getApplicationContext(), LoginMainPage.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), CategoryList.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    // getLoginDetails.close();

                    if (CommonMethods.userid.length() > 0) {
                        //if (CommonMethods.category_Id.length() > 0) {
                            Intent i = new Intent(getApplicationContext(), LoginMainPage.class);
                            startActivity(i);
                            finish();
                            }
                       // }
                        else {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                   // }*/
                    if(CommonMethods.userid.length()>0){
                        CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                        if(CommonMethods.category_Id.length()>0){
                            Intent intent= new Intent(SplashScreen.this,LoginMainPage.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent= new Intent(SplashScreen.this,CategoryList.class);
                            startActivity(intent);
                            finish();
                        }
                        CommonMethods.profile_image_url=sharedPreferences1.getString("profile_imageurl","");
                        CommonMethods.userName=sharedPreferences1.getString("username","");


                    }
                    else {
                        CommonMethods.userid=getLoginDetails.getAuthKey("Select * from LoginTable");
                        if(CommonMethods.userid.length()>0){
                            CommonMethods.profile_image_url=sharedPreferences1.getString("profile_imageurl","");
                            CommonMethods.userName=sharedPreferences1.getString("username","");
                            CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                            Intent intent= new Intent(SplashScreen.this,LoginMainPage.class);
                            startActivity(intent);
                            finish();




                        }
                        else {
                            CommonMethods.securedkeyguest=sharedPreferences.getString("secure_key","");
                            Intent intent= new Intent(SplashScreen.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, SPLASH_TIME_OUT);
    }

}
