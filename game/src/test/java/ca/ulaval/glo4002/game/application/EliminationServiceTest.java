package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EliminationServiceTest {
    private static final String CHARACTER_NAME = "Sally";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);

    @Mock
    private Character characterNotToEliminate, characterToEliminate;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private HamstagramRepository hamstagramRepository;
    @Mock
    private RattedInRepository rattedInRepository;
    @Mock
    private LawsuitRepository lawsuitRepository;

    private EliminationService eliminationService;

    @BeforeEach
    public void setUp() {
        eliminationService = new EliminationService(characterRepository, hamstagramRepository, rattedInRepository, lawsuitRepository);
    }

    @Test
    public void givenACharacterThatShouldBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenCharacterIsEliminated() {
        givenACharacterThatShouldBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(characterToEliminate).eliminate();
    }

    @Test
    public void givenACharacterThatShouldNotBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenCharacterIsNotEliminated() {
        givenACharacterThatShouldNotBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(characterNotToEliminate, Mockito.never()).eliminate();
    }

    @Test
    public void givenACharacterThatShouldBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenCharacterIsDeleted() {
        givenACharacterThatShouldBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(characterToEliminate).eliminate();
    }

    @Test
    public void givenACharacterThatShouldNotBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenCharacterIsNotDeleted() {
        givenACharacterThatShouldNotBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(characterRepository, Mockito.never()).deleteCharacter(CHARACTER_ID);
    }

    @Test
    public void givenACharacterThatShouldBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenHisHamstagramAccountIsDeleted() {
        givenACharacterThatShouldBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(hamstagramRepository).deleteHamstagramAccountsByCharacterID(CHARACTER_ID);
    }

    @Test
    public void givenACharacterThatShouldNotBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenHisHamstagramAccountIsNotDeleted() {
        givenACharacterThatShouldNotBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(hamstagramRepository, Mockito.never()).deleteHamstagramAccountsByCharacterID(CHARACTER_ID);
    }

    @Test
    public void givenACharacterThatShouldBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenHisRattedInAccountIsDeleted() {
        givenACharacterThatShouldBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(rattedInRepository).deleteRattedInAccountByCharacterID(CHARACTER_ID);
    }

    @Test
    public void givenACharacterThatShouldNotBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenHisRattedInAccountIsNotDeleted() {
        givenACharacterThatShouldNotBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(rattedInRepository, Mockito.never()).deleteRattedInAccountByCharacterID(CHARACTER_ID);
    }

    @Test
    public void givenACharacterToBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenAllHisLawsuitsAreDeleted() {
        givenACharacterThatShouldBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(lawsuitRepository).deleteAllCharacterLawsuits(CHARACTER_NAME);
    }

    @Test
    public void givenACharacterNotToBeEliminated_whenDeleteCharactersAndEverythingLinkedToThem_thenHisLawsuitsAreNotDeleted() {
        givenACharacterThatShouldNotBeEliminated();

        eliminationService.deleteCharactersAndEverythingLinkedToThem();

        Mockito.verify(lawsuitRepository, Mockito.never()).deleteAllCharacterLawsuits(CHARACTER_NAME);
    }

    private void givenACharacterThatShouldBeEliminated() {
        Mockito.when(characterToEliminate.getName()).thenReturn(CHARACTER_NAME);
        Mockito.when(characterToEliminate.getCharacterID()).thenReturn(CHARACTER_ID);
        Mockito.when(characterToEliminate.isUnderEliminationThreshold()).thenReturn(true);
        Mockito.when(characterRepository.getAllCharacters()).thenReturn(Set.of(characterToEliminate));
    }

    private void givenACharacterThatShouldNotBeEliminated() {
        Mockito.when(characterNotToEliminate.isUnderEliminationThreshold()).thenReturn(false);
        Mockito.when(characterRepository.getAllCharacters()).thenReturn(Set.of(characterNotToEliminate));
    }
}
