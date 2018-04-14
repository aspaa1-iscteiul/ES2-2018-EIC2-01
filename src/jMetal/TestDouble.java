package jMetal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestDouble {

    public static double evaluate(double[] solutions) {
	double sum = 0;
	for (int index = 0; index < solutions.length; index++)
	    sum += solutions[index];
	return sum;
    }

    public static void main(String... args) throws IOException {
	double[] solutions = new double[args.length - 1];
	for (int index = 0; index < solutions.length; index++)
	    solutions[index] = Double.parseDouble(args[index + 1]);

	FileWriter fileWriter = new FileWriter(new File(args[0]));
	fileWriter.write(evaluate(solutions) + "");
	fileWriter.close();
    }
}
