package gov.nova.cosmic.common.exception;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(404, message);
    }
}
