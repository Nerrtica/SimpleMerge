/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : JQuickView.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.18

package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class JQuickView extends JComponent
{

	public final int DEFAULT_MARGIN = 10;
	
	public final Color DEFAULT_MARK_COLOR = new Color(255, 100, 100);
	public final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	public final Color DEFAULT_RECT_COLOR = new Color(0, 0, 255, 50);
	
	private JScrollTextArea linkedTextArea;
	
	private int internalMargin;
	
	private Color markColor;
	private Color borderColor;
	private Color rectColor;
	
	JQuickView(JScrollTextArea i_linkedTextArea)
	{
		
		InitializeReference(i_linkedTextArea);
		InitializeLocalVariables();

	}
	
	private void InitializeReference(JScrollTextArea i_linkedTextArea)
	{
		
		linkedTextArea = i_linkedTextArea;
		
	}
	
	private void InitializeLocalVariables()
	{
		
		internalMargin = DEFAULT_MARGIN;
		
		markColor = DEFAULT_MARK_COLOR;
		borderColor = DEFAULT_BORDER_COLOR;
		rectColor = DEFAULT_RECT_COLOR;
		
	}

	//////////////////////////////////////////////////////////////////
	
	//Paint All mark
	private void PaintQuickView(Graphics g)
	{

		int i, len;
		int width, height;
		int lineUnit;
		int screenTop, scrollMax, screenHeight;
		int rectTop, rectHeight;
		
		//get this component's size and subtract internal margin value
		width = super.getWidth() - internalMargin * 2;
		height = super.getHeight() - internalMargin * 2;
		
		//draw mark
		if(!linkedTextArea.GetTextPad().GetMarkList().isEmpty())
		{
			
			len = linkedTextArea.GetTextPad().GetMarkList().size();
			lineUnit = (int)(height / len);
			
			g.setColor(markColor);
			for(i = 0; i < len; i++)
			{
				if(linkedTextArea.GetTextPad().GetMarkList().get(i) == true)
					g.fillRect(internalMargin, internalMargin + i * lineUnit, width, lineUnit);
			}
			
		}
		
		//draw border
		g.setColor(borderColor);
		g.drawRect(internalMargin, internalMargin, width, height);
		
		//get values about scroll
		screenTop = linkedTextArea.getVerticalScrollBar().getValue();
		scrollMax = linkedTextArea.getVerticalScrollBar().getMaximum();
		screenHeight = linkedTextArea.getVerticalScrollBar().getVisibleAmount();
		
		rectTop = internalMargin + (int)((height * screenTop) / scrollMax);
		rectHeight = (int)((screenHeight * height) / scrollMax);
		
		//draw screen rect
		g.setColor(rectColor);
		g.fillRect(internalMargin - 1, rectTop, width + 3, rectHeight);

	}
	
	//Override paintComponent
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		PaintQuickView(g);

	}
	
}
