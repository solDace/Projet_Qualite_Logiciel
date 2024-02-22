package ca.ulaval.glo4002.game.interfaces.rest.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RequestLoggerFilter implements ContainerResponseFilter {
    private final Logger logger = LoggerFactory.getLogger(RequestLoggerFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        var statusCode = responseContext.getStatusInfo().getStatusCode();
        var statusName = responseContext.getStatusInfo().getReasonPhrase();
        var path = requestContext.getUriInfo().getPath();
        var method = requestContext.getMethod();

        if (statusCode < 400) {
            logger.info(statusCode + " " + statusName + " - HTTP " + method + " /" + path);
        } else {
            logger.error(statusCode + " " + statusName + " - HTTP " + method + " /" + path);
        }
    }
}
