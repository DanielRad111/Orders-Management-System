package presentation.client;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

import model.Client;
import presentation.order.OrderView;
/**
 * The view for managing clients, including adding, editing, and deleting clients.
 */
public class ClientView extends JFrame {
    /** Text field for entering client ID. */
    private JTextField clientIdField = new JTextField(10);
    /** Text field for entering client name. */
    private JTextField clientNameField = new JTextField(20);
    /** Text field for entering client email. */
    private JTextField clientEmailField = new JTextField(20);
    /** Button for adding a new client. */
    private JButton addClientButton = new JButton("Add Client");
    /** Button for editing an existing client. */
    private JButton editClientButton = new JButton("Edit Client");
    /** Button for deleting an existing client. */
    private JButton deleteClientButton = new JButton("Delete Client");

    /** Table for displaying client information. */
    private JTable clientTable;
    /** Table model for the client table. */
    private DefaultTableModel tableModel;
    /**
     * Constructs a new ClientView object.
     */
    public ClientView() {

        JScrollPane jScrollPane = new JScrollPane();
        clientTable = new JTable();
        jScrollPane.setViewportView(clientTable);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(800, 400);
        setLayout(new FlowLayout());

        add(new JLabel("Client ID: "));
        add(clientIdField);

        add(new JLabel("Client Name: "));
        add(clientNameField);

        add(new JLabel("Client Email: "));
        add(clientEmailField);

        add(addClientButton);
        add(editClientButton);
        add(deleteClientButton);

        add(jScrollPane);

        setVisible(false);
        clientTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow != -1) {
                clientIdField.setText(clientTable.getValueAt(selectedRow, 0).toString());
                clientNameField.setText((String) clientTable.getValueAt(selectedRow, 1));
                clientEmailField.setText((String) clientTable.getValueAt(selectedRow, 2));
            }
        });
    }
    public JTextField getClientIdField(){
        return clientIdField;
    }

    public JTextField getClientNameField() {
        return clientNameField;
    }
    public JTextField getClientEmailField() {
        return clientEmailField;
    }

    public JButton getAddClientButton() {
        return addClientButton;
    }

    public JButton getEditClientButton() {
        return editClientButton;
    }

    public JButton getDeleteClientButton() {
        return deleteClientButton;
    }


    public JTable getClientTable() {
        return clientTable;
    }

    /**
     * Gets the ID of the selected client from the client table.
     *
     * @return The ID of the selected client.
     * @throws RuntimeException if no client is selected.
     */
    public int getClientId() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt(clientTable.getValueAt(selectedRow, 0).toString());
        } else {
            throw new RuntimeException("No client is selected.");
        }
    }

    /**
     * Generates a table from a list of objects.
     *
     * @param objects The list of objects to populate the table with.
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
            clientTable.setModel(tableModel);

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

