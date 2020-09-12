package budget.services;

import budget.domain.Purchase;
import budget.repository.FileStorage;
import budget.ui.Menu;
import budget.ui.UI;

import java.math.BigDecimal;

import static java.util.Objects.isNull;


public class Manager extends AccountService {

    public Manager(FileStorage repository, UI userInterface) {
        super(repository, userInterface);
    }

    public void addIncome() {
        ui.println("enterIncome");
        db.getAccount().addIncome((BigDecimal) ui.readNumber());
    }

    public void printBalance() {
        ui.println("balance", db.getAccount().getBalance());
    }


    public void addPurchase(final Purchase.Category category) {
        ui.println("purchaseName");
        final var name = ui.readLine();
        ui.println("purchasePrice");
        final var price = (BigDecimal) ui.readNumber();
        db.getAccount().addPurchase(new Purchase(category, name, price));
        System.out.println("purchaseAdded");
    }

    public Menu getPurchaseMenu() {
        final var menu = getCategoryMenu(this::addPurchase);
        return menu.add("menu.exit", menu::onlyOnce);
    }

    public void showPurchases() {
        if (db.getAccount().getHistory().size() == 0) {
            printEmpty();
            return;
        }
        final var menu = getCategoryMenu(this::showPurchases);
        menu.add("all", () -> showPurchases(null))
                .add("menu.exit", menu::onlyOnce)
                .run();
    }

    public void showPurchases(final Purchase.Category category) {
        System.out.println(isNull(category) ? "All:" : category.name() + ":");
        db.getAccount().getHistory()
                .stream()
                .filter(purchase -> isNull(category) || purchase.getCategory() == category)
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(this::printTotal, this::printEmpty);
    }

}
