package budget.services;

import budget.domain.Account;
import budget.domain.Purchase;
import budget.ui.ConsoleMenu;
import budget.ui.UI;

import java.math.BigDecimal;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public class Analyzer extends AccountService implements Runnable {

    public Analyzer(Account account, UI userInterface) {
        super(account, userInterface);
    }

    @Override
    public void run() {
        new ConsoleMenu("menu-analyze")
                .add("item.all", this::sortAll)
                .add("item.type", this::sortByType)
                .add("item.certain", new ConsoleMenu("menu-purchase")
                        .onlyOnce()
                        .add("FOOD", () -> sortCertainType(Purchase.Category.FOOD))
                        .add("CLOTHES", () -> sortCertainType(Purchase.Category.CLOTHES))
                        .add("ENTERTAINMENT", () -> sortCertainType(Purchase.Category.ENTERTAINMENT))
                        .add("OTHER", () -> sortCertainType(Purchase.Category.OTHER)))
                .addExit("4")
                .run();
    }

    private void sortCertainType(Purchase.Category category) {
        System.out.println(category.name() + ":");
        account.getHistory()
                .stream()
                .filter(purchase -> purchase.getCategory() == category)
                .sorted(comparing(Purchase::getPrice).reversed())
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(this::printTotal, this::printEmpty);
    }

    private void sortAll() {
        if (account.getHistory().size() == 0) {
            printEmpty();
        } else {
            System.out.println("All:");
            account.getHistory()
                    .stream()
                    .sorted(comparing(Purchase::getPrice).reversed())
                    .peek(System.out::println)
                    .map(Purchase::getPrice)
                    .reduce(BigDecimal::add)
                    .ifPresentOrElse(this::printTotal, this::printEmpty);
        }
    }

    private void sortByType() {
        System.out.println("Types:");
        account.getHistory().stream()
                .collect(groupingBy(Purchase::getCategory,
                        reducing(BigDecimal.ZERO, Purchase::getPrice, BigDecimal::add)))
                .forEach((category, total) -> System.out.printf("%s - $%s%n", category, total));
    }


}
