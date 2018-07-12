package eniversity.com;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.eniversity.app.R;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import get.set.CourseGetSet;


/**
 * Created by soumyay on 4/20/2016.
 */
public class ProceedToPay extends AppCompatActivity {


    Toolbar toolbar;
    ImageView imageViewBackIcon;
    TextView HeadingTextView;
    ImageView imageViewSearchIcon;

    RelativeLayout ralativeLayoutEasy;
    RelativeLayout ralativeLayoutIntermadiate;
    RelativeLayout ralativeLayoutAdvanced;
    ProgressBar ProgressBarLoad;


    String courseId = "";
    String subscribeexamlevel="";
    String CourseName = "";
    String examlevel="";
    String examlevel1="";
    String examlevel2="";
    String examlevel3="";
    String finallevel="";
    String paymentMode="";
    String orderId="";
    String txnId="";
    String txnDate="";
    String banktxnId="";
    String txnAmount="";
    String transactionMId="";

    //private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
   // private static final String CONFIG_CLIENT_ID = "AR3VMLmehfsZfY1JVDLA8qrQ_KO5UgNebiDZgCBReeuYv6mC3ozizZvd7aZrVyNCL9ONwd6oA7gGsVxR";
//old-AVZUbOX3ry-gyvTBVykh9TnK1v49hM0ycQiquryr8NjuRwnayplCFEm1M4ZnK5Q9JCcWzn5_briWUeRH
   // new-AR3VMLmehfsZfY1JVDLA8qrQ_KO5UgNebiDZgCBReeuYv6mC3ozizZvd7aZrVyNCL9ONwd6oA7gGsVxR
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private double basicamount = 0.00;
    private double intermediateamount = 0.00;
    private double advancedamount = 0.00;
    private double subtotal = 0.00;
    private double total = 0.00;
    private  double SubTotal=0.0;
    private double Total=0.0;
  /*  private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
// the following are only used in PayPalFuturePaymentActivity.
            .merchantName("termcon@gmail.com")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.childitems.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.childitems.com/legal"));*/
   // PayPalPayment thingToBuy;
   // PayPalPayment thingToBuy2;

    //payment Details end
    CheckBox easyCheckBox;
    CheckBox intermediateCheckBox;
    CheckBox advancedCheckBox;
    TextView subtotalTextView;
    TextView totalfeesTextView;
    Button payButton;
    TextView basicfeesTextView;
    TextView inermediatefeesTextView;
    TextView advancedfeesTextView;
    TextView taxTextView;
    TextView subtotal1;
    TextView tax;
    TextView courseName;
    TextView btn_yes;
    Button buttonOK;
    TextView txt_dia;
    ArrayList<CourseGetSet> courseGetSetArrayList;

    TextView advancedSubscribedOn;
   TextView easySubscribedOn;
    TextView intermediateSubscribedOn;

    LinearLayout linearMain;
    String easySubOn="";



    String intermediateSubOn="";
    String advancedSubOn="";
    String courseexamlevel="";

