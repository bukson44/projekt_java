package projekt;

import java.util.List;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset;
/**
 * @author Jakub Bukowski
 */
public class PlotChartPanel extends JPanel {
	String plotTitle = "fala akustyczna";
	int option=1;
	List<XYSeries> dataSet;
    XYSeries wave;
    JPanel aimPanel;
    XYSeriesCollection xySeriesCollection;

    double stroj = 440.0; // fixme angielska nazwa stroju czy jak

    public void drawWaveform(String semitoneStr, String octaveStr) {
        wave.clear();
        calculateWave(semitoneStr, octaveStr);
        render();
    }

    private void calculateWave(String semitoneStr, String octaveStr) {

        int semitone = semitoneToInt(semitoneStr);
        int octave = octaveToInt(octaveStr);
        double frequency = stroj * Math.pow(2, (semitone / 12.0)) * Math.pow(2, octave);

        for (double i=0; i < 0.1; i+=0.0001) {
            wave.add(i, Math.sin(i * Math.PI * 2 * frequency));
        }

        System.out.println(wave);

        System.out.println(semitone + " " + octave);
    }

    private void render() {
        xySeriesCollection = new XYSeriesCollection(wave);
        XYDataset dataset = xySeriesCollection;

        JFreeChart lineGraph = ChartFactory.createXYLineChart
                ("",  // Title
                        "Czas [s]",           // X-Axis label
                        "Wychylenie z położenia równowagi",           // Y-Axis label
                        dataset,          // Dataset
                        PlotOrientation.VERTICAL,        //Plot orientation
                        true,                //show legend
                        true,                // Show tooltips
                        false               //url show
                );


        ChartPanel chartPanel = new ChartPanel(lineGraph);
        aimPanel.add(chartPanel);
    }

    private int semitoneToInt(String semitoneStr) {
        int semitone = 0;
        switch(semitoneStr) {
            case "C": semitone = -9; break;
            case "C#": semitone = -8; break;
            case "D": semitone = -7; break;
            case "D#": semitone = -6; break;
            case "E": semitone = -5; break;
            case "F": semitone = -4; break;
            case "F#": semitone = -3; break;
            case "G": semitone = -2; break;
            case "G#": semitone = -1; break;
            case "A": semitone = 0; break;
            case "B": semitone = 1; break;
            case "H": semitone = 2; break;
            default: semitone = 0;
        }
        return semitone;
    }

    private int octaveToInt(String octaveStr) {
        int octave = 0;
        switch (octaveStr) {
            case "Subkontra": octave = -4; break;
            case "Kontra": octave = -3; break;
            case "Wielka": octave = -2; break;
            case "Mała": octave = -1; break;
            case "Razkreślna": octave = 0; break;
            case "Dwukreślna": octave = 1; break;
            case "Trzykreślna": octave = 2; break;
            case "Czterokreślna": octave = 3; break;
        }
        return octave;
    }

    PlotChartPanel(JPanel aimPanel){
        this.aimPanel = aimPanel;
        wave = new XYSeries("seria 2");
        render();
     }

    void setData(){
    	 if(option==1){
    		 
    	 }
    	 else if(option==2){
    		 
    	 }
    	 else if(option==3){
    		 
    	 }
     }
}
