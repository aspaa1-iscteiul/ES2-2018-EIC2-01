package jUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objects.Admin;
import utils.AdminFileUtils;

public class TestAdminFileUtils {

    /**
     * Testing class initiation
     */
    @Test
    public final void testClassadminFileUtils() {
	new AdminFileUtils();
    }

    /**
     * Testing a successful scenario of creating a template of the config.xml
     * document, uploading that same file and confirming we are obtaining equivalent
     * objects
     */
    @Test
    public final void successfullyTestCreateTemplateAndUploadConfigFile() {
	Admin adminWrite = objects.Admin.getDefaultAdmin();
	System.out.println(adminWrite);
	utils.AdminFileUtils.createTemplate("./src/jUnitTests/testFiles/adminConfigTest1.xml");
	Admin adminRead = utils.AdminFileUtils.readFromXML("./src/jUnitTests/testFiles/adminConfigTest1.xml");
	System.out.println(adminRead);
	assertEquals(adminWrite, adminRead);
    }

    /**
     * Testing a scenario in which the createTemplate and readFromXML functions fail
     * and, therefore, it is necessary to handle exceptions
     */
    @Test
    public final void unsuccessfullyTestCreateTemplateAndUploadConfigFile() {
	// For testing purposes, the file path passed as an argument has a typographical
	// error
	utils.AdminFileUtils.createTemplate("./src/jUnitTests/testFikes/adminConfigTest1.xml");
	utils.AdminFileUtils.readFromXML("./src/jUnitTests/testFilis/adminConfigTest1.xml");
    }
}
