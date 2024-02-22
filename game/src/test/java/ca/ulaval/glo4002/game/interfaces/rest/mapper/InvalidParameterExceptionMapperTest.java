package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.interfaces.rest.dto.ErrorDto;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class InvalidParameterExceptionMapperTest {

    private static final String EXPECTED_ERROR_TYPE = "INVALID_NAME";
    private static final String EXPECTED_ERROR_DESCRIPTION = "Invalid name.";

    @Mock
    private InvalidParameterException invalidParameterException;

    private InvalidParameterExceptionMapper invalidParameterExceptionMapper;

    @BeforeEach
    public void setUp() {
        invalidParameterExceptionMapper = new InvalidParameterExceptionMapper();
        Mockito.when(invalidParameterException.getError()).thenReturn(EXPECTED_ERROR_TYPE);
        Mockito.when(invalidParameterException.getDescription()).thenReturn(EXPECTED_ERROR_DESCRIPTION);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithStatusNotFound() {

        Response response = invalidParameterExceptionMapper.toResponse(invalidParameterException);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithValidErrorDtoInItsBody() {

        Response response = invalidParameterExceptionMapper.toResponse(invalidParameterException);

        assertEquals(ErrorDto.class, response.getEntity().getClass());
        assertEquals(invalidParameterException.getError(), ((ErrorDto) response.getEntity()).error());
        assertEquals(invalidParameterException.getDescription(), ((ErrorDto) response.getEntity()).description());
    }
}
