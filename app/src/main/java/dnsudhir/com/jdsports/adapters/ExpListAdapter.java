package dnsudhir.com.jdsports.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import dnsudhir.com.jdsports.R;
import dnsudhir.com.jdsports.model.NavBO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpListAdapter<T> extends BaseExpandableListAdapter {

  private Context context;
  private List<String> listDataHeader;
  private HashMap<String, List<T>> listDataChild;

  public ExpListAdapter(Context context, List<String> listDataHeader,
      HashMap<String, List<T>> listChildData) {
    this.context = context;
    this.listDataHeader = listDataHeader;
    this.listDataChild = listChildData;
  }

  @Override public Object getChild(int groupPosition, int childPosititon) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
  }

  @Override public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  /**
   * We Use the Same Adapter recursively to create nested expandable listview
   * So using instance of we will know which type of data is sent and act accordingly
   * We will call this adapter recursively until we get strings as data
   *
   *
   * @param groupPosition
   * @param childPosition
   * @param isLastChild
   * @param convertView
   * @param parent
   * @return
   */

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    T t = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    if (t instanceof NavBO.NavBean.ChildrenBeanX) {

      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.exp_lv_child, null);
      }

      NavBO.NavBean.ChildrenBeanX childrenBeansX = (NavBO.NavBean.ChildrenBeanX) t;
      List<NavBO.NavBean.ChildrenBeanX.ChildrenBean> childrenBeans = childrenBeansX.getChildren();
      if (childrenBeans != null && childrenBeans.size() > 0) {
        List<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<NavBO.NavBean.ChildrenBeanX.ChildrenBean>> listDataChild =
            new HashMap<>();
        listDataHeader.add(childrenBeansX.getName());
        listDataChild.put(childrenBeansX.getName(),
            (List<NavBO.NavBean.ChildrenBeanX.ChildrenBean>) childrenBeansX.getChildren());

        ExpListAdapter<NavBO.NavBean.ChildrenBeanX.ChildrenBean> listAdapter =
            new ExpListAdapter<>(context, listDataHeader, listDataChild);
        ExpandableListView expandableListView = convertView.findViewById(R.id.ExpLvChild);
        expandableListView.setAdapter(listAdapter);
      }
    } else if (t instanceof NavBO.NavBean.ChildrenBeanX.ChildrenBean) {
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.exp_lv_child, null);
      }
      NavBO.NavBean.ChildrenBeanX.ChildrenBean childrenBeans =
          (NavBO.NavBean.ChildrenBeanX.ChildrenBean) t;

      List<String> childrens = (List<String>) childrenBeans.getChildren();

      if (childrens.size() > 0) {
        List<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<String>> listDataChild = new HashMap<>();
        List<String> strings = new ArrayList<>();

        listDataHeader.add(childrenBeans.getName());
        listDataChild.put(childrenBeans.getName(), (List<String>) childrenBeans.getChildren());

        ExpListAdapter<String> listAdapter =
            new ExpListAdapter<String>(context, listDataHeader, listDataChild);
        ExpandableListView expandableListView = convertView.findViewById(R.id.ExpLvChild);
        expandableListView.setAdapter(listAdapter);
      }
    } else if (t instanceof String) {

      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_child_item, null);
      }
      TextView tvChildItem = convertView.findViewById(R.id.tvChildItem);
      tvChildItem.setText(t.toString());
    }
    return convertView;
  }

  @Override public int getChildrenCount(int groupPosition) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
  }

  @Override public Object getGroup(int groupPosition) {
    return this.listDataHeader.get(groupPosition);
  }

  @Override public int getGroupCount() {
    return this.listDataHeader.size();
  }

  @Override public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    String headerTitle = (String) getGroup(groupPosition);
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.exp_lv_grp, null);
    }

    TextView lblListHeader = convertView.findViewById(R.id.tvExpListHeader);
    lblListHeader.setTypeface(null, Typeface.BOLD);
    lblListHeader.setText(headerTitle);

    return convertView;
  }

  @Override public boolean hasStableIds() {
    return false;
  }

  @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
}
