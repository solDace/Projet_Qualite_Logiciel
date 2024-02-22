package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.application.RattedInService;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.RattedInContactRequestGameAction;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;
import ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin.ContactRequestDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin.RattedInDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RattedInResourceTest {

    private static final String A_USERNAME = "Bob", RECEIVER_USERNAME = "Gilbert", REQUESTER_USERNAME = "Romuald";

    @Mock
    private RattedInService rattedInService;
    @Mock
    private GameService gameService;
    @Mock
    private ContactRequestDto contactRequestDto;
    @Mock
    private RattedInAccount rattedInAccount;
    @Mock
    private RattedInAccountStatus rattedInAccountStatus;

    private RattedInResource rattedInResource;

    @BeforeEach
    public void setUp() {
        rattedInResource = new RattedInResource(rattedInService, gameService);
    }

    @Test
    public void givenValidContactRequestDto_whenReceivingContactRequest_thenRattedInContactRequestGameActionIsAddedToTheGame() {
        Mockito.when(contactRequestDto.username()).thenReturn(REQUESTER_USERNAME);

        rattedInResource.contactRequest(RECEIVER_USERNAME, contactRequestDto);

        GameAction gameAction = new RattedInContactRequestGameAction(RECEIVER_USERNAME, REQUESTER_USERNAME);
        Mockito.verify(gameService).addAction(gameAction);
    }

    @Test
    public void givenValidContactRequestDto_whenReceivingContactRequest_thenReturnResponseWithStatusOK() {
        Mockito.when(contactRequestDto.username()).thenReturn(REQUESTER_USERNAME);

        Response response = rattedInResource.contactRequest(RECEIVER_USERNAME, contactRequestDto);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void givenExistingRattedInAccount_whenGetRattedInAccountUsingItsName_thenReturnStatusOkResponseWithRattedInDtoInInItsBody() {
        givenExistingRattedInAccount();

        Response response = rattedInResource.getRattedInAccount(A_USERNAME);

        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(RattedInDto.class, response.getEntity().getClass());
        assertEquals(A_USERNAME, ((RattedInDto) response.getEntity()).username());
    }

    private void givenExistingRattedInAccount() {
        Mockito.when(rattedInService.getRattedInAccount(A_USERNAME)).thenReturn(rattedInAccount);
        Mockito.when(rattedInAccount.getAccountStatus()).thenReturn(rattedInAccountStatus);
        Mockito.when(rattedInAccount.getUsername()).thenReturn(A_USERNAME);
    }
}
