package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.LawsuitService;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit.LawsuitDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit.LawsuitDtoAssembler;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/lawsuits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LawsuitResource {

    private final LawsuitService lawsuitService;
    private final LawsuitDtoAssembler lawsuitDtoAssembler;

    @Inject
    public LawsuitResource(LawsuitService lawsuitService, LawsuitDtoAssembler lawsuitDtoAssembler) {
        this.lawsuitService = lawsuitService;
        this.lawsuitDtoAssembler = lawsuitDtoAssembler;
    }

    @GET
    public Response getAllLawsuits() {
        Collection<Lawsuit> lawsuits = lawsuitService.getAllLawsuits();

        Set<LawsuitDto> lawsuitsDto = lawsuits.stream().map(lawsuitDtoAssembler::assemble).collect(Collectors.toSet());

        return Response.ok().entity(lawsuitsDto).build();
    }
}
