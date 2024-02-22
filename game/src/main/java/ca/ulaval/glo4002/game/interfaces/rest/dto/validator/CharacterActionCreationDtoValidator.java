package ca.ulaval.glo4002.game.interfaces.rest.dto.validator;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.action.CharacterActionCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidActionCodeException;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;

import jakarta.inject.Inject;

public class CharacterActionCreationDtoValidator {

    private final CharacterActionConverter characterActionConverter;

    @Inject
    public CharacterActionCreationDtoValidator(CharacterActionConverter characterActionConverter) {
        this.characterActionConverter = characterActionConverter;
    }

    public void validate(CharacterActionCreationDto actionCreationDto) throws InvalidParameterException {
        String actionCode = actionCreationDto.actionCode();
        if (actionCode == null) {
            throw new InvalidActionCodeException();
        }

        CharacterInteractionActionType typeFound = characterActionConverter.fromActionCode(actionCode);
        if (typeFound == null) {
            throw new InvalidActionCodeException();
        }
    }
}
