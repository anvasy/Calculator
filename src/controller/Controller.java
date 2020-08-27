package controller;

import model.TreeBuilder;
import model.Parser;
import model.Validator;

import javax.swing.tree.TreeNode;

public class Controller {
    private Parser parser;
    private TreeBuilder treeBuilder;

    public Controller() {
        parser = new Parser();
        treeBuilder = new TreeBuilder();
    }

    public boolean validate(String expression) {
        return !expression.isEmpty() && Validator.validInput(expression);
    }

    public TreeNode buildTree(String expression) {
        return treeBuilder.buildTree(Validator.replaceSymbols(expression));
    }

    public String execute(String expression) {
        if (expression.isEmpty())
            return "answer";

        String answer = parser.parse(expression);
        parser.resultsClear();
        return answer;
    }

}
