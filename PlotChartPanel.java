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

    PlotChartPanel(JPanel aimPanel){
         XYSeries dataSet2= new XYSeries("seria 2");

         double frequency = 440.0;
         // jednostką jest jedna sekunda
             for (double i=0; i < 0.1; i+=0.0001) {
                 dataSet2.add(i, Math.sin(i * Math.PI * 2 * frequency));
             }

             XYSeriesCollection xySeriesCollection = new XYSeriesCollection(dataSet2); 
           
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

    void setData(){
    	 if(option==1){
    		 
    	 }
    	 else if(option==2){
    		 
    	 }
    	 else if(option==3){
    		 
    	 }
     }
}
