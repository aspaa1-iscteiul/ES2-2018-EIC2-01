package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private static ArrayList<ArrayList<Double>> variableList = new ArrayList<ArrayList<Double>>();
    private static ArrayList<ArrayList<Double>> runList = new ArrayList<ArrayList<Double>>();

    /**
     * Reads given file and fills the variableList with a ArrayList with the values from the file for which variable
     * @param file
     */
    private static void readFileInVariablePerspective(File file) {
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

    /**
     * Reads given file and fills the runList with a ArrayList with the values from the file for which run
     * @param file
     */
    private static void readFileInRunPerspective(File file) {
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

    /**
     * Returns variableList after performing {@linkplain #readFileInVariablePerspective}
     * @param file
     * @return
     */
    public static ArrayList<ArrayList<Double>> readFileAndReturnList(File file) {
	variableList.clear();
	readFileInVariablePerspective(file);
	return variableList;
    }

    /**
     * Returns runList after performing {@linkplain #readFileInRunPerspective}
     * @param file
     * @return
     */
    public static ArrayList<ArrayList<Double>> readFileAndReturnListInRunPerspective(File file) {
	runList.clear();
	readFileInRunPerspective(file);
	return runList;
    }
}
