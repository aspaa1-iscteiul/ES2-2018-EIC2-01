package jMetal.binaryConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder.EvolutionStrategyVariant;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

import jMetal.JMetalAlgorithms;

public class BinaryAlgorithms extends JMetalAlgorithms {

    private static SinglePointCrossover crossoverOperator() {
	return new SinglePointCrossover(1.0);
    }

    private static BitFlipMutation mutationOperator(BinaryProblem problem) {
	return new BitFlipMutation(1.0 / problem.getNumberOfBits(0));
    }

    /*
     * Multi Objective
     */
    // TODO PESA2 and NSGAIII not working
    public static final List<String> MULTI_OBJECTIVE = Arrays.asList("MOCell", "MOCHC", "NSGAII", // "NSGAIII", "PESA2",
	    "PAES", "RandomSearch", "SMSEMOA", "SPEA2");

    @SuppressWarnings("unchecked")
    public Algorithm<List<BinarySolution>> getMultiObjectiveAlgortihm(String algorithmName, BinaryProblem problem) {
	if (!MULTI_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid multiobjective algorithm for BinaryProblem");

	try {
	    Method method = getClass().getMethod("get" + algorithmName, BinaryProblem.class);
	    return (Algorithm<List<BinarySolution>>) method.invoke(problem);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public Algorithm<List<BinarySolution>> getMOCell(BinaryProblem problem) {
	return new MOCellBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<BinarySolution>> getMOCHC(BinaryProblem problem) {
	return new MOCHCBuilder(problem).setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<BinarySolution>> getNSGAII(BinaryProblem problem) {
	return new NSGAIIBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    // TODO not working
    public Algorithm<List<BinarySolution>> getNSGAIII(BinaryProblem problem) {
	return new NSGAIIIBuilder<>(problem).setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE)
		.build();
    }

    public Algorithm<List<BinarySolution>> getPAES(BinaryProblem problem) {
	return new PAESBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    // TODO not working
    public Algorithm<List<BinarySolution>> getPESA2(BinaryProblem problem) {
	return new PESA2Builder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<BinarySolution>> getRandomSearch(BinaryProblem problem) {
	return new RandomSearchBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<List<BinarySolution>> getSMSEMOA(BinaryProblem problem) {
	return new SMSEMOABuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<BinarySolution>> getSPEA2(BinaryProblem problem) {
	return new SPEA2Builder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    /*
     * Single Objective
     */

    public static final List<String> SINGLE_OBJECTIVE = Arrays.asList("EvolutionStrategyElitist",
	    "EvolutionStrategyNonElitist", "GeneticAlgorithm");

    @SuppressWarnings("unchecked")
    public Algorithm<BinarySolution> getSingleObjectiveAlgortihm(String algorithmName, BinaryProblem problem) {
	if (!SINGLE_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid singleobjective algorithm for BinaryProblem");

	try {
	    Method method = getClass().getMethod("get" + algorithmName, BinaryProblem.class);
	    return (Algorithm<BinarySolution>) method.invoke(problem);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public Algorithm<BinarySolution> getEvolutionStrategyElitist(BinaryProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<BinarySolution> getEvolutionStrategyNonElitist(BinaryProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.NON_ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<BinarySolution> getGeneticAlgorithm(BinaryProblem problem) {
	return new GeneticAlgorithmBuilder<>(problem, crossoverOperator(), mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }
}
