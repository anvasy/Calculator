package controller;

import model.Parser;
import view.MainFrame;

public class Controller {
    private Parser parser;
    private int depth;

    public Controller(MainFrame mf) {
        parser = new Parser();
        depth = -1;
    }

    public String execute(String expression) {
        if(expression.isEmpty())
            return "ответ";

        String answer = parser.parse(expression);
        parser.resultsClear();
        return answer;
    }

}
