package model;

import com.sun.deploy.util.StringUtils;
import view.ExpressionNode;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {

    public TreeNode buildTree(String expression) {
        List<String> lexemes = getLexemesArray(expression);
        return getNode(lexemes);
    }

    private List<String> getLexemesArray(String expression) {
        List<String> lexemes = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                lexemes.add(number.toString());
                i--;
            } else {
                lexemes.add(String.valueOf(expression.charAt(i)));
            }
        }

        return lexemes;
    }

    private ExpressionNode getNode(List<String> lexemes) {
        if (lexemes == null)
            return null;
        ExpressionNode node = new ExpressionNode(StringUtils.join(lexemes, ""));
        if (lexemes.get(0).equals("(") && lexemes.get(lexemes.size() - 1).equals(")")) {
            lexemes = lexemes.subList(1, lexemes.size() - 1);
        }
        if (lexemes.size() == 1) {
            node.setOperation(lexemes.get(0));
            return node;
        }
        int pos = getMaxPriorityPointPosition(lexemes);
        node.setOperation(getOperationName(lexemes.get(pos)));
        if (node.getOperation().matches("[a-z]+")) {
            node.setChildren(getNode(lexemes.subList(1, lexemes.size())), null);
        } else {
            node.setChildren(getNode(lexemes.subList(0, pos)), getNode(lexemes.subList(pos + 1, lexemes.size())));
        }
        return node;
    }

    private int getMaxPriorityPointPosition(List<String> lexemes) {
        int bracketCounter = 0;
        int pos = -1;
        int max = -1;
        for (int i = 0; i < lexemes.size(); i++) {
            String lexeme = lexemes.get(i);
            if (lexeme.equals("(")) {
                bracketCounter++;
            } else if (lexeme.equals(")")) {
                bracketCounter--;
            } else if (!lexeme.matches("\\d+(\\.\\d+)?")) {
                if (getPriority(lexeme) - 3 * bracketCounter >= max) {
                    max = getPriority(lexeme) - 3 * bracketCounter;
                    pos = i;
                }
            }
        }

        return pos;
    }

    private int getPriority(String operation) {
        switch (operation) {
            case "+":
            case "-":
                return 3;
            case "*":
            case "/":
                return 2;
            default:
                return 1;
        }
    }

    private String getOperationName(String operation) {
        switch (operation) {
            case "s":
                return "sqrt";
            case "r":
                return "reciproc";
            case "n":
                return "ln";
            case "g":
                return "lg";
            case "f":
                return "fact";
            case "p":
                return "percent";
            default:
                return operation;
        }
    }
}
