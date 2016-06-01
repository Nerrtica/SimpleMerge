package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.ImportedFile;

public class TestClass {
	private static ImportedFile testFile = new ImportedFile();
	private static ArrayList<String> testList = new ArrayList<String>();
	private static ArrayList<Boolean> bool = new ArrayList<Boolean>();
	private static String testString = "test line\nit is test line\nfake line\nHello JAVA\nsoftware engineering is very good.";
	private static String fileRoute = "C:\\Users\\TG\\Desktop\\JunitTest.txt";
	private static String asFileRoute = "C:\\Users\\TG\\Desktop\\JunitTestCopy.txt";
	
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
	public void testLoad() {
		testFile.load(fileRoute);
		assertEquals(testList, testFile.getText());
	}
	@Test
	public void testConvert() {
		testFile.convert(testString, bool);
		assertEquals(testList, testFile.getText());
	}
	@Test
	public void testSave() {
		testFile.save();
		testFile.load(fileRoute);
		assertEquals(testList, testFile.getText());
	}

	@Test
	public void testSaveAs() {
		testFile.saveAs(asFileRoute);
		testFile.load(fileRoute);
		assertEquals(testList, testFile.getText());
	}

	@Test
	public void testGetText() {
		assertEquals(testList, testFile.getText());
	}

}