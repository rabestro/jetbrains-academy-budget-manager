package budget.ui;

import java.util.ResourceBundle;

public class LocalMenu extends ConsoleMenu {

    private final ResourceBundle bundle;

    public LocalMenu(UI ui, final String bundleName) {
        super(ui);
        bundle = ResourceBundle.getBundle(bundleName);
        title = bundle.getString("menu.title");
        format = bundle.getString("menu.format");
    }

    public Menu add(final String key, final String description, final Runnable action) {
        return super.add(key, bundle.getString(description), action);
    }

    public Menu add(final String description, final Runnable action) {
        return super.add(bundle.getString(description), action);
    }

    public Menu addExit() {
        return add("0", "menu.exit", this::onlyOnce);
    }

}