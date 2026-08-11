package gov.nova.cosmic.licensing.exception;

import gov.nova.cosmic.common.dto.ApiError;
import gov.nova.cosmic.common.exception.ServiceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LicensingExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception) {
        ApiError error = new ApiError(
                exception.getStatusCode(),
                exception.getMessage(),
                null
        );
        return Response.status(exception.getStatusCode()).entity(error).build();
    }
}
