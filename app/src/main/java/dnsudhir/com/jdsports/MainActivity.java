package dnsudhir.com.jdsports;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.Toast;
import dnsudhir.com.jdsports.adapters.ExpListAdapter;
import dnsudhir.com.jdsports.model.NavBO;
import dnsudhir.com.jdsports.utils.CallAPI;
import dnsudhir.com.jdsports.utils.ServiceGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ExpandableListView expLv;
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

    String api_key = "";

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
    Toast.makeText(this, navBeans.get(1).getName(), Toast.LENGTH_SHORT).show();

    for (int i = 0; i < navBeans.size(); i++) {
      NavBO.NavBean navBean = navBeans.get(i);
      listDataHeader.add(navBean.getName());
      listDataChild.put(navBean.getName(), navBean.getChildren());
    }
    ExpListAdapter<NavBO.NavBean.ChildrenBeanX> listAdapter =
        new ExpListAdapter<>(this, listDataHeader, listDataChild);

    expLv.setAdapter(listAdapter);

    expLv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
      @Override public void onGroupExpand(int groupPosition) {




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
