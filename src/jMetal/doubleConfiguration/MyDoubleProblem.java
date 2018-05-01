package jMetal.doubleConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import jMetal.JMetalProblem;
import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class MyDoubleProblem extends AbstractDoubleProblem implements JMetalProblem {

    private ArrayList<FitnessFunction> fitnessFunctions;

    private DoubleAlgorithms algorithms = new DoubleAlgorithms();

    public MyDoubleProblem(Problem problem) {
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

		    solution.setObjective(index++, Double.parseDouble(output));
		}
	    }
	} catch (IOException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public ExperimentBuilder<?, ?> configure(ArrayList<String> algorithmsNames) {

	ExperimentProblem<DoubleSolution> problem = new ExperimentProblem<>(this);

	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(problem,
		algorithmsNames);

	return new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(getName()).setAlgorithmList(algorithmList)
		.setProblemList(Arrays.asList(problem));
    }

    private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
	    ExperimentProblem<DoubleSolution> experimentProblem, ArrayList<String> algorithmsNames) {
	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

	DoubleProblem problem = (DoubleProblem) experimentProblem.getProblem();

	for (String algorithmName : algorithmsNames) {
	    algorithms.add(new ExperimentAlgorithm<>(this.algorithms.getMultiObjectiveAlgortihm(algorithmName, problem),
		    algorithmName, problem.getName()));
	}

	return algorithms;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PISAHypervolume<DoubleSolution> type() {
	return new PISAHypervolume<DoubleSolution>();
    }

}