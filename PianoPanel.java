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
public class PianoPanel extends JPanel {//Jakub Bukowski
	
	JButton[] tone;
	String[] tone_name;
	JPanel[] panel;
	String tone_type;
	int panel_width=300;
	int octave = 4;

	private final int buttonWidth = 60;
	private final int buttonHeight = 170;

	PianoSimulator simulator;
	
	PianoPanel(String tone_type, PianoSimulator simulator){
		this.simulator = simulator;

		ActionListener toneListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(event.getActionCommand() + " " + simulator.getChosenOctave());
			}
		};

		if(tone_type.equals("tones"))
		{
			String[] tone_name = { "C", "D", "E", "F", "G", "A", "H"};
			
			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width, buttonHeight);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));



			for(int i=0; i<tone_name.length; i++) {
				if(i==0) this.add(Box.createRigidArea(new Dimension(125, buttonHeight)));
				//if(i==2) this.add(Box.createRigidArea(new Dimension(70,button_y))); 
				tone[i]=new JButton(tone_name[i]);
				tone[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
				tone[i].setForeground(Color.black);
				tone[i].setBackground(Color.white);
				panel[i]=new JPanel();
				panel[i].setSize(buttonWidth, buttonHeight);
				panel[i].add(tone[i]);
				this.add(panel[i]);

				tone[i].addActionListener(toneListener);

				if(i==6) this.add(Box.createRigidArea(new Dimension(130, buttonHeight)));
			}
		} else {
			String[] tone_name = { "C#", "D#", "F#", "G#", "B"};

			tone = new JButton[tone_name.length];
			panel = new JPanel[tone_name.length];
			this.setSize(panel_width, buttonHeight);
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

			for(int i=0;i<tone_name.length;i++) {
				if(i==0) this.add(Box.createRigidArea(new Dimension(150, buttonHeight)));
				if(i==2) this.add(Box.createRigidArea(new Dimension(60, buttonHeight)));
				tone[i]=new JButton(tone_name[i]);
				tone[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
				tone[i].setForeground(Color.white);
				tone[i].setBackground(Color.black);
				panel[i]=new JPanel();
				panel[i].setSize(buttonWidth, buttonHeight);
				panel[i].add(tone[i]);
				this.add(panel[i]);
				if(i==4) this.add(Box.createRigidArea(new Dimension(180, buttonHeight)));

				tone[i].addActionListener(toneListener);
			}		
		}	
	}

//	void setFreq(String freqType){
//		if(freqType=="tones freq")
//		{
//			frequencies[0] = Math.pow(2,((octave-1)*12+4-49)/12)*440;
//			frequencies[1] = Math.pow(2,((octave-1)*12+6-49)/12)*440;
//			frequencies[2] = Math.pow(2,((octave-1)*12+8-49)/12)*440;
//			frequencies[3] = Math.pow(2,((octave-1)*12+9-49)/12)*440;
//			frequencies[4] = Math.pow(2,((octave-1)*12+11-49)/12)*440;
//			frequencies[5] = Math.pow(2,((octave-1)*12+13-49)/12)*440;
//			frequencies[6] = Math.pow(2,((octave-1)*12+15-49)/12)*440;
//		}
//		else
//		{
//			frequencies[0] = Math.pow(2,((octave-1)*12+5-49)/12)*440;
//			frequencies[1] = Math.pow(2,((octave-1)*12+7-49)/12)*440;
//			frequencies[2] = Math.pow(2,((octave-1)*12+10-49)/12)*440;
//			frequencies[3] = Math.pow(2,((octave-1)*12+12-49)/12)*440;
//			frequencies[4] = Math.pow(2,((octave-1)*12+14-49)/12)*440;
//		}
//	}
}

