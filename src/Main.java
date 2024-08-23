import java.util.*;
import java.util.stream.Collectors;

public class Main {
    final static int YEAR_FOR_SEARCH = 2011;
    final static String CITY_FOR_SEARCH = "Cambridge";
    final static String CITY_TRADER_EXISTS = "Milan";
    final static String CITY_FOR_SUM_TRANSACTIONS = "Cambridge";

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println("(1)" + findAndSort(transactions, YEAR_FOR_SEARCH));
        System.out.println("(2)" + getUniqCities(transactions));
        System.out.println("(3)" + getTradersByCity(transactions, CITY_FOR_SEARCH));
        System.out.println("(4)" + allTraderNames(transactions));
        System.out.println("(5)" + isTraderExistsByCity(transactions, CITY_TRADER_EXISTS));
        System.out.println("(6)" + sumTransactionsByCity(transactions, CITY_FOR_SUM_TRANSACTIONS));
        System.out.println("(7)" + findMaxValue(transactions));
        System.out.println("(8)" + findTransactionWithMinValue(transactions));
    }

    private static List<Transaction> findAndSort(List<Transaction> transactions, int year) {
        return transactions.stream()
                .filter(y -> y.getYear() == year)
                .sorted(Comparator.comparing(Transaction::getValue))
                .toList();
    }

    private static List<String> getUniqCities(List<Transaction> transactions) {
        return transactions.stream()
                .map(tr -> tr.getTrader().getCity())
                .distinct()
                .toList();
    }

    private static List<String> getTradersByCity(List<Transaction> transactions, String city) {
        return transactions.stream()
                .filter(c -> c.getTrader().getCity().equals(city))
                .map(tr -> tr.getTrader().getName())
                .distinct()
                .sorted()
                .toList();
    }

    private static String allTraderNames(List<Transaction> transactions) {
        return transactions.stream()
                .map(n -> n.getTrader().getName())
                .distinct()
                .sorted()
                .toList()
                .toString();
    }

    private static boolean isTraderExistsByCity(List<Transaction> transactions, String city) {
        return transactions.stream()
                .anyMatch(c -> c.getTrader().getCity().equals(city));
    }

    private static int sumTransactionsByCity(List<Transaction> transactions, String city) {
        List<Integer> lst = transactions.stream()
                .filter(c -> c.getTrader().getCity().equals(city))
                .map(Transaction::getValue)
                .toList();
        int result = 0;
        for (Integer i : lst) {
            result += i;
        }
        return result;
    }

    private static int findMaxValue(List<Transaction> transactions) {
        OptionalInt t = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        if (t.isPresent()) {
            return t.getAsInt();
        }
        return -1;
    }

    private static Transaction findTransactionWithMinValue(List<Transaction> transactions) {
        return transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue))
                .orElse(null);
    }
}