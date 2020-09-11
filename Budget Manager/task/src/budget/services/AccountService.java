package budget.services;

import budget.domain.Account;
import budget.ui.UI;

import java.math.BigDecimal;

abstract class AccountService {
    final Account account;
    final UI ui;

    AccountService(final Account account, final UI userInterface) {
        this.ui = userInterface;
        this.account = account;
    }

    void printEmpty() {
        ui.println("purchase.list.empty");
    }

    void printTotal(final BigDecimal total) {
        ui.println("total", total);
    }

}
