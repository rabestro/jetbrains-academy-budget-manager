package budget.services;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.Menu;
import budget.ui.UI;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.util.Objects.isNull;


public class Manager extends AccountService {

    public Manager(Account account, UI userInterface) {
        super(account, userInterface);
    }

    public void addIncome() {
        System.out.println("Enter income:");
        account.addIncome(ui.readNumber());
    }

    public void printBalance() {
        ui.println("balance", account.getBalance());
    }


    public void addPurchase(final Purchase.Category category) {
        System.out.println("Enter purchase name:");
        final var name = ui.readLine();
        System.out.println("Enter its price:");
        final var price = ui.readNumber();
        account.addPurchase(new Purchase(category, name, price));
        System.out.println("Purchase was added!");
    }

    public Menu getCategoryMenu(final Consumer<Purchase.Category> action, final boolean isAll) {
        final var menu = new Menu("Choose the type of purchase");
        Arrays.stream(Purchase.Category.values())
                .forEach(category -> menu.add(category.getDescription(), () -> action.accept(category)));
        if (isAll) {
            menu.add("All", () -> action.accept(null));
        }
        menu.add("Back", menu::onlyOnce);
        return menu;
    }

    public void showPurchases() {
        if (account.getHistory().size() == 0) {
            printEmpty();
        } else {
            getCategoryMenu(this::showPurchases, true).run();
        }
    }

    public void showPurchases(final Purchase.Category category) {
        System.out.println(isNull(category) ? "All:" : category.name() + ":");
        account.getHistory()
                .stream()
                .filter(purchase -> isNull(category) || purchase.getCategory() == category)
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(
                        total -> System.out.println("Total sum: $" + total),
                        this::printEmpty);
    }

}
