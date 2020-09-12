package budget.ui;

public interface Menu extends Runnable {
    Menu add(String key, String description, Runnable action);

    Menu add(String description, Runnable action);

    Menu onlyOnce();

    Menu addExit();

    Menu addExit(String key);
}
