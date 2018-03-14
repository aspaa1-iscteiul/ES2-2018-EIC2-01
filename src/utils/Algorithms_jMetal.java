package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSS;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.algorithm.multiobjective.cellde.CellDE45;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.gwasfga.GWASFGA;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCell;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHC;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEAD;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.mombi.MOMBI;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.omopso.OMOPSO;
import org.uma.jmetal.algorithm.multiobjective.omopso.OMOPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSO.FunctionType;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.LocalSearchOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.mutation.NonUniformMutation;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.Neighborhood;

public class Algorithms_jMetal<S> {

    ArrayList<String> algorithms = new ArrayList<>();

    public static void main(String[] args) {
	System.out.println("Note: DoubleSolution <=> Solution<Double> && BinarySolution <=> Solution<BinarySet>\n\n");
	System.out.println("Multiobjective Algortihms:");
	System.out.println("\t-> ABYSS (DoubleSolution) ; Builder");
	System.out.println("\t-> CellDE45 (DoubleSolution)");
	System.out.println("\t-> DMOPSO (DoubleSolution) ; Builder");
	System.out.println("\t-> GDE3 (DoubleSolution) ; Builder");
	System.out.println("\t-> GWASFGA (Solution<?>)");
	System.out.println("\t-> IBEA (Solution<?>) ; Builder (DoubleSolution)");
	System.out.println("\t-> MOCell (Solution<?>) ; Builder");
	System.out.println("\t-> MOCHC (BinarySolution) ; Builder");
	System.out.println("\t-> MOEAD (DoubleSolution) ; Builder");
	System.out.println("\t-> MOMBI (Solution<?>)");
	System.out.println("\t-> NSGAII (Solution<?>) ; Builder");
	System.out.println("\t-> NSGAIII (Solution<?>) ; Builder");
	System.out.println("\t-> OMOPSO (DoubleSolution) ; Builder");
	System.out.println("\t-> PAES (Solution<?>) ; Builder");
	System.out.println("\t-> PAES2 (Solution<?>) ; Builder");
	System.out.println("\t-> RandomSearch (Solution<?>) ; Builder");
	System.out.println("\t-> RNSGAII (Solution<?>) ; Builder");
	System.out.println("\t-> SMPSO (DoubleSolution) ; Builder");
	System.out.println("\t-> SMSEMOA (Solution<?>) ; Builder");
	System.out.println("\t-> SPEA2 (Solution<?>) ; Builder");
	System.out.println("\t-> WASFGA (Solution<?>)");
	System.out.println("\n\nSingleobjective Algorithms:");
	System.out.println("\t-> CoralReefsOptimization (Solution<?>) ; Builder");
	System.out.println("\t-> DifferentialEvolution (DoubleSolution) ; Builder");
	System.out.println("\t-> ElitistEvolutionStrategy OR NonElitistEvolutionStrategy (Solution<?>) ; Builder");
	System.out.println("\t-> GenerationalGeneticAlgorithm OR SteadyStateGeneticAlgorithm (Solution<?>) ; Builder");
    }

    public ABYSS createABYSS(boolean builder) {
	int numberOfSubRanges = 0;
	CrossoverOperator<DoubleSolution> crossoverOperator = null;
	LocalSearchOperator<DoubleSolution> localSearch = null;
	Archive<DoubleSolution> archive = null;
	int archiveSize = 0;
	int referenceSet2Size = 0;
	int referenceSet1Size = 0;
	int populationSize = 0;
	int maxEvaluations = 0;
	DoubleProblem problem = null;

	if (!builder)
	    return new ABYSS(problem, maxEvaluations, populationSize, referenceSet1Size, referenceSet2Size, archiveSize,
		    archive, localSearch, crossoverOperator, numberOfSubRanges);
	else
	    return new ABYSSBuilder(problem, archive).build();
    }

    public CellDE45 createCellDE45() {
	SolutionListEvaluator<DoubleSolution> evaluator = null;
	double feedback = 0;
	DifferentialEvolutionCrossover crossover = null;
	SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = null;
	Neighborhood<DoubleSolution> neighborhood = null;
	BoundedArchive<DoubleSolution> archive = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<DoubleSolution> problem = null;

	return new CellDE45(problem, maxEvaluations, populationSize, archive, neighborhood, selection, crossover,
		feedback, evaluator);
    }

    public DMOPSO createnDMOPSO(boolean builder) {
	int maxAge = 0;
	String dataDirectory = null;
	FunctionType functionType = null;
	double changeVelocity2 = 0;
	double changeVelocity1 = 0;
	double weightMax = 0;
	double weightMin = 0;
	double c2Max = 0;
	double c2Min = 0;
	double c1Max = 0;
	double c1Min = 0;
	double r2Max = 0;
	double r2Min = 0;
	double r1Max = 0;
	double r1Min = 0;
	int maxIterations = 0;
	int swarmSize = 0;
	DoubleProblem problem = null;

	if (!builder)
	    return new DMOPSO(problem, swarmSize, maxIterations, r1Min, r1Max, r2Min, r2Max, c1Min, c1Max, c2Min, c2Max,
		    weightMin, weightMax, changeVelocity1, changeVelocity2, functionType, dataDirectory, maxAge);
	else
	    return new DMOPSOBuilder(problem).build();
    }

