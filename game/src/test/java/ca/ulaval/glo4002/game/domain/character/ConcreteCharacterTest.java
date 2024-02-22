package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.Optional;
import java.util.Set;

public class ConcreteCharacterTest extends Character {

    public ConcreteCharacterTest(CharacterID characterID, String name, BankAccount bankAccount, Reputation reputation, Money salary,
                                 WorkAvailability workAvailability) {
        super(characterID, name, CharacterType.AGENT, bankAccount, reputation, salary, workAvailability);
    }

    @Override
    public void hireBestLawyer(Set<Lawyer> allLawyers, LawyerSelector lawyerSelector) {
    }

    @Override
    public void promoteMovie() {
    }

    @Override
    public Optional<HamstagramAccount> getHamstagramAccount() {
        return Optional.empty();
    }

    @Override
    public Optional<RattedInAccount> getRattedInAccount() {
        return Optional.empty();
    }
}
