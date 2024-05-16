package presentation.bill;

import model.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * A view class for displaying a table of bills in the presentation layer.
 */
public class BillTableView extends JFrame{
    /** The table component to display bills. */
    private JTable billTable;
    private DefaultTableModel tableModel;

    /**
     * Constructs a new BillTableView object.
     */
    public BillTableView() {
        setTitle("Bills");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Client Name", "Product Name", "Quantity", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(billTable);

        setVisible(true);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays a list of bills in the table.
     *
     * @param bills The list of bills to display.
     */
    public void displayBills(List<Bill> bills) {
        tableModel.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Bill bill : bills) {
            Object[] row = {bill.id(), bill.clientName(), bill.productName(), bill.quantity(), bill.date().format(formatter)};
            tableModel.addRow(row);
        }
    }
}
