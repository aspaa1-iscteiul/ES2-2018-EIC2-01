package jMetal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class JMetalIntegerProblem extends AbstractIntegerProblem {

    private ArrayList<FitnessFunction> fitnessFunctions;

    public JMetalIntegerProblem(Problem problem) {
	setName(problem.getProblemName());

	ArrayList<DecisionVariable> list = problem.getDecisionVariables();
	setNumberOfVariables(list.size());

	int sum = 0;
	fitnessFunctions = new ArrayList<>(problem.getFitnessFunctions());
	for (FitnessFunction fitness : fitnessFunctions)
	    sum += fitness.optimizationCriteria.size();
	setNumberOfObjectives(sum);

	List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()),
		upperLimit = new ArrayList<>(getNumberOfVariables());

	for (int index = 0; index < getNumberOfVariables(); index++) {
	    lowerLimit.add(Integer.parseInt(list.get(index).lowerBound));
	    upperLimit.add(Integer.parseInt(list.get(index).upperBound));
	}

	setLowerLimit(lowerLimit);
	setUpperLimit(upperLimit);
    }

    @Override
    public void evaluate(IntegerSolution solution) {
	String[] args = new String[4 + solution.getNumberOfVariables()];
	args[0] = "java";
	args[1] = "-jar";
	for (int index = 0; index < solution.getNumberOfVariables(); index++)
	    args[4 + index] = solution.getVariableValueString(index);

	try {
	    int index = 0;
	    for (FitnessFunction f : fitnessFunctions) {
		args[2] = f.jarFilePath;
		for (OptimizationCriteria o : f.optimizationCriteria) {
		    args[3] = o.name;

		    Process p = new ProcessBuilder(args).start();
		    String output = IOUtils.toString(p.getInputStream());
		    p.waitFor();

		    solution.setObjective(index, Integer.parseInt(output));
		    index++;
		}
	    }
	} catch (IOException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}