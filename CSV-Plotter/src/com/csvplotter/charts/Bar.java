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
 * Sets up the dialog box prompt for the bar chart
 * 
 * @param st Stage of the main application
 * @param tp TabPane contains the current data
 * @author giovanniflores
 *
 */
public class Bar {

	public static void dialogSetup(Stage primaryStage, TabPane tp) {
		ChoiceBox<String> columnBox = new ChoiceBox<>();

		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		dialog.initOwner(primaryStage);
		VBox vb = new VBox();
		HBox hb = new HBox();

		vb.spacingProperty().set(20.0);
		vb.setAlignment(Pos.CENTER);

		TableView tv = (TableView) Data.retrieveFromTabPane(tp);

		tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Row> data = tv.getItems();
		Map<String, Object> firstRow = data.get(0);
		LinkedList<String> listOfPossibilities = new LinkedList<>(firstRow.keySet());
		ObservableList<String> model = FXCollections.observableList(listOfPossibilities);

		columnBox.getItems().addAll(model);

		Label columnLabel = new Label("Column");
		hb.getChildren().addAll(columnLabel, columnBox);
		hb.setAlignment(Pos.CENTER);

		Button plotBar = new Button("Plot!");

		vb.getChildren().addAll(hb, plotBar);
		Scene dialogScene = new Scene(vb, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();

		plotBar.setOnAction(e -> {
			Pane pane = new Pane();
			pane.setPrefWidth(500);

			pane.setPrefHeight(500);

			String column = columnBox.getValue();

			Plotter.displayBarChart(column, data, pane);

			pane.widthProperty().addListener((obs, old, newV) -> {
				pane.getChildren().clear();
				pane.setPrefWidth(pane.getWidth());
				pane.setPrefHeight(pane.getHeight());
				Plotter.displayBarChart(column, data, pane);

			});
			pane.heightProperty().addListener((obs, old, newV) -> {
				pane.getChildren().clear();
				pane.setPrefWidth(pane.getWidth());
				pane.setPrefHeight(pane.getHeight());
				Plotter.displayBarChart(column, data, pane);
			});
		});

	}

}
