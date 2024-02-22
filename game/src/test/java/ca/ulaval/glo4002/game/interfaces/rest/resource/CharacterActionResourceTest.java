package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.action.CharacterActionCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterActionCreationDtoValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CharacterActionResourceTest {

    private static final String PROMOTE_MOVIE_ACTION_CODE = "PO";
    private static final String REQUEST_FROM = "Bobino";
    private static final String REQUEST_TO = "Bobinette";
    private CharacterActionResource characterActionResource;
    @Mock
    private CharacterActionConverter characterActionConverter;
    @Mock
    private CharacterActionCreationDto actionCreationDto;
    @Mock
    private GameService gameService;
    @Mock
    private CharacterActionCreationDtoValidator actionCreationDtoValidator;

    @BeforeEach
    public void setUp() {
        characterActionResource = new CharacterActionResource(gameService, actionCreationDtoValidator, characterActionConverter);

        Mockito.when(actionCreationDto.actionCode()).thenReturn(PROMOTE_MOVIE_ACTION_CODE);
    }

    @Test
    public void whenSubmitActionWithActionCreationDtoAsParameter_thenActionCreationDtoValidatesTheActionCreationDto() {

        characterActionResource.submitAction(actionCreationDto);

        Mockito.verify(actionCreationDtoValidator).validate(actionCreationDto);
    }

    @Test
    public void givenAValidActionCreationDto_whenSubmitAction_thenCharacterActionIsAddedToTheGame() {
        Mockito.when(actionCreationDto.from()).thenReturn(REQUEST_FROM);
        Mockito.when(actionCreationDto.to()).thenReturn(REQUEST_TO);
        Mockito.when(characterActionConverter.fromActionCode(PROMOTE_MOVIE_ACTION_CODE))
               .thenReturn(CharacterInteractionActionType.PROMOTE_MOVIE);

        characterActionResource.submitAction(actionCreationDto);

        Mockito.verify(gameService).addCharacterInteraction(REQUEST_FROM, REQUEST_TO, CharacterInteractionActionType.PROMOTE_MOVIE);
    }

    @Test
    public void givenAValidRequest_whenSubmitAction_thenStatusOKIsReturned() {
        Mockito.when(actionCreationDto.from()).thenReturn(REQUEST_FROM);
        Mockito.when(actionCreationDto.to()).thenReturn(REQUEST_TO);
        Mockito.when(characterActionConverter.fromActionCode(PROMOTE_MOVIE_ACTION_CODE))
               .thenReturn(CharacterInteractionActionType.PROMOTE_MOVIE);

        Response response = characterActionResource.submitAction(actionCreationDto);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
