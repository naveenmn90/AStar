package com.example.astar;

import java.util.ArrayList;

/**
 * @author naveenmn
 * Graph is the representation of the map [grid]
 */
public class Graph {
	int[][] graph;
	public Graph(int[][] graphData) {
		graph = graphData;
	}
	
	/**
	 * @param from
	 * @param to
	 * @return cost assosiated with the path
	 */
	public int cost(Node from, Node to) {
		if (from.getX() == to.getX()) {
			return graph[to.getY()][from.getX()];
		} else {
			return graph[from.getY()][to.getX()];
		}
	}
	
	/**
	 * Identify the neighbors to the current Node
	 * @param current
	 * @return list of neighbor nodes
	 */
	public ArrayList<Node> neighbors(Node current) {
		ArrayList<Node> neighborNodes = new ArrayList<>();
		 
		if ((current.getY() + 1 < graph.length) && 
				graph[current.getX()][current.getY() + 1] < Integer.MAX_VALUE) {
			neighborNodes.add(new Node(current.getX(), current.getY() + 1));
		}
		if ((current.getY() - 1 >= 0) && 
				graph[current.getX()][current.getY() - 1] < Integer.MAX_VALUE) {
			neighborNodes.add(new Node(current.getX(), current.getY() - 1));
		}
		if ((current.getX() + 1 < graph.length) && 
				graph[current.getY()][current.getX() + 1] < Integer.MAX_VALUE) {
			neighborNodes.add(new Node(current.getX() + 1, current.getY()));
		}
		if ((current.getX() - 1 >= 0) && 
				graph[current.getY()][current.getX() - 1] < Integer.MAX_VALUE) {
			neighborNodes.add(new Node(current.getX() - 1, current.getY()));
		}

		return neighborNodes;
	}
}
