package utils;

public class OptimizationCriteria {

    // Podes alterar os tipos dos dados entre String, enumerados e tipos
    // numéricos conforme der mais jeito
    public String name;
    public DataType dataType;

    public OptimizationCriteria(String name, DataType dataType) {
	this.name = name;
	this.dataType = dataType;
    }

}
