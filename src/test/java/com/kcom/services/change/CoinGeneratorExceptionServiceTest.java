package com.kcom.services.change;

import com.kcom.services.exceptions.InventoryException;
import com.kcom.services.properties.PropertiesService;
import com.kcom.types.Coin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * @author jan.deulofeu
 */
@RunWith(MockitoJUnitRunner.class)
public class CoinGeneratorExceptionServiceTest {

    @Mock
    private PropertiesService propertiesService;

    @InjectMocks
    private ChangeCalculator changeService;


    @Test
    public void validateThatThrowsInventoryExceptionIfThereAreNotEnoughCoins() {

        final EnumMap<Coin, Integer> expected = new EnumMap<Coin, Integer>(Coin.class) {{
            put(Coin.FIVE, 1);
            put(Coin.TWO, 1);
            put(Coin.PENNY, 23);
        }};

        when(propertiesService.readProperties(anyString())).thenReturn(expected);


        assertThatThrownBy(() -> changeService.getChangeFor(200))
                .isInstanceOf(InventoryException.class)
                .hasMessage("Insufficient Coinage.");
    }
}