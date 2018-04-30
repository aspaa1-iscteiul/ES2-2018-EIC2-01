package jarTests.integerTests;

import java.io.IOException;

public class TestInteger {

    public static int evaluate(int[] solutions) {
	int sum = 0;
	for (int index = 0; index < solutions.length; index += 2)
	    sum += solutions[index];
	return sum;
    }

    public static int evaluate2(int[] solutions) {
	int sum = 0;
	for (int index = 1; index < solutions.length; index += 2)
	    sum += solutions[index];
	return sum;
    }

    public static void main(String... args) throws IOException {
	int[] solutions = new int[args.length - 1];
	for (int index = 0; index < solutions.length; index++)
	    solutions[index] = Integer.parseInt(args[index + 1]);

	if (args[0].equals("1")) {
	    System.out.println(evaluate(solutions));
	} else {
	    System.out.println(evaluate2(solutions));
	}
    }

}