package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.LawsuitService;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit.LawsuitDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit.LawsuitDtoAssembler;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LawsuitResourceTest {

    private final Lawsuit lawsuit = new Lawsuit(4, "Bob", CharacterInteractionActionType.REVEAL_SCANDAL);
    private final Lawsuit anotherLawsuit = new Lawsuit(7, "Charles", CharacterInteractionActionType.GOSSIP);

    @Mock
    private LawsuitService lawsuitService;
    @Mock
    private LawsuitDtoAssembler lawsuitDtoAssembler;
    @Mock
    private LawsuitDto lawsuitDto, anotherLawsuitDto;

    private LawsuitResource lawsuitResource;

    @BeforeEach
    void setUp() {
        lawsuitResource = new LawsuitResource(lawsuitService, lawsuitDtoAssembler);
    }

    @Test
    public void givenLawsuits_whenGetAllLawsuits_thenReturnStatusOkResponseWithSetOfLawsuitDtoInItsBody() {
        Mockito.when(lawsuitService.getAllLawsuits()).thenReturn(Set.of(lawsuit, anotherLawsuit));
        Mockito.when(lawsuitDtoAssembler.assemble(lawsuit)).thenReturn(lawsuitDto);
        Mockito.when(lawsuitDtoAssembler.assemble(anotherLawsuit)).thenReturn(anotherLawsuitDto);

        Response response = lawsuitResource.getAllLawsuits();

        assertEquals(Set.of(lawsuitDto, anotherLawsuitDto), response.getEntity());
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
