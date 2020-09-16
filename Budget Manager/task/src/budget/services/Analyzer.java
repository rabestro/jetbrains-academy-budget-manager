package budget.services;

import budget.domain.Purchase;
import budget.repository.FileStorage;
import budget.ui.UI;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

public class Analyzer extends AccountService implements Runnable {

    public Analyzer(
            final FileStorage repository,
            final UI userInterface) {
        super(repository, userInterface);
    }

    @Override
    public void run() {
        ui.menu("menu-analyze")
                .add("item.all", this::sortAll)
                .add("item.type", this::sortByType)
                .add("item.certain", getCategoryMenu(this::sortCertainType).onlyOnce())
                .addExit("4")
                .run();
    }

    private void sortCertainType(Purchase.Category category) {
        ui.println(category.name(), ":");
        db.getAccount().getHistory()
                .stream()
                .filter(purchase -> purchase.getCategory() == category)
                .sorted(comparing(Purchase::getPrice).reversed())
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(this::printTotal, this::printEmpty);
    }

    private void sortAll() {
        if (db.getAccount().getHistory().isEmpty()) {
            printEmpty();
            return;
        }
        ui.println("sortAll");
        db.getAccount().getHistory()
                .stream()
                .sorted(comparing(Purchase::getPrice).reversed())
                .peek(System.out::println)
                .map(Purchase::getPrice)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(this::printTotal, this::printEmpty);
    }

    private void sortByType() {
        ui.println("types");
        db.getAccount().getHistory().stream()
                .collect(groupingBy(Purchase::getCategory,
                        reducing(BigDecimal.ZERO, Purchase::getPrice, BigDecimal::add)))
                .entrySet()
                .stream()
                .sorted(reverseOrder(comparingByValue(BigDecimal::compareTo)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new))
                .forEach((category, total) -> ui.println(category.name(), " - ", total));

        final var total = db.getAccount().getHistory().stream()
                .map(Purchase::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ui.println("totalSum", total);
    }

    public String capitalize(final String sentence) {
        return sentence.substring(0, 1).toUpperCase() + sentence.substring(1).toLowerCase();
    }

}
