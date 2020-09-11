package budget.repository;

import budget.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileJackson implements FileStorage {
    private final Account account;
    private final ObjectMapper objectMapper;

    public FileJackson(Account account, ObjectMapper objectMapper) {
        this.account = account;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean load() {
        return false;
    }
}
