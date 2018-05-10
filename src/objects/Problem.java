package objects;

import java.util.ArrayList;

/**
 * The Problem class represents an optimization problem's characterization.
 * 
 * @author Ana Pestana
 *
 */
public class Problem {

    private String problemName;
    private String problemDescription;

    private String decisionVariablesSetName;
    private DataType decisionVariablesDataType;
    private String decisionVariablesLowerBound;
    private String decisionVariablesUpperBound;
    private String decisionVariablesInvalidValues;
    private ArrayList<DecisionVariable> decisionVariables;

    private DataType optimizationCriteriaDataType;
    private ArrayList<FitnessFunction> fitnessFunctions;

    private ArrayList<String> optimizationAlgorithms;
    private String idealTimeFrame;
    private String maxTimeFrame;

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
     *            [OPTIONAL] String containing the optimization problem's
     *            description in natural language
     * @param decisionVariablesSetName
     *            [OPTIONAL] String containing the name of the problem's set of
     *            decision variables
     * @param decisionVariablesDataType
     *            data type of the decision variables: {@code DataType.INTEGER} or
     *            {@code DataType.DOUBLE} or {@code DataType.BINARY}
     * @param decisionVariablesLowerBound
     *            lower bound for the values the variables can assume
     * @param decisionVariablesUpperBound
     *            upper bound for the values the variables can assume
     * @param decisionVariablesInvalidValues
     *            exception values to the lowerBound-upperBound interval [OPTIONAL]
     * @param decisionVariables
     *            List of all the problem's decision variables
     * @param optimizationCriteriaDataType
     *            data type of the optimizationCriteria: {@code DataType.INTEGER} or
     *            {@code DataType.DOUBLE} or {@code DataType.BINARY}
     * @param fitnessFunctions
     *            List of all the problem's fitness functions
     * @param optimizationAlgorithms
     *            List of the optimization algorithms selected
     * @param idealTimeFrame
     *            Ideal time frame for the algorithm's runtime
     * @param maxTimeFrame
     *            [OPTIONAL] Maximum time frame accepted for the algorithm's runtime
     */
    public Problem(String problemName, String problemDescription, String decisionVariablesSetName,
	    DataType decisionVariablesDataType, String decisionVariablesLowerBound, String decisionVariablesUpperBound,
	    String decisionVariablesInvalidValues, ArrayList<DecisionVariable> decisionVariables,
	    DataType optimizationCriteriaDataType, ArrayList<FitnessFunction> fitnessFunctions,
	    ArrayList<String> optimizationAlgorithms, String idealTimeFrame, String maxTimeFrame) {
	super();
	this.problemName = problemName;
	this.problemDescription = problemDescription;
	this.decisionVariablesSetName = decisionVariablesSetName;
	this.decisionVariablesDataType = decisionVariablesDataType;
	this.decisionVariablesLowerBound = decisionVariablesLowerBound;
	this.decisionVariablesUpperBound = decisionVariablesUpperBound;
	this.decisionVariablesInvalidValues = decisionVariablesInvalidValues;
	this.decisionVariables = decisionVariables;
	this.optimizationCriteriaDataType = optimizationCriteriaDataType;
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

    public String getDecisionVariablesSetName() {
	return decisionVariablesSetName;
    }

    public void setDecisionVariablesSetName(String decisionVariablesSetName) {
	this.decisionVariablesSetName = decisionVariablesSetName;
    }

    public DataType getDecisionVariablesDataType() {
	return decisionVariablesDataType;
    }

    public void setDecisionVariablesDataType(DataType decisionVariablesDataType) {
	this.decisionVariablesDataType = decisionVariablesDataType;
    }

    public String getDecisionVariablesLowerBound() {
	return decisionVariablesLowerBound;
    }

    public void setDecisionVariablesLowerBound(String decisionVariablesLowerBound) {
	this.decisionVariablesLowerBound = decisionVariablesLowerBound;
    }

    public String getDecisionVariablesUpperBound() {
	return decisionVariablesUpperBound;
    }

    public void setDecisionVariablesUpperBound(String decisionVariablesUpperBound) {
	this.decisionVariablesUpperBound = decisionVariablesUpperBound;
    }

    public String getDecisionVariablesInvalidValues() {
	return decisionVariablesInvalidValues;
    }

    public void setDecisionVariablesInvalidValues(String decisionVariablesInvalidValues) {
	this.decisionVariablesInvalidValues = decisionVariablesInvalidValues;
    }

    public ArrayList<DecisionVariable> getDecisionVariables() {
	return decisionVariables;
    }

    public void setDecisionVariables(ArrayList<DecisionVariable> decisionVariables) {
	this.decisionVariables = decisionVariables;
    }

    public DataType getOptimizationCriteriaDataType() {
	return optimizationCriteriaDataType;
    }

    public void setOptimizationCriteriaDataType(DataType optimizationCriteriaDataType) {
	this.optimizationCriteriaDataType = optimizationCriteriaDataType;
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

    public String getIdealTimeFrame() {
	return idealTimeFrame;
    }

    public void setIdealTimeFrame(String idealTimeFrame) {
	this.idealTimeFrame = idealTimeFrame;
    }

    public String getMaxTimeFrame() {
	return maxTimeFrame;
    }

    public void setMaxTimeFrame(String maxTimeFrame) {
	this.maxTimeFrame = maxTimeFrame;
    }

    @Override
    public String toString() {
	return "Problem's Name: " + problemName + newLine + "Problem's Description: " + problemDescription + newLine
		+ "Decision Variables' Set Name: " + decisionVariablesSetName + newLine
		+ "Decision Variables' Data Type: " + decisionVariablesDataType + newLine
		+ "Decision Variables' Lower Bound: " + decisionVariablesLowerBound + newLine
		+ "Decision Variables' Upper Bound: " + decisionVariablesUpperBound + newLine
		+ "Decision Variables' Invalid Values: " + decisionVariablesInvalidValues + newLine
		+ "Decision Variables: " + decisionVariables + newLine + "Optimization Criteria's Data Type: "
		+ optimizationCriteriaDataType + newLine + "Fitness Functions: " + fitnessFunctions + newLine
		+ "Optimization Algorithms: " + optimizationAlgorithms + newLine + "Ideal Time Frame: " + idealTimeFrame
		+ newLine + "Max Time Frame: " + maxTimeFrame + newLine;
    }

    /**
     * Indicates whether some other object is "equal to" this one. Although this
     * method can receive any object as an argument, it does not validate whether it
     * is really an instantiated objected of the class Problem. Instead, this
     * function assumes the object passed as an argument is a Problem Object.
     */
    public boolean equals(Object obj) {
	Problem other = (Problem) obj;
	return this.toString().equals(other.toString());
    }

    public String[] getInvalidValuesInVector() {
	String[] invalidValuesVector = new String[0];
	if (decisionVariablesInvalidValues != null) {
	    invalidValuesVector = decisionVariablesInvalidValues.split(",");
	}
	return invalidValuesVector;
    }

}
