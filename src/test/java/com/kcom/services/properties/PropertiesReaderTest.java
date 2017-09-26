package com.kcom.services.properties;

import com.kcom.types.Coin;
import org.junit.Test;
import java.util.EnumMap;

import static com.kcom.services.properties.PropertiesManager.PROPERTIES_RESOURCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * @author jan.deulofeu
 */
public class PropertiesReaderTest {

    private PropertiesService propertiesService = new PropertiesManager();


    @Test
    public void validatePropertiesLoaderGeneratesCorrectMap() {

        final EnumMap<Coin, Integer> expected = new EnumMap<Coin, Integer>(Coin.class) {{
            put(Coin.POUND, 11);
            put(Coin.FIFTY, 24);
            put(Coin.TWENTY, 0);
            put(Coin.TEN, 99);
            put(Coin.FIVE, 200);
            put(Coin.TWO, 11);
            put(Coin.PENNY, 23);
        }};

        propertiesService.writeProperties(expected, PROPERTIES_RESOURCE);

        final EnumMap<Coin, Integer> result = propertiesService.readProperties(PROPERTIES_RESOURCE);

        assertThat(result).hasSameSizeAs(expected).containsAllEntriesOf(expected);
    }


    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsNull() {
        assertThatThrownBy(() -> propertiesService.readProperties(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }


    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsEmpty() {
        assertThatThrownBy(() -> propertiesService.readProperties(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsDoseNotExist() {
        assertThatThrownBy(() -> propertiesService.readProperties("test.properties"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

    @Test
    public void validateThatThrowsExceptionIfPropertiesFileIsNotFound() {
        assertThatThrownBy(() -> propertiesService.readProperties("/test.properties"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties not found");
    }

}