package jarTests.doubleTests;

import java.io.IOException;

public class TestDouble {

    public static void main(String... args) throws IOException {
	double sum1 = 0, sum2 = 0;
	for (int index = 0; index < args.length; index++) {
	    double value = Double.parseDouble(args[index]);
	    if (index % 2 == 0)
		sum1 += value;
	    else
		sum2 += value;
	}
	System.out.println(sum1 + " " + sum2);
    }

}