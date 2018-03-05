package utils;

import java.util.ArrayList;

public class FitnessFunction {

    // Podes alterar os tipos dos dados entre String, enumerados e tipos
    // numéricos conforme der mais jeito
    public String jarFilePath;
    public ArrayList<OptimizationCriteria> optimizationCriteria;

    public FitnessFunction(String jarFilePath, ArrayList<OptimizationCriteria> optimizationCriteria) {
	super();
	this.jarFilePath = jarFilePath;
	this.optimizationCriteria = optimizationCriteria;
    }

}
