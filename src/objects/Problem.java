package objects;

import java.util.ArrayList;

/**
 * The Problem class represents an optimization problem's characterization.
 * 
 * @author Ana Pestana
 *
 */
public class Problem {

    protected String problemName;
    protected String problemDescription;
    protected ArrayList<DecisionVariable> decisionVariables;
    protected ArrayList<FitnessFunction> fitnessFunctions;
    protected ArrayList<String> optimizationAlgorithms;
    protected double idealTimeFrame;
    protected double maxTimeFrame;
    public static String newLine = System.getProperty("line.separator");

    /**
     * Empty constructor for the Problem class
     */
    public Problem() {

    }

    /**
     * Constructor for the Problem class.
     * 
     * The list of known solutions is not initialized and, if necessary, it must be
     * set using the setOptimizationAlgorithms(ArrayList<String>
     * optimizationAlgorithms) method
     * 
     * @param problemName
     *            String containing the name of the problem. This name must follow
     *            the Java convention for class names
     * @param problemDescription
     *            String containing the optimization problem's description in
     *            natural language
     * @param decisionVariables
     *            List of all the problem's decision variables
     * @param fitnessFunctions
     *            List of all the problem's fitness functions
     * @param optimizationAlgorithms
     *            List of the optimization algorithms selected
     * @param idealTimeFrame
     *            Ideal time frame for the algorithm's runtime
     * @param maxTimeFrame
     *            Maximum time frame accepted for the algorithm's runtime
     */
    public Problem(String problemName, String problemDescription, ArrayList<DecisionVariable> decisionVariables,
	    ArrayList<FitnessFunction> fitnessFunctions, ArrayList<String> optimizationAlgorithms,
	    double idealTimeFrame, double maxTimeFrame) {
	this.problemName = problemName;
	this.problemDescription = problemDescription;
	this.decisionVariables = decisionVariables;
	this.fitnessFunctions = fitnessFunctions;
	this.optimizationAlgorithms = optimizationAlgorithms;
	this.idealTimeFrame = idealTimeFrame;
	this.maxTimeFrame = maxTimeFrame;
    }

    public String getProblemName() {
	return problemName;
    }

    public void setProblemName(String problemName) {
	this.problemName = problemName;
    }

    public String getProblemDescription() {
	return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
	this.problemDescription = problemDescription;
    }

    public ArrayList<DecisionVariable> getDecisionVariables() {
	return decisionVariables;
    }

    public void setDecisionVariables(ArrayList<DecisionVariable> decisionVariables) {
	this.decisionVariables = decisionVariables;
    }

    public ArrayList<FitnessFunction> getFitnessFunctions() {
	return fitnessFunctions;
    }

    public void setFitnessFunctions(ArrayList<FitnessFunction> fitnessFunctions) {
	this.fitnessFunctions = fitnessFunctions;
    }

    public ArrayList<String> getOptimizationAlgorithms() {
	return optimizationAlgorithms;
    }

    public void setOptimizationAlgorithms(ArrayList<String> optimizationAlgorithms) {
	this.optimizationAlgorithms = optimizationAlgorithms;
    }

    public double getIdealTimeFrame() {
	return idealTimeFrame;
    }

    public void setIdealTimeFrame(double idealTimeFrame) {
	this.idealTimeFrame = idealTimeFrame;
    }

    public double getMaxTimeFrame() {
	return maxTimeFrame;
    }

    public void setMaxTimeFrame(double maxTimeFrame) {
	this.maxTimeFrame = maxTimeFrame;
    }

    @Override
    public String toString() {
	return "Problem's Name: " + problemName + newLine + "Problem's Description: " + problemDescription + newLine
		+ "Decision Variables: " + decisionVariables + newLine + "Fitness Functions: " + fitnessFunctions
		+ newLine + "Optimization Algorithms: " + optimizationAlgorithms + newLine + "Ideal Time Frame: "
		+ idealTimeFrame + newLine + "Maximum Time Frame: " + maxTimeFrame + newLine;
    }

}
