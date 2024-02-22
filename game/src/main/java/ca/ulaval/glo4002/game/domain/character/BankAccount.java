package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.money.Money;

public class BankAccount {

    private static final Money BALANCE_THRESHOLD = new Money(0.0f);
    private Money bankBalance;

    public BankAccount(Money initialBankBalance) {
        this.bankBalance = initialBankBalance;
    }

    public Money getBankBalance() {
        return bankBalance;
    }

    public Money withdraw(Money amount) {
        Money withdrawnAmount;
        if (amount.lowerThan(bankBalance)) {
            withdrawnAmount = amount;
            bankBalance = bankBalance.subtract(amount);
        } else {
            withdrawnAmount = new Money(bankBalance.floatValue());
            bankBalance = new Money(0);
        }
        return withdrawnAmount;
    }

    public void deposit(Money amount) {
        bankBalance = bankBalance.add(amount);
    }

    public void transferMoney(BankAccount bankAccount, Money paidAmount) {
        Money withdrawnMoney = withdraw(paidAmount);
        bankAccount.deposit(withdrawnMoney);
    }

    public boolean bankBalanceLowerThanOrEqualToBalanceThreshold() {
        return bankBalance.lowerThanOrEqual(BALANCE_THRESHOLD);
    }
}
