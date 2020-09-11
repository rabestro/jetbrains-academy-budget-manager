package budget.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface UI {

    void println(String pattern, Object... args);

    String readLine();

    default BigDecimal readNumber() {
        return new BigDecimal(readLine()).setScale(2, RoundingMode.HALF_EVEN);
    }
}
