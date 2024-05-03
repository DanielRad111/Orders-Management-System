package presentation.client;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientView extends JFrame{
    private JTextField ClientNameField = new JTextField(20);
    private JTextField ClientEmailField = new JTextField(20);

    private JButton addClientButton = new JButton("Add Client");
    private JButton editClientButton = new JButton("Edit Client");
    private JButton deleteClientButton = new JButton("Delete Client");
    private JButton viewClientsButton = new JButton("View Clients");

    private JLabel seeClientsLabel = new JLabel("See Clients");

    private JTable clientTable = new JTable();
    private DefaultTableModel tableModel;

    public ClientView() {
        String[] columnNames = {"ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clientTable = new JTable(tableModel);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Clients");
        setSize(700, 500);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Client Name"));
        add(ClientNameField);

        add(new JLabel("Client Email"));
        add(ClientEmailField);

        add(addClientButton);
        add(editClientButton);
        add(deleteClientButton);

        add(seeClientsLabel);
        add(viewClientsButton);

        JScrollPane scrollPane = new JScrollPane(clientTable);
        add(scrollPane);

        setVisible(false);
        clientTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = clientTable.getSelectedRow();
            if(selectedRow != -1){
                ClientNameField.setText(clientTable.getValueAt(selectedRow, 1).toString());
                ClientEmailField.setText(clientTable.getValueAt(selectedRow, 2).toString());
            }
        });
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public JButton getViewClientsButton() {
        return viewClientsButton;
    }

    public JButton getDeleteClientButton() {
        return deleteClientButton;
    }

    public JButton getEditClientButton() {
        return editClientButton;
    }

    public JButton getAddClientButton() {
        return addClientButton;
    }

    public JTextField getClientEmailField() {
        return ClientEmailField;
    }

    public JTextField getClientNameField() {
        return ClientNameField;
    }

    public int getClientId(){
        int selectedRow = clientTable.getSelectedRow();
        if(selectedRow != -1){
            return Integer.parseInt(clientTable.getValueAt(selectedRow, 0).toString());
        }else{
            throw new RuntimeException("No client selected");
        }
    }

    public void refreshTable(List<Client> clients){
        tableModel.setRowCount(0);

        for(Client client : clients){
            Object[] row = new Object[]{client.getId(), client.getName(), client.getEmail()};
            tableModel.addRow(row);
        }
    }
}
