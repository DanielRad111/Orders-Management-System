package bll.validators;

import model.Client;

public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 18;
    @Override
    public void validate(Client client) {
        if(client.getAge() < MIN_AGE){
            throw new IllegalArgumentException("Client is under 18!");
        }
    }
}
