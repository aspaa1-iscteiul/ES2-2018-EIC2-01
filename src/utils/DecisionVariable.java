package utils;

import java.util.ArrayList;

public class DecisionVariable {

    // Podes alterar os tipos dos dados entre String, enumerados e tipos
    // numéricos conforme der mais jeito
    public String name;
    public DataType dataType;
    public String lowerBound;
    public String upperBound;
    public String domain;
    public ArrayList<String> knownSolutions;

    public DecisionVariable(String name, DataType dataType, String lowerBound, String upperBound, String domain,
	    ArrayList<String> knownSolutions) {
	super();
	this.name = name;
	this.dataType = dataType;
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.domain = domain;
	this.knownSolutions = knownSolutions;
    }

}
