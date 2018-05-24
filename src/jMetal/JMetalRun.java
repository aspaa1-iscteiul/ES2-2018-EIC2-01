package jMetal;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	// TODO change subject and message
	email.sendEmail(problem.getProblemName() + " started", "");

	DataType type = problem.getDecisionVariablesDataType();
	if (type == DataType.DOUBLE) {
	    jMetalProblem = new MyDoubleProblem(problem, isSingleobjective);
	} else if (type == DataType.INTEGER) {
	    jMetalProblem = new MyIntegerProblem(problem, isSingleobjective);
	} else {
	    jMetalProblem = new MyBinaryProblem(problem, isSingleobjective);
	}

	progress = new Progress();
	progress.start();

	Executors.newSingleThreadExecutor().execute(new Runnable() {
	    @Override
	    public void run() {
		try {
		    jMetalProblem.run(problem.getOptimizationAlgorithms());
		    afterRunning();
		} catch (Exception e) {
		    // TODO change subject and message
		    email.sendEmail("Error", "");
		    e.printStackTrace();
		    System.exit(1);
		}
	    }
	});
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
	    new RException(null);
	}
	try {
	    ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "pdflatex", problemName + ".tex" });
	    processBuilder.directory(new File(problemDir + "/latex/").getAbsoluteFile());
	    processBuilder.start().waitFor();

	    Desktop.getDesktop().open(new File(problemDir + "/latex/" + problemName + ".pdf"));
	} catch (InterruptedException | IOException e) {
	    new LatexException(null);
	}
	progress.interrupt();
	userInterface.showFrame(true);
	userInterface.goToNextPage();
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

	@Override
	public void run() {
	    boolean p25 = false, p50 = false, p75 = false;
	    while (jMetalProblem.evaluateIteration() < iterations) {
		double percentage = (jMetalProblem.evaluateIteration() / (double) iterations) * 100;
		progressBar.setValue(jMetalProblem.evaluateIteration());
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

    public static class RException extends MouseAdapter {
	private static final String NEW_LINE = "<br/>";
	private JLabel label;
	private JFrame frame;

	public RException(JFrame frame) {
	    label = new JLabel("<html>Ocorreu um problema ao compilar com o Rscript.exe" + NEW_LINE + NEW_LINE
		    + "Sugestão de resolução:" + NEW_LINE
		    + "Por favor, verifique se tem uma aplicação para compilação de documentos .R instalada no seu computador e, caso"
		    + NEW_LINE
		    + "tenha, verifique ainda que o path para o executável Rscript.exe se encontra incluído na variável de ambiente PATH"
		    + NEW_LINE + NEW_LINE
		    + "(Poderá proceder ao download do pacote de software R em: <a href=\"\">https://cran.r-project.org/</a>)</html>");
	    label.addMouseListener(this);
	    this.frame = frame;
	    JOptionPane.showMessageDialog(frame, label, "Indicador Hypervolume", JOptionPane.ERROR_MESSAGE);
	}

	public void mousePressed(MouseEvent event) {
	    if (event.getX() >= 342 && event.getX() <= 486 && event.getY() >= 96 && event.getY() <= 111)
		try {
		    Desktop.getDesktop().browse(new URI("https://cran.r-project.org/"));
		} catch (IOException | URISyntaxException e) {
		    JOptionPane.showMessageDialog(frame, "Por favor, verifique se tem um browser instalado.",
			    "Indicador Hypervolume", JOptionPane.ERROR_MESSAGE);
		}
	}

    }

    public static class LatexException extends MouseAdapter {
	private static final String NEW_LINE = "<br/>";
	private JFrame frame;
	private JLabel label;

	public LatexException(JFrame frame) {
	    label = new JLabel("<html>Ocorreu um problema ao compilar com o pdflatex.exe" + NEW_LINE + NEW_LINE
		    + "Sugestão de resolução:" + NEW_LINE
		    + "Por favor, verifique se tem uma aplicação para compilação de documentos .tex instalada no seu computador e, caso"
		    + NEW_LINE
		    + "tenha, verifique ainda que o path para o executável pdflatex.exe se encontra incluído na variável de ambiente PATH"
		    + NEW_LINE + NEW_LINE
		    + "(Poderá proceder ao download do pacote de software MiKTeX em: <a href=\"\">https://miktex.org/download</a>)</html>");
	    label.addMouseListener(this);
	    this.frame = frame;
	    JOptionPane.showMessageDialog(frame, label, "Indicador Hypervolume", JOptionPane.ERROR_MESSAGE);
	}

	public void mousePressed(MouseEvent event) {
	    if (event.getX() >= 373 && event.getX() <= 533 && event.getY() >= 96 && event.getY() <= 111)
		try {
		    Desktop.getDesktop().browse(new URI("https://miktex.org/download"));
		} catch (IOException | URISyntaxException e) {
		    JOptionPane.showMessageDialog(frame, "Por favor, verifique se tem um browser instalado.",
			    "Indicador Hypervolume", JOptionPane.ERROR_MESSAGE);
		}
	}

    }

}