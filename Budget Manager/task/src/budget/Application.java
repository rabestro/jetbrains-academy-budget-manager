package budget;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.Menu;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public class Application implements Runnable {
    private static final String DATABASE = "purchases.txt";

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
                .add("Add purchase", getCategoryMenu(this::addPurchase, false))
                .add("Show list of purchases", this::showPurchases)
                .add("Balance", this::printBalance)
                .add("Save", this::save)
                .add("Load", this::load)
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    private void load() {

    }

    private void save() {
        try {
            new JsonMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DATABASE), account);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } else {
            getCategoryMenu(this::showPurchases, true).run();
        }
    }

    private Menu getCategoryMenu(final Consumer<Purchase.Category> action, final boolean isAll) {
        final var menu = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menu.add(category.name(), () -> action.accept(category)));
        if (isAll) {
            menu.add("All", () -> action.accept(null));
        }
        menu.add("Back", menu::once);
        return menu;
    }

    private void showPurchases(final Purchase.Category category) {
        System.out.println(isNull(category) ? "All" : category.name());
        account.getHistory()
                .stream()
                .filter(purchase -> isNull(category) || purchase.getCategory() == category)
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(
                        total -> System.out.println("Total sum: $" + total),
                        this::printListEmpty);
    }
}
