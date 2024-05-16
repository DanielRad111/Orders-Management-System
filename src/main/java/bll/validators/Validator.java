package bll.validators;
/**
 * An interface for validating objects of type T.
 *
 * @param <T> The type of objects to be validated.
 */
public interface Validator<T>{
    /**
     * Validates an object of type T.
     *
     * @param t The object to be validated.
     * @return true if the object is valid, false otherwise.
     */
    public boolean validate(T t);
}
