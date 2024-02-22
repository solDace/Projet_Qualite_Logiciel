package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.interfaces.rest.dto.UnknownErrorResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CatchAllExceptionMapperTest {

    private static final String EXPECTED_ERROR_TYPE = "UNKNOWN ERROR";
    private static final String AN_URL_PATH = "/";

    @Mock
    private Exception exception;
    @Mock
    private HttpServletRequest request;

    private CatchAllExceptionMapper catchAllExceptionMapper;

    @BeforeAll
    public static void disableLogs() {
        // Set the logging level to OFF for the specific class ConstraintValidationExceptionMapper
        Logger logger = (Logger) LoggerFactory.getLogger(CatchAllExceptionMapper.class);
        logger.setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUp() {
        catchAllExceptionMapper = new CatchAllExceptionMapper(request);
        Mockito.when(request.getPathInfo()).thenReturn(AN_URL_PATH);
        Mockito.when(exception.getMessage()).thenReturn(EXPECTED_ERROR_TYPE);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithNotFoundStatus() {

        Response response = catchAllExceptionMapper.toResponse(exception);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithErrorDtoForBody() {

        Response response = catchAllExceptionMapper.toResponse(exception);

        assertEquals(UnknownErrorResponse.class, response.getEntity().getClass());
        assertEquals(EXPECTED_ERROR_TYPE, ((UnknownErrorResponse) response.getEntity()).message);
    }
}
