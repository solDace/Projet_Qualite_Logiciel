package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.domain.character.CharacterNotFoundException;
import ca.ulaval.glo4002.game.interfaces.rest.dto.ErrorDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CharacterNotFoundExceptionMapperTest {

    private static final String EXPECTED_ERROR_TYPE = "CHARACTER_NOT_FOUND";
    private static final String EXPECTED_ERROR_DESCRIPTION = "CHARACTER_NOT_FOUND";

    @Mock
    private CharacterNotFoundException characterNotFoundException;

    private CharacterNotFoundExceptionMapper characterNotFoundExceptionMapper;

    @BeforeEach
    public void setUp() {
        characterNotFoundExceptionMapper = new CharacterNotFoundExceptionMapper();
        Mockito.when(characterNotFoundException.getMessage()).thenReturn(EXPECTED_ERROR_DESCRIPTION);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithNotFoundStatus() {

        Response response = characterNotFoundExceptionMapper.toResponse(characterNotFoundException);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithErrorDtoForBody() {

        Response response = characterNotFoundExceptionMapper.toResponse(characterNotFoundException);

        assertEquals(ErrorDto.class, response.getEntity().getClass());
        assertEquals(EXPECTED_ERROR_TYPE, ((ErrorDto) response.getEntity()).error());
        assertEquals(characterNotFoundException.getMessage(), ((ErrorDto) response.getEntity()).description());
    }
}
