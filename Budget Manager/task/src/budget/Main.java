package budget;

import budget.repository.FileJackson;
import budget.ui.ConsoleUI;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(new JsonMapper()),
                new ConsoleUI(ResourceBundle.getBundle("application"))
        ).run();
    }
}
