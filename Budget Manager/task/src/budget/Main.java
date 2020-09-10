package budget;

import budget.domain.Account;

public class Main {
    public static void main(String[] args) {

        new Application(
                new Account()
        ).run();
    }
}
