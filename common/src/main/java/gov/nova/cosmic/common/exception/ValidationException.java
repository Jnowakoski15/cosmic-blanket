package gov.nova.cosmic.common.exception;

public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(422, message);
    }
}
