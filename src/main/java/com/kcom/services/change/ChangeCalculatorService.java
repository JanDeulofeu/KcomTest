package com.kcom.services.change;

import com.kcom.types.Coin;

import java.util.Collection;


/**
 * @author jan.deulofeu
 */
public interface ChangeCalculatorService {

    Collection<Coin> getOptimalChangeFor(int pence);

    Collection<Coin> getChangeFor(int pence);
}
