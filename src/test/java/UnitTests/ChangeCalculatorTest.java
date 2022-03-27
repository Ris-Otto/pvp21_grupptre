package UnitTests;

import com.Controlmatic.PoS_System.model.ChangeCalculator;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

public class ChangeCalculatorTest {

    @Test
    void calculateChange() {
        assert(ChangeCalculator.calculateChange(20.43,10.30)==10.15);
    }

}
