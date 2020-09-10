package budget;

import budget.ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        new Menu("Choose your action:")
                .add("Add income", System.out::println);

        final var total = new Scanner(System.in)
                .useDelimiter("\\R")
                .tokens()
                .peek(System.out::println)
                .map(s -> s.replaceFirst(".*\\$", ""))
                .mapToDouble(Double::parseDouble)
                .sum();

        System.out.printf("%nTotal: $%.2f%n", total);
    }
}
