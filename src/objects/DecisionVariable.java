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
    public String invalidValues;
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
     * @param invalidValues
     *            exception values to the lowerBound-upperBound interval [OPTIONAL]
     * @param knownSolutions
     *            for this variable from previous iterations [OPTIONAL]
     */
    public DecisionVariable(String name, DataType dataType, String lowerBound, String upperBound, String invalidValues,
	    ArrayList<String> knownSolutions) {
	this.name = name;
	this.dataType = dataType;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.invalidValues = invalidValues;
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

    public String getInvalidValues() {
	return invalidValues;
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
		+ ", Upper Bound: " + upperBound + ", Invalid Values: " + invalidValues + ", Known Solutions: "
		+ knownSolutions;
    }

    public String[] getInvalidValuesInVector() {
	String[] v = invalidValues.split(",");
	return v;
    }

}
