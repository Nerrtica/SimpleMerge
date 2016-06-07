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
	
	private static String testString = "fail line\nit is test line\nfake line\nHello JAVA\nsoftware engineering is very good.";
	
	private static String fakeFileRoute = "JunitTestFake.txt";
	private static String fakeAsFileRoute = "JunitTestCopyFake.txt";
	private static String fileRoute = "JunitTest.txt";
	private static String asFileRoute = "JunitTestCopy.txt";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testList.add("fail line");
		testList.add("it is fail line");
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
	public void testSaveException() {
		try{
			testFile.save();
		}
		catch(NullPointerException e){	//fileRoute가 없어서 예외가 발생한다.
			fail();
		}
		try{
			testFile.load(fakeFileRoute);
		}
		catch (FileNotFoundException e){
			fail();
		}
		assertEquals(testList, testFile.getText());
	}
	@Test
	public void testLoadException() {
		try{
			testFile.load(fakeFileRoute);
		}
		catch (FileNotFoundException e){	//찾는 파일 경로가 없을 때, 예외가 발생함
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
	@Test
	public void testConvert() {
		testFile.convert(testString, bool);
		assertEquals(testList, testFile.getText());
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
	public void testSaveAs() {
		testFile.saveAs(asFileRoute);
		try{
			testFile.load(fileRoute);
		}
		catch (FileNotFoundException e){
			fail();
		}
		assertEquals(testList, testFile.getText());
	}

	@Test
	public void testGetText() {
		assertEquals(testList, testFile.getText());
	}
}
