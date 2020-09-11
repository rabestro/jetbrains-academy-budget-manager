package budget.services;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.UI;

import java.math.BigDecimal;

import static java.util.Objects.isNull;


public class Manager extends AccountService {

    public Manager(Account account, UI userInterface) {
        super(account, userInterface);
    }

    public void addIncome() {
        ui.println("enterIncome");
        account.addIncome((BigDecimal) ui.readNumber());
    }

    public void printBalance() {
        ui.println("balance", account.getBalance());
    }


    public void addPurchase(final Purchase.Category category) {
        System.out.println("Enter purchase name:");
        final var name = ui.readLine();
        System.out.println("Enter its price:");
        final var price = (BigDecimal) ui.readNumber();
        account.addPurchase(new Purchase(category, name, price));
        System.out.println("Purchase was added!");
    }

    public UI.Menu getPurchaseMenu() {
        return getCategoryMenu(this::addPurchase, false);
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