    LinearLayout easyLayot;
    LinearLayout intermediateLayout;
    LinearLayout advancedLayout;
    CoordinatorLayout coordinatorLayout;
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    public ProceedToPay() {
        for (int i = 0; i < 3; i++) {
            itemChecked.add(i, true);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proceed_to_pay);

        basicfeesTextView= (TextView) findViewById(R.id.basicfeesTextView);
        inermediatefeesTextView= (TextView) findViewById(R.id.inermediatefeesTextView);
        advancedfeesTextView= (TextView) findViewById(R.id.advancedfeesTextView);
        totalfeesTextView = (TextView) findViewById(R.id.totalTextView);
        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
        subtotalTextView.setVisibility(View.GONE);
        taxTextView= (TextView) findViewById(R.id.taxTextView);
        taxTextView.setVisibility(View.GONE);
        subtotal1= (TextView) findViewById(R.id.subtotal);
        subtotal1.setVisibility(View.GONE);
        tax= (TextView) findViewById(R.id.tax);
        tax.setVisibility(View.GONE);

        easyLayot= (LinearLayout) findViewById(R.id.easyLayot);
        intermediateLayout= (LinearLayout) findViewById(R.id.intermediateLayout);
        advancedLayout= (LinearLayout) findViewById(R.id.advancedLayout);

        payButton = (Button) findViewById(R.id.payButton);
        linearMain= (LinearLayout) findViewById(R.id.linearMain);
        ProgressBarLoad= (ProgressBar) findViewById(R.id.ProgressBarLoad);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        //imageViewSearchIcon= (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        //imageViewSearchIcon.setVisibility(View.GONE);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HeadingTextView = (TextView) toolbar.findViewById(R.id.textViewHeading);
        courseName= (TextView) findViewById(R.id.courseName);
        easySubscribedOn= (TextView) findViewById(R.id.easySubscribedOn);
        intermediateSubscribedOn= (TextView) findViewById(R.id.intermediateSubscribedOn);
        advancedSubscribedOn= (TextView) findViewById(R.id.advancedSubscribedOn);

        ralativeLayoutEasy= (RelativeLayout) findViewById(R.id.ralativeLayoutEasy);
        ralativeLayoutIntermadiate= (RelativeLayout) findViewById(R.id.ralativeLayoutIntermadiate);
        ralativeLayoutAdvanced= (RelativeLayout) findViewById(R.id.ralativeLayoutAdvanced);


        courseId = getIntent().getExtras().getString("courseid");
        subscribeexamlevel=getIntent().getExtras().getString("examlevels");
        CourseName = getIntent().getExtras().getString("coursename");
        courseexamlevel=getIntent().getExtras().getString("courseexamlevel");

        if(!courseexamlevel.contains("Easy")){
            ralativeLayoutEasy.setVisibility(View.GONE);
            easyLayot.setVisibility(View.GONE);
        }
         if(!courseexamlevel.contains("Intermediate")){
            ralativeLayoutIntermadiate.setVisibility(View.GONE);
            intermediateLayout.setVisibility(View.GONE);
        }
        if(!courseexamlevel.contains("Advanced")){
            ralativeLayoutAdvanced.setVisibility(View.GONE);
            advancedLayout.setVisibility(View.GONE);
        }

        //hideSubscribeCourses(subscribeexamlevel);
        easyCheckBox = (CheckBox) findViewById(R.id.easyCheckBox);
        if(ralativeLayoutEasy.getVisibility()==View.VISIBLE)
            easyCheckBox.setChecked(true);
        else
            easyCheckBox.setChecked(false);
        intermediateCheckBox = (CheckBox) findViewById(R.id.intermediateCheckBox);
        if(ralativeLayoutIntermadiate.getVisibility()==View.VISIBLE)
            intermediateCheckBox.setChecked(true);
        else
            intermediateCheckBox.setChecked(false);
        advancedCheckBox = (CheckBox) findViewById(R.id.advancedCheckBox);
        if(ralativeLayoutAdvanced.getVisibility()==View.VISIBLE)
            advancedCheckBox.setChecked(true);
        else
            advancedCheckBox.setChecked(false);

        HeadingTextView.setText("Subscribe");
        courseName.setText(CourseName);

        getDetails();



       /* Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);*/
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (total == 0 || subtotal == 0) {
                    Toast.makeText(ProceedToPay.this, "please select the proper package ", Toast.LENGTH_SHORT).show();

                } else {
                    onStartTransaction(total);

             /*       final Dialog dialog = new Dialog(ProceedToPay.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.paypalprocedure);
                    dialog.setCanceledOnTouchOutside(true);
                    Button paypalButton = (Button) dialog.findViewById(R.id.paypalButton);
                    ImageButton imageViewCancel = (ImageButton) dialog.findViewById(R.id.imageViewCancel);
                    dialog.show();
                    imageViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    paypalButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SubTotal=(1/66.66)*subtotal;
                            Total=(1/66.66)*total;
                            thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(SubTotal)), "USD",
                                    "subtotal", PayPalPayment.PAYMENT_INTENT_SALE);
                            thingToBuy2 = new PayPalPayment(new BigDecimal(String.valueOf(Total)), "USD", "Total", PayPalPayment.PAYMENT_INTENT_SALE);

                            Intent intent = new Intent(ProceedToPay.this,
                                    PaymentActivity.class);
                            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy2);
                            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                        }
                    });*/
                }
            }
        });
    }

    private void hideSubscribeCourses(String subExamLevels,String easySubdate,String intermediatedate,String advancDate){

        if(easySubdate.equals("")||easySubdate.equals("0000-00-00 00:00:00")){
            easyLayot.setVisibility(View.GONE);
        }
        if(intermediatedate.equals("")||intermediatedate.equals("0000-00-00 00:00:00")){
            intermediateLayout.setVisibility(View.GONE);
        }
        if(advancDate.equals("")||advancDate.equals("0000-00-00 00:00:00")){
            advancedLayout.setVisibility(View.GONE);
        }
        if(subExamLevels.contains("Easy")){

            String date = easySubdate;
            String output = date.substring(0, 10);
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date1 = null;
            try {
                date1 = originalFormat.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = targetFormat.format(date1);


            easySubscribedOn.setText(formattedDate);
            ralativeLayoutEasy.setEnabled(false);
            easyCheckBox.setEnabled(false);
            easyCheckBox.setChecked(false);
            easyCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_check_box_black_24dp));
            easyCheckBox.setFocusable(false);
            easyCheckBox.setClickable(false);
            ralativeLayoutEasy.setClickable(false);
            ralativeLayoutEasy.setFocusable(false);


        }

        if(subExamLevels.contains("Intermediate")){
            String date = intermediatedate;
            String output = date.substring(0, 10);
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date1 = null;
            try {
                date1 = originalFormat.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = targetFormat.format(date1);
            intermediateSubscribedOn.setText(formattedDate);
            intermediateCheckBox.setEnabled(false);
            intermediateCheckBox.setChecked(false);
            intermediateCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_check_box_black_24dp));
            intermediateCheckBox.setFocusable(false);
            intermediateCheckBox.setClickable(false);
            ralativeLayoutIntermadiate.setClickable(false);
            ralativeLayoutIntermadiate.setEnabled(false);
            ralativeLayoutIntermadiate.setFocusable(false);
        }
        if(subExamLevels.contains("Advanced")){
            String date = advancDate;
            String output = date.substring(0, 10);
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date1 = null;
            try {
                date1 = originalFormat.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = targetFormat.format(date1);

            advancedSubscribedOn.setText(formattedDate);
            advancedCheckBox.setEnabled(false);
            advancedCheckBox.setChecked(false);
            advancedCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_check_box_black_24dp));
            advancedCheckBox.setFocusable(false);
            advancedCheckBox.setClickable(false);
            ralativeLayoutAdvanced.setEnabled(false);
            ralativeLayoutAdvanced.setClickable(false);
            ralativeLayoutAdvanced.setFocusable(false);
        }
    }

   /* public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(ProceedToPay.this,
                PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }*/

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));
                        Dialog dialog= new Dialog(ProceedToPay.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_alert_dialog);
                        txt_dia= (TextView) dialog.findViewById(R.id.txt_dia);
                        txt_dia.setText("Payment Successful");
                        buttonOK= (Button) dialog.findViewById(R.id.buttonOK);
                        buttonOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DisplayToast();
                                Intent intent = new Intent();
                                setResult(1, intent);
                                finish();
                                //getPaymentDetails(finallevel, paymentMode);
                                //Intent intent = new Intent();
                                //setResult(1, intent);
                               // finish();
                            }
                        });
                        dialog.show();

                        //Toast.makeText(getApplicationContext(), "Payment Successfull ",
                               // Toast.LENGTH_LONG).show();


                        //finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));
                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);
                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }*/

    public void getPaymentDetails(String finalExamLevel,String paymode) {
        try {
            /*if(paymentMode.contains("PayPal")){
                paymentMode="";
            }
            else {
                paymentMode = "PayPal";
            }*/
            JSONObject sendObject = new JSONObject();
            sendObject.put("action", "subscribecourse");
            sendObject.put("userid", CommonMethods.userid);
            sendObject.put("examlevel", finalExamLevel/*"Advanced"*/);
            sendObject.put("courseid", courseId/*"17"*/);
            sendObject.put("paymentmode", "Paytm");
            sendObject.put("notes", "");
            sendObject.put("chequeno", "");
            sendObject.put("transactionAmount",txnAmount);
            sendObject.put("transactionMId",transactionMId);
            sendObject.put("transactionOrderId",orderId);
            sendObject.put("transactionTxnId",txnId);
            sendObject.put("transactionDate",txnDate);
            sendObject.put("transactionBankTxnId",banktxnId);

            Log.v("payment request",sendObject.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(ProceedToPay.this);
            CustomRequest users = new CustomRequest(Request.Method.POST, CommonMethods.url, sendObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof ParseError){

                        setVisibility();
                        finish();
                    }
                    Log.v("error in payment page", "error");
                    error.printStackTrace();

                }
            });
            requestQueue.add(users);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setVisibility(){
        if(finallevel.equalsIgnoreCase("Easy,Intermediate")){

            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Intermediate,Easy")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Easy,Advanced")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Advanced,Easy")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Advanced exam levels",Toast.LENGTH_LONG).show();
        }

        else if(finallevel.equalsIgnoreCase("Intermediate,Advanced")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Advanced,Intermediate")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Intermediate")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Intermediate exam level",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Easy")){
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy exam level",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Advanced")) {
            Toast.makeText(ProceedToPay.this, "You have subscribed for Advanced exam level", Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Easy,Intermediate,Advanced")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Easy,Advanced,Intermediate")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Advanced,Easy,Intermediate")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Advanced,Intermediate,Easy")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Intermediate,Advanced,Easy")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy,Intermediate,Advanced exam levels",Toast.LENGTH_LONG).show();
        }
        else if(finallevel.equalsIgnoreCase("Intermediate,Easy,Advanced")) {
            Toast.makeText(ProceedToPay.this,"You have subscribed for Easy, Intermediate, Advanced exam levels",Toast.LENGTH_LONG).show();
        }
    }

   /* private void sendAuthorizationToServer(PayPalAuthorization authorization) {
    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);
        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);
// processing with
// PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }*/

    @Override
    public void onDestroy() {
// Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void getDetails() {
        //courseGetSetArrayList=new ArrayList<>();
        //linearMain.setVisibility(View.GONE);


        try {
            linearMain.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);

            ProgressBarLoad.setVisibility(View.VISIBLE);

            JSONObject getCourseDetails = new JSONObject();
            getCourseDetails.put("action", "getcoursedetails");
            getCourseDetails.put("courseid", courseId);
            getCourseDetails.put("userid", CommonMethods.userid);

            Log.v("payment request", getCourseDetails.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(ProceedToPay.this);
            CustomRequest courses = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    getCourseDetails, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("the response is ", response.toString());
                    try {

                        String browseCatalogueitems = response.getString("CourseDetails");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            if (browseArray.getJSONObject(i).getString("status").equals("deleted") ||
                                    browseArray.getJSONObject(i).getString("status").equals("blocked")) {
                                final Dialog dialog = new Dialog(ProceedToPay.this);
                                dialog.setTitle(null);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_alert_dialog);
                                TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                                message.setText("Your account has been " + browseArray.getJSONObject(i).getString("status"));
                                Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                                buttonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new LogOut(ProceedToPay.this).execute();
                                        finish();

                                    }
                                });
                                dialog.show();

                            } else {
                                final String bascicfees = browseArray.getJSONObject(i).getString("basicfees");
                                final String intermediateFees = browseArray.getJSONObject(i).getString("intermediatefees");
                                final String advancedFees = browseArray.getJSONObject(i).getString("advancefees");

                                basicfeesTextView.setText(getResources().getString(R.string.rupees) + bascicfees /*+ ".00"*/);
                                inermediatefeesTextView.setText(getResources().getString(R.string.rupees) + intermediateFees /*+ ".00"*/);
                                advancedfeesTextView.setText(getResources().getString(R.string.rupees) + advancedFees /*+ ".00"*/);

                                easySubOn = browseArray.getJSONObject(i).getString("easysubscribedon");
                                intermediateSubOn = browseArray.getJSONObject(i).getString("intermediatesubscribedon");
                                advancedSubOn = browseArray.getJSONObject(i).getString("advancedsubscribedon");

                                hideSubscribeCourses(subscribeexamlevel, easySubOn, intermediateSubOn, advancedSubOn);

                                if (easyCheckBox.isChecked()) {


                                    basicamount = Double.parseDouble(bascicfees);

                                }
                                if (intermediateCheckBox.isChecked()) {

                                    intermediateamount = Double.parseDouble(intermediateFees);

                                }
                                if (advancedCheckBox.isChecked()) {

                                    advancedamount = Double.parseDouble(advancedFees);

                                }
                                subtotal = basicamount + intermediateamount + advancedamount;

                                total = subtotal;

                                subtotalTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(subtotal) + "0");
                                totalfeesTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(total) + "0");

                                easyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {

                                            basicamount = Double.parseDouble(bascicfees);
                                        } else {

                                            basicamount = 0.0;
                                        }
                                        subtotal = basicamount + intermediateamount + advancedamount;

                                        total = subtotal;

                                        subtotalTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(subtotal) + "0");

                                        totalfeesTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(total) + "0");
                                    }
                                });
                                intermediateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {

                                            intermediateamount = Double.parseDouble(intermediateFees);
                                        } else {

                                            intermediateamount = 0.00;

                                        }
                                        subtotal = basicamount + intermediateamount + advancedamount;

                                        total = subtotal;

                                        subtotalTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(subtotal) + "0");

                                        totalfeesTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(total) + "0");
                                    }
                                });
                                advancedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            advancedamount = Double.parseDouble(advancedFees);
                                        } else {
                                            advancedamount = 0.0;
                                        }
                                        subtotal = basicamount + intermediateamount + advancedamount;

                                        total = subtotal;

                                        subtotalTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(subtotal) + "0");

                                        totalfeesTextView.setText(getResources().getString(R.string.rupees) + String.valueOf(total) + "0");
                                    }
                                });


                            }

                            linearMain.setVisibility(View.VISIBLE);
                            payButton.setVisibility(View.VISIBLE);
                            ProgressBarLoad.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
               }
            } , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof NoConnectionError) {

                        ProgressBarLoad.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getDetails();
                                    }
                                });
                        snackbar.show();
                        error.printStackTrace();
                    }
                    else {
                        ProgressBarLoad.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getApplicationContext().getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE).setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getDetails();
                                    }
                                });
                        snackbar.show();
                        error.printStackTrace();
                    }

                }
            });

            requestQueue.add(courses);

        } catch (Exception r) {

        }
    }

    public void DisplayToast() {
        examlevel1 = "";
        examlevel2 = "";
        examlevel3 = "";
        if (easyCheckBox.isChecked()) {
            examlevel1 = "Easy";
        } else {
            examlevel1 = "";
        }

        if (intermediateCheckBox.isChecked()) {
            if (easyCheckBox.isChecked()) {
                examlevel2 = ",Intermediate";
            } else {
                examlevel2 = "Intermediate";
            }
        } else {
            examlevel2 = "";
        }

        if (advancedCheckBox.isChecked()) {
            if (easyCheckBox.isChecked() || intermediateCheckBox.isChecked()) {
                examlevel3 = ",Advanced";
            } else {
                examlevel3 = "Advanced";
            }
        }else{
            examlevel3 = "";
        }
        examlevel = examlevel1 + examlevel2 + examlevel3;
        finallevel=examlevel;
            paymentMode="PayPal";




        if (examlevel.length() > 0) {

            examlevel = "";
           // paymentMode="";
            examlevel1 = "";
            examlevel2 = "";
            examlevel3 = "";
        }

        getPaymentDetails(finallevel, paymentMode);
    }
    private String initOrderId() {
        Random r = new Random(System.currentTimeMillis());
        String orderId = "ORDER" + (2 + r.nextInt(2)) * 10000
                + r.nextInt(10000);
        return orderId;
    }

