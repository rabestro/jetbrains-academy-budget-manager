package budget.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Account {
    private List<Purchase> history;
    private BigDecimal balance;

    public Account() {
        balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        history = new LinkedList<>();
    }

    public void addIncome(final BigDecimal income) {
        balance = balance.add(income);
    }

    public void addPurchase(final Purchase purchase) {
        balance = balance.subtract(purchase.getPrice());
        history.add(purchase);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public List<Purchase> getHistory() {
        return history;
    }

    public void setHistory(final List<Purchase> history) {
        this.history = history;
    }
}
