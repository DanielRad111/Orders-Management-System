package bll.validators;

import model.Client;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<Client> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public boolean validate(Client client) {
        return EMAIL_PATTERN.matcher(client.getEmail()).matches();
    }
}
