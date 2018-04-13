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

    public String name;
    public DataType dataType;
    public ArrayList<String> knownSolutions;

    /**
     * Constructor for OptimizationCriteria
     * 
     * @param name
     *            of the optimizationCriteria
     * @param dataType
     *            of the optimizationCriteria: {@code DataType.INTEGER} or
     *            {@code DataType.DOUBLE}
     * @param knownSolutions
     *            for this criteria from previous iterations [OPTIONAL]
     */
    public OptimizationCriteria(String name, DataType dataType, ArrayList<String> knownSolutions) {
	this.name = name;
	this.dataType = dataType;
	this.knownSolutions = knownSolutions;
    }

    public String getName() {
	return name;
    }

    public DataType getDataType() {
	return dataType;
    }
    
    public ArrayList<String> getKnownSolutions() {
	return knownSolutions;
    }

    public void setKnownSolutions(ArrayList<String> knownSolutions) {
	this.knownSolutions = knownSolutions;
    }
    
    @Override
    public String toString() {
	return Problem.newLine + "\t \t Name: " + name + ", Data Type: " + dataType + ", Known Solutions: "
		+ knownSolutions;
    }

}
