package projekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.border.Border;

public class PianoSimulator extends JFrame implements ActionListener {
    private final int SIZE_X = 1600;
    private final int SIZE_Y = 1200;
    static final int SLIDER_MIN = 0; // fixme static?
    static final int SLIDER_MAX = 100;
    static final int SLIDER_INIT = 0;

    PlotChartPanel plotChartPanel;
	PianoPanel semitonesKeyboard;
	PianoPanel tonesKeyboard;
	JPanel keyboardPanel;

	private String[] octaves = { "Subkontra", "Kontra", "Wielka", "Mała",
			"Razkreślna", "Dwukreślna", "Trzykreślna", "Czterokreślna"};

	private String chosenOctave = octaves[4]; // domyśłlnie razkreślna
	private static int chosenSemitone = 0;
	// 0 to dźwięk A w danej oktawie
	// G# to -1, A# to 1 itd.
	// C to -9

    Border border = BorderFactory.createLoweredBevelBorder();

  //zmienic na poprawny sposób startowania programu  
    public static void main(String[] args) {
        PianoSimulator pianoSimulator = new PianoSimulator();
        pianoSimulator.setVisible(true);
    }
    
    public PianoSimulator() throws HeadlessException {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(SIZE_X,SIZE_Y);
        createRightPanel(BorderLayout.EAST);
        createLeftPanel(BorderLayout.CENTER);
        createMenu();

        GenerationMethod generationMethod = GenerationMethod.SIMPLE;
    }

	void createRightPanel(String layout) {
		/**
		 * @author Gabriela Danowska
		 */

		//CAŁY BOCZNY PANEL (PRAWY)
		JPanel rightPanel = new JPanel(new GridLayout(3, 1));
		rightPanel.setSize(500,400);
		
	    //PANEL POJEYNCZYCH DZWIEKOW I SLIDERA
	    JPanel simplePanel = new JPanel(new GridLayout(2,1));
	    
	    simplePanel.setSize(400, 400);
	
	    //slider
	    JSlider slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	    slider.setSize(400, 200);
	    rightPanel.setBorder(border);
	    slider.setMajorTickSpacing(20);
	    slider.setMinorTickSpacing(10);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    //slider.addChangeListener(new SliderChangeListener(slider));
	    simplePanel.add(slider);
	    

	    JRadioButton simpleButton = new JRadioButton();
	    ActionListener simpleListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				plotChartPanel.option=1;
			}	
		};
		simpleButton.addActionListener(simpleListener);
	    simpleButton.setLabel("POJEDYNCZE DZWIEKI         ");
	    simpleButton.setFont(new Font("Courier", Font.BOLD,20));
	    simplePanel.add(simpleButton);
	    simpleButton.setSelected(true);
	    rightPanel.add(simplePanel);
	    
	    //PANEL INTERWALOW
	    JPanel intervalPanel = new JPanel(new GridLayout(6,1));
	    
	    
	    JRadioButton intervalButton = new JRadioButton();
	    ActionListener intervalListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				plotChartPanel.option=2;
			}	
		};
		intervalButton.addActionListener(intervalListener);
	    intervalButton.setLabel("INTERWALY");
	    intervalButton.setFont(new Font("Courier", Font.BOLD,20));
	    intervalPanel.add(intervalButton);
	    
	    JLabel startingNoteLabel = new JLabel("Dzwiek poczatkowy ");
	    //startingNoteLabel.setFont(new Font("Courier", Font.BOLD,10));
	    intervalPanel.add(startingNoteLabel);
	    
	    String[] tone_name= {"C", "C#","D", "D#","E","F", "F#","G", "G#","A", "B","H"};
		
		JComboBox cb1 = new JComboBox(tone_name);
		intervalPanel.add(cb1);

	    //JComboBox box = new JComboBox();

	    JLabel intervalLabel = new JLabel("Interwal ");
	    intervalPanel.add(intervalLabel);

	    String[] interval_name= {"pryma", "sekunda mała", "sekunda wielka", "tercja mała", "tercja wielka",
	    		"kwarta czysta", "tryton", "kwinta czysta", "seksta mała", "seksta wielka", "septyma mała", 
	    		"septyma wielka", "oktawa"};
		
		JComboBox cb2 = new JComboBox(interval_name);
		intervalPanel.add(cb2);

	    JLabel emptyLabel = new JLabel("");
	    intervalPanel.add(emptyLabel);
	    
	    
	    rightPanel.add(intervalPanel);
	    
	    //PANEL AKORDOW
	    JPanel chordPanel = new JPanel(new GridLayout(8,1));
	    
	    JRadioButton chordButton = new JRadioButton();
	    ActionListener chordListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				plotChartPanel.option=3;
			}	
		};
		chordButton.addActionListener(chordListener);
	    chordButton.setLabel("AKORDY");
	    chordButton.setFont(new Font("Courier", Font.BOLD,20));
	    chordPanel.add(chordButton);
	    
	    JLabel scaleLabel = new JLabel("Skala ");
	    chordPanel.add(scaleLabel);

		JComboBox cb3 = new JComboBox(tone_name);
		chordPanel.add(cb3);
	    
	    JLabel keyLabel = new JLabel("Tonacja ");
	    chordPanel.add(keyLabel);
	    
	    String[] key = {"MINOR", "MAJOR"};
	    JComboBox cb4 = new JComboBox(key);
		chordPanel.add(cb4);

	    JLabel triadLabel = new JLabel("Funkcja (triada harmoniczna) ");
	    chordPanel.add(triadLabel);

	    //PANEL TRIADY
	    JPanel triadPanel = new JPanel(new GridLayout(3,1));
	    
	    JRadioButton TButton = new JRadioButton();
	    TButton.setLabel("TONIKA");
	    triadPanel.add(TButton);
	    
	    JRadioButton SButton = new JRadioButton();
	    SButton.setLabel("SUBDOMINANTA");
	    triadPanel.add(SButton);
	    
	    JRadioButton DButton = new JRadioButton();
	    DButton.setLabel("DOMINANTA");
	    triadPanel.add(DButton);
	    
	    chordPanel.add(triadPanel);
	    
	    rightPanel.add(chordPanel);
	    
	  //sic = simple, interval, chord
	    ButtonGroup sicGroup = new ButtonGroup();
	    sicGroup.add(simpleButton);
	    sicGroup.add(intervalButton);
	    sicGroup.add(chordButton);
	     
	    
	    //TSD = tonika, subdominanta, dominanta
	    ButtonGroup TSDGroup = new ButtonGroup();
	    TSDGroup.add(TButton);
	    TSDGroup.add(SButton);
	    TSDGroup.add(DButton);
	
	    RadioListener radioListener = new RadioListener();
	    simpleButton.addActionListener(radioListener);
	    intervalButton.addActionListener(radioListener);
	    chordButton.addActionListener(radioListener);
	    
	    this.add(rightPanel, layout);
	}

	void createLeftPanel(String layout) {
    	
    	//WSZYSTKO POZOSTAŁE
		JPanel leftPanel = new JPanel(new GridLayout(2, 1));
		leftPanel.setSize(900,800);
		plotChartPanel = new PlotChartPanel(leftPanel);
		keyboardPanel = new JPanel(new GridLayout(2,1));

		semitonesKeyboard = new PianoPanel("semitones", this);
		tonesKeyboard = new PianoPanel("tones", this);

		keyboardPanel.setBorder(border);
		keyboardPanel.add(semitonesKeyboard);
		keyboardPanel.add(tonesKeyboard);

		this.getContentPane().add(leftPanel, java.awt.BorderLayout.CENTER);
		leftPanel.add(keyboardPanel);
		this.add(leftPanel, layout);
    }
	
	void createMenu() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        menuItem = new JMenuItem("Wybierz język");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Kolor tła");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);
        JMenu oktawy = new JMenu("Wybierz oktawę");

		JMenuItem[] chooseOct = new JMenuItem[octaves.length];
		ActionListener octaveListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chosenOctave = e.getActionCommand();
				System.out.println(chosenOctave);
			}
		};

       for(int i =0; i<octaves.length; i++)
       {
    	   chooseOct[i] = new JMenuItem(octaves[i]);
    	   oktawy.add(chooseOct[i]);
		   chooseOct[i].addActionListener(octaveListener);
       }

