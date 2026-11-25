package model;

import exceptions.DailyLimitExceededException;
import exceptions.InvalidAmountException;

public class CheckingAccount extends BankAccount {

    private double dailyWithdrawalLimit = 300;

    public CheckingAccount(String customerName, String userId, String password) {
        super(customerName, userId, password);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit <= 0) {
            throw new InvalidAmountException("Daily withdrawal limit must be greater than 0");
        }
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > dailyWithdrawalLimit) {
            throw new DailyLimitExceededException(
                    "Amount exceeds the daily withdrawal limit of " + dailyWithdrawalLimit);
        }
        super.withdraw(amount);
    }
}
