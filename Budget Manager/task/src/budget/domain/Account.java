package budget.domain;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Account {
    private BigDecimal balance;
    private final LinkedList<Purchase> history;

    Account() {
        balance = BigDecimal.ZERO;
        history = new LinkedList<>();
    }

    public void addPurchase(final Purchase purchase) {
        history.add(purchase);
    }
}
