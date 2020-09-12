package budget;

import budget.repository.FileJackson;
import budget.ui.LocalUI;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(new YAMLMapper()),
                new LocalUI(ResourceBundle.getBundle("application"))
        ).run();
    }
}
