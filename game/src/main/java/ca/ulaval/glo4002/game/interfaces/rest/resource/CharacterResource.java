package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.CharacterService;
import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.action.AddCharacterGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.interfaces.converter.RodentToCharacterTypeConverter;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.CharacterStatusDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.character.RodentType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterCreationDtoValidator;
import ca.ulaval.glo4002.game.interfaces.rest.exception.InvalidParameterException;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/characters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final CharacterCreationDtoValidator characterCreationDtoValidator;
    private final GameService gameService;
    private final CharacterService characterService;

    @Inject
    public CharacterResource(CharacterCreationDtoValidator characterCreationDtoValidator, CharacterService characterService, GameService gameService) {
        this.characterCreationDtoValidator = characterCreationDtoValidator;
        this.gameService = gameService;
        this.characterService = characterService;
    }

    @POST
    public Response addCharacter(CharacterCreationDto characterCreationDto) throws InvalidParameterException {
        characterCreationDtoValidator.validate(characterCreationDto);
        RodentType rodentType = RodentType.valueOf(characterCreationDto.type());
        CharacterType characterType = RodentToCharacterTypeConverter.convertToCharacterType(rodentType);
        Money characterSalary = new Money(characterCreationDto.salary());
        GameAction gameAction = new AddCharacterGameAction(characterCreationDto.name(), characterSalary, characterType);
        gameService.addAction(gameAction);

        return Response.ok().build();
    }

    @Path("/{name}")
    @GET
    public Response getCharacterStatus(@PathParam("name") String name) {
        Character character = characterService.getCharacter(name);

        RodentType rodentType = RodentToCharacterTypeConverter.convertToRodentType(character.getCharacterType());

        CharacterStatusDto characterStatusDto = new CharacterStatusDto(character.getName(),
                                                                       rodentType,
                                                                       character.getReputationPoints(),
                                                                       character.getBankBalance().floatValue(),
                                                                       character.getNbLawsuits());

        return Response.ok().entity(characterStatusDto).build();
    }
}
