package eniversity.com;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.eniversity.app.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import Commmon.Methods.CustomRequest;
import adapter.FaqAdapterClass;
import get.set.FaqGetSet;

public class FrequentlyAskQuesionsClass extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextSearch;
    private TextView textViewHeading;
    private TextView textViewFaq;
    private ImageView imageViewBackIcon;
    private ImageView imageViewSearchIcon;
    private ImageView iamgeViewNavigationdrawer;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBarFaq;
    String edittextValue = "";
    TextView FAQNoQuestionTextView;
    ImageView imageViewAddQuestions;

    FaqAdapterClass adapter;
    private ArrayList<FaqGetSet> faqItemsList;
    ExpandableListView expandableListView;
    private RelativeLayout relativeLayoutAddQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequently_ask_quesions);
        progressBarFaq = (ProgressBar) findViewById(R.id.progressBarFAQ);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        relativeLayoutAddQuestions= (RelativeLayout) findViewById(R.id.relativeLayoutAddQuestions);
        //ToolBar.
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        } catch (Exception e) {

        }
        editTextSearch = (EditText) toolbar.findViewById(R.id.editTextSearch);
        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        textViewFaq = (TextView) findViewById(R.id.textViewFaq);
        imageViewAddQuestions = (ImageView) findViewById(R.id.imageViewAddQuestions);
        FAQNoQuestionTextView = (TextView) findViewById(R.id.FAQNoQuestionTextView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
       // imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);
       // iamgeViewNavigationdrawer = (ImageView) toolbar.findViewById(R.id.iamgeViewNavigationdrawer);
        textViewHeading.setText("FAQ");
        //imageViewSearchIcon.setVisibility(View.GONE);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edittextValue = "1=1";
        getAllFAQuestions(edittextValue);

        try {
            //EXPAND ONLY ONE LIST ITEM IN ENTIRE LISTVIEW..
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                    int len = adapter.getGroupCount();
                    for (int i = 0; i < len; i++) {
                        if (i != groupPosition) {
                            expandableListView.collapseGroup(i);
                        }
                    }

                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {

                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    return false;
                }
            });
        } catch (Exception e) {

        }


        if (CommonMethods.userid.length() == 0) {
            imageViewAddQuestions.setVisibility(View.GONE);
            relativeLayoutAddQuestions.setVisibility(View.GONE);
            textViewFaq.setVisibility(View.GONE);
        }
        imageViewAddQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrequentlyAskQuesionsClass.this, AddFAQClass.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getAllFAQuestions(String searchText) {

        try {
            JSONObject sendFAQObject = new JSONObject();

            sendFAQObject.put("action", "fetchanswerforfaq");
            sendFAQObject.put("wherecondition", searchText);
            sendFAQObject.put("userid", CommonMethods.userid);

            faqItemsList = new ArrayList<FaqGetSet>();


            progressBarFaq.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(FrequentlyAskQuesionsClass.this);

            final CustomRequest customres = new CustomRequest(Request.Method.POST, CommonMethods.url,
                    sendFAQObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(CommonMethods.userid.length()>0){
                        imageViewAddQuestions.setVisibility(View.VISIBLE);
                        relativeLayoutAddQuestions.setVisibility(View.VISIBLE);
                        textViewFaq.setVisibility(View.GONE);
                    }
                    Log.i("FAQ response is---> ", response.toString());
                    try {
                        String browseCatalogueitems = response.getString("FAQ");
                        JSONArray browseArray = new JSONArray(browseCatalogueitems);


                        for (int i = 0; i < browseArray.length(); i++) {
                            FaqGetSet faqItems = new FaqGetSet();
                            faqItems.setQuesion(browseArray.getJSONObject(i).getString("question"));
                            faqItems.setAnswer(browseArray.getJSONObject(i).getString("answer"));
                            faqItemsList.add(faqItems);
                        }
                        adapter = new FaqAdapterClass(FrequentlyAskQuesionsClass.this, faqItemsList);

                        expandableListView.setAdapter(adapter);
                        if (faqItemsList.size() < 1) {
                            FAQNoQuestionTextView.setVisibility(View.VISIBLE);
                        }

                        progressBarFaq.setVisibility(View.GONE);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //String msg=Html.fromHtml("<font color=\"#ffffff\">No internate connection</font>").toString();
                    if (error instanceof NoConnectionError) {
                        imageViewAddQuestions.setVisibility(View.GONE);
                        relativeLayoutAddQuestions.setVisibility(View.GONE);
                        textViewFaq.setVisibility(View.GONE);
                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internate connection", Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        edittextValue = "1=1";
                                        getAllFAQuestions(edittextValue);
                                    }
                                });
                        snackbar.show();
                        progressBarFaq.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                    else
                    {
                        imageViewAddQuestions.setVisibility(View.GONE);
                        relativeLayoutAddQuestions.setVisibility(View.GONE);
                        textViewFaq.setVisibility(View.GONE);
                        Log.v("error", error.toString());
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.tryagain), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        edittextValue = "1=1";
                                        getAllFAQuestions(edittextValue);
                                    }
                                });
                        snackbar.show();
                        progressBarFaq.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                }
            });

            requestQueue.add(customres);
            Log.i("Tag:", sendFAQObject.toString());
        } catch (Exception e) {
        }
    }

}
