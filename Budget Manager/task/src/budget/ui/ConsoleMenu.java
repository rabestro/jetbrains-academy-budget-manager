package budget.ui;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleMenu implements Menu {

    final Map<String, MenuEntry> menu = new LinkedHashMap<>();
    final String errorMessage = "Please enter the number from 0 up to {0}";
    UI ui;
    String title;
    String format;
    boolean isOnlyOnce;

    public ConsoleMenu(UI userInterface) {
        ui = userInterface;
    }

    public ConsoleMenu(UI userInterface, final String title) {
        this(userInterface);
        this.title = title;
    }

    @Override
    public Menu add(String key, String description, Runnable action) {
        menu.put(key, new MenuEntry(description, action));
        return this;
    }

    @Override
    public Menu add(String description, Runnable action) {
        return add(String.valueOf(menu.size() + 1), description, action);
    }

    @Override
    public Menu onlyOnce() {
        isOnlyOnce = true;
        return this;
    }

    @Override
    public Menu addExit() {
        return add("0", "Exit", this::onlyOnce);
    }

    @Override
    public Menu addExit(String description) {
        return add(String.valueOf(menu.size() + 1), description, this::onlyOnce);
    }

    @Override
    public void run() {
        do {
            ui.println("");
            ui.println(title);
            menu.forEach((key, entry) -> ui.println(format, key, entry));
            final var key = ui.readLine().toLowerCase();
            ui.println("");
            menu.getOrDefault(key, new MenuEntry("Error", this::printErrorMessage)).run();
        } while (!isOnlyOnce);
    }

    void printErrorMessage() {
        ui.println(errorMessage, menu.size());
    }

}
