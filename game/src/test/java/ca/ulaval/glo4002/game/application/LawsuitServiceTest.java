package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.character.LawyerSelector;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitFactory;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class LawsuitServiceTest {

    private static final int TURN_NUMBER = 2;
    private static final String CHARACTER_NAME = "Bob";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    private static final Lawsuit A_LAWSUIT = new Lawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);
    private static final Lawsuit ANOTHER_LAWSUIT = new Lawsuit(2, "Alice", CharacterInteractionActionType.GOSSIP);
    @Mock
    private Character targetCharacter, characterWithLawsuit, anotherCharacterWithLawsuit;
    @Mock
    private Lawyer aLawyer, anotherLawyer;
    @Mock
    private LawsuitRepository lawsuitRepository;
    @Mock
    private LawsuitFactory lawsuitFactory;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private CharacterIDFactory characterIDFactory;
    @Mock
    private LawyerSelector lawyerSelector;

    private LawsuitService lawsuitService;

    @BeforeEach
    public void setUp() {
        lawsuitService = new LawsuitService(lawsuitRepository, characterRepository, lawsuitFactory, characterIDFactory, lawyerSelector);
    }

    @Test
    public void givenSavedLawsuits_whenGetAllLawsuits_thenReturnLawsuitsFromRepository() {
        Mockito.when(lawsuitRepository.getAllLawsuits()).thenReturn(List.of(A_LAWSUIT, ANOTHER_LAWSUIT));

        Collection<Lawsuit> returnedLawsuits = lawsuitService.getAllLawsuits();

        assertThat(returnedLawsuits, Matchers.contains(A_LAWSUIT, ANOTHER_LAWSUIT));
    }

    @Test
    public void givenParameters_whenAddingALawsuit_thenTheLawsuitIsCreatedWithThoseParameters() {
        givenTargetCharacterForTheLawsuit();

        lawsuitService.addLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);

        Mockito.verify(lawsuitFactory).createLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);
    }

    @Test
    public void givenACreatedLawsuit_whenAddingALawsuit_thenTheLawsuitIsSaved() {
        givenTargetCharacterForTheLawsuit();
        Mockito.when(lawsuitFactory.createLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE)).thenReturn(A_LAWSUIT);

        lawsuitService.addLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);

        Mockito.verify(lawsuitRepository).saveLawsuit(A_LAWSUIT);
    }

    @Test
    public void givenTargetCharacterForTheLawsuit_whenAddingALawsuit_thenThatCharacterReceiveTheLawsuit() {
        givenTargetCharacterForTheLawsuit();
        Mockito.when(lawsuitFactory.createLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE)).thenReturn(A_LAWSUIT);

        lawsuitService.addLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);

        Mockito.verify(targetCharacter).receiveLawsuit(A_LAWSUIT);
    }

    @Test
    public void givenCharactersWithLawsuitsAndLawyers_whenHireLawyers_thenThoseCharactersSearchForTheBestLawyer() {
        givenCharactersWithLawsuits();
        Set<Lawyer> lawyers = givenLawyers();

        lawsuitService.hireLawyers();

        Mockito.verify(characterWithLawsuit).hireBestLawyer(lawyers, lawyerSelector);
        Mockito.verify(anotherCharacterWithLawsuit).hireBestLawyer(lawyers, lawyerSelector);
    }

    @Test
    public void givenLawyers_whenSettleLawsuits_thenEachLawyerSettleALawsuitForHisClient() {
        givenLawyers();

        lawsuitService.settleLawsuits();

        Mockito.verify(aLawyer).settleLawsuitForClient();
        Mockito.verify(anotherLawyer).settleLawsuitForClient();
    }

    private void givenTargetCharacterForTheLawsuit() {
        Mockito.when(characterRepository.getCharacter(CHARACTER_ID)).thenReturn(Optional.of(targetCharacter));
        Mockito.when(characterIDFactory.create(CHARACTER_NAME)).thenReturn(CHARACTER_ID);
    }

    private void givenCharactersWithLawsuits() {
        Mockito.when(characterRepository.getAllCharactersWithLawsuits())
               .thenReturn(Set.of(characterWithLawsuit, anotherCharacterWithLawsuit));
    }

    private Set<Lawyer> givenLawyers() {
        Set<Lawyer> lawyers = Set.of(aLawyer, anotherLawyer);

        Mockito.when(characterRepository.getAllLawyers())
               .thenReturn(lawyers);

        return lawyers;
    }
}
