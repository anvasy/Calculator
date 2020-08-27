package view;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ExpressionNode implements TreeNode {

    private String operation;
    private String expression;
    private List<ExpressionNode> children;

    public ExpressionNode(String expression) {
        this.expression = expression;
        children = new ArrayList<>();
    }

    public void setChildren(ExpressionNode first, ExpressionNode second) {
        children.add(first);
        if (second != null)
            children.add(second);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (children.size() - 1 < childIndex)
            return null;
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration<ExpressionNode> children() {
        return null;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return operation;
    }
}
