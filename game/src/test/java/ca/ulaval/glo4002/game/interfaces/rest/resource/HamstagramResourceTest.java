package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.HamstagramService;
import ca.ulaval.glo4002.game.application.RepresentationService;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.interfaces.rest.dto.hamstagram.HamstagramDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HamstagramResourceTest {

    private static final String A_CHARACTER_USERNAME = "Bob";

    @Mock
    private HamstagramService hamstagramService;
    @Mock
    private RepresentationService representationService;
    @Mock
    private HamstagramAccount hamstagramAccount;

    private HamstagramResource hamstagramResource;

    @BeforeEach
    public void setUp() {
        hamstagramResource = new HamstagramResource(hamstagramService, representationService);
    }

    @Test
    public void givenExistingCharacter_whenGetCharacterHamstagramAccount_thenReturnStatusOkResponseWithHamstagramDtoInItsBody() {
        givenExistingCharacter();

        Response response = hamstagramResource.getHamstagramAccount(A_CHARACTER_USERNAME);

        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(HamstagramDto.class, response.getEntity().getClass());
        assertEquals(A_CHARACTER_USERNAME, ((HamstagramDto) response.getEntity()).username());
    }

    private void givenExistingCharacter() {
        Mockito.when(hamstagramService.getHamstagramAccount(A_CHARACTER_USERNAME)).thenReturn(hamstagramAccount);
        Mockito.when(hamstagramAccount.getUsername()).thenReturn(A_CHARACTER_USERNAME);
    }
}
