package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;

import java.util.Optional;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class CharacterInteractionService {

    private final CharacterRepository characterRepository;
    private final LawsuitService lawsuitService;
    private final CharacterIDFactory characterIDFactory;

    @Inject
    public CharacterInteractionService(CharacterRepository characterRepository, LawsuitService lawsuitService, CharacterIDFactory characterIDFactory) {
        this.characterRepository = characterRepository;
        this.lawsuitService = lawsuitService;
        this.characterIDFactory = characterIDFactory;
    }

    public void executeCharacterInteraction(String characterSender, String characterReceiver, CharacterInteractionActionType actionType, int turnNumber) {
        Optional<Character> receivingCharacter = findCharacter(characterReceiver);
        Optional<Character> sendingCharacter = findCharacter(characterSender);

        if (sendingCharacter.isPresent()) {
            switch (actionType) {
                case PARTICIPATE_TO_REALITY_SHOW -> sendingCharacter.get().participateToRealityShow();
                case PROMOTE_MOVIE -> sendingCharacter.get().promoteMovie();
                case GOSSIP -> receivingCharacter.ifPresent(character -> {
                    sendingCharacter.get().gossipAbout(character);
                    lawsuitService.addLawsuit(turnNumber, sendingCharacter.get().getName(), actionType);
                });
                case REVEAL_SCANDAL -> receivingCharacter.ifPresent(character -> {
                    sendingCharacter.get().revealScandal(character);
                    lawsuitService.addLawsuit(turnNumber, sendingCharacter.get().getName(), actionType);
                });
                case COMPLAINT_FOR_HARASSMENT -> receivingCharacter.ifPresent(character -> {
                    sendingCharacter.get().complaintForHarassment(character);
                    lawsuitService.addLawsuit(turnNumber, sendingCharacter.get().getName(), actionType);
                });
            }
        }
    }

    private Optional<Character> findCharacter(String characterName) {
        Optional<Character> character = Optional.empty();

        if (characterName != null) {
            CharacterID receivingCharacterID = characterIDFactory.create(characterName);
            character = characterRepository.getCharacter(receivingCharacterID);
        }

        return character;
    }
}
