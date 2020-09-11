package budget.ui;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.util.Objects.nonNull;

public class ConsoleMenu implements UI.Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, Entry> map = new LinkedHashMap<>();
    private final ResourceBundle bundle;

    private final String title;
    private boolean once;
    private String format = "%s) %s%n";

    public ConsoleMenu(final String title) {
        this.title = title;
        bundle = null;
    }

    public ConsoleMenu(final ResourceBundle bundle) {
        this.bundle = bundle;
        this.title = bundle.getString("title");
        this.format = bundle.getString("line-format");
    }

    public ConsoleMenu onlyOnce() {
        once = true;
        return this;
    }

    public ConsoleMenu setFormat(String pattern) {
        format = pattern;
        return this;
    }

    public ConsoleMenu add(final String key, final String description, final Runnable action) {
        map.put(key, new Entry(description, action));
        return this;
    }

    public ConsoleMenu add(final String description, final Runnable action) {
        return add(String.valueOf(map.size() + 1), description, action);
    }

    public ConsoleMenu addExit() {
        return add("0", "Exit", this::onlyOnce);
    }

    public ConsoleMenu addExit(final String key, final String name) {
        return add(key, name, this::onlyOnce);
    }

    @Override
    public void run() {
        do {
            System.out.println();
            System.out.println(title);
            map.forEach((key, entry) -> System.out.printf(format, key, entry));
            final var key = scanner.nextLine().toLowerCase();
            System.out.println();
            map.getOrDefault(key, new Entry("Error", this::printErrorMessage)).run();
        } while (!once);
    }

    private void printErrorMessage() {
        final var msg = nonNull(bundle)
                ? bundle.getString("error")
                : "Please enter the number from 0 up to {0}";
        System.out.println(MessageFormat.format(msg, map.size()));
    }

    private static final class Entry implements Runnable {
        private final String description;
        private final Runnable action;

        private Entry(final String name, final Runnable action) {
            this.description = name;
            this.action = action;
        }

        @Override
        public String toString() {
            return description;
        }

        @Override
        public void run() {
            action.run();
        }
    }
}