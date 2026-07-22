package com.polysolve.view;

import com.polysolve.controller.PolySolveController;

import javax.swing.*;
import java.awt.*;

/*

 * MainFrame
 * Top-level Swing window for POLYSOLVE. Assembles the header banner,
 * the tabbed Algebra / Calculus panels, and the footer.
 
*/
public class MainFrame extends JFrame {

    public MainFrame() {
        super("POLYSOLVE - Polynomial Calculator & Calculus Suite");

        PolySolveController controller = new PolySolveController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 620);
        setMinimumSize(new Dimension(560, 560));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTabs(controller), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Theme.NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 24, 18, 24));

        JLabel title = new JLabel("POLYSOLVE");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Polynomial Calculator & Calculus Suite");
        subtitle.setFont(Theme.FONT_SUBTITLE);
        subtitle.setForeground(Theme.TEAL);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        return header;
    }

    private JTabbedPane buildTabs(PolySolveController controller) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Theme.FONT_SECTION);
        tabs.addTab("Algebra", new AlgebraPanel(controller));
        tabs.addTab("Calculus", new CalculusPanel(controller));
        return tabs;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(Theme.BACKGROUND);
        footer.setBorder(BorderFactory.createEmptyBorder(6, 0, 10, 0));

        JLabel credit = new JLabel("POLYSOLVE");
        credit.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        credit.setForeground(Theme.TEXT_MUTED);

        footer.add(credit);
        return footer;
    }
}
