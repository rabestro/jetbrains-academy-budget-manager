package budget.ui;

public interface UI {

    void println(String pattern, Object... args);

    String readLine();

    Number readNumber();

    Menu menu();

    Menu menu(String title);

}
