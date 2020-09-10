package budget.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Purchase {
    private final String description;
    private final BigDecimal price;

    public Purchase(String description, BigDecimal price) {
        this.description = description;
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s $%s", description, price);
    }
}
