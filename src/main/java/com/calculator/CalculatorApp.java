package com.calculator;

import javax.swing.SwingUtilities;

public final class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorFrame::new);
    }
}
