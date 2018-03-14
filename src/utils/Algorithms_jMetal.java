package utils;

import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
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
import org.uma.jmetal.algorithm.multiobjective.paes.PAES;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearch;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.rnsgaii.RNSGAII;
import org.uma.jmetal.algorithm.multiobjective.rnsgaii.RNSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSO;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOA;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.multiobjective.wasfga.WASFGA;
import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimization;
import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimizationBuilder;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolution;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.ElitistEvolutionStrategy;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder.EvolutionStrategyVariant;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.NonElitistEvolutionStrategy;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
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
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.Neighborhood;

public class Algorithms_jMetal<S> {

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
	System.out.println("\t-> PESA2 (Solution<?>) ; Builder");
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

    /*
     * Multi Objective Algorithms
     */

    /**
     * only DoubleSolution and has builder
     */
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

    /**
     * only DoubleSolution and has NO builder
     */
    public CellDE45 createCellDE45() {
	SolutionListEvaluator<DoubleSolution> evaluator = null;
	double feedback = 0;
	DifferentialEvolutionCrossover crossover = null;
	SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = null;
	Neighborhood<DoubleSolution> neighborhood = null;
	BoundedArchive<DoubleSolution> archive = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	DoubleProblem problem = null;

	return new CellDE45(problem, maxEvaluations, populationSize, archive, neighborhood, selection, crossover,
		feedback, evaluator);
    }

    /**
     * only DoubleSolution and has builder
     */
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

    /**
     * only DoubleSolution and has builder
     */
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

    /**
     * Solution<?> and has NO builder
     */
    public GWASFGA<TheType> createGWASFGA() {
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int maxIterations = 0;
	int populationSize = 0;
	Problem<TheType> problem = null;

	return new GWASFGA<TheType>(problem, populationSize, maxIterations, crossoverOperator, mutationOperator,
		selectionOperator, evaluator);
    }

