package main.functions;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  Exports Cutting Plans data into a csv file
 *  @author Mingeon Sung
 *  date:   1/8/2021
 *
 */

public class Printer {

    //double target, Object[][] inputs, double total, double remainder, Object[][] cuttingPlan, String[] columnNames
    public static void printOutput(CuttingPlan[] cuttingPlans) throws IOException {
        File file = new File("Cutting Plan.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        // Introduction
        bw.append("Cutting Plan,,Created By Mingeon Sung\n");
        bw.append("\n\n");


        for(CuttingPlan cuttingPlan: cuttingPlans) {
            // User Inputs
            bw.append(cuttingPlan.getMaterialName()).append("\n");
            bw.append("Inputs:\n");
            bw.append("Target Length:,,").append(Double.toString(cuttingPlan.getTarget())).append("\n");
            bw.append("#,Length,Quantity\n");
            Object[][] inputs = cuttingPlan.getInputs();
            for (int i = 0; i < inputs.length; i++) {
                bw.append(String.valueOf(i + 1)).append(",").append(String.valueOf(inputs[i][0])).append(",").append(String.valueOf(inputs[i][1])).append("\n");
            }
            bw.append("\n\n");

            // Results
            bw.append("Results:,\n");
            bw.append("Total:,").append(String.valueOf(cuttingPlan.getTotal())).append("\n");
            bw.append("Total Excess Length:,").append(String.valueOf(cuttingPlan.getTotalExcessLength())).append("\n");
            bw.append("\n\n");


            // Cutting Plan
            bw.append("Cutting Plan:\n");
            String[] columnNames = cuttingPlan.getColumnNames();
            Object[][] plan = cuttingPlan.getTableContents();
            StringBuilder str = new StringBuilder();
            for (String columnName : columnNames) {
                str.append(columnName).append(",");
            }
            bw.append(str.toString()).append("\n");
            StringBuilder str2;
            for (Object[] row : plan) {
                str2 = new StringBuilder();
                for (Object cell : row) {
                    str2.append(cell).append(",");
                }
                bw.append(str2.toString()).append("\n");
            }
            bw.append("\n\n");
        }
        open(file);
        bw.flush();
        bw.close();
        fw.close();
    }

    public static void open(File file)
    {
        try
        {
            if (OSDetector.isWindows())
            {
                Runtime.getRuntime().exec(new String[]
                        {"rundll32", "url.dll,FileProtocolHandler",
                                file.getAbsolutePath()});
            } else if (OSDetector.isLinux() || OSDetector.isMac())
            {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                        file.getAbsolutePath()});
            } else
            {
                // Unknown OS, try with desktop
                if (Desktop.isDesktopSupported())
                {
                    Desktop.getDesktop().open(file);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    public Printer() {


        /*
        file = new File("Cutting Plan.txt");
        if(!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("===========================================================\n");
        bw.write("||                                                       ||\n");
        bw.write("||             Cutting Plan by Mingeon Sung              ||\n");
        bw.write("||                                                       ||\n");
        bw.write("===========================================================\n");

        // User Input
        bw.write("User Input:\n");
        double[][] inputs = new double[10][2];
        for(int i = 0; i < 10; i++) {
            bw.write("Material #" + i + " :\t");
            bw.write(inputs[i][0] + "\t" + inputs[i][1] + "\n");
        }
        bw.write("\n\n");

        // Result
        bw.write("Result:\n");
        bw.write("Total Number of Raw Material:\t");

        bw.write("Total Length of Excess:\t");
        bw.write("\n\n");

        // Cutting Plan
        bw.write("Cutting Plan:\n");
        Object[][] cuttingPlan = new Object[10][6];
        for(int i = 0; i < cuttingPlan.length; i++) {
            //lines2[i] = i + "\t";
            for(int j = 0; j < cuttingPlan[i].length; j++) {
                bw.write(cuttingPlan[i][j] + "\t");
            }
            bw.write("\n");
        }
        bw.write("\n\n");
        bw.close();
        fw.close();
         */
    }
}
