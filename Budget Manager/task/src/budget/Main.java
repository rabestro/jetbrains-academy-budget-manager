package budget;

import budget.repository.FileJackson;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class Main {
    public static void main(String[] args) {

        new Application(
                new FileJackson(
                        new YAMLMapper()
                )
        ).run();
    }
}
