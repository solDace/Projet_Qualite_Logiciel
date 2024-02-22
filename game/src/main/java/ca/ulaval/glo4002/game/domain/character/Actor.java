package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Actor extends Character {

    private static final int REALITY_SHOW_FOLLOWERS_TO_WIN = 20000;

    private final Set<Agent> potentialAgents = new HashSet<>();
    private final HamstagramAccount hamstagramAccount;
    private Agent agent;
    private Movie currentMovie;

    public Actor(CharacterID characterID,
                 String name,
                 BankAccount bankAccount,
                 Reputation reputation,
                 Money salary,
                 WorkAvailability workAvailability,
                 HamstagramAccount hamstagramAccount
    ) {
        super(characterID, name, CharacterType.ACTOR, bankAccount, reputation, salary, workAvailability);

        this.hamstagramAccount = hamstagramAccount;
    }

    public void chooseFromPotentialAgents(Hamstrology hamstrology) {
        if (!isRepresentedByAnAgent() && !potentialAgents.isEmpty()) {
            Agent choosenAgent = hamstrology.chooseAnAgent(getName(), Set.copyOf(potentialAgents)).orElse(null);
            signWithAgent(choosenAgent);
        }
        potentialAgents.clear();
    }

    public void signWithAgent(Agent newAgent) {
        if (newAgent == null || agent != null) {
            return;
        }

        agent = newAgent;
        newAgent.signWithActor(this);
    }

    public boolean isRepresentedByAnAgent() {
        return agent != null;
    }

    public Optional<Agent> getAgent() {
        return Optional.ofNullable(agent);
    }

    @Override
    public void hireBestLawyer(Set<Lawyer> allLawyers, LawyerSelector lawyerSelector) {
        if (isRepresentedByAnAgent() && getLawyer().isEmpty()) {
            agent.getRattedInAccount().ifPresent(agentRattedInAccount -> {
                Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(allLawyers, agentRattedInAccount);
                lawyerFound.ifPresent(this::hireLawyer);
            });
        }
    }

    @Override
    protected void receiveScandal() {
        super.receiveScandal();
        hamstagramAccount.receiveScandal();
        removeFromMovie();
    }

    @Override
    public void participateToRealityShow() {
        super.participateToRealityShow();
        hamstagramAccount.addFollowers(REALITY_SHOW_FOLLOWERS_TO_WIN);
        removeFromMovie();
    }

    @Override
    public void receiveHarassmentAccusation() {
        super.receiveHarassmentAccusation();
        hamstagramAccount.receiveHarassmentAccusation();
        removeFromMovie();
    }

    @Override
    public void eliminate() {
        super.eliminate();
        removeFromMovie();
        getAgent().ifPresent(agent -> agent.removeActor(getCharacterID()));
        agent = null;
    }

    private void removeFromMovie() {
        if (currentMovie != null) {
            currentMovie.removeFromCasting(getCharacterID());
            currentMovie = null;
        }
    }

    public void sendBonusToAgent(Money bonus) {
        if (isRepresentedByAnAgent()) {
            agent.gainMoney(bonus);
        }
    }

    public void turnAMovie(Movie movie) {
        if (isAvailable()) {
            currentMovie = movie;
            workAvailability.setCurrentlyWorking(true);
        }
    }

    public void freeFromCurrentMovie() {
        currentMovie = null;
        workAvailability.setCurrentlyWorking(false);
    }

    public double getFollowersByDollarRatio() {
        return hamstagramAccount.getNbFollowers() / getSalary().doubleValue();
    }

    public void removeAgent() {
        agent = null;
    }

    public void receiveRepresentationPropositionFromAgent(Agent agent) {
        potentialAgents.add(agent);
    }

    public Money boostBoxOffice(Money movieGains) {
        Money finalGains = movieGains;
        Optional<Agent> representingAgent = getAgent();

        if (promoteNextMovie) {
            finalGains = new Money(movieGains.floatValue() * 2);
            promoteNextMovie = false;
        }

        if (representingAgent.isPresent()) {
            finalGains = representingAgent.get().boostBoxOffice(finalGains);
        }

        return finalGains;
    }

    @Override
    public void receiveLawsuit(Lawsuit lawsuit) {
        super.receiveLawsuit(lawsuit);
        removeFromMovie();
    }

    @Override
    public void promoteMovie() {
        if (hamstagramAccount.canPromoteMovie() && reputation.canPromoteMovie()) {
            promoteNextMovie = true;
        }
    }

    public void addFollowers(int followers) {
        hamstagramAccount.addFollowers(followers);
    }

    public int getNbFollowers() {
        return hamstagramAccount.getNbFollowers();
    }

    @Override
    public Optional<HamstagramAccount> getHamstagramAccount() {
        return Optional.of(hamstagramAccount);
    }

    @Override
    public Optional<RattedInAccount> getRattedInAccount() {
        return Optional.empty();
    }
}
