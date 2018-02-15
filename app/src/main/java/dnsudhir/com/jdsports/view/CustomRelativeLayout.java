package dnsudhir.com.jdsports.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout {

  public CustomRelativeLayout(Context context) {
    super(context);
  }

  public CustomRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }




}
