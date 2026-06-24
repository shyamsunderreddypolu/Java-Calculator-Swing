package com.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalculatorFrame extends JFrame implements ActionListener {
    private final JLabel displayLabel;
    private final CalculatorEngine engine;

    public CalculatorFrame() {
        super("Java Swing Calculator");
        this.engine = new CalculatorEngine();
        this.displayLabel = createDisplayLabel();
        initializeFrame();
    }

    private JLabel createDisplayLabel() {
        JLabel label = new JLabel("", JLabel.RIGHT);
        label.setPreferredSize(new Dimension(440, 90));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.PLAIN, 32));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        return label;
    }

    private void initializeFrame() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.DARK_GRAY);
        add(displayLabel, BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(createGridPanel(), BorderLayout.CENTER);
        panel.add(createClearButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "x",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            panel.add(createButton(text));
        }
        return panel;
    }

    private JPanel createClearButtonPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);
        JButton clearButton = createButton("Clear");
        clearButton.setBackground(new Color(230, 57, 70));
        clearButton.setForeground(Color.WHITE);
        panel.add(clearButton, BorderLayout.CENTER);
        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.addActionListener(this);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 70));
        return button;
    }

    private void updateDisplay(String text) {
        displayLabel.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = ((JButton) event.getSource()).getText();
        try {
            switch (command) {
                case "Clear":
                    engine.clear();
                    break;
                case "=":
                    engine.evaluate();
                    break;
                default:
                    engine.append(command);
                    break;
            }
            updateDisplay(engine.getDisplayText());
        } catch (IllegalArgumentException ex) {
            engine.clear();
            updateDisplay("Error");
        }
    }
}
