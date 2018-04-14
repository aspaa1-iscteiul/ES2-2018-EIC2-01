package jMetal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class JMetalDoubleProblem extends AbstractDoubleProblem {

    private ArrayList<FitnessFunction> fitnessFunctions;

    public JMetalDoubleProblem(Problem problem) {
	setName(problem.getProblemName());

	ArrayList<DecisionVariable> list = problem.getDecisionVariables();
	setNumberOfVariables(list.size());

	int sum = 0;
	fitnessFunctions = new ArrayList<>(problem.getFitnessFunctions());
	for (FitnessFunction fitness : fitnessFunctions)
	    sum += fitness.optimizationCriteria.size();
	setNumberOfObjectives(sum);

	List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()),
		upperLimit = new ArrayList<>(getNumberOfVariables());

	for (int index = 0; index < getNumberOfVariables(); index++) {
	    lowerLimit.add(Double.parseDouble(list.get(index).lowerBound));
	    upperLimit.add(Double.parseDouble(list.get(index).upperBound));
	}

	setLowerLimit(lowerLimit);
	setUpperLimit(upperLimit);
    }

    @Override
    public void evaluate(DoubleSolution solution) {
	String[] args = new String[4 + solution.getNumberOfVariables()];
	args[0] = "java";
	args[1] = "-jar";
	args[2] = "";
	args[3] = "";
	for (int index = 0; index < solution.getNumberOfVariables(); index++)
	    args[4 + index] = solution.getVariableValueString(index);

	try {
	    int index = 0;
	    for (FitnessFunction f : fitnessFunctions) {
		args[2] = f.jarFilePath;
		for (OptimizationCriteria o : f.optimizationCriteria) {
		    args[3] = "./" + getName() + "_objectives/" + o.name + ".txt";
		    File file = new File(args[3]);
		    file.createNewFile();

		    new ProcessBuilder(args).start().waitFor();

		    Scanner scn = new Scanner(file);
		    solution.setObjective(index, Double.parseDouble(scn.nextLine()));
		    scn.close();
		    index++;
		}
	    }
	} catch (IOException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}