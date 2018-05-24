package jMetal;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MyExceptions {

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
