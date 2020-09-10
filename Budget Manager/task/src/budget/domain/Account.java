package budget.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Account {
    private BigDecimal balance;
    private final List<Purchase> history;

    public Account() {
        balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
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

    public List<Purchase> getHistory() {
        return history;
    }
}
