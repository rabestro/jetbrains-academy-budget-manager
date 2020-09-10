package budget;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.Menu;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application implements Runnable {
    private final Scanner scanner;
    private final Account account;

    public Application(Account account) {
        scanner = new Scanner(System.in);
        this.account = account;
    }

    @Override
    public void run() {

        new Menu("Choose your action:")
                .add("Add income", this::addIncome)
                .add("Add purchase", this::addPurchase)
                .add("Show list of purchases", this::showPurchases)
                .add("Balance", this::printBalance)
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    private void addIncome() {
        System.out.println("Enter income:");
        account.addIncome(scanner.nextBigDecimal());
    }

    private void printBalance() {
        System.out.printf("Balance: $%.2f%n", account.getBalance().doubleValue());
    }

    private void addPurchase() {
        System.out.println("Enter purchase name:");
        final var name = scanner.nextLine();
        System.out.println("Enter its price:");
        final var price = scanner.nextBigDecimal();
        account.addPurchase(new Purchase(name, price));
        System.out.println("Purchase was added!");
    }

    private void showPurchases() {
        account.getHistory()
                .stream()
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(
                        total -> System.out.println("Total sum: $" + total),
                        () -> System.out.println("Purchase list is empty"));
    }
}