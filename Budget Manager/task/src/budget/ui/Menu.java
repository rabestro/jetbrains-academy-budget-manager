package budget.ui;

public interface Menu extends Runnable {

    Menu set(Property key, String value);

    Menu add(String key, String description, Runnable action);

    Menu add(String description, Runnable action);

    Menu onlyOnce();

    Menu addExit();

    Menu addExit(String key);

    enum Property {
        TITLE, FORMAT, EXIT, ERROR
    }
}
