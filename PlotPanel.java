package projekt2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlotPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.gray);
		for(int i =1;i<=17;i++)
		{	
			if(i==9) {
				Graphics2D g2d = (Graphics2D) g;
			 	BasicStroke bs1 = new BasicStroke(2);
			 	g2d.setStroke(bs1);
			 	g.drawLine(1+38*i,0,38*i-4,10);
			 	g.drawLine(1+38*i,0,38*i+5,10);
			}
			if(i==10)
			{
				Graphics2D g2d = (Graphics2D) g;
			 	BasicStroke bs1 = new BasicStroke(1);
			 	g2d.setStroke(bs1);
			}
			g.drawLine(1+38*i, 0, 1+38*i, 369);
		}
	    for(int i=1;i<=12;i++)
	    {
	    	if(i==6) {
				Graphics2D g2d = (Graphics2D) g;
			 	BasicStroke bs1 = new BasicStroke(2);
			 	g2d.setStroke(bs1);
			 	g.drawLine(680,31*i,680-10,31*i-4);
			 	g.drawLine(680,31*i,680-10,31*i+4);
			}
			if(i==7)
			{
				Graphics2D g2d = (Graphics2D) g;
			 	BasicStroke bs1 = new BasicStroke(1);
			 	g2d.setStroke(bs1);
			}
	    	g.drawLine(0,31*i,680,31*i);
	    }	
	}
}
