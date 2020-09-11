package budget.ui;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleMenu implements UI.Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, Entry> map = new LinkedHashMap<>();
    private final ResourceBundle bundle;

    private final String title;
    private final String format;
    private boolean once;

    public ConsoleMenu(final String bundleName) {
        this.bundle = ResourceBundle.getBundle(bundleName);
        this.title = bundle.getString("menu.title");
        this.format = bundle.getString("menu.format");
    }

    public ConsoleMenu onlyOnce() {
        once = true;
        return this;
    }

    public ConsoleMenu add(final String key, final String description, final Runnable action) {
        map.put(key, new Entry(bundle.getString(description), action));
        return this;
    }

    public ConsoleMenu add(final String description, final Runnable action) {
        return add(String.valueOf(map.size() + 1), bundle.getString(description), action);
    }

    public ConsoleMenu addExit() {
        return add("0", bundle.getString("menu.exit"), this::onlyOnce);
    }

    public ConsoleMenu addExit(final String key, final String name) {
        return add(key, name, this::onlyOnce);
    }

    @Override
    public void run() {
        do {
            System.out.println();
            System.out.println(title);
            map.forEach((key, entry) -> System.out.println(MessageFormat.format(format, key, entry)));
            final var key = scanner.nextLine().toLowerCase();
            System.out.println();
            map.getOrDefault(key, new Entry("Error", this::printErrorMessage)).run();
        } while (!once);
    }

    private void printErrorMessage() {
        System.out.println(MessageFormat.format(bundle.getString("menu.error"), map.size()));
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