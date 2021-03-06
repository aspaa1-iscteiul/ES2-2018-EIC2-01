package jMetal.doubleConfiguration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder.EvolutionStrategyVariant;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import jMetal.JMetalAlgorithms;

public class DoubleAlgorithms extends JMetalAlgorithms {

    private static SBXCrossover crossoverOperator() {
	return new SBXCrossover(1.0, 5);
    }

    private static PolynomialMutation mutationOperator(DoubleProblem problem) {
	return new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 10.0);
    }

    /*
     * Multi Objective
     */

    // TODO PESA2 and NSGAIII not working
    public static final ArrayList<String> MULTI_OBJECTIVE = new ArrayList<>(Arrays.asList("DMOPSO", "GDE3", "IBEA",
	    "MOCell", "ConstraintMOEAD", "MOEAD", "MOEADD", "MOEADDRA", "MOEADSTM", "NSGAII", "PAES", // "NSGAIII",
												      // "PESA2",
	    "RandomSearch", "SMSEMOA", "SPEA2"));

    @SuppressWarnings("unchecked")
    public Algorithm<List<DoubleSolution>> getMultiObjectiveAlgortihm(String algorithmName, DoubleProblem problem)
	    throws Exception {
	if (!MULTI_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid multiobjective algorithm for DoubleProblem");

	Method method = getClass().getMethod("get" + algorithmName, DoubleProblem.class);
	return (Algorithm<List<DoubleSolution>>) method.invoke(this, problem);
    }

    public Algorithm<List<DoubleSolution>> getDMOPSO(DoubleProblem problem) {
	return new DMOPSOBuilder(problem).setMaxIterations(MAX_EVALUATIONS).setSwarmSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getGDE3(DoubleProblem problem) {
	return new GDE3Builder(problem).setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getIBEA(DoubleProblem problem) {
	return new IBEABuilder(problem).setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getMOCell(DoubleProblem problem) {
	return new MOCellBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getConstraintMOEAD(DoubleProblem problem) {
	return new MOEADBuilder(problem, Variant.ConstraintMOEAD).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getMOEAD(DoubleProblem problem) {
	return new MOEADBuilder(problem, Variant.MOEAD).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getMOEADD(DoubleProblem problem) {
	return new MOEADBuilder(problem, Variant.MOEADD).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getMOEADDRA(DoubleProblem problem) {
	return new MOEADBuilder(problem, Variant.MOEADDRA).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getMOEADSTM(DoubleProblem problem) {
	return new MOEADBuilder(problem, Variant.MOEADSTM).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getNSGAII(DoubleProblem problem) {
	return new NSGAIIBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    // TODO not working
    public Algorithm<List<DoubleSolution>> getNSGAIII(DoubleProblem problem) {
	return new NSGAIIIBuilder<>(problem).setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE)
		.build();
    }

    public Algorithm<List<DoubleSolution>> getPAES(DoubleProblem problem) {
	return new PAESBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    // TODO not working
    public Algorithm<List<DoubleSolution>> getPESA2(DoubleProblem problem) {
	return new PESA2Builder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getRandomSearch(DoubleProblem problem) {
	return new RandomSearchBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<List<DoubleSolution>> getSMSEMOA(DoubleProblem problem) {
	return new SMSEMOABuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<DoubleSolution>> getSPEA2(DoubleProblem problem) {
	return new SPEA2Builder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    /*
     * Single Objective
     */

    public static final ArrayList<String> SINGLE_OBJECTIVE = new ArrayList<>(Arrays.asList("DifferentialEvolution",
	    "EvolutionStrategyElitist", "EvolutionStrategyNonElitist", "GeneticAlgorithm"));

    @SuppressWarnings("unchecked")
    public Algorithm<DoubleSolution> getSingleObjectiveAlgortihm(String algorithmName, DoubleProblem problem)
	    throws Exception {
	if (!SINGLE_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid singleobjective algorithm for DoubleProblem");

	Method method = getClass().getMethod("get" + algorithmName, DoubleProblem.class);
	return (Algorithm<DoubleSolution>) method.invoke(problem);
    }

    public Algorithm<DoubleSolution> getDifferentialEvolution(DoubleProblem problem) {
	return new DifferentialEvolutionBuilder(problem).setMaxEvaluations(MAX_EVALUATIONS)
		.setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<DoubleSolution> getEvolutionStrategyElitist(DoubleProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<DoubleSolution> getEvolutionStrategyNonElitist(DoubleProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.NON_ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<DoubleSolution> getGeneticAlgorithm(DoubleProblem problem) {
	return new GeneticAlgorithmBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

}
