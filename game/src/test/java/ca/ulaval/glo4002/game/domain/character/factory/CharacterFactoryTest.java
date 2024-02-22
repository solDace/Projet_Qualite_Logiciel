package ca.ulaval.glo4002.game.domain.character.factory;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInAccountFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CharacterFactoryTest {

    private static final Money SALARY = new Money(56651.0f);
    private static final String NAME = "Bob";
    private static final CharacterID CHARACTER_ID = new CharacterID(NAME);

    @Mock
    private HamstagramAccountFactory hamstagramAccountFactory;
    @Mock
    private HamstagramAccount hamstagramAccount;
    @Mock
    private RattedInAccountFactory rattedInAccountFactory;
    @Mock
    private RattedInAccount rattedInAccount;

    private CharacterFactory characterFactory;

    @BeforeEach
    public void setUp() {
        characterFactory = new CharacterFactory(hamstagramAccountFactory, rattedInAccountFactory);
    }

    @Test
    public void whenCreateWithCharacterTypeActor_thenHamsterIsCreated() {

        Character actor = characterFactory.create(CHARACTER_ID, CharacterType.ACTOR, NAME, SALARY);

        assertEquals(Actor.class, actor.getClass());
        assertEquals(NAME, actor.getName());
        assertEquals(SALARY, actor.getSalary());
        assertEquals(CHARACTER_ID, actor.getCharacterID());
    }

    @Test
    public void whenCreateWithCharacterTypeAgent_thenAgentIsCreated() {

        Character agent = characterFactory.create(CHARACTER_ID, CharacterType.AGENT, NAME, SALARY);

        assertEquals(Agent.class, agent.getClass());
        assertEquals(NAME, agent.getName());
        assertEquals(SALARY, agent.getSalary());
        assertEquals(CHARACTER_ID, agent.getCharacterID());
    }

    @Test
    public void whenCreateWithCharacterTypeLawyer_thenLawyerIsCreated() {

        Character lawyer = characterFactory.create(CHARACTER_ID, CharacterType.LAWYER, NAME, SALARY);

        assertEquals(Lawyer.class, lawyer.getClass());
        assertEquals(NAME, lawyer.getName());
        assertEquals(SALARY, lawyer.getSalary());
        assertEquals(CHARACTER_ID, lawyer.getCharacterID());
    }

    @Test
    public void whenCreateWithCharacterTypeActor_thenActorHamstagramAccountIsCreatedFromHamstagramAccountFactory() {
        Mockito.when(hamstagramAccountFactory.createAccount(NAME, CHARACTER_ID)).thenReturn(hamstagramAccount);

        Character agent = characterFactory.create(CHARACTER_ID, CharacterType.ACTOR, NAME, SALARY);

        assertEquals(hamstagramAccount, agent.getHamstagramAccount().orElseThrow());
    }

    @Test
    public void whenCreateWithCharacterTypeAgent_thenAgentHamstagramAccountIsCreatedFromHamstagramAccountFactory() {
        Mockito.when(hamstagramAccountFactory.createAccount(NAME, CHARACTER_ID)).thenReturn(hamstagramAccount);

        Character agent = characterFactory.create(CHARACTER_ID, CharacterType.AGENT, NAME, SALARY);

        assertEquals(hamstagramAccount, agent.getHamstagramAccount().orElseThrow());
    }

    @Test
    public void whenCreateWithCharacterTypeAgent_thenAgentRattedInAccountIsCreatedByRattedInAccountFactory() {
        Mockito.when(rattedInAccountFactory.createAccount(NAME, CHARACTER_ID, RattedInAccountStatus.N_A)).thenReturn(rattedInAccount);

        Character agent = characterFactory.create(CHARACTER_ID, CharacterType.AGENT, NAME, SALARY);

        assertEquals(rattedInAccount, agent.getRattedInAccount().orElseThrow());
    }

    @Test
    public void whenCreateWithCharacterTypeLawyer_thenLawyerRattedInAccountIsCreatedByRattedInAccountFactory() {
        Mockito.when(rattedInAccountFactory.createAccount(NAME, CHARACTER_ID, RattedInAccountStatus.OPEN_TO_WORK)).thenReturn(rattedInAccount);

        Character lawyer = characterFactory.create(CHARACTER_ID, CharacterType.LAWYER, NAME, SALARY);

        assertEquals(rattedInAccount, lawyer.getRattedInAccount().orElseThrow());
    }
}
