package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.interfaces.rest.dto.turn.TurnDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GameResourceTest {

    private static final int A_TURN_NUMBER = 1;

    @Mock
    private GameService gameService;

    private GameResource gameResource;

    @BeforeEach
    public void setUp() {
        gameResource = new GameResource(gameService);
    }

    @Test
    public void whenExecuteTurn_thenUseGameServiceToExecuteTurn() {

        gameResource.executeTurn();

        Mockito.verify(gameService).executeTurn();
    }

    @Test
    public void whenExecuteTurn_thenReturnStatusOkResponseWithStatusDtoInItsBody() {
        Mockito.when(gameService.executeTurn()).thenReturn(A_TURN_NUMBER);

        Response response = gameResource.executeTurn();

        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(TurnDto.class, response.getEntity().getClass());
        assertEquals(A_TURN_NUMBER, ((TurnDto) response.getEntity()).turnNumber());
    }

    @Test
    public void whenResetGame_thenReturnResponseWithStatusOK() {

        Response response = gameResource.resetGame();

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
