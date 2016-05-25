/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : JMarkTextArea.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.18

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Position;

public class JMarkTextArea extends JTextArea
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	//font
	public final String 	DEFALUT_FONT = "Consolas";									//default font
	public final float 		DEFAULT_FONT_SIZE = 12.0f;									//default font size
	public final int 		DEFAULT_TAB_SIZE = 4;										//default tab size
	//editor colors
	public final Color 		DEFAULT_FONT_COLOR = Color.BLACK;							//default font color
	public final Color 		DEFAULT_BG_COLOR = Color.WHITE;								//default background color
	public final Color 		DEFAULT_SELECTED_FONT_COLOR = Color.YELLOW;					//default selected font color (when blocked)
	public final Color 		DEFAULT_SELECTION_COLOR = Color.BLACK;						//default selection color (when blocked)
	//mark color
	public final Color 		DEFAULT_MARK_COLOR = new Color(255, 0, 0, 100);				//default mark color
	public final Color		DEFAULT_SELECTED_MARK_COLOR = new Color(200, 200, 0, 100);	//default selected mark color
	
	//////////////////////////////////////////////////////////////////
	//private variables
	private Color markColor;					//mark color value
	private Color selectedMarkColor;			//selected mark color value
	private int lineHeight;						//each line's height
	
	private int selectLineStart, selectLineEnd;
	
	private ArrayList<Boolean> markList;		//if true, mark that line
	private ArrayList<Boolean> lineBoolList;	//if false, that line won't be save (fake line)
	private ArrayList<Integer> blockIndexList;
	
	//////////////////////////////////////////////////////////////////
	
	//Constructor
	JMarkTextArea()
	{
		InitializeGUIComponent();
		InitializeLocalVariables();
		InitializeDocListener();
		InitializeMouseListener();
	}
	
	//Initialize about GUI component
	private void InitializeGUIComponent()
	{

		SetColors(DEFAULT_FONT_COLOR, DEFAULT_BG_COLOR, DEFAULT_SELECTED_FONT_COLOR, DEFAULT_SELECTION_COLOR, DEFAULT_MARK_COLOR, DEFAULT_SELECTED_MARK_COLOR);
		SetFont(new Font(DEFALUT_FONT, Font.PLAIN, 12), DEFAULT_FONT_SIZE);
		SetTabSize(DEFAULT_TAB_SIZE);

	}
	
	//Initialize local variables
	private void InitializeLocalVariables()
	{
		
		selectLineStart = -1;
		selectLineEnd = -1;
		
		markList = new ArrayList<Boolean>();
		markList.add(false);					//add first line
		
		lineBoolList = new ArrayList<Boolean>();
		lineBoolList.add(true);					//add first line
		
		blockIndexList = new ArrayList<Integer>();
		
	}
	
	//Initialize document listener
	private void InitializeDocListener()
	{
		super.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				DocListener_InsertUpdate(e);
			}
			public void changedUpdate(DocumentEvent e)	//I don't know
			{
				System.out.println("CHANGE");
			}
			public void removeUpdate(DocumentEvent e)
			{
				DocListener_RemoveUpdate(e);
			}
		});
	}
	
	//Initialize mouse listener
	private void InitializeMouseListener()
	{
		super.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				System.out.println("x : " + e.getX() + " y : " + e.getY());
				MouseListener_MousePressed(e);
			}
		});
	}
	
	//listener when mouse ls clicked
	private void MouseListener_MousePressed(MouseEvent e)
	{
		int t_selectedLine, i;
		
		if(super.isEnabled())
			return;
		
		t_selectedLine = e.getY() / lineHeight;

		if(markList.size() > t_selectedLine)
		{
			if(markList.get(t_selectedLine))
			{
				selectLineStart = t_selectedLine;
				selectLineEnd = t_selectedLine;
				
				for(i = t_selectedLine; i >= 0; i--)
				{
					if(markList.get(i))
						selectLineStart = i;
					else
						break;
				}
				
				for(i = t_selectedLine; i < markList.size(); i++)
				{
					if(markList.get(i))
						selectLineEnd = i;
					else
						break;
				}
				
				System.out.println("S : " + selectLineStart + " E : " + selectLineEnd);
				
				super.repaint();
				
			}
		}
	}
	
	//listener when something is inserted in document
	private void DocListener_InsertUpdate(DocumentEvent e)
	{
		
		int t_line, t_offset, t_length, i;
		
		try
		{
			t_offset = e.getOffset();
			t_length = e.getLength();
			t_line = super.getLineOfOffset(t_offset);
			
			//if this line is fake line, set this line to real line
			if(CheckLineBool(t_line) == false)
				lineBoolList.set(t_line, true);
			
			//check all inserted chracters
			for(i = 0; i < t_length; i++)
			{
				//if common chracter is Enter
				if(e.getDocument().getText(t_offset + i, 1).compareTo("\n") == 0)
				{
					//if current position is the first of the document
					//or the first of the line,
					//add current line index
					if(t_offset == 0 || e.getDocument().getText(t_offset + i - 1, 1).compareTo("\n") == 0)
					{
						lineBoolList.add(t_line, true);
						markList.add(t_line, false);
					}
					else
					{
						lineBoolList.add(t_line + 1, true);
						markList.add(t_line + 1, false);
					}
					
					t_line++;
				}
			}
			/////////////////////////////////////////////////////////////////repaint가 필요할지도
		}
		catch(BadLocationException ex)
		{
			System.out.println("ERROR : BadLocationException : JMarkTextArea : DocListener_InsertUpdate");
		}
	}
	
	//listener when something is removed from document
	private void DocListener_RemoveUpdate(DocumentEvent e)
	{
		
		int t_line, t_offset;
		/*
		Position startPosition;
		Position endPosition;
		int startOffset;
		int endOffset;
		int startLine;
		int endLine;

		Document removeDocument = e.getDocument();
		
		startPosition = removeDocument.getStartPosition();
		endPosition = removeDocument.getEndPosition();
		
		startOffset = startPosition.getOffset();
		endOffset = endPosition.getOffset();
		System.out.println(startOffset);
		System.out.println(endOffset);
		try{
			startLine = super.getLineOfOffset(startOffset);
			endLine = super.getLineOfOffset(endOffset);		//에러
			t_offset = e.getOffset();
			t_line = super.getLineOfOffset(t_offset);
			for(int i = t_line+1 ; i < t_line + endLine - 1 ; i++)
			{
				lineBoolList.remove(i);
				markList.remove(i);
			}
		}
		
		catch(BadLocationException ex)
		{
			System.out.println("ERROR : BadLocationException : JMarkTextArea : DocListener_RemoveUpdate");
		}
*/
		/*
		super.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent e) {
				e.getDot();
			}
		});*/
		/*
		try
		{
			t_offset = e.getOffset();
			t_line = super.getLineOfOffset(t_offset);
			
			//if this line is fake line, set this line to real line
			if(CheckLineBool(t_line) == false)
				lineBoolList.set(t_line, true);				

		}
		catch(BadLocationException ex)
		{
			System.out.println("ERROR : BadLocationException : JMarkTextArea : DocListener_RemoveUpdate");
		}*/
	}
	//////////////////////////////////////////////////////////////////
	
	//return if this line is fake line or real line
	private boolean CheckLineBool(int i_lineNum)
	{
		
		if(i_lineNum > lineBoolList.size())
		{
			System.out.println("ERROR : JMarkTextArea : CheckLineBool");
			return true;
		}
		
		return lineBoolList.get(i_lineNum);
		
	}
	
	//Paint All marklist
	private void PaintMark(Graphics g)
	{
		
		int i, len, width;
		
		//if markList is empty, return
		if(markList.isEmpty())
			return;
		
		//initialize local variables
		len = markList.size();
		width = super.getWidth();
		
		//set paint color
		g.setColor(markColor);
		
		//paint all
		for(i = 0; i < len; i++)
		{
			if(i == selectLineStart)
				g.setColor(selectedMarkColor);
			
			if(markList.get(i) == true)
				g.fillRect(0, i * lineHeight, width, lineHeight);
			
			if(i == selectLineEnd)
				g.setColor(markColor);
		}
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//private method
	//set each mark height
	private void SetLineHeight()
	{
		lineHeight = super.getFontMetrics(super.getFont()).getHeight();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//public method
	//get each mark height
	public int GetLineHeight()
	{
		return lineHeight;
	}
	
	//////////////////////////////////////////////////////////////////
	
	//set all colors
	public void SetColors(Color i_fontColor, Color i_backColor, Color i_selectFontColor, Color i_selectionColor, Color i_markColor, Color i_selectedMarkColor)
	{
		
		if(i_fontColor != null)
			super.setForeground(i_fontColor);
			
		if(i_backColor != null)
			super.setBackground(i_backColor);
		
		if(i_selectFontColor != null)
			super.setSelectedTextColor(i_selectFontColor);
		
		if(i_selectionColor != null)
			super.setSelectionColor(i_selectionColor);
		
		if(i_markColor != null)
			SetMarkColor(i_markColor);
		
		if(i_selectedMarkColor != null)
			SetSelectedMarkColor(i_selectedMarkColor);
		
	}
	
	//set mark color
	public void SetMarkColor(Color i_color)
	{
		int t_red, t_green, t_blue;
		
		//if input color is not translucent, set default alpha to 100
		if(i_color.getAlpha() == 0)
		{
			t_red = i_color.getRed();
			t_green = i_color.getGreen();
			t_blue = i_color.getBlue();
			markColor = new Color(t_red, t_green, t_blue, 100);
		}
		else
			markColor = i_color;
	}
	
	//set selected mark color
	public void SetSelectedMarkColor(Color i_color)
	{
		int t_red, t_green, t_blue;
		
		//if input color is not translucent, set default alpha to 100
		if(i_color.getAlpha() == 0)
		{
			t_red = i_color.getRed();
			t_green = i_color.getGreen();
			t_blue = i_color.getBlue();
			selectedMarkColor = new Color(t_red, t_green, t_blue, 100);
		}
		else
			selectedMarkColor = i_color;
	}
	
	//get mark color
	public Color GetMarkColor()
	{
		return markColor;
	}
	
	//get mark list
	public ArrayList<Boolean> GetMarkList()
	{
		return markList;
	}
	
	public int GetMergeBlockIndex()
	{
		
		int i;
		
		
		if(selectLineStart == -1)
		{
			System.out.println("Select block please.");
			return -1;
		}
		
		for(i = 0; i < blockIndexList.size(); i++)
		{
			if(blockIndexList.get(i) == selectLineStart)
			{
				return i;
			}
			
		}
		
		return -1;
		
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	//set font file
	public void TakeFontFileAndSet(String i_FontFilePath)
	{

		try
		{
			//Get font file
			File installFontFile = new File(i_FontFilePath);
			
			SetFont(Font.createFont(Font.TRUETYPE_FONT, installFontFile), DEFAULT_FONT_SIZE);
			
			System.out.println("Font is taken successfully.");
		}catch(Exception e)
		{
			System.out.println("ERROR : Exception : JMarkTextArea : TakeFontFileAndSet");
		}
		
	}
	
	//set font and size
	public void SetFont(Font i_Font, float i_size)
	{
		super.setFont(i_Font);
		SetFontSize(i_size);
	}
	
	//set font size only
	public void SetFontSize(float i_size)
	{
		super.setFont(super.getFont().deriveFont(i_size));
		SetLineHeight();
	}
	
	//set tab size
	public void SetTabSize(int i_tabSize)
	{
		super.setTabSize(i_tabSize);
	}
	
	//////////////////////////////////////////////////////////////////
	
	//get all text
	public String GetText()
	{
		String documentText = "";
		
		try
		{
			documentText = super.getDocument().getText(0, super.getDocument().getLength());
		}
		catch(BadLocationException ex)
		{
			System.out.println("ERROR : BadLocationException : JMarkTextArea : GetText");
		}
		
		return documentText;
	}
	
	//get line bool list
	public ArrayList<Boolean> GetLineBoolList()
	{
		return lineBoolList;
	}
	
	//remove current document and set new document
	public void SetText(ArrayList<String> i_textList)
	{
		
		int i, len;
		
		len = i_textList.size();
		
		//initialize document and relate lists
		super.setText("");

		lineBoolList.clear();
		markList.clear();
		lineBoolList.add(true);
		markList.add(false);
		
		//append all text
		for(i = 0; i < len; i++)
		{
			System.out.println(i_textList.get(i));
			super.append(i_textList.get(i));
			super.append("\n");
		}

	}
	
	//////////////////////////////////////////////////////////////////
	
	//set true one elements to marklist
	public void AddMark(int i_lineNum)
	{
		
		markList.set(i_lineNum, true);
		
		super.repaint();
		
	}
	
	//set true list to marklist
	public void AddMarkList(ArrayList<Integer> i_blockStartList, ArrayList<Integer> i_blockSizeList)
	{
		
		int i, j, t_startIndex, t_blockSize;
		
		blockIndexList = i_blockStartList;
		
		for(i = 0; i < i_blockStartList.size(); i++)
		{
			t_startIndex = i_blockStartList.get(i);
			t_blockSize = i_blockSizeList.get(i);
			for(j = 0; j < t_blockSize; j++)
				AddMark(t_startIndex + j);
		}
		
		//reset selection
		ResetSelectedMark();
		
	}
	
	//set false one elements in marklist
	public void RemoveMark(int i_lineNum)
	{

		markList.set(i_lineNum, false);
		
		//repaint
		super.repaint();
		
	}
	
	//set false all elements in marklist
	public void RemoveAllMark()
	{
		
		int i, size;
		
		blockIndexList.clear();
		
		size = markList.size();
		
		for(i = 0; i < size; i++)
			RemoveMark(i);
		
		super.repaint();
		
	}
	
	//reset selected mark
	public void ResetSelectedMark()
	{
		
		selectLineStart = -1;
		selectLineEnd = -1;
		
	}

	//Override paintComponent
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		PaintMark(g);

	}

}
