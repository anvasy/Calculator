package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private Controller ctr;
    private ArrayList<JButton> buttons;
    private ArrayList<JButton> lgButtons;
    private JTextField dataText;
    private JTextField answerText;

    public MainFrame() {
        setTitle("Калькулятор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(460, 390));
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(190, 212, 204));
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        buttons = new ArrayList<>();
        lgButtons = new ArrayList<>();
        ctr = new Controller();

        initComponents();
        action();
        revalidate();
        repaint();
    }

    private void initComponents() {
        dataText = new JTextField(20);
        dataText.setPreferredSize((new Dimension(500, 60)));
        Font font = new Font("SansSerif", Font.PLAIN, 27);
        dataText.setFont(font);
        dataText.setEditable(false);

        answerText = new JTextField(20);
        answerText.setPreferredSize((new Dimension(500, 40)));
        answerText.setText("answer");
        answerText.setHorizontalAlignment(JTextField.RIGHT);
        Font font1 = new Font("SansSerif", Font.PLAIN, 15);
        answerText.setFont(font1);
        answerText.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        add(dataText, c);

        c.gridy = 1;
        add(answerText, c);
        createButtons();
    }

    private void createButtons() {
        buttons.add(new JButton("1"));
        buttons.add(new JButton("2"));
        buttons.add(new JButton("3"));
        buttons.add(new JButton("C"));
        buttons.add(new JButton("CE"));
        buttons.add(new JButton("tree"));
        JButton lg = new JButton("lg");
        buttons.add(lg);
        lgButtons.add(lg);

        buttons.add(new JButton("4"));
        buttons.add(new JButton("5"));
        buttons.add(new JButton("6"));
        buttons.add(new JButton("+"));
        buttons.add(new JButton("*"));
        buttons.add(new JButton("%"));
        JButton ln = new JButton("ln");
        buttons.add(ln);
        lgButtons.add(ln);

        buttons.add(new JButton("7"));
        buttons.add(new JButton("8"));
        buttons.add(new JButton("9"));
        buttons.add(new JButton("-"));
        buttons.add(new JButton("/"));
        buttons.add(new JButton("sqrt"));
        JButton n = new JButton("!");
        buttons.add(n);
        lgButtons.add(n);

        buttons.add(new JButton("."));
        buttons.add(new JButton("0"));
        buttons.add(new JButton("="));
        buttons.add(new JButton("("));
        buttons.add(new JButton(")"));
        buttons.add(new JButton("1/x"));
        buttons.add(new JButton("ENG"));

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(60, 60));
        }

        for (JButton button : lgButtons) {
            button.setEnabled(false);
        }

        int col = 2;
        int row = 1;

        for (JButton button : buttons) {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = row;
            c.gridy = col;

            add(button, c);

            row++;
            if (row == 8) {
                col++;
                row = 1;
            }
        }
    }

    private void action() {
        for (JButton button : buttons) {
            String buttonName = button.getText();
            switch (buttonName) {
                case "ENG":
                    button.addActionListener(e -> {
                        for (JButton button1 : lgButtons) {
                            button1.setEnabled(!button1.isEnabled());
                        }
                    });
                    break;
                case "lg":
                case "ln":
                case "sqrt":
                    button.addActionListener(e -> {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+\\-/*]")) {
                            dataText.setText(dataText.getText() + button.getText() + "(");
                        }
                    });
                    break;
                case "=":
                    button.addActionListener(e -> answerText.setText(ctr.execute(dataText.getText())));
                    break;
                case "CE":
                    button.addActionListener(e -> {
                        dataText.setText("");
                        answerText.setText("answer");
                    });
                    break;
                case "tree":
                    button.addActionListener(e -> {
                        if (!ctr.validate(dataText.getText()))
                            JOptionPane.showMessageDialog(this, "Invalid expression.");
                        else
                            new TreeFrame(ctr, dataText.getText());
                    });
                    break;
                case "C":
                    button.addActionListener(e -> {
                        if (!dataText.getText().isEmpty())
                            dataText.setText(dataText.getText().substring(0, dataText.getText().length() - 1));
                    });
                    break;
                case ".":
                    button.addActionListener(e -> {
                        if (dataText.getText().matches(".*(\\d)") && !dataText.getText().matches(".*((\\d)+(\\.)(\\d)*)$"))
                            dataText.setText(dataText.getText() + button.getText());
                        else if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+\\-/*]$"))
                            dataText.setText(dataText.getText() + "0.");
                    });
                    break;
                case "!":
                    button.addActionListener(e -> {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+\\-/*]")) {
                            dataText.setText(dataText.getText() + "fact(");
                        }
                    });
                    break;
                case "%":
                    button.addActionListener(e -> {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+\\-/*]")) {
                            dataText.setText(dataText.getText() + "percent(");
                        }
                    });
                    break;
                case "1/x":
                    button.addActionListener(e -> dataText.setText(dataText.getText() + "reciproc("));
                    break;
                case "+":
                case "-":
                case "/":
                case "*":
                    button.addActionListener(e -> {
                        if (dataText.getText().isEmpty()) {
                            dataText.setText("0" + button.getText());
                        }

                        if (dataText.getText().matches(".*[+\\-/*]")) {
                            String text = dataText.getText();
                            text = text.substring(0, text.length() - 1) + button.getText();
                            dataText.setText(text);
                        } else {
                            dataText.setText(dataText.getText() + button.getText());
                        }
                    });
                    break;
                default:
                    button.addActionListener(e -> dataText.setText(dataText.getText() + button.getText()));
                    break;
            }
        }
    }
}
