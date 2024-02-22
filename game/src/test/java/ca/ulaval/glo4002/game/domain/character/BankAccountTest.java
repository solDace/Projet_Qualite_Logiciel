package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.money.Money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BankAccountTest {

    private static final Money INITIAL_BANK_BALANCE = new Money(5043.0f);
    private static final Money AMOUNT_OF_MONEY = new Money(157.0f);
    private static final Money AMOUNT_OF_MONEY_UNDER_BALANCE_AMOUNT = INITIAL_BANK_BALANCE.subtract(AMOUNT_OF_MONEY);
    private static final Money AMOUNT_OF_MONEY_OVER_BALANCE_AMOUNT = INITIAL_BANK_BALANCE.add(AMOUNT_OF_MONEY);
    private static final Money ZERO = new Money(0);
    private static final Money AMOUNT_OF_MONEY_OVER_BALANCE_THRESHOLD = new Money(157.0f);

    @Mock
    private BankAccount anotherBankAccount;

    private BankAccount bankAccount;

    @BeforeEach
    public void setup() {
        bankAccount = new BankAccount(INITIAL_BANK_BALANCE);
    }

    @Test
    public void whenWithdrawAnAmountOfMoneyUnderBalanceAmount_thenBankBalanceLosesTheGivenAmountOfMoney() {

        bankAccount.withdraw(AMOUNT_OF_MONEY_UNDER_BALANCE_AMOUNT);

        assertEquals(INITIAL_BANK_BALANCE.subtract(AMOUNT_OF_MONEY_UNDER_BALANCE_AMOUNT), bankAccount.getBankBalance());
    }

    @Test
    public void whenWithdrawAnAmountOfMoneyOverBalanceAmount_thenBankBalanceBecomesEmpty() {

        bankAccount.withdraw(AMOUNT_OF_MONEY_OVER_BALANCE_AMOUNT);

        assertEquals(ZERO, bankAccount.getBankBalance());
    }

    @Test
    public void whenWithdrawAnAmountOfMoney_thenWithdrawnAmountIsReturned() {

        Money returnedAmount = bankAccount.withdraw(AMOUNT_OF_MONEY);

        assertEquals(AMOUNT_OF_MONEY, returnedAmount);
    }

    @Test
    public void whenDeposit_thenBankBalanceGainsTheDepositedAmountOfMoney() {

        bankAccount.deposit(AMOUNT_OF_MONEY);

        assertEquals(INITIAL_BANK_BALANCE.add(AMOUNT_OF_MONEY), bankAccount.getBankBalance());
    }

    @Test
    public void whenTransferMoneyUnderBankBalance_thenTheOtherBankAccountReceivesTheGivenAmountOfMoney() {

        bankAccount.transferMoney(anotherBankAccount, AMOUNT_OF_MONEY_UNDER_BALANCE_AMOUNT);

        Mockito.verify(anotherBankAccount).deposit(AMOUNT_OF_MONEY_UNDER_BALANCE_AMOUNT);
    }

    @Test
    public void whenTransferMoneyOverBankBalance_thenTheOtherBankAccountReceivesTheRemainingAmount() {

        bankAccount.transferMoney(anotherBankAccount, AMOUNT_OF_MONEY_OVER_BALANCE_AMOUNT);

        Mockito.verify(anotherBankAccount).deposit(INITIAL_BANK_BALANCE);
    }

    @Test
    public void givenBankBalanceUnderOrEqualBalanceThreshold_whenBankBalanceLowerThanOrEqualToBalanceThreshold_thenReturnTrue() {
        BankAccount bankAccount = new BankAccount(ZERO);

        assertTrue(bankAccount.bankBalanceLowerThanOrEqualToBalanceThreshold());
    }

    @Test
    public void givenBankBalanceOverBalanceThreshold_whenBankBalanceLowerThanOrEqualToBalanceThreshold_thenReturnFalse() {
        BankAccount bankAccount = new BankAccount(AMOUNT_OF_MONEY_OVER_BALANCE_THRESHOLD);

        assertFalse(bankAccount.bankBalanceLowerThanOrEqualToBalanceThreshold());
    }
}
