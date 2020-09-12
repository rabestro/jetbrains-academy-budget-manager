package budget.ui;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Scanner;

public class ConsoleUI implements UI {
    final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void println(String pattern, Object... args) {
        System.out.println(MessageFormat.format(pattern, args));
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public Number readNumber() {
        return new BigDecimal(scanner.nextLine());
    }

    @Override
    public Menu menu() {
        return null;
    }

    @Override
    public Menu menu(String title) {
        return null;
    }
}
