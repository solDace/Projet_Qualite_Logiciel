package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import java.util.Set;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class EliminationService {

    private final CharacterRepository characterRepository;
    private final HamstagramRepository hamstagramRepository;
    private final RattedInRepository rattedInRepository;
    private final LawsuitRepository lawsuitRepository;

    @Inject
    public EliminationService(CharacterRepository characterRepository,
                              HamstagramRepository hamstagramRepository,
                              RattedInRepository rattedInRepository,
                              LawsuitRepository lawsuitRepository) {
        this.characterRepository = characterRepository;
        this.hamstagramRepository = hamstagramRepository;
        this.rattedInRepository = rattedInRepository;
        this.lawsuitRepository = lawsuitRepository;
    }

    public void deleteCharactersAndEverythingLinkedToThem() {
        Set<Character> charactersThatShouldBeEliminated = getCharactersThatShouldBeEliminated();

        charactersThatShouldBeEliminated.forEach(character -> {
            character.eliminate();
            characterRepository.deleteCharacter(character.getCharacterID());
            hamstagramRepository.deleteHamstagramAccountsByCharacterID(character.getCharacterID());
            rattedInRepository.deleteRattedInAccountByCharacterID(character.getCharacterID());
            lawsuitRepository.deleteAllCharacterLawsuits(character.getName());
        });
    }

    private Set<Character> getCharactersThatShouldBeEliminated() {
        return characterRepository.getAllCharacters().stream()
                                  .filter(Character::isUnderEliminationThreshold)
                                  .collect(Collectors.toSet());
    }
}
