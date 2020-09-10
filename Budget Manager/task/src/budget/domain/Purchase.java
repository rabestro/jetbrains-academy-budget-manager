package budget.domain;

import java.math.BigDecimal;

public class Purchase {
    private final String description;
    private final BigDecimal price;

    public Purchase(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
