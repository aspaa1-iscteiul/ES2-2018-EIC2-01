package jMetal.integerConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import jMetal.JMetalProblem;
import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class MyIntegerProblem extends AbstractIntegerProblem implements JMetalProblem {

    private ArrayList<FitnessFunction> fitnessFunctions;

    private IntegerAlgorithms algorithms = new IntegerAlgorithms();
    private boolean isSingleObjective;
    private int evaluateIteration = 0;

    public MyIntegerProblem(Problem problem, boolean isSingleObjective) {
	this.isSingleObjective = isSingleObjective;

	setName(problem.getProblemName());

	ArrayList<DecisionVariable> list = problem.getDecisionVariables();
	setNumberOfVariables(list.size());

	int sum = 0;
	fitnessFunctions = new ArrayList<>(problem.getFitnessFunctions());
	for (FitnessFunction fitness : fitnessFunctions)
	    sum += fitness.getOptimizationCriteria().size();
	setNumberOfObjectives(sum);

	setLowerLimit(Collections.nCopies(getNumberOfVariables(),
		Integer.parseInt(problem.getDecisionVariablesLowerBound())));
	setUpperLimit(Collections.nCopies(getNumberOfVariables(),
		Integer.parseInt(problem.getDecisionVariablesUpperBound())));
    }

    @Override
    public void evaluate(IntegerSolution solution) {
	evaluateIteration++;

	String[] args = new String[4 + solution.getNumberOfVariables()];
	args[0] = "java";
	args[1] = "-jar";
	for (int index = 0; index < solution.getNumberOfVariables(); index++)
	    args[4 + index] = solution.getVariableValueString(index);

	try {
	    int index = 0;
	    for (FitnessFunction f : fitnessFunctions) {
		args[2] = f.getJarFilePath();
		for (OptimizationCriteria o : f.getOptimizationCriteria()) {
		    args[3] = o.getName();

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

    @Override
    public ExperimentBuilder<?, ?> configure(ArrayList<String> algorithmsNames) {
	ExperimentProblem<IntegerSolution> experimentProblem = new ExperimentProblem<>(this);

	// TODO not using single objective
	if (isSingleObjective) {
	    configureSingleObjectiveAlgorithmList(experimentProblem, algorithmsNames);
	}

	List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = configureMultiObjectiveAlgorithmList(
		experimentProblem, algorithmsNames);

	return new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>(getName()).setAlgorithmList(algorithmList)
		.setProblemList(Arrays.asList(experimentProblem));
    }

    private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureMultiObjectiveAlgorithmList(
	    ExperimentProblem<IntegerSolution> experimentProblem, ArrayList<String> algorithmsNames) {
	List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();

	IntegerProblem problem = (IntegerProblem) experimentProblem.getProblem();

	for (String algorithmName : algorithmsNames) {
	    algorithms.add(new ExperimentAlgorithm<>(this.algorithms.getMultiObjectiveAlgortihm(algorithmName, problem),
		    algorithmName, problem.getName()));
	}

	return algorithms;
    }

    private List<ExperimentAlgorithm<IntegerSolution, IntegerSolution>> configureSingleObjectiveAlgorithmList(
	    ExperimentProblem<IntegerSolution> experimentProblem, ArrayList<String> algorithmsNames) {
	List<ExperimentAlgorithm<IntegerSolution, IntegerSolution>> algorithms = new ArrayList<>();

	IntegerProblem problem = (IntegerProblem) experimentProblem.getProblem();

	for (String algorithmName : algorithmsNames) {
	    algorithms.add(new ExperimentAlgorithm<IntegerSolution, IntegerSolution>(
		    this.algorithms.getSingleObjectiveAlgortihm(algorithmName, problem), algorithmName,
		    problem.getName()));
	}

	return algorithms;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PISAHypervolume<IntegerSolution> type() {
	return new PISAHypervolume<IntegerSolution>();
    }

    @Override
    public int evaluateIteration() {
	return evaluateIteration;
    }

}