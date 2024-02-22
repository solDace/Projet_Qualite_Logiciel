package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountNotFoundException;
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
class RattedInAccountNotFoundExceptionMapperTest {

    private static final String EXPECTED_ERROR_TYPE = "CHARACTER_NOT_FOUND";
    private static final String EXPECTED_ERROR_DESCRIPTION = "Character not found.";

    @Mock
    private RattedInAccountNotFoundException rattedInAccountNotFoundException;

    private RattedInAccountNotFoundExceptionMapper rattedInAccountNotFoundExceptionMapper;

    @BeforeEach
    public void setUp() {
        rattedInAccountNotFoundExceptionMapper = new RattedInAccountNotFoundExceptionMapper();
        Mockito.when(rattedInAccountNotFoundException.getMessage()).thenReturn(EXPECTED_ERROR_DESCRIPTION);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithNotFoundStatus() {

        Response response = rattedInAccountNotFoundExceptionMapper.toResponse(rattedInAccountNotFoundException);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithErrorDtoForBody() {

        Response response = rattedInAccountNotFoundExceptionMapper.toResponse(rattedInAccountNotFoundException);

        assertEquals(ErrorDto.class, response.getEntity().getClass());
        assertEquals(EXPECTED_ERROR_TYPE, ((ErrorDto) response.getEntity()).error());
        assertEquals(rattedInAccountNotFoundException.getMessage(), ((ErrorDto) response.getEntity()).description());
    }
}
