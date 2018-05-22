package objects;

import java.util.ArrayList;

/**
 * 
 * The Admin class represents the platform's configuration parameters.
 * 
 * @author Ana Pestana
 *
 */
public class Admin {
    protected String email;
    protected ArrayList<QABlock> faq;
    protected String scriptsPath;

    /**
     * Empty constructor for the Admin class
     */
    public Admin() {
    }

    /**
     * Constructor for the Admin class.
     * 
     * @param email
     * @param faq
     * @param scriptsPath
     *            path where the compiled scripts will be stored
     */
    public Admin(String email, ArrayList<QABlock> faq, String scriptsPath) {
	this.email = email; // TODO create an email for the platform admin
	this.faq = faq;
	this.scriptsPath = scriptsPath;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the faq
     */
    public ArrayList<QABlock> getFaq() {
	return faq;
    }

    /**
     * @param faq
     *            the faq to set
     */
    public void setFaq(ArrayList<QABlock> faq) {
	this.faq = faq;
    }

    /**
     * @return the scriptsPath
     */
    public String getScriptsPath() {
	return scriptsPath;
    }

    /**
     * @param scriptsPath
     *            the scriptsPath to set
     */
    public void setScriptsPath(String scriptsPath) {
	this.scriptsPath = scriptsPath;
    }

    /**
     * In case no Admin is registered, this method returns the default configuration
     * parameters for the application
     * 
     * @return Default Admin
     */
    public static Admin getDefaultAdmin() {
	ArrayList<QABlock> faq = QABlock.getDefaultFAQ();
	Admin admin = new Admin("admin@gmail.com", faq, "C:\\");
	return admin;
    }

    /**
     * 
     * This method redefines the string printing of the admin's attributes
     * 
     * @see java.lang.Object#toString()
     * 
     */
    @Override
    public String toString() {
	return "Admin's email: " + email + Problem.newLine + "FAQ Section: " + faq + Problem.newLine + "Script's Path: "
		+ scriptsPath + Problem.newLine;
    }

    /**
     * Indicates whether some other object is "equal to" this one. Although this
     * method can receive any object as an argument, it does not validate whether it
     * is really an instantiated objected of the class Admin. Instead, this function
     * assumes the object passed as an argument is a Problem Object.
     */
    public boolean equals(Object obj) {
	Admin other = (Admin) obj;
	return this.toString().equals(other.toString());
    }

}
