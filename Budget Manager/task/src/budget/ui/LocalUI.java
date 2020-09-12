package budget.ui;

import java.util.ResourceBundle;

public class LocalUI extends ConsoleUI {
    private final ResourceBundle bundle;

    public LocalUI(ResourceBundle bundle) {
        super();
        this.bundle = bundle;
    }

    @Override
    public void println(String pattern, Object... args) {
        super.println(bundle.getString(pattern), args);
    }

    @Override
    public Menu menu() {
        return new LocalMenu(bundle.getBaseBundleName());
    }

    @Override
    public Menu menu(String bundle) {
        return new LocalMenu(bundle);
    }

}
