/**
 * 
 */
package com.example.astar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * @author naveenmn
 *
 */
public class AstarMain {
	
	/**
	 * Adjacency matrix
	 */
	private static int[][] graph;
	
	/**
	 * Symbol Table to hold the weight of each symbol in graph file
	 */
	private static HashMap<Character, Integer> symbolMap; 
	
	private static char sourceSymbol = '@';
	
	private static char destinationSymbol = 'X';
	
	private static int sourceIndex;
	
	private static int destinationIndex;
	
	private static int numberOfVerices;
	
	/**
	 * @param args filname, which contains the graph info
	 */
	public static void main(String[] args) {
		symbolMap = new HashMap<>();
		symbolMap.put('~', Integer.MAX_VALUE);
		symbolMap.put('.', 1);
		symbolMap.put('@', 1);
		symbolMap.put('X', 1);
		symbolMap.put('*', 2);
		symbolMap.put('^', 3);
		
		File graphFile = null;
		if( args.length > 0) {
			graphFile = new File(args[0]);
		} else {
			System.out.println("Please supply the graph file as an argument");
			System.exit(1);
		}
		try {
			createAdjacencyMatrix(graphFile);
		} catch (FileNotFoundException e) {
			System.out.println("Graph file not found");
		}
		
		printGraph();

	}
	
	private static void createAdjacencyMatrix(File graphFile) throws FileNotFoundException {
		Scanner in = new Scanner(graphFile);
		Integer edgeWeight = null;
		int x = 0;
		int y = 0;
		while(in.hasNext()) {
			String line = in.next();
			if (graph == null) {
				numberOfVerices = line.length();
				graph = new int[numberOfVerices][numberOfVerices];
			}
			for(y = 0; y < line.length(); y++) {
				if (line.charAt(y) == sourceSymbol) {
					sourceIndex = y;
				}
				
				if (line.charAt(y) == destinationSymbol) {
					destinationIndex = y;
				}
				edgeWeight = symbolMap.get(line.charAt(y));
//				System.out.println(edgeWeight);
				if (edgeWeight != null) {
					graph[x][y] = edgeWeight;
				} else {
					// any stray char is considered as obstacle 
					graph[x][y] = Integer.MAX_VALUE;
				}
			}
			x++;
			
		}
	}
	
	private static void printGraph() {
		for (int x = 0; x < graph.length; x++) {
			for (int y = 0; y < graph.length; y++) {
				System.out.print(graph[x][y] + " ");
			}
			System.out.print("\n");
		}
	}

}
