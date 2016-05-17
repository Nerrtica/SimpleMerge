/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : ClsView.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.14

package view;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.io.*;
import java.awt.event.*;

import javax.swing.plaf.basic.*;

import model.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ClsView
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	//////////////////////
	//FORM
	public final int 		DEFAULT_WINDOW_WIDTH = 800;						//Window default width size
	public final int 		DEFAULT_WINDOW_HEIGHT = 600;					//Window default height size
	public final String 	WINDOW_CAPTION = "Simple_Merge_TEAM_11";		//Window caption name
	//SPLITPANE
	public final int 		DIVIDER_THICKNESS = 3;							//size of seperating bar in splitpane
	//IMAGE ADDRESS
	public final String 	SAVE_BTN_IMG_ADDRESS = "Images/Save01.png";		//address of save button image
	public final String 	LOAD_BTN_IMG_ADDRESS = "Images/Load01.png";		//address of load button image
	//EDITOR
	public final String		DEFAULT_FONT_PATH = "Fonts/D2Coding.ttc";
	public final float		DEFAULT_FONT_SIZE = 12.0f;
	//SCROLL
	public final int 		WHEEL_SCROLL_AMOUNT = 54;						//scroll amount when mouse wheel
	
	//////////////////////////////////////////////////////////////////
	//GUI Components
	//////////////////////
	//form
	private JFrame 			viewForm;
	//form - north
	private JPanel 			topPanel;
	private JButton 		undoBtn, redoBtn;
	//form - west
	private JPanel			leftSearchPanel;
	private JQuickView		quickView;
	//form - center
	private JSplitPane 		seperator;
	//center - left side
	private JPanel 			leftPanel, left_TopPanel;
	private JButton 		leftSaveBtn, leftLoadBtn;
	private JScrollTextArea	leftEditor;
	//center - right side
	private JPanel 			rightPanel, right_TopPanel;
	private JButton 		rightSaveBtn, rightLoadBtn;
	private JScrollTextArea rightEditor;

	//////////////////////////////////////////////////////////////////
	//private variables
	//////////////////////
	//window margin
	private int		windowMarginX;		//special value to calculate real form's size
	private int		windowMarginY;		//special value to calculate real form's size
	//seperator
	private float 	spRatio;			//seperator's divider's location ratio
	//clipping area size
	private int 	internalWidth;		//real form's clipping area width
	private int 	internalHeight;		//real form's clipping area height
	//font
	private Font 	editorFont;			//font instance that will be used setting editor's font
	
	//////////////////////////////////////////////////////////////////
	
	public static void main(String args[])
	{

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClsView window = new ClsView();
					window.viewForm.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Constructor
	public ClsView()
	{
		InitView();
	}
	
	//Initialize view class
	private void InitView()
	{

		OSBasedSetting();
		
		Init_Variables();
		Init_Form();
		Init_TopBounds();
		Init_CenterBounds();
		Init_WestBounds();

	}
	
	//Check OS and setting window margin variables
	private void OSBasedSetting()
	{
		
		//get os name
		String osType = System.getProperty("os.name");
		
		if(osType.indexOf("Win") >= 0)
		{
			windowMarginX = 20;
			windowMarginY = 50;
		}
		else if(osType.indexOf("Mac") >= 0)
		{
			windowMarginX = 0;
			windowMarginY = 0;
		}

	}
	
	//Initialize class variables
	private void Init_Variables()
	{
		
		spRatio = 0.47f;
		
	}
	
	//Initialize form
	private void Init_Form()
	{
		
		//Create main window
		viewForm = new JFrame(WINDOW_CAPTION);
		viewForm.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewForm.getContentPane().setLayout(new BorderLayout(0,0));
		
		//Create listener when window's size is changed
		viewForm.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				WindowResized();
			}
		});
		
		//Create listener when window's close button is pushed
		viewForm.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				JOptionPane.showMessageDialog(viewForm, "Exiting.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Reset Internal form size
		ResetInternalFormSize();
		
	}
	
	//Initialize top bounds of form
	private void Init_TopBounds()
	{
		
		//Create base top panel
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		viewForm.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		//Create undo button
		undoBtn = new JButton("UNDO");
		topPanel.add(undoBtn);
		
		//Create undo button listenser
		undoBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(undoBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create redo button
		redoBtn = new JButton("REDO");
		topPanel.add(redoBtn);
		
		//Create redo button listenser
		redoBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(redoBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
	}
	
	//Initialize west bounds of form
	private void Init_WestBounds()
	{
		
		leftSearchPanel = new JPanel();
		leftSearchPanel.setLayout(new BorderLayout());
		leftSearchPanel.setPreferredSize(new Dimension(50, internalHeight));
		viewForm.getContentPane().add(leftSearchPanel, BorderLayout.WEST);
		
		quickView = new JQuickView(leftEditor);
		leftSearchPanel.add(quickView, BorderLayout.CENTER);
		
	}
	
	//Initialize center bounds of form
	private void Init_CenterBounds()
	{
		
		//Create splitpane
		seperator = new JSplitPane();
		seperator.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		seperator.setDividerLocation((int)(internalWidth * spRatio));
		seperator.setDividerSize(DIVIDER_THICKNESS);
		viewForm.getContentPane().add(seperator, BorderLayout.CENTER);
		
		//Create splitpane's divider listener
		BasicSplitPaneUI spui = (BasicSplitPaneUI)seperator.getUI();
		if(spui instanceof BasicSplitPaneUI)
		{
			((BasicSplitPaneUI)spui).getDivider().addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					spRatio = (float)seperator.getDividerLocation() / (float)(internalWidth);
				}
			});
		}
		
		//Call Initialize splitpane's left side
		Init_LeftBounds();
		//Call Initialize splitpane's right side
		Init_RightBounds();
		
	}
	
	//Initialize left side of the splitpane
	private void Init_LeftBounds()
	{
		
		//Create base left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout(0, 0));
		seperator.setLeftComponent(leftPanel);
		
		//Create top panel in left side
		left_TopPanel = new JPanel();
		left_TopPanel.setLayout(new FlowLayout());
		leftPanel.add(left_TopPanel, BorderLayout.NORTH);
		
		//Create save and load buttons of left side
		try
		{
			leftSaveBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_BTN_IMG_ADDRESS))));
			leftLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_ADDRESS))));
		}catch(IOException ex){}
		leftSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		leftLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		left_TopPanel.add(leftSaveBtn);
		left_TopPanel.add(leftLoadBtn);
		
		//Create left save button listenser
		leftSaveBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(leftSaveBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create left load button listenser
		leftLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(leftLoadBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create editor of left side
		leftEditor = new JScrollTextArea();
		leftEditor.GetTextPad().TakeFontFileAndSet(DEFAULT_FONT_PATH);
		leftEditor.GetTextPad().SetFontSize(DEFAULT_FONT_SIZE);
		leftPanel.add(leftEditor, BorderLayout.CENTER);

		//Create scrollbar listener
		leftEditor.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				leftEditor.SyncVertScrollVal(rightEditor);
				quickView.repaint();
			}
		});
		
	}
	
	//Initialize right side of the splitpane
	private void Init_RightBounds()
	{
		
		//Create base right panel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(0, 0));
		seperator.setRightComponent(rightPanel);
		
		//Create top panel in right side
		right_TopPanel = new JPanel();
		right_TopPanel.setLayout(new FlowLayout());
		rightPanel.add(right_TopPanel, BorderLayout.NORTH);
		
		//Create save and load buttons of right side
		try
		{
			rightSaveBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_BTN_IMG_ADDRESS))));
			rightLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_ADDRESS))));
		}catch(IOException ex){}
		rightSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		rightLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		right_TopPanel.add(rightSaveBtn);
		right_TopPanel.add(rightLoadBtn);
		
		//Create right save button listenser
		rightSaveBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(rightSaveBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create right load button listenser
		rightLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(rightLoadBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create editor of left side
		rightEditor = new JScrollTextArea();
		rightEditor.GetTextPad().TakeFontFileAndSet(DEFAULT_FONT_PATH);
		rightEditor.GetTextPad().SetFontSize(DEFAULT_FONT_SIZE);
		rightPanel.add(rightEditor, BorderLayout.CENTER);

		//Create scrollbar listener
		rightEditor.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				rightEditor.SyncVertScrollVal(leftEditor);
				quickView.repaint();
			}
		});
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Reset form's real clipping area size
	private void ResetInternalFormSize()
	{
		
		internalWidth = viewForm.getWidth() - windowMarginX;
		internalHeight = viewForm.getHeight() - windowMarginY;
		
	}
	
	//When form's size is changed
	private void WindowResized()
	{
		
		//Reset internal form size
		ResetInternalFormSize();
		
		//Maintain divider's location ratio
		seperator.setDividerLocation((int)(internalWidth * spRatio));

		quickView.repaint();
		
	}

}
