package budget.ui;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleMenu implements Menu {

    private final Map<String, MenuEntry> menu = new LinkedHashMap<>();
    private final Map<Property, String> properties = new EnumMap<>(Property.class);

    private final UI ui;
    private boolean isOnlyOnce;

    public ConsoleMenu(UI userInterface) {
        ui = userInterface;
        set(Property.ERROR, "Please enter the number from 0 up to {0}");
        set(Property.FORMAT, "{0}. {1}");
        set(Property.EXIT, "Exit");
    }

    public ConsoleMenu(UI userInterface, String title) {
        this(userInterface);
        set(Property.TITLE, title);
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
        return add("0", properties.get(Property.EXIT), this::onlyOnce);
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
            menu.forEach((key, entry) -> ui.println(properties.get(Property.FORMAT), key, entry));
            final var key = ui.readLine().toLowerCase();
            ui.println("");
            menu.getOrDefault(key, new MenuEntry("Error",
                    () -> ui.println(properties.get(Property.ERROR), menu.size()))
            ).run();
        } while (!isOnlyOnce);
    }

}
