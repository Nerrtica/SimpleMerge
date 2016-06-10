package test;
/*
import static org.junit.Assert.*;

import java.util.ArrayList;
import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.ImportedFileInterface;

public class EasyMockPractice {
	
	private ImportedFileInterface testFileMock;
	private ArrayList<String> testText = new ArrayList<String>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		testFileMock = createMock(ImportedFileInterface.class);
		
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
	public void testGetText() {
		expect(testFileMock.getText()).andReturn(testText);
		replay(testFileMock);
		
		assertEquals(testText,testFileMock.getText());
		
		verify(testFileMock);
	}

}
*/