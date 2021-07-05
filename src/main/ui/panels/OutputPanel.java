package main.ui.panels;

import main.functions.CuttingPlan;
import main.functions.Printer;
import main.ui.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Mingeon Sung
 * date:    12/20/2020
 */

public class OutputPanel extends JPanel {
    private final MainFrame frame;
    private static final String BUTTON_PROPERTY = "BUTTON_PROPERTY";
    private final CuttingPlan[] cuttingPlans;
    private final CuttingPlanPanel[] cuttingPlanPanelArray;
    private final JPanel cuttingPlansPanel;
    private final JLabel pageLabel;

    private int index;

    private CardLayout cardLayout;

    public OutputPanel(MainFrame frame, CuttingPlan[] cuttingPlans) {
        this.frame = frame;
        this.cuttingPlans = cuttingPlans;
        this.cuttingPlanPanelArray = buildCuttingPlanPanels(cuttingPlans);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        index = 0;

        JLabel titleLabel = buildLabel("Cutting Plan",24);
        pageLabel = buildLabel("1/" + cuttingPlans.length, 16);


        //cuttingPlansPanel = new JPanel(cardLayout);
        this.cuttingPlansPanel = buildCardLayoutPanel();

        JPanel panelControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBorder(new EmptyBorder(5, 150, 5, 150));


        JButton previousButton = buildButton("<<");
        JButton nextButton = buildButton(">>");
        JButton backButton = buildButton("Back");
        JButton printButton = buildButton("Excel");

        panelControlPanel.add(previousButton);
        panelControlPanel.add(pageLabel);
        panelControlPanel.add(nextButton);
        buttonsPanel.add(backButton);
        buttonsPanel.add(printButton);


        add(titleLabel);
        add(cuttingPlansPanel);
        add(panelControlPanel);
        add(buttonsPanel);
    }
    //
    private JPanel buildCardLayoutPanel() {
        System.out.println("OutputPanel: Building Card Layout Panel");
        JPanel panel = new JPanel(cardLayout);
        for (CuttingPlanPanel cuttingPlanPanel : cuttingPlanPanelArray) {
            System.out.println("Adding Cutting Plan: " + cuttingPlanPanel.getStringIndex());
            panel.add(cuttingPlanPanel, cuttingPlanPanel.getStringIndex());
        }
        return panel;
    }

    private CuttingPlanPanel[] buildCuttingPlanPanels(CuttingPlan[] cuttingPlans) {
        System.out.println("OutputPanel: Building Cutting Plan Panels");
        cardLayout = new CardLayout();
        CuttingPlanPanel[] panels = new CuttingPlanPanel[cuttingPlans.length];
        for(int i = 0; i < cuttingPlans.length; i++) {
            System.out.println("Initializing Cutting Plan: " + i);
            panels[i] = new CuttingPlanPanel(cuttingPlans[i], i);
        }
        return panels;
    }

    //JLabel
    private JLabel buildLabel(String content, int fontSize) {
        JLabel label = new JLabel(content);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        return label;
    }


    // JButton
    private JButton buildButton(String name) {
        JButton button = new JButton(name);
        button.putClientProperty(BUTTON_PROPERTY, name);
        button.addActionListener(buttonListener);
        return button;
    }
    // Action Listener for the buttons
    private final ActionListener buttonListener = e -> {
        JComponent source = (JComponent)e.getSource();
        String name = (String) source.getClientProperty(BUTTON_PROPERTY);
        if(name == null){
            throw new IllegalStateException("No BUTTON_PROPERTY on component");
        }
        buttonPressed(name);
    };
    // This is a list of actions for each button when pressed
    private void buttonPressed(String name) {
        switch(name) {
            case "<<":
                // Move to the previous Cutting Plan Panel
                if(index > 0)
                    cardLayout.show(cuttingPlansPanel, Integer.toString(--index));
                updatePage();
                break;
            case ">>":
                // Move to the next Cutting Plan Panel
                if(index < cuttingPlanPanelArray.length - 1)
                    cardLayout.show(cuttingPlansPanel, Integer.toString(++index));
                updatePage();
                break;
            case "Back":
                //Move backward to the input panel
                frame.changeToInputPanel();
                break;
            case "Excel":
                // Transition to the output panel
                try {
                    Printer.printOutput(cuttingPlans);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
        }
    }

    private void updatePage() {
        pageLabel.setText((index + 1) + "/" + cuttingPlanPanelArray.length);
    }

}
