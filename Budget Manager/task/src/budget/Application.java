package budget;

import budget.domain.Purchase;
import budget.repository.FileStorage;
import budget.services.Analyzer;
import budget.services.Manager;
import budget.ui.ConsoleUI;
import budget.ui.Menu;

import java.util.Arrays;
import java.util.function.Consumer;

public class Application implements Runnable {
    private final FileStorage db;
    private final ConsoleUI ui;

    public Application(final FileStorage repository, final ConsoleUI userInterface) {
        db = repository;
        ui = userInterface;
    }

    @Override
    public void run() {
        final var manager = new Manager(db.getAccount(), ui);

        new Menu("Choose your action:")
                .add("Add income", manager::addIncome)
                .add("Add purchase", getCategoryMenu(manager::addPurchase, false))
                .add("Show list of purchases", manager::showPurchases)
                .add("Balance", manager::printBalance)
                .add("Save", db::save)
                .add("Load", db::load)
                .add("Analyze (Sort)", new Analyzer(db.getAccount(), ui))
                .addExit()
                .run();

        System.out.println("Bye!");
    }

    public Menu getCategoryMenu(final Consumer<Purchase.Category> action, final boolean isAll) {
        final var menu = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menu.add(category.name(), () -> action.accept(category)));
        if (isAll) {
            menu.add("All", () -> action.accept(null));
        }
        menu.add("Back", menu::onlyOnce);
        return menu;
    }

}
