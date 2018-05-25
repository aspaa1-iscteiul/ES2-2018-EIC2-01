package utils;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rodrigo
 */
public class GraphGenerator extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color pointColorKnownSolution = new Color(60,179,113);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> scores;
    private List<Double> knownSolutions;

    public GraphGenerator(List<Double> scores, List<Double> knownSolutions) {
	this.scores = scores;
	this.knownSolutions = knownSolutions;
	knownSolutions.add(-3.1);
	knownSolutions.add(6.2);
	knownSolutions.add(3.5);
	knownSolutions.add(8.2);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
	double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

	List<Point> graphPoints = new ArrayList<>();
	for (int i = 0; i < scores.size(); i++) {
	    int x1 = (int) (i * xScale + padding + labelPadding);
	    int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
	    graphPoints.add(new Point(x1, y1));
	}

	List<Point> graphPoints2 = new ArrayList<>();
	for (int i = 0; i < knownSolutions.size(); i++) {
	    int x1 = (int) (0.03 * xScale + padding + labelPadding);
	    int y1 = (int) ((getMaxScore() - knownSolutions.get(i)) * yScale + padding);
	    graphPoints2.add(new Point(x1, y1));
	}

	// draw white background
	g2.setColor(Color.WHITE);
	g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
	g2.setColor(Color.BLACK);

	// create hatch marks and grid lines for y axis.
	for (int i = 0; i < numberYDivisions + 1; i++) {
	    int x0 = padding + labelPadding;
	    int x1 = pointWidth + padding + labelPadding;
	    int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
	    int y1 = y0;
	    if (scores.size() > 0) {
		g2.setColor(gridColor);
		g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
		g2.setColor(Color.BLACK);
		String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
		FontMetrics metrics = g2.getFontMetrics();
		int labelWidth = metrics.stringWidth(yLabel);
		g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
	    }
	    g2.drawLine(x0, y0, x1, y1);
	}

	// and for x axis
	for (int i = 0; i < scores.size(); i++) {
	    if (scores.size() > 1) {
		int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
		int x1 = x0;
		int y0 = getHeight() - padding - labelPadding;
		int y1 = y0 - pointWidth;
		if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
		    g2.setColor(gridColor);
		    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
		    g2.setColor(Color.BLACK);
		    String xLabel = i + "";
		    FontMetrics metrics = g2.getFontMetrics();
		    int labelWidth = metrics.stringWidth(xLabel);
		    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
		}
		g2.drawLine(x0, y0, x1, y1);
	    }
	}

	// create x and y axes 
	g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
	g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

	Stroke oldStroke = g2.getStroke();
	g2.setColor(lineColor);
	g2.setStroke(GRAPH_STROKE);
	for (int i = 0; i < graphPoints.size() - 1; i++) {
	    int x1 = graphPoints.get(i).x;
	    int y1 = graphPoints.get(i).y;
	    int x2 = graphPoints.get(i + 1).x;
	    int y2 = graphPoints.get(i + 1).y;
	    g2.drawLine(x1, y1, x2, y2);
	}

	g2.setStroke(oldStroke);
	g2.setColor(pointColor);
	for (int i = 0; i < graphPoints.size(); i++) {
	    int x = graphPoints.get(i).x - pointWidth / 2;
	    int y = graphPoints.get(i).y - pointWidth / 2;
	    int ovalW = pointWidth;
	    int ovalH = pointWidth;
	    g2.fillOval(x, y, ovalW, ovalH);
	}

	g2.setStroke(oldStroke);
	g2.setColor(pointColorKnownSolution);
	for (int i = 0; i < graphPoints2.size(); i++) {
	    int x = graphPoints2.get(i).x - pointWidth / 2;
	    int y = graphPoints2.get(i).y - pointWidth / 2;
	    int ovalW = pointWidth;
	    int ovalH = pointWidth;
	    g2.fillOval(x, y, ovalW*2, ovalH*2);
	}

    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(width, heigth);
    }

    /**
     * Analyses the list of scores and the list of knownSolutions to find the lowest value
     * @return
     */
    private double getMinScore() {
	double minScoreValues = Double.MIN_VALUE;
	for (Double score : scores) {
	    minScoreValues = Math.min(minScoreValues, score);
	}
	double minScoreSolutions = Double.MIN_VALUE;
	for (Double score : knownSolutions) {
	    minScoreSolutions = Math.min(minScoreSolutions, score);
	}
	return Math.min(minScoreSolutions, minScoreValues);
    }

    /**
     * Analyses the list of scores and the list of knownSolutions to find the highest value
     * @return
     */
    private double getMaxScore() {
	double maxScoreValues = Double.MIN_VALUE;
	for (Double score : scores) {
	    maxScoreValues = Math.max(maxScoreValues, score);
	}
	double maxScoreSolutions = Double.MIN_VALUE;
	for (Double score : knownSolutions) {
	    maxScoreSolutions = Math.max(maxScoreSolutions, score);
	}
	return Math.max(maxScoreSolutions, maxScoreValues);
    }


    public List<Double> getScores() {
	return scores;
    }

    public void setScores(List<Double> scores) {
	this.scores = scores;
	invalidate();
	this.repaint();
    }
    
    /**
     * Creates the graph and it places the graph in a JPanel to be added to the GUI
     * @param list
     * @param graphName
     * @return
     */
    public static JPanel createAndShowGui(ArrayList<Double> list, String graphName) {
	List<Double> scores = list;
	List<Double> knownSolutions = new ArrayList<>();
	JPanel mainPanel = new JPanel();
	JLabel title = new JLabel("Variation of results with runs for " + graphName);
	mainPanel.setLayout(new BorderLayout());
	title.setFont(new Font("Arial", Font.BOLD, 10));
	title.setHorizontalAlignment(JLabel.CENTER);
	GraphGenerator graphPanel = new GraphGenerator(scores, knownSolutions);
	graphPanel.setPreferredSize(new Dimension(350, 300));
	VerticalPanel vertPanel = new VerticalPanel();
	HorizontalPanel horiPanel = new HorizontalPanel();

	mainPanel.add(title, BorderLayout.NORTH);
	mainPanel.add(horiPanel, BorderLayout.SOUTH);
	mainPanel.add(vertPanel, BorderLayout.WEST);
	mainPanel.add(graphPanel, BorderLayout.CENTER);
	return mainPanel;
    }

}