package budget.domain;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Account {
    private BigDecimal balance;
    private final LinkedList<Purchase> history;

    public Account() {
        balance = BigDecimal.ZERO;
        history = new LinkedList<>();
    }

    public void addIncome(final BigDecimal income) {
        balance = balance.add(income);
    }

    public void addPurchase(final Purchase purchase) {
        history.add(purchase);
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
