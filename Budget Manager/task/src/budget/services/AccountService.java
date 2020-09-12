package budget.services;

import budget.domain.Purchase;
import budget.repository.FileStorage;
import budget.ui.Menu;
import budget.ui.UI;

import java.util.function.Consumer;

abstract class AccountService {
    final FileStorage db;
    final UI ui;

    AccountService(final FileStorage repository, final UI userInterface) {
        this.ui = userInterface;
        this.db = repository;
    }

    void printEmpty() {
        ui.println("emptyList");
    }

    void printTotal(final Number total) {
        ui.println("total", total);
    }

    public Menu getCategoryMenu(final Consumer<Purchase.Category> action) {
        final var menu = ui.menu("menu-purchase");
        for (final var category : Purchase.Category.values()) {
            menu.add(category.name(), () -> action.accept(category));
        }
        return menu;
    }

}
