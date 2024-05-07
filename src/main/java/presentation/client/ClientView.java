package presentation.client;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import model.Client;

public class ClientView extends JFrame {
    private JTextField clientIdField = new JTextField(10);
    private JTextField clientNameField = new JTextField(20);
    private JTextField clientEmailField = new JTextField(20);

    private JButton addClientButton = new JButton("Add Client");
    private JButton editClientButton = new JButton("Edit Client");
    private JButton deleteClientButton = new JButton("Delete Client");


    private JTable clientTable;
    private DefaultTableModel tableModel;

    public ClientView() {
        String[] columnNames = {"ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clientTable = new JTable(tableModel);

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

        JScrollPane jScrollPane = new JScrollPane(clientTable);
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

    public int getClientId() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt(clientTable.getValueAt(selectedRow, 0).toString());
        } else {
            throw new RuntimeException("No client is selected.");
        }
    }

    public void refreshTable(List<Client> clients) {
        tableModel.setRowCount(0);

        for (Client client : clients) {
            Object[] row = new Object[]{client.getId(), client.getName(), client.getEmail()};
            tableModel.addRow(row);
        }
    }
}

