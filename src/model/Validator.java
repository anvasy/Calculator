package model;

public class Validator {

    public static boolean validInput(String expression) {
        if (!expression.matches("(.*\\d)|(.*[!)%])") || expression.matches("(.*\\d+\\.\\d+\\..*)"))
            return false;

        return (expression.split("\\(", -1).length - 1) == (expression.split("\\)", -1).length - 1);
    }

    public static String replaceSymbols(String expression) {
        expression = expression.replace("sqrt", "s");
        expression = expression.replace("reciproc", "r");
        expression = expression.replace("ln", "n");
        expression = expression.replace("lg", "g");
        expression = expression.replace("fact", "f");
        expression = expression.replace("percent", "p");

        return expression;
    }
}
