package jMetal.binaryConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import jMetal.JMetalProblem;
import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class MyBinaryProblem extends AbstractBinaryProblem implements JMetalProblem {

    private ArrayList<FitnessFunction> fitnessFunctions;

    private BinaryAlgorithms algorithms = new BinaryAlgorithms();
    private boolean isSingleObjective;

    public MyBinaryProblem(Problem problem, boolean isSingleObjective) {
	this.isSingleObjective = isSingleObjective;
	
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

	// TODO
	// setLowerLimit(lowerLimit);
	// setUpperLimit(upperLimit);
    }

    @Override
    protected int getBitsPerVariable(int index) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void evaluate(BinarySolution solution) {
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

    @Override
    public ExperimentBuilder<?, ?> configure(ArrayList<String> algorithmsNames) {
	ExperimentProblem<BinarySolution> experimentProblem = new ExperimentProblem<>(this);
	
	// TODO not using single objective
	if(isSingleObjective) {
	    configureSingleObjectiveAlgorithmList(experimentProblem, algorithmsNames);
	}

	List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureAlgorithmList(experimentProblem,
		algorithmsNames);

	return new ExperimentBuilder<BinarySolution, List<BinarySolution>>(getName()).setAlgorithmList(algorithmList)
		.setProblemList(Arrays.asList(experimentProblem));
    }

    private List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureAlgorithmList(
	    ExperimentProblem<BinarySolution> experimentProblem, ArrayList<String> algorithmsNames) {
	List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithms = new ArrayList<>();

	BinaryProblem problem = (BinaryProblem) experimentProblem.getProblem();

	for (String algorithmName : algorithmsNames) {
	    algorithms.add(new ExperimentAlgorithm<>(this.algorithms.getMultiObjectiveAlgortihm(algorithmName, problem),
		    algorithmName, problem.getName()));
	}

	return algorithms;
    }

    private List<ExperimentAlgorithm<BinarySolution, BinarySolution>> configureSingleObjectiveAlgorithmList(
	    ExperimentProblem<BinarySolution> experimentProblem, ArrayList<String> algorithmsNames) {
	List<ExperimentAlgorithm<BinarySolution, BinarySolution>> algorithms = new ArrayList<>();

	BinaryProblem problem = (BinaryProblem) experimentProblem.getProblem();

	for (String algorithmName : algorithmsNames) {
	    algorithms.add(new ExperimentAlgorithm<BinarySolution, BinarySolution>(
		    this.algorithms.getSingleObjectiveAlgortihm(algorithmName, problem), algorithmName,
		    problem.getName()));
	}

	return algorithms;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PISAHypervolume<BinarySolution> type() {
	return new PISAHypervolume<BinarySolution>();
    }

}
