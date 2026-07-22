
package com.polysolve.view;
import com.polysolve.controller.PolySolveController;
import com.polysolve.model.Polynomial;
import com.polysolve.model.PolynomialParser;
import javax.swing.*;
import java.awt.*;

/*

 * AlgebraPanel
 * VIEW component for add / subtract / multiply operations on two
 * polynomials.
 
*/
public class AlgebraPanel extends JPanel {

    private final PolySolveController controller;
    private final JTextField fieldA = new JTextField("3x^2+2x+1");
    private final JTextField fieldB = new JTextField("x-1");
    private final JLabel resultValue = new JLabel("Result appears here");

    public AlgebraPanel(PolySolveController controller) {
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

        panel.add(sectionLabel("Algebra: add, subtract, multiply"));
        panel.add(Box.createVerticalStrut(12));

        panel.add(fieldRow("Polynomial A", fieldA));
        panel.add(Box.createVerticalStrut(8));
        panel.add(fieldRow("Polynomial B", fieldB));
        panel.add(Box.createVerticalStrut(14));

        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonRow.setBackground(Theme.BACKGROUND);
        buttonRow.add(makeButton("A + B", e -> compute(Op.ADD)));
        buttonRow.add(makeButton("A - B", e -> compute(Op.SUBTRACT)));
        buttonRow.add(makeButton("A x B", e -> compute(Op.MULTIPLY)));
        panel.add(buttonRow);

        return panel;
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

    private enum Op { ADD, SUBTRACT, MULTIPLY }

    private void compute(Op op) {
        try {
            Polynomial result;
            String symbol;
            switch (op) {
                case ADD:
                    result = controller.add(fieldA.getText(), fieldB.getText());
                    symbol = "A + B = ";
                    break;
                case SUBTRACT:
                    result = controller.subtract(fieldA.getText(), fieldB.getText());
                    symbol = "A - B = ";
                    break;
                default:
                    result = controller.multiply(fieldA.getText(), fieldB.getText());
                    symbol = "A x B = ";
            }
            showResult(symbol + result, false);
        } catch (PolynomialParser.PolySolveParseException ex) {
            showResult(ex.getMessage(), true);
        }
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

    private JPanel fieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Theme.BACKGROUND);
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.TEXT_DARK);
        label.setPreferredSize(new Dimension(110, 24));
        field.setFont(Theme.FONT_INPUT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JButton makeButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(Theme.FONT_BUTTON);
        button.setForeground(Color.black);
        button.setBackground(Theme.TEAL);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(action);
        return button;
    }
}
