package budget;

import budget.domain.Purchase;
import budget.repository.FileStorage;
import budget.services.Analyzer;
import budget.services.Manager;
import budget.ui.ConsoleUI;
import budget.ui.Menu;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public class Application implements Runnable {
    private final Scanner scanner;
    private final FileStorage db;
    private final ConsoleUI ui;

    public Application(final FileStorage repository, final ConsoleUI userInterface) {
        db = repository;
        ui = userInterface;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        final var manager = new Manager(db.getAccount(), ui);

        new Menu("Choose your action:")
                .add("Add income", manager::addIncome)
                .add("Add purchase", getCategoryMenu(manager::addPurchase, false))
                .add("Show list of purchases", this::showPurchases)
                .add("Balance", manager::printBalance)
                .add("Save", db::save)
                .add("Load", db::load)
                .add("Analyze (Sort)", new Analyzer(db.getAccount()))
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    private void printListEmpty() {
        System.out.println("Purchase list is empty!");
    }

    private Menu getCategoryMenu(final Consumer<Purchase.Category> action, final boolean isAll) {
        final var menu = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menu.add(category.name(), () -> action.accept(category)));
        if (isAll) {
            menu.add("All", () -> action.accept(null));
        }
        menu.add("Back", menu::onlyOnce);
        return menu;
    }

    private void showPurchases() {
        if (db.getAccount().getHistory().size() == 0) {
            printListEmpty();
        } else {
            getCategoryMenu(this::showPurchases, true).run();
        }
    }

    private void showPurchases(final Purchase.Category category) {
        System.out.println(isNull(category) ? "All:" : category.name() + ":");
        db.getAccount().getHistory()
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
