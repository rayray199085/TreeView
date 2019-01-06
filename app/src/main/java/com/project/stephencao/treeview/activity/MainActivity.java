package com.project.stephencao.treeview.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.project.stephencao.treeview.R;
import com.project.stephencao.treeview.adapter.SimpleTreeListViewAdapter;
import com.project.stephencao.treeview.adapter.TreeListViewAdapter;
import com.project.stephencao.treeview.bean.FileBean;
import com.project.stephencao.treeview.bean.OrgBean;
import com.project.stephencao.treeview.util.Node;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private SimpleTreeListViewAdapter<OrgBean> mSimpleTreeListViewAdapter;
    private List<FileBean> mData;
    private List<OrgBean> mNewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.id_listview);
        initData();
        initEvent();
    }

    private void initEvent() {
        try {
            mSimpleTreeListViewAdapter = new SimpleTreeListViewAdapter<>(mListView, this, mNewData, 1);
            mListView.setAdapter(mSimpleTreeListViewAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mSimpleTreeListViewAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int pos) {
                if (node.isLeaf()) {
                    Toast.makeText(MainActivity.this, node.getName() + " ----> " + pos, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Node");
                final EditText editText = new EditText(MainActivity.this);
                builder.setView(editText);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if("".equals(editText.getText().toString())){
                            return;
                        }
                        mSimpleTreeListViewAdapter.addAdditionalNode(position,editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true; // return false will also activate item click
            }
        });
    }

    private void initData() {
//        mData = new ArrayList<>();
//        FileBean fileBean = new FileBean(1, 0, "root 1");
//        mData.add(fileBean);
//        fileBean = new FileBean(2, 0, "root 2");
//        mData.add(fileBean);
//        fileBean = new FileBean(3, 0, "root 3");
//        mData.add(fileBean);
//        fileBean = new FileBean(4, 1, "root 1-1");
//        mData.add(fileBean);
//        fileBean = new FileBean(5, 1, "root 1-2");
//        mData.add(fileBean);
//        fileBean = new FileBean(6, 5, "root 1-2-1");
//        mData.add(fileBean);
//        fileBean = new FileBean(7, 3, "root 3-1");
//        mData.add(fileBean);
//        fileBean = new FileBean(8, 3, "root 3-2");
//        mData.add(fileBean);
        mNewData = new ArrayList<>();
        OrgBean orgBean = new OrgBean(1, 0, "root 1");
        mNewData.add(orgBean);
        orgBean = new OrgBean(2, 0, "root 2");
        mNewData.add(orgBean);
        orgBean = new OrgBean(3, 0, "root 3");
        mNewData.add(orgBean);
        orgBean = new OrgBean(4, 1, "root 1-1");
        mNewData.add(orgBean);
        orgBean = new OrgBean(5, 1, "root 1-2");
        mNewData.add(orgBean);
        orgBean = new OrgBean(6, 5, "root 1-2-1");
        mNewData.add(orgBean);
        orgBean = new OrgBean(7, 3, "root 3-1");
        mNewData.add(orgBean);
        orgBean = new OrgBean(8, 3, "root 3-2");
        mNewData.add(orgBean);
    }
}
