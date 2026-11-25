package model;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.InvalidPasswordException;

public class BankAccount {

    private final String userId;
    private final String customerName;
    private final String password;
    private double balance;

    public BankAccount(String customerName, String userId, String password) {
        validatePassword(password);
        this.customerName = customerName;
        this.userId = userId;
        this.password = password;
        this.balance = 0;
    }

    /** Пароль: щонайменше 8 символів та хоча б одна цифра. */
    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            throw new InvalidPasswordException("Password must contain at least one digit");
        }
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than 0");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than 0");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds: balance is " + balance);
        }
        balance -= amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }
}
