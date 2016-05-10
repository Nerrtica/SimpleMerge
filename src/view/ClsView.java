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


//Shade Component like a colored glass CLASS
//Having JComponent's contents
class Shade extends JComponent
{
	private Color shadeColor;
	
	public Shade()
	{
		//Can be translucent
		super.setOpaque(true);
		shadeColor = new Color(255, 255, 0, 100);
	}
	public Shade(Color i_color)
	{
		//Can be translucent
		super.setOpaque(true);
		
		SetColor(i_color);
	}
	
	public void SetColor(Color i_color)
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
	
	public Color GetColor()
	{
		return shadeColor;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(shadeColor);
		g.fillRect(0, 0, super.getWidth(), super.getHeight());
	}
}

public class ClsView
{
private JFrame viewForm;
	
	private JPanel p1, p2;
	private JPanel p3, p4;
	private JPanel p5, p6;
	
	private JButton b1, b2;
	private JButton b3, b4, b5, b6;
	
	private JTextArea t1, t2;
	
	private JSplitPane sp1;
	
	private JScrollPane s1, s2;
	
	private Shade sh1, sh2;
	
	private float spRatio;
	private int internalWidth;
	private int internalHeight;
	
	public static void main(String args[])
	{

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClsView window = new ClsView();
					window.viewForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ClsView()
	{
		InitView();
	}
	
	private void InitView()
	{
		
		viewForm = new JFrame("Mergery");
		viewForm.setSize(800, 600);
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
		//////////////////////////////////////////////////////////////////
		internalWidth = viewForm.getWidth() - 20;
		internalHeight = viewForm.getHeight() - 50;
		
		spRatio = 0.5f;
		//////////////////////////////////////////////////////////////////
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		viewForm.getContentPane().add(p1, BorderLayout.NORTH);
		
		b1 = new JButton("B1");
		p1.add(b1);
		
		b2 = new JButton("B2");
		p1.add(b2);
		//////////////////////////////////////////////////////////////////
		sp1 = new JSplitPane();
		sp1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		sp1.setDividerLocation((int)(internalWidth * spRatio));
		sp1.setDividerSize(3);
		viewForm.getContentPane().add(sp1, BorderLayout.CENTER);
		sp1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent pce) {

			}
		});
		BasicSplitPaneUI spui = (BasicSplitPaneUI)sp1.getUI();
		if(spui instanceof BasicSplitPaneUI)
		{
			((BasicSplitPaneUI)spui).getDivider().addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					spRatio = (float)sp1.getDividerLocation() / (float)(viewForm.getWidth() - 20);
					System.out.println(spRatio);
				}
			});
		}
		//////////////////////////////////////////////////////////////////
		p3 = new JPanel();
		p3.setLayout(new BorderLayout(0,0));
		sp1.setLeftComponent(p3);
		
		p5 = new JPanel();
		p5.setLayout(new FlowLayout());
		p3.add(p5, BorderLayout.NORTH);
		
		try
		{
			b3 = new JButton(new ImageIcon(ImageIO.read(new File("Images/Save01.png"))));
			b3.setMargin(new Insets(0, 0, 0, 0));
		}catch(IOException ex){}
		p5.add(b3);
		
		try
		{
			b4 = new JButton(new ImageIcon(ImageIO.read(new File("Images/Load01.png"))));
			b4.setMargin(new Insets(0, 0, 0, 0));
		}catch(IOException ex){}
		p5.add(b4);
		
		t1 = new JTextArea();
		t1.setFont(new Font("Bitstream Vera Sans Mono", Font.PLAIN, 12));
		t1.setTabSize(4);
		t1.setBackground(Color.WHITE);
		t1.setSelectedTextColor(Color.WHITE);
		t1.setSelectionColor(Color.BLACK);
		t1.setForeground(Color.BLACK);
		t1.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e)
			{
			}
			public void keyReleased(KeyEvent e)
			{
				t1.repaint();
			}
			public void keyPressed(KeyEvent e)
			{
				t1.repaint();
			}
		});
		t1.addMouseMotionListener(new MouseAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				t1.repaint();
				sh1.revalidate();
			}
		});
		t1.addMouseWheelListener(new MouseAdapter()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if(e.getWheelRotation() < 0)
				{
					WheelScroll(false);
				}
				else
				{
					WheelScroll(true);
				}
				
			}
		});
		//t1.getCaret().setBlinkRate(0);
		t1.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent e)
			{
				t1.repaint();

				System.out.println("S");
			}
		});
		
		
		t1.getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				System.out.println("CC");
			}
			
			public void changedUpdate(DocumentEvent e)
			{
				
			}
			public void removeUpdate(DocumentEvent e)
			{
				System.out.println("CCC");
			}
		});
		
		
		s1 = new JScrollPane(t1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		s1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				s2.getVerticalScrollBar().setValue(s1.getVerticalScrollBar().getValue());
			}
		});
		s1.getVerticalScrollBar().addMouseMotionListener(new MouseAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				//s2.getVerticalScrollBar().setValue(s1.getVerticalScrollBar().getValue());
				
			}
		});
		s1.getVerticalScrollBar().addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				//s2.getVerticalScrollBar().setValue(s1.getVerticalScrollBar().getValue());
			}
		});
		p3.add(s1, BorderLayout.CENTER);
		//////////////////////////////////////////////////////////////////
		p4 = new JPanel();
		p4.setLayout(new BorderLayout(0,0));
		sp1.setRightComponent(p4);
		
		p6 = new JPanel();
		p6.setLayout(new FlowLayout());
		p4.add(p6, BorderLayout.NORTH);
		
		try
		{
			b5 = new JButton(new ImageIcon(ImageIO.read(new File("Images/Save01.png"))));
			b5.setMargin(new Insets(0, 0, 0, 0));
		}catch(IOException ex){}
		p6.add(b5);
		
		try
		{
			b6 = new JButton(new ImageIcon(ImageIO.read(new File("Images/Load01.png"))));
			b6.setMargin(new Insets(0, 0, 0, 0));
		}catch(IOException ex){}
		p6.add(b6);
		
		t2 = new JTextArea();
		t2.setFont(new Font("Bitstream Vera Sans Mono", Font.PLAIN, 12));
		t2.setTabSize(4);
		t2.setBackground(Color.DARK_GRAY);
		t2.setSelectedTextColor(Color.BLACK);
		t2.setSelectionColor(Color.WHITE);
		t2.setForeground(Color.WHITE);
		t2.addMouseWheelListener(new MouseAdapter()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if(e.getWheelRotation() < 0)
				{
					WheelScroll(false);
				}
				else
				{
					WheelScroll(true);
				}
			}
		});
		
		s2 = new JScrollPane(t2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		s2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				s1.getVerticalScrollBar().setValue(s2.getVerticalScrollBar().getValue());
			}
		});
		s2.getVerticalScrollBar().addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				//s1.getVerticalScrollBar().setValue(s2.getVerticalScrollBar().getValue());
			}
		});
		p4.add(s2, BorderLayout.CENTER);
		
		sh1 = new Shade(new Color(255, 255, 0, 100));
		sh1.setBounds(0, 0, 200, 100);
		t1.add(sh1);

	}
	
	private void WindowResized()
	{
		
		int baseWinSize;
		float tempRatio;
		
		internalWidth = viewForm.getWidth() - 20;
		internalHeight = viewForm.getHeight() - 50;
		
		sp1.setDividerLocation((int)(internalWidth * spRatio));
		
		sh1.setBounds(0, 0, internalWidth / 2, 180); //한줄에 15픽셀인듯
		
	}
	
	private void WheelScroll(boolean upDown)
	{
		if(upDown == false)
		{
			s1.repaint();
			s2.repaint();
			s1.getVerticalScrollBar().setValue(s1.getVerticalScrollBar().getValue() - 54);
			s2.getVerticalScrollBar().setValue(s2.getVerticalScrollBar().getValue() - 54);
		}
		else
		{
			s1.repaint();
			s2.repaint();
			s1.getVerticalScrollBar().setValue(s1.getVerticalScrollBar().getValue() + 54);
			s2.getVerticalScrollBar().setValue(s2.getVerticalScrollBar().getValue() + 54);
		}
	}
	
}
