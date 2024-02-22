package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterInteractionServiceTest {

    private static final String SENDING_CHARACTER = "1234";
    private static final String RECEIVING_CHARACTER = "5678";
    private static final CharacterID SENDING_CHARACTER_ID = new CharacterID(SENDING_CHARACTER);
    private static final CharacterID RECEIVING_CHARACTER_ID = new CharacterID(RECEIVING_CHARACTER);
    private static final int A_TURN_NUMBER = 2;

    @Mock
    private Character sendingCharacter, receivingCharacter;

    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private LawsuitService lawsuitService;
    @Mock
    private CharacterIDFactory characterIDFactory;

    private CharacterInteractionService characterInteractionService;

    @BeforeEach
    public void setUp() {
        characterInteractionService = new CharacterInteractionService(characterRepository, lawsuitService, characterIDFactory);
        givenASendingCharacterAndAReceivingCharacter();
    }

    @Test
    public void givenStartFalseRumorCharacterInteraction_whenExecuteCharacterInteraction_thenSendingCharacterGossipAboutReceivingCharacter() {
        CharacterInteractionActionType actionType = CharacterInteractionActionType.GOSSIP;
        Mockito.when(characterIDFactory.create(SENDING_CHARACTER)).thenReturn(SENDING_CHARACTER_ID);
        Mockito.when(characterIDFactory.create(RECEIVING_CHARACTER)).thenReturn(RECEIVING_CHARACTER_ID);

        characterInteractionService.executeCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, actionType, A_TURN_NUMBER);

        Mockito.verify(sendingCharacter).gossipAbout(receivingCharacter);
    }

    @Test
    public void givenComplaintCharacterInteraction_whenExecuteCharacterInteraction_thenSendingCharacterComplainAboutReceivingCharacter() {
        CharacterInteractionActionType actionType = CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT;
        Mockito.when(characterIDFactory.create(SENDING_CHARACTER)).thenReturn(SENDING_CHARACTER_ID);
        Mockito.when(characterIDFactory.create(RECEIVING_CHARACTER)).thenReturn(RECEIVING_CHARACTER_ID);

        characterInteractionService.executeCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, actionType, A_TURN_NUMBER);

        Mockito.verify(sendingCharacter).complaintForHarassment(receivingCharacter);
    }

    @Test
    public void givenPromoteMovieCharacterInteraction_whenExecuteCharacterInteraction_thenSendingCharacterPromoteHisMovie() {
        CharacterInteractionActionType actionType = CharacterInteractionActionType.PROMOTE_MOVIE;
        Mockito.when(characterIDFactory.create(SENDING_CHARACTER)).thenReturn(SENDING_CHARACTER_ID);
        Mockito.when(characterIDFactory.create(RECEIVING_CHARACTER)).thenReturn(RECEIVING_CHARACTER_ID);

        characterInteractionService.executeCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, actionType, A_TURN_NUMBER);

        Mockito.verify(sendingCharacter).promoteMovie();
    }

    @Test
    public void givenRevealScandalCharacterInteraction_whenExecuteCharacterInteraction_thenSendingCharacterRevealScandalAboutReceivingCharacter() {
        CharacterInteractionActionType actionType = CharacterInteractionActionType.REVEAL_SCANDAL;
        Mockito.when(characterIDFactory.create(SENDING_CHARACTER)).thenReturn(SENDING_CHARACTER_ID);
        Mockito.when(characterIDFactory.create(RECEIVING_CHARACTER)).thenReturn(RECEIVING_CHARACTER_ID);

        characterInteractionService.executeCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, actionType, A_TURN_NUMBER);

        Mockito.verify(sendingCharacter).revealScandal(receivingCharacter);
    }

    @Test
    public void givenRealityShowCharacterInteraction_whenExecuteCharacterInteraction_thenSendingCharacterParticipateToRealityShow() {
        CharacterInteractionActionType actionType = CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW;
        Mockito.when(characterIDFactory.create(SENDING_CHARACTER)).thenReturn(SENDING_CHARACTER_ID);
        Mockito.when(characterIDFactory.create(RECEIVING_CHARACTER)).thenReturn(RECEIVING_CHARACTER_ID);

        characterInteractionService.executeCharacterInteraction(SENDING_CHARACTER, RECEIVING_CHARACTER, actionType, A_TURN_NUMBER);

        Mockito.verify(sendingCharacter).participateToRealityShow();
    }

    private void givenASendingCharacterAndAReceivingCharacter() {
        Mockito.when(characterRepository.getCharacter(SENDING_CHARACTER_ID)).thenReturn(Optional.of(sendingCharacter));
        Mockito.when(characterRepository.getCharacter(RECEIVING_CHARACTER_ID)).thenReturn(Optional.of(receivingCharacter));
    }
}
