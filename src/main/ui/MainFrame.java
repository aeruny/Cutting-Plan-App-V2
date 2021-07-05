package main.ui;
import main.functions.CuttingPlan;
import main.ui.panels.InputPanel;
import main.ui.panels.OutputPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final InputPanel inputPanel;
    private OutputPanel outputPanel;

    public MainFrame() {
        setTitle("Cutting Plan V2.0 Made by Mingeon Sung");
        setPreferredSize(new Dimension(750,  500));

        inputPanel = new InputPanel(this);

        add(inputPanel);
        setContentPane(inputPanel);


        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void changeToInputPanel() {
        remove(outputPanel);
        setContentPane(inputPanel);
        getContentPane().revalidate();
        getContentPane().repaint();
        pack();
    }

    public void changeToOutputPanel(CuttingPlan[] cuttingPlans) {
        outputPanel = new OutputPanel(this, cuttingPlans);
        add(outputPanel);
        setContentPane(outputPanel);
        getContentPane().revalidate();
        getContentPane().repaint();
        pack();
    }
}
