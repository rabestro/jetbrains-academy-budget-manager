package budget.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUI implements UI {
    private static final Scanner scanner = new Scanner(System.in);
    private final ResourceBundle bundle;

    public ConsoleUI(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void println(String pattern, Object... args) {
        System.out.println(MessageFormat.format(pattern, args));
    }

    @Override
    public BigDecimal readNumber() {
        return new BigDecimal(scanner.nextLine())
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public Menu menu() {
        return new ConsoleMenu(bundle);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
