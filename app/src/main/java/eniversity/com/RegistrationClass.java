package eniversity.com;


import android.Manifest;
import android.app.Activity;

import com.android.volley.NoConnectionError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.eniversity.app.R;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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

import java.io.ByteArrayOutputStream;
import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Commmon.Methods.AlertDialogClass;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;

import data.base.RegistrationTable;
import login.page.LoginMainPage;
import login.page.ProfilePictureClass;

import com.soundcloud.android.crop.Crop;

/**
 * Created by shveta on 3/28/2016.
 */
public class RegistrationClass extends Activity {

    private Calendar calendar;

    private int year;
    private int month;
    private int day;
    private ImageView setDate;
    String finalServerResp = "";
    private EditText editTextUserName;
    private EditText editTextUserEmail;
    private EditText editTextUserPassword;

    private EditText editTextUserPhoneNumber;
    private EditText editTextUserLocation;
    private EditText textViewUserDateOfBirth;
    private Button buttonRegisterNewUser;
    CoordinatorLayout coordinatorLayout;
    private TextView txt_dia;
    private Button buttonOK;

    private TextView textViewRegister;
    private TextView textViewUpdate;
    private ImageView imageViewUploadImage;
    private ProgressBar progressbarLoadRegistration;
    private LinearLayout layoutloadRegistation;
    private TextView textViewTakeImage;
    private CheckBox checkBoxVisiblePassword;

    private String selectedImagePath = "";
    private String name;
    private String email;
    private String password;
    private String mobilenum;
    private String location;
    private String dob;
    private String gcmid = "";
    private String device = "Android";
    private String deviceid = "1";
    private String reg;
    private RelativeLayout relativeLayoutImageUpload;
    private ImageButton imageButtonCloseImage;
    private String imagepath = "";

    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    Bitmap bitmapimage;
ContentValues values;
    Uri imageUri;

    private String mCurrentPhotoPath = "";

    private ImageButton imageButtonCloseRegisterUpdate;

