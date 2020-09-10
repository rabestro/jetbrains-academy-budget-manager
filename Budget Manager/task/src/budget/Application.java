package budget;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public class Application implements Runnable {
    private static final File DATABASE = new File("purchases.txt");
    private static final ObjectMapper MAPPER = new XmlMapper();

    private final Scanner scanner;
    private Account account;

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
                .add("Analyze (Sort)", new Menu("How do you want to sort?")
                        .add("Sort all purchases", this::sortAll)
                        .add("Sort by type", this::sortByType)
                        .add("Sort certain type", new Menu("Choose the type of purchase")
                                .once()
                                .add("Food", () -> sortCertainType(Purchase.Category.FOOD))
                                .add("Clothes", () -> sortCertainType(Purchase.Category.CLOTHES))
                                .add("Entertainment", () -> sortCertainType(Purchase.Category.ENTERTAINMENT))
                                .add("Other", () -> sortCertainType(Purchase.Category.OTHER)))
                        .addExit("4", "Back"))
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    private void sortCertainType(Purchase.Category category) {
        System.out.println(category.name() + ":");
        account.getHistory()
                .stream()
                .filter(purchase -> isNull(category) || purchase.getCategory() == category)
                .sorted(comparing(Purchase::getPrice).reversed())
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(
                        total -> System.out.println("Total sum: $" + total),
                        this::printListEmpty);
    }

    private void sortAll() {
        if (account.getHistory().size() == 0) {
            printListEmpty();
        } else {
            System.out.println("All:");
            account.getHistory()
                    .stream()
                    .sorted(comparing(Purchase::getPrice).reversed())
                    .peek(System.out::println)
                    .map(Purchase::getPrice)
                    .reduce(BigDecimal::add)
                    .ifPresentOrElse(
                            total -> System.out.println("Total: $" + total),
                            this::printListEmpty);
        }
    }

    private void sortByType() {
        System.out.println("Types:");
        account.getHistory().stream()
                .collect(groupingBy(Purchase::getCategory,
                        reducing(BigDecimal.ZERO, Purchase::getPrice, BigDecimal::add)))
                .forEach((category, total) -> System.out.printf("%s - $%s%n", category, total));
    }

    private void load() {
        try {
            account = MAPPER.readValue(DATABASE, Account.class);
            System.out.println("Purchases were loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(DATABASE, account);
            System.out.println("Purchases were saved!");
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
        System.out.println(isNull(category) ? "All:" : category.name() + ":");
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
