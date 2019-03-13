package com.csvplotter.utils;

import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DialogBox {
	
	public static void chooser(Stage st) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		ObservableList<String> xVals = FXCollections.observableArrayList(new LinkedList<>());
		xVals.addAll("x", "y");
		@SuppressWarnings("unused")
		ObservableList<String> yVals = FXCollections.observableArrayList(new LinkedList<>());
		@SuppressWarnings("unused")
		ChoiceBox<String> chooseX = new ChoiceBox<>();

	}
	

}
