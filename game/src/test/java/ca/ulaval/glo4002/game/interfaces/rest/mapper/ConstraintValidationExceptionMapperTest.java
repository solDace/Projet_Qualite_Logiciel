package ca.ulaval.glo4002.game.interfaces.rest.mapper;

import ca.ulaval.glo4002.game.interfaces.rest.dto.ConstraintValidationResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ConstraintValidationExceptionMapperTest {

    @Mock
    private ConstraintViolationException constraintViolationException;

    private ConstraintValidationExceptionMapper constraintValidationExceptionMapper;

    @BeforeAll
    public static void disableLogs() {
        // Set the logging level to OFF for the specific class ConstraintValidationExceptionMapper
        Logger logger = (Logger) LoggerFactory.getLogger(ConstraintValidationExceptionMapper.class);
        logger.setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUp() {
        constraintValidationExceptionMapper = new ConstraintValidationExceptionMapper();
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithBadRequestStatus() {

        Response response = constraintValidationExceptionMapper.toResponse(constraintViolationException);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void whenMappingErrorToResponse_thenReturnResponseWithConstraintValidationResponseInItsBody() {

        Response response = constraintValidationExceptionMapper.toResponse(constraintViolationException);

        assertEquals(ConstraintValidationResponse.class, response.getEntity().getClass());
    }
}
