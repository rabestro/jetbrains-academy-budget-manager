package budget;

import budget.repository.FileJackson;
import budget.ui.LocaleUI;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(new YAMLMapper()),
                new LocaleUI(ResourceBundle.getBundle("application"))
        ).run();
    }
}
