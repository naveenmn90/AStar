package com.example.astar;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author naveenmn
 * AStar Algorithm
 */
public class Astar {
	
	private PriorityQueue<Node> explorer;
	private Graph graph;
	
	
	/**
	 * @param graph Adjacency matrix
	 */
	public Astar(int[][] graph) {
		this.graph = new Graph(graph);
		this.explorer = new PriorityQueue<>(Node.priorityComparator);
	}
	
	/**
	 * Find the shortest path 
	 * @param start Start node
	 * @param goal End node
	 * @return HashMap<Node, Node>: if there is a path it contains the path information i
	 * form of [Goal] => [NearesttoGoal] => [NearerToGoal] => ... => [Start] <br \>
	 * 
	 * If no path return empty map
	 */
	public HashMap<Node, Node> findShortestPath(Node start, Node goal) {
		
		if (start == null || goal == null) {
			throw new NullPointerException("Start and Goal shouldnot be null");
		}
		
		Node current = null;
		HashMap<Node, Node> traveledFrom = new HashMap<>();
		HashMap<Node, Integer> pathCost = new HashMap<>();
		
		start.setPriority(0);
		traveledFrom.put(start, null);
		pathCost.put(start, 0);
		explorer.add(start);
		
		int newCost;
		int priority;
		while(!explorer.isEmpty()) {
			current = explorer.poll();
			if (current.equals(goal)) {
				break;
			}
			
			for (Node next : graph.neighbors(current)) {
				newCost = pathCost.get(current) + graph.cost(current, next);
				
				if (!pathCost.containsKey(next) || newCost < pathCost.get(next)) {
					pathCost.put(next, newCost);
					priority = newCost + heuristic(goal, next);
					next.setPriority(priority);
					explorer.add(next);
					traveledFrom.put(next, current);
				}
			}
			
		}
		
		if (current == null || !current.equals(goal)) {
			return new HashMap<>();
		}
		
		HashMap<Node, Node> shortestPath = new HashMap<>();
		Node next = goal;
		while(!next.equals(start)) {		
			shortestPath.put(next, traveledFrom.get(next));
			next = traveledFrom.get(next);
		}
		
		return shortestPath;
	}
	
	private int heuristic(Node n1, Node n2) {
		// Manhattan distance formula
		return Math.abs(n1.getX() - n2.getX()) + Math.abs(n1.getY() - n2.getY());
	}
}
