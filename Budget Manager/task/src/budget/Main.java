package budget;

import budget.repository.FileJackson;
import budget.ui.ConsoleUI;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(new XmlMapper()),
                new ConsoleUI(ResourceBundle.getBundle("application"))
        ).run();
    }
}
