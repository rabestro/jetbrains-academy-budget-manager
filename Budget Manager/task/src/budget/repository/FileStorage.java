package budget.repository;

import budget.domain.Account;

import java.io.File;

public interface FileStorage {
    String FILE_NAME = "purchases.txt";
    File DATABASE = new File(FILE_NAME);

    boolean save();

    boolean load();

    Account getAccount();
}
