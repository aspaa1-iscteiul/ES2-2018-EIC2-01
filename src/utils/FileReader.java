package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private ArrayList<ArrayList<Double>> variableList = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> runList = new ArrayList<ArrayList<Double>>();

    private void readFileInVariablePerspective(File file) {
	int lineNumber = 0;
	try{
	    Scanner scanner = new Scanner(file, "UTF-8");
	    while (scanner.hasNext()) {
		String tmp = scanner.nextLine();
		String[] variablesValues = tmp.split(" ");
		Double[] parsedVariablesValues = new Double[variablesValues.length];
		for(int i = 0; i < variablesValues.length; i++) {
		    parsedVariablesValues[i] = Double.parseDouble(variablesValues[i]);
		}
		if(lineNumber == 0){
		    for(int i = 0; i < parsedVariablesValues.length; i++) {
			ArrayList<Double> aux = new ArrayList<Double>();
			aux.add(parsedVariablesValues[i]);
			variableList.add(aux);
		    }
		} else {
		    for(int i = 0; i < parsedVariablesValues.length; i++) {
			variableList.get(i).add(parsedVariablesValues[i]);
		    }
		}
		lineNumber++;
	    }
	    scanner.close();
	} catch (FileNotFoundException fe) {
	    fe.printStackTrace();
	}
    }

    private void readFileInRunPerspective(File file) {
	try{
	    Scanner scanner = new Scanner(file, "UTF-8");
	    while (scanner.hasNext()) {
		String tmp = scanner.nextLine();
		String[] variablesValues = tmp.split(" ");
		Double[] parsedVariablesValues = new Double[variablesValues.length];
		for(int i = 0; i < variablesValues.length; i++) {
		    parsedVariablesValues[i] = Double.parseDouble(variablesValues[i]);
		}
		ArrayList<Double> aux = new ArrayList<Double>();
		for(int i = 0; i < parsedVariablesValues.length; i++) {
		    aux.add(parsedVariablesValues[i]);
		}
		runList.add(aux);
	    }
	    scanner.close();
	} catch (FileNotFoundException fe) {
	    fe.printStackTrace();
	}
    }

    public ArrayList<ArrayList<Double>> readFileAndReturnList(File file) {
	readFileInVariablePerspective(file);
	return variableList;
    }

    public ArrayList<ArrayList<Double>> readFileAndReturnListInRunPerspective(File file) {
	readFileInRunPerspective(file);
	return runList;
    }
}
