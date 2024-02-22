package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.application.RattedInService;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.RattedInContactRequestGameAction;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin.ContactRequestDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin.RattedInDto;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rattedin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RattedInResource {

    private final RattedInService rattedInService;

    private final GameService gameService;

    @Inject
    public RattedInResource(RattedInService rattedInService, GameService gameService) {
        this.rattedInService = rattedInService;
        this.gameService = gameService;
    }

    @Path("/{username}/request")
    @POST
    public Response contactRequest(@PathParam("username") String username, ContactRequestDto contactRequestDto) {
        GameAction gameAction = new RattedInContactRequestGameAction(username, contactRequestDto.username());
        gameService.addAction(gameAction);

        return Response.ok().build();
    }

    @Path("/{username}")
    @GET
    public Response getRattedInAccount(@PathParam("username") String username) {
        RattedInAccount rattedInAccount = rattedInService.getRattedInAccount(username);

        RattedInDto rattedInDto = new RattedInDto(rattedInAccount.getUsername(),
                                                  rattedInAccount.getAccountStatus().getTypeName(),
                                                  rattedInAccount.getContactsUsernames());

        return Response.ok().entity(rattedInDto).build();
    }
}
