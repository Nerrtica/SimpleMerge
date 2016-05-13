//////////////////////////////////////////////////////////////////
//메모
//oop개념에 맞게 코드 좀더 정리
//mark list 지우는 메서드 성능 개선
//이쁘게꾸미기
//왼쪽에 전체적으로 마크된부분 볼 수 있게 추가
//한글 안나오는 문제 수정
//////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : ClsView.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.11

package view;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.plaf.basic.*;
import javax.swing.text.BadLocationException;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class JMergeEditor extends JTextArea
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	public final int 		DEFAULT_SHADE_HEIGHT_UNIT = 10;
	public final Color 	DEFAULT_SHADE_COLOR = new Color(255, 0, 0, 100);
	
	//////////////////////////////////////////////////////////////////
	//private variables
	private Color shadeColor;
	private int shadeHeightUnit;
	
	ArrayList<Integer> markList;
	
	//////////////////////////////////////////////////////////////////
	
	//Constructor
	JMergeEditor()
	{
		
		shadeColor = DEFAULT_SHADE_COLOR;
		shadeHeightUnit = DEFAULT_SHADE_HEIGHT_UNIT;
		markList = new ArrayList<Integer>();
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Get shade color
	public Color GetShadeColor()
	{
		return shadeColor;
	}
	
	//Set shade color
	public void SetShadeColor(Color i_color)
	{
		
		int t_red, t_green, t_blue;
		
		//if input color is not translucent, set default alpha to 100
		if(i_color.getAlpha() == 0)
		{
			t_red = i_color.getRed();
			t_green = i_color.getGreen();
			t_blue = i_color.getBlue();
			shadeColor = new Color(t_red, t_green, t_blue, 100);
		}
		else
			shadeColor = i_color;
		
	}
	
	//Get shade height unit
	public int getShadeHeightUnit()
	{
		return shadeHeightUnit;
	}
	
	//Set shade height unit
	public void SetShadeHeightUnit(int i_shadeHeightUnit)
	{
		shadeHeightUnit = i_shadeHeightUnit;
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Add one elements to marklist
	public void AddMarkList(int i_lineNum)
	{
		markList.add(i_lineNum);
		super.repaint();
	}
	
	//Add list to marklist
	public void AddMarkList(ArrayList<Integer> i_list)
	{
		markList.addAll(i_list);
		super.repaint();
	}
	
	//Remove one elements in marklist
	public void RemoveMark(int i_lineNum)
	{
		
		int i, len;
		
		//if markList is empty, return
		if(markList.isEmpty())
			return;
		
		//initialize local variables
		len = markList.size();
		
		//find that line and remove
		for(i = 0; i < len; i++)
		{
			if(markList.get(i) == i_lineNum)
			{
				markList.remove(i);
				return;
			}
		}
		
		//repaint
		super.repaint();
		
	}
	
	//Remove all elements in marklist
	public void RemoveAllMark()
	{
		markList.clear();
		super.repaint();
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Paint All marklist
	private void PaintShade(Graphics g)
	{
		
		int i, len, width;
		
		//if markList is empty, return
		if(markList.isEmpty())
			return;
		
		//initialize local variables
		len = markList.size();
		width = super.getWidth();
		
		//set paint color
		g.setColor(shadeColor);
		
		//paint all
		for(i = 0; i < len; i++)
			g.fillRect(0, markList.get(i) * shadeHeightUnit, width, shadeHeightUnit);
		
	}
	
	//Override paintComponent
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		PaintShade(g);

	}
	
}

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
	public final int 		WINDOW_MARGIN_X = 20;							//Window internal margin x-axis
	public final int 		WINDOW_MARGIN_Y = 50;							//Window internal margin y-axis
	//SPLITPANE
	public final int 		DIVIDER_THICKNESS = 3;							//size of seperating bar in splitpane
	//IMAGE ADDRESS
	public final String 	SAVE_BTN_IMG_ADDRESS = "Images\\Save01.png";		//address of save button image
	public final String 	LOAD_BTN_IMG_ADDRESS = "Images\\Load01.png";		//address of load button image
	//EDITOR
	public final String		DEFAULT_FONT_PATH = "Fonts\\D2Coding.ttc";
	//public final String 	EDITOR_FONT = "Bitstream Vera Sans Mono";		//editor's font
	public final String 	EDITOR_FONT = "D2Coding";						//editor's font
	public final int 		EDITOR_FONT_SIZE = 12;							//editor's font size
	public final int 		EDITOR_TAB_SIZE = 4;							//editor's tab size
	public final Color 		EDITOR_BG_COLOR = Color.WHITE;					//editor's background color
	public final Color 		EDITOR_FONT_COLOR = Color.BLACK;				//editor's font color
	public final Color 		EDITOR_SELECTION_COLOR = Color.BLACK;			//editor's selection color (when blocked)
	public final Color 		EDITOR_SELECTED_FONT_COLOR = Color.YELLOW;		//editor's selected font color (when blocked)
	//SCROLL
	public final int 		WHEEL_SCROLL_AMOUNT = 54;						//scroll amount when mouse wheel
	//SHADE
	public final int 		EDITOR_LINE_HEIGHT = 15;						//line height in editor
	public final Color 		SHADE_COLOR = new Color(255, 255, 0, 100);		//shade color
	
	//////////////////////////////////////////////////////////////////
	//GUI Components
	//////////////////////
	//form
	private JFrame 			viewForm;
	//form - north
	private JPanel 			topPanel;
	private JButton 		undoBtn, redoBtn;
	//form - center
	private JSplitPane 		seperator;
	//center - left side
	private JPanel 			leftPanel, left_TopPanel;
	private JButton 		leftSaveBtn, leftLoadBtn;
	private JMergeEditor 	leftEditor;
	private JScrollPane 	leftScrollPane;
	//center - right side
	private JPanel 			rightPanel, right_TopPanel;
	private JButton 		rightSaveBtn, rightLoadBtn;
	private JMergeEditor 	rightEditor;
	private JScrollPane 	rightScrollPane;

	//////////////////////////////////////////////////////////////////
	//private variables
	//////////////////////
	//seperator
	private float spRatio;			//seperator's divider's location ratio
	//clipping area size
	private int internalWidth;		//real form's clipping area width
	private int internalHeight;	//real form's clipping area height
	
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

		InstallFont();
		
		Init_Variables();
		Init_Form();
		Init_TopBounds();
		Init_CenterBounds();

	}
	
	//Install D2Coding.ttc font to system's font folder
	private void InstallFont()
	{
		
		//Get file path of font to move
		File installFontFile = new File(DEFAULT_FONT_PATH);
		//Get path of System's font folder
		String systemRootPath = System.getenv().get("SystemRoot");
		
		systemRootPath = systemRootPath + "\\Fonts\\";
		System.out.println("System font Folder Path : " + systemRootPath);
		
		//Move file
		try
		{
			if(installFontFile.renameTo(new File(systemRootPath + installFontFile.getName())))
				System.out.println("Font has installed successfully.");
			else
				System.out.println("Failed to install font.");
		}catch(Exception e) { System.out.println("Exception occor.");}
		
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
				JOptionPane.showMessageDialog(undoBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(redoBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
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
				JOptionPane.showMessageDialog(leftSaveBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create left load button listenser
		leftLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(leftLoadBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create editor of left side
		leftEditor = new JMergeEditor();
		leftEditor.setFont(new Font(EDITOR_FONT, Font.PLAIN, EDITOR_FONT_SIZE));
		leftEditor.setTabSize(EDITOR_TAB_SIZE);
		leftEditor.setBackground(EDITOR_BG_COLOR);
		leftEditor.setForeground(EDITOR_FONT_COLOR);
		leftEditor.setSelectionColor(EDITOR_SELECTION_COLOR);
		leftEditor.setSelectedTextColor(EDITOR_SELECTED_FONT_COLOR);
		leftEditor.SetShadeColor(SHADE_COLOR);
		leftEditor.SetShadeHeightUnit(15);
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(0);
		a.add(3);
		a.add(5);
		leftEditor.AddMarkList(a);
		
		//Create editor's mouse wheel listener
		leftEditor.addMouseWheelListener(new MouseAdapter()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if(e.getWheelRotation() < 0)
					WheelScroll(false);
				else
					WheelScroll(true);
			}
		});
		
		//Create editor's document listener
		leftEditor.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				System.out.print("INSERT ");
				System.out.print(e.getLength() + " ");
				System.out.print(e.getOffset() + " ");
				try
				{
					System.out.println(e.getDocument().getText(e.getOffset(), e.getLength()));
				} catch (BadLocationException e1) {}
			}
			
			public void changedUpdate(DocumentEvent e)
			{
				
			}
			public void removeUpdate(DocumentEvent e)
			{
				System.out.print("REMOVE ");
				System.out.print(e.getLength() + " ");
				System.out.println(e.getOffset() + " ");
			}
		});
		
		//Create scrollpane of left side
		leftScrollPane = new JScrollPane(leftEditor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		leftPanel.add(leftScrollPane, BorderLayout.CENTER);
		
		//Create scrollbar listener of scrollpane
		leftScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				rightScrollPane.getVerticalScrollBar().setValue(leftScrollPane.getVerticalScrollBar().getValue());
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
				JOptionPane.showMessageDialog(rightSaveBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create right load button listenser
		rightLoadBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(rightLoadBtn, "누르지마", WINDOW_CAPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//Create editor of right side
		rightEditor = new JMergeEditor();
		rightEditor.setFont(new Font(EDITOR_FONT, Font.PLAIN, EDITOR_FONT_SIZE));
		rightEditor.setTabSize(EDITOR_TAB_SIZE);
		rightEditor.setBackground(EDITOR_BG_COLOR);
		rightEditor.setForeground(EDITOR_FONT_COLOR);
		rightEditor.setSelectionColor(EDITOR_SELECTION_COLOR);
		rightEditor.setSelectedTextColor(EDITOR_SELECTED_FONT_COLOR);
		rightEditor.SetShadeColor(SHADE_COLOR);
		rightEditor.SetShadeHeightUnit(15);
		
		//Create editor's mouse wheel listener
		rightEditor.addMouseWheelListener(new MouseAdapter()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if(e.getWheelRotation() < 0)
					WheelScroll(false);
				else
					WheelScroll(true);
			}
		});
		
		//Create editor's document listener
		rightEditor.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				System.out.print("INSERT ");
				System.out.print(e.getLength() + " ");
				System.out.print(e.getOffset() + " ");
				try
				{
					System.out.println(e.getDocument().getText(e.getOffset(), e.getLength()));
				} catch (BadLocationException e1) {}
			}
			
			public void changedUpdate(DocumentEvent e)
			{
				
			}
			public void removeUpdate(DocumentEvent e)
			{
				System.out.print("REMOVE ");
				System.out.print(e.getLength() + " ");
				System.out.println(e.getOffset() + " ");
			}
		});
		
		//Create scrollpane of right side
		rightScrollPane = new JScrollPane(rightEditor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rightPanel.add(rightScrollPane, BorderLayout.CENTER);
		
		//Create scrollbar listener of scrollpane
		rightScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				leftScrollPane.getVerticalScrollBar().setValue(rightScrollPane.getVerticalScrollBar().getValue());
			}
		});
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//Reset form's real clipping area size
	private void ResetInternalFormSize()
	{
		
		internalWidth = viewForm.getWidth() - WINDOW_MARGIN_X;
		internalHeight = viewForm.getHeight() - WINDOW_MARGIN_Y;
		
	}
	
	//When form's size is changed
	private void WindowResized()
	{
		
		//Reset internal form size
		ResetInternalFormSize();
		
		//Maintain divider's location ratio
		seperator.setDividerLocation((int)(internalWidth * spRatio));
		
	}
	
	//when mouse wheel scrolled
	private void WheelScroll(boolean upDown)
	{
		//Synchronize scroll value of left and right side
		if(upDown == false)
		{
			leftScrollPane.getVerticalScrollBar().setValue(leftScrollPane.getVerticalScrollBar().getValue() - WHEEL_SCROLL_AMOUNT);
			rightScrollPane.getVerticalScrollBar().setValue(rightScrollPane.getVerticalScrollBar().getValue() - WHEEL_SCROLL_AMOUNT);
		}
		else
		{
			leftScrollPane.getVerticalScrollBar().setValue(leftScrollPane.getVerticalScrollBar().getValue() + WHEEL_SCROLL_AMOUNT);
			rightScrollPane.getVerticalScrollBar().setValue(rightScrollPane.getVerticalScrollBar().getValue() + WHEEL_SCROLL_AMOUNT);
		}
	}
	
}
