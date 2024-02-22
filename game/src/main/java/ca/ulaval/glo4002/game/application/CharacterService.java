package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterNotFoundException;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import java.util.Set;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class CharacterService {

    private final CharacterFactory characterFactory;
    private final CharacterRepository characterRepository;
    private final HamstagramRepository hamstagramRepository;
    private final RattedInRepository rattedInRepository;
    private final CharacterIDFactory characterIDFactory;

    @Inject
    public CharacterService(CharacterFactory characterFactory,
                            CharacterIDFactory characterIDFactory,
                            CharacterRepository characterRepository,
                            HamstagramRepository hamstagramRepository,
                            RattedInRepository rattedInRepository
    ) {
        this.characterRepository = characterRepository;
        this.characterFactory = characterFactory;
        this.characterIDFactory = characterIDFactory;
        this.hamstagramRepository = hamstagramRepository;
        this.rattedInRepository = rattedInRepository;
    }

    public Character getCharacter(String name) {
        CharacterID characterID = characterIDFactory.create(name);
        return characterRepository.getCharacter(characterID).orElseThrow(CharacterNotFoundException::new);
    }

    public void addCharacter(String name, Money salary, CharacterType characterType) {
        CharacterID characterID = characterIDFactory.create(name);
        if (characterRepository.getCharacter(characterID).isEmpty()) {
            Character character = characterFactory.create(characterID, characterType, name, salary);

            characterRepository.saveCharacter(character);
            saveSocialMediaAccounts(character);
        }
    }

    public void allLoseReputationPoints(int nbReputationPoints) {
        Set<Character> allCharacters = characterRepository.getAllCharacters();

        allCharacters.forEach(character -> character.loseReputation(nbReputationPoints));

        allCharacters.forEach(characterRepository::saveCharacter);
    }

    public void allLoseMoney(Money moneyAmount) {
        Set<Character> allCharacters = characterRepository.getAllCharacters();

        allCharacters.forEach(character -> character.loseMoney(moneyAmount));

        allCharacters.forEach(characterRepository::saveCharacter);
    }

    public void allNextTurn() {
        Set<Character> allCharacters = characterRepository.getAllCharacters();
        allCharacters.forEach(Character::nextTurn);
    }

    private void saveSocialMediaAccounts(Character character) {
        character.getHamstagramAccount().ifPresent(hamstagramRepository::saveHamstagramAccount);
        character.getRattedInAccount().ifPresent(rattedInRepository::saveRattedInAccount);
    }
}
