package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.HamstagramService;
import ca.ulaval.glo4002.game.application.RepresentationService;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.interfaces.rest.dto.hamstagram.HamstagramDto;

import java.util.Set;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hamstagram")
@Produces(MediaType.APPLICATION_JSON)
public class HamstagramResource {

    private final HamstagramService hamstagramService;
    private final RepresentationService contratService;

    @Inject
    public HamstagramResource(HamstagramService service, RepresentationService representationService) {
        this.hamstagramService = service;
        this.contratService = representationService;
    }

    @Path("/{username}")
    @GET
    public Response getHamstagramAccount(@PathParam("username") String username) {
        String agentName = contratService.getAgentNameRepresentingActor(username);
        Set<String> representedActorsNames = contratService.getActorNamesRepresentedByAgent(username);
        HamstagramAccount hamstagramAccount = hamstagramService.getHamstagramAccount(username);

        HamstagramDto hamstagramDto = new HamstagramDto(hamstagramAccount.getUsername(),
                                                        hamstagramAccount.getNbFollowers(),
                                                        representedActorsNames,
                                                        agentName);

        return Response.ok().entity(hamstagramDto).build();
    }
}
