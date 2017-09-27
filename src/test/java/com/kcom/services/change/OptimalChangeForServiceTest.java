package com.kcom.services.change;

import com.kcom.types.Coin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author jan.deulofeu
 */
@RunWith(Parameterized.class)
public class OptimalChangeForServiceTest {

    private final int change;
    private final List<Coin> expected;
    private final ChangeCalculatorService changeService = new ChangeCalculator();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {-1, Collections.emptyList()},
                {0, Collections.emptyList()},
                {1, Arrays.asList(Coin.PENNY)},
                {18, Arrays.asList(Coin.TEN, Coin.FIVE, Coin.TWO, Coin.PENNY)},
                {22, Arrays.asList(Coin.TWENTY, Coin.TWO)},
                {35, Arrays.asList(Coin.TWENTY, Coin.TEN, Coin.FIVE)},
                {41, Arrays.asList(Coin.TWENTY, Coin.TWENTY, Coin.PENNY)},
                {55, Arrays.asList(Coin.FIFTY, Coin.FIVE)},
                {60, Arrays.asList(Coin.FIFTY, Coin.TEN)},
                {75, Arrays.asList(Coin.FIFTY, Coin.TWENTY, Coin.FIVE)},
                {89, Arrays.asList(Coin.FIFTY, Coin.TWENTY, Coin.TEN, Coin.FIVE, Coin.TWO, Coin.TWO)},
                {92, Arrays.asList(Coin.FIFTY, Coin.TWENTY, Coin.TWENTY, Coin.TWO)},
                {113, Arrays.asList(Coin.POUND, Coin.TEN, Coin.TWO, Coin.PENNY)},
                {205, Arrays.asList(Coin.POUND, Coin.POUND, Coin.FIVE)},
                {350, Arrays.asList(Coin.POUND, Coin.POUND, Coin.POUND, Coin.FIFTY)},
                {401, Arrays.asList(Coin.POUND, Coin.POUND, Coin.POUND, Coin.POUND, Coin.PENNY)}
        });
    }

    public OptimalChangeForServiceTest(final int change, final List<Coin> expected) {
        this.change = change;
        this.expected = expected;
    }

    @Test
    public void validateChangeUsingOptimalChangeForService() {

        final Collection<Coin> result = this.changeService.getOptimalChangeFor(this.change);

        assertThat(result).hasSameSizeAs(this.expected).containsAll(this.expected);
    }
}