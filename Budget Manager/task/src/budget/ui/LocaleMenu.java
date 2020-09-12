package budget.ui;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleMenu implements UI.Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, Entry> map = new LinkedHashMap<>();
    private final ResourceBundle bundle;

    private final String title;
    private final String format;
    private boolean once;

    public LocaleMenu(final String bundleName) {
        this.bundle = ResourceBundle.getBundle(bundleName);
        this.title = bundle.getString("menu.title");
        this.format = bundle.getString("menu.format");
    }

    public LocaleMenu onlyOnce() {
        once = true;
        return this;
    }

    public LocaleMenu add(final String key, final String description, final Runnable action) {
        map.put(key, new Entry(bundle.getString(description), action));
        return this;
    }

    public LocaleMenu add(final String description, final Runnable action) {
        return add(String.valueOf(map.size() + 1), description, action);
    }

    public LocaleMenu addExit() {
        return add("0", "menu.exit", this::onlyOnce);
    }

    public LocaleMenu addExit(final String key) {
        return add(key, "menu.exit", this::onlyOnce);
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