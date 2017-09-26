package com.kcom.services.properties;

import com.kcom.types.Coin;
import java.util.EnumMap;


/**
 * @author jan.deulofeu
 */
public interface PropertiesService {

    EnumMap<Coin, Integer> readProperties(String resourceName);

    void writeProperties(EnumMap<Coin, Integer> properties, String resourceName);

}
