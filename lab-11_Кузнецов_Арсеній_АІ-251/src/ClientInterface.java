import exceptions.*;
import model.BankAccount;
import model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {

        String option;
        while (true) {
            printMainMenu();
            option = scanner.next();

            try {
                switch (Integer.parseInt(option)) {
                    case 1:
                        addNewAccount();
                        break;
                    case 2:
                        deleteAccount();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        printInfo();
                        break;
                    case 6:
                        return;
                    default:
                        System.err.println("Error: please enter a valid choice (1 thru 6)\n");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: please enter a valid choice (1 thru 6)\n");
            } catch (AccountException e) {
                System.err.println("Error: " + e.getMessage() + "\n");
            }
        }

    }

    private void printMainMenu() {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    // ---------- допоміжні методи ----------

    private BankAccount findAccount(String userId) {
        return accounts.stream()
                .filter(a -> a.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account with user id '" + userId + "' does not exist"));
    }

    /** Знаходить рахунок та перевіряє пароль. */
    private BankAccount authenticate(String userId, String password) {
        BankAccount account = findAccount(userId);
        if (!account.checkPassword(password)) {
            throw new WrongPasswordException("Incorrect password");
        }
        return account;
    }

    private double parseAmount(String text) {
        try {
            double amount = Double.parseDouble(text);
            if (Double.isNaN(amount) || Double.isInfinite(amount)) {
                throw new InvalidAmountException("Amount is not a valid number: " + text);
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Amount is not a valid number: " + text);
        }
    }

    // ---------- операції ----------

    private void addNewAccount() {

        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        var name = scanner.next();
        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();

        if (accounts.stream().anyMatch(a -> a.getUserId().equals(userId))) {
            throw new DuplicateUserIdException("User id '" + userId + "' is already taken");
        }
        BankAccount.validatePassword(password);
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Password and confirmation do not match");
        }

        accounts.add(new CheckingAccount(name, userId, password));
        System.out.println("\nAccount created successfully.");
    }

    private void deleteAccount() {

        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = authenticate(userId, password);
        if (account.getBalance() != 0) {
            throw new NonZeroBalanceException(
                    "Cannot delete account with non-zero balance: " + account.getBalance());
        }

        accounts.remove(account);
        System.out.println("\nAccount deleted successfully.");
    }

    private void deposit() {

        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount account = authenticate(userId, password);
        account.deposit(parseAmount(amount));
        System.out.println("\nDeposit completed successfully. New balance: " + account.getBalance());
    }

    private void withdraw() {

        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount account = authenticate(userId, password);
        account.withdraw(parseAmount(amount));
        System.out.println("\nWithdrawal completed successfully. New balance: " + account.getBalance());
    }

    private void printInfo() {

        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = authenticate(userId, password);

        System.out.println("\nAccount info: ");

        System.out.println(" - customer name: " + account.getCustomerName());
        System.out.println(" - account balance: " + account.getBalance());
        System.out.println(" - account type: Checking");
        System.out.println(" - account daily withdrawal limit: "
                + ((CheckingAccount) account).getDailyWithdrawalLimit());

        System.out.print("\nPress Enter to return to main menu...");

        // перший nextLine() дочитує залишок рядка після next(), другий чекає на Enter
        scanner.nextLine();
        scanner.nextLine();
    }

}
