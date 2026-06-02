/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Storie
 */
public class ExportService {

    public static void exportToCSV(TableModel model, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.print(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) writer.print(",");
            }
            writer.println();

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    writer.print(value != null ? value.toString() : "");
                    if (j < model.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
            }
        }
    }
}

