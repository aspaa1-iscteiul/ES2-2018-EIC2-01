package jMetal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
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

import objects.Problem;

public class JMetalAutomaticConfiguration {

    private static final int INDEPENDENT_RUNS = 5;

    public JMetalAutomaticConfiguration(Problem problem) throws IOException {
	String experimentBaseDirectory = "experimentBaseDirectory";

	List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
	problemList.add(new ExperimentProblem<>(new JMetalDoubleProblem(problem)));

	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(
		problemList);

	Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
		"AntiSpamFilterAutomaticConfigurationStudy").setAlgorithmList(algorithmList).setProblemList(problemList)
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

    private static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
	    List<ExperimentProblem<DoubleSolution>> problemList) {
	List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

	for (int i = 0; i < problemList.size(); i++) {
	    Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
		    new SBXCrossover(1.0, 5),
		    new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
			    .setMaxEvaluations(500).setPopulationSize(100).build();
	    algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
	}

	return algorithms;
    }

}
