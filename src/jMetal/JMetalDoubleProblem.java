package jMetal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import objects.DecisionVariable;
import objects.FitnessFunction;
import objects.OptimizationCriteria;
import objects.Problem;

@SuppressWarnings("serial")
public class JMetalDoubleProblem extends AbstractDoubleProblem implements JMetalProblem {

    private static final int INDEPENDENT_RUNS = 4, MAX_EVALUATIONS = 2000;

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
    public void run() throws IOException {
	String experimentBaseDirectory = "experimentBaseDirectory";

	List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
	problemList.add(new ExperimentProblem<>(this));

	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(
		problemList);

	Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
		getName()).setAlgorithmList(algorithmList).setProblemList(problemList)
			.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
			.setOutputParetoSetFileName("VAR")
			.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
			.setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
			.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

	new ExecuteAlgorithms<>(experiment).run();
	new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
	new ComputeQualityIndicators<>(experiment).run();
	new GenerateLatexTablesWithStatistics(experiment).run();
	new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
    }

    private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
	    List<ExperimentProblem<DoubleSolution>> problemList) {
	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

	for (int i = 0; i < problemList.size(); i++) {
	    Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
		    new SBXCrossover(1.0, 5),
		    new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
			    .setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(100).build();
	    algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
	}

	return algorithms;
    }

}