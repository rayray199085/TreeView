package com.project.stephencao.treeview.util;

import com.project.stephencao.treeview.R;
import com.project.stephencao.treeview.annotation.TreeNodeId;
import com.project.stephencao.treeview.annotation.TreeNodeLabel;
import com.project.stephencao.treeview.annotation.TreeNodePId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper {
    public static <T> List<Node> convertData2Node(List<T> data) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        int id = -1;
        int pId = -1;
        String label = null;
        for (T t : data) {
            Class<?> aClass = t.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
//                    check the type of the value
//                    Class type = field.getAnnotation(TreeNodeId.class).type();
//                    if(type==Integer.class){
//
//                    }
                    field.setAccessible(true);
                    id = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodePId.class) != null) {
                    field.setAccessible(true);
                    pId = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }
            }
            node = new Node(id, pId, label);
            nodes.add(node);
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node currentNode = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node nextNode = nodes.get(j);
                if (nextNode.getpId() == currentNode.getId()) {
                    nextNode.setParent(currentNode);
                    currentNode.getChildren().add(nextNode);
                } else if (currentNode.getpId() == nextNode.getId()) {
                    currentNode.setParent(nextNode);
                    nextNode.getChildren().add(nextNode);
                }
            }
        }
        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    private static void setNodeIcon(Node n) {
        if (n.getChildren().size() > 0) {
            if (n.isExpand()) {
                n.setIcon(R.drawable.tree_ex);
            } else {
                n.setIcon(R.drawable.tree_ec);
            }
        } else {
            n.setIcon(-1);
        }
    }

    public static <T> List<Node> getSortedNodes(List<T> data, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertData2Node(data);
        List<Node> rootNodes = getRootNodes(nodes);
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        List<Node> children = node.getChildren();
        for (Node n : children) {
            addNode(result, n, defaultExpandLevel, currentLevel + 1);
        }
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> rootNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    public static List<Node> visibleNodesFilter(List<Node> nodes) {
        List<Node> nodeList = new ArrayList<>();
        for (Node node : nodes) {
            if(node.isRoot() || node.isParentExpand()){
                setNodeIcon(node);
                nodeList.add(node);
            }
        }
        return nodeList;
    }
}
