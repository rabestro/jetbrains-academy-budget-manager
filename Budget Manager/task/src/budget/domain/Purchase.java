package budget.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Purchase {
    private Category category;
    private String description;
    private BigDecimal price;

    public Purchase() {

    }

    public Purchase(
            final Category category,
            final String description,
            final BigDecimal price) {
        this.category = category;
        this.description = description;
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s $%s", description, price);
    }

    public enum Category {
        FOOD, CLOTHES, ENTERTAINMENT, OTHER
    }

}
