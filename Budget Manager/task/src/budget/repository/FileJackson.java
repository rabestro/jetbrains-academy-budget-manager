package budget.repository;

import budget.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class FileJackson implements FileStorage {
    private final ObjectMapper objectMapper;
    private Account account;

    public FileJackson(ObjectMapper objectMapper) {
        this.account = new Account();
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save() {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(DATABASE, account);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load() {
        try {
            account = objectMapper
                    .readValue(DATABASE, Account.class);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account getAccount() {
        return account;
    }
}
