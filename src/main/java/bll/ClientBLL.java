package bll;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;
/**
 * Business logic layer for handling Client entities.
 */
public class ClientBLL {
    /** The list of validators for Client entities. */
    private List<Validator<Client>> validators;
    /** The Data Access Object (DAO) for Client entities. */
    private final ClientDAO clientDAO;

    public ClientBLL(ClientDAO clientDAO) {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        this.clientDAO = clientDAO;
    }

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    public void insertClient(Client client) throws IllegalAccessException {
        for (Validator<Client> validator : validators) {
            if (validator.validate(client)) {
                clientDAO.insert(client);
            } else {
                throw new IllegalAccessException("Validation failed for the client!");
            }
        }
    }

    public void updateClient(Client client){
        System.out.println("ClientBLL: Updating client with ID: " + client.getId());
        System.out.println("New name: " + client.getName() + ", new email: " + client.getEmail());
        clientDAO.update(client);
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    public Client findByName(String name) {
        return clientDAO.findByName(name);
    }

    public List<String> getAllClientNames() {
        List<String> clientNames = new ArrayList<>();
        for (Client client : findAllClients()) {
            clientNames.add(client.getName());
        }
        return clientNames;
    }

}
