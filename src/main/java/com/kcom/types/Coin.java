package com.kcom.types;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author jan.deulofeu
 */
public enum Coin {

    POUND(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    PENNY(1);

    private final int denomination;

    Coin(final int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static Coin getCoinTypeByValue(final Integer value) {

        return Arrays.stream(Coin.values()).filter(e -> e.denomination == value).findFirst().get();
    }
}
