package budget.repository;

import java.io.File;

public interface FileStorage {
    String FILE_NAME = "purchases.txt";
    File DATABASE = new File(FILE_NAME);

}
