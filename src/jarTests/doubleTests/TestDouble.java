package jarTests.doubleTests;

import java.io.IOException;

public class TestDouble {

    public static double evaluate(double[] solutions, boolean plus) {
	double sum = 0;
	for (int index = 0; index < solutions.length; index++)
	    sum += (plus ? -1 : 1) * solutions[index];
	return sum;
    }

    public static void main(String... args) throws IOException {
	double[] solutions = new double[args.length - 1];
	for (int index = 0; index < solutions.length; index++)
	    solutions[index] = Double.parseDouble(args[index + 1]);

	System.out.println(evaluate(solutions, args[0].equals("1")));
    }

}