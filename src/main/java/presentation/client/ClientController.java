package presentation.client;

import bll.ClientBLL;
import model.Client;

import java.util.List;

public class ClientController {
    private ClientView clientView;
    private ClientBLL clientBLL;

    public ClientController(ClientView clientView, ClientBLL clientBLL) {
        this.clientView = new ClientView();
        this.clientBLL = new ClientBLL();

        clientView.getAddClientButton().addActionListener(e -> addClient());
        clientView.getEditClientButton().addActionListener(e -> editClient());
        clientView.getDeleteClientButton().addActionListener(e -> deleteClient());

        refreshTable();
    }

    private void addClient() {
        String name = clientView.getClientName().getText();
        String email = clientView.getClientEmail().getText();
        Client client = new Client(name, email);
        clientBLL.insertClient(client);

        clientView.getClientName().setText("");
        clientView.getClientEmail().setText("");

        refreshTable();
    }

    private void editClient() {
        int id = clientView.getClientId();
        String name  = clientView.getClientName().getText().trim();
        String email = clientView.getClientEmail().getText().trim();

        if(name.isEmpty() || email.isEmpty()) {
            System.out.println("Name and email are empty!");
            return;
        }
        Client client = new Client(id, name, email);
        clientBLL.updateClient(client);

        clientView.getClientName().setText("");
        clientView.getClientEmail().setText("");

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
