package budget.services;

import budget.domain.Account;
import budget.ui.UI;

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

}
