package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Agent extends Character implements CharacterOnRattedIn {

    private static final int REALITY_SHOW_FOLLOWERS_TO_WIN = 8000;

    private final Set<Actor> actors = new HashSet<>();

    private final HamstagramAccount hamstagramAccount;
    private final RattedInAccount rattedInAccount;

    public Agent(CharacterID characterID, String name, BankAccount bankAccount, Reputation reputation, Money salary, WorkAvailability workAvailability,
                 HamstagramAccount hamstagramAccount, RattedInAccount rattedInAccount
    ) {
        super(characterID, name, CharacterType.AGENT, bankAccount, reputation, salary, workAvailability);

        this.hamstagramAccount = hamstagramAccount;
        this.rattedInAccount = rattedInAccount;
    }

    @Override
    public Optional<HamstagramAccount> getHamstagramAccount() {
        return Optional.of(hamstagramAccount);
    }

    @Override
    public Optional<RattedInAccount> getRattedInAccount() {
        return Optional.of(rattedInAccount);
    }

    public void signWithActor(Actor actor) {
        if (isAvailable()) {
            actors.add(actor);
        }
    }

    public Set<Actor> getAllActors() {
        return actors;
    }

    @Override
    public boolean isContactAcceptable(Character contact) {
        return contact.getReputationPoints() >= getReputationPoints();
    }

    @Override
    public void eliminate() {
        super.eliminate();
        removeItselfFromItsContacts();
        loseAllClients();
    }

    @Override
    public void removeItselfFromItsContacts() {
        rattedInAccount.removeItselfFromItsContactsNetwork();
    }

    public void sendPropositionToChosenActors(Set<Actor> actors) {
        if (isAvailable()) {
            Set<Actor> actorsReceivingRepresentationPropositions = chooseActorsByCriteria(actors);
            for (Actor actor : actorsReceivingRepresentationPropositions) {
                actor.receiveRepresentationPropositionFromAgent(this);
            }
        }
    }

    private Set<Actor> chooseActorsByCriteria(Set<Actor> actors) {
        return actors.stream()
                     .filter(actor -> !actor.isRepresentedByAnAgent())
                     .filter(actor -> actor.getReputationPoints() >= getReputationPoints())
                     .collect(Collectors.toSet());
    }

    public void removeActor(CharacterID actorId) {
        actors.removeIf(actor -> actor.getCharacterID().equals(actorId));
    }

    @Override
    protected void receiveScandal() {
        super.receiveScandal();
        hamstagramAccount.receiveScandal();
        loseAllClients();
    }

    @Override
    public void participateToRealityShow() {
        super.participateToRealityShow();
        addFollowers(REALITY_SHOW_FOLLOWERS_TO_WIN);
        loseAllClients();
    }

    @Override
    public void receiveHarassmentAccusation() {
        super.receiveHarassmentAccusation();

        loseAllClients();
        hamstagramAccount.receiveHarassmentAccusation();
        rattedInAccount.removeItselfFromItsContactsNetwork();
    }

    public void claimFees() {
        actors.forEach(actor -> actor.payFees(bankAccount, getSalary()));
    }

    private void loseAllClients() {
        actors.forEach(Actor::removeAgent);
        actors.clear();
    }

    @Override
    public void receiveLawsuit(Lawsuit lawsuit) {
        super.receiveLawsuit(lawsuit);
        loseAllClients();
    }

    @Override
    public void hireBestLawyer(Set<Lawyer> allLawyers, LawyerSelector lawyerSelector) {
        if (getLawyer().isEmpty()) {
            Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(allLawyers, rattedInAccount);
            lawyerFound.ifPresent(this::hireLawyer);
        }
    }

    @Override
    public void promoteMovie() {
        if (hamstagramAccount.canPromoteMovie() && reputation.canPromoteMovie()) {
            promoteNextMovie = true;
        }
    }

    public Money boostBoxOffice(Money movieGains) {
        Money finalGains = movieGains;

        if (promoteNextMovie) {
            finalGains = new Money(movieGains.floatValue() * 2);
            promoteNextMovie = false;
        }

        return finalGains;
    }

    public int getNbFollowers() {
        return hamstagramAccount.getNbFollowers();
    }

    public void addFollowers(int followers) {
        hamstagramAccount.addFollowers(followers);
    }
}
