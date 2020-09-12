package budget.ui;

import java.util.ResourceBundle;

public class LocalUI extends ConsoleUI {
    private final ResourceBundle bundle;

    public LocalUI(ResourceBundle bundle) {
        super();
        this.bundle = bundle;
    }

    @Override
    public void println(String key, Object... args) {
        super.println(bundle.containsKey(key) ? bundle.getString(key) : key, args);
    }

    @Override
    public Menu menu() {
        return menu(bundle.getBaseBundleName());
    }

    @Override
    public Menu menu(String bundle) {
        return new LocalMenu(this, bundle);
    }

}
