package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.CharacterService;
import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.action.AddCharacterGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.interfaces.converter.RodentToCharacterTypeConverter;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterStatusDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.RodentType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterCreationDtoValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CharacterResourceTest {

    private static final String A_CHARACTER_NAME = "Bob";
    private static final String A_VALID_CHARACTER_TYPE = "hamster";
    private static final int A_SALARY = 1000;
    private static final int A_CHARACTER_REPUTATION_POINTS = 80;
    private static final Money MONEY = new Money(4504.0f);
    private static final CharacterType CHARACTER_TYPE = CharacterType.ACTOR;

    @Mock
    private CharacterCreationDtoValidator characterCreationDtoValidator;
    @Mock
    private CharacterService characterService;
    @Mock
    private GameService gameService;
    @Mock
    private CharacterCreationDto characterCreationDto;
    @Mock
    private Actor existingCharacter;

    private CharacterResource characterResource;

    @BeforeEach
    public void setUp() {
        characterResource = new CharacterResource(characterCreationDtoValidator, characterService, gameService);
    }

    @Test
    public void whenAddCharacterWithValidCharacterCreationDto_thenAddCharacterGameActionIsAddedToTheGame() {
        Mockito.when(characterCreationDto.type()).thenReturn(A_VALID_CHARACTER_TYPE);
        Mockito.when(characterCreationDto.name()).thenReturn(A_CHARACTER_NAME);
        Mockito.when(characterCreationDto.salary()).thenReturn(A_SALARY);

        characterResource.addCharacter(characterCreationDto);

        GameAction gameAction = new AddCharacterGameAction(A_CHARACTER_NAME, new Money(A_SALARY),
                                                           RodentToCharacterTypeConverter.convertToCharacterType(RodentType.valueOf(A_VALID_CHARACTER_TYPE)));
        Mockito.verify(gameService).addAction(gameAction);
    }

    @Test
    public void whenAddCharacterWithValidCharacterCreationDto_thenReturnResponseWithStatusOK() {
        Mockito.when(characterCreationDto.type()).thenReturn(A_VALID_CHARACTER_TYPE);

        Response response = characterResource.addCharacter(characterCreationDto);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void givenExistingCharacter_whenGetCharacterStatusUsingItsName_thenReturnStatusOkResponseWithCharacterStatusDtoInItsBody() {
        givenExistingCharacter();

        Response response = characterResource.getCharacterStatus(A_CHARACTER_NAME);

        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(CharacterStatusDto.class, response.getEntity().getClass());
        assertEquals(A_CHARACTER_NAME, ((CharacterStatusDto) response.getEntity()).name());
    }

    private void givenExistingCharacter() {
        Mockito.when(characterService.getCharacter(A_CHARACTER_NAME)).thenReturn(existingCharacter);
        Mockito.when(existingCharacter.getName()).thenReturn(A_CHARACTER_NAME);
        Mockito.when(existingCharacter.getCharacterType()).thenReturn(CHARACTER_TYPE);
        Mockito.when(existingCharacter.getBankBalance()).thenReturn(MONEY);
        Mockito.when(existingCharacter.getReputationPoints()).thenReturn(A_CHARACTER_REPUTATION_POINTS);
    }
}
