package dnsudhir.com.jdsports;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.okayj.axui.preordertreeadapter.PreOrderTreeAdapter;
import dnsudhir.com.jdsports.adapters.ExpListAdapter;
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

  class TreeAdapter extends PreOrderTreeAdapter<Content, PreOrderTreeAdapter.ViewHolder> {
    private final int VIEW_TYPE_BOOK = 0;
    private final int VIEW_TYPE_PART = 1;
    private final int VIEW_TYPE_CHAPTER = 2;
    private final int VIEW_TYPE_TOPIC = 3;
    private final int VIEW_TYPE_CONTENT = 4;

    LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
    private Content content;

    public TreeAdapter(Content content) {
      this.content = content;
    }

    @Override protected Content root() {
      return content;
    }

    @Override protected boolean ignoreRoot() {
      return false;
    }

    @Override protected int getChildSize(Content content) {
      return content.subContentSize();
    }

    @Override protected Content getChild(Content parent, int childPosition) {
      return parent.getSubContent(childPosition);
    }

    @Override protected int getViewTypeCount() {
      return 4;
    }

    @Override protected int getViewType(Content content, int depth) {
      switch (depth) {
        case 1:
          return VIEW_TYPE_BOOK;
        case 2:
          return VIEW_TYPE_PART;
        case 3:
          return VIEW_TYPE_CHAPTER;
        case 4:
          return VIEW_TYPE_TOPIC;
        case 5:
          return VIEW_TYPE_CONTENT;
        default:
          throw new IllegalStateException();
      }
    }

    @Override protected ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      View view = null;
      switch (viewType) {
        case VIEW_TYPE_BOOK:
          view = layoutInflater.inflate(R.layout.item_book, viewGroup, false);
          break;
        case VIEW_TYPE_PART:
          view = layoutInflater.inflate(R.layout.item_part, viewGroup, false);
          break;
        case VIEW_TYPE_CHAPTER:
          view = layoutInflater.inflate(R.layout.item_chapter, viewGroup, false);
          break;
        case VIEW_TYPE_TOPIC:
          view = layoutInflater.inflate(R.layout.item_chapter, viewGroup, false);
          break;
        case VIEW_TYPE_CONTENT:
          view = layoutInflater.inflate(R.layout.item_content, viewGroup, false);
          break;
      }

      ViewHolder viewHolder = new ViewHolder(view);
      viewHolder.bindView();
      return viewHolder;
    }

    @Override protected void onBindViewHolder(ViewHolder viewHolder, Content data) {
      viewHolder.setItem(data);
    }

    @Override protected int getItemId(Content data) {
      return 0;
    }
  }

  class ViewHolder extends cn.okayj.axui.viewholder.ViewHolder<Content> {
    TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
    }

    @Override public void setItem(Content item) {
      super.setItem(item);
      textView.setText(item.getContent());
    }

    @Override protected void onItemSet(Content item) {
      super.onItemSet(item);
      textView.setText(item.getContent());
    }
    }

    }
}
