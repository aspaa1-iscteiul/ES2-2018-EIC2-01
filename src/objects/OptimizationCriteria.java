package objects;

import java.util.ArrayList;

/**
 * The OptimizationCriteria class represents an evaluation criteria for the
 * optimization algorithms selected
 * 
 * @author Ana Pestana
 *
 */
public class OptimizationCriteria {

    private String name;
    private ArrayList<String> knownSolutions;

    /**
     * Constructor for OptimizationCriteria
     * 
     * @param name
     *            of the optimizationCriteria
     * @param knownSolutions
     *            for this criteria from previous iterations [OPTIONAL]
     */
    public OptimizationCriteria(String name, ArrayList<String> knownSolutions) {
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
	return Problem.newLine + "\t \t Name: " + name + ", Known Solutions: " + knownSolutions;
    }

}
