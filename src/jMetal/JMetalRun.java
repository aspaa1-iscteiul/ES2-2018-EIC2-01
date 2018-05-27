package jMetal;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import frames.UserInterface;
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
    private UserInterface userInterface;
    private Progress progress;
    private RunProblem runProblem;
    private MaximumTime maximumTime;

    public JMetalRun(UserInterface userinterface, Problem problem, boolean isSingleobjective, String userEmail,
	    String adminEmail) {
	this.userInterface = userinterface;
	this.problem = problem;
	this.isSingleobjective = isSingleobjective;
	iterations = JMetalProblem.INDEPENDENT_RUNS * JMetalAlgorithms.MAX_EVALUATIONS
		* problem.getOptimizationAlgorithms().size();
	email = new Email(userEmail);
	email.setToCC(adminEmail);
    }

    public void run() {
	userInterface.showFrame(false);

	email.sendEmail(false, problem.getProblemName() + "Update do progresso - Inicio",
		"Olá! Gostaríamos de " + "informar que a execução do processo de otimização foi iniciada. "
			+ "Pedimos-lhe que aguarde pela chegada dos resultados.");
	DataType dataType = problem.getDecisionVariablesDataType();
	if (dataType == DataType.DOUBLE) {
	    jMetalProblem = new MyDoubleProblem(problem, isSingleobjective);
	} else if (dataType == DataType.INTEGER) {
	    jMetalProblem = new MyIntegerProblem(problem, isSingleobjective);
	} else {
	    jMetalProblem = new MyBinaryProblem(problem, isSingleobjective);
	}

	progress = new Progress();
	progress.start();

	runProblem = new RunProblem();
	runProblem.start();

	maximumTime = new MaximumTime();
	maximumTime.start();
    }

    private class MaximumTime extends Thread {
	private boolean kill = false;

	@Override
	public void run() {
	    try {
		int maximumMilliseconds = Integer.parseInt(problem.getMaxTimeFrame()) * 60 * 1000;
		int milliseconds = 0;

		while (milliseconds <= maximumMilliseconds) {
		    long var = System.currentTimeMillis();
		    try {
			sleep(1000);
		    } catch (InterruptedException e) {
			if (kill)
			    return;
		    }
		    milliseconds += System.currentTimeMillis() - var;
		}
		progress.closeProgress();
		email.sendEmail(true, problem.getProblemName() + "Processo de otimização interrompido",
			"Olá! Gostaríamos de " + "informar que processo de otimização foi interrompido uma"
				+ " vez que o tempo estabelecido pela Maximum Time Frame foi excedido.");
		System.exit(1);
	    } catch (NumberFormatException e) {
	    }
	}

	public void kill() {
	    kill = true;
	    interrupt();
	}
    }

    private class RunProblem extends Thread {
	@Override
	public void run() {
	    try {
		jMetalProblem.run(problem.getOptimizationAlgorithms());
		maximumTime.kill();
		afterRunning();
	    } catch (Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		email.sendEmail(true, "Error", sw.toString());
		System.exit(1);
	    }
	}

	private void afterRunning() {
	    String problemName = problem.getProblemName();
	    String problemDir = "./experimentBaseDirectory/" + problemName;
	    try {
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "Rscript", "HV.Boxplot.R" });
		processBuilder.directory(new File(problemDir + "/R/").getAbsoluteFile());
		processBuilder.start().waitFor();

		Desktop.getDesktop().open(new File(problemDir + "/R/HV.Boxplot.eps"));
	    } catch (IOException | InterruptedException e) {
		new MyExceptions.RException(progress.getFrame());
	    }
	    try {
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "pdflatex", problemName + ".tex" });
		processBuilder.directory(new File(problemDir + "/latex/").getAbsoluteFile());
		processBuilder.start().waitFor();

		Desktop.getDesktop().open(new File(problemDir + "/latex/" + problemName + ".pdf"));
	    } catch (InterruptedException | IOException e) {
		new MyExceptions.LatexException(progress.getFrame());
	    }
	    progress.closeProgress();
	    userInterface.showFrame(true);
	    userInterface.goToNextPage();
	}

    }

    private class Progress extends Thread {
	private JProgressBar progressBar;
	private JFrame progressFrame;

	public Progress() {
	    UIManager.put("ProgressBar.background", Color.LIGHT_GRAY);
	    UIManager.put("ProgressBar.foreground", new Color(0, 190, 0));
	    UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
	    UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

	    progressBar = new JProgressBar(0, iterations);
	    progressBar.setPreferredSize(new Dimension(600, 30));
	    progressBar.setStringPainted(true);
	    progressBar.setFont(new Font("Consolas", Font.PLAIN, 20));

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

	public JFrame getFrame() {
	    return progressFrame;
	}

	public void closeProgress() {
	    progressFrame.dispose();
	}

	@Override
	public void run() {
	    boolean p25 = false, p50 = false, p75 = false;
	    while (jMetalProblem.evaluateIteration() < iterations) {
		double percentage = (jMetalProblem.evaluateIteration() / (double) iterations) * 100;
		progressBar.setValue(jMetalProblem.evaluateIteration());
		progressBar.setString(String.format("%.2f", percentage) + " %");
		if (percentage >= 25 && !p25) {
		    p25 = true;
		    email.sendEmail(false, problem.getProblemName() + "Update do progresso 25%",
			    "Olá! Gostaríamos de "
				    + "informar que a execução do processo de otimização chegou aos 25%. "
				    + "Pedimos-lhe que aguarde um pouco mais pela chegada dos resultados.");
		    System.out.println("email de 25% sended...");
		} else if (percentage >= 50 && !p50) {
		    p50 = true;
		    email.sendEmail(false, problem.getProblemName() + "Update do progresso 50%",
			    "Olá! Gostaríamos de "
				    + "informar que a execução do processo de otimização chegou aos 50%. "
				    + "Pedimos-lhe que aguarde um pouco mais pela chegada dos resultados.");
		    System.out.println("email de 50% sended...");
		} else if (percentage >= 75 && !p75) {
		    p75 = true;
		    email.sendEmail(false, problem.getProblemName() + "Update do progresso 75%",
			    "Olá! Gostaríamos de "
				    + "informar que a execução do processo de otimização chegou aos 75%. "
				    + "Pedimos-lhe que aguarde um pouco mais pela chegada dos resultados.");
		    System.out.println("email de 75% sended...");
		}
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    email.sendEmail(false, problem.getProblemName() + " completed", "");
	    progressBar.setValue(iterations);
	    progressBar.setString("99.99 %");
	}

    }
}