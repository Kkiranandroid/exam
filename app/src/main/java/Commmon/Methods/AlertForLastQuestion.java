package Commmon.Methods;

/**
 * Created by Administrator on 12/6/2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eniversity.app.R;



import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eniversity.app.R;

/**
 * Created by ramagouda on 6/10/2016.
 */
public class AlertForLastQuestion extends Dialog {
    SpannableStringBuilder dialogMassage;
    public Activity c;
    public Dialog d;
    public TextView massage;
    public Button buttonOK;
    public LinearLayout Lineardia_main;
    public AlertForLastQuestion(Activity a,SpannableStringBuilder dialogMassage) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.dialogMassage = dialogMassage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.custom_alert_dialog);
        Lineardia_main= (LinearLayout) findViewById(R.id.Lineardia_main);
        massage = (TextView) findViewById(R.id.txt_dia);
        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(ok);
        massage.setText(dialogMassage);

    }
    private  View.OnClickListener ok= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

}

