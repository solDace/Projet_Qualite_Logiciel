package ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit;

import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;

import jakarta.inject.Inject;

public class LawsuitDtoAssembler {

    private final CharacterActionConverter characterActionConverter;

    @Inject
    public LawsuitDtoAssembler(CharacterActionConverter characterActionConverter) {
        this.characterActionConverter = characterActionConverter;
    }

    public LawsuitDto assemble(Lawsuit lawsuit) {
        return new LawsuitDto(lawsuit.getTurnNumber(),
                              lawsuit.getCharacterName(),
                              characterActionConverter.toActionCode(lawsuit.getActionType()),
                              lawsuit.getLawyerName());
    }
}
