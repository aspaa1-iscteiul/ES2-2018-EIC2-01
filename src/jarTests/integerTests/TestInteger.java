package jarTests.integerTests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestInteger {

    public static int evaluate(int[] solutions) {
	int sum = 0;
	for (int index = 0; index < solutions.length; index++)
	    sum += solutions[index];
	return sum;
    }

    public static void main(String... args) throws IOException {
	int[] solutions = new int[args.length - 1];
	for (int index = 0; index < solutions.length; index++)
	    solutions[index] = Integer.parseInt(args[index + 1]);

	FileWriter fileWriter = new FileWriter(new File(args[0]));
	fileWriter.write(evaluate(solutions) + "");
	fileWriter.close();
    }

}