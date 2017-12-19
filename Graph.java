import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Scanner;

public class Graph {
	//private ArrayList<Node>			intersections	= new ArrayList<Node>();
	private HashMap<String, Node>	is		= new HashMap<String, Node>();
	private ArrayList<Edge>			roads	= new ArrayList<Edge>();
	private PriorityQueue<Node>		p		= new PriorityQueue<Node>();

	public Graph(String fileName) {
		String line;
		String[] parsed;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			while ((line = bufferedReader.readLine()) != null) {
				parsed = line.split("[\\s+]");//split by whitespace
				if (line.charAt(0) == 'i') { //for intersections
					is.put(parsed[1], new Node(parsed[1], Float.parseFloat(parsed[3]) * .78f, Float.parseFloat(
					        parsed[2])));//.78f is because the map data is distorted with standard calculations
				}
				else {
					roads.add(new Edge(getNode(parsed[2]), getNode(parsed[3])));//edges take in two nodes
				}
			}
			bufferedReader.close();
		}

		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
		catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		for (Edge i : roads) {
			i.getNode1().addNode(i.getNode2(), i); //add connections to each node
			i.getNode2().addNode(i.getNode1(), i);
		}
	}

	public void path(String in) {
		// Core code where algorithm is actually implemented
		Node origin = getNode(in.split("[\\s+]")[0]); //parse directions text
		Node destination = getNode(in.split("[\\s+]")[1]);
		if (origin == null || destination == null) {
			System.out.println("One or more of your intersections was invalid, please try again");
			return;
		}
		origin.setC(Color.blue);
		destination.setC(Color.green);
		origin.setDistance(0.0f); //by default all nodes are set to infinity
		Node current = origin; // start in origin
		while (current != null) {
			current.setVisited(true); //set it as visited so it won't hit it again
			Iterator<Entry<Edge, Node>> it = current.getConnections().entrySet().iterator();//iterate through all the nodes connected to the current node
			while (it.hasNext()) {
				Map.Entry<Edge, Node> node = (Map.Entry<Edge, Node>) it.next();
				if (node.getValue().getDistance() > current.getDistance() + node.getKey().getDistance()) {
					//if the path through the current node is shorter than the distance programmed into the node rn
					node.getValue().setDistance((float) (current.getDistance() + node.getKey().getDistance())); //update distance
					node.getValue().setPrev(current); //set current node as the shortest connection 
				}
				if (!node.getValue().isVisited() && !p.contains(node.getValue())) p.add(node.getValue()); //if it hasent been visited (isn't alread in the queue), add it to priority queue
			}
			current = p.poll(); //remove shortest distance node
		}
		String out = destination.getName();
		int dis = (int) (destination.getDistance() * 69);//approximation based on lat and longitude and where ny is
		while (destination.getPrev() != null) {
			//travel through all the previous nodes until you reach the origin node (you can do this at any node to find the shortest path)
			edge(destination, destination.getPrev(), destination.getConnections()).setTraveled(true);
			destination = destination.getPrev();
			out = destination.getName().concat(" -> ").concat(out);

		}
		int i = 0;
		//formatting stuff
		for (String i2 : out.split("[\\s]")) {
			if (!i2.equals("->")) {
				System.out.format("%-8s", i2);
			}
			else System.out.format("%-3s", i2);
			if (i > 12) {
				System.out.println();
				i = 0;
			}
			else i++;

		}
		System.out.println("\nTotal distance: " + dis + " miles");

		if (!destination.equals(origin)) System.out.println("Sorry, unable to find a path between those two points.");

	}

	public Edge edge(Node in, Node in2, Map<Edge, Node> map) {
		//returns the edge that connects two nodes
		Iterator<Entry<Edge, Node>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Edge, Node> edge = (Map.Entry<Edge, Node>) it.next();
			if (edge.getValue().equals(in2)) return edge.getKey();
		}
		System.out.println("Error no connections between those two nodes");
		return null;
	}

	public ArrayList<Edge> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Edge> roads) {
		this.roads = roads;
	}

	public Node getNode(String in) {

		return is.get(in);

	}

	public Collection<Node> getIntersections() {
		return is.values();
	}
}