//       ActionListener octListener1 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 1;
//				tonesKeyboard.octave = 1;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[0].addActionListener(octListener1);
//		ActionListener octListener2 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 2;
//				tonesKeyboard.octave = 2;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[1].addActionListener(octListener2);
//		ActionListener octListener3 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 3;
//				tonesKeyboard.octave = 3;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[2].addActionListener(octListener3);
//		ActionListener octListener4 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 4;
//				tonesKeyboard.octave = 4;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[3].addActionListener(octListener4);
//		ActionListener octListener5 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 5;
//				tonesKeyboard.octave = 5;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[4].addActionListener(octListener5);
//		ActionListener octListener6 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 6;
//				tonesKeyboard.octave = 6;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[5].addActionListener(octListener6);
//		ActionListener octListener7 = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				semitonesKeyboard.octave = 7;
//				tonesKeyboard.octave = 7;
//				semitonesKeyboard.setFreq("semitones freq");
//				tonesKeyboard.setFreq("tones freq");
//			}
//		};
//		chooseOct[6].addActionListener(octListener7);
       
       
        menu.add(oktawy);
        //menu.add(menuItem);

        this.setJMenuBar(menuBar);
    }


public class RadioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
//        switch(arg0.getActionCommand()) {
//            case "Regular": generationMethod = GenerationMethod.REGULAR; break;
//            case "Random": generationMethod = GenerationMethod.RANDOM; break;
//        }
        //revalidate();
        //repaint();
    }

}

enum GenerationMethod {
    SIMPLE, INTERVAL, CHORD
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}

	public String getChosenOctave() {
		return chosenOctave;
	}
}
