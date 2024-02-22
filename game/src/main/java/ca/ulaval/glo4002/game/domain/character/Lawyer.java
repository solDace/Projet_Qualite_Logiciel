package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.Optional;
import java.util.Set;

public class Lawyer extends Character implements CharacterOnRattedIn {
    private final RattedInAccount rattedInAccount;
    private Character client;

    public Lawyer(CharacterID characterID, String name, BankAccount bankAccount, Reputation reputation, Money salary, WorkAvailability workAvailability,
                  RattedInAccount rattedInAccount) {
        super(characterID, name, CharacterType.LAWYER, bankAccount, reputation, salary, workAvailability);

        this.rattedInAccount = rattedInAccount;
    }

    @Override
    public Optional<RattedInAccount> getRattedInAccount() {
        return Optional.of(rattedInAccount);
    }

    @Override
    public Optional<HamstagramAccount> getHamstagramAccount() {
        return Optional.empty();
    }

    public boolean isInContacts(RattedInAccount otherRattedInAccount) {
        return rattedInAccount.isInContacts(otherRattedInAccount);
    }

    @Override
    public boolean isContactAcceptable(Character contact) {
        return true;
    }

    @Override
    public void eliminate() {
        super.eliminate();
        removeItselfFromItsContacts();
        getClient().ifPresent(Character::freeLawyer);
    }

    @Override
    public void removeItselfFromItsContacts() {
        rattedInAccount.removeItselfFromItsContactsNetwork();
    }

    public Optional<Character> getClient() {
        return Optional.ofNullable(client);
    }

    public void startWorkingForClient(Character client) {
        this.client = client;
        rattedInAccount.setToBusy();
        workAvailability.setCurrentlyWorking(true);
    }

    public void finishWorkingForClient() {
        client = null;
        rattedInAccount.setToOpenToWork();
        workAvailability.setCurrentlyWorking(false);
    }

    public void claimFees() {
        getClient().ifPresent(client -> client.payFees(bankAccount, getSalary()));
    }

    public Optional<Lawsuit> settleLawsuitForClient() {
        return getClient().map(Character::resolveLawsuit);
    }

    @Override
    public void receiveLawsuit(Lawsuit lawsuit) {
        super.receiveLawsuit(lawsuit);
        finishWorkingForClient();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() && rattedInAccount.isOpenToWork();
    }

    @Override
    public void hireBestLawyer(Set<Lawyer> allLawyers, LawyerSelector lawyerSelector) {
        if (getLawyer().isEmpty()) {
            Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(allLawyers, rattedInAccount);
            lawyerFound.ifPresent(this::hireLawyer);
        }
    }

    @Override
    public void receiveHarassmentAccusation() {
        super.receiveHarassmentAccusation();
        rattedInAccount.removeItselfFromItsContactsNetwork();
    }

    @Override
    public void promoteMovie() {
        promoteNextMovie = false;
    }
}
