package view;

import controller.Controller;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import java.awt.Color;
import java.awt.BorderLayout;

public class TreeFrame extends JFrame {
    Controller controller;
    String expression;

    JLabel answer;
    JTree expressionTree;

    public TreeFrame(Controller controller, String expression) {
        this.controller = controller;
        this.expression = expression;

        setTitle(expression);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(190, 212, 204));

        initComponents();
        action();
    }

    private void initComponents() {
        expressionTree = new JTree(controller.buildTree(expression));

        expressionTree.setCellRenderer(new TreeCellStyle());
        add(expressionTree, BorderLayout.CENTER);
        answer = new JLabel(expression + " = " + controller.execute(expression));
        add(answer, BorderLayout.PAGE_END);
    }

    private void action() {
        expressionTree.addTreeSelectionListener(e -> {
            ExpressionNode selectedNode =
                    (ExpressionNode) expressionTree.getLastSelectedPathComponent();
            answer.setText(selectedNode.getExpression() + " = " + controller.execute(selectedNode.getExpression()));
        });
    }
}
