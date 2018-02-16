package dnsudhir.com.jdsports.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.okayj.axui.preordertreeadapter.PreOrderTreeAdapter;
import dnsudhir.com.jdsports.R;
import dnsudhir.com.jdsports.model.Content;

public class TreeAdapter extends PreOrderTreeAdapter<Content, TreeAdapter.Holder> {

  private final int VIEW_TYPE_BOOK = 0;
  private final int VIEW_TYPE_PART = 1;
  private final int VIEW_TYPE_CHAPTER = 2;
  private final int VIEW_TYPE_CONTENT = 3;

  private Context context;
  private Content content;
  private int countDepth;
  private LayoutInflater layoutInflater;

  public TreeAdapter(Context context, Content content, int countDepth) {

    this.context = context;
    this.content = content;
    this.countDepth = countDepth;
    layoutInflater = LayoutInflater.from(context);
  }

  @Override protected Content root() {
    return content;
  }

  @Override protected int getChildSize(Content content) {
    return content.subContentSize();
  }

  @Override protected Content getChild(Content parent, int childPosition) {
    return parent.getSubContent(childPosition);
  }

  @Override protected int getViewTypeCount() {
    return countDepth;
  }

  @Override protected int getViewType(Content o, int depth) {
    switch (depth) {
      case 1:
        return VIEW_TYPE_BOOK;
      case 2:
        return VIEW_TYPE_PART;
      case 3:
        return VIEW_TYPE_CHAPTER;
      case 4:
        return VIEW_TYPE_CONTENT;
      default:
        throw new IllegalStateException();
    }
  }

  @Override protected Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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
      case VIEW_TYPE_CONTENT:
        view = layoutInflater.inflate(R.layout.item_content, viewGroup, false);
        break;
    }

    return new Holder(view);
  }

  @Override protected void onBindViewHolder(Holder viewHolder, Content data) {
    viewHolder.setItem(data);
  }

  @Override protected boolean ignoreRoot() {
    return false;
  }

  static class Holder
      extends cn.okayj.axui.preordertreeadapter.PreOrderTreeAdapter.ViewHolder<Content> {
    TextView textView;

    public Holder(View itemView) {
      super(itemView);
      textView = (TextView) itemView;
    }

    @Override public void setItem(Content item) {
      super.setItem(item);
      textView.setText(item.getContent());
    }

    @Override protected void onItemSet(Content item) {
      super.onItemSet(item);
    }

    @Override public Content getItem() {
      return super.getItem();
    }
  }
}
