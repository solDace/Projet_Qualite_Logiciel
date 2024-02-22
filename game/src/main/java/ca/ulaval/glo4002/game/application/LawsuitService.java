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
import java.util.Set;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class LawsuitService {

    private final LawsuitRepository lawsuitRepository;
    private final CharacterRepository characterRepository;
    private final LawsuitFactory lawsuitFactory;
    private final CharacterIDFactory characterIDFactory;
    private final LawyerSelector lawyerSelector;

    @Inject
    public LawsuitService(LawsuitRepository lawsuitRepository, CharacterRepository characterRepository, LawsuitFactory lawsuitFactory,
                          CharacterIDFactory characterIDFactory, LawyerSelector lawyerSelector) {
        this.lawsuitRepository = lawsuitRepository;
        this.characterRepository = characterRepository;
        this.lawsuitFactory = lawsuitFactory;
        this.lawyerSelector = lawyerSelector;
        this.characterIDFactory = characterIDFactory;
    }

    public Collection<Lawsuit> getAllLawsuits() {
        return lawsuitRepository.getAllLawsuits();
    }

    public void addLawsuit(int turnNumber, String characterName, CharacterInteractionActionType actionType) {
        Lawsuit lawsuit = lawsuitFactory.createLawsuit(turnNumber, characterName, actionType);

        CharacterID characterID = characterIDFactory.create(characterName);
        Character character = characterRepository.getCharacter(characterID).orElseThrow();

        character.receiveLawsuit(lawsuit);

        lawsuitRepository.saveLawsuit(lawsuit);
    }

    public void hireLawyers() {
        Set<Character> charactersWithLawsuits = characterRepository.getAllCharactersWithLawsuits();
        Set<Lawyer> lawyers = characterRepository.getAllLawyers();

        charactersWithLawsuits.forEach(character -> character.hireBestLawyer(lawyers, lawyerSelector));
    }

    public void settleLawsuits() {
        characterRepository.getAllLawyers().forEach(lawyer -> lawyer.settleLawsuitForClient().ifPresent(lawsuitRepository::deleteLawsuit));
    }
}