    final String emailPattern = "^[a-zA-Z][a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String dateOfBirthPattern="([0-9]{2})/([0-9]{2})/([0-9]{4})";
    private Pattern pattern;
    private Matcher matcher;
    File mTempCameraPhotoFile=null;

    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);


        imageButtonCloseRegisterUpdate = (ImageButton) findViewById(R.id.imageButtonCloseRegisterUpdate);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        textViewUpdate = (TextView) findViewById(R.id.textViewUpdate);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        editTextUserPassword = (EditText) findViewById(R.id.editTextUserPassword);
        editTextUserPassword.setTypeface(Typeface.SANS_SERIF);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        editTextUserPhoneNumber = (EditText) findViewById(R.id.editTextUserPhoneNumber);
        //editTextUserLocation = (EditText) findViewById(R.id.editTextUserLocation);
        textViewUserDateOfBirth = (EditText) findViewById(R.id.editTextUserDateOfBirth);
        buttonRegisterNewUser = (Button) findViewById(R.id.buttonRegisterNewUser);
        checkBoxVisiblePassword = (CheckBox) findViewById(R.id.checkBoxVisiblePassword);

        imageViewUploadImage = (ImageView) findViewById(R.id.imageViewUploadImage);

        progressbarLoadRegistration = (ProgressBar) findViewById(R.id.progressbarLoadRegistration);
        layoutloadRegistation = (LinearLayout) findViewById(R.id.layoutloadReagistation);
        editTextUserName.requestFocus();
        relativeLayoutImageUpload = (RelativeLayout) findViewById(R.id.relativeLayoutImageUpload);
        imageButtonCloseImage = (ImageButton) findViewById(R.id.imageButtonCloseImage);
        imageButtonCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutImageUpload.setVisibility(View.GONE);
                selectedImagePath = "";
                imagepath = "";
                textViewTakeImage.setVisibility(View.VISIBLE);
            }
        });

        //setDate = (ImageView) findViewById(R.id.buttonSetDate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        if (CommonMethods.userid.length() > 0) {
            getMyProfileDetails();
            buttonRegisterNewUser.setText("Update");
            textViewRegister.setVisibility(View.GONE);
            textViewUpdate.setVisibility(View.VISIBLE);

        }




      /*  setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sel_Date=textViewUserDateOfBirth.getText().toString();
                Log.i("Selected date :----->",sel_Date);
                if(sel_Date.length()<=0) {
                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }else {
                    String[] str=sel_Date.split("-");
                    year=Integer.parseInt(str[2]);
                    month=Integer.parseInt(str[1])-1;
                    day=Integer.parseInt(str[0]);
                }
                showDialog(999);
            }
        });*/
        editTextUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextUserPassword.getText().length() > 0) {
                    checkBoxVisiblePassword.setVisibility(View.VISIBLE);
                } else {
                    checkBoxVisiblePassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonRegisterNewUser.setOnClickListener(submitForm);


        textViewRegister.setText("Registration");

        imageButtonCloseRegisterUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });


        textViewTakeImage = (TextView) findViewById(R.id.textViewTakeImage);
        textViewTakeImage.setOnClickListener(BrowseImageClickListener);


        checkBoxVisiblePassword.setOnCheckedChangeListener(cbShowPwdsignupListener);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            DatePickerDialog datePickerDialog=new DatePickerDialog(this, myDateListener, year, month, day);

            final Calendar c = Calendar.getInstance();
            Date todaysDate = c.getTime();
            datePickerDialog.getDatePicker().setMaxDate(todaysDate.getTime()); //Set the current date as a max date.....
            datePickerDialog.show();

            return datePickerDialog;
        }
        return null;
    }


    private CompoundButton.OnCheckedChangeListener cbShowPwdsignupListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int start, end;
            if (!isChecked) {
                start = editTextUserPassword.getSelectionStart();
                end = editTextUserPassword.getSelectionEnd();
                editTextUserPassword.setTransformationMethod(new PasswordTransformationMethod());
                ;
                editTextUserPassword.setSelection(start, end);
            } else {
                start = editTextUserPassword.getSelectionStart();
                end = editTextUserPassword.getSelectionEnd();
                editTextUserPassword.setTransformationMethod(null);
                editTextUserPassword.setSelection(start, end);
            }
        }
    };

    View.OnClickListener submitForm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editTextUserName.length() == 0) {
                editTextUserName.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter first name");
                customDialog.show();
                return;
            }
            if (editTextUserEmail.length() == 0) {
                editTextUserEmail.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter email");
                customDialog.show();

                return;
            }
            if (!editTextUserEmail.getText().toString().matches(emailPattern)) {
                editTextUserEmail.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please Enter Valid Email");
                customDialog.show();
                return;
            }

            if (editTextUserPassword.length() == 0) {
                editTextUserPassword.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter password");
                customDialog.show();

                return;
            }
            if (editTextUserPhoneNumber.length() == 0  ) {
                editTextUserPhoneNumber.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter phone");
                customDialog.show();

                return;
            }
            if(!android.text.TextUtils.isDigitsOnly(editTextUserPhoneNumber.getText().toString())){
                editTextUserPhoneNumber.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid phone number");
                customDialog.show();

                return;
            }
            /*if (editTextUserLocation.length() == 0) {
                editTextUserLocation.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please Enter Location");
                customDialog.show();
                return;
            }*/

            if (editTextUserPhoneNumber.length() < 5 && editTextUserPhoneNumber.length() > 15) {
                editTextUserPhoneNumber.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Phone number invalid");
                customDialog.show();
                return;
            }
           /* if (textViewUserDateOfBirth.getText().length() == 0) {

                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter date of birth");
                customDialog.show();

                return;
            }
            if(!textViewUserDateOfBirth.getText().toString().matches(dateOfBirthPattern))
            {
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter date of birth in \n DD/MM/YYYY format");
                customDialog.show();

                return;

            }*/
            Date strDate = null;
            try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                strDate  = sdf.parse(textViewUserDateOfBirth.getText().toString());


            } catch (ParseException e) {
                e.printStackTrace();
            }
            matcher = Pattern.compile(dateOfBirthPattern).matcher(textViewUserDateOfBirth.getText().toString());
            if(!matcher.matches()){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }
            if(validate(textViewUserDateOfBirth.getText().toString())==false){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }
            String[] date=textViewUserDateOfBirth.getText().toString().split("/");
            if(Integer.parseInt(date[0])>31||Integer.parseInt(date[0])<=0){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }
            if(Integer.parseInt(date[1])>12||Integer.parseInt(date[1])<=0){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }
            if(Integer.parseInt(String.valueOf(date[2].charAt(0)))<1){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }
            if(new Date().before(strDate)){
                textViewUserDateOfBirth.requestFocus();
                AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Please enter valid date of birth");
                customDialog.show();
                return;
            }



            /*else {
                if (isThisDateBefour12Year(textViewUserDateOfBirth.getText().toString()) == true) {

                } else {
                    textViewUserDateOfBirth.requestFocus();
                    Toast.makeText(RegistrationClass.this, "Invalid date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
            }*/

           else {
                name = editTextUserName.getText().toString();
                email = editTextUserEmail.getText().toString();
                password = editTextUserPassword.getText().toString();
                mobilenum = editTextUserPhoneNumber.getText().toString();
                //location = editTextUserLocation.getText().toString();
                dob = textViewUserDateOfBirth.getText().toString();
                String[] dob1=dob.split("/");
                String dateOfBirth=dob1[0]+"-"+dob1[1]+"-"+dob1[2];
                Log.v("date of birth is",dateOfBirth);
                reg = buttonRegisterNewUser.getText().toString();
             //   CommonMethods.image_path=selectedImagePath;
               // Log.e(" CommonMethods.image_path", CommonMethods.image_path);
                Register1 registration = new Register1();


                registration.execute(name, email, password, mobilenum, "", dateOfBirth, selectedImagePath, gcmid, device, deviceid);


            }
        }
    };

    private boolean isThisDateBefour12Year(String selectedDate) {

        String[] y = selectedDate.split("-");
        String year = y[2];

        int current_year = Calendar.getInstance().get(Calendar.YEAR);

        System.out.println("Selected Year :---> " + year);
        System.out.println("Current Year(-12) :---> " + current_year);

        if (Integer.parseInt(year) < (current_year - 0013)) {

            return true;
        } else {
            return false;
        }
    }

    private void closeWindow() {
        String activity = getIntent().getExtras().getString("activityIntent");
        Intent intent = null;
        switch (activity) {
            case "LoginMainPage":
                intent = new Intent(RegistrationClass.this, LoginMainPage.class);
                break;
            case "ExploreClass":
                intent = new Intent(RegistrationClass.this, ExploreClass.class);
                break;
            case "CourseList":
                intent = new Intent(RegistrationClass.this, CourseList.class);
                break;
            case "ToppersClass":
                intent = new Intent(RegistrationClass.this, ToppersClass.class);
                break;
            default:
                intent = new Intent(RegistrationClass.this, MainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }


    public class Register1 extends AsyncTask<String, Void, String> {
        public String serverResponse = "";
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(RegistrationClass.this, null,
                    "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... params) {

           /* Glide.get(RegistrationClass.this).clearDiskCache();*/

            serverResponse = CommonMethods.sendFilesToServer(
                    params[0], params[1], params[2], params[3],
                    params[4], params[5], params[6], params[7],
                    params[8], params[9],RegistrationClass.this);
            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finalServerResp = serverResponse;
            dialog.dismiss();
            dialog.cancel();
            responcefromServer(finalServerResp);
        }
    }

    public void responcefromServer(String listId) {
        Log.i("Server Response", listId);
        String message="";
        String userid="";
        String securekey="";
        if(listId.contains("Registered successfully")) {
            try {
                //Glide.get(RegistrationClass.this).clearMemory();
               /* Glide.get(RegistrationClass.this).clearMemory();
                Glide.get(RegistrationClass.this).clearDiskCache();*/

                JSONObject object = new JSONObject(listId);
                String requestdetails = object.getString("RegisteredDetails");
                JSONArray requestdetailsarr = new JSONArray(requestdetails);
                for (int i = 0; i < requestdetailsarr.length(); i++) {
                    message = requestdetailsarr.getJSONObject(i).getString("message");
                    userid = requestdetailsarr.getJSONObject(i).getString("userid");
                    securekey=requestdetailsarr.getJSONObject(i).getString("securedkey");
                    CommonMethods.userid = userid;
                    //CommonMethods.userid=userid;
                    SharedPreferences sharedPreferences=getSharedPreferences(CommonMethods.MyPREFERENCES,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("type","user");
                    editor.putString("secure_key",securekey);
                    editor.commit();

                    CommonMethods.securedkeyuser=sharedPreferences.getString("secure_key","");
                    Log.v("secured key register", CommonMethods.securedkeyuser);



                    RegistrationTable registrationTable = new RegistrationTable(RegistrationClass.this);
                    registrationTable.open();
                    registrationTable.addlogindetails(requestdetailsarr.getJSONObject(i).getString("userid"));
                    registrationTable.close();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (message.equalsIgnoreCase("Registered successfully")) {

              /*  Dialog dialog = new Dialog(RegistrationClass.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_alert_dialog);
                txt_dia = (TextView) dialog.findViewById(R.id.txt_dia);
                txt_dia.setText("Registered  Successfully");
                buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                        Intent intent = new Intent(getApplicationContext(), CategoryList.class);
                        // intent.putExtra("courseid",userid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
               /*     }
                });
                dialog.show();*/
            }
        }
        else if (listId.equalsIgnoreCase("Updated successfully")) {

            ProfilePictureClass serverData = new ProfilePictureClass(RegistrationClass.this);
            CommonMethods.image_path = serverData.getCurrentProfilePic();
            //CommonMethods.image_path = selectedImagePath;
            CommonMethods.user_name = serverData.getCurrentUserName();
            SharedPreferences sharedPreferences1=getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences1.edit();
            editor1.putString("profile_imageurl", CommonMethods.image_path);
            editor1.putString("username",CommonMethods.user_name);
            editor1.commit();
            CommonMethods.profile_image_url= CommonMethods.image_path;
            CommonMethods.userName= CommonMethods.user_name;

         /*   Glide.get(RegistrationClass.this).clearMemory();
            Glide.get(RegistrationClass.this).clearDiskCache();
*/
         /*   SharedPreferences sharedPreferences1=getSharedPreferences(CommonMethods.MyPREFERENCES_CAMERA,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences1.edit();
            editor1.putString("profile_imageurl",selectedImagePath);
            editor1.putString("username",editTextUserName.getText().toString());
            editor1.commit();

            CommonMethods.profile_image_url=sharedPreferences1.getString("profile_imageurl","");
            CommonMethods.userName=sharedPreferences1.getString("username","");*/


            Dialog dialog= new Dialog(RegistrationClass.this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert_dialog);
            txt_dia= (TextView) dialog.findViewById(R.id.txt_dia);
            txt_dia.setText("Updated  Successfully");
            buttonOK= (Button) dialog.findViewById(R.id.buttonOK);
            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    closeWindow();
                }
            });
            dialog.show();


        } else if (listId.contains("Email")) {

            AlertDialogClass customDialog = new AlertDialogClass(RegistrationClass.this, "Email already exist");
            customDialog.show();
        }
    }

    public View.OnClickListener BrowseImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CommonMethods.hideKeyboard(RegistrationClass.this,(RegistrationClass.this).getCurrentFocus());
            imageViewUploadImage.setImageBitmap(null);
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationClass.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {


                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(RegistrationClass.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                            takePicture();
                        } else {
                            ActivityCompat.requestPermissions(RegistrationClass.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }

                    }
                    else {

                        takePicture();
                    }



                } else if (items[item].equals("Choose from Gallery")) {


                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(RegistrationClass.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                            openGallery();
                        } else {
                            ActivityCompat.requestPermissions(RegistrationClass.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        }
                    } else {

                        openGallery();

                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }
    };

    private void takeFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "TempFolder");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            } else {
                exportDir.delete();
            }
            mTempCameraPhotoFile = new File(exportDir, "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
            Log.d("UUID", "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempCameraPhotoFile));
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(RegistrationClass.this, "Camera error..!!", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
               /* if(isItCropImage()) {*/
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }

  /*  private void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(RegistrationClass.this, "Camera error..!!", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
               *//* if(isItCropImage()) {*//*
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
            storageDir.createNewFile();
        }

        File image = File.createTempFile(imageFileName, /* prefix */".jpg", /* suffix */storageDir /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/DirName/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/DirName/"), fileName);
        if (file.exists()) {

            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG,100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
            //imageViewUploadImage.setVisibility(View.VISIBLE);
        } else if (requestCode == Crop.REQUEST_CROP) {
            try {
                handleCrop(resultCode, data);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
           // imageViewUploadImage.setVisibility(View.VISIBLE);
        } else if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            beginCrop(Uri.parse(mCurrentPhotoPath));
            ///imageViewUploadImage.setVisibility(View.VISIBLE);
        }

            //CommonMethods.image_path = selectedImagePath;


    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }
    private void openGallery() {
        Crop.pickImage(RegistrationClass.this);
    }

    private void handleCrop(int resultCode, Intent result) throws FileNotFoundException, IOException {
        if (resultCode == RESULT_OK) {

            Uri imageUri = Crop.getOutput(result);
            String picturePath = getRealPathFromURI(imageUri);

            Bitmap bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            selectedImagePath = picturePath;


            Glide.with(getApplicationContext()).load(new File(selectedImagePath))
                    .asBitmap()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_no_image_availeble)
                    .into(imageViewUploadImage);

            // imageViewShownImage.setImageBitmap(image);
            imageViewUploadImage.setVisibility(View.VISIBLE);
            relativeLayoutImageUpload.setVisibility(View.VISIBLE);
            textViewTakeImage.setVisibility(View.GONE);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null,
                null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public String getOriginalImagePath() {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = RegistrationClass.this.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        int column_index_data = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();

        return cursor.getString(column_index_data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                    Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 2);
                    Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 2909: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                } else {
                    Log.e("Permission", "Denied");
                }
                return;
            }
        }


    }

    public void getMyProfileDetails() {


        imageViewUploadImage.setVisibility(View.VISIBLE);
        progressbarLoadRegistration.setVisibility(View.VISIBLE);
        layoutloadRegistation.setVisibility(View.GONE);

        try {
            JSONObject getProfileDetails = new JSONObject();
            getProfileDetails.put("action", "getmyprofile");
            getProfileDetails.put("userid", CommonMethods.userid);


            RequestQueue requestQueue = Volley.newRequestQueue(RegistrationClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    getProfileDetails, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        Log.i("the response is ", response.toString());
                        String profile = response.getString("Profile");
                        JSONArray getArray = new JSONArray(profile);
                        for (int i = 0; i < getArray.length(); i++) {

                            editTextUserName.setText(getArray.getJSONObject(i).getString("name"));
                            editTextUserEmail.setText(getArray.getJSONObject(i).getString("email"));
                            String pass=getArray.getJSONObject(i).getString("password");
                            if (pass.equals("null")) {
                                editTextUserPassword.setText("");
                            } else {
                                editTextUserPassword.setText(getArray.getJSONObject(i).getString("password"));
                            }
                            editTextUserPhoneNumber.setText(getArray.getJSONObject(i).getString("mobilenumber"));
                           // editTextUserLocation.setText(getArray.getJSONObject(i).getString("location"));
                            String dobd=getArray.getJSONObject(i).getString("dob");
                            if(dobd.equals("00-00-0000")){
                                textViewUserDateOfBirth.setText("");
                            }else {
                                String dateofBirth=getArray.getJSONObject(i).getString("dob");
                                String[] dobarr=dateofBirth.split("-");
                                textViewUserDateOfBirth.setText(dobarr[0]+"/"+dobarr[1]+"/"+dobarr[2]);
                            }
                            String imageUrl = getArray.getJSONObject(i).getString("profileimage");
                            if (imageUrl.length() > 0) {
                               // selectedImagePath = getArray.getJSONObject(i).getString("profileimage");
                                relativeLayoutImageUpload.setVisibility(View.VISIBLE);
                                imageViewUploadImage.setVisibility(View.VISIBLE);
                                if(imageUrl.contains("http")){
                                    Glide.with(RegistrationClass.this).load(imageUrl)
                                            .asBitmap()
                                            .skipMemoryCache(true)

                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .placeholder(R.drawable.ic_no_image_availeble)
                                            .into(imageViewUploadImage);


                                }else{
                                    Glide.with(RegistrationClass.this).load(new File(imageUrl))
                                            .asBitmap()
                                            .skipMemoryCache(true)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .placeholder(R.drawable.ic_no_image_availeble)
                                            .into(imageViewUploadImage);
                                }

                                selectedImagePath=getArray.getJSONObject(i).getString("profileimage");

                                textViewTakeImage.setVisibility(View.GONE);

                            } else {
                                Picasso.with(RegistrationClass.this).load(R.drawable.ic_no_image_availeble)
                                        .error(R.drawable.ic_no_image_availeble).into(imageViewUploadImage);
                                relativeLayoutImageUpload.setVisibility(View.GONE);
                                imageViewUploadImage.setVisibility(View.GONE);
                                textViewTakeImage.setVisibility(View.VISIBLE);
                            }
                            progressbarLoadRegistration.setVisibility(View.GONE);
                            layoutloadRegistation.setVisibility(View.VISIBLE);
                            //textViewTakeImage.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        progressbarLoadRegistration.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources()
                                        .getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getMyProfileDetails();
                                    }
                                });


                        snackbar.show();

                        error.printStackTrace();
                    }
                    else {
                        Log.e("social login error",error.toString());
                        progressbarLoadRegistration.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources()
                                        .getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getMyProfileDetails();
                                    }
                                });


                        snackbar.show();

                        error.printStackTrace();
                    }
                }

            });
            requestQueue.add(customres);

        } catch (Exception e) {

        }
    }



    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if (String.valueOf(day).length() == 1 && String.valueOf(month).length() == 1) {
            textViewUserDateOfBirth.setText(new StringBuilder().append("0").append(day).append("-")
                    .append("0").append(month).append("-").append(year));
        } else if (String.valueOf(day).length() == 1) {
            textViewUserDateOfBirth.setText(new StringBuilder().append("0").append(day).append("-")
                    .append(month).append("-").append(year));
        } else if (String.valueOf(month).length() == 1) {
            textViewUserDateOfBirth.setText(new StringBuilder().append(day).append("-")
                    .append("0").append(month).append("-").append(year));
        } else {
           /* textViewUserDateOfBirth.setText(new StringBuilder().append(day).append("-")
                    .append(month).append("-").append(year));*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeWindow();
    }
    public boolean validate(final String date){

        matcher = Pattern.compile(dateOfBirthPattern).matcher(date);

        if(matcher.matches()){
            matcher.reset();

            if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }

                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }


                else{
                    return true;
                }
            }

            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public  Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private  Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImagePath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    public static class ExifUtil {

        public static Bitmap rotateBitmap(String src, Bitmap bitmap) {
            try {
                int orientation = getExifOrientation(src);

                if (orientation == 1) {
                    return bitmap;
                }

                Matrix matrix = new Matrix();
                switch (orientation) {
                    case 2:
                        matrix.setScale(-1, 1);
                        break;
                    case 3:
                        matrix.setRotate(180);
                        break;
                    case 4:
                        matrix.setRotate(180);
                        matrix.postScale(-1, 1);
                        break;
                    case 5:
                        matrix.setRotate(90);
                        matrix.postScale(-1, 1);
                        break;
                    case 6:
                        matrix.setRotate(90);
                        break;
                    case 7:
                        matrix.setRotate(-90);
                        matrix.postScale(-1, 1);
                        break;
                    case 8:
                        matrix.setRotate(-90);
                        break;
                    default:
                        return bitmap;
                }

                try {
                    Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    return oriented;
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        private static int getExifOrientation(String src) throws IOException {
            int orientation = 1;

            try {
                /**
                 * if your are targeting only api level >= 5
                 * ExifInterface exif = new ExifInterface(src);
                 * orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                 */
                if (Build.VERSION.SDK_INT >= 5) {
                    Class<?> exifClass = Class.forName("android.media.ExifInterface");
                    Constructor<?> exifConstructor = exifClass.getConstructor(new Class[] { String.class });
                    Object exifInstance = exifConstructor.newInstance(new Object[] { src });
                    Method getAttributeInt = exifClass.getMethod("getAttributeInt", new Class[] { String.class, int.class });
                    Field tagOrientationField = exifClass.getField("TAG_ORIENTATION");
                    String tagOrientation = (String) tagOrientationField.get(null);
                    orientation = (Integer) getAttributeInt.invoke(exifInstance, new Object[] { tagOrientation, 1});
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            return orientation;
        }
    }

}
