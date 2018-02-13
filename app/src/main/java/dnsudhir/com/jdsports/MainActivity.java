package dnsudhir.com.jdsports;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import dnsudhir.com.jdsports.adapters.CustomListViewAdapter;
import dnsudhir.com.jdsports.model.NavBO;
import dnsudhir.com.jdsports.utils.CallAPI;
import dnsudhir.com.jdsports.utils.ServiceGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ListView expLv;
  private List<String> listDataHeader;
  private HashMap<String, List<NavBO.NavBean.ChildrenBeanX>> listDataChild;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    expLv = findViewById(R.id.exp_lv);

    listDataHeader = new ArrayList<>();
    listDataChild = new HashMap<>();

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

    List<NavBO.NavBean> navBeans = result.getNav();
    Toast.makeText(this, navBeans.get(0).getName(), Toast.LENGTH_SHORT).show();

   /* for (int i = 0; i < navBeans.size(); i++) {
      NavBO.NavBean navBean = navBeans.get(i);
      listDataHeader.add(navBean.getName());
      listDataChild.put(navBean.getName(), navBean.getChildren());
    }*/
    //ExpListAdapter<NavBO.NavBean.ChildrenBeanX> listAdapter =
    //    new ExpListAdapter<>(this, listDataHeader, listDataChild);

    CustomListViewAdapter<NavBO.NavBean> listAdapter = new CustomListViewAdapter<>(this, navBeans);

    expLv.setAdapter(listAdapter);

    expLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object object = parent.getAdapter().getItem(position);

        if (object instanceof NavBO.NavBean) {
          NavBO.NavBean navBean = (NavBO.NavBean) object;
          List<NavBO.NavBean.ChildrenBeanX> childrenBeanXList = navBean.getChildren();
          CustomListViewAdapter<NavBO.NavBean.ChildrenBeanX> adapter =
              new CustomListViewAdapter<>(MainActivity.this, childrenBeanXList);
          LinearLayout linearLayout = (LinearLayout) view;
          ListView listView = new ListView(MainActivity.this);
          listView.setAdapter(adapter);
          linearLayout.addView(listView);
        } else if (object instanceof NavBO.NavBean.ChildrenBeanX) {
          NavBO.NavBean.ChildrenBeanX childrenBeanX = (NavBO.NavBean.ChildrenBeanX) object;
          List<NavBO.NavBean.ChildrenBeanX.ChildrenBean> childrenBeans =
              childrenBeanX.getChildren();
          CustomListViewAdapter<NavBO.NavBean.ChildrenBeanX.ChildrenBean> adapter =
              new CustomListViewAdapter<>(MainActivity.this, childrenBeans);
          LinearLayout linearLayout = (LinearLayout) view;
          ListView listView = new ListView(MainActivity.this);
          listView.setAdapter(adapter);
          linearLayout.addView(listView);
        } else if (object instanceof NavBO.NavBean.ChildrenBeanX.ChildrenBean) {
          NavBO.NavBean.ChildrenBeanX.ChildrenBean childrenBean =
              (NavBO.NavBean.ChildrenBeanX.ChildrenBean) object;
          List<String> childrenBeans = (List<String>) childrenBean.getChildren();
          CustomListViewAdapter<String> adapter =
              new CustomListViewAdapter<>(MainActivity.this, childrenBeans);
          LinearLayout linearLayout = (LinearLayout) view;
          ListView listView = new ListView(MainActivity.this);
          listView.setAdapter(adapter);
          linearLayout.addView(listView);
        } else if (object instanceof String) {

        }
      }
    });
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
}
