package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountNotFoundException;
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
class HamstagramAccountNotFoundExceptionMapperTest {

    private static final String EXPECTED_ERROR_TYPE = "CHARACTER_NOT_FOUND";
    private static final String EXPECTED_ERROR_DESCRIPTION = "Character not found.";

    @Mock
    private HamstagramAccountNotFoundException hamstagramAccountNotFoundException;

    private HamstagramAccountNotFoundExceptionMapper hamstagramAccountNotFoundExceptionMapper;

    @BeforeEach
    public void setUp() {
        hamstagramAccountNotFoundExceptionMapper = new HamstagramAccountNotFoundExceptionMapper();
        Mockito.when(hamstagramAccountNotFoundException.getMessage()).thenReturn(EXPECTED_ERROR_DESCRIPTION);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithNotFoundStatus() {

        Response response = hamstagramAccountNotFoundExceptionMapper.toResponse(hamstagramAccountNotFoundException);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithErrorDtoForBody() {

        Response response = hamstagramAccountNotFoundExceptionMapper.toResponse(hamstagramAccountNotFoundException);

        assertEquals(ErrorDto.class, response.getEntity().getClass());
        assertEquals(EXPECTED_ERROR_TYPE, ((ErrorDto) response.getEntity()).error());
        assertEquals(hamstagramAccountNotFoundException.getMessage(), ((ErrorDto) response.getEntity()).description());
    }
}
