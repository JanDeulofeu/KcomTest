package com.kcom.services.properties;

import com.kcom.types.Coin;
import org.junit.Test;
import java.util.EnumMap;
import java.util.Map;

import static com.kcom.services.properties.PropertiesManager.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author jan.deulofeu
 */
public class PropertiesWriterTest {

    private final PropertiesService propertiesService = new PropertiesManager();

    @Test
    public void validatePropertiesArePersistedToFile() {

        final EnumMap<Coin, Integer> expected = new EnumMap<Coin, Integer>(Coin.class) {{
            put(Coin.POUND, 11);
            put(Coin.FIFTY, 24);
            put(Coin.TWENTY, 0);
            put(Coin.TEN, 99);
            put(Coin.FIVE, 200);
            put(Coin.TWO, 11);
            put(Coin.PENNY, 123);
        }};

        this.propertiesService.writeProperties(expected, PROPERTIES_RESOURCE);

        final Map<Coin, Integer> result = this.propertiesService.readProperties(PROPERTIES_RESOURCE);

        assertThat(result).hasSameSizeAs(expected).containsAllEntriesOf(expected);

    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsNull() {
        assertThatThrownBy(() -> this.propertiesService.writeProperties(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsEmpty() {
        assertThatThrownBy(() -> propertiesService.writeProperties(null, PROPERTIES_RESOURCE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsDoseNotExist() {
        assertThatThrownBy(() -> propertiesService.writeProperties(null, "test.properties"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsNotFound() {
        assertThatThrownBy(() -> propertiesService.writeProperties(null, "/test.properties"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }
}