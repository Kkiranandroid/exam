package Commmon.Methods;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

import data.base.CategoryTable;
import data.base.LoginTable;
import data.base.RegistrationTable;
import login.page.ProfilePictureClass;
import eniversity.com.MainActivity;

public class LogOut extends AsyncTask<String, Void, String> {
    private Context context;


    private ProgressDialog dialog;


    public LogOut(Context context) {
        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {

        try {

 /*           Glide.get(context).clearDiskCache();*/
            context.getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA, Context.MODE_PRIVATE).edit().clear().apply();
            CommonMethods.profile_image_url="";
            CommonMethods.userName="";

            CommonMethods.securedkeyuser="";
            CommonMethods.category_Id="";
            CategoryTable categoryTable=new CategoryTable(context);
            categoryTable.open();
            categoryTable.delettableuserTB();
            categoryTable.close();
            CommonMethods.userid = "";
            RegistrationTable registrationTable=new RegistrationTable(context);
            registrationTable.open();
            registrationTable.delettableuserTB();
            registrationTable.close();
            CommonMethods.userid = "";
            LoginTable loginTb = new LoginTable(context);
            loginTb.open();
            loginTb.delettableuserTB();
            loginTb.close();
            CommonMethods.user_name="";
            CommonMethods.image_path="";
            ProfilePictureClass.httpImgUrlPath="";
            ProfilePictureClass.httpUserName="";
            LoginManager.getInstance().logOut();


        } catch (Exception t) {
            t.printStackTrace();
            Log.getStackTraceString(t);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        dialog.dismiss();

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(i);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, null,
                "Please wait...", true);
    }
}
