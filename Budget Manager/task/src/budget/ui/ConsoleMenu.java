package budget.ui;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleMenu implements Menu {

    final Map<String, MenuEntry> menu = new LinkedHashMap<>();
    final Map<Property, String> properties = new EnumMap<>(Property.class);

    UI ui;
    String format = "{0}) {1}";
    boolean isOnlyOnce;
    String error = "Please enter the number from 0 up to {0}";

    public ConsoleMenu(UI userInterface) {
        ui = userInterface;
    }

    public ConsoleMenu(UI userInterface, final String title) {
        this(userInterface);
        properties.put(Property.TITLE, title);
    }

    @Override
    public Menu set(Property key, String value) {
        properties.put(key, value);
        return this;
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
            ui.println(properties.getOrDefault(Property.TITLE, Property.TITLE.name()));
            menu.forEach((key, entry) -> ui.println(format, key, entry));
            final var key = ui.readLine().toLowerCase();
            ui.println("");
            menu.getOrDefault(key, new MenuEntry("Error", this::printErrorMessage)).run();
        } while (!isOnlyOnce);
    }

    void printErrorMessage() {
        ui.println(error, menu.size());
    }

}
