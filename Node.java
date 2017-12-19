import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Node implements Comparable<Node> {
	private Float			cord1, cord2;
	private Map<Edge, Node>	connections	= new HashMap<Edge, Node>();
	private String			name;
	private boolean			visited		= false;
	private Float			distance	= Float.POSITIVE_INFINITY;
	private Color			c			= Color.white;
	private Node			prev		= null;
	private Point			point;

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * @return the prev
	 */
	public Node getPrev() {
		return prev;
	}

	/**
	 * @param prev
	 *            the prev to set
	 */
	public void setPrev(Node prev) {
		this.prev = prev;
	}

	/**
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setC(Color c) {
		this.c = c;
	}

	/**
	 * @return the connections
	 */
	public Map<Edge, Node> getConnections() {
		return connections;
	}

	/**
	 * @param connections
	 *            the connections to set
	 */
	public void setConnections(HashMap<Edge, Node> connections) {
		this.connections = connections;
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * @param visited
	 *            the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return the distance
	 */
	public Float getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public void addNode(Node in, Edge i) {
		if (!connections.containsKey(i)) connections.put(i, in);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cord1
	 */
	public Float getCord1() {
		return cord1;
	}

	/**
	 * @param cord1
	 *            the cord1 to set
	 */
	public void setCord1(Float cord1) {
		this.cord1 = cord1;
	}

	/**
	 * @return the cord2
	 */
	public Float getCord2() {
		return cord2;
	}

	/**
	 * @param cord2
	 *            the cord2 to set
	 */
	public void setCord2(Float cord2) {
		this.cord2 = cord2;
	}

	/**
	 * @param cord1
	 * @param cord2
	 */
	public Node(String name, Float cord1, Float cord2) {
		this.name = name;
		this.cord1 = cord1;
		this.cord2 = cord2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Node o) {
		// NOTE: if you want to have some fun, swap -1 and 1, and you will get the LONGEST path between two nodes
		if (o.getDistance() < getDistance()) return 1;
		if (o.getDistance() > getDistance()) return -1;
		return 0;
	}

}
