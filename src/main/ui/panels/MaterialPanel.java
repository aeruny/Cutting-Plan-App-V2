package main.ui.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The class is a single panel that contains components for one material's inputs.
 * @author  Mingeon Sung
 * date:    1/7/2021
 */

public class MaterialPanel extends JPanel {

    private int index;
    private final String BUTTON_PROPERTY = "BUTTON_PROPERTY";

    private final HintTextField matNameInput;
    private final JTextField rawLengthInput;
    private final JTextField toleranceInput;
    private final InputTableModel tableModel;
    private final JTable table;

    public MaterialPanel(int index) {
        this.index = index;
        setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED)));

        // Labels
        JLabel rawMatLabel = buildLabel("Target Length: ", 16);
        JLabel toleranceLabel = buildLabel("Tolerance Length: ", 14);

        // TextField
        matNameInput = new HintTextField("Material " + (index + 1), 8);
        rawLengthInput = buildTextField("",6,16);
        toleranceInput = buildTextField("0.0",4, 14);

        // Buttons
        JButton deleteRowButton = buildButton("Delete a Row");
        JButton addRowButton = buildButton("Add a Row");
        JButton resetButton = buildButton("Reset");

        // Input Table
        tableModel = new InputTableModel();

        table = buildTable(tableModel);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(new EmptyBorder(5,50,5,50));

        // JPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 0, 0);
        JPanel matNamePanel = new JPanel(new FlowLayout());
        JPanel rawLengthPanel = new JPanel(new FlowLayout());
        JPanel tolerancePanel = new JPanel(flow);
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        //matNamePanel.add(matNameLabel);
        matNamePanel.add(matNameInput);
        rawLengthPanel.add(rawMatLabel);
        rawLengthPanel.add(rawLengthInput);
        tolerancePanel.add(toleranceLabel);
        tolerancePanel.add(toleranceInput);

        topPanel.add(matNamePanel);
        topPanel.add(rawLengthPanel);
        topPanel.add(tolerancePanel);
        topPanel.add(controlPanel);

        controlPanel.add(addRowButton);
        controlPanel.add(deleteRowButton);
        buttonsPanel.add(resetButton);

        add(topPanel, BorderLayout.NORTH);
        add(tablePane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // JLabel
    private JLabel buildLabel(String content, int textSize) {
        JLabel label = new JLabel(content);
        label.setFont(new Font("Dialog", Font.BOLD, textSize));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        return label;
    }

    // JTextField
    private JTextField buildTextField(String text, int columnSize, int textSize) {
        JTextField textField = new JTextField(text);
        textField.setColumns(columnSize);
        textField.setFont(new Font("Dialog", Font.BOLD, textSize));
        textField.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        return textField;
    }

    // JTable
    private JTable buildTable(InputTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
        table.setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setRowHeight(20);
        TableColumn column;


        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0)
                column.setPreferredWidth(5); // # column is bigger
            else
                column.setPreferredWidth(200);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return table;
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
            case "Add a Row":
                //Add a row to the Table
                tableModel.addRow();
                break;
            case "Delete a Row":
                //Delete the last row
                tableModel.removeRow();
                break;
            case "Reset":
                // Reset the TextFields
                matNameInput.updateHint("Material " + (index + 1));
                rawLengthInput.setText("");
                toleranceInput.setText("0");
                // Reset the Table
                tableModel.resetRows();
                break;
        }
    }

    public String getName() {
        System.out.println("Pushing Name: " + matNameInput.getText());
        return matNameInput.getText();
    }
    public double getTarget() {
        System.out.println("Pushing Target: " + Double.parseDouble(rawLengthInput.getText()));
        return Double.parseDouble(rawLengthInput.getText());
    }

    public double getTolerance() {
        System.out.println("Pushing Tolerance " + Double.parseDouble(toleranceInput.getText()));
        return Double.parseDouble(toleranceInput.getText());
    }
    public Object[][] getInputs() {
        System.out.println("Pushing Inputs");
        if(table.getRowCount() == 0)
            return null;
        Object[][] objects = new Object[table.getRowCount()][2];
        for(int i = 0; i < table.getRowCount(); i++) {
            for(int j = 0; j < 2; j++) {
                System.out.println(tableModel.getValueAt(i, j + 1));
                objects[i][j] = tableModel.getValueAt(i, j + 1);
            }
        }
        return objects;
    }

    private boolean isTableFilled() {
        for(int i = 1; i < table.getRowCount(); i++) {
            for(int j = 0; j < 2; j++) {
                if(tableModel.getValueAt(i, j + 1) == "") {
                    System.err.println("Table: Empty Field Detected");
                    return false;
                }
                if(j == 1 && Double.parseDouble(tableModel.getValueAt(i, 1).toString()) > Double.parseDouble(rawLengthInput.getText())) {
                    System.err.println("Table: Length Input Greater than Target at (" + i + ", 1)");
                    System.err.println("Cell Value = " + tableModel.getValueAt(i, j + 1).toString() + "\tTarget Value = " + Double.parseDouble(rawLengthInput.getText()));
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTextFieldFilled() {
        if (matNameInput.getText().isEmpty()) {
            System.err.println("Text Field: No Input Detected");
            return false;
        }
        if (toleranceInput.getText().isEmpty()) {
            System.err.println("Text Field: No Input Detected");
            return false;
        }
        if (rawLengthInput.getText().isEmpty()) {
            System.err.println("Text Field: No Input Detected");
            return false;
        }
        return true;
    }

    public boolean hasInputs() {
        return isTableFilled() & isTextFieldFilled();
    }

    public boolean hasValidInputs() {
        double target = getTarget();
        if(target < getTolerance())
            return false;
        for(int i = 0; i < table.getRowCount(); i++) {
            for(int j = 0; j < 2; j++) {
                if(j == 1 && Double.parseDouble(table.getValueAt(i,j).toString()) > target)
                    return false;
                if(Double.parseDouble(table.getValueAt(i,j).toString()) <= 0)
                    return false;
            }
        }
        return true;
    }

    public String getStringIndex() {
        return Integer.toString(index);
    }

    public void setIndex(int index) {
        this.index = index;
        matNameInput.updateHint("Material " + (index + 1));
    }
}
