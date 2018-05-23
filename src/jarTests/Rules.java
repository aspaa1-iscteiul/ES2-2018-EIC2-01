package jarTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Rules {

    private static final String dirPath = "./jarTests/rules/";

    public static void main(String... args) throws FileNotFoundException {
	double[] var = new double[args.length];
	for (int index = 0; index < args.length; index++)
	    var[index] = Double.parseDouble(args[index]);

	ArrayList<ArrayList<String>> ham = log(lines("ham.log")), spam = log(lines("spam.log"));
	HashMap<String, Double> rules = readRules(var);

	int hamTotal = 0;
	for (int i = 0; i < ham.size(); i++) {
	    double sum = 0;
	    for (String rule : ham.get(i))
		try {
		    sum += rules.get(rule);
		} catch (NullPointerException e) {
		}
	    if (sum > 5)
		hamTotal++;
	}

	int spamTotal = 0;
	for (int i = 0; i < spam.size(); i++) {
	    double sum = 0;
	    for (String rule : spam.get(i))
		try {
		    sum += rules.get(rule);
		} catch (NullPointerException e) {
		}
	    if (sum <= 5)
		spamTotal++;
	}

	System.out.println(hamTotal + " " + spamTotal);
    }

    private static ArrayList<ArrayList<String>> log(ArrayList<String> lines) {
	ArrayList<ArrayList<String>> result = new ArrayList<>();
	for (String line : lines) {
	    ArrayList<String> tmpList = new ArrayList<String>(Arrays.asList(line.split("\t")));
	    tmpList.remove(0);
	    result.add(tmpList);
	}
	return result;
    }

    public static ArrayList<String> lines(String fileName) throws FileNotFoundException {
	ArrayList<String> lines = new ArrayList<>();
	Scanner scn = new Scanner(new File(dirPath + fileName).getAbsoluteFile());
	while (scn.hasNextLine()) {
	    String line = scn.nextLine();
	    if (!line.equals(""))
		lines.add(line);
	}
	scn.close();
	return lines;
    }

    private static HashMap<String, Double> readRules(double[] var) throws FileNotFoundException {
	ArrayList<String> rules = lines("rules.cf");
	if (rules.size() != var.length)
	    return null;

	HashMap<String, Double> map = new HashMap<>();
	for (int i = 0; i < rules.size(); i++)
	    map.put(rules.get(i), var[i]);
	return map;
    }

}
