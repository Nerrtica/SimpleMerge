package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.LCSAlgo;

public class FailTestLCSAlgo {
	private List<String> leftText = new ArrayList<String>();
	private List<String> rightText = new ArrayList<String>();
	private int[] leftDiffList = {0,1,2,5,6,7,8,9,10,12};//10
	private int[] rightDiffList = {0,1,2,-1,-1,3,4,5,6,7,8,10};//12
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		for(int i = 0 ; i< 10 ; i++)
			leftText.add("test line");
		
		for(int i = 0 ; i< 3 ; i++)
			rightText.add("test line");
		for(int i = 0 ; i< 2 ; i++)
			rightText.add("add line");
		for(int i = 0 ; i< 7 ; i++)
			rightText.add("test line");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLCSLength() {
		
		assertEquals(11, LCSAlgo.LCSLength(leftText, rightText));
		
	}

	@Test
	public void testMakeDiffList() {
		LCSAlgo.LCSLength(leftText, rightText);
		LCSAlgo.makeDiffList(leftText, rightText, 10, 12);
		
		for(int i = 0 ; i < 10 ; i++)
			assertEquals(leftDiffList[i], LCSAlgo.getLeftDiffList()[i]);
		for(int j = 0 ; j < 12 ; j++)
			assertEquals(rightDiffList[j], LCSAlgo.getRightDiffList()[j]);
		
	}
}
