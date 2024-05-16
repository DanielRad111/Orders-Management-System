package presentation.order;

import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * View class for managing orders in the presentation layer.
 */
public class OrderView extends JFrame {
    private JTextField orderId = new JTextField(5);
    private JComboBox<String> clientBox;
    private JComboBox<String> productBox;
    private JTextField quantityField = new JTextField(20);
    private JButton createOrderButton = new JButton("Create Order");
    private JButton editOrderButton = new JButton("Edit Order");
    private JButton deleteOrderButton = new JButton("Delete Order");
    private JTable orderTable;
    private DefaultTableModel tableModel;

    /**
     * Constructs a new OrderView object.
     *
     * @param clientNames A list of client names for populating the client dropdown box.
     * @param productNames A list of product names for populating the product dropdown box.
     */
    public OrderView(List<String> clientNames, List<String> productNames) {
        setTitle("Order creation");
        clientBox = new JComboBox<>(clientNames.toArray(new String[0]));
        productBox = new JComboBox<>(productNames.toArray(new String[0]));

        JScrollPane tableScrollPane = new JScrollPane();
        orderTable = new JTable();
        tableScrollPane.setViewportView(orderTable);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(700, 500);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Order ID:"), BorderLayout.WEST);
        add(orderId, BorderLayout.CENTER);

        add(new JLabel("Client Name:"), BorderLayout.WEST);
        add(clientBox, BorderLayout.CENTER);

        add(new JLabel("Product Name:"), BorderLayout.EAST);
        add(productBox, BorderLayout.SOUTH);

        add(new JLabel("Quantity:"), BorderLayout.NORTH);
        add(quantityField, BorderLayout.CENTER);

        add(createOrderButton);
        add(editOrderButton);
        add(deleteOrderButton);

        add(tableScrollPane);

        setVisible(true);
    }

    public JTextField getOrderId() {
        return orderId;
    }
    public JComboBox<String> getClientBox() {
        return clientBox;
    }

    public JComboBox<String> getProductBox() {
        return productBox;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

    public JButton getEditOrderButton() {
        return editOrderButton;
    }
    public JButton getDeleteOrderButton() {
        return deleteOrderButton;
    }

    public JTable getOrderTable() {
        return orderTable;
    }

    /**
     * Generates a table from a list of objects and displays it in the order table.
     *
     * @param objects The list of objects to be displayed in the table.
     */
    public void generateTableFromObjects(List<?> objects) {
        if (objects != null && !objects.isEmpty()) {
            Class<?> objClass = objects.get(0).getClass();
            Field[] fields = objClass.getDeclaredFields();
            String[] columnNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                columnNames[i] = fields[i].getName();
            }
            tableModel = new DefaultTableModel(columnNames, 0);
            orderTable.setModel(tableModel);

            for (Object obj : objects) {
                Object[] rowData = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    try {
                        rowData[i] = fields[i].get(obj);
                    } catch (IllegalAccessException e) {
                        Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                tableModel.addRow(rowData);
            }
        }
    }
}
