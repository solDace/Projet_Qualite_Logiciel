package ca.ulaval.glo4002.game.domain.rattedIn.factory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInIDFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RattedInAccountFactoryTest {
    private static final String A_USERNAME = "BOB_007";
    private static final CharacterID CHARACTER_ID = new CharacterID(A_USERNAME);

    @Mock
    private RattedInIDFactory rattedInIDFactory;
    private RattedInAccountFactory rattedInAccountFactory;

    @BeforeEach
    public void setUp() {
        rattedInAccountFactory = new RattedInAccountFactory(rattedInIDFactory);
    }

    @Test
    public void givenAgentCharacterType_whenCreatingAnRattedInAccount_thenRattedInAccountStatusIsN_A() {

        RattedInAccount rattedInAccount = rattedInAccountFactory.createAccount(A_USERNAME, CHARACTER_ID, RattedInAccountStatus.N_A);

        assertEquals(RattedInAccountStatus.N_A, rattedInAccount.getAccountStatus());
    }

    @Test
    public void givenLawyerCharacterType_whenCreatingAnRattedInAccount_thenRattedInAccountStatusIsOPEN_TO_WORK() {

        RattedInAccount rattedInAccount = rattedInAccountFactory.createAccount(A_USERNAME, CHARACTER_ID, RattedInAccountStatus.OPEN_TO_WORK);

        assertEquals(RattedInAccountStatus.OPEN_TO_WORK, rattedInAccount.getAccountStatus());
    }
}
