/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : ClsView.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.18

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
	public final int		DEFAULT_WINDOW_X 		= 100;								//Window default x position
	public final int		DEFAULT_WINDOW_Y 		= 100;								//Window default y position
	public final int 		DEFAULT_WINDOW_WIDTH 	= 1000;								//Window default width size
	public final int 		DEFAULT_WINDOW_HEIGHT 	= 700;								//Window default height size
	public final int		DEFAULT_WEST_WIDTH 		= 100;								//Window default west bounds width size
	public final String 	WINDOW_CAPTION 			= "Simple_Merge_TEAM_11";			//Window caption name
	public final Color		COMPONENT_BG_COLOR 		= Color.WHITE;						//Component's background color
	//SPLITPANE
	public final int 		DIVIDER_THICKNESS = 5;										//size of seperating bar in splitpane
	//IMAGE PATH
	public final String 	SAVE_BTN_IMG_PATH 			= "Images/Save01.png";			//path of save button image
	public final String 	LOAD_BTN_IMG_PATH 			= "Images/Open01.png";			//path of load button image
	public final String		TO_LEFT_BTN_IMG_PATH 		= "Images/ToLeft01.png";		//path of to left button image
	public final String		TO_RIGHT_BTN_IMG_PATH 		= "Images/ToRight01.png";		//path of to right button image
	public final String		TO_ALL_LEFT_BTN_IMG_PATH 	= "Images/AllToLeft01.png";		//path of all to left button image
	public final String		TO_ALL_RIGHT_BTN_IMG_PATH 	= "Images/AllToRight01.png";	//path of all to right button image	
	public final String		COMPARE_BTN_IMG_PATH 		= "Images/Compare01.png";		//path of compare button image
	public final String		UNDO_BTN_IMG_PATH 			= "Images/Undo01.png";			//path of undo button image
	public final String		REDO_BTN_IMG_PATH 			= "Images/Redo01.png";			//path of redo button image
	public final String		EDIT_BTN_IMG_PATH 			= "Images/Edit01.png";			//path of edit button image
	//TOOLTIP TEXT
	public final String		TOOLTIP_UNDO_BTN 			= "Undo";						//tooltip of undo button
	public final String		TOOLTIP_REDO_BTN 			= "Redo";						//tooltip of redo button
	public final String		TOOLTIP_COMPARE_BTN 		= "Compare";					//tooltip of compare button
	public final String		TOOLTIP_TO_LEFT_BTN 		= "Merge to Left";				//tooltip of merge to left button
	public final String		TOOLTIP_TO_RIGHT_BTN 		= "Merge to Right";				//tooltip of merge to right button
	public final String		TOOLTIP_ALL_TO_LEFT_BTN 	= "Merge all to Left";			//tooltip of merge all to left button
	public final String		TOOLTIP_ALL_TO_RIGHT_BTN 	= "Merge all to Right";			//tooltip of merge all to right button
	public final String		TOOLTIP_SAVE_BTN 			= "Save";						//tooltip of save button
	public final String		TOOLTIP_LOAD_BTN 			= "Open";						//tooltip of load button
	public final String		TOOLTIP_EDIT_BTN 			= "Edit";						//tooltip of edit button
	//EDITOR
	public final String		DEFAULT_FONT_PATH = "Fonts/D2Coding.ttc";					//path of editor font
	public final float		DEFAULT_FONT_SIZE = 12.0f;									//size of editor font
	//SCROLL
	public final int 		WHEEL_SCROLL_AMOUNT = 54;									//scroll amount when mouse wheel
	//DIALOGBOX
	public final int		DEFAULT_DIALOG_WIDTH 	= 800;								//dialog box default width
	public final int		DEFAULT_DIALOG_HEIGHT 	= 400;								//dialog box default height
	public final Font		DEFAULT_DIALOG_FONT 	= new Font("돋움", Font.PLAIN, 12);	//dialog box default font
	
	//////////////////////////////////////////////////////////////////
	//GUI Components
	//////////////////////
	//form
	private JFrame 			viewForm;
	//form - north
	private JPanel 			topPanel;
	private JButton 		undoBtn, redoBtn;
	private JButton			mergeToLeftBtn, mergeToRightBtn;
	private JButton			mergeAllToLeftBtn, mergeAllToRightBtn;
	private JButton			compareBtn;
	//form - west
	private JPanel			leftSearchPanel;
	private JQuickView		leftQuickView;
	private JQuickView		rightQuickView;
	//form - center
	private JSplitPane 		seperator;
	//center - left side
	private JPanel 			leftPanel, left_TopPanel;
	private JButton 		leftSaveBtn, leftLoadBtn, leftEditBtn;
	private JScrollTextArea	leftEditor;
	//center - right side
	private JPanel 			rightPanel, right_TopPanel;
	private JButton 		rightSaveBtn, rightLoadBtn, rightEditBtn;
	private JScrollTextArea rightEditor;
	//dialog box
	private JFileChooser	fileDialogBox;

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
	
	private ImportedFile imf1, imf2;
	
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
		
		spRatio = 0.5f;
		
	}
	
	//Initialize form
	private void Init_Form()
	{
		
		//Create main window
		viewForm = new JFrame(WINDOW_CAPTION);
		viewForm.setLocation(DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
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
		
		//set file dialog box window
		fileDialogBox = new JFileChooser();
		fileDialogBox.setMultiSelectionEnabled(false);
		fileDialogBox.setPreferredSize(new Dimension(DEFAULT_DIALOG_WIDTH, DEFAULT_DIALOG_HEIGHT));
		InitDialogBoxFont(fileDialogBox.getComponents());
		
		//Reset Internal form size
		ResetInternalFormSize();
		
		spRatio = (float)((internalWidth - DEFAULT_WEST_WIDTH) / 2) / (internalWidth - DEFAULT_WEST_WIDTH);
		
	}
	
	//Initialize dialogbox font
	private void InitDialogBoxFont(Component[] comp)
	{
		
		int i;
		Font dialogFont = new Font("돋움", Font.PLAIN, 12);
		
		for(i = 0; i < comp.length; i++)
		{
			if(comp[i] instanceof Container)
				InitDialogBoxFont(((Container)comp[i]).getComponents());
			try
			{
				comp[i].setFont(dialogFont);
			}catch(Exception ex){}
		}
		
	}
	
	//Initialize top bounds of form
	private void Init_TopBounds()
	{
		
		//Create base top panel
		topPanel = new JPanel();
		topPanel.setBackground(COMPONENT_BG_COLOR);
		topPanel.setLayout(new FlowLayout(0));
		viewForm.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		//Create button and get button images
		try
		{
			undoBtn = new JButton(new ImageIcon(ImageIO.read(new File(UNDO_BTN_IMG_PATH))));
			redoBtn = new JButton(new ImageIcon(ImageIO.read(new File(REDO_BTN_IMG_PATH))));
			mergeToLeftBtn = new JButton(new ImageIcon(ImageIO.read(new File(TO_LEFT_BTN_IMG_PATH))));
			mergeToRightBtn = new JButton(new ImageIcon(ImageIO.read(new File(TO_RIGHT_BTN_IMG_PATH))));
			mergeAllToLeftBtn = new JButton(new ImageIcon(ImageIO.read(new File(TO_ALL_LEFT_BTN_IMG_PATH))));
			mergeAllToRightBtn = new JButton(new ImageIcon(ImageIO.read(new File(TO_ALL_RIGHT_BTN_IMG_PATH))));
			compareBtn = new JButton(new ImageIcon(ImageIO.read(new File(COMPARE_BTN_IMG_PATH))));
		}
		catch(IOException ex)
		{
			System.out.println("ERROR : IOException : ClsView : Init_TopBounds");
		}
		
		undoBtn.setMargin(new Insets(0, 0, 0, 0));
		redoBtn.setMargin(new Insets(0, 0, 0, 0));
		compareBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeToLeftBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeToRightBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeAllToLeftBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeAllToRightBtn.setMargin(new Insets(0, 0, 0, 0));
		
		undoBtn.setToolTipText(TOOLTIP_UNDO_BTN);
		redoBtn.setToolTipText(TOOLTIP_REDO_BTN);
		compareBtn.setToolTipText(TOOLTIP_COMPARE_BTN);
		mergeToLeftBtn.setToolTipText(TOOLTIP_TO_LEFT_BTN);
		mergeToRightBtn.setToolTipText(TOOLTIP_TO_RIGHT_BTN);
		mergeAllToLeftBtn.setToolTipText(TOOLTIP_ALL_TO_LEFT_BTN);
		mergeAllToRightBtn.setToolTipText(TOOLTIP_ALL_TO_RIGHT_BTN);
		
		topPanel.add(undoBtn);
		topPanel.add(redoBtn);
		topPanel.add(compareBtn);
		topPanel.add(mergeToLeftBtn);
		topPanel.add(mergeToRightBtn);
		topPanel.add(mergeAllToLeftBtn);
		topPanel.add(mergeAllToRightBtn);
		
		//Create undo button listenser
		undoBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(undoBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});

		//Create redo button listenser
		redoBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(redoBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});

		//Create merge to left button listenser
		mergeToLeftBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(mergeToLeftBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});

		//Create merge to right button listenser
		mergeToRightBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(mergeToRightBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create merge all to left button listenser
		mergeAllToLeftBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(mergeAllToLeftBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});

		//Create merge all to right button listenser
		mergeAllToRightBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(mergeAllToRightBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});

		//Create compare button listenser
		compareBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(compareBtn, "DO NOT PRESS.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
	}
	
	//Initialize west bounds of form
	private void Init_WestBounds()
	{
		
		leftSearchPanel = new JPanel();
		leftSearchPanel.setBackground(COMPONENT_BG_COLOR);
		leftSearchPanel.setLayout(new GridLayout(1, 2));
		leftSearchPanel.setPreferredSize(new Dimension(100, internalHeight));
		viewForm.getContentPane().add(leftSearchPanel, BorderLayout.WEST);
		
		leftQuickView = new JQuickView(leftEditor);
		leftSearchPanel.add(leftQuickView);
		
		rightQuickView = new JQuickView(rightEditor);
		leftSearchPanel.add(rightQuickView);
		
	}
	
	//Initialize center bounds of form
	private void Init_CenterBounds()
	{
		
		//Create splitpane
		seperator = new JSplitPane();
		seperator.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		seperator.setDividerLocation((int)((internalWidth - DEFAULT_WEST_WIDTH) * spRatio));
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
		left_TopPanel.setBackground(COMPONENT_BG_COLOR);
		left_TopPanel.setLayout(new FlowLayout(0));
		leftPanel.add(left_TopPanel, BorderLayout.NORTH);
		
		//Create save and load buttons of left side
		try
		{
			leftSaveBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_BTN_IMG_PATH))));
			leftLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_PATH))));
			leftEditBtn = new JButton(new ImageIcon(ImageIO.read(new File(EDIT_BTN_IMG_PATH))));
		}catch(IOException ex){}
		leftSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		leftLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		leftEditBtn.setMargin(new Insets(0, 0, 0, 0));
		leftSaveBtn.setToolTipText(TOOLTIP_SAVE_BTN);
		leftLoadBtn.setToolTipText(TOOLTIP_LOAD_BTN);
		leftEditBtn.setToolTipText(TOOLTIP_EDIT_BTN);
		left_TopPanel.add(leftSaveBtn);
		left_TopPanel.add(leftLoadBtn);
		left_TopPanel.add(leftEditBtn);
		
		//Create left save button listenser
		leftSaveBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(fileDialogBox.showSaveDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					imf1.save();
					JOptionPane.showMessageDialog(leftSaveBtn, fileDialogBox.getSelectedFile().toString(), WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Create left load button listenser
		leftLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(fileDialogBox.showOpenDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					//JOptionPane.showMessageDialog(leftLoadBtn, fileDialogBox.getSelectedFile().toString(), WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
					imf1 = new ImportedFile();
					imf1.load(fileDialogBox.getSelectedFile().toString());
					leftEditor.GetTextPad().SetText(imf1.getText());
					leftEditor.GetTextPad().setEditable(false);
					leftEditBtn.setEnabled(true);
				}
			}
		});
		
		//Create left edit button listenser
		leftEditBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				leftEditor.GetTextPad().setEditable(true);
				leftEditBtn.setEnabled(false);
			}
		});
		
		//Create editor of left side
		leftEditor = new JScrollTextArea();
		leftEditor.GetTextPad().TakeFontFileAndSet(DEFAULT_FONT_PATH);
		leftEditor.GetTextPad().SetFontSize(DEFAULT_FONT_SIZE);
		leftEditor.GetTextPad().setEditable(false);
		leftPanel.add(leftEditor, BorderLayout.CENTER);

		//Create scrollbar listener
		leftEditor.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				leftEditor.SyncVertScrollVal(rightEditor);
				leftQuickView.repaint();
				rightQuickView.repaint();
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
		right_TopPanel.setBackground(COMPONENT_BG_COLOR);
		right_TopPanel.setLayout(new FlowLayout(0));
		rightPanel.add(right_TopPanel, BorderLayout.NORTH);
		
		//Create save and load buttons of right side
		try
		{
			rightSaveBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_BTN_IMG_PATH))));
			rightLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_PATH))));
			rightEditBtn = new JButton(new ImageIcon(ImageIO.read(new File(EDIT_BTN_IMG_PATH))));
		}catch(IOException ex){}
		rightSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		rightLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		rightEditBtn.setMargin(new Insets(0, 0, 0, 0));
		rightSaveBtn.setToolTipText(TOOLTIP_SAVE_BTN);
		rightLoadBtn.setToolTipText(TOOLTIP_LOAD_BTN);
		rightEditBtn.setToolTipText(TOOLTIP_EDIT_BTN);
		right_TopPanel.add(rightSaveBtn);
		right_TopPanel.add(rightLoadBtn);
		right_TopPanel.add(rightEditBtn);
		
		//Create right save button listenser
		rightSaveBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(fileDialogBox.showSaveDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					JOptionPane.showMessageDialog(rightSaveBtn, fileDialogBox.getSelectedFile().toString(), WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Create right load button listenser
		rightLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(fileDialogBox.showOpenDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					//JOptionPane.showMessageDialog(rightLoadBtn, fileDialogBox.getSelectedFile().toString(), WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
					imf2 = new ImportedFile();
					imf2.load(fileDialogBox.getSelectedFile().toString());
					rightEditor.GetTextPad().SetText(imf2.getText());
					rightEditor.GetTextPad().setEditable(false);
					rightEditBtn.setEnabled(true);
				}
			}
		});
		
		//Create right edit button listenser
		rightEditBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				rightEditor.GetTextPad().setEditable(true);
				rightEditBtn.setEnabled(false);
			}
		});
		
		//Create editor of left side
		rightEditor = new JScrollTextArea();
		rightEditor.GetTextPad().TakeFontFileAndSet(DEFAULT_FONT_PATH);
		rightEditor.GetTextPad().SetFontSize(DEFAULT_FONT_SIZE);
		rightEditor.GetTextPad().setEditable(false);
		rightPanel.add(rightEditor, BorderLayout.CENTER);

		//Create scrollbar listener
		rightEditor.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				rightEditor.SyncVertScrollVal(leftEditor);
				leftQuickView.repaint();
				rightQuickView.repaint();
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
		seperator.setDividerLocation((int)((internalWidth - DEFAULT_WEST_WIDTH) * spRatio));

		leftQuickView.repaint();
		rightQuickView.repaint();
		
	}

}
