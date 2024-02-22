package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.interfaces.rest.dto.turn.TurnDto;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    private final GameService gameService;

    @Inject
    public GameResource(GameService service) {
        this.gameService = service;
    }

    @Path("/turn")
    @POST
    public Response executeTurn() {
        int turnNumber = gameService.executeTurn();

        return Response.ok().entity(new TurnDto(turnNumber)).build();
    }

    @Path("/reset")
    @POST
    public Response resetGame() {
        gameService.resetGame();

        return Response.ok().build();
    }
}
