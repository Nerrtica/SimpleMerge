package test;
import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DiffBlockInterface;
import model.ImportedFile;

public class TestMergeFunction {
	private ImportedFile testFile = new ImportedFile();
	private ImportedFile oppositeFile = new ImportedFile();
	private ArrayList<DiffBlockInterface> DiffMock;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DiffMock.add(createMock(DiffBlockInterface.class));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoad() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvert() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAs() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompare() {
		fail("Not yet implemented");
	}

	@Test
	public void testMerge() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetText() {
		fail("Not yet implemented");
	}

}
