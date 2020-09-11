package budget.ui;

public interface UI {

    void println(String pattern, Object... args);

    String readLine();

    Number readNumber();

    Menu menu();

    Menu menu(String title);

    interface Menu extends Runnable {
        Menu add(String key, String description, Runnable action);

        Menu add(String description, Runnable action);

        Menu onlyOnce();

        Menu addExit();

        Menu addExit(String key, String name);
    }
}
