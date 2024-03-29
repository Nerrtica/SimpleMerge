/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : ClsView.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.28

package view;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.plaf.basic.*;

import controller.ClsController;
import model.*;

import javax.swing.*;
import javax.imageio.*;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ClsView
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	//////////////////////
	//BUTTON NAME
	public final String		NAME_LEFT_SAVE_BTN 		= "LeftSaveButton";					//left save button name
	public final String		NAME_LEFT_SAVE_AS_BTN	= "LeftSaveAsButton";				//left save as button  name
	public final String		NAME_LEFT_LOAD_BTN 		= "LeftLoadButton";					//left load button name
	public final String		NAME_LEFT_EDIT_BTN 		= "LeftEditButton";					//left edit button name
	public final String		NAME_LEFT_COMPLETE_BTN	= "LeftCompleteButton";				//left complete button name
	public final String		NAME_RIGHT_SAVE_BTN 	= "RightSaveButton";				//right save button name
	public final String		NAME_RIGHT_SAVE_AS_BTN	= "RightSaveAsButton";				//right save as button name
	public final String		NAME_RIGHT_LOAD_BTN	 	= "RightLoadButton";				//right load button name
	public final String		NAME_RIGHT_EDIT_BTN 	= "RightEditButton";				//right edit button name
	public final String		NAME_RIGHT_COMPLETE_BTN	= "RightCompleteButton";			//right complete button name
	public final String		NAME_COMPARE_BTN 		= "CompareButton";					//compare button name
	public final String		NAME_TO_LEFT_BTN 		= "MergeToLeftButton";				//merge to left button name
	public final String		NAME_TO_RIGHT_BTN 		= "MergeToRightButton";				//merge to right button name
	public final String		NAME_ALL_TO_LEFT_BTN 	= "MergeAllToLeftButton";			//merge all to left button name
	public final String		NAME_ALL_TO_RIGHT_BTN 	= "MergeAllToRightButton";			//merge all to right button name
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
	public final String		FORM_ICON_PATH				= "Images/Form01.png";			//path of form icon image
	public final String		LOGO_IMG_PATH				= "Images/Logo2.png";			//path of logo image
	public final String 	SAVE_BTN_IMG_PATH 			= "Images/Save01.png";			//path of save button image
	public final String 	LOAD_BTN_IMG_PATH 			= "Images/Open01.png";			//path of load button image
	public final String		TO_LEFT_BTN_IMG_PATH 		= "Images/ToLeft01.png";		//path of to left button image
	public final String		TO_RIGHT_BTN_IMG_PATH 		= "Images/ToRight01.png";		//path of to right button image
	public final String		TO_ALL_LEFT_BTN_IMG_PATH 	= "Images/AllToLeft01.png";		//path of all to left button image
	public final String		TO_ALL_RIGHT_BTN_IMG_PATH 	= "Images/AllToRight01.png";	//path of all to right button image	
	public final String		COMPARE_BTN_IMG_PATH 		= "Images/Compare01.png";		//path of compare button image
	public final String		EDIT_BTN_IMG_PATH 			= "Images/Edit01.png";			//path of edit button image
	public final String		COMPLETE_BTN_IMG_PATH 		= "Images/Complete01.png";		//path of complete button image
	public final String		SAVE_AS_BTN_IMG_PATH		= "Images/SaveAs01.png";		//path of save as button image
	//TOOLTIP TEXT
	public final String		TOOLTIP_COMPARE_BTN 		= "Compare";					//tooltip of compare button
	public final String		TOOLTIP_TO_LEFT_BTN 		= "Merge to Left";				//tooltip of merge to left button
	public final String		TOOLTIP_TO_RIGHT_BTN 		= "Merge to Right";				//tooltip of merge to right button
	public final String		TOOLTIP_ALL_TO_LEFT_BTN 	= "Merge all to Left";			//tooltip of merge all to left button
	public final String		TOOLTIP_ALL_TO_RIGHT_BTN 	= "Merge all to Right";			//tooltip of merge all to right button
	public final String		TOOLTIP_SAVE_BTN 			= "Save";						//tooltip of save button
	public final String		TOOLTIP_LOAD_BTN 			= "Open";						//tooltip of load button
	public final String		TOOLTIP_EDIT_BTN 			= "Edit";						//tooltip of edit button
	public final String		TOOLTIP_COMPLETE_BTN		= "Complete Edit";				//tooltip of complete button
	public final String		TOOLTIP_SAVE_AS_BTN			= "Save As";					//tooltip of save as button
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
	private JPanel			topButtonPanel;
	private JPanel			topLogoPanel;
	private JLabel			topLogoLabel;
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
	private JButton 		leftSaveBtn, leftSaveAsBtn, leftLoadBtn, leftEditBtn, leftCompleteBtn;
	private JScrollTextArea	leftEditor;
	//center - right side
	private JPanel 			rightPanel, right_TopPanel;
	private JButton 		rightSaveBtn, rightSaveAsBtn, rightLoadBtn, rightEditBtn, rightCompleteBtn;
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
	
	private ButtonListener btnListener;

	//Constructor
	public ClsView(ClsController i_controller)
	{
		
		InitView(i_controller);
		
	}
	
	//Initialize view class
	private void InitView(ClsController i_refCtrler)
	{

		OSBasedSetting();
		
		Init_Variables();
		Init_Form(i_refCtrler);
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
	private void Init_Form(ClsController i_refCtrler)
	{
		
		btnListener = new ButtonListener(i_refCtrler);
		
		//Create main window
		viewForm = new JFrame(WINDOW_CAPTION);
		try
		{
			viewForm.setIconImage(new ImageIcon(ImageIO.read(new File(FORM_ICON_PATH))).getImage());
		}
		catch(IOException ex)
		{
			System.out.println("ERROR : IOException : ClsView : Init_Form");
		}
		viewForm.setLocation(DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
		viewForm.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewForm.getContentPane().setLayout(new BorderLayout(0, 0));
		viewForm.setVisible(true);
		
		//Create listener when window's size is changed
		viewForm.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                WindowResized();
            }
        });
		
		//set file dialog box window
		fileDialogBox = new JFileChooser(System.getProperty("user.home") + "/Desktop");
		fileDialogBox.setMultiSelectionEnabled(false);
		fileDialogBox.setPreferredSize(new Dimension(DEFAULT_DIALOG_WIDTH, DEFAULT_DIALOG_HEIGHT));
		InitDialogBoxFont(fileDialogBox.getComponents());
		
		//Reset Internal form size
		ResetInternalFormSize();
		
		//recalculate separate ratio
		spRatio = (float)((internalWidth - DEFAULT_WEST_WIDTH) / 2) / (internalWidth - DEFAULT_WEST_WIDTH);
		
	}
	
	//Initialize dialogbox font
	private void InitDialogBoxFont(Component[] comp)
	{
		
		int i;
		Font dialogFont = DEFAULT_DIALOG_FONT;
		
		for(i = 0; i < comp.length; i++)
		{
			if(comp[i] instanceof Container)
				InitDialogBoxFont(((Container)comp[i]).getComponents());
			
			try
			{
				comp[i].setFont(dialogFont);
			}
			catch(Exception ex)
			{
				System.out.println("ERROR : Exception : ClsView : InitDialogBoxFont");
			}
		}
		
	}
	
	//Initialize top bounds of form
	private void Init_TopBounds()
	{
		
		//Create base top panel
		topPanel = new JPanel();
		topPanel.setBackground(COMPONENT_BG_COLOR);
		topPanel.setLayout(new GridLayout(1, 2));
		viewForm.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		//Create top panel that has buttons
		topButtonPanel = new JPanel();
		topButtonPanel.setBackground(COMPONENT_BG_COLOR);
		topButtonPanel.setLayout(new FlowLayout(0));
		topPanel.add(topButtonPanel);
		
		//Create top panel that has logo image
		topLogoPanel = new JPanel();
		topLogoPanel.setBackground(COMPONENT_BG_COLOR);
		topLogoPanel.setLayout(new FlowLayout(2));
		topPanel.add(topLogoPanel);
		
		//Create Logo
		try
		{
			topLogoLabel = new JLabel("", new ImageIcon(ImageIO.read(new File(LOGO_IMG_PATH))), JLabel.CENTER);
			topLogoPanel.add(topLogoLabel);
		}
		catch(IOException ex)
		{
			System.out.println("ERROR : IOException : ClsView : Init_TopBounds");
		}
		
		//Create button and get button images
		try
		{
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
		
		compareBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeToLeftBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeToRightBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeAllToLeftBtn.setMargin(new Insets(0, 0, 0, 0));
		mergeAllToRightBtn.setMargin(new Insets(0, 0, 0, 0));
		
		compareBtn.setToolTipText(TOOLTIP_COMPARE_BTN);
		mergeToLeftBtn.setToolTipText(TOOLTIP_TO_LEFT_BTN);
		mergeToRightBtn.setToolTipText(TOOLTIP_TO_RIGHT_BTN);
		mergeAllToLeftBtn.setToolTipText(TOOLTIP_ALL_TO_LEFT_BTN);
		mergeAllToRightBtn.setToolTipText(TOOLTIP_ALL_TO_RIGHT_BTN);
		
		compareBtn.setName(NAME_COMPARE_BTN);
		mergeToLeftBtn.setName(NAME_TO_LEFT_BTN);
		mergeToRightBtn.setName(NAME_TO_RIGHT_BTN);
		mergeAllToLeftBtn.setName(NAME_ALL_TO_LEFT_BTN);
		mergeAllToRightBtn.setName(NAME_ALL_TO_RIGHT_BTN);
		
		compareBtn.addActionListener(btnListener);
		mergeToLeftBtn.addActionListener(btnListener);
		mergeToRightBtn.addActionListener(btnListener);
		mergeAllToLeftBtn.addActionListener(btnListener);
		mergeAllToRightBtn.addActionListener(btnListener);
		
		topButtonPanel.add(compareBtn);
		topButtonPanel.add(mergeToLeftBtn);
		topButtonPanel.add(mergeToRightBtn);
		topButtonPanel.add(mergeAllToLeftBtn);
		topButtonPanel.add(mergeAllToRightBtn);
		
		compareBtn.setEnabled(false);
		setMergeBtnEnable(false);
		
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
		
		//Link
		rightEditor.GetTextPad().LinkTextArea(leftEditor.GetTextPad());
		leftEditor.GetTextPad().LinkTextArea(rightEditor.GetTextPad());
		
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
			leftSaveAsBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_AS_BTN_IMG_PATH))));
			leftLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_PATH))));
			leftEditBtn = new JButton(new ImageIcon(ImageIO.read(new File(EDIT_BTN_IMG_PATH))));
			leftCompleteBtn = new JButton(new ImageIcon(ImageIO.read(new File(COMPLETE_BTN_IMG_PATH))));
		}
		catch(IOException ex)
		{
			System.out.println("ERROR : IOException : ClsView : Init_LeftBounds");
		}
		
		leftSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		leftSaveAsBtn.setMargin(new Insets(0, 0, 0, 0));
		leftLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		leftEditBtn.setMargin(new Insets(0, 0, 0, 0));
		leftCompleteBtn.setMargin(new Insets(0, 0, 0, 0));
		
		leftSaveBtn.setToolTipText(TOOLTIP_SAVE_BTN);
		leftSaveAsBtn.setToolTipText(TOOLTIP_SAVE_AS_BTN);
		leftLoadBtn.setToolTipText(TOOLTIP_LOAD_BTN);
		leftEditBtn.setToolTipText(TOOLTIP_EDIT_BTN);
		leftCompleteBtn.setToolTipText(TOOLTIP_COMPLETE_BTN);
		
		leftSaveBtn.setName(NAME_LEFT_SAVE_BTN);
		leftSaveAsBtn.setName(NAME_LEFT_SAVE_AS_BTN);
		leftLoadBtn.setName(NAME_LEFT_LOAD_BTN);
		leftEditBtn.setName(NAME_LEFT_EDIT_BTN);
		leftCompleteBtn.setName(NAME_LEFT_COMPLETE_BTN);
		
		leftSaveBtn.addActionListener(btnListener);
		leftSaveAsBtn.addActionListener(btnListener);
		leftLoadBtn.addActionListener(btnListener);
		leftEditBtn.addActionListener(btnListener);
		leftCompleteBtn.addActionListener(btnListener);
		
		left_TopPanel.add(leftSaveBtn);
		left_TopPanel.add(leftSaveAsBtn);
		left_TopPanel.add(leftLoadBtn);
		left_TopPanel.add(leftEditBtn);
		left_TopPanel.add(leftCompleteBtn);

		leftSaveBtn.setEnabled(false);
		
		leftCompleteBtn.setEnabled(false);
		
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
			rightSaveAsBtn = new JButton(new ImageIcon(ImageIO.read(new File(SAVE_AS_BTN_IMG_PATH))));
			rightLoadBtn = new JButton(new ImageIcon(ImageIO.read(new File(LOAD_BTN_IMG_PATH))));
			rightEditBtn = new JButton(new ImageIcon(ImageIO.read(new File(EDIT_BTN_IMG_PATH))));
			rightCompleteBtn = new JButton(new ImageIcon(ImageIO.read(new File(COMPLETE_BTN_IMG_PATH))));
		}
		catch(IOException ex)
		{
			System.out.println("ERROR : IOException : ClsView : Init_RightBounds");
		}
		
		rightSaveBtn.setMargin(new Insets(0, 0, 0, 0));
		rightSaveAsBtn.setMargin(new Insets(0, 0, 0, 0));
		rightLoadBtn.setMargin(new Insets(0, 0, 0, 0));
		rightEditBtn.setMargin(new Insets(0, 0, 0, 0));
		rightCompleteBtn.setMargin(new Insets(0, 0, 0, 0));
		
		rightSaveBtn.setToolTipText(TOOLTIP_SAVE_BTN);
		rightSaveAsBtn.setToolTipText(TOOLTIP_SAVE_AS_BTN);
		rightLoadBtn.setToolTipText(TOOLTIP_LOAD_BTN);
		rightEditBtn.setToolTipText(TOOLTIP_EDIT_BTN);
		rightCompleteBtn.setToolTipText(TOOLTIP_COMPLETE_BTN);
		
		rightSaveBtn.setName(NAME_RIGHT_SAVE_BTN);
		rightSaveAsBtn.setName(NAME_RIGHT_SAVE_AS_BTN);
		rightLoadBtn.setName(NAME_RIGHT_LOAD_BTN);
		rightEditBtn.setName(NAME_RIGHT_EDIT_BTN);
		rightCompleteBtn.setName(NAME_RIGHT_COMPLETE_BTN);
		
		rightSaveBtn.addActionListener(btnListener);
		rightSaveAsBtn.addActionListener(btnListener);
		rightLoadBtn.addActionListener(btnListener);
		rightEditBtn.addActionListener(btnListener);
		rightCompleteBtn.addActionListener(btnListener);
		
		right_TopPanel.add(rightSaveBtn);
		right_TopPanel.add(rightSaveAsBtn);
		right_TopPanel.add(rightLoadBtn);
		right_TopPanel.add(rightEditBtn);
		right_TopPanel.add(rightCompleteBtn);

		rightSaveBtn.setEnabled(false);
		
		rightCompleteBtn.setEnabled(false);
		
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
	
	//////////////////////////////////////////////////////////////////
	
	//set right save, save as button enable true or false
	public void setRightSaveBtnEnable (boolean i_enable)
	{
		rightSaveBtn.setEnabled(i_enable);
		rightSaveAsBtn.setEnabled(i_enable);
	}
	
	//set left save, save as button enable true or false
	public void setLeftSaveBtnEnable (boolean i_enable)
	{
		leftSaveBtn.setEnabled(i_enable);
		leftSaveAsBtn.setEnabled(i_enable);
	}
	
	//set buttons relate to merge enable true or false
	public void setMergeBtnEnable (boolean i_enable)
	{
		mergeToLeftBtn.setEnabled(i_enable);
		mergeToRightBtn.setEnabled(i_enable);
		mergeAllToLeftBtn.setEnabled(i_enable);
		mergeAllToRightBtn.setEnabled(i_enable);
	}
	
	//set left side textarea editable or not and toggle related button
	public void setLeftEditable(boolean i_enable)
	{
		leftEditor.GetTextPad().setEditable(i_enable);
		leftEditBtn.setEnabled(!i_enable);
		leftCompleteBtn.setEnabled(i_enable);
	}
	
	//set right side textarea editable or not and toggle related button
	public void setRightEditable(boolean i_enable)
	{
		rightEditor.GetTextPad().setEditable(i_enable);
		rightEditBtn.setEnabled(!i_enable);
		rightCompleteBtn.setEnabled(i_enable);
	}
	
	public JScrollTextArea getTextEditor(boolean isLeft)
	{
		if(isLeft)
			return leftEditor;
		else
			return rightEditor;
	}
	
	public void repaintQuickView()
	{
		leftQuickView.repaint();
		rightQuickView.repaint();
	}
	
	//////////////////////////////////////////////////////////////////
	
	public void setText(ArrayList<String> i_text, boolean isLeft)
	{
		
		if(isLeft)
			leftEditor.GetTextPad().SetText(i_text);
		else
			rightEditor.GetTextPad().SetText(i_text);
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//inner class about button listener
	private class ButtonListener implements ActionListener
	{

        private ClsController refCtrler;

        ButtonListener(ClsController i_refCtrler) {
            refCtrler = i_refCtrler;
        }

		public void actionPerformed(ActionEvent e)
		{
			String t_srcName = ((JButton)e.getSource()).getName();

			if(t_srcName.equals(NAME_COMPARE_BTN))
			{
				refCtrler.compare();
				
				setMergeBtnEnable(true);

				repaintQuickView();
			}
			else if(t_srcName.equals(NAME_TO_LEFT_BTN))//////////////////////////////////////////////MERGE///////////
			{
				try
				{
					refCtrler.merge(rightEditor.GetTextPad().GetMergeBlockIndex(), true);
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(viewForm, "Select block please.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
				repaintQuickView();
			}
			else if(t_srcName.equals(NAME_TO_RIGHT_BTN))
			{
				try
				{
					refCtrler.merge(leftEditor.GetTextPad().GetMergeBlockIndex(), false);
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(viewForm, "Select block please.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
				repaintQuickView();
			}
			else if(t_srcName.equals(NAME_ALL_TO_LEFT_BTN))
			{
				try
				{
					refCtrler.mergeAll(true);
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(viewForm, "Select block please.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
				repaintQuickView();
			}
			else if(t_srcName.equals(NAME_ALL_TO_RIGHT_BTN))
			{
				try
				{
					refCtrler.mergeAll(false);
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(viewForm, "Select block please.", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
				repaintQuickView();
			}
			else if(t_srcName.equals(NAME_LEFT_SAVE_BTN))//////////////////////////////////////////////LEFT SIDE///////////
			{
				try{
					refCtrler.save(true);
				}
				catch(NullPointerException ex){
					JOptionPane.showMessageDialog(viewForm, "It don't have file route", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(t_srcName.equals(NAME_LEFT_SAVE_AS_BTN))
			{
				if(fileDialogBox.showSaveDialog(viewForm) == JFileChooser.APPROVE_OPTION)
					refCtrler.saveAs(fileDialogBox.getSelectedFile().toString(), true);
			}
			else if(t_srcName.equals(NAME_LEFT_LOAD_BTN))
			{
				if(fileDialogBox.showOpenDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					try{
						refCtrler.load(fileDialogBox.getSelectedFile().toString(), true);
					}
					catch(FileNotFoundException ex){
						JOptionPane.showMessageDialog(viewForm, "It is wrong file name", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
					}
					setLeftEditable(false);
					setLeftSaveBtnEnable(true);
					
					compareBtn.setEnabled(true);
					setMergeBtnEnable(false);
				}
			}
			else if(t_srcName.equals(NAME_LEFT_EDIT_BTN))
			{
				setLeftEditable(true);
				setLeftSaveBtnEnable(false);
				
				setMergeBtnEnable(false);
				compareBtn.setEnabled(false);
				
				leftEditor.GetTextPad().ResetSelectedMark();
			}
			else if(t_srcName.equals(NAME_LEFT_COMPLETE_BTN))
			{
				setLeftEditable(false);
				setLeftSaveBtnEnable(true);
				
				refCtrler.edit(leftEditor.GetTextPad().GetText(), leftEditor.GetTextPad().GetLineBoolList(), true);
				
				if(!rightCompleteBtn.isEnabled())
					compareBtn.setEnabled(true);
			}
			else if(t_srcName.equals(NAME_RIGHT_SAVE_BTN))//////////////////////////////////////////////RIGHT SIDE///////////
			{
				try{
					refCtrler.save(false);
				}
				catch(NullPointerException ex){
					JOptionPane.showMessageDialog(viewForm, "It don't have file route", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(t_srcName.equals(NAME_RIGHT_SAVE_AS_BTN))
			{
				if(fileDialogBox.showSaveDialog(viewForm) == JFileChooser.APPROVE_OPTION)
					refCtrler.saveAs(fileDialogBox.getSelectedFile().toString(), false);
			}
			else if(t_srcName.equals(NAME_RIGHT_LOAD_BTN))
			{
				if(fileDialogBox.showOpenDialog(viewForm) == JFileChooser.APPROVE_OPTION)
				{
					try{
						refCtrler.load(fileDialogBox.getSelectedFile().toString(), false);
					}
					catch(FileNotFoundException ex){
						JOptionPane.showMessageDialog(viewForm, "It is wrong file name", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
					}
					setRightEditable(false);
					setRightSaveBtnEnable(true);
					
					compareBtn.setEnabled(true);
					setMergeBtnEnable(false);
				}
			}
			else if(t_srcName.equals(NAME_RIGHT_EDIT_BTN))
			{
				setRightEditable(true);
				setRightSaveBtnEnable(false);
				
				setMergeBtnEnable(false);
				compareBtn.setEnabled(false);
				
				rightEditor.GetTextPad().ResetSelectedMark();
			}
			else if(t_srcName.equals(NAME_RIGHT_COMPLETE_BTN))
			{
				setRightEditable(false);
				setRightSaveBtnEnable(true);
				
				refCtrler.edit(rightEditor.GetTextPad().GetText(), rightEditor.GetTextPad().GetLineBoolList(), false);
				
				if(!leftCompleteBtn.isEnabled())
					compareBtn.setEnabled(true);
			}

		}

	}
	
	//////////////////////////////////////////////////////////////////
	
	
	
}
