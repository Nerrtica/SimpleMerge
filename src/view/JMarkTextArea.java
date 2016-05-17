package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import model.DiffBlock;

public class JMarkTextArea extends JTextArea
{
	//////////////////////////////////////////////////////////////////
	//CONSTANTS
	//font
	public final String 	DEFALUT_FONT = "Consolas";						//default font
	public final float 		DEFAULT_FONT_SIZE = 12.0f;						//default font size
	public final int 		DEFAULT_TAB_SIZE = 4;							//default tab size
	//editor colors
	public final Color 		DEFAULT_FONT_COLOR = Color.BLACK;				//default font color
	public final Color 		DEFAULT_BG_COLOR = Color.WHITE;					//default background color
	public final Color 		DEFAULT_SELECTED_FONT_COLOR = Color.YELLOW;		//default selected font color (when blocked)
	public final Color 		DEFAULT_SELECTION_COLOR = Color.BLACK;			//default selection color (when blocked)
	//mark color
	public final Color 		DEFAULT_MARK_COLOR = new Color(255, 0, 0, 100);	//default mark color
	
	//////////////////////////////////////////////////////////////////
	//private variables
	private Color markColor;					//mark color value
	private int lineHeight;						//each line's height
	
	private ArrayList<Boolean> markList;		//if true, mark that line
	private ArrayList<Boolean> lineBoolList;	//if false, that line won't be save (fake line)
	
	//////////////////////////////////////////////////////////////////
	
	//Constructor
	JMarkTextArea()
	{
		InitializeGUIComponent();
		InitializeLocalVariables();
		InitializeDocListener();
	}
	
	//Initialize about GUI component
	private void InitializeGUIComponent()
	{

		SetColors(DEFAULT_FONT_COLOR, DEFAULT_BG_COLOR, DEFAULT_SELECTED_FONT_COLOR, DEFAULT_SELECTION_COLOR, DEFAULT_MARK_COLOR);
		SetFont(new Font(DEFALUT_FONT, Font.PLAIN, 12), DEFAULT_FONT_SIZE);
		SetTabSize(DEFAULT_TAB_SIZE);

	}
	
	//Initialize local variables
	private void InitializeLocalVariables()
	{
		
		markList = new ArrayList<Boolean>();
		markList.add(false);					//add first line
		
		lineBoolList = new ArrayList<Boolean>();
		lineBoolList.add(true);					//add first line
		
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
			public void changedUpdate(DocumentEvent e)
			{
				System.out.println("CHANGE");
			}
			public void removeUpdate(DocumentEvent e)
			{
				DocListener_RemoveUpdate(e);
			}
		});
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
		}
	}
	
	//////////////////////////////////////////////////////////////////
	/*
	private int GetCurrentLine(int i_offset)
	{
		
		int curLine, len, i;
		
		curLine = 0;
		len = super.getDocument().getLength();
		
		if(i_offset > len)
			return -1;
		
		try
		{
			for(i = 0; i < i_offset; i++)
			{
				if(super.getDocument().getText(i, 1) == "\n")
					curLine++;
			}
		}
		catch(BadLocationException ex)
		{
			System.out.println("ERROR : BadLocationException : JMarkTextArea : GetCurrentLine");
		}
		
		return curLine;
		
	}
	*/
	
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
			if(markList.get(i) == true)
				g.fillRect(0, i * lineHeight, width, lineHeight);
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
	public void SetColors(Color i_fontColor, Color i_backColor, Color i_selectFontColor, Color i_selectionColor, Color i_markColor)
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
	public void AddMarkList(ArrayList<DiffBlock> i_list)
	{
		
		int i, j, t_startIndex, t_blockSize;
		
		for(i = 0; i < i_list.size(); i++)
		{
			t_startIndex = i_list.get(i).getStartIndex();
			t_blockSize = i_list.get(i).getLineNumber();
			for(j = 0; j < t_blockSize; j++)
				AddMark(t_startIndex + j);
		}
		
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
		
		size = markList.size();
		
		for(i = 0; i < size; i++)
			RemoveMark(i);
		
		super.repaint();
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	
	
	//Override paintComponent
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		PaintMark(g);

	}
	
	//////////////////////////////////////////////////////////////////

	
}
