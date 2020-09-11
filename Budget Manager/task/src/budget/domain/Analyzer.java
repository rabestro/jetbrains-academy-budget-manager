package budget.domain;

public class Analyzer implements Runnable {
    private final Account account;

    public Analyzer(Account account) {
        this.account = account;
    }

    @Override
    public void run() {

    }
}
