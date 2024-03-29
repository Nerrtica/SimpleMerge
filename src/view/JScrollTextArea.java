/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Project name      : Simple Merge - SE Term Project 11 team
//File name         : JScrollTextArea.java
//Developer         : Do-Gun Park
//School            : Chung-Ang Univ.
//Student num       : 20123272
//Developing period : 2016.05.05 ~ 2016.05.18

package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import model.DiffBlock;

public class JScrollTextArea extends JScrollPane
{

	public final int 		WHEEL_SCROLL_AMOUNT = 54;						//scroll amount when mouse wheel
	
	private JMarkTextArea textPad;
	
	
	JScrollTextArea()
	{
		InitializeGUIComponent();
		InitializeLocalVariables();
	}
	
	private void InitializeGUIComponent()
	{
		textPad = new JMarkTextArea();
		super.setViewportView(textPad);
		super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	}
	
	private void InitializeLocalVariables()
	{

		
	}
	
	public JMarkTextArea GetTextPad()
	{
		return textPad;
	}
	
	public void SyncVertScrollVal(JScrollTextArea i_scrollTextArea)
	{
		i_scrollTextArea.getVerticalScrollBar().setValue(super.getVerticalScrollBar().getValue());
	}
	
}
