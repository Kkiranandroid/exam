package eniversity.com;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.eniversity.app.R;

public class About extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageViewBackIcon;
    private TextView textViewHeading;
    private ImageView imageViewSearchIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
       // imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading.setText("About");
        //imageViewSearchIcon.setVisibility(View.GONE);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData = " Eniversity is a new technique to conduct an examination through mobile. Eniversity provides innovative examination process. Eniversity will be offered over a period of time and the candidate can choose the Day and Time of his/her convenience to take the Exam. </br></br><b>Eniversity exam Pattern:</b></br></br>" +
                "All questions of the Eniversity are of objective type (multiple choice questions with only one correct choice of answer). No marks are awarded for questions not attempted. While the candidate can skip a question, the computer will not allow the candidate to choose more than one option as correct answer. Subscribe to courses and take exam.Results are Immediately displayed.\n" +
                " \n";
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.loadData(String.format(htmlText, myData), "text/html", "utf-8");
        webView.setBackgroundColor(Color.parseColor("#ffffff"));

    }

}
