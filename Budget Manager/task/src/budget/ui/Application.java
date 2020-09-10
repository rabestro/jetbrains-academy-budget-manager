package budget.ui;

import budget.domain.Account;

public class Application implements Runnable {
    private final Account account;

    public Application(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        new Menu("Choose your action:")
                .add("Add income", System.out::println);
    }
}
