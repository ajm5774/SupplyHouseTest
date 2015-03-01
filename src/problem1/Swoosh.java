package problem1;

import java.lang.Math;
import java.lang.Double;
import java.util.Arrays;
import java.lang.String;
import java.lang.StringBuilder;

public class Swoosh {

	private int origWidth = 16;
	private int origHeight = 8;
	
	public void drawSwoosh(int width)
	{
		int standardWidth = 16;
		
		//edge points on standard swoosh
		int[][] swooshPoints = 
			{
				{0,3}, {0,12},
				{1,1},{1,14},
				{2,0},{2,15},
				{3,0},{3,11},
				{4,0},{4,7},
				{5,0},{5,5},
				{6,1},{6,6},
				{7,3},{7,7}
			};
		
		
		double printRatio = (double)width/(double)standardWidth;
		int newWidth = (int)((double)origWidth*printRatio);
		int newHeight = (int)((double)origHeight*printRatio);
		
		swooshPoints = scalePoints(swooshPoints, printRatio);
		
		int[][]newSwooshPoints;
		//added rows, add points to new rows so they arent empty
		if(newWidth >= origWidth)
			newSwooshPoints = addNewPoints(swooshPoints, newHeight);
		//Multiple rows collapse down to a single row leaving 2+
		//points per row. Only use two points per row.
		else
		{
			int numRows = (int)(7.0 * printRatio)+1;
			newSwooshPoints = filterTwoPerRow2(swooshPoints, numRows);
			if(numRows < newHeight)
				newSwooshPoints = addNewPoints(newSwooshPoints, newHeight);
		}
		
		//Use the coordinates to generate a filled grid, and print
		String swooshString = stringSwoosh(newSwooshPoints, newHeight, newWidth);
		System.out.println(swooshString);
	}
	
	/*
	 * Scale the initial points to fit the end size
	 */
	private int[][] scalePoints(int[][] swooshPoints, double ratio)
	{
		for(int i = 0; i < swooshPoints.length; i++)
		{
			for(int j = 0; j < swooshPoints[i].length; j++)
			{
				swooshPoints[i][j] = (int)(Math.round((double)swooshPoints[i][j])*ratio);
			}
		}
		
		return swooshPoints;
	}
	
	/*
	 * Limit the stars per row to only 2 
	 * 
	 * Approach 1, grab the furthest points from each other
	 */
	private int[][] filterTwoPerRow(int[][] swooshPoints, int numRows)
	{
		int[][] newSwooshPoints = new int[numRows*2][2];
		
		int colHigh, colLow;
		int row = 0;
		int newIndex = 0;
		
		for(int i = 0; i < swooshPoints.length; i++)
		{
			
			colHigh = 0;
			colLow = 0;
			
			while(i < swooshPoints.length && swooshPoints[i][0] == row)
			{
				if(colLow > swooshPoints[i][1])
					colLow = swooshPoints[i][1];
				
				if(colHigh < swooshPoints[i][1])
					colHigh = swooshPoints[i][1];
				
				i++;
			}
			if(colHigh != colLow)
			{
				newSwooshPoints[newIndex][0] = row;
				newSwooshPoints[newIndex][1] = colLow;
				newIndex++;
				newSwooshPoints[newIndex][0] = row;
				newSwooshPoints[newIndex][1] = colHigh;
				newIndex++;
			}
			row++;
		}
		
		return newSwooshPoints;
	}
	
	/*
	 * Limit the stars per row to only 2 
	 * 
	 * Approach 2, grab the first two points (topmost points if two rows collapse down to one)
	 * Ideally, the two consecutive points with the longest length should be picked
	 */
	private int[][] filterTwoPerRow2(int[][] swooshPoints, int newHeight)
	{
		int[][] newSwooshPoints = new int[newHeight*2][2];

		int row = 0;
		int newIndex = 0;
		int numPoints = 2;
		
		for(int i = 0; i < swooshPoints.length -1;)
		{
			//grab first two points
			for(int j = 0; j < numPoints; j++)
			{
				newSwooshPoints[newIndex][0] = row;
				newSwooshPoints[newIndex][1] = swooshPoints[i][1];
				newIndex++;
				i++;
			}
			
			while(i < swooshPoints.length && swooshPoints[i][0] == row)
			{
				i++;
			}
			row++;
		}
		
		return newSwooshPoints;
	}
	
	/*
	 * Fill in the intermediate rows by averaging the coordinates in surrounding rows
	 */
	private int[][] addNewPoints(int[][] swooshPoints, int newHeight)
	{
		int[][] newSwooshPoints = new int[newHeight*2][2];
		int row, col, nextRow, nextCol, rowDiff, newRow, newCol, prevRow, prevCol;
		int[][] lastAddedRow = new int[2][2];
		double colDiff;
		int numPointsPerRow = 2;
		
		int i = 0;
		int pntIndex = 0;
		while(i < swooshPoints.length)
		{
			row = swooshPoints[i][0];
			col = swooshPoints[i][1];
			
			//add old points
			newSwooshPoints[pntIndex][0] = row;
			newSwooshPoints[pntIndex][1] = col;
			pntIndex++;
			
			//get next row
			if(i < swooshPoints.length-2)
			{
				nextRow = swooshPoints[i+2][0];
				nextCol = swooshPoints[i+2][1];
			}
			//last row needs to be extrapolated and created manually
			else
			{
				//use the last 
				prevCol = swooshPoints[i-2][1];
				colDiff = col - prevCol;
				nextRow = newHeight;
				nextCol = (int)(col + colDiff);
			}
			
			//add new points by averaging the x and y differences of previous and next row 
			rowDiff = nextRow-row;
			for(int j = 1; j < rowDiff; j++)
			{
				newRow = row + j;
				colDiff = (double)(nextCol - col) * ((double)j/(double)rowDiff);//average the points
				newCol = (int)(colDiff) + col;
				newSwooshPoints[pntIndex][0] = newRow;
				newSwooshPoints[pntIndex][1] = newCol;
				pntIndex++;
			}
			
			i++;
		}
		
		return newSwooshPoints;
	}
	
	/*
	 * Print out the swoosh
	 */
	private String stringSwoosh(int[][] swooshPoints, int newHeight, int newWidth)
	{
		String[][] resultArray = new String[newHeight][newWidth];
		StringBuilder resultString = new StringBuilder("");
		
		//fill grid with all -
		for(String[] row: resultArray)
			Arrays.fill(row, "-");
		
		//mark the coordinate in grid with an x
		for(int[] points: swooshPoints)
		{
			resultArray[points[0]][points[1]] = "x";
		}
		
		boolean startFill = false;
		for(String[] row: resultArray)
		{
			startFill = false;
			for(int j = 0; j < row.length; j++)
			{
				if(row[j] == "x")
				{
					startFill = !startFill;
				}
				else
				{
					if(startFill)
						row[j] = "x";
				}
				resultString.append(row[j]);
				resultString.append(" ");
			}
			resultString.append("\n");
		}
		
		return resultString.toString();
	}
}
