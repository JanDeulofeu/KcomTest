package com.kcom.services.change;

import com.kcom.services.exceptions.InventoryException;
import com.kcom.services.properties.PropertiesManager;
import com.kcom.services.properties.PropertiesService;
import com.kcom.types.Coin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.kcom.services.properties.PropertiesManager.PROPERTIES_RESOURCE;


/**
 * @author jan.deulofeu
 */
public class ChangeCalculator implements ChangeCalculatorService {

    private final Lock lock;
    private final PropertiesService propertiesService;

    public ChangeCalculator() {
        this.propertiesService = new PropertiesManager();
        this.lock = new ReentrantLock();
    }

    public ChangeCalculator(final PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
        this.lock = new ReentrantLock();
    }


    @Override
    public Collection<Coin> getOptimalChangeFor(int pence) {

        final Collection<Coin> results = new ArrayList<>();

        for (final Coin coin : Coin.values()) {

            while(pence >= coin.getDenomination())
            {
                results.add(coin);
                pence -= coin.getDenomination();
            }
        }

        return results;
    }


    @Override
    public Collection<Coin> getChangeFor( int pence) {

        final Collection<Coin> results = new ArrayList<>();

        lock.lock();
        try {

            final EnumMap<Coin, Integer> coinMap = propertiesService.readProperties(PROPERTIES_RESOURCE);

            for (final Map.Entry<Coin, Integer> coin : coinMap.entrySet()) {

                while (coin.getValue() > 0 && pence >= coin.getKey().getDenomination()) {
                    results.add(coin.getKey());
                    pence -= coin.getKey().getDenomination();
                    coin.setValue(coin.getValue() - 1);
                }
            }
            propertiesService.writeProperties(coinMap, PROPERTIES_RESOURCE);

            if(pence > 0)
            {
                throw new InventoryException("Insufficient Coinage.");
            }
        }finally {
            lock.unlock();
        }
        return results;
    }
}
