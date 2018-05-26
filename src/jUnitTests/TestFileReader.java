package jUnitTests;

import java.io.File;

import org.junit.Test;

import utils.FileReader;

/**
 * Created with the purpose of testing the class FileReader.java
 * 
 * @author Ana Pestana
 *
 */
public class TestFileReader {

    /**
     * Testing class initiation
     */
    @Test
    public final static void testClassFileReader() {
	new FileReader();
    }

    /**
     * Testing the readFileAndReturnList and readFileAndReturnListInRunPerspective
     * methods
     */
    @Test
    public final static void testFileReading() {
	FileReader.readFileAndReturnList(new File("./src/jUnitTests/testFiles/TestFileReader.txt"));
	FileReader.readFileAndReturnListInRunPerspective(new File("./src/jUnitTests/testFiles/TestFileReader.txt"));

    }

}
