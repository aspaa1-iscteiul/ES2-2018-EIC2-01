package utils;

import java.util.ArrayList;

public class FitnessFunction {

    // Podes alterar os tipos dos dados entre String, enumerados e tipos
    // numéricos conforme der mais jeito
    public String jarFilePath;
    public ArrayList<OptimizationCriteria> optimizationCriteria;

    public FitnessFunction(String jarFilePath, ArrayList<OptimizationCriteria> optimizationCriteria) {
	this.jarFilePath = jarFilePath;
	this.optimizationCriteria = optimizationCriteria;
    }

    public String getJarFilePath() {
        return jarFilePath;
    }

    public void setJarFilePath(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    public ArrayList<OptimizationCriteria> getOptimizationCriteria() {
        return optimizationCriteria;
    }

    public void setOptimizationCriteria(ArrayList<OptimizationCriteria> optimizationCriteria) {
        this.optimizationCriteria = optimizationCriteria;
    }
    
    

}
