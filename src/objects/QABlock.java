package objects;

import java.util.ArrayList;

public class QABlock {
    private String question;
    private String answer;

    /**
     * Constructor for the QABlock class
     * 
     * @param question
     * @param answer
     */
    public QABlock(String question, String answer) {
	this.question = question;
	this.answer = answer;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
	return question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
	return answer;
    }

    /**
     * This method initiates an Array List with the default questions and answers
     * for the FAQ section
     * 
     * @return default Q&A
     */
    public static ArrayList<QABlock> getDefaultFAQ() {
	String question1 = "How can I cancel the optimization process in case its taking to long to produce a solution?";
	String answer1 = "The optimization process can not be canceled once it starts runnning. If your problem is time sensitive you should define a maximum time frame for the production of the solution. If this limit is surpassed then the optimization process is forcebly terminated.";
	QABlock qaBlock1 = new QABlock(question1, answer1);

	String question2 = "What sort of indication will be sent to me via email?";
	String answer2 = "You will be sent emails to update you on the progress of the optimization process when it has reached 25%, 50% and 75% of the estimated total duration.";
	QABlock qaBlock2 = new QABlock(question2, answer2);

	ArrayList<QABlock> faq = new ArrayList<>();
	faq.add(qaBlock1);
	faq.add(qaBlock2);

	return faq;
    }

    /**
     * 
     * This method redefines the string printing of the Q&A Block's attributes
     * 
     * @see java.lang.Object#toString()
     * 
     */
    @Override
    public String toString() {
	return "Question: " + question + Problem.newLine + "Answer: " + answer + Problem.newLine;
    }

}
