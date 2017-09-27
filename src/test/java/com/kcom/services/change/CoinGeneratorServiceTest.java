package com.kcom.services.change;

import com.kcom.services.properties.PropertiesManager;
import com.kcom.services.properties.PropertiesService;
import com.kcom.types.Coin;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static com.kcom.services.properties.PropertiesManager.PROPERTIES_RESOURCE;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author jan.deulofeu
 */
@RunWith(Parameterized.class)
public class CoinGeneratorServiceTest {

    private final int change;
    private final List<Coin> expected;
    private final ChangeCalculatorService changeService = new ChangeCalculator();
    private static final PropertiesService propertiesService = new PropertiesManager();
    private static EnumMap<Coin, Integer> testProperties;

    @Before
    public void init()
    {
        testProperties = new EnumMap<Coin, Integer>(Coin.class) {{
            put(Coin.POUND, 3);
            put(Coin.FIFTY, 1);
            put(Coin.TWENTY, 2);
            put(Coin.TEN, 15);
            put(Coin.FIVE, 10);
            put(Coin.TWO, 20);
            put(Coin.PENNY, 50);
        }};

        propertiesService.writeProperties(testProperties, PROPERTIES_RESOURCE);
    }

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
                {401, Arrays.asList(Coin.POUND, Coin.POUND, Coin.POUND, Coin.FIFTY, Coin.TWENTY, Coin.TWENTY, Coin.TEN, Coin.PENNY)}
        });
    }


    public CoinGeneratorServiceTest(final int change, final List<Coin> expected) {
        this.change = change;
        this.expected = expected;
    }


    @Test
    public void validateChangeUsingChangeForService() {

        final Collection<Coin> result = changeService.getChangeFor(this.change);

        assertThat(result).hasSameSizeAs(this.expected).containsAll(this.expected);
    }

    @Test
    public void validatePropertiesFileIsPersistedWithValuesDecremented() {

        final Collection<Coin> result = changeService.getChangeFor(this.change);

        final EnumMap<Coin, Integer> coinIntegerEnumMap = propertiesService.readProperties(PROPERTIES_RESOURCE);

        testProperties.entrySet().stream().forEach(coin -> {
            result.stream().forEach(k -> {
                if (coin.getKey().equals(k)) {
                    coin.setValue(coin.getValue() - 1);
                }
            });
        });

        assertThat(testProperties).hasSameSizeAs(coinIntegerEnumMap).containsAllEntriesOf(coinIntegerEnumMap);
    }
}