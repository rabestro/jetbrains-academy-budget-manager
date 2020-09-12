package budget;

import budget.repository.FileStorage;
import budget.services.Analyzer;
import budget.services.Manager;
import budget.ui.LocaleUI;

public class Application implements Runnable {
    private final FileStorage repository;
    private final LocaleUI ui;

    public Application(final FileStorage repository, final LocaleUI userInterface) {
        this.repository = repository;
        ui = userInterface;
    }

    @Override
    public void run() {
        final var manager = new Manager(repository, ui);

        ui.menu("menu-main")
                .add("item.addIncome", manager::addIncome)
                .add("item.addPurchase", manager.getPurchaseMenu())
                .add("item.purchases", manager::showPurchases)
                .add("item.balance", manager::printBalance)
                .add("item.save", this::save)
                .add("item.load", this::load)
                .add("item.analyze", new Analyzer(repository, ui))
                .addExit()
                .run();

        ui.println("bye");
    }

    private void save() {
        ui.println(repository.save() ? "saved" : "error.save", FileStorage.DATABASE.getAbsolutePath());
    }

    private void load() {
        ui.println(repository.load() ? "loaded" : "error.load", FileStorage.DATABASE.getAbsolutePath());
    }
}
