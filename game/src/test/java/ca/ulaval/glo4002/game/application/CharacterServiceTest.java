package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterNotFoundException;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {
    private static final Money CHARACTER_SALARY = new Money(100.0f);
    private static final int REPUTATION_LOST = 6;
    private static final Money MONEY_LOST = new Money(456.0f);
    private static final String CHARACTER_NAME = "Bob";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);
    private static final CharacterType A_CHARACTER_TYPE = CharacterType.ACTOR;

    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private CharacterFactory characterFactory;
    @Mock
    private Character aCharacter, anotherCharacter;
    @Mock
    private Actor anActor;
    @Mock
    private Agent anAgent;
    @Mock
    private Lawyer aLawyer;
    @Mock
    private HamstagramRepository hamstagramRepository;
    @Mock
    private HamstagramAccount aHamstagramAccount;
    @Mock
    private RattedInRepository rattedInRepository;
    @Mock
    private RattedInAccount aRattedInAccount;
    @Mock
    private CharacterIDFactory characterIDFactory;

    private CharacterService characterService;

    @BeforeEach
    public void setUp() {
        characterService = new CharacterService(characterFactory, characterIDFactory, characterRepository, hamstagramRepository, rattedInRepository);
    }

    @Test
    public void givenACharacterAlreadyInRepository_whenGetACharacter_thenCharacterIsFetchedFromRepository() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(aCharacter));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.getCharacter(CHARACTER_NAME);

        Mockito.verify(characterRepository).getCharacter(CHARACTER_ID);
    }

    @Test
    public void givenACharacterNotInRepository_whenGetACharacter_thenThrowsCharacterNotFoundException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.getCharacter(CHARACTER_NAME));
    }

    @Test
    public void givenACharacterAlreadyInRepository_whenGetACharacter_thenCharacterIsReturned() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(aCharacter));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        Character returnedCharacter = characterService.getCharacter(CHARACTER_NAME);

        assertEquals(aCharacter, returnedCharacter);
    }

    @Test
    public void givenACharacterNotInRepository_whenAddCharacter_thenSaveCharacterInCharacterRepository() {
        Mockito.when(characterFactory.create(CHARACTER_ID, A_CHARACTER_TYPE, CHARACTER_NAME, CHARACTER_SALARY)).thenReturn(aCharacter);
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, A_CHARACTER_TYPE);

        Mockito.verify(characterRepository).saveCharacter(aCharacter);
    }

    @Test
    public void givenACharacterAlreadyInRepository_whenAddingACharacter_thenCharacterIsNotCreated() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(aCharacter));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.ACTOR);

        Mockito.verifyNoInteractions(characterFactory);
    }

    @Test
    public void givenACharacterAlreadyInRepository_whenAddingACharacter_thenCharacterIsNotSavedInTheRepository() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(aCharacter));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.ACTOR);

        Mockito.verify(characterRepository, Mockito.never()).saveCharacter(aCharacter);
    }

    @Test
    public void givenAnActorNotInRepository_whenAddCharacter_thenSaveHamstagramAccount() {
        Mockito.when(characterFactory.create(CHARACTER_ID, CharacterType.ACTOR, CHARACTER_NAME, CHARACTER_SALARY)).thenReturn(anActor);
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.empty());
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);
        Mockito.when(anActor.getHamstagramAccount()).thenReturn(Optional.of(aHamstagramAccount));

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.ACTOR);

        Mockito.verify(hamstagramRepository).saveHamstagramAccount(anActor.getHamstagramAccount().orElseThrow());
    }

    @Test
    public void givenAnAgentNotInRepository_whenAddCharacter_thenCreateHamstagramAccount() {
        Mockito.when(characterFactory.create(CHARACTER_ID, CharacterType.AGENT, CHARACTER_NAME, CHARACTER_SALARY)).thenReturn(anAgent);
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.empty());
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);
        Mockito.when(anAgent.getHamstagramAccount()).thenReturn(Optional.of(aHamstagramAccount));

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.AGENT);

        Mockito.verify(hamstagramRepository).saveHamstagramAccount(anAgent.getHamstagramAccount().orElseThrow());
    }

    @Test
    public void givenAnActorAlreadyInRepository_whenAddCharacter_thenDoNotCreateAnHamstagramAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(anActor));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.ACTOR);

        Mockito.verifyNoInteractions(hamstagramRepository);
    }

    @Test
    public void givenAnAgentAlreadyInRepository_whenAddCharacter_thenDoNotCreateAnHamstagramAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(anAgent));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.AGENT);

        Mockito.verifyNoInteractions(hamstagramRepository);
    }

    @Test
    public void givenAnAgentNotInRepository_whenAddCharacter_thenSaveRattedInAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.empty());
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);
        Mockito.when(characterFactory.create(CHARACTER_ID, CharacterType.AGENT, CHARACTER_NAME, CHARACTER_SALARY)).thenReturn(anAgent);
        Mockito.when(anAgent.getRattedInAccount()).thenReturn(Optional.of(aRattedInAccount));

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.AGENT);

        Mockito.verify(rattedInRepository).saveRattedInAccount(anAgent.getRattedInAccount().orElseThrow());
    }

    @Test
    public void givenALawyerNotInRepository_whenAddCharacter_thenSaveRattedInAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.empty());
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);
        Mockito.when(characterFactory.create(CHARACTER_ID, CharacterType.LAWYER, CHARACTER_NAME, CHARACTER_SALARY)).thenReturn(aLawyer);
        Mockito.when(aLawyer.getRattedInAccount()).thenReturn(Optional.of(aRattedInAccount));

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.LAWYER);

        Mockito.verify(rattedInRepository).saveRattedInAccount(aLawyer.getRattedInAccount().orElseThrow());
    }

    @Test
    public void givenAnAgentAlreadyInRepository_whenAddCharacter_thenDoNotCreateARattedInAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(anAgent));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.AGENT);

        Mockito.verifyNoInteractions(rattedInRepository);
    }

    @Test
    public void givenALawyerAlreadyInRepository_whenAddCharacter_thenDoNotCreateARattedInAccount() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(aLawyer));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);

        characterService.addCharacter(CHARACTER_NAME, CHARACTER_SALARY, CharacterType.LAWYER);

        Mockito.verifyNoInteractions(rattedInRepository);
    }

    @Test
    public void givenTwoSavedCharacters_whenAllLoseReputationPoints_thenThoseCharactersLoseThatMuchReputation() {
        givenTwoSavedCharacters();

        characterService.allLoseReputationPoints(REPUTATION_LOST);

        Mockito.verify(aCharacter).loseReputation(REPUTATION_LOST);
        Mockito.verify(anotherCharacter).loseReputation(REPUTATION_LOST);
    }

    @Test
    public void givenTwoSavedCharacters_whenAllLoseMoney_thenThoseCharactersLoseThatMuchMoney() {
        givenTwoSavedCharacters();

        characterService.allLoseMoney(MONEY_LOST);

        Mockito.verify(aCharacter).loseMoney(MONEY_LOST);
        Mockito.verify(anotherCharacter).loseMoney(MONEY_LOST);
    }

    @Test
    public void givenTwoSavedCharacters_whenAllNextTurn_thenThoseCharactersCallsNextTurn() {
        givenTwoSavedCharacters();

        characterService.allNextTurn();

        Mockito.verify(aCharacter).nextTurn();
        Mockito.verify(anotherCharacter).nextTurn();
    }

    private void givenTwoSavedCharacters() {
        Mockito.when(characterRepository.getAllCharacters()).thenReturn(Set.of(aCharacter, anotherCharacter));
    }
}
