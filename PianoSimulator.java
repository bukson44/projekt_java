package projekt;

import java.awt.BorderLayout;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PianoSimulator extends JFrame implements ActionListener {
    private final int SIZE_X = 1600;
    private final int SIZE_Y = 1200;
    static final int SLIDER_MIN = 0; // fixme static?
    static final int SLIDER_MAX = 100;
    static final int SLIDER_INIT = 100;

    PlotChartPanel plotChartPanel;
	PianoPanel semitonesKeyboard;
	PianoPanel tonesKeyboard;
	JPanel plotPanel;
	JPanel keyboardPanel;

	private String[] octaves = { "Subkontra", "Kontra", "Wielka", "Mała",
			"Razkreślna", "Dwukreślna", "Trzykreślna", "Czterokreślna"};

	private String chosenOctave = octaves[4]; // domyśłlnie razkreślna
	private String chosenSemitone = "A";
	private double chosenLevel = SLIDER_INIT;
	private String chosenSemitoneForInterval = "C";
	private String chosenInterval = "pryma";
	private String chosenSemitoneForChord = "C";
	private String chosenChord = "MAJOR";

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
	    simplePanel.add(slider);

		ChangeListener sliderListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				JSlider slider = (JSlider)event.getSource();
				chosenLevel = (double)slider.getValue();
				System.out.println(chosenLevel);
				play();

			}
		};

		slider.addChangeListener(sliderListener);
	    

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
	    
	    // PANEL INTERWALOW
	    JPanel intervalPanel = new JPanel(new GridLayout(6,1));

		// radio interwałów
	    JRadioButton intervalRadioButton = new JRadioButton();
	    intervalRadioButton.setLabel("INTERWALY");
	    intervalRadioButton.setFont(new Font("Courier", Font.BOLD,20));
	    intervalPanel.add(intervalRadioButton);
	    
	    JLabel startingNoteLabel = new JLabel("Dzwiek poczatkowy ");
	    //startingNoteLabel.setFont(new Font("Courier", Font.BOLD,10));
	    intervalPanel.add(startingNoteLabel);

	    String[] tone_names = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B","H"};

		// combo wybierania dźwięku początkowego (dolnego) dla interwału
		JComboBox soundIntervalComboBox = new JComboBox(tone_names);
		intervalPanel.add(soundIntervalComboBox);

		ActionListener soundComboListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource(); // this is needeed for retrieving value of combo box
				chosenSemitoneForInterval = (String)cb.getSelectedItem();
				// because it should be memoried independently of semitone for chords
				chosenSemitone = chosenSemitoneForInterval;
				System.out.println(chosenSemitone);
				play();
			}
		};

		soundIntervalComboBox.addActionListener(soundComboListener);

	    JLabel intervalLabel = new JLabel("Interwał");
	    intervalPanel.add(intervalLabel);

	    String[] interval_names = {"pryma", "sekunda mała", "sekunda wielka", "tercja mała", "tercja wielka",
	    		"kwarta czysta", "tryton", "kwinta czysta", "seksta mała", "seksta wielka", "septyma mała", 
	    		"septyma wielka", "oktawa"};
		
		JComboBox intervalsComboBox = new JComboBox(interval_names);

		intervalPanel.add(intervalsComboBox);

		ActionListener intervalListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource();
				chosenInterval = (String)cb.getSelectedItem();
				play();
				System.out.println(chosenInterval);
			}
		};

		intervalsComboBox.addActionListener(intervalListener);

//	    JLabel emptyLabel = new JLabel("");
//	    intervalPanel.add(emptyLabel);
	    
	    rightPanel.add(intervalPanel);
	    
	    //PANEL AKORDOW
	    JPanel chordPanel = new JPanel(new GridLayout(8,1));
	    
	    JRadioButton chordRadioButton = new JRadioButton();
//	    ActionListener chordListener = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent arg0)
//			{
//				plotChartPanel.option=3;
//			}
//		};
//		chordRadioButton.addActionListener(chordListener);
	    chordRadioButton.setLabel("AKORDY");
	    chordRadioButton.setFont(new Font("Courier", Font.BOLD,20));
	    chordPanel.add(chordRadioButton);
	    
	    JLabel scaleLabel = new JLabel("Skala ");
	    chordPanel.add(scaleLabel);

		JComboBox cb3 = new JComboBox(tone_names);
		chordPanel.add(cb3);

		ActionListener soundForChordComboListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource(); // this is needeed for retrieving value of combo box
				chosenSemitoneForChord = (String)cb.getSelectedItem();
				// because it should be memoried independently of semitone for chords
				chosenSemitone = chosenSemitoneForChord;
				System.out.println(chosenSemitone);
				play();
			}
		};

		cb3.addActionListener(soundForChordComboListener);


	    
	    JLabel keyLabel = new JLabel("Tonacja ");
	    chordPanel.add(keyLabel);
	    
	    String[] key = {"MAJOR", "MINOR"};
	    JComboBox cb4 = new JComboBox(key);
		chordPanel.add(cb4);

		ActionListener chordListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource(); // this is needeed for retrieving value of combo box
				chosenChord = (String)cb.getSelectedItem();
				System.out.println(chosenChord);
				play();
			}
		};

		cb4.addActionListener(chordListener);

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
	    sicGroup.add(intervalRadioButton);
	    sicGroup.add(chordRadioButton);
	     
	    
	    //TSD = tonika, subdominanta, dominanta
	    ButtonGroup TSDGroup = new ButtonGroup();
	    TSDGroup.add(TButton);
	    TSDGroup.add(SButton);
	    TSDGroup.add(DButton);
	
	    RadioListener radioListener = new RadioListener();
	    simpleButton.addActionListener(radioListener);
	    intervalRadioButton.addActionListener(radioListener);
	    chordRadioButton.addActionListener(radioListener);
	    
	    this.add(rightPanel, layout);
	}

	void createLeftPanel(String layout) {
    	
    	//WSZYSTKO POZOSTAŁE
		JPanel leftPanel = new JPanel(new GridLayout(2, 1));
		leftPanel.setSize(900,800);
		plotPanel = new JPanel(new GridLayout(1, 1));
		plotChartPanel = new PlotChartPanel(plotPanel);
		keyboardPanel = new JPanel(new GridLayout(2,1));

		semitonesKeyboard = new PianoPanel("semitones", this);
		tonesKeyboard = new PianoPanel("tones", this);

		keyboardPanel.setBorder(border);
		keyboardPanel.add(semitonesKeyboard);
		keyboardPanel.add(tonesKeyboard);

		this.getContentPane().add(leftPanel, java.awt.BorderLayout.CENTER);
		leftPanel.add(plotPanel);
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
				play();
			}
		};

       for(int i =0; i<octaves.length; i++) {
    	   chooseOct[i] = new JMenuItem(octaves[i]);
    	   oktawy.add(chooseOct[i]);
		   chooseOct[i].addActionListener(octaveListener);
       }
       
        menu.add(oktawy);
        //menu.add(menuItem);

        this.setJMenuBar(menuBar);
    }


	public void play() {
		plotChartPanel.draw(chosenSemitone, chosenOctave, chosenLevel / 100.0, chosenInterval, chosenChord);
	}
	// this version is used only by the keyboard in PianoPanel
	public void play(String semitone) {
		chosenSemitone = semitone;
		plotChartPanel.draw(chosenSemitone, chosenOctave, chosenLevel / 100.0, chosenInterval, chosenChord);
	}

	// maybe not needed
	public void play(String semitone, String octave, double level) {
		plotChartPanel.draw(semitone, octave, level / 100.0, chosenInterval, chosenChord);
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
