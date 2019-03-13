package com.csvplotter.app;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.csvplotter.charts.Bar;
import com.csvplotter.charts.Bubble;
import com.csvplotter.charts.Scatter;
import com.csvplotter.utils.Data;
import com.csvplotter.utils.Row;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Welcome to the CSV Plotter!
 * This project is an excel-like data set application that takes in a .csv file as input, puts it in a table view, 
 * and allows for some rough graphing capabilities using scatter, bubble, and or bar charts.
 * @author giovanniflores
 *
 */
public class Main extends Application {
	private static TabPane tp;

	
	private void load(File curFile) {
		try {
			ObservableList<Row> data = FXCollections.observableList(Data.read(curFile));
			TableView<Row> table = new TableView<>(data);
			Row firstRow = data.get(0);
			for (String s : firstRow.keySet()) {
				TableColumn<Row, Object> column = new TableColumn<>(s);
				column.setCellValueFactory(new MapValueFactory(s));
				table.getColumns().add(column);

			}
			Tab curTab = new Tab();
			curTab.setText(curFile.getName());
			curTab.setContent(table);
			tp.getTabs().add(curTab);
			tp.getSelectionModel().select(curTab);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void start(Stage primaryStage) {
		tp = new TabPane();
		MenuBar mb = new MenuBar();
		Menu newFile = new Menu("Document");
		Menu plotting = new Menu("Plot");

		mb.getMenus().addAll(newFile, plotting);

		MenuItem loadedFile = new MenuItem("Open");

		newFile.getItems().add(loadedFile);

		MenuItem scatter = new MenuItem("Scatter");
		plotting.getItems().add(scatter);

		MenuItem bubble = new MenuItem("Bubble");
		plotting.getItems().add(bubble);

		MenuItem bar = new MenuItem("Bar");
		plotting.getItems().add(bar);
		loadedFile.setOnAction((ActionEvent event) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Data Doc");
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				load(selectedFile);
			}
		});

		scatter.setOnAction((ActionEvent event) -> {
			Scatter.dialogSetup(primaryStage, tp);

		});
		bubble.setOnAction((ActionEvent event) -> {

			Bubble.dialogSetup(primaryStage, tp);
		});
		bar.setOnAction((ActionEvent event) -> {
			Bar.dialogSetup(primaryStage, tp);
		});

		VBox vBox = new VBox();
		vBox.getChildren().addAll(mb, tp);
		Scene sc = new Scene(vBox, 600, 600);
		primaryStage.setScene(sc);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
