package budget.ui;

import java.util.ResourceBundle;

public class LocalMenu extends ConsoleMenu {

    private final ResourceBundle bundle;

    public LocalMenu(UI ui, String bundleName) {
        super(ui);
        bundle = ResourceBundle.getBundle(bundleName);

        for (var property : Property.values()) {
            var key = "menu." + property.name().toLowerCase();
            if (bundle.containsKey(key)) {
                set(property, bundle.getString(key));
            }
        }
    }

    public Menu add(String key, String description, Runnable action) {
        return super.add(key, bundle.getString(description), action);
    }

    public Menu add(String description, Runnable action) {
        return super.add(bundle.getString(description), action);
    }

    public Menu addExit() {
        return add("0", "menu.exit", this::onlyOnce);
    }

}