package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();

    private void readFile(File file) {
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
			list.add(aux);
		    }
		} else {
		    for(int i = 0; i < parsedVariablesValues.length; i++) {
			list.get(i).add(parsedVariablesValues[i]);
		    }
		}
		lineNumber++;
	    }
	    scanner.close();
	} catch (FileNotFoundException fe) {
	    fe.printStackTrace();
	}
    }
    
    public ArrayList<ArrayList<Double>> readFileAndReturnList(File file) {
	readFile(file);
	return list;
    }

}
