package gov.nova.cosmic.gateway.filter;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!securityIdentity.isAnonymous()) {
            requestContext.getHeaders().putSingle("X-User-Id", securityIdentity.getPrincipal().getName());
            securityIdentity.getRoles().forEach(role ->
                requestContext.getHeaders().add("X-User-Role", role)
            );
        }
    }
}
