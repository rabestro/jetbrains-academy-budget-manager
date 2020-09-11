package budget.repository;

import budget.domain.Account;

import java.io.File;

public interface FileStorage {
    File DATABASE = new File("purchases.txt");

    boolean save();

    boolean load();

    Account getAccount();
}
