package dnsudhir.com.jdsports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import dnsudhir.com.jdsports.model.NavBO;
import dnsudhir.com.jdsports.utils.CallAPI;
import dnsudhir.com.jdsports.utils.ServiceGenerator;
import java.util.ArrayList;
import java.util.List;

public class AddViewDynamically extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGetNavData();
  }

  private void mGetNavData() {

    String api_key = "56E94850997111E3A5E20800200C9A66";

    new CallAPI.Builder<NavBO>(this, ServiceGenerator.getInstance().getNav(api_key),
        true).setOnCallCompleteListner(new CallAPI.OnCallComplete<NavBO>() {
      @Override public void CallCompleted(boolean b, NavBO result) {
        if (result != null) {
          mHandleNavBo(result);
        }
      }
    }).execute();
  }

  private void mHandleNavBo(NavBO result) {

    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setGravity(LinearLayout.VERTICAL);

    List<NavBO.NavBean> navBeans = result.getNav();
    Toast.makeText(this, navBeans.get(0).getName(), Toast.LENGTH_SHORT).show();

    for (int i = 0; i < 3; i++) {
      NavBO.NavBean navBean = navBeans.get(i);
      TextView textView = new TextView(this);
      textView.setText(navBean.getName());
      linearLayout.addView(textView);

      List<NavBO.NavBean.ChildrenBeanX> childrenBeanXList = navBean.getChildren();
      List<String> list = new ArrayList<>();
      for (int i1 = 0; i1 < childrenBeanXList.size(); i1++) {
        NavBO.NavBean.ChildrenBeanX childrenBeanX = childrenBeanXList.get(i1);

        TextView textView1 = new TextView(this);
        textView1.setText(childrenBeanX.getName());
        linearLayout.addView(textView1);

        List<NavBO.NavBean.ChildrenBeanX.ChildrenBean> childrenBeans = childrenBeanX.getChildren();

        for (int i2 = 0; i2 < childrenBeans.size(); i2++) {
          NavBO.NavBean.ChildrenBeanX.ChildrenBean childrenBean = childrenBeans.get(i2);

          TextView textView2 = new TextView(this);
          textView2.setText(childrenBean.getName());
          linearLayout.addView(textView2);

          List<String> stringList = (List<String>) childrenBean.getChildren();

          for (int i3 = 0; i3 < stringList.size(); i3++) {
          String s = stringList.get(i3);
          TextView textView3 = new TextView(this);
          textView3.setText(s);
          linearLayout.addView(textView3);
        }
      }
      }
    }

    ScrollView scrollView = new ScrollView(this);
    scrollView.addView(linearLayout);
    setContentView(scrollView);

  }
}
