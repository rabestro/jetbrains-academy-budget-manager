package budget.repository;

import budget.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class FileJackson implements FileStorage {
    private final Account account;
    private final ObjectMapper objectMapper;

    public FileJackson(Account account, ObjectMapper objectMapper) {
        this.account = account;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save() {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(DATABASE, account);
//            System.out.println("Purchases were saved!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load() {
        return false;
    }
}
