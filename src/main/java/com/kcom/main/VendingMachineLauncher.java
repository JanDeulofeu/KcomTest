package com.kcom.main;

import com.kcom.services.change.ChangeCalculator;
import com.kcom.services.change.ChangeCalculatorService;


/**
 * @author jan.deulofeu
 */
public class VendingMachineLauncher {

    private static Integer service = 0;
    private static Integer pence = 0;

    private static ChangeCalculatorService changeCalculatorService = new ChangeCalculator() ;

    public static void main(final String[] args) throws Exception {


        new VendingMachineLauncher().validateInputParameters(args);


        switch (service)
        {
            case 1:
                System.out.println(String.format("Result for OptimalChangeFor with Pence [%s]: ", pence));
                changeCalculatorService.getOptimalChangeFor(pence).stream().forEach(coin -> System.out.println(String.format("Coin: %s", coin)));
                break;
            case 2:
                System.out.println(String.format("Result for ChangeFor with Pence [%s]: ", pence));
                changeCalculatorService.getChangeFor(pence).stream().forEach(coin -> System.out.println(String.format("Coin: %s", coin)));
                break;
        }
    }

    private void validateInputParameters(final String[] args)
    {
        if(args == null || args.length == 0)
        {
            throw new IllegalArgumentException("Not input parameters introduced 1- Service (OptimalChangeFor or ChangeFor) 2- Pence");
        }

        if(args[0] == null)
        {
            throw new IllegalArgumentException(String.format("Change service selected 1-OptimalChangeFor , 2-ChangeFor  in field %s", 1));
        }

        if(args[1] == null)
        {
            throw new IllegalArgumentException(String.format("Not pence provided in field %s.", 2));
        }

        service = Integer.valueOf(args[0]);
        pence = Integer.valueOf(args[1]);
    }
}
