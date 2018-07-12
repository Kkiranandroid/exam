package eniversity.com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Commmon.Methods.AlertDialogClass;
import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;

import com.eniversity.app.R;

public class AddFAQClass extends AppCompatActivity {

    EditText editTextAddQuesion;

    EditText editTextSearch;
    ImageView imageViewBackIcon, imageViewSearchIcon, iamgeViewNavigationdrawer;
    TextView textViewHeading;
    Button buttonDoneFaq;
    TextView txt_dia;
    Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_faqclass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        // imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
        // iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
        // imageViewSearchIcon.setVisibility(View.GONE);
        buttonDoneFaq = (Button) findViewById(R.id.buttonDoneFaq);
        buttonDoneFaq.setVisibility(View.VISIBLE);
        buttonDoneFaq.setOnClickListener(onClickDoneButton);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (ViewGroup.LayoutParams.WRAP_CONTENT));
        lp.setMargins(0, 8, 0, 0);
        textViewHeading.setLayoutParams(lp);
        textViewHeading.setText("Add Question");
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(closeActivity);
        editTextAddQuesion = (EditText) findViewById(R.id.editTextQuesions);
    }

    View.OnClickListener onClickDoneButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editTextAddQuesion.getText().toString().length() <= 0) {
                AlertDialogClass customDialog = new AlertDialogClass(AddFAQClass.this, "Please enter the question");
                customDialog.show();
                CommonMethods.hideKeyboard(AddFAQClass.this, (AddFAQClass.this).getCurrentFocus());
                //Toast.makeText(AddFAQClass.this, "Please Enter the Question", Toast.LENGTH_SHORT).show();
            } else {
                // String s=editTextAddQuesion.getText().toString();
                if (editTextAddQuesion.getText().toString().trim().equals("")) {
                    AlertDialogClass customDialog = new AlertDialogClass(AddFAQClass.this, "Please enter the question");
                    customDialog.show();
                    CommonMethods.hideKeyboard(AddFAQClass.this, (AddFAQClass.this).getCurrentFocus());
                } else {
                    addQuestionDialog();
                }
            }
        }
    };
    View.OnClickListener closeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backListener();
        }
    };

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void addQuestionDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddFAQClass.this);
        alertDialogBuilder.setMessage("Are you sure you want to add this question?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                try {
                    addQuesions();
                    /*Dialog dialog1= new Dialog(AddFAQClass.this);
                    dialog1.setCanceledOnTouchOutside(true);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.custom_alert_dialog);
                    txt_dia= (TextView) dialog1.findViewById(R.id.txt_dia);
                    txt_dia.setText("Question added");
                    buttonOK= (Button) dialog1.findViewById(R.id.buttonOK);
                    buttonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(AddFAQClass.this, FrequentlyAskQuesionsClass.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    dialog1.show();*/
                    //Toast.makeText(AddFAQClass.this, "Question Added", Toast.LENGTH_SHORT).show();

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

    private void addQuesions() {
        try {

            JSONObject sendRequestObjects = new JSONObject();
            sendRequestObjects.put("action", "insertfaq");
            sendRequestObjects.put("userid", CommonMethods.userid);
            sendRequestObjects.put("question", editTextAddQuesion.getText().toString());

            Log.v("send Req Objects :", sendRequestObjects.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(AddFAQClass.this);
            CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendRequestObjects, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("FAQ");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.getString("status").equals("deleted") || jsonObject.getString("status").equals("blocked")) {
                            final Dialog dialog = new Dialog(AddFAQClass.this);
                            dialog.setTitle(null);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setContentView(R.layout.custom_alert_dialog);
                            TextView message = (TextView) dialog.findViewById(R.id.txt_dia);
                            message.setText("Your account has been " + jsonObject.getString("status"));
                            Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
                            buttonOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    new LogOut(AddFAQClass.this).execute();
                                    finish();

                                }
                            });
                            dialog.show();
                        } else {
                            Dialog dialog1 = new Dialog(AddFAQClass.this);
                            dialog1.setCanceledOnTouchOutside(true);
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog1.setContentView(R.layout.custom_alert_dialog);
                            txt_dia = (TextView) dialog1.findViewById(R.id.txt_dia);
                            txt_dia.setText("Question added");
                            buttonOK = (Button) dialog1.findViewById(R.id.buttonOK);
                            buttonOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AddFAQClass.this, FrequentlyAskQuesionsClass.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            dialog1.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("FAQ1 response is ", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("the Error is", error.toString());
                    error.printStackTrace();
                }
            });

            requestQueue.add(customres);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backListener();
    }

    private void backListener() {
        Intent intent = new Intent(AddFAQClass.this, FrequentlyAskQuesionsClass.class);
        startActivity(intent);
        hideKeyboard();
        finish();
    }
}
