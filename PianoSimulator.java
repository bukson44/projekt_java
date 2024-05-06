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
	private static final int SLIDER_MIN = 0; // fixme static?
	private static final int SLIDER_MAX = 100;
	private static final int SLIDER_INIT = 100;

    private PlotChartPanel plotChartPanel;
	private PianoPanel semitonesKeyboard;
	private PianoPanel tonesKeyboard;
	private JPanel plotPanel;
	private JPanel keyboardPanel;

	private String[] octaves = { "Subkontra", "Kontra", "Wielka", "Mała",
			"Razkreślna", "Dwukreślna", "Trzykreślna", "Czterokreślna"};

	private String[] tone_names = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B","H"};

	String[] interval_names = {"pryma", "sekunda mała", "sekunda wielka", "tercja mała", "tercja wielka",
			"kwarta czysta", "tryton", "kwinta czysta", "seksta mała", "seksta wielka", "septyma mała",
			"septyma wielka", "oktawa"};


	private String chosenOctave = octaves[4]; // domyślnie razkreślna
	private String chosenSemitone = "A";
	private double chosenLevel = SLIDER_INIT;
	private String chosenSemitoneForInterval = "C";
	private String chosenInterval = "pryma";
	private String chosenSemitoneForChord = "C";
	private String chosenChord = "MAJOR";

	private Border border = BorderFactory.createLoweredBevelBorder();

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

		// CAŁY BOCZNY PANEL (PRAWY)
		JPanel rightPanel = new JPanel(new GridLayout(3, 1));
	    rightPanel.setBorder(border);
		rightPanel.setSize(500,400);

	    // PANEL POJEDYNCZYCH DZWIEKOW I SLIDERA
	    JPanel simplePanel = new JPanel(new GridLayout(2,1));
	    simplePanel.setSize(400, 400);

	    // slider
	    JSlider slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	    slider.setSize(400, 200);
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
				play();

			}
		};
		slider.addChangeListener(sliderListener);


		// radio button "pojedyncze dźwięki"
	    JRadioButton simpleRadioButton = new JRadioButton();
	    simpleRadioButton.setLabel("POJEDYNCZE DZWIEKI");
	    simpleRadioButton.setFont(new Font("Courier", Font.BOLD,20));
	    simplePanel.add(simpleRadioButton);
	    simpleRadioButton.setSelected(true);

	    rightPanel.add(simplePanel);
	    
	    // PANEL INTERWALOW
	    JPanel intervalPanel = new JPanel(new GridLayout(6,1));

		// radio button "interwały"
	    JRadioButton intervalRadioButton = new JRadioButton();
	    intervalRadioButton.setLabel("INTERWALY");
	    intervalRadioButton.setFont(new Font("Courier", Font.BOLD,20));
	    intervalPanel.add(intervalRadioButton);
	    
	    JLabel startingNoteLabel = new JLabel("Dzwiek poczatkowy ");
	    //startingNoteLabel.setFont(new Font("Courier", Font.BOLD,10));
	    intervalPanel.add(startingNoteLabel);

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
				if(plotChartPanel.getMode().equals("INTERWALY")) play();

			}
		};
		soundIntervalComboBox.addActionListener(soundComboListener);

	    JLabel intervalLabel = new JLabel("Interwał");
	    intervalPanel.add(intervalLabel);

		// combo box dla interwałów
		JComboBox intervalsComboBox = new JComboBox(interval_names);
		intervalPanel.add(intervalsComboBox);

		ActionListener intervalListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource();
				chosenInterval = (String)cb.getSelectedItem();
				if(plotChartPanel.getMode().equals("INTERWALY")) play();
			}
		};
		intervalsComboBox.addActionListener(intervalListener);

//	    JLabel emptyLabel = new JLabel("");
//	    intervalPanel.add(emptyLabel);
	    
	    rightPanel.add(intervalPanel);
	    
	    //PANEL AKORDOW
	    JPanel chordPanel = new JPanel(new GridLayout(8,1));


	    JRadioButton chordRadioButton = new JRadioButton("AKORDY");
	    chordRadioButton.setFont(new Font("Courier", Font.BOLD,20));
	    chordPanel.add(chordRadioButton);
	    
	    JLabel scaleLabel = new JLabel("Skala ");
	    chordPanel.add(scaleLabel);

		JComboBox soundChordComboBox = new JComboBox(tone_names);
		chordPanel.add(soundChordComboBox);

		ActionListener soundForChordComboListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource(); // this is needeed for retrieving value of combo box
				chosenSemitoneForChord = (String)cb.getSelectedItem();
				// because it should be memoried independently of semitone for chords
				chosenSemitone = chosenSemitoneForChord;
			if(plotChartPanel.getMode().equals("AKORDY")) play();			}
		};
		soundChordComboBox.addActionListener(soundForChordComboListener);
	    
	    JLabel keyLabel = new JLabel("Tonacja ");
	    chordPanel.add(keyLabel);
	    
	    String[] key = {"MAJOR", "MINOR"};
	    JComboBox chordType = new JComboBox(key);
		chordPanel.add(chordType);

		ActionListener chordListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox cb = (JComboBox)event.getSource(); // this is needeed for retrieving value of combo box
				chosenChord = (String)cb.getSelectedItem();
				if(plotChartPanel.getMode().equals("AKORDY")) play();
			}
		};

		chordType.addActionListener(chordListener);

	    JLabel triadLabel = new JLabel("Funkcja (triada harmoniczna) ");
	    chordPanel.add(triadLabel);

	    //PANEL TRIADY
	    JPanel triadPanel = new JPanel(new GridLayout(3,1));
	    
	    JRadioButton TButton = new JRadioButton("TONIKA");
	    triadPanel.add(TButton);
	    
	    JRadioButton SButton = new JRadioButton("SUBDOMINANTA");
	    triadPanel.add(SButton);
	    
	    JRadioButton DButton = new JRadioButton("DOMINANTA");
	    triadPanel.add(DButton);
	    
	    chordPanel.add(triadPanel);
	    
	    rightPanel.add(chordPanel);
	    
	  //sic = simple, interval, chord
	    ButtonGroup sicGroup = new ButtonGroup();
	    sicGroup.add(simpleRadioButton);
	    sicGroup.add(intervalRadioButton);
	    sicGroup.add(chordRadioButton);

		ActionListener radioGroupListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plotChartPanel.setMode(e.getActionCommand());
				play();
			}
		};

		simpleRadioButton.addActionListener(radioGroupListener);
		intervalRadioButton.addActionListener(radioGroupListener);
		chordRadioButton.addActionListener(radioGroupListener);

	    
	    //TSD = tonika, subdominanta, dominanta
	    ButtonGroup TSDGroup = new ButtonGroup();
	    TSDGroup.add(TButton);
	    TSDGroup.add(SButton);
	    TSDGroup.add(DButton);
	
	    RadioListener radioListener = new RadioListener();
	    simpleRadioButton.addActionListener(radioListener);
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
		if(plotChartPanel.getMode().equals("POJEDYNCZE DZWIEKI")) {
			plotChartPanel.draw(chosenSemitone, chosenOctave, chosenLevel / 100.0, chosenInterval, chosenChord);
		}
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

	// fixme this should be used instead of Strings
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