    /**
     * Solution<?> and has builder BUT only for DoubleSolution
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public IBEA<DoubleSolution> createIBEA(boolean builder) {
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	int maxEvaluations = 0;
	int archiveSize = 0;
	int populationSize = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new IBEA(problem, populationSize, archiveSize, maxEvaluations, selectionOperator, crossoverOperator,
		    mutationOperator);
	else {
	    Problem<DoubleSolution> problem2 = null;
	    return new IBEABuilder(problem2).build();
	}
    }

    /**
     * Solution<?> and has builder
     */
    public MOCell<TheType> createMOCell(boolean builder) {
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	Neighborhood<TheType> neighborhood = null;
	BoundedArchive<TheType> archive = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new MOCell<TheType>(problem, maxEvaluations, populationSize, archive, neighborhood,
		    crossoverOperator, mutationOperator, selectionOperator, evaluator);
	else
	    return new MOCellBuilder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /**
     * only BinarySolution and has builder
     */
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

    /**
     * only DoubleSolution and has builder for a lot of variants
     */
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

    /**
     * Solution<?> and has NO builder
     */
    public MOMBI<TheType> createMOMBI() {
	String pathWeights = null;
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selection = null;
	MutationOperator<TheType> mutation = null;
	CrossoverOperator<TheType> crossover = null;
	int maxIterations = 0;
	Problem<TheType> problem = null;

	return new MOMBI<TheType>(problem, maxIterations, crossover, mutation, selection, evaluator, pathWeights);
    }

    /**
     * Solution<?> and has builder
     */
    public NSGAII<TheType> createNSGAII(boolean builder) {
	SolutionListEvaluator<TheType> evaluator = null;
	Comparator<TheType> dominanceComparator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new NSGAII<TheType>(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator,
		    selectionOperator, dominanceComparator, evaluator);
	else
	    return new NSGAIIBuilder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /**
     * Solution<?> and only has builder
     */
    public NSGAIII<TheType> createNSGAIII() {
	Problem<TheType> problem = null;

	return new NSGAIIIBuilder<TheType>(problem).build();
    }

    /**
     * only DoubleSolution and has builder
     */
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

    /**
     * Solution<?> and has builder
     */
    public PAES<TheType> createPAES(boolean builder) {
	MutationOperator<TheType> mutationOperator = null;
	int biSections = 0;
	int maxEvaluations = 0;
	int archiveSize = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new PAES<TheType>(problem, archiveSize, maxEvaluations, biSections, mutationOperator);
	else
	    return new PAESBuilder<TheType>(problem).build();
    }

    /**
     * Solution<?> and has builder
     */
    public PESA2<TheType> createPAES2(boolean builder) {
	SolutionListEvaluator<TheType> evaluator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int biSections = 0;
	int archiveSize = 0;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new PESA2<TheType>(problem, maxEvaluations, populationSize, archiveSize, biSections,
		    crossoverOperator, mutationOperator, evaluator);
	else
	    return new PESA2Builder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /**
     * Solution<?> and has builder
     */
    public RandomSearch<TheType> createRandomSearch(boolean builder) {
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new RandomSearch<TheType>(problem, maxEvaluations);
	else
	    return new RandomSearchBuilder<TheType>(problem).build();
    }

    /**
     * Solution<?> and has builder
     */
    public RNSGAII<TheType> createRNSGAII(boolean builder) {
	double epsilon = 0;
	List<Double> interestPoint = null;
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new RNSGAII<TheType>(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator,
		    selectionOperator, evaluator, interestPoint, epsilon);
	else
	    return new RNSGAIIBuilder<TheType>(problem, crossoverOperator, mutationOperator, interestPoint, epsilon)
		    .build();
    }

    /**
     * only DoubleSolution and has builder
     */
    public SMPSO createSMPSO(boolean builder) {
	SolutionListEvaluator<DoubleSolution> evaluator = null;
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
	MutationOperator<DoubleSolution> mutationOperator = null;
	BoundedArchive<DoubleSolution> leaders = null;
	int swarmSize = 0;
	DoubleProblem problem = null;

	if (!builder)
	    return new SMPSO(problem, swarmSize, leaders, mutationOperator, maxIterations, r1Min, r1Max, r2Min, r2Max,
		    c1Min, c1Max, c2Min, c2Max, weightMin, weightMax, changeVelocity1, changeVelocity2, evaluator);
	else
	    return new SMPSOBuilder(problem, leaders).build();
    }

    /**
     * Solution<?> and has builder
     */
    public SMSEMOA<TheType> createSMSEMOA(boolean builder) {
	Hypervolume<TheType> hypervolumeImplementation = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	double offset = 0;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new SMSEMOA<TheType>(problem, maxEvaluations, populationSize, offset, crossoverOperator,
		    mutationOperator, selectionOperator, hypervolumeImplementation);
	else
	    return new SMSEMOABuilder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /**
     * Solution<?> and has builder
     */
    public SPEA2<TheType> createSPEA2(boolean builder) {
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int populationSize = 0;
	int maxIterations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new SPEA2<TheType>(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
		    selectionOperator, evaluator);
	else
	    return new SPEA2Builder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /**
     * Solution<?> and has NO builder
     */
    public WASFGA<TheType> createWASFGA() {
	List<Double> referencePoint = null;
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int maxIterations = 0;
	int populationSize = 0;
	Problem<TheType> problem = null;

	return new WASFGA<TheType>(problem, populationSize, maxIterations, crossoverOperator, mutationOperator,
		selectionOperator, evaluator, referencePoint);
    }

    /*
     * Single Objective Algorithms
     */

    /**
     * Solution<?> and has builder
     */
    public CoralReefsOptimization<TheType> createCoralReefsOptimization(boolean builder) {
	int attemptsToSettle = 0;
	double pd = 0;
	double fa = 0;
	double fbs = 0;
	double rho = 0;
	int m = 0;
	int n = 0;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	Comparator<TheType> comparator = null;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    return new CoralReefsOptimization<TheType>(problem, maxEvaluations, comparator, selectionOperator,
		    crossoverOperator, mutationOperator, n, m, rho, fbs, fa, pd, attemptsToSettle);
	else
	    return new CoralReefsOptimizationBuilder<TheType>(problem, selectionOperator, crossoverOperator,
		    mutationOperator).build();
    }

    /**
     * only DoubleSolution and has builder
     */
    public DifferentialEvolution createDifferentialEvolution(boolean builder) {
	SolutionListEvaluator<DoubleSolution> evaluator = null;
	DifferentialEvolutionSelection selectionOperator = null;
	DifferentialEvolutionCrossover crossoverOperator = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	DoubleProblem problem = null;

	if (!builder)
	    return new DifferentialEvolution(problem, maxEvaluations, populationSize, crossoverOperator,
		    selectionOperator, evaluator);
	else
	    return new DifferentialEvolutionBuilder(problem).build();
    }

    /**
     * Solution<?> and has builder for two variants algorithms
     */
    public Algorithm<TheType> createEvolutionStrategy(boolean builder, boolean elitist) {
	MutationOperator<TheType> mutation = null;
	int maxEvaluations = 0;
	int lambda = 0;
	int mu = 0;
	Problem<TheType> problem = null;
	EvolutionStrategyVariant variant = elitist ? EvolutionStrategyVariant.ELITIST
		: EvolutionStrategyVariant.NON_ELITIST;

	if (!builder)
	    if (elitist)
		return new ElitistEvolutionStrategy<TheType>(problem, mu, lambda, maxEvaluations, mutation);
	    else
		return new NonElitistEvolutionStrategy<TheType>(problem, mu, lambda, maxEvaluations, mutation);
	else
	    return new EvolutionStrategyBuilder<TheType>(problem, mutation, variant).build();
    }

    /**
     * Solution<?> and has builder for two variants of the algorithm
     */
    public Algorithm<TheType> createGeneticAlgorithm(boolean builder, boolean generetaniol) {
	SolutionListEvaluator<TheType> evaluator = null;
	SelectionOperator<List<TheType>, TheType> selectionOperator = null;
	MutationOperator<TheType> mutationOperator = null;
	CrossoverOperator<TheType> crossoverOperator = null;
	int populationSize = 0;
	int maxEvaluations = 0;
	Problem<TheType> problem = null;

	if (!builder)
	    if (generetaniol)
		return new GenerationalGeneticAlgorithm<TheType>(problem, maxEvaluations, populationSize,
			crossoverOperator, mutationOperator, selectionOperator, evaluator);
	    else
		return new SteadyStateGeneticAlgorithm<TheType>(problem, maxEvaluations, populationSize,
			crossoverOperator, mutationOperator, selectionOperator);
	else
	    return new GeneticAlgorithmBuilder<TheType>(problem, crossoverOperator, mutationOperator).build();
    }

    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */

    @SuppressWarnings("serial")
    public static abstract class TheType implements Solution<TheType> {
    }

}
