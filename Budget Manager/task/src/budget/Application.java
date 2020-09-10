package budget;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.Menu;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class Application implements Runnable {
    private final Scanner scanner;
    private final Account account;

    public Application(Account account) {
        scanner = new Scanner(System.in);
        this.account = account;
    }

    @Override
    public void run() {
        final var menuAddPurchase = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menuAddPurchase.add(category.name(), () -> addPurchase(category)));
        menuAddPurchase.add("Back", menuAddPurchase::once);

        new Menu("Choose your action:")
                .add("Add income", this::addIncome)
                .add("Add purchase", menuAddPurchase)
                .add("Show list of purchases", this::showPurchases)
                .add("Balance", this::printBalance)
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    private void addIncome() {
        System.out.println("Enter income:");
        account.addIncome(new BigDecimal(scanner.nextLine()));
    }

    private void printBalance() {
        System.out.println("Balance: $" + account.getBalance());
    }

    private void addPurchase(final Purchase.Category category) {
        System.out.println("Enter purchase name:");
        final var name = scanner.nextLine();
        System.out.println("Enter its price:");
        final var price = new BigDecimal(scanner.nextLine());
        account.addPurchase(new Purchase(category, name, price));
        System.out.println("Purchase was added!");
    }

    private void printListEmpty() {
        System.out.println("Purchase list is empty!");
    }

    private void showPurchases() {
        if (account.getHistory().size() == 0) {
            printListEmpty();
            return;
        }
        final var menu = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menu.add(category.name(), () -> showPurchases(category)));
        menu.add("All", () -> showPurchases(null));
        menu.add("Back", menu::once);
        menu.run();
    }

    private void showPurchases(final Purchase.Category category) {
        if (account.getHistory().size() > 0) {
            System.out.println(isNull(category) ? "All" : category.name());
        }
        account.getHistory()
                .stream()
                .filter(purchase -> isNull(category) || purchase.getCategory() == category)
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(
                        total -> System.out.println("Total sum: $" + total),
                        () -> System.out.println("Purchase list is empty"));
    }
}
