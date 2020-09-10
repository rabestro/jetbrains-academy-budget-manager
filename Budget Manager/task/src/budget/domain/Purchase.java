package budget.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Purchase {
    private final Category category;
    private final String description;
    private final BigDecimal price;

    public Purchase(
            final Category category,
            final String description,
            final BigDecimal price) {
        this.category = category;
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

    public enum Category {
        FOOD, CLOTHES, ENTERTAINMENT, OTHER
    }

}
