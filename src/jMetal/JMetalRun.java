package jMetal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import frames.frameUtils.Email;
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
    private Email email;

    public JMetalRun(Problem problem, boolean isSingleobjective, String userEmail) {
	this.problem = problem;
	this.isSingleobjective = isSingleobjective;
	iterations = JMetalProblem.INDEPENDENT_RUNS * JMetalAlgorithms.MAX_EVALUATIONS
		* problem.getOptimizationAlgorithms().size();
	email = new Email(userEmail);
    }

    public void run() {
	// TODO change subject and message
	email.sendEmail(problem.getProblemName() + " started", "");

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
		    p.interrupt();
		    afterRunning();
		} catch (IOException e) {
		    // TODO change subject and message
		    email.sendEmail("Error", "");
		    e.printStackTrace();
		    System.exit(1);
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

	public Progress() {
	    UIManager.put("ProgressBar.background", Color.LIGHT_GRAY);
	    UIManager.put("ProgressBar.foreground", Color.GREEN);
	    UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
	    UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

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
	    boolean p25 = false, p50 = false, p75 = false;
	    while (jMetalProblem.evaluateIteration() < iterations) {
		double percentage = (jMetalProblem.evaluateIteration() / (double) iterations) * 100;
		progressBar.setString(String.format("%.2f", percentage) + " %");
		if (percentage >= 25 && !p25) {
		    p25 = true;
		    // TODO change subject and message
		    email.sendEmail(problem.getProblemName() + " is 25% complete", "");
		    System.out.println("email de 25% sended...");
		} else if (percentage >= 50 && !p50) {
		    p50 = true;
		    // TODO change subject and message
		    email.sendEmail(problem.getProblemName() + " is 50% complete", "");
		    System.out.println("email de 50% sended...");
		} else if (percentage >= 75 && !p75) {
		    p75 = true;
		    // TODO change subject and message
		    email.sendEmail(problem.getProblemName() + " is 75% complete", "");
		    System.out.println("email de 75% sended...");
		}
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    progressFrame.dispose();
	}
    }

}
