package controller;

import model.Parser;

public class Controller {
    private Parser parser;

    public Controller() {
        parser = new Parser();
    }

    public String execute(String expression) {
        if (expression.isEmpty())
            return "ответ";

        String answer = parser.parse(expression);
        parser.resultsClear();
        return answer;
    }

}
