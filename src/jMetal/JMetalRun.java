package jMetal;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import jMetal.binaryConfiguration.MyBinaryProblem;
import jMetal.doubleConfiguration.MyDoubleProblem;
import jMetal.integerConfiguration.MyIntegerProblem;
import objects.DataType;
import objects.Problem;

public class JMetalRun {

    private Problem problem;
    private boolean isSingleobjective;
    private int iterations;
    private JMetalProblem jMetalProblem;

    public JMetalRun(Problem problem, boolean isSingleobjective) {
	this.problem = problem;
	this.isSingleobjective = isSingleobjective;
	iterations = JMetalProblem.INDEPENDENT_RUNS * JMetalAlgorithms.MAX_EVALUATIONS
		* problem.getOptimizationAlgorithms().size();
    }

    public void run() {
	DataType type = problem.getDecisionVariables().get(0).dataType;
	if (type == DataType.DOUBLE) {
	    jMetalProblem = new MyDoubleProblem(problem, isSingleobjective);
	} else if (type == DataType.INTEGER) {
	    jMetalProblem = new MyIntegerProblem(problem, isSingleobjective);
	} else {
	    jMetalProblem = new MyBinaryProblem(problem, isSingleobjective);
	}

	Progress p = new Progress();
	p.start();

	Executors.newSingleThreadExecutor().execute(new Runnable() {
	    @Override
	    public void run() {
		try {
		    jMetalProblem.run(problem.getOptimizationAlgorithms());
		    afterRunning();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	});
    }

    private void afterRunning() {
	// TODO Auto-generated method stub
	System.out.println("algorithms finished... show graphics with results...");
    }

    private class Progress extends Thread {
	private JProgressBar progressBar;
	private JFrame progressFrame;
	private int value;

	public Progress() {
	    value = 0;

	    progressBar = new JProgressBar(0, iterations);
	    progressBar.setPreferredSize(new Dimension(400, 22));
	    progressBar.setStringPainted(true);
	    progressBar.setFont(new Font("Consolas", Font.PLAIN, 18));
	    progressBar.setIndeterminate(true);

	    progressFrame = new JFrame();
	    progressFrame.add(progressBar);
	    progressFrame.pack();
	    progressFrame.setLocation(
		    new Point((Toolkit.getDefaultToolkit().getScreenSize().width - progressFrame.getWidth()) / 2,
			    (Toolkit.getDefaultToolkit().getScreenSize().height - progressFrame.getHeight()) / 2));
	    progressFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    progressFrame.setResizable(false);
	    progressFrame.setAlwaysOnTop(true);
	    progressFrame.setVisible(true);
	}

	@Override
	public void run() {
	    while ((value = jMetalProblem.evaluateIteration()) < iterations) {
		double percentage = (value / (double) iterations) * 100;
		progressBar.setString(String.format("%.2f", percentage) + " %");
		if (percentage == 25) {
		    System.out.println("email de 25%");
		} else if (percentage == 50) {
		    System.out.println("email de 50%");
		} else if (percentage == 75) {
		    System.out.println("email de 75%");
		}
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    progressFrame.dispose();
	}
    }

}
