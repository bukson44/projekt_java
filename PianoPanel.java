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
	JPanel[] panel;
	int panel_width=300;

	private final int buttonWidth = 60;
	private final int buttonHeight = 170;

	PianoSimulator simulator;
	
	PianoPanel(String tone_type, PianoSimulator simulator){
		this.simulator = simulator;

		ActionListener toneListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				simulator.play(event.getActionCommand());
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
}

