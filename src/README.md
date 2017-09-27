**KCom Test**

Author: Jan Deulofe


This project is build using maven.


**Build Project:**

mvn clean install

**Run all the tests:**

mvn test

**To run the application:**

The application have a Main class which launches the service OptimalChangeFor or ChangeFor

The application expects the parameters:

1- Change Service: 1: OptimalChangeFor 2:ChangeFor
2- The pence

The application will throw an IllegalArgumentException if the input parameters are incorrect, informing the user which was the cause of it.

The coin-inventory.properties is deployed on the jar

**Example:**

java -cp kcom-1.0-SNAPSHOT.jar com.kcom.main.VendingMachineLauncher "1" "250"

Result for OptimalChangeFor with Pence [250]: 
Coin: POUND
Coin: POUND
Coin: FIFTY





