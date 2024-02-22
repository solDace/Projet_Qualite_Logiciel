package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.domain.character.CharacterNotFoundException;
import ca.ulaval.glo4002.game.interfaces.rest.dto.ErrorDto;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CharacterNotFoundExceptionMapper implements ExceptionMapper<CharacterNotFoundException> {

    @Override
    public Response toResponse(CharacterNotFoundException exception) {
        ErrorDto error = new ErrorDto("CHARACTER_NOT_FOUND", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
