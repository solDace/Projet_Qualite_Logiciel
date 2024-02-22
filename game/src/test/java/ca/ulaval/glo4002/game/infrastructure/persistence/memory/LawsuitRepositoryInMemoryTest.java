package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LawsuitRepositoryInMemoryTest {

    private static final String CHARACTER_NAME = "Bob", ANOTHER_CHARACTER_NAME = "Charles";

    private final Lawsuit lawsuit = new Lawsuit(4, CHARACTER_NAME, CharacterInteractionActionType.GOSSIP);
    private final Lawsuit anotherLawsuit = new Lawsuit(7, ANOTHER_CHARACTER_NAME, CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW);

    private LawsuitRepositoryInMemory lawsuitRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        lawsuitRepositoryInMemory = new LawsuitRepositoryInMemory();
        lawsuitRepositoryInMemory.deleteAllLawsuits();
    }

    @Test
    public void whenSaveALawsuit_thenItIsAddedToTheRepository() {

        lawsuitRepositoryInMemory.saveLawsuit(lawsuit);

        assertTrue(lawsuitRepositoryInMemory.getAllLawsuits().contains(lawsuit));
    }

    @Test
    public void givenALawsuitInTheRepository_whenDeleteThisLawsuit_thenItIsRemovedFromTheRepository() {
        lawsuitRepositoryInMemory.saveLawsuit(lawsuit);

        lawsuitRepositoryInMemory.deleteLawsuit(lawsuit);

        assertFalse(lawsuitRepositoryInMemory.getAllLawsuits().contains(lawsuit));
    }

    @Test
    public void givenSeveralLawsuitsInTheRepository_whenDeleteAllLawsuits_thenAllLawsuitsAreRemovedFromTheRepository() {
        lawsuitRepositoryInMemory.saveLawsuit(lawsuit);
        lawsuitRepositoryInMemory.saveLawsuit(anotherLawsuit);

        lawsuitRepositoryInMemory.deleteAllLawsuits();

        assertTrue(lawsuitRepositoryInMemory.getAllLawsuits().isEmpty());
    }

    @Test
    public void givenSeveralLawsuitsInTheRepository_whenDeleteAllCharacterLawsuits_thenTheLawsuitsOfTheCharacterAreRemovedFromTheRepository() {
        lawsuitRepositoryInMemory.saveLawsuit(lawsuit);
        lawsuitRepositoryInMemory.saveLawsuit(anotherLawsuit);

        lawsuitRepositoryInMemory.deleteAllCharacterLawsuits(CHARACTER_NAME);

        assertEquals(List.of(anotherLawsuit), lawsuitRepositoryInMemory.getAllLawsuits());
    }
}
