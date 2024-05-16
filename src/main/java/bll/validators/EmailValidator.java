package bll.validators;

import model.Client;

import java.util.regex.Pattern;
/**
 * A validator for email addresses.
 */
public class EmailValidator implements Validator<Client> {
    /** The regular expression pattern for validating email addresses. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    /**
     * Validates the email address of a client.
     *
     * @param client The client whose email address is to be validated.
     * @return true if the email address is valid, false otherwise.
     */
    @Override
    public boolean validate(Client client) {
        return EMAIL_PATTERN.matcher(client.getEmail()).matches();
    }
}
