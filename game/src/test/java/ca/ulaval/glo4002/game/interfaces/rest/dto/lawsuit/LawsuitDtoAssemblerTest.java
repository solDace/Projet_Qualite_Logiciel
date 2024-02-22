package ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LawsuitDtoAssemblerTest {

    private static final int TURN_NUMBER = 2;
    private static final String CHARACTER_NAME = "Bob";
    private static final String LAWYER_NAME = "Alice";
    private static final String ACTION_CODE = "FR";
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    @Mock
    private CharacterActionConverter characterActionConverter;

    private LawsuitDtoAssembler lawsuitDtoAssembler;

    @BeforeEach
    public void setUp() {
        lawsuitDtoAssembler = new LawsuitDtoAssembler(characterActionConverter);
    }

    @Test
    public void givenALawsuitWithALawyer_whenAssemblingALawsuitDto_thenReturnLawsuitDtoWithTheSameValues() {
        Mockito.when(characterActionConverter.toActionCode(ACTION_TYPE)).thenReturn(ACTION_CODE);
        Lawsuit lawsuit = new Lawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);
        lawsuit.setLawyerName(LAWYER_NAME);

        LawsuitDto lawsuitDto = lawsuitDtoAssembler.assemble(lawsuit);

        assertEquals(TURN_NUMBER, lawsuitDto.turnNumber());
        assertEquals(CHARACTER_NAME, lawsuitDto.characterName());
        assertEquals(LAWYER_NAME, lawsuitDto.lawyerName());
        assertEquals(ACTION_CODE, lawsuitDto.actionCode());
    }
}
