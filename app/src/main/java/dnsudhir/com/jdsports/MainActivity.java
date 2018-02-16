package dnsudhir.com.jdsports;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import dnsudhir.com.jdsports.adapters.CustomListViewAdapter;
import dnsudhir.com.jdsports.adapters.ExpListAdapter;
import dnsudhir.com.jdsports.adapters.TreeAdapter;
import dnsudhir.com.jdsports.model.Content;
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
  private TreeAdapter treeAdapter;
  private ListView listView;
  private Content navMain;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    listView = findViewById(R.id.listView);
    expLv = findViewById(R.id.exp_lv);

    //Method that calls WebService To get Nav Response
    //nGetNavData();
    method3(null);
  }

  private void nGetNavData() {

    String api_key = "56E94850997111E3A5E20800200C9A66";
    /**
     * Utility Class for Calling WebServices
     */
    new CallAPI.Builder<>(this, ServiceGenerator.getInstance().getNav(api_key),
        true).setOnCallCompleteListner(new CallAPI.OnCallComplete<NavBO>() {
      @Override public void CallCompleted(boolean b, NavBO result) {
        if (result != null) {
          mHandleNavBo(result);
        }
      }
    }).execute();
  }

  /**
   * Handling the response
   */
  private void mHandleNavBo(NavBO result) {

    List<NavBO.NavBean> navBeans = result.getNav();
    method1(navBeans);
  }

  /**
   * Method 1
   * Using Expandable List View
   * We will use Recursion and Generics to create nested expandable list view
   */
  private void method1(List<NavBO.NavBean> navBeans) {
    listDataHeader = new ArrayList<>();
    listDataChild = new HashMap<>();
    for (int i = 0; i < navBeans.size(); i++) {
      NavBO.NavBean navBean = navBeans.get(i);

      listDataHeader.add(navBean.getName());
      listDataChild.put(navBean.getName(), navBean.getChildren());
    }
    ExpListAdapter<NavBO.NavBean.ChildrenBeanX> listAdapter =
        new ExpListAdapter<>(this, listDataHeader, listDataChild);
    expLv.setAdapter(listAdapter);
  }

  /**
   * Method 2
   * Using ListView
   * We will use Recursion and Generics to create nested List View which expands on click
   */
  private void method2(List<NavBO.NavBean> navBeans) {

    CustomListViewAdapter<NavBO.NavBean> listAdapter = new CustomListViewAdapter<>(this, navBeans);

    listView.setAdapter(listAdapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object object = parent.getAdapter().getItem(position);

        /**
         *  We use the same concept we use in Expandable list view
         *  On List Item click we will add listview dynamically for linearlayout
         *  Depending on the type of data we will call CustLitViewadapter recursively
         */

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
        }
      }
    });
  }

  private void method3(List<NavBO.NavBean> navBeans) {

    //treeAdapter = new TreeAdapter(this, null);

    navMain = new Content("BOOK A");

    Content part1 = new Content("Part I");
    Content chapter1 = new Content("Chapter 1");
    chapter1.addSubContent(new Content("some topic 1.1"));
    chapter1.addSubContent(new Content("some topic 1.2"));
    chapter1.addSubContent(new Content("some topic 1.3"));
    chapter1.addSubContent(new Content("some topic 1.4"));
    chapter1.addSubContent(new Content("some topic 1.5"));
    Content chapter2 = new Content("Chapter 2");
    chapter2.addSubContent(new Content("some topic 2.1"));
    chapter2.addSubContent(new Content("some topic 2.2"));
    chapter2.addSubContent(new Content("some topic 2.3"));
    Content chapter3 = new Content("Chapter 3");
    chapter3.addSubContent(new Content("some topic 3.1"));
    chapter3.addSubContent(new Content("some topic 3.2"));

    part1.addSubContent(chapter1);
    part1.addSubContent(chapter2);
    part1.addSubContent(chapter3);

    Content part2 = new Content("Part II");
    Content chapter4 = new Content("Chapter 4");
    chapter4.addSubContent(new Content("some topic 4.1"));
    chapter4.addSubContent(new Content("some topic 4.2"));
    chapter4.addSubContent(new Content("some topic 4.3"));
    chapter4.addSubContent(new Content("some topic 4.4"));
    Content chapter5 = new Content("Chapter 5");
    chapter5.addSubContent(new Content("some topic 5.1"));
    chapter5.addSubContent(new Content("some topic 5.2"));
    chapter5.addSubContent(new Content("some topic 5.3"));
    chapter5.addSubContent(new Content("some topic 5.4"));
    chapter5.addSubContent(new Content("some topic 5.5"));

    part2.addSubContent(chapter4);
    part2.addSubContent(chapter5);

    navMain.addSubContent(part1);
    navMain.addSubContent(part2);

    treeAdapter = new TreeAdapter(this, navMain, 4);
    listView.setAdapter(treeAdapter.asListAdapter());
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
