package utils;

import java.util.ArrayList;

public class Problem {

    protected String problemName;
    protected String problemDescription;
    protected ArrayList<DecisionVariable> decisionVariables;
    protected ArrayList<FitnessFunction> fitnessFunctions;
    protected ArrayList<String> optimizationAlgorithms;
    protected double idealTimeFrame;
    protected double maxTimeFrame;
    public static String newLine = System.getProperty("line.separator");


    public Problem() {

    }

    public Problem(String name, String description, double idealTime, double maxTime) {
	this.problemName = name;
	this.problemDescription = description;
	this.idealTimeFrame = idealTime;
	this.maxTimeFrame = maxTime;
    }

    public String getProblemName() {
	return problemName;
    }

    public String getProblemDescription() {
	return problemDescription;
    }

    public double getIdealTimeFrame() {
	return idealTimeFrame;
    }

    public double getMaxTimeFrame() {
	return maxTimeFrame;
    }

    public void setProblemName(String problemName) {
	this.problemName = problemName;
    }

    public void setProblemDescription(String problemDescription) {
	this.problemDescription = problemDescription;
    }

    public void setIdealTimeFrame(double idealTimeFrame) {
	this.idealTimeFrame = idealTimeFrame;
    }

    public void setMaxTimeFrame(double maxTimeFrame) {
	this.maxTimeFrame = maxTimeFrame;
    }

    @Override
    public String toString() {
	return "Problem: " + newLine + "Problem Name = " + problemName + newLine + "Problem's Description = " + problemDescription
		+ newLine + "Ideal Time Frame = " + idealTimeFrame + newLine + "Max Time Frame = " + maxTimeFrame;
    }

}
