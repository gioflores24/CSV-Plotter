package com.csvplotter.graphing;

import java.util.HashMap;

import java.util.Map;

import com.csvplotter.utils.Row;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Plotter {

	/**
	 * Handles axes and scatter plot size
	 * 
	 * @param xaxis
	 * @param yaxis
	 * @param data
	 * @param tv    Table in which data resides
	 * @param p
	 */
	public static void displayScatterPlot(String xaxis, String yaxis, ObservableList<Row> data, TableView tv, Pane p) {

		Stage st = new Stage();

		p.setPadding(new Insets(25, 25, 25, 25));

		Line xLine = new Line(25, p.getPrefHeight() - 20, p.getPrefWidth(), p.getPrefHeight() - 20);

		Line yLine = new Line(25, 25, 25, p.getPrefHeight() - 20);

		Label xLabel = new Label(xaxis);
		Label yLabel = new Label(yaxis);
		xLabel.setTranslateX(p.getPrefWidth() / 2);
		xLabel.setTranslateY(p.getPrefHeight() - 13);

		yLabel.setTranslateY(p.getPrefHeight() / 2);
		yLabel.setRotate(90);

		p.getChildren().addAll(yLabel, xLine, yLine, xLabel);
		createCircles(p, xaxis, yaxis, data, tv);

		Scene sc = new Scene(p, 550, 550);

		st.setScene(sc);
		st.show();
	}

	/**
	 * Handles scatter circle size, position, as well as maximum values
	 * 
	 * @param p     Pane in which to put data
	 * @param xaxis
	 * @param yaxis
	 * @param data
	 * @param tv    Table in which data resides, needed for highlighting row
	 */
	private static void createCircles(Pane p, String xaxis, String yaxis, ObservableList<Row> data, TableView tv) {
		long total = data.size();
		double maxXAxis = Double.MIN_VALUE;
		double maxYAxis = Double.MIN_VALUE;

		for (int i = 0; i < total; i++) {
			Row each = data.get(i);

			Double xCoordinate = (Double) each.get(xaxis);
			Double yCoordinate = (Double) each.get(yaxis);
			if (xCoordinate > maxXAxis) {
				maxXAxis = xCoordinate;
			}
			if (yCoordinate > maxYAxis) {
				maxYAxis = yCoordinate;
			}

		}
		for (int i = 0; i < total; i++) {
			Row each = data.get(i);
			Double xCoordinate = (Double) each.get(xaxis);
			Double yCoordinate = (Double) each.get(yaxis);

			Circle c = new Circle((xCoordinate / maxXAxis) * p.getPrefWidth() - 20,
					(yCoordinate / maxYAxis) * p.getPrefHeight() - 20, 5);

			p.getChildren().add(c);
			final int j = i;
			c.setOnMouseClicked(event -> {
				tv.getSelectionModel().clearSelection();
				tv.getSelectionModel().select(j);

			});
		}

	}

	/**
	 * Handles bubble circle size, position, as well as maximum values
	 * 
	 * @param p     Pane in which to put data
	 * @param xaxis
	 * @param yaxis
	 * @param data
	 * @param tv    Table in which data resides, needed for highlighting row
	 */
	private static void createBubbles(Pane p, String xaxis, String yaxis, String radius, ObservableList<Row> data,
			TableView tv) {
		long total = data.size();
		double maxXAxis = Double.MIN_VALUE;
		double maxYAxis = Double.MIN_VALUE;
		double maxRadius = Double.MIN_VALUE;

		for (int i = 0; i < total; i++) {

			Row each = data.get(i);
			Double xCoordinate = (Double) each.get(xaxis);
			Double yCoordinate = (Double) each.get(xaxis);
			Double bubbleRadius = (Double) each.get(radius);
			if (xCoordinate > maxXAxis) {
				maxXAxis = xCoordinate;
			}

			if (yCoordinate > maxYAxis) {
				maxYAxis = yCoordinate;
			}

			if (bubbleRadius > maxRadius) {
				maxRadius = bubbleRadius;
			}

		}

		for (int i = 0; i < total; i++) {
			Row each = data.get(i);
			Double xCoordinate = (Double) each.get(xaxis);
			Double yCoordinate = (Double) each.get(yaxis);
			Double bubbleRadius = (Double) each.get(radius);

			Circle c = new Circle((xCoordinate / maxXAxis) * p.getPrefWidth() - 20,
					(yCoordinate / maxYAxis) * p.getPrefHeight() - 20,
					(bubbleRadius / maxRadius) * (p.getPrefWidth() / 20));
			c.fillProperty().set(Color.DARKBLUE);

			p.getChildren().add(c);
			int j = i;
			c.setOnMouseClicked(event -> {
				tv.getSelectionModel().clearSelection();
				tv.getSelectionModel().select(j);
			});
		}
	}

	/**
	 * Handles axes and bubble plot size
	 * 
	 * @param xaxis
	 * @param yaxis
	 * @param r
	 * @param data
	 * @param tv
	 * @param p
	 */
	public static void displayBubbleChart(String xaxis, String yaxis, String r, ObservableList<Row> data, TableView tv,
			Pane p) {
		Stage st = new Stage();

		p.setPadding(new Insets(25, 25, 25, 25));

		Line xLine = new Line(25, p.getPrefHeight() - 20, p.getPrefWidth() - 20, p.getPrefHeight() - 20);

		Line yLine = new Line(25, 25, 25, p.getPrefHeight() - 20);

		Label xLabel = new Label(xaxis);
		Label yLabel = new Label(yaxis);
		xLabel.setTranslateX(p.getPrefWidth() / 2);
		xLabel.setTranslateY(p.getPrefHeight() - 13);

		yLabel.setTranslateY(p.getPrefHeight() / 2);
		yLabel.setRotate(90);

		p.getChildren().addAll(yLabel, xLine, yLine, xLabel);
		createBubbles(p, xaxis, yaxis, r, data, tv);

		Scene sc = new Scene(p, 550, 550);

		st.setScene(sc);
		st.show();

	}

	/**
	 * Handles axes and bar chart size
	 * 
	 * @param column The selected column of data
	 * @param data
	 * @param p      Pane in which to put chart
	 */
	public static void displayBarChart(String column, ObservableList<Row> data, Pane p) {
		Stage st = new Stage();

		p.setPadding(new Insets(25, 25, 25, 25));

		Line xLine = new Line(25, p.getPrefHeight() - 20, p.getPrefWidth(), p.getPrefHeight() - 20);

		Line yLine = new Line(25, 25, 25, p.getPrefHeight() - 20);

		Label xLabel = new Label(column);
		xLabel.setTranslateX(p.getPrefWidth() / 2);
		xLabel.setTranslateY(p.getPrefHeight() - 12);

		p.getChildren().addAll(xLine, yLine, xLabel);
		createBars(p, column, data);

		Scene sc = new Scene(p, 550, 550);

		st.setScene(sc);
		st.show();

	}

	/**
	 * Bars are created based on frequency of appearance. Handles their size,
	 * position, etc.
	 * 
	 * @param p
	 * @param column
	 * @param data
	 */
	private static void createBars(Pane p, String column, ObservableList<Row> data) {
		long total = data.size();

		HashMap<Object, Integer> each = new HashMap<>(); // number of keys in the column

		for (int i = 0; i < total; i++) {
			Row category = data.get(i);
			Object key = category.get(column);

			if (each.containsKey(key) == true) {
				int count = each.get(key) + 1;
				each.replace(key, count);
			} else {
				each.put(key, 1);
			}

		}

		double widthOfEach = (p.getPrefWidth() - 20) / each.size();

		int amount = 0; // total categories

		for (Map.Entry<Object, Integer> c : each.entrySet()) {
			amount = amount + c.getValue();
			System.out.println(c.getKey() + " " + c.getValue());
		}
		double inBetween = 30;
		for (Map.Entry<Object, Integer> entry : each.entrySet()) {

			double valueOfY = (double) entry.getValue() / amount;
			double howBig = (p.getPrefHeight() - 20) * valueOfY;
			double beginningYPos = (p.getPrefHeight() - 20) - howBig;

			String barValue = new String(entry.getValue() + "");
			Label valueOfBar = new Label(barValue);
			valueOfBar.setTranslateX(inBetween + 40);
			valueOfBar.setTranslateY(beginningYPos - 20);

			Rectangle r = new Rectangle(inBetween, beginningYPos, widthOfEach - 2, howBig);
			r.fillProperty().set(Color.DARKBLUE);
			inBetween += widthOfEach;
			p.getChildren().addAll(r, valueOfBar);

		}
	}

}
