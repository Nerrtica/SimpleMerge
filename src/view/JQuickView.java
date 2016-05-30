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
	public final Color DEFAULT_FAKE_COLOR = new Color(255, 0, 0);
	public final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	public final Color DEFAULT_RECT_COLOR = new Color(0, 0, 0, 100);
	
	private JScrollTextArea linkedTextArea;
	
	private int internalMargin;
	
	private Color markColor;
	private Color fakeColor;
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
		fakeColor = DEFAULT_FAKE_COLOR;
		borderColor = DEFAULT_BORDER_COLOR;
		rectColor = DEFAULT_RECT_COLOR;
		
	}

	//////////////////////////////////////////////////////////////////
	
	//Paint All mark
	private void PaintQuickView(Graphics g)
	{

		int i, len;
		int width, height;
		float lineUnit;
		int intLineUnit;
		int screenTop, scrollMax, screenHeight;
		int rectTop, rectHeight;
		
		//error check
		if(linkedTextArea.GetTextPad().GetMarkList().size() !=  linkedTextArea.GetTextPad().GetLineBoolList().size())
		{
			System.out.println("MarkListSize is not same as boolListSize");
			return;
		}
		
		//get this component's size and subtract internal margin value
		width = super.getWidth() - internalMargin * 2;
		height = super.getHeight() - internalMargin * 2;
		len = linkedTextArea.GetTextPad().GetMarkList().size();
		lineUnit = (float)((float)height / (float)len);
		intLineUnit = (int)lineUnit;
		
		if(intLineUnit == 0)
			intLineUnit = 1;
		
		//draw mark
		if(!linkedTextArea.GetTextPad().GetMarkList().isEmpty())
		{
			
			g.setColor(markColor);
			for(i = 0; i < len; i++)
			{
				if(linkedTextArea.GetTextPad().GetMarkList().get(i) == true)
					g.fillRect(internalMargin, internalMargin + (int)Math.round(i * lineUnit), width, intLineUnit);
			}
			
		}
		
		//draw fake line mark
		if(!linkedTextArea.GetTextPad().GetLineBoolList().isEmpty())
		{
			
			g.setColor(fakeColor);
			for(i = 0; i < len; i++)
			{
				if(linkedTextArea.GetTextPad().GetLineBoolList().get(i) == false)
					g.fillRect(internalMargin, internalMargin + (int)Math.round(i * lineUnit), 5, intLineUnit);
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
