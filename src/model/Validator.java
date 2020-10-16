package model;

import java.util.Stack;

public class Validator {

    public static boolean validInput(String expression) {
        if (!expression.matches("(.*\\d)|(.*[!)%])") || expression.matches("(.*\\d+\\.\\d+\\..*)"))
            return false;

        return (expression.split("\\(", -1).length - 1) == (expression.split("\\)", -1).length - 1);
    }

    private static String removeExtraBrackets(String expr) {
        StringBuilder expression = new StringBuilder(expr);
        Stack<Integer> st = new Stack<>();
        int i = 0;

        while (i < expression.length())
        {
            if (expression.charAt(i) == '(') {
                if (expression.charAt(i + 1) == '(') {
                    st.push(-i);
                } else {
                    st.push(i);
                }
                i++;
            } else if (expression.charAt(i) == ')') {
                int top = st.peek();
                if (expression.charAt(i - 1) == ')' && top < 0) {
                    expression.setCharAt(-top, '@');
                    expression.setCharAt(i, '@');
                    st.pop();
                } else if (expression.charAt(i - 1) != ')' && top > 0) {
                    st.pop();
                }
                i++;
            } else {
                i++;
            }
        }

        return expression.toString().replaceAll("@", "");
    }

    public static String replaceSymbols(String expression) {
        expression = removeExtraBrackets(expression);

        expression = expression.replace("sqrt", "s");
        expression = expression.replace("reciproc", "r");
        expression = expression.replace("ln", "n");
        expression = expression.replace("lg", "g");
        expression = expression.replace("fact", "f");
        expression = expression.replace("percent", "p");

        return expression;
    }
}
