package test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DiffBlock;
import model.ImportedFile;

public class TestMergeFunction {
	
	private ImportedFile testFile = new ImportedFile();
	private ImportedFile oppositeFile = new ImportedFile();
	private ArrayList<DiffBlock> leftDiffBlock = new ArrayList<DiffBlock>();
	private ArrayList<DiffBlock> rightDiffBlock = new ArrayList<DiffBlock>();
	
	private ArrayList<DiffBlock> leftDiffBlockTest = new ArrayList<DiffBlock>();
	private ArrayList<DiffBlock> rightDiffBlockTest = new ArrayList<DiffBlock>();
	
	private DiffBlock leftDiff = new DiffBlock(3,2);
	private DiffBlock rightDiff = new DiffBlock(3,4);	
	
	private int[] leftDiffList = {0,1,2,5,6,7,8,9,10,11};//10
	private int[] rightDiffList = {0,1,2,-1,-1,3,4,5,6,7,8,9};//12
	
	private String fileRoute = "LCSTestL.txt";
	private String oppositeFileRoute = "LCSTestR.txt";
	
	private ArrayList<String> testText = new ArrayList<String>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testFile.load(fileRoute);
		oppositeFile.load(oppositeFileRoute);
		
		leftDiffBlockTest.add(leftDiff);
		rightDiffBlockTest.add(rightDiff);
		
		for(int i = 0 ; i< 3 ; i++)
			testText.add("test line");
		for(int i = 0 ; i< 2 ; i++)
			testText.add("add line");
		for(int i = 0 ; i< 7 ; i++)
			testText.add("test line");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompare() {
		leftDiffBlock = testFile.compare(oppositeFile, leftDiffList);
		rightDiffBlock = oppositeFile.compare(testFile, rightDiffList);
		
		assertEquals(leftDiffBlock.get(0).getStartIndex(), leftDiffBlockTest.get(0).getStartIndex());
		assertEquals(leftDiffBlock.get(0).getEndIndex(), leftDiffBlockTest.get(0).getEndIndex());
		assertEquals(leftDiffBlock.get(0).getLineNumber(), leftDiffBlockTest.get(0).getLineNumber());
		
		assertEquals(rightDiffBlock.get(0).getStartIndex(), rightDiffBlockTest.get(0).getStartIndex());
		assertEquals(rightDiffBlock.get(0).getEndIndex(), rightDiffBlockTest.get(0).getEndIndex());
		assertEquals(rightDiffBlock.get(0).getLineNumber(), rightDiffBlockTest.get(0).getLineNumber());
	}

	@Test
	public void testMerge() {
		leftDiffBlock = testFile.compare(oppositeFile, leftDiffList);
		rightDiffBlock = oppositeFile.compare(testFile, rightDiffList);
		
		testFile.merge(oppositeFile, leftDiffBlock, rightDiffBlock, 0);
		
		assertEquals(testText,testFile.getText());
	}

}
