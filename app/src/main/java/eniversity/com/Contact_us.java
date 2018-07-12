package eniversity.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.eniversity.app.R;

public class Contact_us extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageViewBackIcon;
    private TextView textViewHeading;
    private ImageView imageViewSearchIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewHeading = (TextView) toolbar.findViewById(R.id.textViewHeading);
        //imageViewSearchIcon = (ImageView) toolbar.findViewById(R.id.imageViewSearchIcon);

        imageViewBackIcon = (ImageView) toolbar.findViewById(R.id.imageViewBackIcon);
        textViewHeading.setText("Contact Us");
        //imageViewSearchIcon.setVisibility(View.GONE);
        imageViewBackIcon.setVisibility(View.VISIBLE);
        imageViewBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
