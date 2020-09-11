package budget.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUI implements UI {
    private static final Scanner scanner = new Scanner(System.in);
    private final ResourceBundle bundle;

    public ConsoleUI(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void printTotal(final BigDecimal total) {
        System.out.println("Total: $" + total);
    }

    public BigDecimal readNumber() {
        return new BigDecimal(scanner.nextLine())
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
