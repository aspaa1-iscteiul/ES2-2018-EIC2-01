package jMetal.integerConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
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
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import jMetal.JMetalAlgorithms;

public class IntegerAlgorithms extends JMetalAlgorithms {

    private static final IntegerSBXCrossover crossoverOperator = new IntegerSBXCrossover(0.9, 20.0);

    private static IntegerPolynomialMutation mutationOperator(IntegerProblem problem) {
	return new IntegerPolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0);
    }

    /*
     * Multi Objective
     */

    // TODO PESA2 and NSGAIII not working
    public static final List<String> MULTI_OBJECTIVE = Arrays.asList("MOCell", "NSGAII", "PAES", // "NSGAIII", "PESA2",
	    "RandomSearch", "SMSEMOA", "SPEA2");

    @SuppressWarnings("unchecked")
    public Algorithm<List<IntegerSolution>> getMultiObjectiveAlgortihm(String algorithmName, IntegerProblem problem) {
	if (!MULTI_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid multiobjective algorithm for IntegerProblem");

	try {
	    Method method = getClass().getMethod("get" + algorithmName, IntegerProblem.class);
	    return (Algorithm<List<IntegerSolution>>) method.invoke(problem);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public Algorithm<List<IntegerSolution>> getMOCell(IntegerProblem problem) {
	return new MOCellBuilder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<IntegerSolution>> getNSGAII(IntegerProblem problem) {
	return new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    // TODO not working
    public Algorithm<List<IntegerSolution>> getNSGAIII(IntegerProblem problem) {
	return new NSGAIIIBuilder<>(problem).setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE)
		.build();
    }

    public Algorithm<List<IntegerSolution>> getPAES(IntegerProblem problem) {
	return new PAESBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    // TODO not working
    public Algorithm<List<IntegerSolution>> getPESA2(IntegerProblem problem) {
	return new PESA2Builder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<IntegerSolution>> getRandomSearch(IntegerProblem problem) {
	return new RandomSearchBuilder<>(problem).setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<List<IntegerSolution>> getSMSEMOA(IntegerProblem problem) {
	return new SMSEMOABuilder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    public Algorithm<List<IntegerSolution>> getSPEA2(IntegerProblem problem) {
	return new SPEA2Builder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxIterations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

    /*
     * Single Objective
     */

    public static final List<String> SINGLE_OBJECTIVE = Arrays.asList("EvolutionStrategyElitist",
	    "EvolutionStrategyNonElitist", "GeneticAlgorithm");

    @SuppressWarnings("unchecked")
    public Algorithm<IntegerSolution> getSingleObjectiveAlgortihm(String algorithmName, IntegerProblem problem) {
	if (!SINGLE_OBJECTIVE.contains(algorithmName))
	    throw new IllegalArgumentException(
		    algorithmName + " is not a valid singleobjective algorithm for IntegerProblem");

	try {
	    Method method = getClass().getMethod("get" + algorithmName, IntegerProblem.class);
	    return (Algorithm<IntegerSolution>) method.invoke(problem);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public Algorithm<IntegerSolution> getEvolutionStrategyElitist(IntegerProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<IntegerSolution> getEvolutionStrategyNonElitist(IntegerProblem problem) {
	return new EvolutionStrategyBuilder<>(problem, mutationOperator(problem), EvolutionStrategyVariant.NON_ELITIST)
		.setMaxEvaluations(MAX_EVALUATIONS).build();
    }

    public Algorithm<IntegerSolution> getGeneticAlgorithm(IntegerProblem problem) {
	return new GeneticAlgorithmBuilder<>(problem, crossoverOperator, mutationOperator(problem))
		.setMaxEvaluations(MAX_EVALUATIONS).setPopulationSize(POPULATION_SIZE).build();
    }

}
