package budget.services;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.UI;


public class Manager {
    private final Account account;
    private final UI ui;

    public Manager(Account account, final UI userInterface) {
        this.ui = userInterface;
        this.account = account;
    }

    private void addIncome() {
        System.out.println("Enter income:");
        account.addIncome(ui.readNumber());
    }

    private void printBalance() {
        System.out.println("Balance: $" + account.getBalance());
    }

    private void addPurchase(final Purchase.Category category) {
        System.out.println("Enter purchase name:");
        final var name = ui.readLine();
        System.out.println("Enter its price:");
        final var price = ui.readNumber();
        account.addPurchase(new Purchase(category, name, price));
        System.out.println("Purchase was added!");
    }

}
