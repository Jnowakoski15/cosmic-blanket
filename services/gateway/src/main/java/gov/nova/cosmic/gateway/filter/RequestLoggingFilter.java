package gov.nova.cosmic.gateway.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.UUID;

@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(RequestLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        requestContext.getHeaders().putSingle("X-Trace-Id", traceId);
        LOG.infof("[%s] %s %s", traceId, requestContext.getMethod(), requestContext.getUriInfo().getPath());
    }
}
