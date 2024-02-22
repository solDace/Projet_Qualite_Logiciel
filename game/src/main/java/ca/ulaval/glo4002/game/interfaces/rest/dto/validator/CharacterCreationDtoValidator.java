package ca.ulaval.glo4002.game.interfaces.rest.dto.validator;

import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.RodentType;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidCharacterNameException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidCharacterTypeException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidSalaryException;

public class CharacterCreationDtoValidator {
    public void validate(CharacterCreationDto characterCreationDto) throws InvalidParameterException {
        validateName(characterCreationDto.name());
        validateType(characterCreationDto.type());
        validateSalary(characterCreationDto.salary());
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidCharacterNameException();
        }
    }

    private void validateType(String type) {
        for (RodentType rodentType : RodentType.values()) {
            if (rodentType.name().equals(type)) {
                return;
            }
        }
        throw new InvalidCharacterTypeException();
    }

    private void validateSalary(int salary) {
        if (salary <= 0) {
            throw new InvalidSalaryException();
        }
    }

}
