package presentation.client;

import bll.ClientBLL;
import model.Client;

import java.util.List;

public class ClientController {
    private ClientView clientView;
    private ClientBLL clientBLL;

    public ClientController(ClientView clientView, ClientBLL clientBLL){
        this.clientView = new ClientView();
        this.clientBLL = new ClientBLL();

        clientView.getAddClientButton().addActionListener(e -> {
            try {
                addClient();
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        clientView.getEditClientButton().addActionListener(e -> {
            try {
                editClient();
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        clientView.getDeleteClientButton().addActionListener(e -> deleteClient());

        refreshTable();
    }

    private void addClient() throws IllegalAccessException {
        String name = clientView.getClientNameField().getText();
        String email = clientView.getClientEmailField().getText();
        Client client = new Client(name, email);
        clientBLL.insertClient(client);

        clientView.getClientNameField().setText("");
        clientView.getClientEmailField().setText("");

        refreshTable();
    }

    private void editClient() throws IllegalAccessException {
        int id = clientView.getClientId();
        String name  = clientView.getClientNameField().getText().trim();
        String email = clientView.getClientEmailField().getText().trim();

        if(name.isEmpty() || email.isEmpty()) {
            System.out.println("Name and email are empty!");
            return;
        }
        Client client = new Client(id, name, email);
        clientBLL.updateClient(client);

        clientView.getClientNameField().setText("");
        clientView.getClientEmailField().setText("");

        refreshTable();
    }

    private void deleteClient(){
        int row = clientView.getClientTable().getSelectedRow();
        if(row >= 0){
            int id = Integer.parseInt(clientView.getClientTable().getValueAt(row, 0).toString());
            clientBLL.deleteClient(id);

            refreshTable();
        }else{
            System.out.println("No row selected for deletion!");
        }
    }

    private void refreshTable() {
        List<Client> clients = clientBLL.findAllClients();
        clientView.refreshTable(clients);
    }
}
