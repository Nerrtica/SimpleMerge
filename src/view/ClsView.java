//////////////////////////////////////////////////////////////////
//메모
//에디터에서 글자를 수정했을 때 수정한 영역의 위치와 수정한 내용을 리턴을 할수 있게하기
//////////////////////////////////////////////////////////////////

package view;

import java.awt.*;
import java.io.*;
import java.beans.*;
import java.awt.event.*;

import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class JMergeEditor extends JTextArea
{
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(new Color(255, 0, 0, 100));
		g.fillRect(10, 10, 200, 100);
	}
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ClsView
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	//////////////////////
	//FORM
	public final int DEFAULT_WINDOW_WIDTH = 800;					//Window default width size
	public final int DEFAULT_WINDOW_HEIGHT = 600;					//Window default height size
	public final String WINDOW_CAPTION = "Simple_Merge_TEAM_11";	//Window caption name
	public final int WINDOW_MARGIN_X = 20;							//Window internal margin x-axis
	public final int WINDOW_MARGIN_Y = 50;							//Window internal margin y-axis
	//SPLITPANE
	public final int DIVIDER_THICKNESS = 3;						//size of seperating bar in splitpane
	//IMAGE ADDRESS
	public final String SAVE_BTN_IMG_ADDRESS = "Images/Save01.png";	//address of save button image
	public final String LOAD_BTN_IMG_ADDRESS = "Images/Load01.png";	//address of load button image
	//EDITOR
	public final String EDITOR_FONT = "Bitstream Vera Sans Mono";		//editor's font
	public final int EDITOR_FONT_SIZE = 12;							//editor's font size
	public final int EDITOR_TAB_SIZE = 4;								//editor's tab size
	public final Color EDITOR_BG_COLOR = Color.WHITE;				//editor's background color
	public final Color EDITOR_FONT_COLOR = Color.BLACK;				//editor's font color
	public final Color EDITOR_SELECTION_COLOR = Color.BLACK;			//editor's selection color (when blocked)
	public final Color EDITOR_SELECTED_FONT_COLOR = Color.YELLOW;	//editor's selected font color (when blocked)
	//SCROLL
	public final int WHEEL_SCROLL_AMOUNT = 54;			//scroll amount when mouse wheel
	//SHADE
	public final int EDITOR_LINE_HEIGHT = 15;			//line height in editor
	
	//////////////////////////////////////////////////////////////////
	//GUI Components
	//////////////////////
	//form
	private JFrame viewForm;
	//form - north
	private JPanel topPanel;
	private JButton undoBtn, redoBtn;
	//form - center
	private JSplitPane seperator;
	//center - left side
	private JPanel leftPanel, left_TopPanel;
	private JButton leftSaveBtn, leftLoadBtn;
	private JMergeEditor leftEditor;
	private JScrollPane leftScrollPane;
	//center - right side
	private JPanel rightPanel, right_TopPanel;
	private JButton rightSaveBtn, rightLoadBtn;
	private JMergeEditor rightEditor;
	private JScrollPane rightScrollPane;

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
		
		Init_Variables();
		Init_Form();
		Init_TopBounds();
		Init_CenterBounds();

	}
	
	//Initialize class variables
	private void Init_Variables()
	{
		
		spRatio = 0.5f;
		
	}
	
	//Initialize form
	private void Init_Form()
	{
		
		viewForm = new JFrame(WINDOW_CAPTION);
		viewForm.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewForm.getContentPane().setLayout(new BorderLayout(0,0));
		
		viewForm.addComponentListener(new ComponentListener()
		{
			public void componentResized(ComponentEvent e)
			{
				WindowResized();
			}

			//@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			//@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			//@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
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
		
		//Create redo button
		redoBtn = new JButton("REDO");
		topPanel.add(redoBtn);
		
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
		
		//Create editor of left side
		leftEditor = new JMergeEditor();
		leftEditor.setFont(new Font(EDITOR_FONT, Font.PLAIN, EDITOR_FONT_SIZE));
		leftEditor.setTabSize(EDITOR_TAB_SIZE);
		leftEditor.setBackground(EDITOR_BG_COLOR);
		leftEditor.setForeground(EDITOR_FONT_COLOR);
		leftEditor.setSelectionColor(EDITOR_SELECTION_COLOR);
		leftEditor.setSelectedTextColor(EDITOR_SELECTED_FONT_COLOR);
		
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
				System.out.println("INSERT");
			}
			
			public void changedUpdate(DocumentEvent e)
			{
				
			}
			public void removeUpdate(DocumentEvent e)
			{
				System.out.println("REMOVE");
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
		
		//Create editor of right side
		rightEditor = new JMergeEditor();
		rightEditor.setFont(new Font(EDITOR_FONT, Font.PLAIN, EDITOR_FONT_SIZE));
		rightEditor.setTabSize(EDITOR_TAB_SIZE);
		rightEditor.setBackground(EDITOR_BG_COLOR);
		rightEditor.setForeground(EDITOR_FONT_COLOR);
		rightEditor.setSelectionColor(EDITOR_SELECTION_COLOR);
		rightEditor.setSelectedTextColor(EDITOR_SELECTED_FONT_COLOR);
		
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
				System.out.println("INSERT");
			}
			
			public void changedUpdate(DocumentEvent e)
			{
				
			}
			public void removeUpdate(DocumentEvent e)
			{
				System.out.println("REMOVE");
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
