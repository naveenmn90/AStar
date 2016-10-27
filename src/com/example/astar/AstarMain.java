/**
 * 
 */
package com.example.astar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Scanner;


/**
 * @author naveenmn
 * AStar driver class
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
	private static HashMap<Integer, Character> reverseSymbolMap; 
	
	private static char sourceSymbol = '@';
	
	private static char destinationSymbol = 'X';
	
	private static Node start;
	
	private static Node goal;
	
	private static int numberOfVerices;
	
	private static final String OUTPUT_FOLDER = "graphs" + 
			File.separator + "output" + File.separator;
	
	
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
		
		reverseSymbolMap = new HashMap<>();
		reverseSymbolMap.put(Integer.MAX_VALUE, '~');
		reverseSymbolMap.put(1, '.');
		reverseSymbolMap.put(0, '#');
		reverseSymbolMap.put(2, '*');
		reverseSymbolMap.put(3, '^');
		
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
//		printGraph();
		
		if (shortestPath.isEmpty()) {
			System.out.println("No path found to goal" + goal);
			System.exit(0);
		}
		System.out.println("Shortest Path\n");
//		printShortestPath(shortestPath);
		try {
			createMapFile(shortestPath, graphFile);
		} catch (IOException e) {
			System.out.println("Error in creating output file");
		}
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
		in.close();
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
	
	private static void createMapFile(HashMap<Node, Node> shortestPath, File sourceFile) throws IOException {
		Node next = goal;
		while(!next.equals(start)) {		
			graph[next.getY()][next.getX()] = 0;
			next = shortestPath.get(next);
		}
		graph[next.getY()][next.getX()] = 0;
		
		String destination = OUTPUT_FOLDER + sourceFile.getName();
		File targetFile = new File(destination);
		if (targetFile.createNewFile()) {
			System.out.println("Output file created");
		}
		RandomAccessFile output = new RandomAccessFile(destination, "rw");
		for (int y = 0; y < graph.length; y++) {
			for (int x = 0; x < graph.length; x++) {
				output.writeChar(reverseSymbolMap.get(graph[y][x]));
			}
			output.writeChars("\n");
		}
		output.close();		
	}

}
