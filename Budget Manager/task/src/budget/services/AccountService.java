package budget.services;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.UI;

import java.math.BigDecimal;
import java.util.function.Consumer;

abstract class AccountService {
    final Account account;
    final UI ui;

    AccountService(final Account account, final UI userInterface) {
        this.ui = userInterface;
        this.account = account;
    }

    void printEmpty() {
        ui.println("list_empty");
    }

    void printTotal(final BigDecimal total) {
        ui.println("total", total);
    }

    public UI.Menu getCategoryMenu(final Consumer<Purchase.Category> action, final boolean isAll) {
        final var menu = ui.menu("menu-purchase");
        for (final var category : Purchase.Category.values()) {
            menu.add(category.name(), () -> action.accept(category));
        }
        if (isAll) {
            menu.add("all", () -> action.accept(null));
        }
        menu.add("menu.exit", menu::onlyOnce);
        return menu;
    }

}
