/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaBall_results;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author 2162068h
 * Modified from http://docs.oracle.com/javafx/2/charts/bar-chart.htm
 */
public class ResultsBarChart extends Application {
    @Override
        public void start(Stage stage) {
        stage.setTitle("Results - Bar Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<>(xAxis,yAxis);
        bc.setTitle("Results by team");
        xAxis.setLabel("Team");       
        yAxis.setLabel("Goals");
        Parameters param = getParameters();
        List<String> str;
        str = param.getUnnamed();
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Goal for");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Goal against");
        for (int i = 0; i < str.size(); i += 3){
            series1.getData().add(new XYChart.Data(str.get(i), 
                    Integer.parseInt(str.get(i + 1))));
            series2.getData().add(new XYChart.Data(str.get(i), 
                    Integer.parseInt(str.get(i + 2))));
        }
         
        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }
        
        public static void main(String[] args) {
        launch(args);
        }
}
