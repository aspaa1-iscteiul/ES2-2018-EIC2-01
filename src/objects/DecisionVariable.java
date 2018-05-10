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

    private String name;
    private ArrayList<String> knownSolutions;

    /**
     * Constructor for DecisionVariables
     * 
     * @param name
     *            of the decision variable
     * @param knownSolutions
     *            for this variable from previous iterations [OPTIONAL]
     */
    public DecisionVariable(String name, ArrayList<String> knownSolutions) {
	this.name = name;
	this.knownSolutions = knownSolutions;
    }

    public String getName() {
	return name;
    }

    public ArrayList<String> getKnownSolutions() {
	return knownSolutions;
    }

    public void setKnownSolutions(ArrayList<String> knownSolutions) {
	this.knownSolutions = knownSolutions;
    }

    @Override
    public String toString() {
	return Problem.newLine + "\tName: " + name + ", Known Solutions: " + knownSolutions;
    }

}
