package ca.ulaval.glo4002.game.interfaces.rest.dto.validator;

import ca.ulaval.glo4002.game.interfaces.rest.dto.action.CharacterActionCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidActionCodeException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CharacterActionCreationDtoValidatorTest {

    private static final String A_NAME = "Bobinette";
    private static final String ANOTHER_NAME = "Bobino";
    private static final String VALID_ACTION_CODE = "PO";
    private static final String INVALID_ACTION_CODE = "INVALID_ACTION_CODE";
    private CharacterActionCreationDtoValidator actionCreationDtoValidator;
    private CharacterActionCreationDto actionCreationDto;

    @BeforeEach
    public void setUp() {
        CharacterActionConverter characterActionConverter = new CharacterActionConverter();
        actionCreationDtoValidator = new CharacterActionCreationDtoValidator(characterActionConverter);
    }

    @Test
    public void givenAValidActionCreationDto_whenValidate_thenNoExceptionIsThrown() {
        actionCreationDto = new CharacterActionCreationDto(A_NAME, ANOTHER_NAME, VALID_ACTION_CODE);

        assertDoesNotThrow(() -> actionCreationDtoValidator.validate(actionCreationDto));
    }

    @Test
    public void givenAnInvalidActionCode_whenValidate_thenInvalidActionCodeExceptionIsThrown() {
        actionCreationDto = new CharacterActionCreationDto(A_NAME, ANOTHER_NAME, INVALID_ACTION_CODE);

        assertThrows(InvalidActionCodeException.class, () -> actionCreationDtoValidator.validate(actionCreationDto));
    }

    @Test
    public void givenNoActionCode_whenValidate_thenInvalidActionCodeExceptionIsThrown() {
        actionCreationDto = new CharacterActionCreationDto(A_NAME, ANOTHER_NAME, null);

        assertThrows(InvalidActionCodeException.class, () -> actionCreationDtoValidator.validate(actionCreationDto));
    }
}
