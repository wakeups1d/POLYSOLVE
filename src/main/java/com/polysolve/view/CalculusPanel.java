package com.polysolve.view;

import com.polysolve.controller.PolySolveController;
import com.polysolve.model.PolynomialParser;

import javax.swing.*;
import java.awt.*;

/*
 
 * CalculusPanel
 * VIEW component for evaluate / differentiate / integrate / definite
 * integral operations on a single polynomial. 
 
*/
public class CalculusPanel extends JPanel {

    private final PolySolveController controller;
    private final JTextField fieldA = new JTextField("3x^2+2x+1");
    private final JTextField fieldX = new JTextField("5");
    private final JTextField fieldC = new JTextField("0");
    private final JTextField fieldLower = new JTextField("0");
    private final JTextField fieldUpper = new JTextField("2");
    private final JLabel resultValue = new JLabel("Result appears here");

    public CalculusPanel(PolySolveController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 16));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setBackground(Theme.BACKGROUND);

        add(buildInputsPanel(), BorderLayout.NORTH);
        add(buildResultPanel(), BorderLayout.CENTER);
    }

    private JPanel buildInputsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.BACKGROUND);

        panel.add(sectionLabel("Calculus: evaluate, differentiate, integrate"));
        panel.add(Box.createVerticalStrut(12));
        panel.add(fieldRow("Polynomial A", fieldA, 110));
        panel.add(Box.createVerticalStrut(14));

        panel.add(operationRow("Evaluate at x =", fieldX, 60, "Evaluate", e -> evaluate()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(operationRow(null, null, 0, "Differentiate d/dx", e -> differentiate()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(operationRow("Constant C =", fieldC, 60, "Integrate", e -> integrate()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(definiteIntegralRow());

        return panel;
    }

    private JPanel definiteIntegralRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setBackground(Theme.BACKGROUND);

        JLabel label = new JLabel("Bounds a =");
        label.setFont(Theme.FONT_LABEL);
        fieldLower.setFont(Theme.FONT_INPUT);
        fieldLower.setPreferredSize(new Dimension(55, 30));
        JLabel labelB = new JLabel("b =");
        labelB.setFont(Theme.FONT_LABEL);
        fieldUpper.setFont(Theme.FONT_INPUT);
        fieldUpper.setPreferredSize(new Dimension(55, 30));

        JButton button = makeButton("Definite Integral");
        button.addActionListener(e -> definiteIntegral());

        row.add(label);
        row.add(fieldLower);
        row.add(labelB);
        row.add(fieldUpper);
        row.add(button);
        return row;
    }

    private JPanel buildResultPanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Theme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel label = new JLabel("RESULT");
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.TEXT_MUTED);

        resultValue.setFont(Theme.FONT_RESULT);
        resultValue.setForeground(Theme.NAVY);

        card.add(label, BorderLayout.NORTH);
        card.add(resultValue, BorderLayout.CENTER);
        return card;
    }

    private void evaluate() {
        try {
            double x = Double.parseDouble(fieldX.getText().trim());
            double result = controller.evaluate(fieldA.getText(), fieldX.getText());
            showResult("A(" + trimZero(x) + ") = " + trimZero(result), false);
        } catch (NumberFormatException nfe) {
            showResult("\"" + fieldX.getText() + "\" is not a valid number for x", true);
        } catch (PolynomialParser.PolySolveParseException ex) {
            showResult(ex.getMessage(), true);
        }
    }

    private void differentiate() {
        try {
            showResult("d/dx [A(x)] = " + controller.differentiate(fieldA.getText()), false);
        } catch (PolynomialParser.PolySolveParseException ex) {
            showResult(ex.getMessage(), true);
        }
    }

    private void integrate() {
        try {
            showResult("Integral A dx = " + controller.integrate(fieldA.getText(), fieldC.getText()) + " + C", false);
        } catch (PolynomialParser.PolySolveParseException ex) {
            showResult(ex.getMessage(), true);
        }
    }

    private void definiteIntegral() {
        try {
            double a = Double.parseDouble(fieldLower.getText().trim());
            double b = Double.parseDouble(fieldUpper.getText().trim());
            double result = controller.definiteIntegral(fieldA.getText(), fieldLower.getText(), fieldUpper.getText());
            showResult("Integral from " + trimZero(a) + " to " + trimZero(b) + " = " + trimZero(result), false);
        } catch (NumberFormatException nfe) {
            showResult("Bounds a and b must be valid numbers", true);
        } catch (PolynomialParser.PolySolveParseException ex) {
            showResult(ex.getMessage(), true);
        }
    }

    private String trimZero(double num) {
        double rounded = Math.round(num * 1000.0) / 1000.0;
        if (rounded == Math.floor(rounded)) {
            return String.valueOf((long) rounded);
        }
        return String.valueOf(rounded);
    }

    private void showResult(String text, boolean isError) {
        resultValue.setText(text);
        resultValue.setForeground(isError ? Theme.ERROR_RED : Theme.NAVY);
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_SECTION);
        label.setForeground(Theme.NAVY);
        return label;
    }

    private JPanel fieldRow(String labelText, JTextField field, int labelWidth) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Theme.BACKGROUND);
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.TEXT_DARK);
        label.setPreferredSize(new Dimension(labelWidth, 24));
        field.setFont(Theme.FONT_INPUT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JPanel operationRow(String labelText, JTextField field, int fieldWidth,
                                 String buttonText, java.awt.event.ActionListener action) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setBackground(Theme.BACKGROUND);
        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setFont(Theme.FONT_LABEL);
            row.add(label);
        }
        if (field != null) {
            field.setFont(Theme.FONT_INPUT);
            field.setPreferredSize(new Dimension(fieldWidth, 30));
            row.add(field);
        }
        JButton button = makeButton(buttonText);
        button.addActionListener(action);
        row.add(button);
        return row;
    }

    private JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.FONT_BUTTON);
        button.setForeground(Color.black);
        button.setBackground(Theme.TEAL_DARK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return button;
    }
}
