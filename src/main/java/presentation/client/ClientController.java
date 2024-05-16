package presentation.client;

import bll.ClientBLL;
import model.Client;

import java.util.List;
/**
 * Controller class for managing clients in the presentation layer.
 */
public class ClientController {
    /** The view for managing clients. */
    private ClientView clientView;
    /** The bll for managing clients. */
    private ClientBLL clientBLL;

    /**
     * Constructs a new ClientController object.
     *
     * @param clientView The view for managing clients.
     * @param clientBLL The bll for managing clients.
     */
    public ClientController(ClientView clientView, ClientBLL clientBLL) {
        this.clientView = clientView;
        this.clientBLL = clientBLL;

        clientView.getAddClientButton().addActionListener(e -> {
            try {
                addClient();
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        clientView.getEditClientButton().addActionListener(e -> editClient());
        clientView.getDeleteClientButton().addActionListener(e -> deleteClient());

        refreshTable();
    }

    /**
     * Adds a new client to the database.
     *
     * @throws IllegalAccessException if the client fails validation.
     */
    private void addClient() throws IllegalAccessException {
        int id = Integer.parseInt(clientView.getClientIdField().getText());
        String name = clientView.getClientNameField().getText();
        String email = clientView.getClientEmailField().getText();
        Client client = new Client(id, name, email);
        clientBLL.insertClient(client);

        clientView.getClientIdField().setText("");
        clientView.getClientNameField().setText("");
        clientView.getClientEmailField().setText("");

        refreshTable();
    }

    /**
     * Edits an existing client in the system.
     */
    private void editClient(){
        int id = clientView.getClientId();
        String name = clientView.getClientNameField().getText().trim();
        String email = clientView.getClientEmailField().getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("Name and email fields must not be empty.");
            return;
        }

        Client client = new Client(id, name, email);
        clientBLL.updateClient(client);

        clientView.getClientIdField().setText("");
        clientView.getClientNameField().setText("");
        clientView.getClientEmailField().setText("");

        refreshTable();
    }
    /**
     * Deletes an existing client from the system.
     */
    public void deleteClient() {
        int row = clientView.getClientTable().getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(clientView.getClientTable().getValueAt(row, 0).toString());
            clientBLL.deleteClient(id);

            refreshTable();
        } else {
            System.out.println("No row selected for deletion");
        }
    }
    /**
     * Refreshes the client table with updated data.
     */
    private void refreshTable() {
        List<Client> clients = clientBLL.findAllClients();
        clientView.generateTableFromObjects(clients);
    }
}
