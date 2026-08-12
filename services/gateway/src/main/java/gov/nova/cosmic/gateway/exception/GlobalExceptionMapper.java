package gov.nova.cosmic.gateway.exception;

import gov.nova.cosmic.common.dto.ApiError;
import gov.nova.cosmic.common.exception.ServiceException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ServiceException se) {
            return Response.status(se.getStatusCode())
                    .entity(new ApiError(se.getStatusCode(), se.getMessage(), uriInfo.getPath()))
                    .build();
        }

        LOG.error("Unhandled exception", exception);
        return Response.status(500)
                .entity(new ApiError(500, "Internal server error", uriInfo.getPath()))
                .build();
    }
}
