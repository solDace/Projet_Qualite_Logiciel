package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

public abstract class Character {

    private static final Money REALITY_SHOW_MONEY_TO_WIN = new Money(50000);
    protected final WorkAvailability workAvailability;
    protected final BankAccount bankAccount;
    protected final Reputation reputation;
    protected boolean promoteNextMovie;
    private final CharacterID characterID;
    private final String name;
    private final CharacterType characterType;
    private final Money salary;
    private final LinkedList<Lawsuit> activeLawsuits = new LinkedList<>();
    private boolean hasSentRumors;
    private Lawyer lawyer;

    public Character(CharacterID characterID, String name, CharacterType characterType, BankAccount bankAccount, Reputation reputation, Money salary,
                     WorkAvailability workAvailability) {
        this.characterID = characterID;
        this.name = name;
        this.characterType = characterType;
        this.bankAccount = bankAccount;
        this.reputation = reputation;
        this.salary = salary;
        this.workAvailability = workAvailability;
        this.hasSentRumors = false;
        this.promoteNextMovie = false;
        this.lawyer = null;
    }

    public boolean isUnderEliminationThreshold() {
        boolean isReputationUnderThreshold = reputation.isUnderEliminationThreshold();
        boolean isEmptyBankBalance = bankAccount.bankBalanceLowerThanOrEqualToBalanceThreshold();
        boolean isHamstagramAccountUnderElimination = getHamstagramAccount().isPresent() && getHamstagramAccount().get().isUnderEliminationThreshold();

        return isReputationUnderThreshold || isEmptyBankBalance || isHamstagramAccountUnderElimination;
    }

    public void eliminate() {
        freeLawyer();
    }

    public Optional<Lawyer> getLawyer() {
        return Optional.ofNullable(lawyer);
    }

    public abstract void hireBestLawyer(Set<Lawyer> allLawyers, LawyerSelector lawyerSelector);

    public abstract Optional<HamstagramAccount> getHamstagramAccount();

    public abstract Optional<RattedInAccount> getRattedInAccount();

    protected void hireLawyer(Lawyer newLawyer) {
        lawyer = newLawyer;
        newLawyer.startWorkingForClient(this);
        activeLawsuits.forEach(lawsuit -> lawsuit.setLawyerName(newLawyer.getName()));
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public String getName() {
        return name;
    }

    public Money getBankBalance() {
        return bankAccount.getBankBalance();
    }

    public int getReputationPoints() {
        return reputation.getPoints();
    }

    public Money getSalary() {
        return new Money(salary.floatValue());
    }

    public CharacterID getCharacterID() {
        return characterID;
    }

    public void loseReputation(int qty) {
        reputation.losePoints(qty);
    }

    public void loseMoney(Money amount) {
        bankAccount.withdraw(amount);
    }

    public void gainSalary() {
        gainMoney(getSalary());
    }

    public void gainMoney(Money amount) {
        bankAccount.deposit(amount);
    }

    public void nextTurn() {
        workAvailability.nextTurn();
    }

    public boolean hasAlreadySentARumor() {
        return hasSentRumors;
    }

    public void revealScandal(Character targetCharacter) {
        if (reputation.canRevealScandal()) {
            targetCharacter.receiveScandal();
        }
    }

    protected void receiveScandal() {
        reputation.receiveScandal();
        workAvailability.receiveScandal();
    }

    public void gossipAbout(Character targetCharacter) {
        hasSentRumors = true;
        targetCharacter.receiveGossip();
    }

    protected void receiveGossip() {
        reputation.receiveRumor();
    }

    public void participateToRealityShow() {
        gainMoney(REALITY_SHOW_MONEY_TO_WIN);
        reputation.participateToRealityShow();
        workAvailability.participateToRealityShow();
    }

    public abstract void promoteMovie();

    public void complaintForHarassment(Character targetCharacter) {
        if (!hasAlreadySentARumor()) {
            targetCharacter.receiveHarassmentAccusation();
        }
    }

    public void receiveHarassmentAccusation() {
        reputation.receiveHarassmentAccusation();
        workAvailability.receiveHarassmentAccusation();
    }

    public boolean isAvailable() {
        return workAvailability.isOpenToWork() && activeLawsuits.isEmpty();
    }

    private boolean isRepresentedByALawyer() {
        return lawyer != null;
    }

    protected void payFees(BankAccount receiverBankAccount, Money feesAmount) {
        bankAccount.transferMoney(receiverBankAccount, feesAmount);
    }

    public int getNbLawsuits() {
        return activeLawsuits.size();
    }

    public void receiveLawsuit(Lawsuit lawsuit) {
        if (isRepresentedByALawyer()) {
            lawsuit.setLawyerName(lawyer.getName());
        }

        activeLawsuits.add(lawsuit);
    }

    protected Lawsuit resolveLawsuit() {
        Lawsuit lawsuit = activeLawsuits.pop();

        if (isLawyerJobFinished()) {
            freeLawyer();
        }

        return lawsuit;
    }

    private boolean isLawyerJobFinished() {
        return isRepresentedByALawyer() && activeLawsuits.isEmpty();
    }

    protected void freeLawyer() {
        getLawyer().ifPresent(lawyer -> {
            lawyer.finishWorkingForClient();
            this.lawyer = null;
        });
    }
}
