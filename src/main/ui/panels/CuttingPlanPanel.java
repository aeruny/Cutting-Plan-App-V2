package main.ui.panels;

import main.functions.CuttingPlan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author  Mingeon Sung
 * date:    1/7/2021
 */

public class CuttingPlanPanel extends JPanel{

    private final int index;


    public CuttingPlanPanel(CuttingPlan cuttingPlan, int index) {
        this.index = index;
        Object[][] tableContents = cuttingPlan.getTableContents();
        String[] columnNames = cuttingPlan.getColumnNames();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED)));


        JLabel nameLabel = buildLabel(cuttingPlan.getMaterialName(), 16);
        JLabel targetLabel = buildLabel("Target Length: " + cuttingPlan.getTarget(), 12);
        JLabel toleranceLabel = buildLabel("Tolerance: " + cuttingPlan.getTolerance(), 12);
        JLabel totalLabel = buildLabel("Total Amount: " + cuttingPlan.getTotal(), 14);
        JLabel excessLabel = buildLabel("Excess Length: " + cuttingPlan.getTotalExcessLength(), 14);

        // Set JTable with the data from parameter
        JTable table = buildTable(tableContents, columnNames);



        // JPanel
        JPanel inputsPanel = new JPanel(new FlowLayout());
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));

                // Set JScrollPane for a scrollable table
        JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        if(columnNames.length >= 10)
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int sideMargin = 30;
        int offset = 8;
        if(columnNames.length < offset) {
            sideMargin += (sideMargin - offset) * (offset - columnNames.length);
        }
        tablePanel.setBorder(new EmptyBorder(5,sideMargin,5,sideMargin));
        GridLayout gridLayout = new GridLayout(1,2);
        gridLayout.setHgap(60);

        inputsPanel.add(targetLabel);
        inputsPanel.add(toleranceLabel);

        resultPanel.add(nameLabel);
        resultPanel.add(inputsPanel);
        resultPanel.add(totalLabel);
        resultPanel.add(excessLabel);

        add(resultPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        System.out.println("Cutting Plan Panel is working");
    }

    //JLabel
    private JLabel buildLabel(String content, int fontSize) {
        JLabel label = new JLabel(content);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        return label;
    }

    // JTable
    private JTable buildTable(Object[][] tableContents, String[] columnNames) {
        JTable table = new JTable(tableContents, columnNames);

        // Set basic settings for table
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));
        table.setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);                      // # column width (smallest)
        table.getColumnModel().getColumn(columnNames.length - 2).setPreferredWidth(40); // # column width (smaller)
        table.getColumnModel().getColumn(columnNames.length - 1).setPreferredWidth(40); // remainder column width (smaller)

        // Center the texts inside table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return table;
    }

    public String getStringIndex() {
        return Integer.toString(index);
    }
}
