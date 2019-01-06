package com.project.stephencao.treeview.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.project.stephencao.treeview.util.Node;
import com.project.stephencao.treeview.util.TreeHelper;

import java.util.List;

public abstract class TreeListViewAdapter<T> extends BaseAdapter {
    protected List<Node> mAllNodes;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<Node> mVisibleNodes;
    protected ListView mTree;
    protected OnTreeNodeClickListener mOnTreeNodeClickListener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.mOnTreeNodeClickListener = onTreeNodeClickListener;
    }

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int pos);
    }

    public TreeListViewAdapter(ListView mTree, Context context, List<T> mData, int defaultExpandLevel) throws IllegalAccessException {
        mAllNodes = TreeHelper.getSortedNodes(mData, defaultExpandLevel);
        mVisibleNodes = TreeHelper.visibleNodesFilter(mAllNodes);
        this.mContext = context;
        mInflater = LayoutInflater.from(this.mContext);
        this.mTree = mTree;
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expandOrShrink(position);
                if (mOnTreeNodeClickListener != null) {
                    mOnTreeNodeClickListener.onClick(mVisibleNodes.get(position), position);
                }
            }
        });
    }

    private void expandOrShrink(int position) {
        Node node = mVisibleNodes.get(position);
        if (node != null) {
            if (node.isLeaf()) {
                return;
            }
            node.setExpand(!node.isExpand());
            mVisibleNodes = TreeHelper.visibleNodesFilter(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mVisibleNodes.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        convertView.setPadding(node.getLevel()*30,3,3,3);
        return convertView;
    }

    public abstract View getConvertView(Node node, int pos, View convertView, ViewGroup parent);
}
