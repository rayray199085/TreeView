package com.project.stephencao.treeview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.project.stephencao.treeview.R;
import com.project.stephencao.treeview.util.Node;
import com.project.stephencao.treeview.util.TreeHelper;

import java.util.List;

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {
    public SimpleTreeListViewAdapter(ListView mTree, Context context, List<T> mData, int defaultExpandLevel) throws IllegalAccessException {
        super(mTree, context, mData, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int pos, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder.mImageView = convertView.findViewById(R.id.id_item_icon);
            viewHolder.mTextView = convertView.findViewById(R.id.id_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            viewHolder.mImageView.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolder.mImageView.setVisibility(View.VISIBLE);
            viewHolder.mImageView.setImageResource(node.getIcon());
        }
        viewHolder.mTextView.setText(node.getName());
        return convertView;
    }

    public void addAdditionalNode(int position, String name) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        Node child = new Node(mAllNodes.size(),node.getId(),name);
        node.getChildren().add(child);
        child.setParent(node);
        mAllNodes.add(indexOf+1,child);
        mVisibleNodes = TreeHelper.visibleNodesFilter(mAllNodes);
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
