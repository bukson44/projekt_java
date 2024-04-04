package projekt2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PianoPanel extends JPanel{
	
	JButton[] tone;
	String[] tone_name;
	JPanel[] panel;
	int button_x,button_y;
	String tone_type;
	int panel_width=300;
	
	PianoPanel(String tone_type){
		if(tone_type=="tones")
		{
			
			button_x = 50;
			button_y = 170;
			String[] tone_name= {"C","D","E","F","G","A","H"};
			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width,button_y);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			for(int i=0;i<tone_name.length;i++)
			{
				if(i==0) this.add(Box.createRigidArea(new Dimension(125,button_y)));
				//if(i==2) this.add(Box.createRigidArea(new Dimension(70,button_y))); 
				tone[i]=new JButton(tone_name[i]);
				tone[i].setPreferredSize(new Dimension(button_x,button_y));
				tone[i].setForeground(Color.black);
				tone[i].setBackground(Color.white);
				panel[i]=new JPanel();
				panel[i].setSize(button_x,button_y);
				panel[i].add(tone[i]);
				this.add(panel[i]);
				if(i==6) this.add(Box.createRigidArea(new Dimension(130,button_y))); 
			}		
		}
		else
		{	
			
			button_x = 50;
			button_y = 170;
			String[] tone_name= {"C#","D#","F#","G#","B"};
			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width,button_y);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			for(int i=0;i<tone_name.length;i++)
			{
				if(i==0) this.add(Box.createRigidArea(new Dimension(150,button_y)));
				if(i==2) this.add(Box.createRigidArea(new Dimension(60,button_y))); 
				tone[i]=new JButton(tone_name[i]);
				tone[i].setPreferredSize(new Dimension(button_x,button_y));
				tone[i].setForeground(Color.white);
				tone[i].setBackground(Color.black);
				panel[i]=new JPanel();
				panel[i].setSize(button_x,button_y);
				panel[i].add(tone[i]);
				this.add(panel[i]);
				if(i==4) this.add(Box.createRigidArea(new Dimension(160,button_y))); 
			}		
		}	
	}
}
