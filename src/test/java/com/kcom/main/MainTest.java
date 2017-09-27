package com.kcom.main;

import com.kcom.services.exceptions.InventoryException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MainTest {

    private VendingMachineLauncher vendingMachine = new VendingMachineLauncher();


    @Test
    public void validateThatThrowsExceptionIfInputParameterServiceIsProvided() {
        assertThatThrownBy(() -> vendingMachine.main(new String[]{null, "1"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("Change service selected 1-OptimalChangeFor , 2-ChangeFor  in field %s", 1));
    }

    @Test
    public void validateThatThrowsExceptionIfInputParameterPenceIsProvided() {
        assertThatThrownBy(() -> vendingMachine.main(new String[]{"OptimalChangeFor", null}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(String.format("Not pence provided in field %s.", 2)));
    }

    @Test
    public void validateThatThrowsExceptionIfInputParameterAreNull() {
        assertThatThrownBy(() -> vendingMachine.main(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not input parameters introduced 1- Service (OptimalChangeFor or ChangeFor) 2- Pence");
    }

    @Test
    public void validateThatThrowsExceptionIfInputParameterAreEmpty() {
        assertThatThrownBy(() -> vendingMachine.main(new String[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not input parameters introduced 1- Service (OptimalChangeFor or ChangeFor) 2- Pence");
    }


    @Test
    public void validateThatThrowsInventoryExceptionIfInsuficientChange() {
        assertThatThrownBy(() -> vendingMachine.main(new String[]{"2",  "5000"}))
                .isInstanceOf(InventoryException.class)
                .hasMessage("Insufficient Coinage.");
    }
}