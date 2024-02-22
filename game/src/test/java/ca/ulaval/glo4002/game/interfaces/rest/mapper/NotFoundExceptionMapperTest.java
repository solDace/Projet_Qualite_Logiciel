package ca.ulaval.glo4002.game.interfaces.rest.mapper;

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
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionMapperTest {

    private static final String AN_INVALID_PATH = "/invalid_path";

    @Mock
    private NotFoundException notFoundException;
    @Mock
    private HttpServletRequest request;

    private NotFoundExceptionMapper notFoundExceptionMapper;

    @BeforeAll
    public static void disableLogs() {
        // Set the logging level to OFF for the specific class ConstraintValidationExceptionMapper
        Logger logger = (Logger) LoggerFactory.getLogger(NotFoundExceptionMapper.class);
        logger.setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUp() {
        notFoundExceptionMapper = new NotFoundExceptionMapper(request);
        Mockito.when(request.getPathInfo()).thenReturn(AN_INVALID_PATH);
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithBadRequestStatus() {

        Response response = notFoundExceptionMapper.toResponse(notFoundException);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
}
