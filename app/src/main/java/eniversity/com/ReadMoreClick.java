package eniversity.com;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by soumyay on 5/17/2016.
 */
public abstract class ReadMoreClick extends ClickableSpan {
    @Override
    public abstract void onClick(View widget);

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
       /* ds.setColor(Color.rgb(65, 105, 225));*/
        ds.setColor(Color.rgb(69, 185, 124));
        ds.setUnderlineText(false);
    }
}
