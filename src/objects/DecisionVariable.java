package objects;

import java.util.ArrayList;

/**
 * The DecisionVariable class represents the factors to be considered by the
 * fitness functions in order to access the quality of the
 * OptimizationAlgorithm's solution
 * 
 * @author Ana Pestana
 *
 */
public class DecisionVariable {

    public String name;
    public DataType dataType;
    public String lowerBound;
    public String upperBound;
    public String domain;
    public ArrayList<String> knownSolutions;

    /**
     * Constructor for DecisionVariables
     * 
     * @param name
     *            of the decision variable
     * @param dataType
     *            of the decision variable: {@code DataType.INTEGER} or
     *            {@code DataType.DOUBLE}
     * @param lowerBound
     *            for the values the variable can assume
     * @param upperBound
     *            for the values the variable can assume
     * @param domain
     *            exception values to the lowerBound-upperBound interval
     * @param knownSolutions
     *            for this variable from previous iterations
     */
    public DecisionVariable(String name, DataType dataType, String lowerBound, String upperBound, String domain,
	    ArrayList<String> knownSolutions) {
	this.name = name;
	this.dataType = dataType;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.domain = domain;
	this.knownSolutions = knownSolutions;
    }

    public String getName() {
	return name;
    }

    public DataType getDataType() {
	return dataType;
    }

    public String getLowerBound() {
	return lowerBound;
    }

    public String getUpperBound() {
	return upperBound;
    }

    public String getDomain() {
	return domain;
    }

    public ArrayList<String> getKnownSolutions() {
	return knownSolutions;
    }

    public void setKnownSolutions(ArrayList<String> knownSolutions) {
	this.knownSolutions = knownSolutions;
    }

    @Override
    public String toString() {
	return Problem.newLine + "\tName: " + name + ", Data Type: " + dataType + ", Lower Bound: " + lowerBound
		+ ", Upper Bound: " + upperBound + ", Domain: " + domain + ", Known Solutions: " + knownSolutions;
    }

}
