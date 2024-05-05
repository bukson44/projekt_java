package projekt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * @author Jakub Bukowski
 */
public class PianoPanel extends JPanel{//Jakub Bukowski
	
	JButton[] tone;
	String[] tone_name;
	JPanel[] panel;
	int button_x,button_y;
	String tone_type;
	int panel_width=300;
	double[] frequencies;
	int octave = 4;
	
	PianoPanel(String tone_type){
		if(tone_type=="tones")
		{
			button_x = 50;
			button_y = 170;
			String[] tone_name = new String[7];
			tone_name[0]="C";
			tone_name[1]="D";
			tone_name[2]="E";
			tone_name[3]="F";
			tone_name[4]="G";
			tone_name[5]="A";
			tone_name[6]="H";
			
			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width,button_y);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			frequencies = new double[tone_name.length];
			frequencies[0] = Math.pow(2,((octave-1)*12+4-49)/12)*440;
			frequencies[1] = Math.pow(2,((octave-1)*12+6-49)/12)*440;
			frequencies[2] = Math.pow(2,((octave-1)*12+8-49)/12)*440;
			frequencies[3] = Math.pow(2,((octave-1)*12+9-49)/12)*440;
			frequencies[4] = Math.pow(2,((octave-1)*12+11-49)/12)*440;
			frequencies[5] = Math.pow(2,((octave-1)*12+13-49)/12)*440;
			frequencies[6] = Math.pow(2,((octave-1)*12+15-49)/12)*440;
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
			ActionListener toneListener1 = new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					
				}	
			};
			tone[0].addActionListener(toneListener1);
		}
		else
		{	
			button_x = 50;
			button_y = 170;
			String[] tone_name = new String[5];
			tone_name[0]="C#";
			tone_name[1]="D#";
			tone_name[2]="F#";
			tone_name[3]="G#";
			tone_name[4]="B";
			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width,button_y);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			frequencies = new double[tone_name.length];
			frequencies[0] = Math.pow(2,((octave-1)*12+5-49)/12)*440;
			frequencies[1] = Math.pow(2,((octave-1)*12+7-49)/12)*440;
			frequencies[2] = Math.pow(2,((octave-1)*12+10-49)/12)*440;
			frequencies[3] = Math.pow(2,((octave-1)*12+12-49)/12)*440;
			frequencies[4] = Math.pow(2,((octave-1)*12+14-49)/12)*440;
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
	void setFreq(String freqType){
		if(freqType=="tones freq")
		{
			frequencies[0] = Math.pow(2,((octave-1)*12+4-49)/12)*440;
			frequencies[1] = Math.pow(2,((octave-1)*12+6-49)/12)*440;
			frequencies[2] = Math.pow(2,((octave-1)*12+8-49)/12)*440;
			frequencies[3] = Math.pow(2,((octave-1)*12+9-49)/12)*440;
			frequencies[4] = Math.pow(2,((octave-1)*12+11-49)/12)*440;
			frequencies[5] = Math.pow(2,((octave-1)*12+13-49)/12)*440;
			frequencies[6] = Math.pow(2,((octave-1)*12+15-49)/12)*440;
		}
		else
		{
			frequencies[0] = Math.pow(2,((octave-1)*12+5-49)/12)*440;
			frequencies[1] = Math.pow(2,((octave-1)*12+7-49)/12)*440;
			frequencies[2] = Math.pow(2,((octave-1)*12+10-49)/12)*440;
			frequencies[3] = Math.pow(2,((octave-1)*12+12-49)/12)*440;
			frequencies[4] = Math.pow(2,((octave-1)*12+14-49)/12)*440;
		}
	}
}

