package jMetal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;

public interface JMetalProblem {

    public static final int INDEPENDENT_RUNS = 4;

    public int evaluateIteration();

    public default void run(ArrayList<String> algorithmsNames) throws IOException {
	execute(configure(algorithmsNames));
    }

    public ExperimentBuilder<?, ?> configure(ArrayList<String> algorithmsNames);

    public default void execute(ExperimentBuilder<?, ?> experimentBuilder) throws IOException {
	String experimentBaseDirectory = "experimentBaseDirectory";

	Experiment<?, ?> experiment = experimentBuilder.setExperimentBaseDirectory(experimentBaseDirectory)
		.setOutputParetoFrontFileName("FUN").setOutputParetoSetFileName("VAR")
		.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
		.setIndicatorList(Arrays.asList(type())).setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8)
		.build();

	new ExecuteAlgorithms<>(experiment).run();
	new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
	new ComputeQualityIndicators<>(experiment).run();
	new GenerateLatexTablesWithStatistics(experiment).run();
	new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
    }

    public <T extends Solution<?>> PISAHypervolume<T> type();

}
