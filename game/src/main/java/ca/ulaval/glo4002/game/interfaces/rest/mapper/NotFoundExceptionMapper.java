package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private final Logger logger = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    private final HttpServletRequest request;

    @Inject
    public NotFoundExceptionMapper(@Context HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Response toResponse(NotFoundException exception) {
        var path = request.getPathInfo();

        if (shouldLogNotFoundException(path)) {
            logger.error("Resource not found for " + path);
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    /* If making requests in a browser, they will try to fetch a favicon, and this just pollutes the logs */
    private boolean shouldLogNotFoundException(String path) {
        return !path.startsWith("/favicon");
    }
}
