
public class Edge {
	private String	name;
	private Node	node1, node2;
	private float	distance;
	private boolean	traveled	= false;

	/**
	 * @return the traveled
	 */
	public boolean isTraveled() {
		return traveled;
	}

	/**
	 * @param traveled
	 *            the traveled to set
	 */
	public void setTraveled(boolean traveled) {
		this.traveled = traveled;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}

	/**
	 * @param name
	 * @param node1
	 * @param node2
	 */
	public Edge(Node node1, Node node2) {

		this.node1 = node1;
		this.node2 = node2;
		distance = (float) Math.sqrt(Math.pow(node2.getCord1() - node1.getCord1(), 2) + Math.pow(node2.getCord2()
		        - node1.getCord2(), 2));//approximation assuming for a given map, it is nearly flat
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
	 * @return the node1
	 */
	public Node getNode1() {
		return node1;
	}

	/**
	 * @param node1
	 *            the node1 to set
	 */
	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	/**
	 * @return the node2
	 */
	public Node getNode2() {
		return node2;
	}

	/**
	 * @param node2
	 *            the node2 to set
	 */
	public void setNode2(Node node2) {
		this.node2 = node2;
	}
}
