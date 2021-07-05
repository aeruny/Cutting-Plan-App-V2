package main.ui.panels;

import main.functions.CuttingPlan;
import main.ui.MainFrame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The class contains components of the input screen.
 * @author  Mingeon Sung
 * date:    12/20/2020
 *
 */

public class InputPanel extends JPanel {

    private final MainFrame frame;
    private final CardLayout cardLayout;
    private final JPanel materialsPanel;
    private final List<MaterialPanel> materials;
    private final JLabel pageLabel;
    private int index;

    private final String BUTTON_PROPERTY = "BUTTON_PROPERTY";

    public InputPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JLabel titleLabel = buildLabel("Cutting Plan", 24);
        pageLabel = buildLabel("1/1", 16);

        JButton backButton = buildButton("<<");
        JButton nextButton = buildButton(">>");
        JButton addMaterialButton = buildButton("Add Material");
        JButton deleteMaterialButton = buildButton("Delete Material");
        JButton generateButton = buildButton("Generate");
        generateButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        materials = new ArrayList<>();
        materials.add(new MaterialPanel(0));

        cardLayout = new CardLayout();
        materialsPanel = new JPanel(cardLayout);
        materialsPanel.add(materials.get(0), "0");



        // JPanel
        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 30, 10);
        JPanel controlPanel = new JPanel(flow);

        controlPanel.add(deleteMaterialButton);
        controlPanel.add(backButton);
        controlPanel.add(pageLabel);
        controlPanel.add(nextButton);
        controlPanel.add(addMaterialButton);

        add(titleLabel);
        add(materialsPanel);
        add(controlPanel);
        add(generateButton);
    }


    // JLabel
    private JLabel buildLabel(String content, int textSize) {
        JLabel label = new JLabel(content);
        label.setFont(new Font("Dialog", Font.BOLD, textSize));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
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
    // This is the list of actions for each button when pressed
    private void buttonPressed(String name) {
        switch(name) {
            case "Add Material":
                //Add a new input panel
                System.out.println("Adding Material at Index: " + materials.size() + " Showing Index: " + materials.size());
                materials.add(new MaterialPanel(materials.size()));
                materialsPanel.add(materials.get(materials.size() - 1), materials.get(materials.size() - 1).getStringIndex());
                //Move to the new panel
                index = materials.size() - 1;
                cardLayout.show(materialsPanel, materials.get(index).getStringIndex());
                updatePage();
                updateOrder();
                break;
            case "Delete Material":
                if(materials.size() > 1) {
                    //System.out.println("Deleting Material at Index: " + index + " Showing Index: " + (index - 1));

                    //Delete the current panel
                    //materialsPanel.remove(index);
                    //materialsPanel.remove(cardLayout.getC)
                    materials.remove(materials.size() - 1);
                    index = index >= materials.size() - 1 ? materials.size() - 2 : index;
                    updatePage();
                    updateOrder();
                    //Move to the previous panel if the last panel is selected
                    cardLayout.show(materialsPanel, Integer.toString(index));
                }
            case ">>":
                //Move toward the more recently created input panel
                if(index < materials.size() - 1)
                    cardLayout.show(materialsPanel, Integer.toString(++index));
                updatePage();
                System.out.println("Currently Looking at " + index + "  Material: " + materials.get(index).getStringIndex());
                break;
            case "<<":
                //Move backward to the input panel
                if(index > 0)
                    cardLayout.show(materialsPanel, Integer.toString(--index));
                updatePage();
                System.out.println("Currently Looking at " + index + "  Material: " + materials.get(index).getStringIndex());
                break;

            case "Generate":
                // Transition to the output panel
                System.out.println("Generate");
                if(pushInputs() != null)
                    frame.changeToOutputPanel(pushInputs());

                //System.out.println(materials.toString());
                System.out.println("Generation complete");
                break;
        }
    }

    private void updatePage() {
        pageLabel.setText((index + 1) + "/" + materials.size());
    }

    private void updateOrder() {
        //System.out.println("------------------------------");
        for(int i = 0; i < materials.size(); i++) {
            //System.out.print(materials.get(i).getStringIndex() + "      ");
            materials.get(i).setIndex(i);
            //System.out.println(materials.get(i).getStringIndex());
        }
    }

    private CuttingPlan[] pushInputs() {
        CuttingPlan[] cuttingPlans = new CuttingPlan[materials.size()];
        for(int i = 0; i < cuttingPlans.length; i++) {
            System.out.println("Checking Material " + materials.get(i).getStringIndex());
            if(materials.get(i).hasInputs() && materials.get(i).hasValidInputs()) {
                System.out.println("Inputs Validated");
                MaterialPanel mat = materials.get(i);
                cuttingPlans[i] = new CuttingPlan(mat.getName(), mat.getTarget(), mat.getTolerance(), mat.getInputs());
            }
            else {
                System.err.println("Invalid Input Found");
                return null;
            }
        }
        return cuttingPlans;
    }


}
