package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.action.CharacterActionCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterActionCreationDtoValidator;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidActionCodeException;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/actions")
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterActionResource {

    private final GameService gameService;
    private final CharacterActionConverter actionCodeConverter;
    private final CharacterActionCreationDtoValidator actionCreationDtoValidator;

    @Inject
    public CharacterActionResource(GameService gameService, CharacterActionCreationDtoValidator actionCreationDtoValidator,
                                   CharacterActionConverter actionCodeConverter) {
        this.gameService = gameService;
        this.actionCodeConverter = actionCodeConverter;
        this.actionCreationDtoValidator = actionCreationDtoValidator;
    }

    @POST
    public Response submitAction(CharacterActionCreationDto actionCreationDto) throws InvalidActionCodeException {
        actionCreationDtoValidator.validate(actionCreationDto);

        CharacterInteractionActionType characterInteractionActionType = actionCodeConverter.fromActionCode(actionCreationDto.actionCode());

        gameService.addCharacterInteraction(actionCreationDto.from(), actionCreationDto.to(), characterInteractionActionType);

        return Response.ok().build();
    }
}
