package budget.services;

import budget.domain.Account;
import budget.ui.UI;

import java.math.BigDecimal;

public abstract class AccountService {
    protected final Account account;
    protected final UI ui;

    public AccountService(final Account account, final UI userInterface) {
        this.ui = userInterface;
        this.account = account;
    }

    public void printBalance() {
        ui.println("balance", account.getBalance());
    }

    protected void printEmpty() {
        ui.println("purchase.list.empty");
    }

    protected void printTotal(final BigDecimal total) {
        System.out.println("Total: $" + total);
        ui.println("total", total);
    }

}