/*    public void onStartTransaction() {
        PaytmPGService Service = null;
        PaytmMerchant Merchant = null;
        Map<String, String> paramMap = new HashMap<String, String>();
        //    if (transactionStage == false) {
        Service = PaytmPGService.getProductionService();
        paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID", initOrderId());
        paramMap.put("MID", "Microb98475788094871");
        paramMap.put("CUST_ID", "CUST_" + CommonMethods.userId+"_"+initOrderId());
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail112");
        paramMap.put("WEBSITE", "MicrobhWAP");
        paramMap.put("TXN_AMOUNT",Spredrivertotalamount);
        paramMap.put("THEME", "merchant");
        paramMap.put("EMAIL", email);
        paramMap.put("MOBILE_NO",phonenumber);
        Merchant = new PaytmMerchant(
                "https://dropmeplease.microbharat.com/paytm_Checksum/generatechecksum.php",
                "https://dropmeplease.microbharat.com/paytm_Checksum/verifychecksum.php");

        *//*} else {
            Service = PaytmPGService.getStagingService();
            paramMap.put("REQUEST_TYPE", "DEFAULT");
            paramMap.put("ORDER_ID", initOrderId());
            paramMap.put("MID", "Microb85349971126787");
            paramMap.put("CUST_ID", "CUST_" + CommonMethods.userId+"_"+initOrderId());
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            paramMap.put("WEBSITE", "Microwap");
            paramMap.put("TXN_AMOUNT",*//**//*Spredrivertotalamount*//**//*"2.00");
            paramMap.put("THEME", "merchant");


            paramMap.put("EMAIL", email);
            paramMap.put("MOBILE_NO",phonenumber);

            Merchant = new PaytmMerchant(
                    "https://dropmeplease.microbharat.com/paytm_Checksum_Staging/generatechecksum.php",
                    "https://dropmeplease.microbharat.com/paytm_Checksum_Staging/verifychecksum.php");
        }
*//*

        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, Merchant, null);
        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {

                        Toast.makeText(getApplicationContext(), inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionSuccess(Bundle inResponse) {
                        Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();

                        //Log.d("LOG", "Payment Transaction is successful " + inResponse);
                      *//*  Bundle[{STATUS=TXN_SUCCESS, BANKNAME=, ORDERID=ORDER300005277,
                                TXNAMOUNT=1.00, TXNDATE=2016-12-07 11:11:33.0, MID=Microb85349971126787,
                                TXNID=32112662, RESPCODE=01, PAYMENTMODE=PPI, BANKTXNID=390905, CURRENCY=INR,
                                GATEWAYNAME=WALLET, IS_CHECKSUM_VALID=Y, RESPMSG=Txn Successful.}]*//*

                        String orderId = inResponse.getString("ORDERID");
                        String txnId = inResponse.getString("TXNID");
                        String txnDate = inResponse.getString("TXNDATE");
                        String banktxnId = inResponse.getString("BANKTXNID");
                        String txnAmount = inResponse.getString("TXNAMOUNT");
                        String transactionMId = inResponse.getString("MID");
                        isPayment(bookingId, orderId, txnAmount, txnDate, txnId, banktxnId, transactionMId);
                        CommonMethods.bookingIdForClient = bookingId;
                        Intent intent = new Intent(PaymentActivity.this, Payment_Successfull_Activity.class);
                        CommonMethods.isNotificationForClient = "Your ride completed successfully";
                        intent.putExtra("TXNID",txnId);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onTransactionFailure(String inErrorMessage,
                                                     Bundle inResponse) {

                        //Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed, due to " + inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                        Toast.makeText(getBaseContext(), "If network is not available ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {

                        Toast.makeText(getBaseContext(), inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                        Toast.makeText(getBaseContext(), inErrorMessage, Toast.LENGTH_LONG).show();

                    }
                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {

                    }
                });
    }*/

    public void onStartTransaction(double total) {
        PaytmPGService Service = PaytmPGService.getStagingService();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID", initOrderId());
        paramMap.put("MID", "Microb85349971126787");/*Microb85349971126787*//*WorldP64425807474247//DIY12386817555501617*/
        //paramMap.put("CUST_ID", "CUST23657");
        paramMap.put("CUST_ID", "CUST_" + CommonMethods.userid+"_"+initOrderId());
        paramMap.put("CHANNEL_ID",  "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("WEBSITE", "Microwap");/*Microwap*//*worldpressplg//DIYtestingwap*/
        paramMap.put("TXN_AMOUNT",String.valueOf(total));
        paramMap.put("THEME", "merchant");
        paramMap.put("EMAIL", "abc@gmail.com"/*email*/);
        paramMap.put("MOBILE_NO", "9343999888"/*phonenumber*/);

        PaytmOrder Order = new PaytmOrder(paramMap);

        PaytmMerchant Merchant = new PaytmMerchant("https://dropmeplease.microbharat.com/paytm_Checksum_Staging/generatechecksum.php",
                "https://dropmeplease.microbharat.com/paytm_Checksum_Staging/verifychecksum.php");

       /* PaytmMerchant Merchant = new PaytmMerchant(
                "https://dropmeplease.microbharat.com/paytm_Checksum/generatechecksum.php",
                "https://dropmeplease.microbharat.com/paytm_Checksum/verifychecksum.php");*/


        Service.initialize(Order, Merchant, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {

                        /*Toast.makeText(ProceedToPay.this, inErrorMessage.toString(), Toast.LENGTH_LONG).show();*/
                    }

                    @Override
                    public void onTransactionSuccess(Bundle inResponse) {
                        Toast.makeText(getBaseContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();

                        Log.d("LOG", "Payment Transaction is successful " + inResponse);

                        orderId = inResponse.getString("ORDERID");
                         txnId = inResponse.getString("TXNID");
                         txnDate = inResponse.getString("TXNDATE");
                         banktxnId = inResponse.getString("BANKTXNID");
                        txnAmount = inResponse.getString("TXNAMOUNT");
                        transactionMId = inResponse.getString("MID");

                       // isPayment(bookingId,orderId,txnAmount,txnDate,txnId,banktxnId,transactionMId);

                        DisplayToast();

                    }

                    @Override
                    public void onTransactionFailure(String inErrorMessage,
                                                     Bundle inResponse) {

                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed, due to "+ inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                        Toast.makeText(getBaseContext(), "If network is not available ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {

                        Toast.makeText(getBaseContext(), inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                        Toast.makeText(getBaseContext(), inErrorMessage, Toast.LENGTH_LONG).show();

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {

                    }

                });
    }


}
