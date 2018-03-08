package objects;

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

    /**
     * Constructor for OptimizationCriteria
     * 
     * @param name
     *            of the optimizationCriteria
     * @param dataType
     *            of the optimizationCriteria: {@code DataType.INTEGER} or
     *            {@code DataType.DOUBLE}
     */
    public OptimizationCriteria(String name, DataType dataType) {
	this.name = name;
	this.dataType = dataType;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public DataType getDataType() {
	return dataType;
    }

    public void setDataType(DataType dataType) {
	this.dataType = dataType;
    }

    @Override
    public String toString() {
	return Problem.newLine + "\t \t Name: " + name + ", Data Type: " + dataType;
    }

}
