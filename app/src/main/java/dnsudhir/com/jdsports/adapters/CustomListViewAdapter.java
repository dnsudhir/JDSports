package dnsudhir.com.jdsports.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dnsudhir.com.jdsports.R;
import dnsudhir.com.jdsports.model.NavBO;
import java.util.List;

public class CustomListViewAdapter<T> extends BaseAdapter {

  private Context context;
  private List<T> list;

  public CustomListViewAdapter(Context context, List<T> list) {
    this.context = context;
    this.list = list;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int position) {
    return list.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * We Use the Same Adapter recursively to create nested listview
   * So using instance of we will know which type of data is sent and act accordingly
   * We will call this adapter recursively until we get strings as data
   */

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Holder holder;
    T t = list.get(position);

    String label = "";

    if (t instanceof NavBO.NavBean) {
      NavBO.NavBean navBean = (NavBO.NavBean) t;
      label = navBean.getName();
    } else if (t instanceof NavBO.NavBean.ChildrenBeanX) {
      NavBO.NavBean.ChildrenBeanX childrenBeanX = (NavBO.NavBean.ChildrenBeanX) t;
      label = childrenBeanX.getName();
    } else if (t instanceof NavBO.NavBean.ChildrenBeanX.ChildrenBean) {
      NavBO.NavBean.ChildrenBeanX.ChildrenBean childrenBean =
          (NavBO.NavBean.ChildrenBeanX.ChildrenBean) t;
      label = childrenBean.getName();
    } else if (t instanceof String) {
      label = (String) t;
    }

    if (convertView == null) {
      holder = new Holder();
      convertView = View.inflate(context, R.layout.list_child_item, null);
      holder.tvItemName = convertView.findViewById(R.id.tvChildItem);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }

    holder.tvItemName.setText(label);

    return convertView;
  }

  private static class Holder {
    private TextView tvItemName;
  }
}














