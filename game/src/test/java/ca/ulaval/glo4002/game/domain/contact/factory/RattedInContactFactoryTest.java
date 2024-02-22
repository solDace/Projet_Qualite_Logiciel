package ca.ulaval.glo4002.game.domain.contact.factory;

import ca.ulaval.glo4002.game.domain.contact.RattedInContact;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class RattedInContactFactoryTest {

    @Mock
    private RattedInAccount requestingAccount, receivingAccount;

    private RattedInContactFactory rattedInContactFactory;

    @BeforeEach
    public void setUp() {
        rattedInContactFactory = new RattedInContactFactory();
    }

    @Test
    public void givenARequestingAccountAndAReceivingAccount_whenCreatingAContact_thenCreateThatContact() {

        RattedInContact rattedInContact = rattedInContactFactory.create(requestingAccount, receivingAccount);

        assertNotNull(rattedInContact);
    }
}
