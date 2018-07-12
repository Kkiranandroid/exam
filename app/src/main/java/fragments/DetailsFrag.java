package fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.eniversity.app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import Commmon.Methods.LogOut;
import eniversity.com.CorseInfo;
import eniversity.com.ProceedToPay;
import eniversity.com.ReadMoreClick;
import get.set.CountGetSet;
import get.set.Examdetails_GetSet;

import static com.eniversity.app.R.id.linearLayout1;

/**
 * Created by soumyay on 4/17/2017.
 */
@SuppressLint("ValidFragment")
public class DetailsFrag extends Fragment {
    LinearLayout linearlayout_details_main;

    String courseId="";
    String fees="";
    String num_of_users="";
    String Description="";
    Float ratings;
    String isRated = "";
    String CourseName="";
    TextView courseNameTextView;
    TextView courseFeesTextView;
    TextView courseUsersTextView;
    TextView descriptionTextView;
    LinearLayout ratingLayout;
    RatingBar UserRatingsratingBar;
    RatingBar userRatingBar;
    TextView ratingDescriptionTextView;
    Button ratingSubmitButton;
   // ImageView addratingImageView;

    float rating;
    String description;
    String ratingVal = "";

    String[] ratingval2;
    ImageView addratingImageView;


    public DetailsFrag(String courseId,String CourseName,String fees,String num_of_users, String Description, Float ratings,String isRated){
        this.courseId=courseId;
        this.CourseName=CourseName;
        this.fees=fees;
        this.num_of_users=num_of_users;
        this.Description=Description;
        this.ratings=ratings;
        this.isRated=isRated;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_frag_layout, container, false);

        linearlayout_details_main= (LinearLayout) view.findViewById(R.id.linearlayout_details_main);

        addratingImageView= (ImageView) view.findViewById(R.id.addratingImageView);
        courseNameTextView= (TextView) view.findViewById(R.id.courseNameTextView);
        courseFeesTextView= (TextView) view.findViewById(R.id.courseFeesTextView);
        courseUsersTextView= (TextView) view.findViewById(R.id.courseUsersTextView);
        descriptionTextView= (TextView) view.findViewById(R.id.descriptionTextView);
        UserRatingsratingBar= (RatingBar) view.findViewById(R.id.UserRatingsratingBar);
        ratingLayout= (LinearLayout) view.findViewById(R.id.ratingLayout);
        courseNameTextView.setText(CourseName);
        courseFeesTextView.setText(fees);
        courseUsersTextView.setText(num_of_users);
        if(!Description.equals("")) {
            descriptionTextView.setText(Description);
        }
        else{
            descriptionTextView.setVisibility(View.GONE);
        }

        if (Description.length() > 70) {
            makeTextViewResizable(descriptionTextView, 2, "Read More", true);
        } else {

        }

        if(!isRated.equals("0")){
            addratingImageView.setVisibility(View.GONE);
        }

        UserRatingsratingBar.setRating(ratings);
        UserRatingsratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });



        if (CommonMethods.userid.length() > 0) {

            ratingLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.user_rating);

                    // dialog.setTitle("  User Ratings  ");

                    if (isRated.equals("0")) {
                        dialog.show();
                    } else {

                    }
                    dialog.setCanceledOnTouchOutside(true);
                    userRatingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
                    ratingDescriptionTextView = (TextView) dialog.findViewById(R.id.textViewDescription);
                    ratingSubmitButton = (Button) dialog.findViewById(R.id.submitButton);
                    ratingSubmitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rating = userRatingBar.getRating();
                            description = ratingDescriptionTextView.getText().toString();

                            /*AlertDialog.Builder builder = new AlertDialog.Builder(CorseInfo.this);
                            builder.setMessage("Rating submitted, thank you");
                            builder.setPositiveButton("OK", null);
                            AlertDialog alert = builder.create();*/
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Please enter ratings");
                            builder1.setPositiveButton("OK", null);
                            AlertDialog alert1 = builder1.create();
                            if (userRatingBar.getRating() == 0.0) {
                                alert1.show();
                                //dialog.dismiss();
                            } else {
                                isRated = "1";
                                //alert.show();
                                setUserRating();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            });
        } else {
            addratingImageView.setVisibility(View.GONE);

        }

        return view;
    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                }
            }
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ReadMoreClick() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {


                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);

                        tv.invalidate();
                        makeTextViewResizable(tv, -2, "Read Less", false);


                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);

                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "Read More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    private void setUserRating() {
        try {
            JSONObject getCourseDetails = new JSONObject();
            getCourseDetails.put("action", "inserrating");
            getCourseDetails.put("userid", CommonMethods.userid);
            getCourseDetails.put("courseid", courseId);
            getCourseDetails.put("rating", rating);
            getCourseDetails.put("description", description);

            Log.v("Sending Rating Req :", getCourseDetails.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            CustomRequest courses = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    getCourseDetails, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("the rating response is ", response.toString());
                    String browseCatalogueitems = null;
                    try {
                        browseCatalogueitems = response.getString("RatingId");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);
                        for (int i = 0; i < browseArray.length(); i++) {
                            if (browseArray.getJSONObject(i).getString("status").equals("deleted") ||
                                    browseArray.getJSONObject(i).getString("status").equals("blocked")) {
                                final Dialog dialog = new Dialog(getActivity());
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
                                        new LogOut(getActivity()).execute();
                                        getActivity().finish();

                                    }
                                });
                                dialog.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Rating submitted, thank you");
                                builder.setPositiveButton("OK", null);
                                AlertDialog alert = builder.create();
                                alert.show();
                                ratingVal = browseArray.getJSONObject(i).getString("ratingid");
                                ratingval2 = ratingVal.split("\\^");
                                addratingImageView.setVisibility(View.GONE);
                                //Toast.makeText(CorseInfo.this, ratingval2[1],Toast.LENGTH_SHORT).show();
                                UserRatingsratingBar.setRating(Float.parseFloat(String.valueOf(ratingval2[1])));
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });
            requestQueue.add(courses);
        } catch (Exception r) {

        }
    }


}
