package budget.ui;

public interface Menu extends Runnable {
    Menu title(String title);

    Menu format(String format);

    Menu add(String key, String description, Runnable action);

    Menu add(String description, Runnable action);

    Menu onlyOnce();

    Menu addExit();

    Menu addExit(String key);
}
