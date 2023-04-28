package project_demo;

import javax.swing.*;
import java.awt.*;

class Dashboard extends JFrame {
    private JPanel panel;
    private JButton computerOperatorBtn;
    private JButton sectionOfficerBtn;
    private JButton nayabSubbaBtn;
    private JButton kharidarBtn;

    public Dashboard() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        panel = new JPanel(new GridLayout(2, 2, 10, 10));

        computerOperatorBtn = new JButton("Computer Operator");
        sectionOfficerBtn = new JButton("Section Officer");
        nayabSubbaBtn = new JButton("Nayab Subba");
        kharidarBtn = new JButton("Kharidar");

        panel.add(computerOperatorBtn);
        panel.add(sectionOfficerBtn);
        panel.add(nayabSubbaBtn);
        panel.add(kharidarBtn);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
    }
}
