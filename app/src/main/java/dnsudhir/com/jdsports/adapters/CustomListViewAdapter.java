package dnsudhir.com.jdsports.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Holder holder;
    T t = list.get(position);

    if (t instanceof NavBO.NavBean) {
      NavBO.NavBean navBean = (NavBO.NavBean) t;
      if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(context, R.layout.list_child_item, null);
        holder.tvItemName = convertView.findViewById(R.id.tvChildItem);
        convertView.setTag(holder);
      } else {
        holder = (Holder) convertView.getTag();
      }

      holder.tvItemName.setText(navBean.getName());
    } else if (t instanceof NavBO.NavBean.ChildrenBeanX) {
      NavBO.NavBean.ChildrenBeanX childrenBeanX = (NavBO.NavBean.ChildrenBeanX) t;
      if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(context, R.layout.list_child_item, null);
        holder.tvItemName = convertView.findViewById(R.id.tvChildItem);
        convertView.setTag(holder);
      } else {
        holder = (Holder) convertView.getTag();
      }
      holder.tvItemName.setText(childrenBeanX.getName());
    } else if (t instanceof NavBO.NavBean.ChildrenBeanX.ChildrenBean) {
      NavBO.NavBean.ChildrenBeanX.ChildrenBean childrenBean =
          (NavBO.NavBean.ChildrenBeanX.ChildrenBean) t;
      if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(context, R.layout.list_child_item, null);
        holder.tvItemName = convertView.findViewById(R.id.tvChildItem);
        convertView.setTag(holder);
      } else {
        holder = (Holder) convertView.getTag();
      }

      holder.tvItemName.setText(childrenBean.getName());
    } else if (t instanceof String) {
      String itemName = (String) t;
      if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(context, R.layout.list_child_item, null);
        holder.tvItemName = convertView.findViewById(R.id.tvChildItem);

        convertView.setTag(holder);
      } else {
        holder = (Holder) convertView.getTag();
      }
      holder.tvItemName.setText(itemName);
    }

    return convertView;
  }

  static class Holder {
    private ListView listView;
    private TextView tvItemName;
  }
}














