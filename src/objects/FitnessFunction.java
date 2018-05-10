package objects;

import java.util.ArrayList;

/**
 * The FitnessFunction class represents a function that weights the
 * DecisionVariables in order to produce an evaluation for the
 * optimizationAlgorithms in the form of OptimizationCriteria
 * 
 * @author Ana Pestana
 *
 */
public class FitnessFunction {

    private String jarFilePath;
    private ArrayList<OptimizationCriteria> optimizationCriteria;

    /**
     * Constructor for FitnessFunctions
     * 
     * @param jarFilePath
     *            String containing the path to the function's executable jar file
     * @param optimizationCriteria
     *            outputs produced by the FitnessFunction
     */
    public FitnessFunction(String jarFilePath, ArrayList<OptimizationCriteria> optimizationCriteria) {
	this.jarFilePath = jarFilePath;
	this.optimizationCriteria = optimizationCriteria;
    }

    public String getJarFilePath() {
	return jarFilePath;
    }

    public ArrayList<OptimizationCriteria> getOptimizationCriteria() {
	return optimizationCriteria;
    }

    public void setOptimizationCriteria(ArrayList<OptimizationCriteria> optimizationCriteria) {
	this.optimizationCriteria = optimizationCriteria;
    }

    @Override
    public String toString() {
	return Problem.newLine + "\t Jar File's Path: " + jarFilePath + ", Optimization Criteria: "
		+ optimizationCriteria;
    }

}
