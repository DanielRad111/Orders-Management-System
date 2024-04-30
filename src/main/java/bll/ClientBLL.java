package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import java.util.*;

public class ClientBLL {
    private List<Validator<Client>> validators;
    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
    }

    public Client findClientById(int id){
        Client client = ClientDAO.findById(id);
        if(client == null){
            throw new NoSuchElementException("The client with id " + id + " does not exist");
        }
        return client;
    }

    public int insertClient(Client client){
        for(Validator<Client> validator : validators){
            validator.validate(client);
        }
        return ClientDAO.insert(client);
    }
}
