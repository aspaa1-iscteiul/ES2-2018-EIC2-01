package objects;

public class OptimizationCriteria {

    // Podes alterar os tipos dos dados entre String, enumerados e tipos
    // numéricos conforme der mais jeito
    public String name;
    public DataType dataType;

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
