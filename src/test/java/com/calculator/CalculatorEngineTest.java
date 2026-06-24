package com.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorEngineTest {

    @Test
    public void appendsDigitsAndOperatorsCorrectly() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("7");
        engine.append("+");
        engine.append("3");
        assertEquals("7+3", engine.getDisplayText());
    }

    @Test
    public void replacesConsecutiveOperators() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("7");
        engine.append("+");
        engine.append("-");
        engine.append("2");
        assertEquals("7-2", engine.getDisplayText());
    }

    @Test
    public void evaluatesBasicExpression() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("7");
        engine.append("+");
        engine.append("3");
        engine.evaluate();
        assertEquals("10", engine.getDisplayText());
    }

    @Test
    public void evaluatesOperatorPrecedence() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("2");
        engine.append("+");
        engine.append("3");
        engine.append("x");
        engine.append("4");
        engine.evaluate();
        assertEquals("14", engine.getDisplayText());
    }

    @Test
    public void handlesDecimalValues() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("1");
        engine.append(".");
        engine.append("5");
        engine.append("+");
        engine.append("2");
        engine.append(".");
        engine.append("5");
        engine.evaluate();
        assertEquals("4", engine.getDisplayText());
    }

    @Test
    public void clearsExpression() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.append("8");
        engine.append("x");
        engine.append("3");
        engine.clear();
        assertEquals("", engine.getDisplayText());
    }
}
