package com.csvplotter.charts;

import java.util.LinkedList;
import java.util.Map;

import com.csvplotter.graphing.Plotter;
import com.csvplotter.utils.Data;
import com.csvplotter.utils.Row;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Sets up the dialog box prompt for the Scatter chart
 * @param st Stage of the main application
 * @param tp TabPane contains the current data
 * @author giovanniflores
 *
 */
public abstract class Scatter {
	public static void dialogSetup(Stage st, TabPane tp)
	{
		ChoiceBox<String> xaxisBox = new ChoiceBox<>();
		ChoiceBox<String> yaxisBox = new ChoiceBox<>();
		
		
		
		
		Stage dialog = new Stage(); 
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        dialog.initOwner(st);
        VBox vb = new VBox();
        	HBox hb = new HBox();
        	HBox hb2 = new HBox();
        	
        	
        	vb.spacingProperty().set(20.0);
        	vb.setAlignment(Pos.CENTER);
        	
        TableView tv = (TableView) Data.retrieveFromTabPane(tp);//we get it from the selected tab. 
        tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Row> data = tv.getItems();
        Map<String, Object> firstRow = data.get(0);
        
        LinkedList<String> listOfPossibilities = new LinkedList<>(firstRow.keySet());
        
        ObservableList<String> model = FXCollections.observableList(listOfPossibilities);
        
        xaxisBox.getItems().addAll(model);
        yaxisBox.getItems().addAll(model);
        
        Label xaxisLabel = new Label("x-axis");
        Label yaxisLabel = new Label("y-axis");
        hb.getChildren().addAll(xaxisLabel, xaxisBox);
       
        hb.setAlignment(Pos.CENTER);
        
        hb2.getChildren().addAll(yaxisLabel, yaxisBox);
        hb2.setAlignment(Pos.CENTER);
        
        Button plotScatter = new Button("Plot");
        
        
       
        vb.getChildren().addAll(hb, hb2, plotScatter);
        Scene dialogScene = new Scene(vb, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        
        plotScatter.setOnAction(e ->
        {
        		dialog.close();
        		Pane pane = new Pane();
        		pane.setPrefWidth(500);
        		
        		pane.setPrefHeight(500);
        		
        		            		
        		
        		String xaxis = xaxisBox.getValue();
        		String yaxis = yaxisBox.getValue();
        		
        		
        		
        		Plotter.displayScatterPlot(xaxis, yaxis, data, tv, pane);
        		
        		pane.widthProperty().addListener((obs,old,newV) ->
        		{

				pane.getChildren().clear();
				pane.setPrefWidth(pane.getWidth());
				pane.setPrefHeight(pane.getHeight());
            		Plotter.displayScatterPlot(xaxis, yaxis,  data, tv, pane);

        		});
        		pane.heightProperty().addListener((obs,old,newV)->
        		{
        			System.out.println("Changed");
        			pane.getChildren().clear();
        			pane.setPrefWidth(pane.getWidth());
        			pane.setPrefHeight(pane.getHeight());
        			Plotter.displayScatterPlot(xaxis, yaxis, data, tv, pane);
        		});
        });
	}
}
