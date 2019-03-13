package com.csvplotter.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;



public class Data 
{
	
	/**
	 * Helper method to charts in order to retrieve the table from the current tab
	 * @param tp The Current Tab
	 * @return
	 */
	public static TableView retrieveFromTabPane(TabPane tp)
	{
		return (TableView) tp.getSelectionModel().getSelectedItem().getContent();
	}
	public static List<Row> read(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String[] keys = reader.readLine().split(",");
		List<Row> result = new ArrayList<>(); //I don't care that it's an array list. I
 		
		
		while(true)
		{
			String line = reader.readLine();
			//line == null means there are no more lines
			if(line == null)
			{
				break;
			}
			//process the line
			String[] values = line.split(",");
			Row row = new Row();
			for(int i = 0; i < keys.length; i++)
			{
				try
				{
					row.put(keys[i], Double.parseDouble(values[i]));
				}
				catch(NumberFormatException ex)
				{
					row.put(keys[i],values[i]);
				}
			}
			result.add(row);
		}
		reader.close();
		System.out.println(result);
		return result;
	}
}
