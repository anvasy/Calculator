package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    Controller ctr;
    private ArrayList <JButton> buttons;
    private ArrayList <JButton> lgButtons;
    private JTextField dataText;
    private JTextField answerText;
    private JTree dataTree;
    private DataTreeModel treeModel;
    private int depth = -1;

    public MainFrame() {
        setTitle("Калькулятор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(490, 440));
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(190, 212, 204));
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        buttons = new ArrayList<>();
        lgButtons = new ArrayList<>();
        ctr = new Controller(this);

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
        answerText.setText("ответ ");
        answerText.setHorizontalAlignment(JTextField.RIGHT);
        Font font1 = new Font("SansSerif", Font.PLAIN, 15);
        answerText.setFont(font1);
        answerText.setEditable(false);

        GridBagConstraints c =  new GridBagConstraints();
        c.fill   = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth  = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        add(dataText, c);

        GridBagConstraints c1 =  new GridBagConstraints();
        c1.fill   = GridBagConstraints.BOTH;
        c1.gridheight = 1;
        c1.gridwidth  = GridBagConstraints.REMAINDER;
        c1.gridx = 0;
        c1.gridy = 1;
        add(answerText, c1);

        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("The Java Series");
        dataTree = new JTree(top);
        //dataTree = new JTree(treeModel);
        dataTree.setEditable(false);
        dataTree.setRootVisible(true);

        GridBagConstraints c2 =  new GridBagConstraints();
        c2.fill   = GridBagConstraints.BOTH;
        c2.gridheight = GridBagConstraints.REMAINDER;
        c2.gridwidth  = 1;
        c2.gridx = 0;
        c2.gridy = 1;
        add(dataTree, c2);

        createButtons();
    }

    private void createButtons() {

        buttons.add( new JButton("<<"));
        buttons.add( new JButton(">>"));
        buttons.add( new JButton("C"));
        buttons.add( new JButton("CE"));
        buttons.add( new JButton("("));
        buttons.add( new JButton(")"));

        buttons.add( new JButton("1"));
        buttons.add( new JButton("2"));
        buttons.add( new JButton("3"));
        buttons.add( new JButton("+"));
        buttons.add( new JButton("%"));
        JButton lg = new JButton("lg");
        buttons.add(lg);
        lgButtons.add(lg);

        buttons.add( new JButton("4"));
        buttons.add( new JButton("5"));
        buttons.add( new JButton("6"));
        buttons.add(new JButton("-"));
        buttons.add(new JButton("sqrt"));
        JButton ln = new JButton("ln");
        buttons.add(ln);
        lgButtons.add(ln);

        buttons.add(new JButton("7"));
        buttons.add(new JButton("8"));
        buttons.add( new JButton("9"));
        buttons.add(new JButton("/"));
        buttons.add( new JButton("1/x"));
        JButton n = new JButton("!");
        buttons.add(n);
        lgButtons.add(n);

        buttons.add( new JButton("0"));
        buttons.add(new JButton("00"));
        buttons.add(new JButton("."));
        buttons.add(new JButton("*"));
        buttons.add(new JButton("="));
        buttons.add( new JButton("ENG"));

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(60, 60));
        }

        for (JButton button : lgButtons) {
            button.setEnabled(false);
        }

        int col = 2;
        int row = 1;

        for (JButton button : buttons) {

            GridBagConstraints c =  new GridBagConstraints();
            c.fill   = GridBagConstraints.BOTH;
            c.gridheight = 1;
            c.gridwidth  = 1;
            c.gridx = row;
            c.gridy = col;

            add(button, c);

            row++;
            if(row == 7) {
                col++;
                row = 1;
            }
        }

    }

    private void action() {
        for (JButton button : buttons) {
            if(button.getText() == "ENG") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (JButton button : lgButtons) {
                            button.setEnabled(!button.isEnabled());
                        }
                    }
                });
            }
            else if(button.getText() == "lg" || button.getText() == "ln" || button.getText() == "sqrt"){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+-//*///]")) {
                            dataText.setText(dataText.getText() + button.getText() + "(");
                        }
                    }
                });
            }
            else if(button.getText() == "=") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        answerText.setText(ctr.execute(dataText.getText()));
                    }
                });
            }
            else if(button.getText() == "CE") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dataText.setText("");
                        answerText.setText("ответ");
                    }
                });
            }
            else if(button.getText() == "<<") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
            }
            else if(button.getText() == ">>") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
            }
            else if(button.getText() == "C") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!dataText.getText().isEmpty())
                            dataText.setText(dataText.getText().substring(0, dataText.getText().length() - 1));
                    }
                });
            }
            else if(button.getText() == ".") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(dataText.getText().matches(".*(\\d)"))
                            dataText.setText(dataText.getText() + button.getText());
                        else
                            dataText.setText(dataText.getText() + "0" + button.getText());
                    }
                });
            }
            else if(button.getText() == "!") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+-//*///]")) {
                            dataText.setText(dataText.getText() + "fact(");
                        }
                    }
                });
            }
            else if(button.getText() == "%") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (dataText.getText().isEmpty() || dataText.getText().matches(".*[+-//*///]")) {
                            dataText.setText(dataText.getText() + "percent(");
                        }
                    }
                });
            }
            else if(button.getText() == "1/x") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dataText.setText(dataText.getText() + "reciproc(");
                    }
                });
            }
            else if(button.getText() == "+" || button.getText() == "-" || button.getText() == "/" || button.getText() == "*") {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if(dataText.getText().isEmpty()) {
                                dataText.setText("0" + button.getText());
                        }

                        if (dataText.getText().matches(".*[-//*///+]")) {
                            String text = dataText.getText();
                            text = text.substring(0, text.length() - 1) + button.getText();
                            dataText.setText(text);
                        } else {
                            dataText.setText(dataText.getText() + button.getText());
                        }
                    }
                });
            }
            else {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dataText.setText(dataText.getText() + button.getText());
                    }
                });
            }
        }
    }

}
