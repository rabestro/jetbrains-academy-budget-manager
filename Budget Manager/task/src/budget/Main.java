package budget;

import budget.domain.Account;

public class Main {
    public static void main(String[] args) {

        new Application(new Account()).run();

//        final var total = new Scanner(System.in)
//                .useDelimiter("\\R")
//                .tokens()
//                .peek(System.out::println)
//                .map(s -> s.replaceFirst(".*\\$", ""))
//                .mapToDouble(Double::parseDouble)
//                .sum();
//
//        System.out.printf("%nTotal: $%.2f%n", total);
    }
}
