package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import java.util.*;

public class ClientBLL {
    private List<Validator<Client>> validators;
    ClientDAO clientDAO = new ClientDAO();
    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
    }

    public Client findClientById(int id){
        Client client = clientDAO.findById(id);
        if(client == null){
            throw new NoSuchElementException("The client with id " + id + " does not exist");
        }
        return client;
    }

    public Client findClientByName(String name){
        Client client = clientDAO.findByName(name);
        if(client == null){
            throw new NoSuchElementException("The client with name " + name + " does not exist!");
        }
        return client;
    }

    public List<Client> findAllClients(){
        return clientDAO.findAll();
    }

    public void insertClient(Client client) throws IllegalAccessException {
        for(Validator<Client> validator : validators){
            validator.validate(client);
        }
        clientDAO.insert(client);
    }

    public void updateClient(Client client) throws IllegalAccessException {
        System.out.println("ClientBLL: Updating client with id " + client.getId());
        System.out.println("New name: " + client.getName() + ", new email: " + client.getEmail());
        clientDAO.update(client);
    }

    public void deleteClient(int id){
        clientDAO.delete(id);
    }

    public List<String> getAllClientNames(){
        List<String> clientNames = new ArrayList<>();
        for(Client client : findAllClients()){
            clientNames.add(client.getName());
        }
        return clientNames;
    }
}
