/**
 * 
 */
package com.example.astar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	
	private static Node start;
	
	private static Node goal;
	
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
		
		Astar astar = new Astar(graph);
		HashMap<Node, Node> shortestPath = astar.findShortestPath(start, goal);
		System.out.println("Input Graph:\n");
		printGraph();
		System.out.println("Shortest Path\n");
		printShortestPath(shortestPath);
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
			for(x = 0; x < line.length(); x++) {
				if (line.charAt(x) == sourceSymbol) {
					start = new Node(x, y);
				}
				
				if (line.charAt(x) == destinationSymbol) {
					goal = new Node(x, y);
				}
				edgeWeight = symbolMap.get(line.charAt(x));
//				System.out.println(edgeWeight);
				if (edgeWeight != null) {
					graph[y][x] = edgeWeight;
				} else {
					// any stray char is considered as obstacle 
					graph[y][x] = Integer.MAX_VALUE;
				}
			}
			y++;
			
		}
	}
	
	private static void printGraph() {
		for (int y = 0; y < graph.length; y++) {
			for (int x = 0; x < graph.length; x++) {
				System.out.print(graph[y][x] + " ");
			}
			System.out.print("\n");
		}
	}
	
	private static void printShortestPath(HashMap<Node, Node> shortestPath) {
		Node next = goal;
		System.out.print(next);
		while(!next.equals(start)) {		
			System.out.print(" => " + shortestPath.get(next));
			next = shortestPath.get(next);
		}
	}

}
