package budget;

import budget.repository.FileJackson;
import budget.ui.ConsoleUI;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(new YAMLMapper()),
                new ConsoleUI(ResourceBundle.getBundle("application"))
        ).run();
    }
}
