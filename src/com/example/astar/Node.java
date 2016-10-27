package com.example.astar;

import java.util.Comparator;

/**
 * @author naveenmn
 * Node represents a vertex in the graph
 * In our grid representation every cell is vertex
 */
public class Node {
	private final int x, y;
	
	//priority is used for priority Queue
	private int priority;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	//Comparator for priority queue
	public static Comparator<Node> priorityComparator = new Comparator<Node>() {
		
		@Override
		public int compare(Node n1, Node n2) {
			return n1.getPriority() - n2.getPriority();
		}
	};

	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
	    if (!(other instanceof Node))
	          return false;
	    Node node = (Node)other;
		if (this.x == node.getX() && this.y == node.getY()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (Integer.toString(x) + "," + Integer.toString(y)).hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + x + " ," + y + "]";
	}
	
}