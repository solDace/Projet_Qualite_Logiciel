package ca.ulaval.glo4002.game.interfaces.rest.dto.validator;

import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidCharacterNameException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidCharacterTypeException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidSalaryException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CharacterCreationDtoValidatorTest {

    private static final String A_VALID_NAME = "Bob";
    private static final String A_INVALID_NAME = "";
    private static final String A_VALID_TYPE = "hamster";
    private static final String A_INVALID_TYPE = "";
    private static final int A_VALID_SALARY = 1000;
    private static final int A_INVALID_SALARY = 0;

    @Mock
    private CharacterCreationDto characterCreationDto;

    private CharacterCreationDtoValidator characterCreationDtoValidator;

    @BeforeEach
    public void setUp() {
        characterCreationDtoValidator = new CharacterCreationDtoValidator();
    }

    @Test
    public void whenValidateDtoWithCorrectParameters_thenNoInvalidParameterExceptionIsThrow() {
        Mockito.when(characterCreationDto.name()).thenReturn(A_VALID_NAME);
        Mockito.when(characterCreationDto.type()).thenReturn(A_VALID_TYPE);
        Mockito.when(characterCreationDto.salary()).thenReturn(A_VALID_SALARY);

        assertDoesNotThrow(() -> characterCreationDtoValidator.validate(characterCreationDto));
    }

    @Test
    public void whenValidateDtoWithEmptyName_thenThrowsInvalidCharacterNameException() {
        Mockito.when(characterCreationDto.name()).thenReturn(A_INVALID_NAME);

        assertThrows(InvalidCharacterNameException.class, () -> characterCreationDtoValidator.validate(characterCreationDto));
    }

    @Test
    public void whenValidateDtoWithIncorrectType_thenThrowsInvalidCharacterTypeException() {
        Mockito.when(characterCreationDto.name()).thenReturn(A_VALID_NAME);
        Mockito.when(characterCreationDto.type()).thenReturn(A_INVALID_TYPE);

        assertThrows(InvalidCharacterTypeException.class, () -> characterCreationDtoValidator.validate(characterCreationDto));
    }

    @Test
    public void whenValideDtoWithSalaryThatIsNotAPositiveNumber_thenThrowsInvalidSalaryException() {
        Mockito.when(characterCreationDto.name()).thenReturn(A_VALID_NAME);
        Mockito.when(characterCreationDto.type()).thenReturn(A_VALID_TYPE);
        Mockito.when(characterCreationDto.salary()).thenReturn(A_INVALID_SALARY);

        assertThrows(InvalidSalaryException.class, () -> characterCreationDtoValidator.validate(characterCreationDto));
    }
}