    public GDE3 createDGE3(boolean builder) {
	SolutionListEvaluator<DoubleSolution> evaluator = null;
	DifferentialEvolutionCrossover crossover = null;
	DifferentialEvolutionSelection selection = null;
	int maxEvaluations = 0;
	int populationSize = 0;
	DoubleProblem problem = null;

	if (!builder)
	    return new GDE3(problem, populationSize, maxEvaluations, selection, crossover, evaluator);
	else
	    return new GDE3Builder(problem).build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GWASFGA<?> createGWASFGA() {
	SolutionListEvaluator<?> evaluator = null;
	SelectionOperator<List<?>, ?> selectionOperator = null;
	MutationOperator<?> mutationOperator = null;
	CrossoverOperator<?> crossoverOperator = null;
	int maxIterations = 0;
	int populationSize = 0;
	Problem<?> problem = null;

	return new GWASFGA(problem, populationSize, maxIterations, crossoverOperator, mutationOperator,
		selectionOperator, evaluator);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public IBEA<?> createIBEA(boolean builder) {
	MutationOperator<?> mutationOperator = null;
	CrossoverOperator<?> crossoverOperator = null;
	SelectionOperator<List<?>, ?> selectionOperator = null;
	int maxEvaluations = 0;
	int archiveSize = 0;
	int populationSize = 0;
	Problem<?> problem = null;

	if (!builder)
	    return new IBEA(problem, populationSize, archiveSize, maxEvaluations, selectionOperator, crossoverOperator,
		    mutationOperator);
	else {
	    Problem<DoubleSolution> problem2 = null;
	    return new IBEABuilder(problem2).build();
	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MOCell<?> createMOCell(boolean builder) {
	SolutionListEvaluator<?> evaluator = null;
	SelectionOperator<List<?>, ?> selectionOperator = null;
	MutationOperator<?> mutationOperator = null;
	CrossoverOperator<?> crossoverOperator = null;
	Neighborhood<?> neighborhood = null;
	BoundedArchive<?> archive = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<?> problem = null;

	if (!builder)
	    return new MOCell(problem, maxEvaluations, populationSize, archive, neighborhood, crossoverOperator,
		    mutationOperator, selectionOperator, evaluator);
	else
	    return new MOCellBuilder(problem, crossoverOperator, mutationOperator).build();
    }

    public MOCHC createMOCHC(boolean builder) {
	SelectionOperator<List<BinarySolution>, BinarySolution> parentSelection = null;
	SolutionListEvaluator<BinarySolution> evaluator = null;
	MutationOperator<BinarySolution> cataclysmicMutation = null;
	SelectionOperator<List<BinarySolution>, List<BinarySolution>> newGenerationSelection = null;
	CrossoverOperator<BinarySolution> crossoverOperator = null;
	double initialConvergenceCount = 0;
	double preservedPopulation = 0;
	int convergenceValue = 0;
	int maxEvaluations = 0;
	int populationSize = 0;
	BinaryProblem problem = null;

	if (!builder)
	    return new MOCHC(problem, populationSize, maxEvaluations, convergenceValue, preservedPopulation,
		    initialConvergenceCount, crossoverOperator, cataclysmicMutation, newGenerationSelection,
		    parentSelection, evaluator);
	else
	    return new MOCHCBuilder(problem).build();
    }

    public AbstractMOEAD<DoubleSolution> createMOEAD(boolean builder) {
	int neighborSize = 0;
	int maximumNumberOfReplacedSolutions = 0;
	double neighborhoodSelectionProbability = 0;
	String dataDirectory = null;
	org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD.FunctionType functionType = null;
	CrossoverOperator<DoubleSolution> crossover = null;
	MutationOperator<DoubleSolution> mutation = null;
	int maxEvaluations = 0;
	int resultPopulationSize = 0;
	int populationSize = 0;
	Problem<DoubleSolution> problem = null;

	if (!builder)
	    return new MOEAD(problem, populationSize, resultPopulationSize, maxEvaluations, mutation, crossover,
		    functionType, dataDirectory, neighborhoodSelectionProbability, maximumNumberOfReplacedSolutions,
		    neighborSize);
	else {
	    Variant variant = null;
	    return new MOEADBuilder(problem, variant).build();
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MOMBI<?> createMOMBI() {
	String pathWeights = null;
	SolutionListEvaluator<?> evaluator = null;
	SelectionOperator<List<?>, ?> selection = null;
	MutationOperator<?> mutation = null;
	CrossoverOperator<?> crossover = null;
	int maxIterations = 0;
	Problem<?> problem = null;

	return new MOMBI(problem, maxIterations, crossover, mutation, selection, evaluator, pathWeights);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public NSGAII<?> createNSGAII(boolean builder) {
	SolutionListEvaluator<?> evaluator = null;
	Comparator<?> dominanceComparator = null;
	SelectionOperator<List<?>, ?> selectionOperator = null;
	MutationOperator<?> mutationOperator = null;
	CrossoverOperator<?> crossoverOperator = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<?> problem = null;
	if (!builder)
	    return new NSGAII(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator,
		    selectionOperator, dominanceComparator, evaluator);
	else
	    return new NSGAIIBuilder(problem, crossoverOperator, mutationOperator).build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public NSGAIII<?> createNSGAIII() {
	Problem<?> problem = null;

	return new NSGAIIIBuilder(problem).build();
    }

    public OMOPSO createOMOPSO(boolean builder) {
	NonUniformMutation nonUniformMutation = null;
	UniformMutation uniformMutation = null;
	int archiveSize = 0;
	int maxIterations = 0;
	int swarmSize = 0;
	SolutionListEvaluator<DoubleSolution> evaluator = null;
	DoubleProblem problem = null;

	if (!builder)
	    return new OMOPSO(problem, evaluator, swarmSize, maxIterations, archiveSize, uniformMutation,
		    nonUniformMutation);
	else
	    return new OMOPSOBuilder(problem, evaluator).build();
    }

}
