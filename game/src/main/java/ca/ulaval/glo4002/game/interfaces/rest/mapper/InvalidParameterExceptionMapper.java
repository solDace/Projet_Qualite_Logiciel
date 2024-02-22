package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.interfaces.rest.dto.ErrorDto;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {

    @Override
    public Response toResponse(InvalidParameterException exception) {
        ErrorDto errorResponse = new ErrorDto(exception.getError(), exception.getDescription());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
