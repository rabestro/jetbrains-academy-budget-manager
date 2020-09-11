package budget;

import budget.repository.FileStorage;
import budget.services.Analyzer;
import budget.services.Manager;
import budget.ui.ConsoleUI;

public class Application implements Runnable {
    private final FileStorage repository;
    private final ConsoleUI ui;

    public Application(final FileStorage repository, final ConsoleUI userInterface) {
        this.repository = repository;
        ui = userInterface;
    }

    @Override
    public void run() {
        final var manager = new Manager(repository.getAccount(), ui);

        ui.menu("main-menu")
                .add("item.addIncome", manager::addIncome)
                .add("item.addPurchase", manager.getPurchaseMenu())
                .add("item.purchases", manager::showPurchases)
                .add("item.balance", manager::printBalance)
                .add("item.save", repository::save)
                .add("item.load", repository::load)
                .add("item.analyze", new Analyzer(repository.getAccount(), ui))
                .addExit()
                .run();

        ui.println("bye");
    }

}
