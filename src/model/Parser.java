package model;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private double result;
    private String remainder;

    public String parse(String expression) {

        if(!validInput(expression))
            return "ошибка";

        expression = replaceSymbols(expression);
        sumSubtract(expression);
        return String.valueOf(result);
    }

    public void resultsClear() {
        remainder = "";
        result = 0;
    }

    private boolean validInput (String expression) {

        if(!expression.matches("(.*\\d)|(.*[!)%])") || expression.matches("(.*\\d+\\.\\d+\\..*)"))
            return false;

        int bracketPairsCounter = 0;

        for(char sym : expression.toCharArray()) {
            if(sym == '(')
                bracketPairsCounter++;
            if(sym == ')')
                bracketPairsCounter--;
        }

        if(bracketPairsCounter == 0)
            return true;
        else
            return false;
    }

    private String replaceSymbols(String expression) {
        expression = expression.replace("sqrt", "s");
        expression = expression.replace("reciproc", "r");
        expression = expression.replace("ln", "n");
        expression = expression.replace("lg", "g");
        expression = expression.replace("fact", "f");
        expression = expression.replace("percent", "p");

        return expression;
    }

    private void sumSubtract(String expression) {

        multiplyDivide(expression);
        double res = result;

        while (remainder.length() > 0) {

            if (!(remainder.startsWith("+") || remainder.startsWith("-")))
                break;

            char sign = remainder.charAt(0);
            String next = remainder.substring(1);

            multiplyDivide(next);

            if (sign == '+') {
                res += result;
            } else {
                res -= result;
            }
        }

        result = res;
    }

    private void multiplyDivide(String expression) {
        doBrackets(expression);

        double res = result;
        while (true) {
            if (remainder.length() == 0) {
                return;
            }
            char sign = remainder.charAt(0);
            if ((sign != '*' && sign != '/'))
                return;

            String next = remainder.substring(1);

            doBrackets(next);

            if (sign == '*') {
                res *= result;
            } else {
                res /= result;
            }

            result = res;
        }
    }

    private void doBrackets(String expression) {

        if (expression.startsWith("(")) {
            sumSubtract(expression.substring(1));
            if (!remainder.isEmpty() && remainder.charAt(0) == ')')
                remainder = remainder.substring(1);
            return;
        }

        doFunction(expression);
    }

    private void doFunction(String expression) {
        String f = "";

        int i = 0;
        while (i < expression.length() &&
                (Character.isLetter(expression.charAt(i)) || ( Character.isDigit(expression.charAt(i)) && i > 0 ) )) {
            f += expression.charAt(i);
            i++;
        }

        if (!f.isEmpty()) {
            doBrackets(expression.substring(f.length()));
            functionsCount(f);
        }

        doDigit(expression);
    }

    private void functionsCount(String s) {

        switch (s) {
            case "r":
                result = 1/result;
                break;
            case "f":
                result = factorial(result);
                break;
            case "p":
                result = 100/result;
                break;
            case "n":
                result = Math.log(result);
                break;
            case "g":
                result = Math.log10(result);
                break;
            case "s":
                result = Math.sqrt(result);
                break;
        }

    }

    private void doDigit(String s)
    {
        boolean negative = false;

        if(s.startsWith("-")){
            negative = true;
            s = s.substring(1);
        }

        int i = 0;
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.'))  i++;
        if(i == 0)
            return;

        double num = Double.parseDouble(s.substring(0, i));
        if(negative)
            num = -num;

        remainder = s.substring(i);
        result = num;
    }

    private double factorial(double number) {
        double res = 1;
        for (double factor = 2; factor <= number; factor++) {
            res *= factor;
        }
        return res;
    }

}
