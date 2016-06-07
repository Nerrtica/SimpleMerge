package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.ImportedFile;

public class FailTestFileFunction {
	private static ImportedFile testFile = new ImportedFile();
	private static ArrayList<String> testList = new ArrayList<String>();
	private static ArrayList<Boolean> bool = new ArrayList<Boolean>();
	private static String testString = "test line\nit is test line\nfake line\nHello JAVA\nsoftware engineering is very good.";
	private static String fileRoute = "C:\\Users\\TG\\Desktop\\JunitTestFake.txt";
	private static String asFileRoute = "C:\\Users\\TG\\Desktop\\JunitTestCopyFake.txt";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testList.add("test line");
		testList.add("it is test line");
		testList.add("Hello JAVA");
		testList.add("software engineering is very good.");
		bool.add(true);
		bool.add(true);
		bool.add(false);
		bool.add(true);
		bool.add(true);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testSave() {
		try{
			testFile.save();
		}
		catch(NullPointerException e){
			fail();
		}
		try{
			testFile.load(fileRoute);
		}
		catch (FileNotFoundException e){
			fail();
		}
		assertEquals(testList, testFile.getText());
	}
	@Test
	public void testLoad() {
		try{
			testFile.load(fileRoute);
		}
		catch (FileNotFoundException e){
			fail();
		}
		assertEquals(testList, testFile.getText());
	}
}
