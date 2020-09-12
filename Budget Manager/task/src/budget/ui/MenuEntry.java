package budget.ui;

final class MenuEntry implements Runnable {
    private final String description;
    private final Runnable action;

    MenuEntry(final String description, final Runnable action) {
        this.description = description;
        this.action = action;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public void run() {
        action.run();
    }
}
