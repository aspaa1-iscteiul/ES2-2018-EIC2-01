package jarTests.doubleTests;

import java.io.IOException;

public class TestDouble {

    public static double evaluate(double[] solutions) {
	double sum = 0;
	for (int index = 0; index < solutions.length; index += 2)
	    sum += solutions[index];
	return sum;
    }

    public static double evaluate2(double[] solutions) {
	double sum = 0;
	for (int index = 1; index < solutions.length; index += 2)
	    sum += solutions[index];
	return sum;
    }

    public static void main(String... args) throws IOException {
	double[] solutions = new double[args.length - 1];
	for (int index = 0; index < solutions.length; index++)
	    solutions[index] = Double.parseDouble(args[index + 1]);

	if (args[0].equals("1")) {
	    System.out.println(evaluate(solutions));
	} else {
	    System.out.println(evaluate2(solutions));
	}
    }

}