package ca.ulaval.glo4002.game.domain.character.factory;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.BankAccount;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.character.Reputation;
import ca.ulaval.glo4002.game.domain.character.WorkAvailability;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInAccountFactory;

import jakarta.inject.Inject;

public class CharacterFactory {

    private static final Money CHARACTER_INITIAL_BANK_BALANCE = new Money(1000);
    private static final int CHARACTER_INITIAL_REPUTATION_POINTS = 75;
    private final HamstagramAccountFactory hamstagramAccountFactory;
    private final RattedInAccountFactory rattedInAccountFactory;

    @Inject
    public CharacterFactory(HamstagramAccountFactory hamstagramAccountFactory, RattedInAccountFactory rattedInAccountFactory) {
        this.hamstagramAccountFactory = hamstagramAccountFactory;
        this.rattedInAccountFactory = rattedInAccountFactory;
    }

    public Character create(CharacterID characterID, CharacterType type, String name, Money salary) {
        Reputation reputation = new Reputation(CHARACTER_INITIAL_REPUTATION_POINTS);
        BankAccount bankAccount = new BankAccount(CHARACTER_INITIAL_BANK_BALANCE);

        return switch (type) {
            case ACTOR -> new Actor(characterID,
                                    name,
                                    bankAccount,
                                    reputation,
                                    salary,
                                    new WorkAvailability(),
                                    hamstagramAccountFactory.createAccount(name, characterID));
            case AGENT -> new Agent(characterID,
                                    name,
                                    bankAccount,
                                    reputation,
                                    salary,
                                    new WorkAvailability(),
                                    hamstagramAccountFactory.createAccount(name, characterID),
                                    rattedInAccountFactory.createAccount(name, characterID, RattedInAccountStatus.N_A));
            case LAWYER -> new Lawyer(characterID,
                                      name,
                                      bankAccount,
                                      reputation,
                                      salary,
                                      new WorkAvailability(),
                                      rattedInAccountFactory.createAccount(name, characterID, RattedInAccountStatus.OPEN_TO_WORK));
        };
    }
}
