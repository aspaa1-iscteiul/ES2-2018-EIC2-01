package jUnitTests;

import org.junit.Test;

/**
 * This class gathers all JUnitTests, allowing us to easily calculate the
 * testing coverage and, on the other hand, making it easier to automate the
 * testing process.
 * 
 * @author Ana Pestana
 *
 */
public class TestSuite {

    /**
     * Method responsible for running all the JUnitTests
     */
    @Test
    public final void runTestSuite() {
	TestUserFileUtils.testClassUserFileUtils();
	TestUserFileUtils.successfullyTestWriteToAndReadFromXML1();
	TestUserFileUtils.successfullyTestWriteToAndReadFromXML2();
	TestUserFileUtils.unsuccessfullyTestWriteToAndReadFromXML();
	TestUserFileUtils.testGetInvalidValuesInVector();

	TestAdminFileUtils.testClassadminFileUtils();
	TestAdminFileUtils.successfullyTestCreateTemplateAndUploadConfigFile();
	TestAdminFileUtils.unsuccessfullyTestCreateTemplateAndUploadConfigFile();
	
//	TestFileReader.testClassFileReader();
//	TestFileReader.testFileReading();
    }

    /**
     * Testing different classes initialization
     */
    @Test
    public final void testClassInitialization() {
	new TestUserFileUtils();
	new TestAdminFileUtils();
//	new TestFileReader();
    }

}
